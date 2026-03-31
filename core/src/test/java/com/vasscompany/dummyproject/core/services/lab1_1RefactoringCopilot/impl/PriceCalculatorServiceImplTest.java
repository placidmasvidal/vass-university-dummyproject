package com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot.impl;

import com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot.PriceCalculatorService;
import com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot.PriceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PriceCalculatorServiceImplTest {

    private PriceCalculatorService service;

    @BeforeEach
    void setUp() {
        service = new PriceCalculatorServiceImpl();
    }

    @Test
    @DisplayName("calculateFinalPrice: caso básico debe retornar 52.5")
    void testCalculateFinalPriceCasoBasico() {
        assertEquals(52.5, service.calculateFinalPrice(10, 5, 20, 4), 0.001);
    }

    @Test
    @DisplayName("calculateFinalPrice: divisor cero debe lanzar IllegalArgumentException")
    void testCalculateFinalPriceDivisorCero() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateFinalPrice(10, 5, 20, 0));
    }

    @Test
    @DisplayName("calculateFinalPrice: si el resultado es negativo debe devolver valor absoluto")
    void testCalculateFinalPriceResultadoNegativoDevuelveValorAbsoluto() {
        assertEquals(47.5, service.calculateFinalPrice(-10, 5, 20, 4), 0.001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN})
    void testCalculateFinalPriceNaNEnBaseValue(double invalidValue) {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateFinalPrice(invalidValue, 5, 20, 4));
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN})
    void testCalculateFinalPriceNaNEnMultiplier(double invalidValue) {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateFinalPrice(10, invalidValue, 20, 4));
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN})
    void testCalculateFinalPriceNaNEnDividend(double invalidValue) {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateFinalPrice(10, 5, invalidValue, 4));
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN})
    void testCalculateFinalPriceNaNEnDivisor(double invalidValue) {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateFinalPrice(10, 5, 20, invalidValue));
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void testCalculateFinalPriceInfiniteEnBaseValue(double invalidValue) {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateFinalPrice(invalidValue, 5, 20, 4));
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void testCalculateFinalPriceInfiniteEnMultiplier(double invalidValue) {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateFinalPrice(10, invalidValue, 20, 4));
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void testCalculateFinalPriceInfiniteEnDividend(double invalidValue) {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateFinalPrice(10, 5, invalidValue, 4));
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void testCalculateFinalPriceInfiniteEnDivisor(double invalidValue) {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateFinalPrice(10, 5, 20, invalidValue));
    }

    @Test
    @DisplayName("calculateFinalPrice: Double.MAX_VALUE como input no debe lanzar excepción si el resto de inputs son válidos")
    void testCalculateFinalPriceDoubleMaxValue() {
        assertDoesNotThrow(() -> service.calculateFinalPrice(Double.MAX_VALUE, 1, 20, 4));
    }

    @Test
    @DisplayName("isPriceValidForType: STANDARD válido")
    void testIsPriceValidForTypeStandardValido() {
        assertTrue(service.isPriceValidForType(PriceType.STANDARD, 500, null));
    }

    @Test
    @DisplayName("isPriceValidForType: STANDARD inválido en límite inferior")
    void testIsPriceValidForTypeStandardLimiteInferior() {
        assertFalse(service.isPriceValidForType(PriceType.STANDARD, 0, null));
    }

    @Test
    @DisplayName("isPriceValidForType: STANDARD inválido en límite superior")
    void testIsPriceValidForTypeStandardLimiteSuperior() {
        assertFalse(service.isPriceValidForType(PriceType.STANDARD, 1000, null));
    }

    @Test
    @DisplayName("isPriceValidForType: PREMIUM válido")
    void testIsPriceValidForTypePremiumValido() {
        assertTrue(service.isPriceValidForType(PriceType.PREMIUM, 500, null));
    }

    @Test
    @DisplayName("isPriceValidForType: PREMIUM inválido en límite")
    void testIsPriceValidForTypePremiumLimite() {
        assertFalse(service.isPriceValidForType(PriceType.PREMIUM, 100, null));
    }

    @Test
    @DisplayName("isPriceValidForType: CUSTOM válido con config y precio cero")
    void testIsPriceValidForTypeCustomValido() {
        assertTrue(service.isPriceValidForType(PriceType.CUSTOM, 0, "cfg"));
    }

    @Test
    @DisplayName("isPriceValidForType: CUSTOM inválido sin config")
    void testIsPriceValidForTypeCustomSinConfig() {
        assertFalse(service.isPriceValidForType(PriceType.CUSTOM, 10, null));
    }

    @Test
    @DisplayName("isPriceValidForType: CUSTOM inválido con config vacía")
    void testIsPriceValidForTypeCustomConfigVacia() {
        assertFalse(service.isPriceValidForType(PriceType.CUSTOM, 10, ""));
    }

    @Test
    @DisplayName("isPriceValidForType: CUSTOM inválido con precio negativo")
    void testIsPriceValidForTypeCustomPrecioNegativo() {
        assertFalse(service.isPriceValidForType(PriceType.CUSTOM, -1, "cfg"));
    }

    @Test
    @DisplayName("isPriceValidForType: null debe retornar false")
    void testIsPriceValidForTypeNull() {
        assertFalse(service.isPriceValidForType(null, 100, "cfg"));
    }
}
