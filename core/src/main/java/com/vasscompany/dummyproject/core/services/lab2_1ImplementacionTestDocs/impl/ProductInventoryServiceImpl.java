package com.vasscompany.dummyproject.core.services.lab2_1ImplementacionTestDocs.impl;

import com.vasscompany.dummyproject.core.services.lab2_1ImplementacionTestDocs.ProductInventoryItem;
import com.vasscompany.dummyproject.core.services.lab2_1ImplementacionTestDocs.ProductInventoryService;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component(service = ProductInventoryService.class, immediate = true)
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductInventoryServiceImpl.class);

    private final Map<String, ProductInventoryItem> inventory = new ConcurrentHashMap<>();

    // Implementa todos los métodos de ProductInventoryService usando inventory como almacenamiento en memoria.
// Reglas:
// - SKU obligatorio, no nulo, no vacío y único
// - name obligatorio, no nulo y no vacío
// - unitPrice mayor que cero
// - initialStock no negativo
// - al operar con un SKU inexistente, lanzar IllegalArgumentException
// - updateStock debe permitir sumar o restar, pero no aceptar 0 ni dejar stock negativo
// - normaliza SKU con trim antes de usarlo
// - getTotalInventoryValue debe sumar unitPrice * stock con BigDecimal
// - no usar JCR ni otros servicios
// - logging solo cuando aporte valor

    @Override
    public void registerProduct(String sku, String name, BigDecimal unitPrice, int initialStock) {
        // Validaciones de entrada
        if (sku == null || sku.trim().isEmpty()) {
            throw new IllegalArgumentException("SKU is required and cannot be empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required and cannot be empty");
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than zero");
        }
        if (initialStock < 0) {
            throw new IllegalArgumentException("Initial stock cannot be negative");
        }

        String normalizedSku = sku.trim();
        if (inventory.containsKey(normalizedSku)) {
            throw new IllegalArgumentException("SKU already exists: " + normalizedSku);
        }

        ProductInventoryItem item = new ProductInventoryItem(normalizedSku, name.trim(), unitPrice, initialStock);
        inventory.put(normalizedSku, item);
    }

    // Implementa updateStock con validación defensiva.
// Debe rechazar quantityChange == 0.
// Si el SKU no existe, lanzar IllegalArgumentException.
// Si el decremento deja el stock por debajo de cero, lanzar IllegalArgumentException.
// Mantén el comportamiento simple y claro para test unitario.
    @Override
    public void updateStock(String sku, int quantityChange) {
        if (quantityChange == 0) {
            throw new IllegalArgumentException("Quantity change cannot be zero");
        }
        String normalizedSku = normalizeSku(sku);
        ProductInventoryItem item = getExistingProduct(normalizedSku);
        item.updateStock(quantityChange);
    }

    @Override
    public void removeProduct(String sku) {
        String normalizedSku = normalizeSku(sku);
        if (!inventory.containsKey(normalizedSku)) {
            throw new IllegalArgumentException("SKU does not exist: " + normalizedSku);
        }
        inventory.remove(normalizedSku);
    }

    // Refuerza isProductAvailable para rechazar requiredQuantity <= 0 con IllegalArgumentException.
    @Override
    public boolean isProductAvailable(String sku, int requiredQuantity) {
        if (requiredQuantity <= 0) {
            throw new IllegalArgumentException("Required quantity must be greater than zero");
        }
        String normalizedSku = normalizeSku(sku);
        ProductInventoryItem item = getExistingProduct(normalizedSku);
        return item.getStock() >= requiredQuantity;
    }

    @Override
    public Optional<ProductInventoryItem> getProduct(String sku) {
        String normalizedSku = normalizeSku(sku);
        return Optional.ofNullable(inventory.get(normalizedSku));
    }

    @Override
    public int getTotalStock() {
        return inventory.values().stream().mapToInt(ProductInventoryItem::getStock).sum();
    }

    @Override
    public BigDecimal getTotalInventoryValue() {
        return inventory.values().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Añade los métodos privados que faltan para que esta clase compile.
// Implementa:
// - normalizeSku(String sku): valida que no sea null ni vacío tras trim y devuelve el SKU normalizado
// - getExistingProduct(String normalizedSku): devuelve el producto existente o lanza IllegalArgumentException si no existe
// Mantén mensajes de error claros y coherentes con el resto de la clase.

    private String normalizeSku(String sku) {
        if (sku == null || sku.trim().isEmpty()) {
            throw new IllegalArgumentException("SKU is required and cannot be empty");
        }
        return sku.trim();
    }

    private ProductInventoryItem getExistingProduct(String normalizedSku) {
        ProductInventoryItem item = inventory.get(normalizedSku);
        if (item == null) {
            throw new IllegalArgumentException("SKU does not exist: " + normalizedSku);
        }
        return item;
    }

    @Override
    public Collection<ProductInventoryItem> listAllProducts() {
        return inventory.values();
    }

    // Implementa getAllProducts devolviendo una vista segura/simple de los productos registrados.
    @Override
    public Collection<ProductInventoryItem> getAllProducts() {
        return inventory.values();
    }

}
