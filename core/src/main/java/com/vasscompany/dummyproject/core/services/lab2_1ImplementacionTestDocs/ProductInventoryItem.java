package com.vasscompany.dummyproject.core.services.lab2_1ImplementacionTestDocs;

import java.math.BigDecimal;

public class ProductInventoryItem {

    private final String sku;
    private final String name;
    private final BigDecimal unitPrice;
    private int stock;
    public ProductInventoryItem(String sku, String name, BigDecimal unitPrice, int stock) {
        this.sku = sku;
        this.name = name;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void updateStock(int quantityChange) {
        int newStock = this.stock + quantityChange;
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.stock = newStock;
    }

    @Override
    public int hashCode() {
        return sku.hashCode();
    }

    @Override
    public String toString() {
        return "ProductInventoryItem{" +
                "sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", stock=" + stock +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInventoryItem that = (ProductInventoryItem) o;
        return sku.equals(that.sku);
    }
}
