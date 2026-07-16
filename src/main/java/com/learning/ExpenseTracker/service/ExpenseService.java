package com.learning.ExpenseTracker.service;

import com.learning.ExpenseTracker.dto.ExpenseDTO;
import com.learning.ExpenseTracker.exception.ExpenseNotFoundException;
import com.learning.ExpenseTracker.mapper.ExpenseMapper;
import com.learning.ExpenseTracker.model.Expense;
import com.learning.ExpenseTracker.repository.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    private static final Logger logger =
            LoggerFactory.getLogger(ExpenseService.class);

    @Autowired
    private ExpenseRepository repo;

    @Autowired
    private ExpenseMapper expenseMapper;

    // Get all expenses
    public List<ExpenseDTO> getExpenses() {

        logger.info("Fetching all expenses.");

        List<Expense> expenses = repo.findAll();

        logger.info("Successfully retrieved {} expenses.", expenses.size());

        return expenseMapper.toDTOList(expenses);
    }

    // Get expense by id
    public ExpenseDTO getExpenseById(int id) {

        logger.info("Fetching expense with ID: {}", id);

        Expense expense = repo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Expense with ID {} not found.", id);
                    return new ExpenseNotFoundException(
                            "Expense with id " + id + " not found");
                });

        logger.info("Expense with ID {} retrieved successfully.", id);

        return expenseMapper.toDTO(expense);
    }

    // Add new expense
    public void addExpense(ExpenseDTO expenseDTO) {

        logger.info("Adding new expense: {}", expenseDTO.getTitle());

        Expense expense = expenseMapper.toEntity(expenseDTO);

        repo.save(expense);

        logger.info("Expense added successfully.");
    }

    // Update expense
    public void updateExpense(ExpenseDTO expenseDTO) {

        logger.info("Updating expense with ID: {}", expenseDTO.getId());

        Expense expense = expenseMapper.toEntity(expenseDTO);

        repo.save(expense);

        logger.info("Expense updated successfully.");
    }

    // Delete expense
    public void deleteExpense(int id) {

        logger.info("Deleting expense with ID: {}", id);

        repo.deleteById(id);

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

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ExpenseDTO> expensePage =
                repo.findAll(pageable)
                        .map(expenseMapper::toDTO);

        logger.info(
                "Successfully retrieved {} expenses on page {}.",
                expensePage.getNumberOfElements(),
                page);

        return expensePage;
    }

}