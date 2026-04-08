package com.vasscompany.dummyproject.core.services.lab1_2ImplementacionPrompting.impl;

import com.vasscompany.dummyproject.core.models.lab1_2ImplementacionPrompting.ShipmentValidationResult;
import com.vasscompany.dummyproject.core.services.lab1_2ImplementacionPrompting.ApplicantType;
import com.vasscompany.dummyproject.core.services.lab1_2ImplementacionPrompting.ShippingCalculatorService;
import com.vasscompany.dummyproject.core.services.lab1_2ImplementacionPrompting.ShippingType;
import org.osgi.service.component.annotations.Component;

@Component(service = ShippingCalculatorService.class, immediate = true)
public class ShippingCalculatorServiceImpl implements ShippingCalculatorService {

    // reglas de coste base/peso/descuento/mínimo
    // caso ancla numérico (500,2.5,PREMIUM -> 46.00)
    @Override
    public double calculateShippingCost(double distanceKm, double weightKg, ApplicantType applicantType) {
        if (distanceKm < 0) {
            throw new IllegalArgumentException("distanceKm no puede ser negativo");
        }
        if (weightKg <= 0) {
            throw new IllegalArgumentException("weightKg debe ser mayor que 0");
        }
        if (applicantType == null) {
            throw new IllegalArgumentException("applicantType no puede ser null");
        }

        // Reglas exactas:
        // - baseDistanceCost = (distanceKm / 100.0) * 10.0
        // - extraWeightKg = max(0, weightKg - 1.0)
        // - extraWeightCost = extraWeightKg * 5.0
        // - subtotal = baseDistanceCost + extraWeightCost
        // - discount: PREMIUM 20%, REGULAR 10%, NEW 0%
        // - finalCost = max(subtotal * (1 - discount), 20.0)
        // - return con 2 decimales
        // Caso ancla: (500, 2.5, PREMIUM) -> 46.00

        double baseDistanceCost = (distanceKm / 100.0) * 10.0;
        double extraWeightKg = Math.max(0, weightKg - 1.0);
        double extraWeightCost = extraWeightKg * 5.0;
        double subtotal = baseDistanceCost + extraWeightCost;

        double discount = 0.0;
        switch (applicantType) {
            case PREMIUM:
                discount = 0.20;
                break;
            case REGULAR:
                discount = 0.10;
                break;
            case NEW:
                discount = 0.0;
                break;
        }
        double finalCost = subtotal * (1 - discount);
        finalCost = Math.max(finalCost, 20.0);

        return Math.round(finalCost * 100.0) / 100.0;

    }

    @Override
    public int calculateDeliveryTime(double distanceKm, ShippingType shippingType) {
        if (distanceKm < 0) {
            throw new IllegalArgumentException("distanceKm no puede ser negativo");
        }
        if (shippingType == null) {
            throw new IllegalArgumentException("shippingType no puede ser null");
        }

        // TDD bootstrap: valor temporal hasta implementar reglas de negocio.
        return 0;
    }

    @Override
    public ShipmentValidationResult validateShipment(double weightKg, int[] dimensionsCm, String destinationCode) {
        if (weightKg <= 0) {
            return new ShipmentValidationResult(false, "Peso invalido");
        }
        if (dimensionsCm == null || dimensionsCm.length != 3) {
            return new ShipmentValidationResult(false, "Dimensiones invalidas");
        }
        if (destinationCode == null || destinationCode.isEmpty()) {
            return new ShipmentValidationResult(false, "Codigo postal invalido");
        }

        // TDD bootstrap: respuesta temporal hasta implementar reglas de negocio.
        return new ShipmentValidationResult(false, "PENDING_IMPLEMENTATION");
    }
}
