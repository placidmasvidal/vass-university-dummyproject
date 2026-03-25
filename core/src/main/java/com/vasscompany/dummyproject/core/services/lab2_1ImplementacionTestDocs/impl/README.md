# Lab 2.1: Implementación + Tests + Documentación Simultáneos (Java / AEM)

## Objetivo

Implementar una feature completa de forma **simultánea** en un proyecto **Java / AEM-like**,
generando a la vez:

- código de producción,
- tests unitarios,
- y documentación técnica/funcional,

usando IA con **contexto multi-archivo** y siguiendo la arquitectura del proyecto dummy.

## Duración Estimada

**3-4 horas**

## Prerequisitos

- ✅ Sesión 2.2 completada
- ✅ Conocimiento de contexto multi-archivo
- ✅ Copilot configurado y funcionando en VS Code / IntelliJ IDEA
- ✅ Proyecto `vass-university-dummyproject` compilando localmente
- ✅ Conocimientos básicos de Java 11, Maven, JUnit 5 y servicios OSGi

## Contexto del Ejercicio

En lugar del laboratorio original en Python orientado a un pequeño gestor de inventario,
en este laboratorio trabajarás sobre una versión equivalente y realista para un entorno
**Java / AEM 6.5-like**.

Vas a implementar un módulo de **gestión de inventario de productos** reutilizable desde
servicios OSGi, servlets o Sling Models, con estas capacidades:

- agregar productos al inventario,
- incrementar o reducir stock,
- validar disponibilidad,
- calcular stock total,
- calcular valor total del inventario,
- y mantener una persistencia básica **en memoria** para el laboratorio.

> **Importante:** aunque AEM suele persistir información en JCR, para este laboratorio se usará
> una persistencia simple en memoria para centrar el ejercicio en la técnica de generación
> simultánea de **implementación + tests + documentación**, no en infraestructura JCR avanzada.

## Relación con la arquitectura del proyecto

Este laboratorio debe respetar la estructura del proyecto dummy:

- **`core/`** para la lógica Java y los servicios OSGi,
- **`src/test/java/`** para tests unitarios,
- **`.copilot-context/`** para documentación de apoyo,
- separación por capas y responsabilidades,
- uso de interfaces de servicio,
- y estilo alineado con AEM / OSGi DS.

La arquitectura del proyecto define una separación clara entre presentación, aplicación,
servicios y datos, y destaca el uso de servicios OSGi, tests unitarios y documentación
actualizada como parte del flujo normal de desarrollo. Además, el proyecto recomienda ubicar
la lógica de negocio en `core/services`, la configuración en `ui.config` y los tests unitarios
en `core/src/test/java`.

---

## Qué vas a construir

### Artefactos principales del laboratorio

Crea estos archivos de forma **simultánea**:

```text
core/src/main/java/com/vasscompany/dummyproject/core/services/lab2_1ImplementacionTestDocs/
├── ProductInventoryService.java
├── ProductInventoryItem.java
└── impl/
    ├── ProductInventoryServiceImpl.java
    └── README.md   <-- este archivo

core/src/test/java/com/vasscompany/dummyproject/core/services/lab2_1ImplementacionTestDocs/impl/
└── ProductInventoryServiceImplTest.java

core/src/main/java/com/vasscompany/dummyproject/core/services/lab2_1ImplementacionTestDocs/docs/
└── inventory-service.md

.copilot-context/
├── arquitectura.md
└── patrones.md
```

## Diseño funcional sugerido

### `ProductInventoryItem.java`
Modelo simple de dominio con, como mínimo:

- `sku`
- `name`
- `unitPrice`
- `stock`

### `ProductInventoryService.java`
Interfaz del servicio con Javadoc y/o prompt contextual para que Copilot entienda el contrato.

Métodos sugeridos:

- `void addProduct(ProductInventoryItem item)`
- `void removeProduct(String sku)`
- `void increaseStock(String sku, int amount)`
- `void decreaseStock(String sku, int amount)`
- `boolean hasStock(String sku, int requestedUnits)`
- `int getTotalUnits()`
- `BigDecimal getTotalInventoryValue()`
- `Optional<ProductInventoryItem> getProduct(String sku)`
- `Collection<ProductInventoryItem> getAllProducts()`

### `ProductInventoryServiceImpl.java`
Implementación OSGi con almacenamiento en memoria para el laboratorio.

### `ProductInventoryServiceImplTest.java`
Tests unitarios con JUnit 5 y, si te resulta útil, AEM Mocks para registrar e inyectar el servicio.

### `inventory-service.md`
Documentación funcional y técnica del módulo:

- propósito,
- decisiones de diseño,
- reglas de validación,
- ejemplos de uso,
- limitaciones del almacenamiento en memoria,
- y posibles siguientes pasos hacia una versión AEM real con JCR.

---

## Requisito clave del laboratorio

No debes generar primero el código, luego los tests y luego la documentación.

Debes trabajar con **varios archivos abiertos a la vez** para que la IA tenga contexto
multi-archivo y sugiera artefactos sincronizados entre sí.

Ese es el aprendizaje central del laboratorio.

---

## Instrucciones

### Paso 1: Preparar el contexto multi-archivo (15 min)

1. Crea la estructura de paquetes indicada arriba.
2. Asegúrate de tener accesibles estos documentos de contexto:
   - `.copilot-context/arquitectura.md`
   - `.copilot-context/patrones.md`
3. Abre **simultáneamente** en tu IDE:
   - `ProductInventoryService.java`
   - `ProductInventoryItem.java`
   - `ProductInventoryServiceImpl.java`
   - `ProductInventoryServiceImplTest.java`
   - `inventory-service.md`
   - `.copilot-context/arquitectura.md`
   - `.copilot-context/patrones.md`

### Contexto que debes tener en mente

El proyecto dummy documenta como prácticas recomendadas:

- arquitectura por capas,
- uso de interfaces de servicio,
- inyección de dependencias con OSGi DS,
- separación entre lógica de negocio y acceso a datos,
- tests unitarios en `core/src/test/java`,
- y evitar anti-patrones típicos como mezclar demasiadas responsabilidades.

### Paso 2: Diseñar primero el contrato y el contexto (20 min)

Empieza por **la interfaz** y **la documentación**, no por la implementación.

#### En `ProductInventoryService.java`

Escribe un Javadoc inicial que sirva como prompt contextual para Copilot:

```java
/**
 * Servicio OSGi para gestionar inventario de productos en un contexto AEM-like.
 *
 * Responsabilidades:
 * - registrar productos en inventario
 * - modificar stock de forma segura
 * - validar disponibilidad de unidades
 * - calcular métricas agregadas del inventario
 *
 * Reglas:
 * - el SKU es obligatorio y único
 * - el nombre es obligatorio
 * - el precio unitario debe ser mayor que cero
 * - el stock nunca puede quedar en negativo
 * - si se intenta operar sobre un SKU inexistente, debe lanzarse excepción
 *
 * Consideraciones:
 * - implementación stateless a nivel de API, con almacenamiento interno simple
 * - laboratorio educativo, sin acceso real a JCR
 * - orientado a test unitario sencillo
 */
public interface ProductInventoryService {
    // deja que Copilot sugiera la firma inicial de métodos
}
```

#### En `inventory-service.md`

Escribe al menos este encabezado:

```markdown
# Product Inventory Service

Servicio de inventario para un proyecto Java / AEM-like.

## Objetivo
Gestionar productos, stock y métricas agregadas de inventario.

## Reglas principales
- SKU obligatorio y único
- Stock no negativo
- Precio mayor que cero
```

Con ambos archivos abiertos, la IA tenderá a sugerir una interfaz y una implementación más coherentes.

---

### Paso 3: Implementación simultánea en múltiples archivos (90 min)

#### En `ProductInventoryServiceImpl.java`

Empieza con una cabecera como esta:

```java
package com.vasscompany.dummyproject.core.services.lab2_1ImplementacionTestDocs.impl;

import com.vasscompany.dummyproject.core.services.lab2_1ImplementacionTestDocs.ProductInventoryItem;
import com.vasscompany.dummyproject.core.services.lab2_1ImplementacionTestDocs.ProductInventoryService;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component(service = ProductInventoryService.class, immediate = true)
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductInventoryServiceImpl.class);

    private final Map<String, ProductInventoryItem> inventory = new ConcurrentHashMap<>();

    // deja que Copilot sugiera la implementación
}
```

#### Mientras escribes esto, la IA debería empezar a sugerir:

- firmas de métodos en la interfaz,
- tests equivalentes en `ProductInventoryServiceImplTest.java`,
- y ampliaciones naturales de la documentación en `inventory-service.md`.

### Iteración recomendada

1. Escribe o acepta una operación en `ProductInventoryServiceImpl.java`
2. Cambia inmediatamente a `ProductInventoryServiceImplTest.java`
3. Acepta o ajusta el test correspondiente
4. Cambia a `inventory-service.md`
5. Documenta esa misma regla o comportamiento
6. Repite el ciclo

No trabajes en bloques grandes de 200 líneas. Trabaja en pequeñas iteraciones sincronizadas.

---

### Paso 4: Tests unitarios simultáneos (30 min)

Crea tests mientras implementas, no al final.

#### Estructura sugerida para `ProductInventoryServiceImplTest.java`

```java
@ExtendWith(AemContextExtension.class)
class ProductInventoryServiceImplTest {

    private final AemContext context = new AemContext();
    private ProductInventoryService service;

    @BeforeEach
    void setUp() {
        context.registerInjectActivateService(new ProductInventoryServiceImpl());
        service = context.getService(ProductInventoryService.class);
    }

    @Test
    void shouldAddProductSuccessfully() {
        // ...
    }

    @Test
    void shouldRejectDuplicatedSku() {
        // ...
    }

    @Test
    void shouldRejectNegativeStock() {
        // ...
    }

    @Test
    void shouldCalculateTotalInventoryValue() {
        // ...
    }
}
```

> Si prefieres un test unitario puro sin AEM Mocks porque el servicio no depende todavía de APIs de Sling,
> también es válido. Pero en un contexto AEM-like, usar AEM Mocks ayuda a mantener el hábito de registrar
> servicios OSGi de forma parecida a como vivirán en runtime.

### Casos mínimos que debes cubrir

#### Altas y bajas
- agregar producto válido
- rechazar SKU duplicado
- eliminar producto existente
- intentar eliminar SKU inexistente

#### Validaciones
- rechazar SKU nulo o vacío
- rechazar nombre nulo o vacío
- rechazar precio cero o negativo
- rechazar stock inicial negativo
- rechazar decrementos que dejen stock negativo
- rechazar operaciones con amount cero o negativo

#### Comportamiento funcional
- comprobar stock suficiente
- calcular unidades totales
- calcular valor total del inventario con `BigDecimal`
- recuperar producto por SKU
- listar productos registrados

#### Casos edge
- SKU con espacios alrededor
- grandes cantidades de stock
- precios con decimales
- inventario vacío
- concurrencia básica si decides endurecer el laboratorio

---

### Paso 5: Documentación simultánea (20 min)

Mientras vas aceptando implementación y tests, mantén actualizado `inventory-service.md`.

### Estructura sugerida de `inventory-service.md`

```markdown
# Product Inventory Service

## Propósito
[Qué resuelve este servicio]

## Responsabilidades
- Alta de productos
- Gestión de stock
- Cálculo de métricas agregadas

## Reglas de validación
- [lista]

## Ejemplos de uso
```java
// ejemplos de uso
```

## Decisiones de diseño
- Servicio OSGi
- Almacenamiento en memoria para laboratorio
- BigDecimal para importes

## Limitaciones
- No persiste en JCR
- No expone servlet todavía

## Evolución futura
- Repositorio JCR
- Servlet de consulta
- Integración con Sling Model
```
```

### Qué debe reflejar la documentación

La documentación debe corresponder exactamente con el código actual:

- si el servicio lanza `IllegalArgumentException`, debe decirlo,
- si el SKU se normaliza con `trim()`, debe decirlo,
- si la implementación usa `ConcurrentHashMap`, debe estar documentado como decisión técnica,
- si el inventario no persiste entre reinicios, debe quedar explícito.

---

### Paso 6: Validación continua (20 min)

Ejecuta tests frecuentemente durante el ejercicio:

```bash
mvn -pl core test
```

O, si quieres ejecutar solo la clase:

```bash
mvn -pl core -Dtest=ProductInventoryServiceImplTest test
```

Si el proyecto compila por módulos, también puedes validar el bundle `core` completo:

```bash
mvn clean install -pl core
```

La documentación del proyecto indica precisamente el uso de Maven para compilar, ejecutar tests
unitarios y trabajar por módulos dentro del reactor AEM.

---

### Paso 7: Refinamiento final en paralelo (20 min)

Haz una última pasada de mejora conjunta sobre los tres artefactos.

#### Revisión de implementación

- [ ] ¿La interfaz define claramente el contrato?
- [ ] ¿La implementación usa nombres semánticos?
- [ ] ¿Se usa `BigDecimal` para importes?
- [ ] ¿Las validaciones están centralizadas y son claras?
- [ ] ¿Los mensajes de error ayudan a depurar?
- [ ] ¿El servicio registra la interfaz correcta en `@Component`?
- [ ] ¿La lógica de negocio está separada del transporte o presentación?

#### Revisión de tests

- [ ] ¿Los tests cubren happy path?
- [ ] ¿Cubren errores y casos edge?
- [ ] ¿Son legibles y mantenibles?
- [ ] ¿No dependen del orden de ejecución?
- [ ] ¿La cobertura es alta (>90% como objetivo orientativo)?

#### Revisión de documentación

- [ ] ¿Describe el contrato real del servicio?
- [ ] ¿Incluye ejemplos de uso?
- [ ] ¿Documenta limitaciones y decisiones técnicas?
- [ ] ¿Está sincronizada con el código final?

---

## Criterios de calidad específicos para Java / AEM

Además de completar el laboratorio, el resultado debe seguir criterios consistentes con el proyecto dummy:

- ✅ Interfaz separada del `impl`
- ✅ Servicio registrado con `@Component(service = ProductInventoryService.class)`
- ✅ Sin `System.out.println`
- ✅ Logging con SLF4J cuando tenga sentido
- ✅ Validación explícita de entradas
- ✅ Sin secretos hardcodeados
- ✅ Sin mezclar lógica de negocio con servlet, HTL o acceso JCR real
- ✅ Tests unitarios claros y repetibles
- ✅ Documentación útil, no decorativa

Los documentos del proyecto remarcan precisamente el uso de servicios OSGi, tests unitarios,
separación por capas, validación de inputs y evitación de anti-patrones de AEM/OSGi.

---

## Entregables

Al finalizar el lab, este paquete o conjunto de paquetes debe contener:

- ✅ `ProductInventoryService.java`
- ✅ `ProductInventoryItem.java`
- ✅ `ProductInventoryServiceImpl.java`
- ✅ `ProductInventoryServiceImplTest.java`
- ✅ `inventory-service.md`
- ✅ notas o reflexión breve sobre cómo trabajaste la generación simultánea

### Archivo opcional de reflexión

Puedes crear también:

```text
core/src/main/java/com/vasscompany/dummyproject/core/services/lab2_1ImplementacionTestDocs/reflexion_lab_2.1.md
```

Con preguntas como:

- ¿Qué sugirió mejor la IA: la interfaz, la implementación, los tests o la documentación?
- ¿Qué parte tuviste que corregir manualmente?
- ¿La IA mantuvo coherencia entre código y docs?
- ¿Cuántas iteraciones necesitaste?
- ¿Cuánto tiempo ahorraste frente a hacerlo de forma secuencial?

---

## Criterios de Aceptación

El laboratorio se considera completo cuando:

- [ ] El servicio compila y funciona
- [ ] Los tests unitarios pasan
- [ ] La documentación está actualizada y refleja el comportamiento real
- [ ] Código, tests y docs se generaron de forma simultánea, no secuencial
- [ ] Las reglas de validación están cubiertas por tests
- [ ] La implementación sigue convenciones Java / AEM del proyecto
- [ ] No hay señales de anti-patrones graves (responsabilidades mezcladas, logs incorrectos, validaciones ausentes)

---

## Evaluación

**Éxito si:**

- completaste **código + tests + documentación** en menos tiempo que haciéndolo por separado,
- la calidad es equivalente o mejor que una implementación secuencial,
- los tests y la documentación están alineados con la implementación final,
- y el resultado podría encajar razonablemente en un proyecto AEM real como servicio reutilizable.

### Rúbrica orientativa

| Criterio | Excelente (4) | Bueno (3) | Satisfactorio (2) | Necesita Mejora (1) |
|----------|---------------|-----------|-------------------|---------------------|
| **Implementación** | Servicio limpio, robusto, bien validado y bien estructurado | Servicio correcto con pocos ajustes pendientes | Servicio funcional con carencias menores | Servicio incompleto o frágil |
| **Tests** | Cobertura amplia, edge cases, errores y happy path | Tests buenos con mayoría de casos relevantes | Tests básicos funcionales | Tests insuficientes |
| **Documentación** | Completa, útil y perfectamente sincronizada | Documentación buena con pequeños huecos | Documentación suficiente | Documentación pobre o desactualizada |
| **Uso multi-archivo de IA** | Flujo claramente simultáneo y bien aprovechado | Buen uso del contexto multi-archivo | Uso parcial del enfoque | Trabajo secuencial con poca sincronía |
| **Ajuste a Java/AEM** | Muy bien adaptado al stack y convenciones del proyecto | Bien adaptado con detalles menores | Adaptación suficiente | Adaptación débil o demasiado genérica |

**Puntuación mínima para aprobar: 12/20 (60%)**

---

## Tips y Ayuda

### Si la IA te genera código demasiado genérico

Refuerza el contexto en la interfaz y en la documentación:

- indica que es un **servicio OSGi**,
- especifica que estás en un proyecto **AEM 6.5-like**,
- recuerda que la persistencia del laboratorio es en memoria,
- y exige validaciones explícitas y tests con JUnit 5.

### Si los tests no acompañan al código

No sigas implementando a ciegas. Añade o ajusta primero:

- nombres de tests más descriptivos,
- métodos con comportamiento más concreto,
- y reglas de validación escritas en la documentación.

### Si la documentación queda demasiado pobre

Pide a la IA que documente:

- responsabilidades,
- reglas,
- decisiones técnicas,
- limitaciones,
- y ejemplos de uso reales.

### Si quieres llevar el lab un paso más allá

Puedes añadir una segunda fase opcional:

- exponer un servlet de solo lectura para consultar inventario,
- adaptar el servicio a un Sling Model,
- o sustituir el almacenamiento en memoria por una abstracción tipo repository.

---

## Resultado esperado

Al terminar, deberías haber practicado una forma de trabajo muy útil para desarrollo asistido con IA:

1. definir bien el contrato,
2. abrir varios artefactos a la vez,
3. hacer crecer implementación, tests y documentación en paralelo,
4. y validar continuamente que todo sigue alineado.

Ese patrón de trabajo encaja especialmente bien en proyectos Java/AEM, donde la calidad suele
venir de mantener sincronizados **interfaces, implementaciones, tests, configuración y documentación**.

---

**Versión**: 1.0
