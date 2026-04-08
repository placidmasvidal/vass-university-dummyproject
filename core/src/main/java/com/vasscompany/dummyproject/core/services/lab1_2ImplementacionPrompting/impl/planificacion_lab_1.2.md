**Estructura del servicio**

- Implementar en `ShippingCalculatorServiceImpl` los 3 metodos de `ShippingCalculatorService`:
  - `calculateShippingCost(double distanceKm, double weightKg, ApplicantType applicantType)`
  - `calculateDeliveryTime(double distanceKm, ShippingType shippingType)`
  - `validateShipment(double weightKg, int[] dimensionsCm, String destinationCode)`
- Usar enums:
  - `ApplicantType`: `PREMIUM`, `REGULAR`, `NEW`
  - `ShippingType`: `STANDARD`, `EXPRESS`, `ECONOMY`
- Usar DTO `ShipmentValidationResult` para devolver resultado de validacion (`valid` + `message`).


**Datos de entrada/salida**

- `calculateShippingCost`
  - Entrada: distancia, peso, tipo de solicitante
  - Salida: `double` en EUR con 2 decimales
  - Error esperado: `IllegalArgumentException` en entradas invalidas
- `calculateDeliveryTime`
  - Entrada: distancia, tipo de envio
  - Salida: `int` dias estimados
  - Error esperado: `IllegalArgumentException` en entradas invalidas
- `validateShipment`
  - Entrada: peso, array de 3 dimensiones, codigo destino
  - Salida: `ShipmentValidationResult`


**Reglas de negocio**

- Coste base: `10 EUR` por cada `100 km`.
- Peso: `+5 EUR` por cada kg adicional despues del primer kg.
- Descuentos por solicitante:
  - `PREMIUM`: 20%
  - `REGULAR`: 10%
  - `NEW`: 0%
- Coste minimo final: `20 EUR`.
- Entrega:
  - `STANDARD`: 1 dia/100 km, minimo 2 dias
  - `EXPRESS`: 0.5 dias/100 km, minimo 1 dia, redondeo hacia arriba
  - `ECONOMY`: 2 dias/100 km, minimo 4 dias
- Validaciones de envio:
  - Peso maximo 30 kg
  - Dimensiones maximas 150 x 150 x 150 cm
  - Codigo destino valido: 5 digitos (`\\d{5}`)


**Casos edge a cubrir**

- `distanceKm < 0`
- `weightKg <= 0` para calculo de coste
- `applicantType == null`
- `shippingType == null`
- `dimensionsCm == null` o longitud distinta de 3
- Dimension mayor de 150
- Peso mayor de 30
- Codigo destino invalido
- Distancia corta que de coste menor a 20 (debe aplicar minimo)


**Estrategia TDD (arranque)**

- Paso 1: tests de ancla para cada metodo (happy path).
- Paso 2: tests de validacion (nulls, rangos, formatos).
- Paso 3: implementacion minima para pasar tests.
- Paso 4: refactor (constantes, metodos privados, legibilidad).
- Paso 5: revisar Javadoc y limpiar codigo.


**Criterios de aceptacion**

- Los 3 metodos implementados y pasando tests.
- Reglas de negocio aplicadas exactamente (coste, descuento, minimo, tiempos).
- Validaciones de envio correctas para peso, dimensiones y CP.
- Inputs invalidos gestionados con excepcion o resultado invalido segun contrato.
- Codigo legible y mantenible, sin magic numbers dispersos.


**Checklist de ejecucion**

- [ ] Escribir tests de `calculateShippingCost`
- [ ] Escribir tests de `calculateDeliveryTime`
- [ ] Escribir tests de `validateShipment`
- [ ] Implementar metodos en `ShippingCalculatorServiceImpl`
- [ ] Ejecutar `mvn -pl core test`
- [ ] Ajustar/refactorizar tras test verde
- [ ] Revisar Javadoc final
