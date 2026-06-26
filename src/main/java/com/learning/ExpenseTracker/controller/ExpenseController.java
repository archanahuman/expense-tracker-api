package com.learning.ExpenseTracker.controller;

import com.learning.ExpenseTracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.learning.ExpenseTracker.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@RestController
public class ExpenseController {
    @Autowired
    private ExpenseService service;

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getExpenses(){
        return new ResponseEntity<>(service.getExpenses(), HttpStatus.OK);
    }

    @GetMapping("/expenses/{id}")
    public  ResponseEntity<Expense> getExpenseById(@PathVariable int id){
        return new ResponseEntity<>(service.getExpenseById(id),HttpStatus.OK);
    }

    @PostMapping("/expenses")
    public ResponseEntity<String> addExpense(@RequestBody @Valid Expense expense){
        service.addExpense(expense);
        return new ResponseEntity<>("Expense added",HttpStatus.CREATED);
    }

    @PutMapping("/expenses")
    public ResponseEntity<String> updateExpense(@RequestBody Expense expense){
        service.updateExpense(expense);
        return new ResponseEntity<>("Expense Updated",HttpStatus.OK);
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable int id){
        service.deleteExpense(id);
        return new ResponseEntity<>("Expense Deleted",HttpStatus.OK);
    }

    @GetMapping("/expenses/category/{category}")
    public ResponseEntity<List<Expense>> getExpenseByCategory(@PathVariable String category){
        return new ResponseEntity<>(service.getExpensesByCategory(category),HttpStatus.OK);
    }

    @GetMapping("/expenses/title/{title}")
    public ResponseEntity<List<Expense>> getExpenseByTitle(@PathVariable String title){
        return new ResponseEntity<>(service.getExpenseByTitle(title),HttpStatus.OK);
    }

    @GetMapping("/expenses/amountGreaterThan/{amount}")
    public ResponseEntity<List<Expense>> getExpenseByAmountGreaterThan(@PathVariable BigDecimal amount){
        return new ResponseEntity<>(service.getExpenseByAmountGreaterThan(amount),HttpStatus.OK);
    }

    @GetMapping("/expenses/dateBetween/{startDate}/{endDate}")
    public ResponseEntity<List<Expense>> getExpenseDateBetween(
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate) {

        return new ResponseEntity<>(
                service.getExpenseByDateBetween(startDate, endDate),
                HttpStatus.OK);
    }

    @GetMapping("/expenses/titleContaining/{title}")
    public ResponseEntity<List<Expense>> getExpenseByTitleContaining(@PathVariable String title){
        return new ResponseEntity<>(service.getExpenseByTitleContaining(title),HttpStatus.OK);
    }

    @GetMapping("/expenses/amountLessThan/{amount}")
    public ResponseEntity<List<Expense>> getExpenseByAmountLessThan(
            @PathVariable BigDecimal amount){

        return new ResponseEntity<>(
                service.getExpenseByAmountLessThan(amount),
                HttpStatus.OK);
    }

    @GetMapping("/expenses/categoryIgnoreCase/{category}")
    public ResponseEntity<List<Expense>> getExpenseByCategoryIgnoreCase(
            @PathVariable String category){

        return new ResponseEntity<>(
                service.getExpenseByCategoryIgnoreCase(category),
                HttpStatus.OK);
    }
    @GetMapping("/expenses/category/{category}/amountGreaterThan/{amount}")
    public ResponseEntity<List<Expense>> getExpenseByCategoryAndAmountGreaterThan(
            @PathVariable String category,@PathVariable BigDecimal amount){

        return new ResponseEntity<>(
                service.getExpenseByCategoryAndAmountGreaterThan(category,amount),
                HttpStatus.OK);
    }


    @GetMapping("/expenses/category/{category}/title/{title}")
    public ResponseEntity<List<Expense>> getExpenseByCategoryOrTitle(
            @PathVariable String category,@PathVariable String title){

        return new ResponseEntity<>(
                service.getExpenseByCategoryOrTitle(category,title),
                HttpStatus.OK);
    }

    @GetMapping("/expenses/amountByDesc")
    public ResponseEntity<List<Expense>> getExpenseByAmountDesc(){
        return new ResponseEntity<>(
                service.getExpenseOrderByAmountDesc(),
                HttpStatus.OK);
    }

    @GetMapping("/expenses/sort/dateAsc")
    public ResponseEntity<List<Expense>> getExpensesOrderByDateAsc(){

        return new ResponseEntity<>(
                service.getExpensesOrderByDateAsc(),
                HttpStatus.OK);
    }
}

