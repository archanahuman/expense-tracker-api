package com.learning.ExpenseTracker.controller;

import com.learning.ExpenseTracker.dto.ExpenseDTO;
import com.learning.ExpenseTracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    // Get all expenses with pagination and sorting
    @GetMapping
    public ResponseEntity<Page<ExpenseDTO>> getExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return new ResponseEntity<>(
                service.getExpenses(page, size, sortBy, direction),
                HttpStatus.OK);
    }

    // Get expense by ID
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable int id) {

        return new ResponseEntity<>(
                service.getExpenseById(id),
                HttpStatus.OK);
    }

    // Add new expense
    @PostMapping
    public ResponseEntity<String> addExpense(
            @RequestBody @Valid ExpenseDTO expenseDTO) {

        service.addExpense(expenseDTO);

        return new ResponseEntity<>(
                "Expense added successfully",
                HttpStatus.CREATED);
    }

    // Update expense
    @PutMapping
    public ResponseEntity<String> updateExpense(
            @RequestBody @Valid ExpenseDTO expenseDTO) {

        service.updateExpense(expenseDTO);

        return new ResponseEntity<>(
                "Expense updated successfully",
                HttpStatus.OK);
    }

    // Delete expense
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable int id) {

        service.deleteExpense(id);

        return new ResponseEntity<>(
                "Expense deleted successfully",
                HttpStatus.OK);
    }

    // Find by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByCategory(
            @PathVariable String category) {

        return new ResponseEntity<>(
                service.getExpensesByCategory(category),
                HttpStatus.OK);
    }

    // Find by title
    @GetMapping("/title/{title}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByTitle(
            @PathVariable String title) {

        return new ResponseEntity<>(
                service.getExpenseByTitle(title),
                HttpStatus.OK);
    }

    // Amount greater than
    @GetMapping("/amountGreaterThan/{amount}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByAmountGreaterThan(
            @PathVariable BigDecimal amount) {

        return new ResponseEntity<>(
                service.getExpenseByAmountGreaterThan(amount),
                HttpStatus.OK);
    }

    // Amount less than
    @GetMapping("/amountLessThan/{amount}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByAmountLessThan(
            @PathVariable BigDecimal amount) {

        return new ResponseEntity<>(
                service.getExpenseByAmountLessThan(amount),
                HttpStatus.OK);
    }

    // Date between
    @GetMapping("/dateBetween/{startDate}/{endDate}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByDateBetween(
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate) {

        return new ResponseEntity<>(
                service.getExpenseByDateBetween(startDate, endDate),
                HttpStatus.OK);
    }

    // Title containing
    @GetMapping("/titleContaining/{title}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByTitleContaining(
            @PathVariable String title) {

        return new ResponseEntity<>(
                service.getExpenseByTitleContaining(title),
                HttpStatus.OK);
    }

    // Category ignore case
    @GetMapping("/categoryIgnoreCase/{category}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByCategoryIgnoreCase(
            @PathVariable String category) {

        return new ResponseEntity<>(
                service.getExpenseByCategoryIgnoreCase(category),
                HttpStatus.OK);
    }

    // Category and amount greater than
    @GetMapping("/category/{category}/amountGreaterThan/{amount}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByCategoryAndAmountGreaterThan(
            @PathVariable String category,
            @PathVariable BigDecimal amount) {

        return new ResponseEntity<>(
                service.getExpenseByCategoryAndAmountGreaterThan(category, amount),
                HttpStatus.OK);
    }

    // Category or title
    @GetMapping("/category/{category}/title/{title}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByCategoryOrTitle(
            @PathVariable String category,
            @PathVariable String title) {

        return new ResponseEntity<>(
                service.getExpenseByCategoryOrTitle(category, title),
                HttpStatus.OK);
    }

    // Sort by amount descending
    @GetMapping("/sort/amountDesc")
    public ResponseEntity<List<ExpenseDTO>> getExpenseOrderByAmountDesc() {

        return new ResponseEntity<>(
                service.getExpenseOrderByAmountDesc(),
                HttpStatus.OK);
    }

    // Sort by date ascending
    @GetMapping("/sort/dateAsc")
    public ResponseEntity<List<ExpenseDTO>> getExpensesOrderByDateAsc() {

        return new ResponseEntity<>(
                service.getExpensesOrderByDateAsc(),
                HttpStatus.OK);
    }
}