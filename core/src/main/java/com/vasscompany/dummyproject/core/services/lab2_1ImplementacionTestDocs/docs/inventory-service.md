# Product Inventory Service

## Objetivo

En este laboratorio estoy implementando un servicio de inventario para un proyecto Java / AEM-like, con la idea de practicar generación simultánea de código, tests y documentación con ayuda de Copilot.

El servicio está planteado como un servicio OSGi reutilizable y utiliza almacenamiento en memoria para centrarse en la lógica de negocio, sin entrar todavía en persistencia real con JCR.

## Estado actual

A estas alturas ya tengo bastante avanzado el contrato del servicio y buena parte de la implementación principal.

La interfaz `ProductInventoryService` recoge las operaciones básicas del inventario: registro de productos, eliminación, actualización de stock, consulta de disponibilidad, recuperación por SKU, cálculo de stock total y cálculo del valor total del inventario.

También está creado el modelo `ProductInventoryItem`, que representa cada producto con su SKU, nombre, precio unitario y stock.

En la implementación `ProductInventoryServiceImpl`, Copilot ya ha generado la mayor parte de la lógica de negocio principal:
- validación de SKU, nombre, precio y stock inicial,
- control de SKU duplicado,
- actualización de stock,
- eliminación de productos,
- comprobación de disponibilidad,
- cálculo de stock total,
- y cálculo del valor total con `BigDecimal`.

La persistencia se hace en memoria mediante un `ConcurrentHashMap`.

## Qué funciona ya

En la implementación actual ya está planteado correctamente:

- registro de productos con validaciones básicas,
- normalización del SKU con `trim()`,
- rechazo de precios no válidos,
- rechazo de stock inicial negativo,
- rechazo de SKU duplicado,
- actualización de stock sin permitir que quede en negativo,
- cálculo agregado de stock total,
- cálculo agregado del valor total del inventario.

## Qué queda pendiente

Aunque la implementación ya está bastante avanzada, todavía me quedan algunos puntos para cerrarlo bien:

- completar métodos auxiliares privados que la implementación usa para centralizar validaciones,
- terminar de completar `ProductInventoryItem` con `hashCode()` y `toString()`,
- revisar si añado también el listado completo de productos para dejar el contrato más alineado con el enunciado,
- completar los tests unitarios con asserts reales,
- y hacer una última revisión para que la documentación refleje exactamente el comportamiento final.

## Reglas de validación

Las reglas que se están aplicando o que quiero dejar cerradas en la versión final son:

- el SKU es obligatorio y único,
- el nombre es obligatorio,
- el precio unitario debe ser mayor que cero,
- el stock inicial no puede ser negativo,
- no se permite operar sobre un SKU inexistente,
- la actualización de stock no puede dejar el stock final en negativo,
- y no se deben aceptar cantidades inválidas en operaciones de stock.

## Decisiones de diseño

### Servicio OSGi

La implementación se hace como servicio OSGi porque encaja con la arquitectura del proyecto y con la forma habitual de organizar lógica reutilizable en AEM. La documentación del proyecto insiste en separar bien servicios, implementación y tests unitarios dentro de `core`, que es justo el enfoque seguido aquí. :contentReference[oaicite:12]{index=12} :contentReference[oaicite:13]{index=13}

### Almacenamiento en memoria

Para este laboratorio no uso JCR ni repositorio real. El objetivo es centrarse en la lógica del servicio y en el trabajo simultáneo entre implementación, tests y documentación.

### BigDecimal para importes

He utilizado `BigDecimal` para los precios y para el cálculo del valor total del inventario, porque es la opción adecuada para evitar errores de precisión en cálculos económicos.

### Estructura por capas

He intentado mantener la separación entre contrato, implementación y pruebas, siguiendo la idea de arquitectura por capas y evitando mezclar la lógica de negocio con servlets o con otras capas del proyecto. :contentReference[oaicite:14]{index=14} :contentReference[oaicite:15]{index=15}

## Limitaciones

La versión actual todavía tiene limitaciones claras:

- no persiste datos entre reinicios,
- no expone todavía un servlet ni un Sling Model,
- los tests unitarios aún no están terminados,
- y la implementación necesita una última pasada de cierre para quedar completamente consistente.

## Siguientes pasos

Los siguientes pasos que me faltan para cerrar el laboratorio son:

1. completar los helpers privados y los detalles que faltan en la implementación,
2. terminar `ProductInventoryItem`,
3. completar los tests unitarios,
4. revisar la interfaz para alinearla del todo con el enunciado,
5. y actualizar esta documentación con el comportamiento final exacto.

## Conclusión

Considero que el laboratorio ya está bien encarrilado. La parte más difícil, que era aterrizar el contrato y arrancar la lógica principal del servicio, ya está bastante avanzada.

Lo que me queda ahora no es tanto rediseñar, sino rematar bien: cerrar helpers, completar tests y dejar completamente sincronizados código y documentación.
