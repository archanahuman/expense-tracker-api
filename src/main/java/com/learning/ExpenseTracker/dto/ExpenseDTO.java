package com.learning.ExpenseTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseDTO {

    private int id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    public ExpenseDTO() {
    }

    public ExpenseDTO(int id, String title, String category,
                      BigDecimal amount, LocalDate date) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}