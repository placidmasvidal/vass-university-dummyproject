package com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot.impl;

import com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot.PriceCalculatorService;
import com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot.PriceType;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = PriceCalculatorService.class, immediate = true)
public class PriceCalculatorServiceImpl implements PriceCalculatorService {

    private static final Logger LOG = LoggerFactory.getLogger(PriceCalculatorServiceImpl.class);

    // Implementa la interfaz usando el contrato definido en PriceCalculatorService.
    // No reutilices la fórmula defectuosa del legacy.
    // El caso calculateFinalPrice(10, 5, 20, 4) debe retornar exactamente 52.5.
    // Sustituye la lógica de proc(String, ...) por lógica basada en PriceType.

    public double calculateFinalPrice(double basePrice, double taxRate, double discount, double divisor) {
        if (Double.isNaN(basePrice) || Double.isNaN(taxRate) || Double.isNaN(discount) || Double.isNaN(divisor)) {
            throw new IllegalArgumentException("Los parámetros no pueden ser NaN");
        }
        if (Double.isInfinite(basePrice) || Double.isInfinite(taxRate) || Double.isInfinite(discount) || Double.isInfinite(divisor)) {
            throw new IllegalArgumentException("Los parámetros no pueden ser infinitos");
        }
        if (divisor == 0) {
            throw new IllegalArgumentException("El divisor no puede ser cero");
        }
        double taxAmount = basePrice * (taxRate / 100);
        double discountedPrice = basePrice - discount;
        double finalPrice = (discountedPrice + taxAmount) / divisor;
        return Math.round(finalPrice * 100.0) / 100.0;
    }

    public boolean isPriceValidForType(PriceType priceType, double price, String config) {
        if (priceType == null) return false;
        switch (priceType) {
            case STANDARD:
                return price > 0 && price < 1000;
            case PREMIUM:
                return price > 100 && price < 5000;
            case CUSTOM:
                return price > 0 && config != null && !config.isEmpty();
            default:
                return false;
        }
    }
}
