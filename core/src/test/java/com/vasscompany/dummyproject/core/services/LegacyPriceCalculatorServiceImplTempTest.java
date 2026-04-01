package com.vasscompany.dummyproject.core.services;

import com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot.impl.LegacyPriceCalculatorServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class LegacyPriceCalculatorServiceImplTempTest {

    private final LegacyPriceCalculatorServiceImpl service = new LegacyPriceCalculatorServiceImpl();

    @Test
    @DisplayName("calc(10, 5, 20, 4) debe retornar 55.0")
    void testCalcCaso1() {
        assertEquals(55.0, service.calc(10, 5, 20, 4));
    }

    @Test
    @DisplayName("calc(2, 3, 10, 2) debe retornar 11.0")
    void testCalcCaso2() {
        assertEquals(11.0, service.calc(2, 3, 10, 2));
    }

    @Test
    @DisplayName("calc(10, 5, 20, 0) debe retornar null")
    void testCalcDivisorCero() {
        assertNull(service.calc(10, 5, 20, 0));
    }

    @Test
    @DisplayName("calc(-10, 5, 20, 4) debe retornar 45.0")
    void testCalcResultadoNegativoConvertidoAAbsoluto() {
        assertEquals(45.0, service.calc(-10, 5, 20, 4));
    }

    @Test
    @DisplayName("proc(\"A\", 500, null) debe retornar true")
    void testProcTipoA() {
        assertTrue(service.proc("A", 500, null));
    }

    @Test
    @DisplayName("proc(\"C\", -1, \"cfg\") debe retornar false")
    void testProcTipoCConValorNegativo() {
        assertFalse(service.proc("C", -1, "cfg"));
    }
}
