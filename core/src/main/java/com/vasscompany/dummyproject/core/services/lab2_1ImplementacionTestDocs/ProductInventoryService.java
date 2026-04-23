package com.vasscompany.dummyproject.core.services.lab2_1ImplementacionTestDocs;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

/**
 * Servicio OSGi para gestionar inventario de productos en un contexto AEM-like.
 *
 * Responsabilidades:
 * - registrar productos en inventario
 * - modificar stock de forma segura
 * - validar disponibilidad de unidades
 * - calcular métricas agregadas del inventario
 *
 * Reglas:
 * - el SKU es obligatorio y único
 * - el nombre es obligatorio
 * - el precio unitario debe ser mayor que cero
 * - el stock nunca puede quedar en negativo
 * - si se intenta operar sobre un SKU inexistente, debe lanzarse excepción
 *
 * Consideraciones:
 * - implementación stateless a nivel de API, con almacenamiento interno simple
 * - laboratorio educativo, sin acceso real a JCR
 * - orientado a test unitario sencillo
 */
public interface ProductInventoryService {

    // Product Inventory Service

//Servicio de inventario para un proyecto Java / AEM-like.

// Objetivo
//Gestionar productos, stock y métricas agregadas de inventario.

// Reglas principales
//- SKU obligatorio y único
//- Stock no negativo
//- Precio mayor que cero

    void registerProduct(String sku, String name, BigDecimal unitPrice, int initialStock);

    void updateStock(String sku, int quantityChange);

    void removeProduct(String sku);

    boolean isProductAvailable(String sku, int requiredQuantity);

    Optional<ProductInventoryItem> getProduct(String sku);

    int getTotalStock();

    // get total inventory value (sum of unit price * stock for all products)
    BigDecimal getTotalInventoryValue();

// Deja esta interfaz coherente con el laboratorio.
// Mantén los nombres actuales de métodos si no hace falta renombrarlos.
// Añade el método que falta para listar todos los productos del inventario.
// El contrato debe cubrir: alta, baja, actualización de stock, consulta por SKU,
// disponibilidad, stock total y valor total del inventario.

    Collection<ProductInventoryItem> listAllProducts();

    Collection<ProductInventoryItem> getAllProducts();

}
