package com.vasscompany.dummyproject.core.services.lab2_2RefactoringMultitarea;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Result returned after order processing.
 */
public class OrderProcessingResult {

    private String orderId;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal finalAmount;
    private boolean processed;
    private String message;
    private List<String> appliedRules = new ArrayList<>();

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(final BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(final BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(final BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(final boolean processed) {
        this.processed = processed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public List<String> getAppliedRules() {
        return appliedRules;
    }

    public void setAppliedRules(final List<String> appliedRules) {
        this.appliedRules = appliedRules;
    }
}
