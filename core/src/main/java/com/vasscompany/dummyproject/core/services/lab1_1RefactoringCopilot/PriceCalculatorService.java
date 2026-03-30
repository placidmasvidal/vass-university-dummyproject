package com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot;

/**
 * CLEAR
 *
 * C - Context:
 * Este servicio pertenece a la capa core/services de un proyecto AEM y encapsula
 * lógica de cálculo y validación de precios de forma clara, reutilizable y testeable.
 * La refactorización es correctiva: no debe reproducir fielmente el comportamiento
 * defectuoso del código legacy, sino cumplir la regla de negocio esperada del laboratorio.
 *
 * L - Language:
 * Java 21, OSGi Declarative Services, Javadoc, SLF4J y nombres descriptivos en camelCase.
 *
 * E - Examples:
 * - calculateFinalPrice(10, 5, 20, 4) retorna 52.5
 * - calculateFinalPrice(10, 5, 20, 0) lanza IllegalArgumentException
 * - isPriceValidForType(PriceType.STANDARD, 500, null) retorna true
 * - isPriceValidForType(PriceType.CUSTOM, -1, "cfg") retorna false
 *
 * A - Assumptions:
 * - El servicio es stateless y thread-safe.
 * - La especificación funcional del laboratorio prevalece sobre el comportamiento legacy.
 * - Los parámetros numéricos deben ser finitos.
 * - El divisor debe ser distinto de cero.
 * - Los tipos de precio deben modelarse con enum, no con strings.
 *
 * R - Results:
 * - El cálculo principal retorna un double redondeado a dos decimales.
 * - Nunca retorna null.
 * - Si el divisor es cero, lanza IllegalArgumentException.
 * - Si algún input es NaN o infinito, lanza IllegalArgumentException.
 * - Debe poder inyectarse en OSGi mediante @Reference.
 */
public interface PriceCalculatorService {

    // Firma no válida, sin nombres descriptivos generada de primeras por copilot con el prompt arriba proporcionado: double calculateFinalPrice(double a, double b, double c, double d);
    // modifica la firma anterior para que use nombres descriptivos y no deje dudas sobre el propósito de cada parámetro
    // Firma mejorada pero aun no válidadouble calculateFinalPrice(double basePrice, double taxRate, double discount, double divisor);
    // Firma válida:
    double calculateFinalPrice(double baseValue, double multiplier, double dividend, double divisor);

    boolean isPriceValidForType(PriceType priceType, double price, String config);
}
