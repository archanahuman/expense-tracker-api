package com.learning.ExpenseTracker.service;
import com.learning.ExpenseTracker.mapper.ExpenseMapper;
import com.learning.ExpenseTracker.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.learning.ExpenseTracker.dto.ExpenseDTO;
import com.learning.ExpenseTracker.model.Expense;
import com.learning.ExpenseTracker.exception.ExpenseNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

    @ExtendWith(MockitoExtension.class)
    class ExpenseServiceTest {

        @Mock
        private ExpenseRepository repo;

        @Mock
        private ExpenseMapper expenseMapper;

        @InjectMocks
        private ExpenseService service;
        @Test
        void shouldReturnExpenseWhenIdExists() {

            // Arrange
            Expense expense = new Expense();
            expense.setId(1);
            expense.setTitle("Burger");
            expense.setAmount(new BigDecimal("250"));
            expense.setCategory("Food");
            expense.setDate(LocalDate.now());

            ExpenseDTO expenseDTO = new ExpenseDTO();
            expenseDTO.setId(1);
            expenseDTO.setTitle("Burger");
            expenseDTO.setAmount(new BigDecimal("250"));
            expenseDTO.setCategory("Food");
            expenseDTO.setDate(expense.getDate());

            when(repo.findById(1)).thenReturn(Optional.of(expense));
            when(expenseMapper.toDTO(expense)).thenReturn(expenseDTO);

            // Act
            ExpenseDTO result = service.getExpenseById(1);

            // Assert
            assertEquals(1, result.getId());
            assertEquals("Burger", result.getTitle());
            assertEquals(new BigDecimal("250"), result.getAmount());
            assertEquals("Food", result.getCategory());

            verify(repo).findById(1);
            verify(expenseMapper).toDTO(expense);
        }
        @Test
        void shouldThrowExceptionWhenExpenseNotFound() {

            // Arrange
            when(repo.findById(100))
                    .thenReturn(Optional.empty());

            // Act & Assert
            ExpenseNotFoundException exception = assertThrows(
                    ExpenseNotFoundException.class,
                    () -> service.getExpenseById(100)
            );

            assertEquals("Expense with id 100 not found", exception.getMessage());

            verify(repo).findById(100);
        }
        @Test
        void shouldAddExpenseSuccessfully() {

            // Arrange
            ExpenseDTO expenseDTO = new ExpenseDTO();
            expenseDTO.setTitle("Pizza");
            expenseDTO.setAmount(new BigDecimal("500"));
            expenseDTO.setCategory("Food");
            expenseDTO.setDate(LocalDate.now());

            Expense expense = new Expense();
            expense.setTitle("Pizza");
            expense.setAmount(new BigDecimal("500"));
            expense.setCategory("Food");
            expense.setDate(expenseDTO.getDate());

            when(expenseMapper.toEntity(expenseDTO)).thenReturn(expense);

            // Act
            service.addExpense(expenseDTO);

            // Assert
            verify(expenseMapper).toEntity(expenseDTO);
            verify(repo).save(expense);
        }
        @Test
        void shouldUpdateExpenseSuccessfully() {

            // Arrange
            ExpenseDTO expenseDTO = new ExpenseDTO();
            expenseDTO.setId(1);
            expenseDTO.setTitle("Updated Pizza");
            expenseDTO.setAmount(new BigDecimal("600"));
            expenseDTO.setCategory("Food");
            expenseDTO.setDate(LocalDate.now());

            Expense expense = new Expense();
            expense.setId(1);
            expense.setTitle("Updated Pizza");
            expense.setAmount(new BigDecimal("600"));
            expense.setCategory("Food");
            expense.setDate(expenseDTO.getDate());

            when(expenseMapper.toEntity(expenseDTO)).thenReturn(expense);

            // Act
            service.updateExpense(expenseDTO);

            // Assert
            verify(expenseMapper).toEntity(expenseDTO);
            verify(repo).save(expense);
        }
        @Test
        void shouldDeleteExpenseSuccessfully() {

            // Act
            service.deleteExpense(1);

            // Assert
            verify(repo).deleteById(1);
        }

    }
