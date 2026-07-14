package com.learning.ExpenseTracker.controller;

import com.learning.ExpenseTracker.dto.ExpenseDTO;
import com.learning.ExpenseTracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get All Expenses",
            description = "Returns a paginated and sorted list of all expenses."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully")
    })
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

    @Operation(
            summary = "Get Expense By ID",
            description = "Returns an expense based on its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expense found successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable int id) {

        return new ResponseEntity<>(
                service.getExpenseById(id),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Add Expense",
            description = "Creates a new expense in the database."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Expense created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<String> addExpense(
            @RequestBody @Valid ExpenseDTO expenseDTO) {

        service.addExpense(expenseDTO);

        return new ResponseEntity<>(
                "Expense added successfully",
                HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update Expense",
            description = "Updates an existing expense."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expense updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    @PutMapping
    public ResponseEntity<String> updateExpense(
            @RequestBody @Valid ExpenseDTO expenseDTO) {

        service.updateExpense(expenseDTO);

        return new ResponseEntity<>(
                "Expense updated successfully",
                HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Expense",
            description = "Deletes an expense using its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expense deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable int id) {

        service.deleteExpense(id);

        return new ResponseEntity<>(
                "Expense deleted successfully",
                HttpStatus.OK);
    }

    @Operation(
            summary = "Find Expenses By Category",
            description = "Returns all expenses belonging to the given category."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully")
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByCategory(
            @PathVariable String category) {

        return new ResponseEntity<>(
                service.getExpensesByCategory(category),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Find Expenses By Title",
            description = "Returns expenses having the given title."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully")
    })
    @GetMapping("/title/{title}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByTitle(
            @PathVariable String title) {

        return new ResponseEntity<>(
                service.getExpenseByTitle(title),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Find Expenses By Amount Greater Than",
            description = "Returns expenses whose amount is greater than the specified value."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully")
    })
    @GetMapping("/amountGreaterThan/{amount}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByAmountGreaterThan(
            @PathVariable BigDecimal amount) {

        return new ResponseEntity<>(
                service.getExpenseByAmountGreaterThan(amount),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Find Expenses By Amount Less Than",
            description = "Returns expenses whose amount is less than the specified value."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully")
    })
    @GetMapping("/amountLessThan/{amount}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByAmountLessThan(
            @PathVariable BigDecimal amount) {

        return new ResponseEntity<>(
                service.getExpenseByAmountLessThan(amount),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Find Expenses Between Dates",
            description = "Returns expenses whose date lies between the given start and end dates."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully")
    })
    @GetMapping("/dateBetween/{startDate}/{endDate}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByDateBetween(
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate) {

        return new ResponseEntity<>(
                service.getExpenseByDateBetween(startDate, endDate),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Search Expense By Title",
            description = "Returns expenses whose title contains the given keyword."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully")
    })
    @GetMapping("/titleContaining/{title}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByTitleContaining(
            @PathVariable String title) {

        return new ResponseEntity<>(
                service.getExpenseByTitleContaining(title),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Find Expenses By Category (Ignore Case)",
            description = "Returns expenses by category without considering letter case."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully")
    })
    @GetMapping("/categoryIgnoreCase/{category}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByCategoryIgnoreCase(
            @PathVariable String category) {

        return new ResponseEntity<>(
                service.getExpenseByCategoryIgnoreCase(category),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Find Expenses By Category And Minimum Amount",
            description = "Returns expenses matching the category and having an amount greater than the specified value."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully")
    })
    @GetMapping("/category/{category}/amountGreaterThan/{amount}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByCategoryAndAmountGreaterThan(
            @PathVariable String category,
            @PathVariable BigDecimal amount) {

        return new ResponseEntity<>(
                service.getExpenseByCategoryAndAmountGreaterThan(category, amount),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Find Expenses By Category Or Title",
            description = "Returns expenses matching either the given category or title."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully")
    })
    @GetMapping("/category/{category}/title/{title}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByCategoryOrTitle(
            @PathVariable String category,
            @PathVariable String title) {

        return new ResponseEntity<>(
                service.getExpenseByCategoryOrTitle(category, title),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Sort Expenses By Amount (Descending)",
            description = "Returns all expenses sorted by amount in descending order."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully")
    })
    @GetMapping("/sort/amountDesc")
    public ResponseEntity<List<ExpenseDTO>> getExpenseOrderByAmountDesc() {

        return new ResponseEntity<>(
                service.getExpenseOrderByAmountDesc(),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Sort Expenses By Date (Ascending)",
            description = "Returns all expenses sorted by date in ascending order."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully")
    })
    @GetMapping("/sort/dateAsc")
    public ResponseEntity<List<ExpenseDTO>> getExpensesOrderByDateAsc() {

        return new ResponseEntity<>(
                service.getExpensesOrderByDateAsc(),
                HttpStatus.OK);
    }
}