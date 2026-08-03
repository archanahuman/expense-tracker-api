package com.learning.ExpenseTracker.service;

import com.learning.ExpenseTracker.dto.ExpenseDTO;
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

        logger.info("Fetching expense with ID: {}", id);

        User currentUser = getCurrentUser();

        Expense expense = repo.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> {
                    logger.error("Expense with ID {} not found for current user.", id);

                    return new ExpenseNotFoundException(
                            "Expense not found or does not belong to the current user.");
                });

        logger.info("Expense retrieved successfully.");

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

        logger.info("Updating expense with ID: {}", expenseDTO.getId());

        User currentUser = getCurrentUser();

        Expense existingExpense = repo.findByIdAndUser(
                expenseDTO.getId(),
                currentUser
        ).orElseThrow(() ->
                new ExpenseNotFoundException(
                        "Expense not found or does not belong to the current user."));

        existingExpense.setTitle(expenseDTO.getTitle());
        existingExpense.setCategory(expenseDTO.getCategory());
        existingExpense.setAmount(expenseDTO.getAmount());
        existingExpense.setDate(expenseDTO.getDate());

        repo.save(existingExpense);

        logger.info("Expense updated successfully.");
    }

    // Delete expense
    public void deleteExpense(int id) {

        logger.info("Deleting expense with ID: {}", id);

        User currentUser = getCurrentUser();

        Expense expense = repo.findByIdAndUser(id, currentUser)
                .orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found or does not belong to the current user."));

        repo.delete(expense);

        logger.info("Expense deleted successfully.");
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
}