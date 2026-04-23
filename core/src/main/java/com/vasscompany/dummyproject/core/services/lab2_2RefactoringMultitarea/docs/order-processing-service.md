# Order Processing Service

## Objetivo
Procesar pedidos aplicando validaciones de entrada, cálculo de subtotal,
descuentos de negocio y construcción del resultado final.

## Estructura actual
- `OrderProcessingService`: interfaz del servicio
- `LegacyOrderProcessingServiceImpl`: versión legacy base
- `OrderProcessingServiceImpl`: versión sobre la que se realizará el refactor
- `OrderProcessingRequest`: datos de entrada
- `OrderProcessingResult`: resultado de salida
- `OrderProcessingServiceImplTest`: red de seguridad mediante tests

## Reglas de negocio actuales
- La petición no puede ser nula
- El pedido debe contener al menos una línea
- Cada línea debe tener:
    - SKU no vacío
    - precio unitario mayor que cero
    - cantidad mayor que cero
- El subtotal es la suma de `unitPrice * quantity`
- Reglas de descuento:
    - `VIP` => 10%
    - `B2B` => 15% si subtotal >= 100
    - `WEB + PROMO5` => 5% adicional
- El descuento total está limitado al 20%
- Los importes monetarios se redondean a 2 decimales con `HALF_UP`

## Problemas detectados en el legacy
- método principal demasiado largo
- validación mezclada con cálculo
- strings mágicos (`VIP`, `B2B`, `WEB`, `PROMO5`)
- responsabilidad múltiple en un único método
- construcción manual del resultado
- logging mejorable
- documentación mínima

## Objetivo del refactor
Mantener exactamente el comportamiento observable mientras se mejora:
- legibilidad
- modularidad
- testabilidad
- nombres
- documentación

## Casos baseline cubiertos por tests
- pedido estándar sin descuento
- pedido VIP
- pedido B2B con umbral
- cupón web
- acumulación de descuentos
- límite máximo del 20%
- request nula
- líneas vacías
- SKU inválido
- precio inválido
- cantidad inválida
- tipo de cliente desconocido
