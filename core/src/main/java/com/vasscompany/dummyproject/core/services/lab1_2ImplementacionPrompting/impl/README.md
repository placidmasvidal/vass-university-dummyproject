# Lab 1.2: Implementación de Feature Nueva con Prompting Consciente (Java / AEM)

## Objetivo

Implementar una feature nueva desde cero usando GitHub Copilot con técnicas de prompting
efectivo aprendidas, demostrando uso consciente y estratégico en un entorno real Java/AEM 6.5.

## Duración Estimada

**3-4 horas** (trabajo asíncrono entre sesiones)

## Prerequisitos

- ✅ Sesión 1.2 completada
- ✅ Lab 1.1 completado (o al menos iniciado)
- ✅ Dominio de principios CLEAR
- ✅ Conocimiento de Java básico-intermedio y OSGi
- ✅ Proyecto `vass-university-dummyproject` compilando localmente (`mvn clean install`)

## Contexto del Ejercicio

Eres parte de un equipo desarrollando un portal de gestión de becas (ICEX). Necesitas
implementar un nuevo servicio OSGi de cálculo de costes de envío de documentación que:

1. Calcula el coste de envío de documentación basado en distancia y peso
2. Aplica descuentos según tipo de solicitante
3. Calcula el tiempo estimado de entrega
4. Valida restricciones de envío

Debes implementar esto desde cero usando Copilot con prompting consciente, siguiendo
los patrones OSGi del proyecto definidos en `arquitectura.md` y `patrones.md`.

---

## Requisitos de la Feature

### Funcionalidad Principal

**Interfaz:** `ShippingCalculatorService.java`
**Implementación:** `impl/ShippingCalculatorServiceImpl.java`

Debe contener los siguientes métodos:

1. **`calculateShippingCost(double distanceKm, double weightKg, ApplicantType applicantType)`**
    - Calcula coste base según distancia y peso
    - Aplica descuentos según tipo de solicitante
    - Retorna coste final en euros (redondeado a 2 decimales)

2. **`calculateDeliveryTime(double distanceKm, ShippingType shippingType)`**
    - Calcula días hábiles estimados
    - Considera diferentes tipos de envío
    - Retorna número de días como `int`

3. **`validateShipment(double weightKg, int[] dimensionsCm, String destinationCode)`**
    - Valida restricciones de peso y dimensiones
    - Valida si se puede enviar al destino
    - Retorna `ShipmentValidationResult` (record o clase DTO con `valid` y `errorMessage`)

### Reglas de Negocio

**Coste de Envío:**
- Base: €10 por cada 100km
- Peso: +€5 por cada kg adicional después del primer kg
- Descuentos por tipo de solicitante (`enum ApplicantType`):
    - `PREMIUM`: 20% descuento
    - `REGULAR`: 10% descuento
    - `NEW`: Sin descuento
- Mínimo: €20 (el coste nunca puede ser inferior)

**Tiempo de Entrega (`enum ShippingType`):**
- `STANDARD`: 1 día por cada 100km (mínimo 2 días)
- `EXPRESS`: 0.5 días por cada 100km (mínimo 1 día), redondeado hacia arriba
- `ECONOMY`: 2 días por cada 100km (mínimo 4 días)
- Solo días hábiles (lunes–viernes)

**Validaciones:**
- Peso máximo: 30kg
- Dimensiones máximas: 150cm × 150cm × 150cm (array de 3 elementos)
- Solo envíos dentro de España (código de destino debe ser código postal válido: 5 dígitos)

---

## Instrucciones Paso a Paso

### Paso 1: Diseño y Planificación (30 min)

**Tareas:**

Antes de escribir código, planifica con Copilot como herramienta de diseño:

1. **Estructura del servicio**: ¿Qué métodos necesitas? ¿Qué enums y DTOs?
2. **Datos de entrada/salida**: ¿Qué parámetros? ¿Qué retornan? ¿Qué excepciones?
3. **Casos de uso**: Escribe ejemplos concretos de uso en el Javadoc
4. **Casos edge**: ¿Qué límites hay que manejar?

**Crea archivo:** `planificacion_lab_1.2.md`

**Template sugerido:**
```markdown
# Planificación: ShippingCalculatorService (AEM/OSGi)

## Métodos Principales

1. calculateShippingCost(...)
   - Inputs: distanceKm, weightKg, ApplicantType
   - Output: double (euros, 2 decimales)
   - Ejemplos: calculateShippingCost(500, 2.5, PREMIUM) → ?
   - Excepciones: IllegalArgumentException si inputs inválidos

2. calculateDeliveryTime(...)
   ...

3. validateShipment(...)
   ...

## Enums y DTOs necesarios
- enum ApplicantType { PREMIUM, REGULAR, NEW }
- enum ShippingType { STANDARD, EXPRESS, ECONOMY }
- class/record ShipmentValidationResult { boolean valid; String errorMessage; }

## Casos Edge
- Peso 0 o negativo
- Distancia muy grande
- ApplicantType null
- Array de dimensiones con tamaño incorrecto
- ...

## Estructura OSGi
- ¿Servicio stateless? Sí
- ¿Necesita @Activate? Solo si hay configuración OSGi
- ¿Necesita ResourceResolver? No (lógica pura)
```

---

### Paso 2: Implementación con Prompting CLEAR (90 min)

**Tareas:**

Implementa cada método usando la técnica CLEAR. Escribe el prompt completo como Javadoc
en la interfaz **antes** de dejar que Copilot implemente.

#### Método 1: `calculateShippingCost`

**Escribe el prompt CLEAR completo en la interfaz:**

```java
/**
 * [AQUÍ VA TU PROMPT CLEAR COMPLETO]
 *
 * Context: Servicio OSGi en AEM 6.5 para portal ICEX. Calcula el coste de
 * envío de documentación de becas basado en distancia, peso y tipo de solicitante.
 *
 * Language: Java 11, OSGi DS annotations, Javadoc, SLF4J, Google Java Style.
 * Usar BigDecimal internamente para precisión monetaria, retornar double.
 *
 * Examples:
 * - calculateShippingCost(500, 2.5, ApplicantType.PREMIUM)
 *   → Base: 50.0 (500/100 * 10), Peso extra: 7.5 (1.5kg * 5),
 *     Subtotal: 57.5, Descuento 20%: 11.5, Total: 46.0
 * - calculateShippingCost(50, 0.5, ApplicantType.NEW)
 *   → Base: 10.0 (mínimo por distancia corta), Peso: 0 (<1kg),
 *     Sin descuento, pero mínimo es 20.0 → Total: 20.0
 *
 * Assumptions:
 * - distanceKm >= 0
 * - weightKg > 0
 * - applicantType nunca null (validar con Objects.requireNonNull)
 * - Coste mínimo siempre €20
 *
 * Returns: double con 2 decimales de precisión
 * Raises: IllegalArgumentException si distanceKm < 0 o weightKg <= 0
 */
double calculateShippingCost(double distanceKm, double weightKg, ApplicantType applicantType);
```

**Ahora deja que Copilot genere la implementación en `ShippingCalculatorServiceImpl`.**

**Itera si es necesario:**
- Si el código no aplica los descuentos correctamente, agrega más ejemplos numéricos
- Si no usa BigDecimal para el cálculo interno, especifícalo en el prompt
- Si falta la validación del mínimo de €20, añade ese caso al prompt

#### Método 2: `calculateDeliveryTime`

**Repite el proceso:**
1. Escribe prompt CLEAR completo en la interfaz
2. Deja que Copilot implemente en `ShippingCalculatorServiceImpl`
3. Revisa: ¿redondea hacia arriba con `Math.ceil` para EXPRESS?
4. Itera hasta obtener código correcto

**Tips:**
- Incluye ejemplos con los 3 tipos de envío y distintas distancias
- Menciona explícitamente el redondeo (`Math.ceil`) para EXPRESS
- Especifica que el retorno es `int` (días completos)

#### Método 3: `validateShipment`

**Mismo proceso:**
- Prompt CLEAR con ejemplos de validaciones que pasan y que fallan
- Implementación con Copilot
- Verificar que el array `dimensionsCm` se valida con exactamente 3 elementos
- Verificar que el código postal se valida con regex `\d{5}`

---

### Paso 3: Tests Unitarios con AEM Mocks (60 min)

**Tareas:**

1. Crea la suite completa de tests usando Copilot con prompt apropiado
2. Ejecuta con `mvn test` y verifica que pasen
3. Comprueba cobertura con `mvn test jacoco:report`

**Crea el archivo:**
- core/src/test/java/com/vasscompany/dummyproject/core/services/lab1_2ImplementacionPrompting/impl/ShippingCalculatorServiceImplTest.java


**Prompt CLEAR para los tests:**

```java
/**
 * [AQUÍ VA TU PROMPT CLEAR PARA LOS TESTS]
 *
 * Context: Tests JUnit 5 para ShippingCalculatorServiceImpl en AEM 6.5.
 * Usar AemContext (io.wcm.testing.mock.aem.junit5) para registrar el servicio.
 * El servicio es stateless, no accede al JCR.
 *
 * Language: JUnit 5 (@Test, @DisplayName, @ParameterizedTest, @MethodSource),
 * AemContextExtension, assertEquals con delta para doubles.
 *
 * Examples a cubrir:
 * - calculateShippingCost con los 3 tipos de ApplicantType
 * - Coste mínimo de €20 se aplica correctamente
 * - calculateDeliveryTime con los 3 tipos de ShippingType
 * - validateShipment: peso excesivo, dimensiones excesivas, CP inválido, CP válido
 * - IllegalArgumentException para inputs inválidos
 *
 * Assumptions: Tests independientes, sin I/O, sin dependencias externas.
 */
```

**Estructura sugerida:**

```java
@ExtendWith(AemContextExtension.class)
class ShippingCalculatorServiceImplTest {

    private final AemContext context = new AemContext();
    private ShippingCalculatorService service;

    @BeforeEach
    void setUp() {
        context.registerInjectActivateService(new ShippingCalculatorServiceImpl());
        service = context.getService(ShippingCalculatorService.class);
    }

    @Test
    @DisplayName("Coste con cliente PREMIUM aplica 20% de descuento")
    void testCostePremium() { ... }

    @Test
    @DisplayName("Coste mínimo de €20 se aplica aunque el cálculo sea inferior")
    void testCosteMinimoAplicado() { ... }

    @Test
    @DisplayName("Tiempo entrega EXPRESS redondea hacia arriba")
    void testTiempoExpressRedondeoArriba() { ... }

    @Test
    @DisplayName("Peso superior a 30kg invalida el envío")
    void testValidacionPesoExcesivo() { ... }

    @Test
    @DisplayName("Código postal con formato incorrecto invalida el envío")
    void testValidacionCodigoPostalInvalido() { ... }

    @ParameterizedTest
    @DisplayName("Descuentos correctos para cada tipo de solicitante")
    @MethodSource("providerTiposSolicitante")
    void testDescuentosPorTipoSolicitante(ApplicantType tipo, double costeEsperado) { ... }

    // Agrega más casos edge según encuentres
}
```

---

### Paso 4: Documentación y Javadoc (30 min)

**Tareas:**

1. Asegúrate de que la interfaz tiene Javadoc completo en todos los métodos
2. Verifica que los enums (`ApplicantType`, `ShippingType`) tienen Javadoc por constante
3. Verifica que `ShipmentValidationResult` tiene Javadoc

**Usa Copilot para mejorar la documentación:**

```java
/**
 * [PROMPT para Copilot]
 * Mejorar Javadoc de todos los métodos siguiendo formato estándar Java:
 * descripción, @param, @return, @throws, @since, con ejemplos de uso.
 */
```

---

### Paso 5: Refinamiento y Optimización (30 min)

**Tareas:**

1. Revisa el código generado con ojos críticos
2. Identifica mejoras posibles:
    - ¿Hay lógica duplicada entre métodos?
    - ¿Las constantes están extraídas con nombres descriptivos?
    - ¿La lógica del enum de descuentos podría estar en el propio enum?
    - ¿Se puede usar un `switch` expression de Java 14+?
3. Usa Copilot para refactorizar:

```java
// [PROMPT para Copilot]
// Refactorizar para mejorar legibilidad y mantenibilidad:
// - Extraer constantes mágicas a static final con nombres descriptivos
// - Mover la lógica de descuento al propio enum ApplicantType (método getDiscount())
// - Simplificar lógica de validación
// - Asegurar Google Java Style compliance
```

---

### Paso 6: Auditoría Completa (20 min)

**Checklist de auditoría para Java/AEM:**

- [ ] Seguridad: ¿No hay credenciales hardcodeadas?
- [ ] Seguridad: ¿No se usa `getAdministrativeResourceResolver()`?
- [ ] Seguridad: ¿Los inputs se validan correctamente (nulls, rangos)?
- [ ] Calidad: ¿La lógica es correcta? ¿Todos los tests pasan?
- [ ] Calidad: ¿Cobertura de tests >80%? (`mvn test jacoco:report`)
- [ ] Calidad: ¿Google Java Style compliant?
- [ ] Documentación: ¿Javadoc completo y claro?


---

## Entregables

Al finalizar el lab, este paquete debe contener:

1. ✅ `ShippingCalculatorService.java` - Interfaz OSGi con prompts CLEAR como Javadoc
2. ✅ `impl/ShippingCalculatorServiceImpl.java` - Implementación completa y refactorizada
3. ✅ `ApplicantType.java` - Enum con lógica de descuento encapsulada
4. ✅ `ShippingType.java` - Enum de tipos de envío
5. ✅ `ShipmentValidationResult.java` - DTO/record de resultado de validación
6. ✅ `ShippingCalculatorServiceImplTest.java` - Suite completa de tests (en `src/test/...`)
7. ✅ `planificacion_lab_1.2.md` - Planificación inicial documentada
8. ✅ Notas o comentarios de los prompts usados e iteraciones realizadas

---

## Criterios de Aceptación

El lab se considera completo cuando:

- [ ] Los 3 métodos están implementados y funcionan correctamente
- [ ] Todos los tests pasan (`mvn test` — 100% pass rate)
- [ ] Cobertura de tests >80% (`mvn test jacoco:report`)
- [ ] La interfaz OSGi está correctamente definida y registrada (`@Component(service = ShippingCalculatorService.class)`)
- [ ] Los prompts CLEAR están documentados como Javadoc en la interfaz
- [ ] Javadoc completo en todos los métodos públicos, enums y DTOs
- [ ] Enums usados en lugar de magic strings
- [ ] Auditoría de seguridad completada
- [ ] Código sigue Google Java Style (`mvn checkstyle:check`)
- [ ] Los métodos manejan correctamente los casos edge (nulls, rangos, divisor cero)

---

## Evaluación

### Rúbrica de Evaluación

| Criterio | Excelente (4) | Bueno (3) | Satisfactorio (2) | Necesita Mejora (1) |
|----------|---------------|-----------|-------------------|---------------------|
| **Prompts CLEAR** | Prompts excepcionales como Javadoc, todos los elementos, muy descriptivos | Prompts buenos con mayoría de elementos | Prompts básicos con algunos elementos | Prompts incompletos o vagos |
| **Implementación OSGi** | Código excepcional: interfaz correcta, enums, DTO, robusto | Código bueno, interfaz y enums correctos | Código funcional con estructura básica | Código básico, falta estructura OSGi |
| **Tests** | Suite completa, alta cobertura, AemContext, casos edge | Tests buenos con buena cobertura | Tests básicos funcionales | Tests incompletos o sin AemContext |
| **Uso de Copilot** | Uso estratégico excelente, múltiples iteraciones documentadas | Uso bueno con iteraciones | Uso básico con alguna iteración | Uso pasivo, sin iteraciones |
| **Calidad General** | Código producción-ready, Checkstyle limpio, Javadoc completo | Código bueno con mejoras menores | Código funcional | Código necesita trabajo |

**Puntuación mínima para aprobar: 12/20 (60%)**

---

## Tips y Ayuda

### Si te quedas atascado:

1. **Copilot no entiende las reglas de negocio:**
    - Agrega más ejemplos numéricos específicos al Javadoc del prompt
    - Descompón el problema: primero pide solo la lógica del coste base, luego el descuento
    - Escribe pseudocódigo como comentario y luego pide a Copilot que lo convierta a Java

2. **Tests fallan:**
    - Verifica que entendiste correctamente los requisitos con un cálculo manual
    - Comprueba que `assertEquals` usa delta para doubles: `assertEquals(46.0, result, 0.001)`
    - Revisa que el `AemContext` registra el servicio antes de llamarlo en `@BeforeEach`

3. **Código muy complejo:**
    - Pide a Copilot que simplifique: `// Simplify this method keeping the same behavior`
    - Mueve la lógica de descuento al enum: `ApplicantType.PREMIUM.getDiscountRate()`
    - Extrae cada validación a un método privado con nombre descriptivo

### Recursos Adicionales

- Revisa sesión 1.2 sobre prompting efectivo
- Consulta `arquitectura.md` del proyecto para patrones OSGi correctos
- Consulta `patrones.md` para ejemplos de `@Component`, interfaces y enums
- Referencia OSGi DS Annotations: [OSGi Compendium R7](https://docs.osgi.org/specification/osgi.cmpn/7.0.0/)
- Referencia AEM Mocks: [wcm.io Testing](https://wcm.io/testing/aem-mock/)

---

**Versión**: 1.0
