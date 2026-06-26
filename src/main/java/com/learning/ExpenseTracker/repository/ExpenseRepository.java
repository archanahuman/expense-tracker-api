package com.learning.ExpenseTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.learning.ExpenseTracker.model.Expense;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;



@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Integer> {

     List<Expense> findByCategory(String category);
     List<Expense> findByTitle(String Title);
     List<Expense> findByAmountGreaterThan(BigDecimal amount);

     List<Expense> findByDateBetween(LocalDate start,
                                     LocalDate end);

     List<Expense> findByTitleContaining(String title);

     List<Expense> findByAmountLessThan(BigDecimal amount);

     List<Expense> findByCategoryIgnoreCase(String category);

     List<Expense> findByCategoryAndAmountGreaterThan(
             String category,
             BigDecimal amount);

     List<Expense> findByCategoryOrTitle(
             String category,
             String title);


     List<Expense> findByOrderByAmountDesc();

     List<Expense> findByOrderByDateAsc();


}
