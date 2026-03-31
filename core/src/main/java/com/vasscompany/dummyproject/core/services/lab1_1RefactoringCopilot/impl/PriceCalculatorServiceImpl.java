package com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot.impl;

import com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot.PriceCalculatorService;
import com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot.PriceType;
import org.osgi.service.component.annotations.Component;

@Component(service = PriceCalculatorService.class, immediate = true)
public class PriceCalculatorServiceImpl implements PriceCalculatorService {

    @Override
    public double calculateFinalPrice(double baseValue, double multiplier, double dividend, double divisor) {
        validateFiniteInputs(baseValue, multiplier, dividend, divisor);

        if (divisor == 0) {
            throw new IllegalArgumentException("El divisor no puede ser cero");
        }

        double rawResult = (baseValue * multiplier) + ((dividend / divisor) / 2.0);
        // añade una validación para que el resultado intermedio no se vuelva infinito
        if (Double.isInfinite(rawResult)) {
            throw new IllegalArgumentException("El resultado del cálculo es infinito, revisa los parámetros de entrada");
        }
        double finalResult = Math.abs(rawResult);

        return Math.round(finalResult * 100.0) / 100.0;
    }

    private void validateFiniteInputs(double... values) {
        for (double value : values) {
            if (Double.isNaN(value)) {
                throw new IllegalArgumentException("Los parámetros no pueden ser NaN");
            }
            if (Double.isInfinite(value)) {
                throw new IllegalArgumentException("Los parámetros no pueden ser infinitos");
            }
        }
    }

    @Override
    public boolean isPriceValidForType(PriceType priceType, double price, String config) {
        if (priceType == null) {
            return false;
        }

        switch (priceType) {
            case STANDARD:
                return price > 0 && price < 1000;
            case PREMIUM:
                return price > 100;
            case CUSTOM:
                return config != null && !config.isEmpty() && price >= 0;
            default:
                return false;
        }
    }
}
