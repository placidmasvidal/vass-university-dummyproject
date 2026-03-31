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

    // Asegúrate de que el método calculateFinalPrice cumpla con el resultado esperado: 10, 5, 20, 4 -> 52.5 usando la firma baseValue, multiplier, dividend y divisor


// Corrige la implementación actual.
// El método generado ahora devuelve 17.5 para (10, 5, 20, 4), pero el contrato del laboratorio exige 52.5.
// No inventes significados como taxRate o discount.
// Usa solo los nombres neutrales: baseValue, multiplier, dividend, divisor.
// Mantén las validaciones de NaN, infinito y divisor cero.
// Si no puedes deducir con certeza una fórmula correcta a partir del contexto disponible,
// no improvises una fórmula: deja una implementación explícita con TODO o UnsupportedOperationException
// y documenta la contradicción entre el legacy real (55.0) y el ejemplo esperado del laboratorio (52.5).
// utiliza esta fórmula:
// rawResult = (baseValue * multiplier) + ((dividend / divisor) / 2.0)
// finalResult = abs(rawResult)
// rounded = round(finalResult, 2)
    @Override
    public double calculateFinalPrice(double baseValue, double multiplier, double dividend, double divisor) {
        validateFiniteInputs(baseValue, multiplier, dividend, divisor);
        if (divisor == 0) {
            throw new IllegalArgumentException("El divisor no puede ser cero");
        }
        double rawResult = (baseValue * multiplier) + ((dividend / divisor) / 2.0);
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
