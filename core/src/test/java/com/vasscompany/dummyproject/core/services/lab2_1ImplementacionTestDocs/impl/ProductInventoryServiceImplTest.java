package com.vasscompany.dummyproject.core.services.lab2_1ImplementacionTestDocs.impl;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductInventoryServiceImplTest {

    private final ProductInventoryServiceImpl service = new ProductInventoryServiceImpl();

    @Test
    public void testRegisterProductValid() {
        service.registerProduct("SKU123", "Test Product", new BigDecimal("10.00"), 100);
        assertTrue(service.getProduct("SKU123").isPresent());
    }

    @Test
    public void testRegisterProductDuplicateSKU() {
        service.registerProduct("SKU123", "Test Product", new BigDecimal("10.00"), 100);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.registerProduct("SKU123", "Another Product", new BigDecimal("20.00"), 50);
        });
        assertTrue(exception.getMessage().contains("SKU already exists"));
    }

    @Test
    public void testRegisterProductEmptySKU() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.registerProduct("   ", "Test Product", new BigDecimal("10.00"), 100);
        });
        assertTrue(exception.getMessage().contains("SKU is required"));
    }

    @Test
    public void testRegisterProductEmptyName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.registerProduct("SKU124", "   ", new BigDecimal("10.00"), 100);
        });
        assertTrue(exception.getMessage().contains("Name is required"));
    }

    @Test
    public void testRegisterProductInvalidPrice() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.registerProduct("SKU125", "Test Product", new BigDecimal("-5.00"), 100);
        });
        assertTrue(exception.getMessage().contains("Unit price must be greater than zero"));
    }

    @Test
    public void testRegisterProductNegativeStock() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.registerProduct("SKU126", "Test Product", new BigDecimal("10.00"), -10);
        });
        assertTrue(exception.getMessage().contains("Initial stock cannot be negative"));
    }

    @Test
    public void testRemoveProductExisting() {
        service.registerProduct("SKU127", "Test Product", new BigDecimal("10.00"), 100);
        service.removeProduct("SKU127");
        assertTrue(service.getProduct("SKU127").isEmpty());
    }

    @Test
    public void testRemoveProductNonExisting() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.removeProduct("SKU128");
        });
        assertTrue(exception.getMessage().contains("SKU does not exist"));
    }

    @Test
    public void testUpdateStockPositive() {
        service.registerProduct("SKU129", "Test Product", new BigDecimal("10.00"), 100);
        service.updateStock("SKU129", 50);
        assertTrue(service.getProduct("SKU129").get().getStock() == 150);
    }

    @Test
    public void testUpdateStockZero() {
        service.registerProduct("SKU130", "Test Product", new BigDecimal("10.00"), 100);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.updateStock("SKU130", 0);
        });
        assertTrue(exception.getMessage().contains("Quantity change cannot be zero"));
    }

    @Test
    public void testUpdateStockNegativeResult() {
        service.registerProduct("SKU131", "Test Product", new BigDecimal("10.00"), 100);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.updateStock("SKU131", -150);
        });
        assertTrue(exception.getMessage().contains("Stock cannot be negative"));
    }


    @Test
    public void testIsProductAvailable() {
        service.registerProduct("SKU132", "Test Product", new BigDecimal("10.00"), 100);
        assertTrue(service.isProductAvailable("SKU132", 50));
        assertFalse(service.isProductAvailable("SKU132", 150));
    }

    @Test
    public void testGetTotalStock() {
        service.registerProduct("SKU133", "Product 1", new BigDecimal("10.00"), 100);
        service.registerProduct("SKU134", "Product 2", new BigDecimal("20.00"), 50);
        assertEquals(150, service.getTotalStock());
    }

    @Test
    public void testGetTotalInventoryValue() {
        service.registerProduct("SKU135", "Product 1", new BigDecimal("10.00"), 2);
        service.registerProduct("SKU136", "Product 2", new BigDecimal("5.50"), 4);
        assertEquals(new BigDecimal("42.00"), service.getTotalInventoryValue());
    }

    @Test
    public void testGetAllProducts() {
        service.registerProduct("SKU137", "Product 1", new BigDecimal("10.00"), 10);
        service.registerProduct("SKU138", "Product 2", new BigDecimal("20.00"), 20);
        assertEquals(2, service.getAllProducts().size());
    }

}
