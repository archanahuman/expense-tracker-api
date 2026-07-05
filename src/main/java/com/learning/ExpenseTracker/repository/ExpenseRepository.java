package com.learning.ExpenseTracker.repository;

import com.learning.ExpenseTracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

     List<Expense> findByCategory(String category);

     List<Expense> findByTitle(String title);

     List<Expense> findByAmountGreaterThan(BigDecimal amount);

     List<Expense> findByAmountLessThan(BigDecimal amount);

     List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

     List<Expense> findByTitleContaining(String title);

     List<Expense> findByCategoryIgnoreCase(String category);

     List<Expense> findByCategoryAndAmountGreaterThan(String category, BigDecimal amount);

     List<Expense> findByCategoryOrTitle(String category, String title);

     List<Expense> findByOrderByAmountDesc();

     List<Expense> findByOrderByDateAsc();
}