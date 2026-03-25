# Lab 1.1: Refactorización de Servicio Legacy con Copilot (Java / AEM)

## Objetivo

Refactorizar un servicio OSGi legacy usando GitHub Copilot de forma consciente, aplicando
principios de uso estratégico y técnicas de contexto priming en un entorno real Java/AEM 6.5.

## Duración Estimada

**2-3 horas** (trabajo asíncrono entre sesiones)

## Prerequisitos

- ✅ Sesión 1.1 completada
- ✅ Copilot configurado y funcionando en VS Code / IntelliJ IDEA
- ✅ Conocimiento básico de Java y OSGi
- ✅ Proyecto `vass-university-dummyproject` compilando localmente (`mvn clean install`)

## Contexto del Ejercicio

Tienes un servicio OSGi legacy que funciona (parcialmente) pero tiene varios problemas
propios de código Java/AEM heredado:

- Nombres de métodos y variables sin semántica (`calc`, `getI`, `proc`, `x`, `y`, `z`)
- `@Component` registrado como `Object.class` en lugar de la interfaz correcta
- Bug silencioso de división por cero: en Java `double / 0` retorna `Infinity`, no lanza excepción
- Magic strings (`"A"`, `"B"`, `"C"`) en lugar de un `enum`
- Sin interfaz OSGi definida
- Sin Javadoc
- Lógica de negocio mezclada con acceso a configuración del sistema
- Sin ciclo de vida OSGi (`@Activate` / `@Deactivate`)

Tu tarea es refactorizarlo usando Copilot de forma consciente, mejorando legibilidad,
mantenibilidad y calidad según los estándares del proyecto.

---

## Servicio Legacy Original

```java
// archivo: impl/LegacyPriceCalculatorServiceImpl.java

@Component(service = Object.class, immediate = true)
public class LegacyPriceCalculatorServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(LegacyPriceCalculatorServiceImpl.class);

    public Double calc(double a, double b, double c, double d) {
        double x = a * b;
        double y;
        try {
            y = c / d;
        } catch (Exception e) {
            return null;
        }
        if (d == 0) {
            return null;
        }
        double z = (x + y) * 2;
        if (z < 0) {
            z = Math.abs(z);
        }
        double result = z / 2;
        return Math.round(result * 100.0) / 100.0;
    }

    public String getI(String p, String k) {
        if (p == null || k == null) return "";
        try {
            Object r = getData(p, k);
            if (r != null) {
                return r.toString();
            }
        } catch (Exception e) {
            LOG.error("err", e);
        }
        return "";
    }

    private Object getData(String p, String k) {
        return System.getProperty(p + "." + k);
    }

    public boolean proc(String type, double val, String cfg) {
        if (type == null) return false;
        boolean r = false;
        if (type.equals("A")) {
            if (val > 0 && val < 1000) { r = true; }
        } else if (type.equals("B")) {
            if (val > 100) { r = true; }
        } else if (type.equals("C")) {
            if (cfg != null && !cfg.isEmpty() && val >= 0) { r = true; }
        }
        return r;
    }
}
```

> **Nota:** Este servicio se usa así en el código llamante:
> ```java
> LegacyPriceCalculatorServiceImpl svc = new LegacyPriceCalculatorServiceImpl();
> Double result = svc.calc(10, 5, 20, 4);  // Debería retornar 52.5
> ```

---

## Instrucciones Paso a Paso

### Paso 1: Análisis y Comprensión (15 min)

**Tareas:**

1. Lee el código legacy cuidadosamente
2. Identifica qué hace realmente cada método. Ejecuta mentalmente o en una clase `main` temporal:

```java
calc(10, 5, 20, 4)    // ¿Qué retorna?
calc(2, 3, 10, 2)     // ¿Qué retorna?
calc(10, 5, 20, 0)    // ¿Qué retorna? — OJO: en Java double/0 = Infinity, no excepción
calc(-10, 5, 20, 4)   // ¿Qué retorna?
proc("A", 500, null)  // ¿Qué retorna?
proc("C", -1, "cfg")  // ¿Qué retorna?
```

3. Identifica y documenta los problemas:
    - ¿Qué hace `calc()` realmente? ¿Hay algún bug?
    - ¿Por qué el `try/catch` de `calc()` nunca se dispara en Java para `double`?
    - ¿Qué problema tiene `@Component(service = Object.class)`?
    - ¿Por qué las comparaciones `type.equals("A")` son un problema de mantenibilidad?

**Output esperado:**
- Comentarios en el propio código legacy o un `reflexion_lab_1.1.md` inicial
- Lista de bugs reales encontrados
- Lista de casos edge no manejados

---

### Paso 2: Diseño del Prompt CLEAR (20 min)

**Tareas:**

Usa la técnica **CLEAR** aprendida en la sesión 1.2 para diseñar prompts completos para cada artefacto:

- **C**ontext: ¿Qué problema resuelve este servicio? ¿Dónde encaja en AEM?
- **L**anguage: Java 11, OSGi DS annotations, Javadoc, SLF4J, Google Java Style
- **E**xamples: Ejemplos de uso con inputs y outputs esperados
- **A**ssumptions: ¿Qué asumimos sobre los inputs? ¿El servicio es stateless?
- **R**esults: ¿Qué retorna? ¿Qué excepciones lanza? ¿Qué registra en el `@Component`?

**Crea dos archivos nuevos:**
- .../lab1_1RefactoringCopilot/PriceCalculatorService.java
- .../lab1_1RefactoringCopilot/impl/PriceCalculatorServiceImpl.java


**Escribe el prompt CLEAR completo como Javadoc en la interfaz:**

```java
/**
 * [AQUÍ VA TU PROMPT CLEAR COMPLETO]
 * Incluye contexto AEM, ejemplos de cálculo, supuestos sobre inputs,
 * excepciones esperadas y convenciones OSGi del proyecto.
 */
public interface PriceCalculatorService {
    // Deja que Copilot sugiera los métodos basándose en el Javadoc anterior
}
```

---

### Paso 3: Refactorización con Copilot (30 min)

**Tareas:**

1. Con el prompt CLEAR escrito, deja que Copilot sugiera la implementación
2. **NO aceptes la primera sugerencia sin revisar**
3. Si la sugerencia no es óptima, refina el prompt y vuelve a intentar
4. Itera hasta obtener código de calidad

**Criterios de calidad para Java/AEM:**

- ✅ `@Component(service = PriceCalculatorService.class)` correcto
- ✅ Interfaz OSGi definida por separado en el mismo paquete
- ✅ Nombres de métodos y variables descriptivos en camelCase
- ✅ Javadoc completo en la interfaz
- ✅ División por cero gestionada con `IllegalArgumentException`
- ✅ Magic strings reemplazados por `enum PriceType { STANDARD, PREMIUM, CUSTOM }`
- ✅ SLF4J logging correcto (sin `System.out.println`, sin `e.printStackTrace()`)
- ✅ Separación de responsabilidades (cálculo separado de acceso a configuración)
- ✅ `@Activate` si hay inicialización necesaria

**Ejemplo de estructura esperada:**

```java
@Component(service = PriceCalculatorService.class, immediate = true)
public class PriceCalculatorServiceImpl implements PriceCalculatorService {

    private static final Logger LOG = LoggerFactory.getLogger(PriceCalculatorServiceImpl.class);

    @Override
    public double calculateFinalPrice(
            double baseValue,
            double multiplier,
            double dividend,
            double divisor) {
        // Implementación refactorizada generada con Copilot
    }

    @Override
    public boolean isPriceValidForType(PriceType priceType, double price, String configuration) {
        // Implementación refactorizada generada con Copilot
    }
}
```

---

### Paso 4: Tests Unitarios con AEM Mocks (30 min)

**Tareas:**

1. Crea tests unitarios para validar que el servicio refactorizado es funcionalmente equivalente al legacy
2. Usa Copilot para generar los tests (con prompt CLEAR apropiado)
3. Ejecuta los tests con `mvn test` y verifica que pasen
4. Prueba casos edge específicos de Java:
    - Divisor cero
    - `Double.MAX_VALUE` como input
    - `Double.NaN` como input
    - `Double.POSITIVE_INFINITY` como input
    - Precisión de punto flotante (usa `assertEquals(expected, actual, 0.001)`)
    - Todos los valores de `PriceType`

**Crea el archivo:**
- core/src/test/java/com/vasscompany/dummyproject/core/services/lab1_1RefactoringCopilot/impl/PriceCalculatorServiceImplTest.java


**Prompt CLEAR para los tests:**

```java
/**
 * [AQUÍ VA TU PROMPT CLEAR PARA LOS TESTS]
 * Incluye: contexto AEM Mocks (io.wcm.testing.mock.aem.junit5.AemContext),
 * JUnit 5 (@Test, @DisplayName, @ParameterizedTest),
 * los casos a cubrir y el comportamiento esperado ante excepciones.
 */
```

**Estructura sugerida:**

```java
@ExtendWith(AemContextExtension.class)
class PriceCalculatorServiceImplTest {

    private final AemContext context = new AemContext();
    private PriceCalculatorService service;

    @BeforeEach
    void setUp() {
        context.registerInjectActivateService(new PriceCalculatorServiceImpl());
        service = context.getService(PriceCalculatorService.class);
    }

    @Test
    @DisplayName("Caso básico: calculateFinalPrice(10, 5, 20, 4) debe retornar 52.5")
    void testCasoBasico() { ... }

    @Test
    @DisplayName("Divisor cero debe lanzar IllegalArgumentException")
    void testDivisorCero() { ... }

    @Test
    @DisplayName("Resultado negativo debe retornar valor absoluto")
    void testResultadoNegativo() { ... }

    @ParameterizedTest
    @DisplayName("Validación de tipos de precio")
    @MethodSource("providerTiposPrecio")
    void testValidacionTiposPrecio(PriceType tipo, double precio, String config, boolean esperado) { ... }

    // Agrega más tests según encuentres casos edge
}
```

---

### Paso 5: Comparación y Reflexión (20 min)

**Tareas:**

1. Compara el servicio legacy con el refactorizado
2. Mide las mejoras:

| Aspecto | Legacy | Refactorizado |
|---------|--------|---------------|
| Nombre del método principal | `calc()` | `calculateFinalPrice()` |
| Registro OSGi | `Object.class` (incorrecto) | `PriceCalculatorService.class` |
| División por cero | Bug silencioso (`Infinity`) | `IllegalArgumentException` |
| Tipos de proceso | Magic strings `"A"`, `"B"`, `"C"` | `enum PriceType` |
| Documentación | Ninguna | Javadoc completo |
| Separación de responsabilidades | Mezcladas | Separadas |
| Testabilidad | Sin interfaz → difícil de mockear | Interfaz + AEM Mocks |

3. Reflexiona sobre el proceso con Copilot:
    - ¿Cuántas iteraciones del prompt necesitaste?
    - ¿Qué parte sugirió mejor Copilot: la interfaz, la implementación o los tests?
    - ¿Qué tuviste que corregir manualmente?
    - ¿Qué diferencias encontraste respecto a usar Copilot con Python?
    - ¿Cuánto tiempo ahorraste vs. hacerlo manualmente?

**Crea el archivo:** `reflexion_lab_1.1.md` en este mismo directorio con tus observaciones.

---

### Paso 6: Auditoría de Seguridad (15 min)

Aplica el checklist de seguridad adaptado a AEM/Java:

- [ ] ¿El servicio usa `getAdministrativeResourceResolver()`? (No debería)
- [ ] ¿Hay credenciales o rutas hardcodeadas? (Revisar `getData()` del legacy)
- [ ] ¿Se expone información sensible en logs? (El `LOG.error("err", e)` del legacy es insuficiente)
- [ ] ¿Se usa Service User en lugar de admin para acceso al JCR? (Si aplica)
- [ ] ¿El `@Component` expone solo la interfaz necesaria y no `Object.class`?
- [ ] ¿Las excepciones se gestionan adecuadamente sin swallowing silencioso?
- [ ] ¿Los inputs se validan antes de usarse en operaciones sensibles?

**Si encuentras problemas, documéntalos y corrígelos.**

---

## Entregables

Al finalizar el lab, este paquete debe contener:

- ✅ `PriceCalculatorService.java` - Interfaz con Javadoc y prompt CLEAR
- ✅ `PriceCalculatorServiceImpl.java` - Implementación refactorizada con Copilot
- ✅ `PriceCalculatorServiceImplTest.java` - Tests unitarios completos con AEM Mocks
- ✅ `reflexion_lab_1.1.md` - Reflexión sobre el proceso de refactorización con Copilot
- ✅ Documentación de auditoría de seguridad (en `reflexion_lab_1.1.md` o un archivo separado)
