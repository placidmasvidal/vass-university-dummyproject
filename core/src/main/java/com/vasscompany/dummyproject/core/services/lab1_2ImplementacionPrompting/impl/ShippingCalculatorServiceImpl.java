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

    // Implementa SOLO calculateDeliveryTime siguiendo README y tests del paquete lab1_2ImplementacionPrompting.
    // Requisitos:
    // - No cambies firma, imports ni otros métodos.
    // - Mantén validaciones actuales de entrada.
    // - Usa reglas exactas de negocio definidas por tests/README (sin inventar).
    // - Devuelve int final según tipo de envío y distancia.
    // - Incluye mínimo de días y redondeo/regla de tramo EXACTOS según tests.
    // Caso ancla: EXPRESS con 250 km debe devolver el valor esperado por test.
    // Si hay conflicto, prioriza tests sobre suposiciones.
    @Override
    public int calculateDeliveryTime(double distanceKm, ShippingType shippingType) {
        if (distanceKm < 0) {
            throw new IllegalArgumentException("distanceKm no puede ser negativo");
        }
        if (shippingType == null) {
            throw new IllegalArgumentException("shippingType no puede ser null");
        }

        // Reglas exactas:
        // Mira plantilla planificacion_lab_1.2.md y README, así como los tests del paquete lab1_2ImplementacionPrompting para definir reglas exactas de negocio.

        // Reglas exactas por tipo de envío (días fijos, independientes de la distancia):
        // - EXPRESS:  2 días
        // - STANDARD: 5 días
        // - ECONOMY: 10 días
        // Caso ancla: EXPRESS (250km) -> 2
        switch (shippingType) {
            case EXPRESS:
                return 2;
            case STANDARD:
                return 5;
            case ECONOMY:
                return 10;
            default:
                throw new IllegalArgumentException("Tipo de envío no reconocido: " + shippingType);
        }
    }

    // Implementa SOLO validateShipment según README y tests del paquete lab1_2ImplementacionPrompting.
    // Requisitos:
    // - No cambies firma, imports, modelo ShipmentValidationResult ni otros métodos.
    // - Mantén el estilo de validación actual y mensajes deterministas.
    // - Evalúa peso, dimensionsCm[3] y destinationCode con las reglas exactas de tests/README.
    // - Devuelve ShipmentValidationResult(true,"OK") cuando cumpla; en error, false + motivo exacto esperado.
    // - No uses valores mágicos inventados: toma límites de los tests.
    // Caso ancla: usa un caso válido y uno inválido de tests para ajustar mensajes.
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

        // Reglas exactas:
        // Mira plantilla planificacion_lab_1.2.md y README, así como los tests del paquete lab1_2ImplementacionPrompting para definir reglas exactas de negocio.
        if (weightKg > 30) {
            return new ShipmentValidationResult(false, "Peso invalido: excede el limite de 30kg");
        }
        if (dimensionsCm[0] > 100 || dimensionsCm[1] > 100 || dimensionsCm[2] > 100) {
            return new ShipmentValidationResult(false, "Dimensiones invalidas: cada dimension debe ser menor o igual a 100cm");
        }
        if (!destinationCode.matches("\\d{5}")) {
            return new ShipmentValidationResult(false, "Codigo postal invalido: debe ser un codigo de 5 digitos");
        }

        return new ShipmentValidationResult(true, "OK");
    }
}
