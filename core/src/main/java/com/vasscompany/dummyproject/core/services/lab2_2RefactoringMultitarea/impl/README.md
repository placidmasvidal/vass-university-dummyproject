# Lab 2.2: Refactoring Asistido con Preservación de Tests (Java / AEM)

## Objetivo

Refactorizar un módulo **legacy** dentro de un proyecto **Java / AEM-like** manteniendo los
**tests como red de seguridad**, usando IA para mejorar **código, tests y documentación**
sin alterar la funcionalidad observable.

El objetivo del laboratorio no es rehacer el servicio desde cero, sino **mejorar su diseño interno**
de forma segura, incremental y verificable.

---

## Duración estimada

**2-3 horas**

---

## Contexto del ejercicio

En el laboratorio original en Python se trabaja sobre un módulo `order_processor.py` con tests ya
existentes. En esta adaptación, el equivalente será un **servicio OSGi legacy de procesamiento de pedidos**
dentro del módulo `core` del proyecto dummy.

El punto de partida será una implementación funcional pero con problemas típicos de mantenibilidad:

- métodos demasiado largos,
- validaciones mezcladas con lógica de negocio,
- uso de `magic strings`,
- responsabilidades mezcladas,
- logging poco expresivo,
- documentación insuficiente,
- y tests que deben actuar como red de seguridad.

El objetivo es refactorizar **sin cambiar el comportamiento observable**, mejorando a la vez:

- legibilidad,
- estructura,
- nombres,
- testabilidad,
- y documentación técnica/funcional.

> **Importante:** este laboratorio trata de **refactoring seguro**, no de rediseño libre.
> Si un cambio mejora el diseño pero altera el contrato funcional, no se considera válido.

---

## Relación con la arquitectura del proyecto

El proyecto dummy sigue una arquitectura AEM multi-módulo con una separación clara entre
presentación, lógica de aplicación, servicios y configuración. La lógica Java reutilizable vive en el
módulo `core`, la configuración OSGi en `ui.config`, y los tests unitarios en `core/src/test/java`.
Además, el proyecto compila con Maven, usa arquitectura por capas y está orientado a servicios OSGi,
inyección de dependencias y código testeable. fileciteturn0file0 fileciteturn0file1

A nivel de build, el proyecto tiene configurado Maven con compilación sobre **Java 21** y ejecución de
pruebas mediante **Surefire**, por lo que este laboratorio debe encajar con ese flujo real. fileciteturn0file2

Este laboratorio debe respetar ese enfoque:

- interfaz y contrato en `core/services/...`,
- implementación en `impl/`,
- tests unitarios en `core/src/test/java/...`,
- documentación del módulo junto al propio paquete del laboratorio,
- y refactorización incremental guiada por tests.

---

## Qué vas a refactorizar

### Escenario Java / AEM propuesto

Trabajarás sobre una implementación legacy con una responsabilidad funcional como esta:

- recibir datos de un pedido,
- validar entrada,
- calcular subtotales y descuentos,
- aplicar reglas según tipo de cliente o canal,
- devolver un resultado de procesamiento,
- y registrar trazas de ejecución.

El equivalente del `order_processor.py` original será un servicio con una estructura similar a esta:

```text
core/src/main/java/com/vasscompany/dummyproject/core/services/lab2_2RefactoringMultitarea/
├── OrderProcessingService.java
├── OrderProcessingRequest.java
├── OrderProcessingResult.java
└── impl/
    ├── LegacyOrderProcessingServiceImpl.java   # Punto de partida legacy
    ├── OrderProcessingServiceImpl.java         # Versión refactorizada
    └── README.md                               # Este archivo

core/src/test/java/com/vasscompany/dummyproject/core/services/lab2_2RefactoringMultitarea/impl/
└── OrderProcessingServiceImplTest.java

core/src/main/java/com/vasscompany/dummyproject/core/services/lab2_2RefactoringMultitarea/docs/
└── order-processing-service.md
```

> Puedes ajustar los nombres exactos si ya arrancaste el laboratorio con otra convención,
> pero mantén la idea: **un servicio legacy, una versión refactorizada, tests y documentación**.

---

## Punto de partida recomendado

### `LegacyOrderProcessingServiceImpl.java`

Este archivo debe simular el módulo legacy que “funciona”, pero cuesta mantener.

Características recomendadas del legado:

- método principal demasiado largo, por ejemplo `processOrder(...)`,
- parámetros o variables poco expresivos,
- `if/else` anidados,
- `magic strings` como `"B2C"`, `"B2B"`, `"VIP"`, `"WEB"`,
- validaciones dispersas,
- descuentos mezclados con cálculo,
- construcción manual del resultado,
- logs poco útiles,
- y ausencia de Javadoc útil.

### `OrderProcessingServiceImpl.java`

Será la versión refactorizada que:

- conserva el comportamiento,
- mejora nombres,
- extrae métodos privados,
- separa validación, cálculo y ensamblado del resultado,
- mejora la legibilidad,
- y añade documentación.

### `OrderProcessingServiceImplTest.java`

Debe verificar que la versión refactorizada mantiene el comportamiento esperado y cubrir también
casos edge identificados durante el refactor.

### `order-processing-service.md`

Debe documentar:

- estructura final del servicio,
- reglas de negocio mantenidas,
- decisiones de refactor,
- casos edge detectados,
- y ejemplos de uso actualizados.

---

## Requisito clave del laboratorio

Los **tests son la red de seguridad**.

No se trata de refactorizar “a ojo”. Se trata de:

1. ejecutar tests antes,
2. hacer cambios pequeños,
3. volver a ejecutar tests,
4. y seguir únicamente cuando el comportamiento siga preservado.

Ese es el aprendizaje central del laboratorio.

---

## Instrucciones

### Paso 1: Preparar el contexto del refactor (15 min)

1. Crea o abre estos archivos:

```text
core/src/main/java/com/vasscompany/dummyproject/core/services/lab2_2RefactoringMultitarea/
├── OrderProcessingService.java
├── OrderProcessingRequest.java
├── OrderProcessingResult.java
└── impl/
    ├── LegacyOrderProcessingServiceImpl.java
    ├── OrderProcessingServiceImpl.java
    └── README.md

core/src/test/java/com/vasscompany/dummyproject/core/services/lab2_2RefactoringMultitarea/impl/
└── OrderProcessingServiceImplTest.java

core/src/main/java/com/vasscompany/dummyproject/core/services/lab2_2RefactoringMultitarea/docs/
└── order-processing-service.md

.copilot-context/
├── arquitectura.md
└── patrones.md
```

2. Abre simultáneamente en tu IDE:
   - `LegacyOrderProcessingServiceImpl.java`
   - `OrderProcessingService.java`
   - `OrderProcessingServiceImpl.java`
   - `OrderProcessingServiceImplTest.java`
   - `order-processing-service.md`
   - `.copilot-context/arquitectura.md`
   - `.copilot-context/patrones.md`

3. Ejecuta los tests para obtener una baseline:

```bash
mvn -pl core test -Dtest=OrderProcessingServiceImplTest
```

> Si todavía no has escrito tests suficientes, crea primero una baseline mínima que capture el
> comportamiento observable del legacy.

---

### Paso 2: Entender primero el módulo legacy (20 min)

Antes de refactorizar, entiende **qué hace realmente** el legado.

#### Tareas

1. Lee `LegacyOrderProcessingServiceImpl.java` completo.
2. Identifica:
   - entradas válidas e inválidas,
   - descuentos y reglas aplicadas,
   - condiciones especiales por tipo de cliente o canal,
   - side effects relevantes,
   - y excepciones o retornos especiales.
3. Documenta el comportamiento actual aunque no te guste.
4. Añade o ajusta tests para reflejar lo que el código hace **de verdad**, no lo que “debería” hacer.

#### Preguntas guía

- ¿Qué validaciones están mezcladas con la lógica de cálculo?
- ¿Qué nombres hacen difícil entender el flujo?
- ¿Hay `magic strings` que deberían ser `enum` o constantes?
- ¿Existen ramas duplicadas o casi duplicadas?
- ¿Hay bloques `try/catch` demasiado amplios?
- ¿El servicio mezcla validación, cálculo y formateo del resultado?
- ¿Hay logs con poco valor diagnóstico?

**Output esperado:**

- lista de problemas encontrados,
- lista de reglas funcionales reales,
- y baseline de tests que capture el comportamiento actual.

---

### Paso 3: Refactorizar con tests como red (60 min)

Refactoriza de forma **incremental**.

#### Técnica recomendada

Haz cambios pequeños y ejecuta tests continuamente.

```java
// Refactorizar LegacyOrderProcessingServiceImpl hacia OrderProcessingServiceImpl manteniendo:
// 1. comportamiento observable equivalente
// 2. nombres más claros
// 3. responsabilidades separadas
// 4. métodos privados extraídos
// 5. documentación técnica útil
//
// CRÍTICO: los tests deben seguir pasando tras cada cambio relevante
```

#### Proceso sugerido

1. Cambia un único aspecto pequeño.
2. Ejecuta tests.
3. Si fallan, revisa si cambiaste el comportamiento.
4. Corrige o revierte.
5. Continúa con el siguiente refactor pequeño.

#### Refactors seguros recomendados

- renombrar variables y métodos privados,
- extraer validaciones a métodos específicos,
- extraer cálculo de descuentos,
- extraer creación de resultado,
- sustituir `magic strings` por `enum` o constantes semánticas,
- reducir niveles de anidación con early returns,
- mejorar Javadoc,
- y mejorar logging sin alterar salidas funcionales.

#### Usa Copilot para

- proponer refactors pequeños y seguros,
- sugerir nombres más expresivos,
- identificar duplicidad,
- y mantener sincronizados tests y documentación.

---

### Paso 4: Actualizar documentación simultáneamente (30 min)

Mientras refactorizas, actualiza también `order-processing-service.md`.

#### El documento debe incluir

```markdown
# Order Processing Service

## Objetivo
Procesar pedidos aplicando validaciones y reglas de negocio.

## Estructura tras el refactor
- validación de entrada
- cálculo de subtotales
- aplicación de descuentos
- ensamblado del resultado

## Mejoras realizadas
- extracción de métodos privados
- nombres más expresivos
- reducción de anidación
- centralización de reglas repetidas

## Ejemplos de uso
[casos de entrada y salida]
```

#### Recomendación

No dejes la documentación para el final. La idea del laboratorio es que **código, tests y docs**
evolucionen juntos.

---

### Paso 5: Mejorar tests (30 min)

Ahora que el código es más claro, mejora también la suite de tests.

#### Objetivos de esta fase

- preservar todos los tests originales útiles,
- añadir casos edge descubiertos durante el análisis,
- mejorar nombres de tests,
- evitar duplicidad,
- y aumentar cobertura si faltan ramas importantes.

#### Casos que deberías considerar

- pedido nulo,
- líneas vacías,
- importes cero o negativos,
- tipos de cliente no reconocidos,
- canales no reconocidos,
- descuentos acumulados o incompatibles,
- redondeos monetarios,
- resultados límite,
- y diferencias entre cliente estándar, VIP o B2B si aplica.

#### Prompt orientativo para Copilot

```java
/**
 * Genera tests JUnit 5 para OrderProcessingServiceImpl.
 * Contexto:
 * - proyecto Java / AEM-like con Maven
 * - servicio OSGi registrado por interfaz
 * - objetivo: validar equivalencia funcional con el legacy
 * - cubrir casos normales, edge cases y excepciones
 * - usar nombres de test descriptivos
 * - usar assertThrows cuando aplique
 * - usar BigDecimal y tolerancias correctas si hay importes
 */
```

#### Ejecución sugerida

```bash
mvn -pl core test -Dtest=OrderProcessingServiceImplTest
```

---

## Criterios de calidad para Java / AEM

La versión refactorizada debería cumplir, como mínimo, con estos criterios:

- ✅ `@Component(service = OrderProcessingService.class)` correcto
- ✅ interfaz separada de la implementación
- ✅ nombres descriptivos en camelCase
- ✅ Javadoc útil en interfaz y métodos públicos clave
- ✅ lógica dividida en métodos pequeños y coherentes
- ✅ logging con SLF4J, sin `System.out.println` ni `printStackTrace`
- ✅ validaciones centralizadas o claramente localizadas
- ✅ tests unitarios legibles y mantenibles
- ✅ documentación sincronizada con el diseño final

---

## Ejemplo de estructura esperada

### Interfaz

```java
public interface OrderProcessingService {

    OrderProcessingResult processOrder(OrderProcessingRequest request);
}
```

### Implementación refactorizada

```java
@Component(service = OrderProcessingService.class, immediate = true)
public class OrderProcessingServiceImpl implements OrderProcessingService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderProcessingServiceImpl.class);

    @Override
    public OrderProcessingResult processOrder(final OrderProcessingRequest request) {
        validateRequest(request);

        BigDecimal subtotal = calculateSubtotal(request);
        BigDecimal discount = calculateDiscount(request, subtotal);
        BigDecimal finalAmount = subtotal.subtract(discount);

        return buildResult(request, subtotal, discount, finalAmount);
    }

    private void validateRequest(final OrderProcessingRequest request) {
        // ...
    }

    private BigDecimal calculateSubtotal(final OrderProcessingRequest request) {
        // ...
        return BigDecimal.ZERO;
    }

    private BigDecimal calculateDiscount(
            final OrderProcessingRequest request,
            final BigDecimal subtotal) {
        // ...
        return BigDecimal.ZERO;
    }

    private OrderProcessingResult buildResult(
            final OrderProcessingRequest request,
            final BigDecimal subtotal,
            final BigDecimal discount,
            final BigDecimal finalAmount) {
        // ...
        return null;
    }
}
```

### Test unitario

```java
@ExtendWith(AemContextExtension.class)
class OrderProcessingServiceImplTest {

    private final AemContext context = new AemContext();
    private OrderProcessingService service;

    @BeforeEach
    void setUp() {
        context.registerInjectActivateService(new OrderProcessingServiceImpl());
        service = context.getService(OrderProcessingService.class);
    }

    @Test
    @DisplayName("Debe mantener el cálculo esperado para pedido web estándar")
    void shouldProcessStandardWebOrder() {
        // ...
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la petición es nula")
    void shouldFailWhenRequestIsNull() {
        // ...
    }
}
```

---

## Entregables

Al finalizar el laboratorio, este paquete debería contener:

- ✅ `OrderProcessingService.java` - interfaz del servicio refactorizado
- ✅ `LegacyOrderProcessingServiceImpl.java` - versión legacy base de comparación
- ✅ `OrderProcessingServiceImpl.java` - versión refactorizada
- ✅ `OrderProcessingServiceImplTest.java` - tests unitarios con baseline + casos edge
- ✅ `order-processing-service.md` - documentación técnica/funcional actualizada
- ✅ `reflexion_lab_2.2.md` - reflexión final del laboratorio

---

## Reflexión final (15 min)

Crea el archivo `reflexion_lab_2.2.md` con tus observaciones.

### Preguntas sugeridas

- ¿Qué parte del legacy era más difícil de entender?
- ¿Qué tests fueron más importantes como red de seguridad?
- ¿Qué refactor sugirió bien la IA y cuáles tuviste que corregir?
- ¿Dónde intentó “mejorar” el diseño cambiando sin querer el comportamiento?
- ¿Qué aprendiste sobre refactor seguro en un entorno Java / AEM?
- ¿Cuánto tiempo ahorraste frente a hacerlo sin IA?

---

## Criterios de aceptación

El laboratorio se considera completo cuando:

- [ ] El código fue refactorizado y es más mantenible
- [ ] Todos los tests originales relevantes siguen pasando
- [ ] La cobertura se mantiene o mejora
- [ ] La documentación fue actualizada y refleja la estructura final
- [ ] El código resultante es más legible y modular
- [ ] La funcionalidad exacta se preserva
- [ ] Se documentó la reflexión final del proceso

---

## Tips y ayuda

### Si te quedas atascado

1. **No entiendes el legacy:**
   - ejecuta ejemplos concretos,
   - documenta comportamiento observable,
   - y escribe tests antes de tocar demasiado.

2. **Los tests fallan tras un cambio:**
   - revisa si hiciste refactor o reescritura,
   - deshaz el último cambio,
   - y vuelve a una modificación más pequeña.

3. **Copilot propone cambios demasiado grandes:**
   - pide refactors pequeños,
   - limita el alcance del prompt,
   - y trabaja por bloques: validación, cálculo, ensamblado.

4. **La documentación se desincroniza:**
   - actualízala en paralelo,
   - no la relegues al final,
   - y usa ejemplos concretos del comportamiento real.

---

## Bonus opcional

Si terminas antes, añade una mejora adicional sin romper el objetivo del lab:

1. introducir `enum` para tipos de cliente o canal,
2. reemplazar condicionales repetidos por métodos expresivos,
3. añadir parametrized tests,
4. o comparar complejidad antes/después del refactor.

---

**Versión**: 1.0
