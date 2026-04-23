# Inventory Service

## Objetivo

En este laboratorio he implementado un servicio de inventario para un proyecto Java / AEM-like, con el objetivo de practicar la generación simultánea de implementación, tests y documentación con ayuda de Copilot.

La idea del ejercicio no era trabajar con persistencia real en JCR, sino centrarme en la lógica de negocio del servicio y en cómo mantener alineados contrato, implementación, pruebas y documentación.

## Qué resuelve este servicio

El servicio permite gestionar un inventario simple de productos en memoria. Las operaciones principales que cubre son:

- registrar productos,
- eliminar productos,
- actualizar stock,
- consultar si un producto tiene stock suficiente,
- obtener un producto por SKU,
- calcular el stock total,
- calcular el valor total del inventario,
- y listar los productos registrados.

## Implementación

La implementación se ha realizado como un servicio OSGi, siguiendo la estructura habitual del proyecto dummy y la separación entre interfaz e implementación.

Para el almacenamiento se ha utilizado un `ConcurrentHashMap`, ya que en este laboratorio el inventario no se persiste en repositorio y solo se necesita una solución simple en memoria. Esta decisión permite centrarse en la lógica del servicio sin mezclarla con acceso a JCR u otras capas del proyecto.

También se ha utilizado `BigDecimal` para los importes, de manera que el cálculo del valor total del inventario sea correcto y no dependa de tipos numéricos con problemas de precisión.

## Validaciones aplicadas

Durante la implementación se han incluido validaciones para mantener la coherencia de los datos:

- el SKU es obligatorio y único,
- el nombre es obligatorio,
- el precio unitario debe ser mayor que cero,
- el stock inicial no puede ser negativo,
- no se puede operar sobre un SKU inexistente,
- no se permiten cambios de stock iguales a cero,
- y no se permite que una operación deje el stock final en negativo.

Además, el SKU se normaliza con `trim()` antes de utilizarse en las operaciones del servicio.

## ProductInventoryItem

La clase `ProductInventoryItem` representa cada producto del inventario y contiene los datos básicos del dominio:

- `sku`
- `name`
- `unitPrice`
- `stock`

Además de los getters, se añadió una forma controlada de actualizar el stock y se completó la clase con `equals`, `hashCode` y `toString`, de forma que sea utilizable de manera consistente dentro del inventario y en los tests unitarios.

## Tests unitarios

Se han implementado tests unitarios con JUnit 5 para verificar tanto escenarios correctos como casos de error.

Entre los casos cubiertos están:

- alta de producto válida,
- rechazo de SKU duplicado,
- rechazo de SKU vacío,
- rechazo de nombre vacío,
- rechazo de precio inválido,
- rechazo de stock inicial negativo,
- eliminación de producto existente,
- error al eliminar un SKU inexistente,
- aumento de stock,
- rechazo de cambios de stock a cero,
- y rechazo de decrementos que dejan el stock en negativo.

Además, se han añadido pruebas para disponibilidad, stock total, valor total del inventario y listado de productos, de manera que el servicio quede cubierto de forma más completa.

## Limitaciones

La principal limitación de esta implementación es que el inventario solo existe en memoria. Eso significa que:

- no persiste entre reinicios,
- no está integrado con JCR,
- no expone todavía un servlet,
- y no se ha conectado aún con Sling Models u otras capas de AEM.

## Conclusión

Considero que el laboratorio ha quedado bien resuelto para el objetivo que planteaba. La implementación sigue una estructura razonable para un servicio Java / AEM-like, aplica validaciones explícitas y cuenta con una base de tests suficientemente clara para comprobar el comportamiento principal.

Lo que más valor me ha aportado del ejercicio ha sido trabajar con varios artefactos a la vez y ver cómo Copilot mantenía cierta coherencia entre contrato, implementación y pruebas, aunque después haya sido necesario revisar y ajustar manualmente varios detalles para dejar el resultado listo para entregar.
