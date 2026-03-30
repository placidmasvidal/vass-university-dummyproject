package com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot.impl;

import org.junit.jupiter.api.Test;

public class PriceCalculatorServiceImplTest {

    private final PriceCalculatorServiceImpl service = new PriceCalculatorServiceImpl();

    // Implementa test para calculateFinalPrice de la implementación PriceCalculatorServiceImpl, cubriendo casos normales, casos límite y casos de error.
    // el resultado esperado de 10, 5, 20, 4 debe ser exactamente 52.5
    @Test
    public void testCalculateFinalPrice_NormalCase() {
        double result = service.calculateFinalPrice(10, 5, 20, 4);
        assert result == 52.5 : "Expected 52.5 but got " + result;
    }
    // Implementa test para isPriceValidForType con cada tipo de PriceType y casos límite

}
