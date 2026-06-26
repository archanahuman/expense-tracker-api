package com.learning.ExpenseTracker.service;

import com.learning.ExpenseTracker.exception.ExpenseNotFoundException;
import com.learning.ExpenseTracker.model.Expense;
import com.learning.ExpenseTracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repo;

    public List<Expense> getExpenses() {
        return repo.findAll();
    }

    public Expense getExpenseById(int id) {
        return repo.findById(id).orElseThrow(()->new ExpenseNotFoundException("Expense with id "+id+"not found"));
    }

    public void addExpense(Expense expense) {
        repo.save(expense);
    }

    public void updateExpense(Expense expense) {
        repo.save(expense);
    }

    public void deleteExpense(int id) {
        repo.deleteById(id);
    }

    public List<Expense> getExpensesByCategory(String category){
        return repo.findByCategory(category);
    }
    public List<Expense> getExpenseByTitle(String title){
        return repo.findByTitle(title);
    }


    public List<Expense> getExpenseByAmountGreaterThan(BigDecimal amount){
        return repo.findByAmountGreaterThan(amount);
    }


    public List<Expense> getExpenseByDateBetween(LocalDate startDate, LocalDate endDate){
        return repo.findByDateBetween(startDate,endDate);
    }

    public List<Expense> getExpenseByTitleContaining(String title){
        return repo.findByTitleContaining(title);
    }

    public List<Expense> getExpenseByAmountLessThan(BigDecimal amount){
        return repo.findByAmountLessThan(amount);
    }
    public List<Expense> getExpenseByCategoryIgnoreCase(String category){
        return repo.findByCategoryIgnoreCase(category);
    }

    public List<Expense> getExpenseByCategoryAndAmountGreaterThan(String category,BigDecimal amount){
        return repo.findByCategoryAndAmountGreaterThan(category,amount);
    }

    public List<Expense> getExpenseByCategoryOrTitle(String category,String title){
        return  repo.findByCategoryOrTitle(category,title);
    }
    public List<Expense> getExpenseOrderByAmountDesc(){
        return repo.findByOrderByAmountDesc();
    }
    public List<Expense> getExpensesOrderByDateAsc(){
        return repo.findByOrderByDateAsc();
    }



}