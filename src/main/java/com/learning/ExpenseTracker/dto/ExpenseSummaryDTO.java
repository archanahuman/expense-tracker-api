package com.learning.ExpenseTracker.dto;

import java.math.BigDecimal;
import java.util.Map;

public class ExpenseSummaryDTO {

    private BigDecimal totalSpending;
    private BigDecimal thisMonth;
    private long transactionCount;
    private Map<String, BigDecimal> categoryBreakdown;
    private Map<String, BigDecimal> monthlySpending;

    public ExpenseSummaryDTO(
            BigDecimal totalSpending,
            BigDecimal thisMonth,
            long transactionCount,
            Map<String, BigDecimal> categoryBreakdown,
            Map<String, BigDecimal> monthlySpending) {

        this.totalSpending = totalSpending;
        this.thisMonth = thisMonth;
        this.transactionCount = transactionCount;
        this.categoryBreakdown = categoryBreakdown;
        this.monthlySpending = monthlySpending;
    }

    public BigDecimal getTotalSpending() {
        return totalSpending;
    }

    public BigDecimal getThisMonth() {
        return thisMonth;
    }

    public long getTransactionCount() {
        return transactionCount;
    }

    public Map<String, BigDecimal> getCategoryBreakdown() {
        return categoryBreakdown;
    }

    public Map<String, BigDecimal> getMonthlySpending() {
        return monthlySpending;
    }
}