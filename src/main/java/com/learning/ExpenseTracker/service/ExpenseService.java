package com.learning.ExpenseTracker.service;

import com.learning.ExpenseTracker.dto.ExpenseDTO;
import com.learning.ExpenseTracker.exception.ExpenseNotFoundException;
import com.learning.ExpenseTracker.mapper.ExpenseMapper;
import com.learning.ExpenseTracker.model.Expense;
import com.learning.ExpenseTracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repo;

    @Autowired
    private ExpenseMapper expenseMapper;

    // Get all expenses
    public List<ExpenseDTO> getExpenses() {

        List<Expense> expenses = repo.findAll();

        return expenseMapper.toDTOList(expenses);
    }

    // Get expense by id
    public ExpenseDTO getExpenseById(int id) {

        Expense expense = repo.findById(id)
                .orElseThrow(() ->
                        new ExpenseNotFoundException("Expense with id " + id + " not found"));

        return expenseMapper.toDTO(expense);
    }

    // Add new expense
    public void addExpense(ExpenseDTO expenseDTO) {

        Expense expense = expenseMapper.toEntity(expenseDTO);

        repo.save(expense);
    }

    // Update expense
    public void updateExpense(ExpenseDTO expenseDTO) {

        Expense expense = expenseMapper.toEntity(expenseDTO);

        repo.save(expense);
    }

    // Delete expense
    public void deleteExpense(int id) {

        repo.deleteById(id);
    }

    // Find by category
    public List<ExpenseDTO> getExpensesByCategory(String category) {

        return expenseMapper.toDTOList(
                repo.findByCategory(category)
        );
    }

    // Find by title
    public List<ExpenseDTO> getExpenseByTitle(String title) {

        return expenseMapper.toDTOList(
                repo.findByTitle(title)
        );
    }

    // Amount greater than
    public List<ExpenseDTO> getExpenseByAmountGreaterThan(BigDecimal amount) {

        return expenseMapper.toDTOList(
                repo.findByAmountGreaterThan(amount)
        );
    }

    // Amount less than
    public List<ExpenseDTO> getExpenseByAmountLessThan(BigDecimal amount) {

        return expenseMapper.toDTOList(
                repo.findByAmountLessThan(amount)
        );
    }

    // Date between
    public List<ExpenseDTO> getExpenseByDateBetween(LocalDate startDate,
                                                    LocalDate endDate) {

        return expenseMapper.toDTOList(
                repo.findByDateBetween(startDate, endDate)
        );
    }

    // Title containing
    public List<ExpenseDTO> getExpenseByTitleContaining(String title) {

        return expenseMapper.toDTOList(
                repo.findByTitleContaining(title)
        );
    }

    // Ignore case
    public List<ExpenseDTO> getExpenseByCategoryIgnoreCase(String category) {

        return expenseMapper.toDTOList(
                repo.findByCategoryIgnoreCase(category)
        );
    }

    // Category and amount
    public List<ExpenseDTO> getExpenseByCategoryAndAmountGreaterThan(
            String category,
            BigDecimal amount) {

        return expenseMapper.toDTOList(
                repo.findByCategoryAndAmountGreaterThan(category, amount)
        );
    }

    // Category or title
    public List<ExpenseDTO> getExpenseByCategoryOrTitle(
            String category,
            String title) {

        return expenseMapper.toDTOList(
                repo.findByCategoryOrTitle(category, title)
        );
    }

    // Sort amount descending
    public List<ExpenseDTO> getExpenseOrderByAmountDesc() {

        return expenseMapper.toDTOList(
                repo.findByOrderByAmountDesc()
        );
    }

    // Sort date ascending
    public List<ExpenseDTO> getExpensesOrderByDateAsc() {

        return expenseMapper.toDTOList(
                repo.findByOrderByDateAsc()
        );
    }

    // Pagination and Sorting
    public Page<ExpenseDTO> getExpenses(int page,
                                        int size,
                                        String sortBy,
                                        String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repo.findAll(pageable)
                .map(expenseMapper::toDTO);
    }

}