package com.vasscompany.dummyproject.core.services.lab1_2ImplementacionPrompting;

import com.vasscompany.dummyproject.core.models.lab1_2ImplementacionPrompting.ShipmentValidationResult;

public interface ShippingCalculatorService {

    /**
     * Calcula el costo de envío basado en la distancia, peso y tipo de solicitante.
     * @param distanceKm
     * @param weightKg
     * @param applicantType
     * @return
     */
    double calculateShippingCost(double distanceKm, double weightKg, ApplicantType applicantType);

    /**
     * Calcula el tiempo de entrega estimado basado en la distancia y tipo de envío.
     * @param distanceKm
     * @param shippingType
     * @return
     */
    int calculateDeliveryTime(double distanceKm, ShippingType shippingType);

    /**
     * Valida si un envío es elegible para ser procesado basado en su peso, dimensiones y código de destino.
     * @param weightKg
     * @param dimensionsCm
     * @param destinationCode
     * @return
     */
    ShipmentValidationResult validateShipment(double weightKg, int[] dimensionsCm, String destinationCode);
}
