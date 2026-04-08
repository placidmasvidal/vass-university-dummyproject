package com.vasscompany.dummyproject.core.services.lab1_2ImplementacionPrompting.impl;

import com.vasscompany.dummyproject.core.models.lab1_2ImplementacionPrompting.ShipmentValidationResult;
import com.vasscompany.dummyproject.core.services.lab1_2ImplementacionPrompting.ApplicantType;
import com.vasscompany.dummyproject.core.services.lab1_2ImplementacionPrompting.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShippingCalculatorServiceImplTest {

    private final ShippingCalculatorServiceImpl service = new ShippingCalculatorServiceImpl();

    @Test
    @DisplayName("calculateShippingCost: caso ancla PREMIUM (500km, 2.5kg) -> 46.00")
    void calculateShippingCost_premiumAnchorCase() {
        double result = service.calculateShippingCost(500, 2.5, ApplicantType.PREMIUM);
        assertEquals(46.00, result, 0.001);
    }

    @Test
    @DisplayName("calculateShippingCost: aplica minimo de 20 EUR")
    void calculateShippingCost_appliesMinimumCost() {
        double result = service.calculateShippingCost(50, 0.5, ApplicantType.NEW);
        assertEquals(20.00, result, 0.001);
    }

    @Test
    @DisplayName("calculateShippingCost: distanceKm negativo lanza IllegalArgumentException")
    void calculateShippingCost_negativeDistance_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.calculateShippingCost(-1, 2, ApplicantType.REGULAR)
        );
    }

    @Test
    @DisplayName("calculateShippingCost: applicantType null lanza IllegalArgumentException")
    void calculateShippingCost_nullApplicant_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.calculateShippingCost(100, 2, null)
        );
    }

    @Test
    @DisplayName("calculateDeliveryTime: caso ancla EXPRESS (250km) -> 2")
    void calculateDeliveryTime_expressAnchorCase() {
        int result = service.calculateDeliveryTime(250, ShippingType.EXPRESS);
        assertEquals(2, result);
    }

    @Test
    @DisplayName("calculateDeliveryTime: shippingType null lanza IllegalArgumentException")
    void calculateDeliveryTime_nullShippingType_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.calculateDeliveryTime(100, null)
        );
    }

    @Test
    @DisplayName("validateShipment: envio valido")
    void validateShipment_validCase() {
        ShipmentValidationResult result = service.validateShipment(10, new int[] {30, 20, 10}, "28013");

        assertTrue(result.isValid());
        assertEquals("OK", result.getMessage());
    }

    @Test
    @DisplayName("validateShipment: peso mayor de 30kg invalido")
    void validateShipment_overweight_isInvalid() {
        ShipmentValidationResult result = service.validateShipment(31, new int[] {30, 20, 10}, "28013");

        assertFalse(result.isValid());
        assertTrue(result.getMessage().toLowerCase().contains("peso"));
    }

    @Test
    @DisplayName("validateShipment: codigo postal invalido")
    void validateShipment_invalidPostalCode_isInvalid() {
        ShipmentValidationResult result = service.validateShipment(10, new int[] {30, 20, 10}, "28A13");

        assertFalse(result.isValid());
        assertTrue(result.getMessage().toLowerCase().contains("postal"));
    }
}
