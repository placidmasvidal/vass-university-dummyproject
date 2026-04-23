package com.vasscompany.dummyproject.core.services.lab2_2RefactoringMultitarea;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Input request for order processing.
 */
public class OrderProcessingRequest {

    private String orderId;
    private String customerType;
    private String channel;
    private String couponCode;
    private List<OrderLine> lines = new ArrayList<>();

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(final String customerType) {
        this.customerType = customerType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(final String channel) {
        this.channel = channel;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(final String couponCode) {
        this.couponCode = couponCode;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public void setLines(final List<OrderLine> lines) {
        this.lines = lines;
    }

    /**
     * Order line.
     */
    public static class OrderLine {

        private String sku;
        private BigDecimal unitPrice;
        private int quantity;

        public OrderLine() {
        }

        public OrderLine(final String sku, final BigDecimal unitPrice, final int quantity) {
            this.sku = sku;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(final String sku) {
            this.sku = sku;
        }

        public BigDecimal getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(final BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(final int quantity) {
            this.quantity = quantity;
        }
    }
}
