package com.learning.ExpenseTracker.service;

import com.learning.ExpenseTracker.dto.ExpenseDTO;
import com.learning.ExpenseTracker.dto.ExpenseSummaryDTO;
import com.learning.ExpenseTracker.exception.ExpenseNotFoundException;
import com.learning.ExpenseTracker.mapper.ExpenseMapper;
import com.learning.ExpenseTracker.entity.Expense;
import com.learning.ExpenseTracker.repository.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.learning.ExpenseTracker.entity.User;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import com.learning.ExpenseTracker.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.data.domain.Pageable;
@Service
public class ExpenseService {

    private static final Logger logger =
            LoggerFactory.getLogger(ExpenseService.class);

    private final ExpenseRepository repo;
    private final ExpenseMapper expenseMapper;
    private final UserRepository userRepository;

    public ExpenseService(
            ExpenseRepository repo,
            ExpenseMapper expenseMapper,
            UserRepository userRepository) {

        this.repo = repo;
        this.expenseMapper = expenseMapper;
        this.userRepository = userRepository;
    }
    // Get all expenses
    // Get all expenses
    public List<ExpenseDTO> getExpenses() {

        User currentUser = getCurrentUser();

        logger.info("Fetching expenses for user '{}'.",
                currentUser.getUsername());

        List<Expense> expenses = repo.findByUser(currentUser);

        logger.info("Successfully retrieved {} expenses for user '{}'.",
                expenses.size(),
                currentUser.getUsername());

        return expenseMapper.toDTOList(expenses);
    }

    // Get expense by id
    public ExpenseDTO getExpenseById(int id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        logger.info(
                "Fetching expense with ID {} for user '{}'.",
                id,
                username
        );

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found.")
                );

        Expense expense =
                repo.findByIdAndUser(id, user)
                        .orElseThrow(() ->
                                new ExpenseNotFoundException(
                                        "Expense not found."
                                )
                        );

        return expenseMapper.toDTO(expense);
    }

    // Add new expense
    public void addExpense(ExpenseDTO expenseDTO) {

        logger.info("Adding new expense: {}", expenseDTO.getTitle());

        User currentUser = getCurrentUser();

        Expense expense = expenseMapper.toEntity(expenseDTO);

        expense.setUser(currentUser);

        repo.save(expense);

        logger.info("Expense added successfully for user '{}'.",
                currentUser.getUsername());
    }

    // Update expense
    public void updateExpense(ExpenseDTO expenseDTO) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        logger.info(
                "Updating expense with ID {} for user '{}'.",
                expenseDTO.getId(),
                username
        );

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found.")
                );

        Expense existingExpense =
                repo.findByIdAndUser(
                        expenseDTO.getId(),
                        user
                ).orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found."
                        )
                );

        existingExpense.setTitle(expenseDTO.getTitle());
        existingExpense.setCategory(expenseDTO.getCategory().trim().toUpperCase());
        existingExpense.setAmount(expenseDTO.getAmount());
        existingExpense.setDate(expenseDTO.getDate());

        repo.save(existingExpense);

        logger.info(
                "Expense with ID {} updated successfully for user '{}'.",
                expenseDTO.getId(),
                username
        );
    }

    // Delete expense
    public void deleteExpense(int id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        logger.info(
                "Deleting expense with ID {} for user '{}'.",
                id,
                username
        );

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found.")
                );

        Expense expense =
                repo.findByIdAndUser(id, user)
                        .orElseThrow(() ->
                                new ExpenseNotFoundException(
                                        "Expense not found."
                                )
                        );

        repo.delete(expense);

        logger.info(
                "Expense with ID {} deleted successfully for user '{}'.",
                id,
                username
        );
    }

    // Find by category
    public List<ExpenseDTO> getExpensesByCategory(String category) {

        logger.info("Fetching expenses for category: {}", category);

        List<Expense> expenses = repo.findByCategory(category);

        logger.info("Found {} expenses.", expenses.size());

        return expenseMapper.toDTOList(expenses);
    }

    // Find by title
    public List<ExpenseDTO> getExpenseByTitle(String title) {

        logger.info("Fetching expenses with title: {}", title);

        List<Expense> expenses = repo.findByTitle(title);

        logger.info("Found {} expenses.", expenses.size());

        return expenseMapper.toDTOList(expenses);
    }

    // Amount greater than
    public List<ExpenseDTO> getExpenseByAmountGreaterThan(BigDecimal amount) {

        logger.info("Fetching expenses with amount greater than {}", amount);

        List<Expense> expenses = repo.findByAmountGreaterThan(amount);

        logger.info("Found {} expenses.", expenses.size());

        return expenseMapper.toDTOList(expenses);
    }

    // Amount less than
    public List<ExpenseDTO> getExpenseByAmountLessThan(BigDecimal amount) {

        logger.info("Fetching expenses with amount less than {}", amount);

        List<Expense> expenses = repo.findByAmountLessThan(amount);

        logger.info("Found {} expenses.", expenses.size());

        return expenseMapper.toDTOList(expenses);
    }

    // Date between
    public List<ExpenseDTO> getExpenseByDateBetween(LocalDate startDate,
                                                    LocalDate endDate) {

        logger.info("Fetching expenses between {} and {}",
                startDate, endDate);

        List<Expense> expenses =
                repo.findByDateBetween(startDate, endDate);

        logger.info("Found {} expenses.", expenses.size());

        return expenseMapper.toDTOList(expenses);
    }

    // Title containing
    public List<ExpenseDTO> getExpenseByTitleContaining(String title) {

        logger.info("Searching expenses containing title: {}", title);

        List<Expense> expenses =
                repo.findByTitleContaining(title);

        logger.info("Found {} expenses.", expenses.size());

        return expenseMapper.toDTOList(expenses);
    }

    // Ignore case
    public List<ExpenseDTO> getExpenseByCategoryIgnoreCase(String category) {

        logger.info("Fetching expenses for category (ignore case): {}", category);

        List<Expense> expenses =
                repo.findByCategoryIgnoreCase(category);

        logger.info("Found {} expenses.", expenses.size());

        return expenseMapper.toDTOList(expenses);
    }

    // Category and amount
    public List<ExpenseDTO> getExpenseByCategoryAndAmountGreaterThan(
            String category,
            BigDecimal amount) {

        logger.info(
                "Fetching expenses for category '{}' with amount greater than {}",
                category,
                amount);

        List<Expense> expenses =
                repo.findByCategoryAndAmountGreaterThan(category, amount);

        logger.info("Found {} expenses.", expenses.size());

        return expenseMapper.toDTOList(expenses);
    }

    // Category or title
    public List<ExpenseDTO> getExpenseByCategoryOrTitle(
            String category,
            String title) {

        logger.info(
                "Fetching expenses with category '{}' OR title '{}'",
                category,
                title);

        List<Expense> expenses =
                repo.findByCategoryOrTitle(category, title);

        logger.info("Found {} expenses.", expenses.size());

        return expenseMapper.toDTOList(expenses);
    }

    // Sort amount descending
    public List<ExpenseDTO> getExpenseOrderByAmountDesc() {

        logger.info("Fetching expenses sorted by amount descending.");

        List<Expense> expenses =
                repo.findByOrderByAmountDesc();

        logger.info("Found {} expenses.", expenses.size());

        return expenseMapper.toDTOList(expenses);
    }

    // Sort date ascending
    public List<ExpenseDTO> getExpensesOrderByDateAsc() {

        logger.info("Fetching expenses sorted by date ascending.");

        List<Expense> expenses =
                repo.findByOrderByDateAsc();

        logger.info("Found {} expenses.", expenses.size());

        return expenseMapper.toDTOList(expenses);
    }

    // Pagination and Sorting
// Pagination and Sorting
    // Pagination and Sorting
    public Page<ExpenseDTO> getExpenses(int page,
                                        int size,
                                        String sortBy,
                                        String direction) {

        logger.info(
                "Fetching expenses. Page={}, Size={}, SortBy={}, Direction={}",
                page,
                size,
                sortBy,
                direction);

        User currentUser = getCurrentUser();

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ExpenseDTO> expensePage =
                repo.findByUser(currentUser, pageable)
                        .map(expenseMapper::toDTO);

        logger.info(
                "Successfully retrieved {} expenses for user '{}'.",
                expensePage.getNumberOfElements(),
                currentUser.getUsername());

        return expensePage;
    }
    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found."));
    }
    public ExpenseSummaryDTO getExpenseSummary() {

        logger.info("Calculating expense summary");

        User currentUser = getCurrentUser();

        /*
         * Fetch ALL expenses for the current user.
         *
         * Pageable.unpaged() is used because dashboard analytics
         * must not depend on the current pagination page.
         */
        Page<ExpenseDTO> page =
                repo.findByUser(currentUser, Pageable.unpaged())
                        .map(expenseMapper::toDTO);

        var expenses = page.getContent();

        BigDecimal totalSpending = expenses.stream()
                .map(ExpenseDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDate today = LocalDate.now();

        BigDecimal thisMonth = expenses.stream()
                .filter(expense ->
                        expense.getDate() != null
                                && expense.getDate().getYear() == today.getYear()
                                && expense.getDate().getMonth() == today.getMonth())
                .map(ExpenseDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long transactionCount = expenses.size();

        /*
         * Category breakdown
         *
         * FOOD, food and Food are treated as FOOD.
         */
        Map<String, BigDecimal> categoryBreakdown = new LinkedHashMap<>();

        for (ExpenseDTO expense : expenses) {

            String category = expense.getCategory() == null
                    ? "OTHER"
                    : expense.getCategory().trim().toUpperCase();

            categoryBreakdown.merge(
                    category,
                    expense.getAmount(),
                    BigDecimal::add
            );
        }

        /*
         * Monthly spending for the current year.
         */
        Map<String, BigDecimal> monthlySpending = new LinkedHashMap<>();

        for (Month month : Month.values()) {
            monthlySpending.put(
                    month.name().substring(0, 1)
                            + month.name().substring(1).toLowerCase(),
                    BigDecimal.ZERO
            );
        }

        for (ExpenseDTO expense : expenses) {

            if (expense.getDate() == null) {
                continue;
            }

            if (expense.getDate().getYear() != today.getYear()) {
                continue;
            }

            String monthName =
                    expense.getDate()
                            .getMonth()
                            .name()
                            .substring(0, 1)
                            + expense.getDate()
                            .getMonth()
                            .name()
                            .substring(1)
                            .toLowerCase();

            monthlySpending.merge(
                    monthName,
                    expense.getAmount(),
                    BigDecimal::add
            );
        }

        logger.info(
                "Expense summary calculated for user '{}'. Total={}, Transactions={}",
                currentUser.getUsername(),
                totalSpending,
                transactionCount
        );

        return new ExpenseSummaryDTO(
                totalSpending,
                thisMonth,
                transactionCount,
                categoryBreakdown,
                monthlySpending
        );
    }
}