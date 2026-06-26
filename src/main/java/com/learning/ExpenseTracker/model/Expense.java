package com.learning.ExpenseTracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Expense {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message ="Category cannot be blank")
    private String category;
    @Positive(message = "Amount must be greater than 0")
    private BigDecimal amount;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }




    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public Expense(){

    }
    public Expense(int id, String title, BigDecimal amount, String category, LocalDate date){
        this.id=id;
        this.title=title;
        this.amount=amount;
        this.category=category;
        this.date=date;
    }

}
