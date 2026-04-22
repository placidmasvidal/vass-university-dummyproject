# Product Inventory Service

Servicio de inventario para un proyecto Java / AEM-like.

## Objetivo

El objetivo de este laboratorio es construir un servicio reutilizable para gestionar productos en inventario, controlar el stock disponible y calcular métricas agregadas como el stock total y el valor total del inventario.

La idea es hacerlo siguiendo una estructura parecida a la de un proyecto AEM real: interfaz de servicio, implementación OSGi, tests unitarios y documentación técnica, aunque en este caso la persistencia sea solo en memoria.

## Estado actual del laboratorio

En el estado actual he podido avanzar sobre todo en el contrato y en la estructura base del ejercicio.

Por un lado, ya está creada la interfaz `ProductInventoryService`, donde quedan definidos los métodos principales del servicio: alta de producto, actualización de stock, eliminación, consulta de disponibilidad, recuperación por SKU, cálculo de stock total y cálculo del valor total del inventario.

También está creado el modelo `ProductInventoryItem`, con sus atributos básicos (`sku`, `name`, `unitPrice` y `stock`) y sus getters.

La implementación `ProductInventoryServiceImpl` está preparada como servicio OSGi y ya parte de un `ConcurrentHashMap` para guardar los productos en memoria, pero en este punto todavía no están implementadas las reglas de negocio ni las validaciones.

En cuanto a tests, la clase `ProductInventoryServiceImplTest` ya está arrancada y contiene identificados varios casos importantes, pero todavía faltan las aserciones y completar la cobertura real.

## Responsabilidades previstas del servicio

- Registrar productos en inventario
- Actualizar el stock de productos existentes
- Eliminar productos por SKU
- Consultar si un producto tiene stock suficiente
- Recuperar un producto concreto
- Calcular el stock total del inventario
- Calcular el valor total del inventario

## Reglas de validación previstas

Las reglas que quiero aplicar en la implementación son estas:

- El SKU debe ser obligatorio y único
- El nombre debe ser obligatorio
- El precio unitario debe ser mayor que cero
- El stock inicial no puede ser negativo
- No se debe permitir operar sobre SKUs inexistentes
- No se debe permitir una actualización de stock que deje el valor final en negativo

## Decisiones de diseño

### Servicio OSGi

La implementación está planteada como servicio OSGi, porque encaja con la arquitectura del proyecto dummy y con la forma habitual de organizar lógica reutilizable en AEM.

### Almacenamiento en memoria

Para este laboratorio no se utiliza JCR ni acceso a repositorio real. El inventario se guardará en memoria con un `ConcurrentHashMap`, ya que el objetivo del ejercicio no es la persistencia sino practicar implementación, tests y documentación de forma simultánea.

### Uso de BigDecimal

Para importes y cálculos económicos se utilizará `BigDecimal`, para evitar problemas de precisión que tendríamos con tipos numéricos de coma flotante.

## Limitaciones actuales

Ahora mismo el servicio todavía no está terminado, así que esta documentación describe sobre todo el diseño previsto y el estado de avance, no una versión completamente cerrada.

Además, aunque el laboratorio intenta parecerse a un caso real de AEM, de momento:

- no hay persistencia en JCR,
- no hay servlet de exposición,
- no hay integración con Sling Models,
- y la cobertura de tests todavía no está completada.

## Pendientes

Los siguientes pasos que me faltan para cerrar bien el laboratorio son:

1. Implementar la lógica completa en `ProductInventoryServiceImpl`
2. Añadir validaciones explícitas y mensajes de error claros
3. Completar los tests unitarios con casos de éxito y error
4. Revisar que la documentación quede totalmente alineada con el comportamiento final

## Conclusión

De momento considero que el laboratorio está bien encaminado en cuanto a estructura y contrato, pero todavía está en una fase intermedia.

Lo más avanzado ahora mismo es la definición del servicio y del modelo. Lo que falta realmente es completar la lógica de negocio, terminar los tests y actualizar esta documentación para que refleje el comportamiento final exacto.
