package com.vasscompany.dummyproject.core.services.lab2_1ImplementacionTestDocs.impl;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductInventoryServiceImplTest {

    private final ProductInventoryServiceImpl service = new ProductInventoryServiceImpl();

// Casos mínimos que deben cubrir los tests:
// Altas y bajas
// agregar producto válido
// rechazar SKU duplicado
// eliminar producto existente
// intentar eliminar SKU inexistente

// Validaciones
// rechazar SKU nulo o vacío
// rechazar nombre nulo o vacío
// rechazar precio cero o negativo
// rechazar stock inicial negativo
// rechazar decrementos que dejen stock negativo
// rechazar operaciones con amount cero o negativo

// Comportamiento funcional
// comprobar stock suficiente
// calcular unidades totales
// calcular valor total del inventario con BigDecimal
// recuperar producto por SKU
// listar productos registrados
// Casos edge
// SKU con espacios alrededor
// grandes cantidades de stock
// precios con decimales
// inventario vacío
// concurrencia básica si decides endurecer el laboratorio

    // Completa esta clase como test unitario puro con JUnit 5.
// Usa el servicio instanciado directamente.
// Añade asserts reales para:
// - alta válida
// - SKU duplicado
// - SKU vacío
// - nombre vacío
// - precio cero o negativo
// - stock inicial negativo
// - eliminar SKU existente
// - eliminar SKU inexistente
// - updateStock positivo
// - updateStock negativo válido
// - updateStock que deja stock negativo
// - isProductAvailable
// - getTotalStock
// - getTotalInventoryValue con BigDecimal
// Los nombres de test deben ser descriptivos y el código legible.

    // Completa esta clase de test con JUnit 5 usando instancia directa del servicio.
// Añade tests reales con assertions para:
// - registerProduct válido
// - SKU duplicado
// - SKU vacío
// - nombre vacío
// - precio <= 0
// - stock inicial negativo
// - removeProduct existente
// - removeProduct inexistente
// - updateStock positivo
// - updateStock cero
// - updateStock que deja stock negativo
// - isProductAvailable
// - getTotalStock
// - getTotalInventoryValue con BigDecimal

    @Test
    public void testRegisterProduct_Valid() {
        service.registerProduct("SKU123", "Test Product", new BigDecimal("9.99"), 10);
        assertTrue(service.getProduct("SKU123").isPresent());
    }

    @Test
    public void testRegisterProduct_DuplicateSKU() {
        service.registerProduct("SKU123", "Test Product", new BigDecimal("9.99"), 10);
        try {
            service.registerProduct("SKU123", "Another Product", new BigDecimal("19.99"), 5);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("SKU already exists"));
        }
    }

    @Test
    public void testRegisterProduct_EmptySKU() {
        try {
            service.registerProduct("   ", "Test Product", new BigDecimal("9.99"), 10);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("SKU cannot be null or empty"));
        }
    }

    @Test
    public void testRegisterProduct_EmptyName() {
        try {
            service.registerProduct("SKU124", "   ", new BigDecimal("9.99"), 10);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Name cannot be null or empty"));
        }
    }

    @Test
    public void testRegisterProduct_NegativePrice() {
        try {
            service.registerProduct("SKU125", "Test Product", new BigDecimal("-1.00"), 10);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Unit price must be greater than zero"));
        }
    }

    @Test
    public void testRegisterProduct_NegativeStock() {
        try {
            service.registerProduct("SKU126", "Test Product", new BigDecimal("9.99"), -5);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Initial stock cannot be negative"));
        }
    }



}
