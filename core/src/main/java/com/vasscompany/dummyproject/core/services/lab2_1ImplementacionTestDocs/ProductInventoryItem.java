package com.vasscompany.dummyproject.core.services.lab2_1ImplementacionTestDocs;

import java.math.BigDecimal;

public class ProductInventoryItem {

    private String sku;
    private String name;
    private BigDecimal unitPrice;
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

// Completa este modelo de dominio para que sea usable por el servicio.
// Manténlo simple, sin Lombok.
// Añade lo necesario para trabajar bien con inventario en memoria:
// equals/hashCode por SKU, toString y una forma controlada de actualizar stock
// o de crear una copia con stock distinto.
// No metas lógica de negocio compleja aquí.

    public void updateStock(int quantityChange) {
        int newStock = this.stock + quantityChange;
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.stock = newStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInventoryItem that = (ProductInventoryItem) o;
        return sku.equals(that.sku);
    }
}
