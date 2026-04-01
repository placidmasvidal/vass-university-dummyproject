# Reflexión final — Lab 1.1 Refactorización de Servicio Legacy con Copilot

## 1. Objetivo del laboratorio

El objetivo del laboratorio ha sido refactorizar un servicio OSGi legacy en un proyecto AEM/Java usando GitHub Copilot de forma consciente, mejorando legibilidad, mantenibilidad, diseño OSGi, validación de inputs y testabilidad. El punto de llegada esperado por el laboratorio incluye una interfaz separada, un `@Component(service = PriceCalculatorService.class)`, nombres descriptivos, sustitución de *magic strings* por `enum PriceType` y tests unitarios con AEM Mocks.

## 2. Situación inicial del código legacy

El servicio legacy presentaba varios problemas relevantes:

- `@Component(service = Object.class)` en lugar de registrar el servicio bajo una interfaz explícita.
- Métodos y variables sin semántica (`calc`, `proc`, `getI`, `x`, `y`, `z`).
- Manejo incorrecto de la división por cero en `double`.
- Uso de *magic strings* (`"A"`, `"B"`, `"C"`) en vez de un tipo fuerte.
- Mezcla de lógica de negocio con acceso a configuración del sistema.
- Logging poco expresivo y manejo silencioso de errores.

Estos problemas estaban alineados con la descripción inicial del README del laboratorio y con los anti-patrones señalados en el propio servicio legacy.

## 3. Principales dificultades encontradas con Copilot

### 3.1 Contexto demasiado contaminado por el legacy

El primer error importante fue introducir el prompt CLEAR directamente dentro del archivo `LegacyPriceCalculatorServiceImpl.java`. Esto aumentó mucho el sesgo de autocompletado local: Copilot tendía a continuar el código cercano en vez de proponer un rediseño real.

En retrospectiva, fue una mala decisión dejar el prompting dentro del archivo legacy, porque el contexto local pesó más que la intención global del prompt.

### 3.2 Contrato funcional subespecificado

El laboratorio exige que `calculateFinalPrice(10, 5, 20, 4)` retorne `52.5`, pero no define de forma inequívoca la fórmula matemática completa. Esto deja demasiados grados de libertad: Copilot puede generar implementaciones que “parecen razonables”, pero no satisfacen el contrato esperado.

Además, apareció una contradicción relevante entre:
- el comportamiento real del legacy, que devuelve `55.0`,
- y el ejemplo esperado del laboratorio, que exige `52.5`.

Esto dificultó decidir cuándo una sugerencia de Copilot era realmente correcta.

### 3.3 Tensión entre instrucciones

En varios prompts se dio una tensión clara entre dos órdenes simultáneas:

- “No reutilices la fórmula defectuosa del legacy”.
- “Genera la implementación en un contexto donde el legacy sigue visible y domina el archivo”.

En autocompletado, el contexto local tuvo más peso que la instrucción abstracta. Como resultado, Copilot tendió varias veces a conservar la forma del legacy aunque mejorara validaciones.

### 3.4 Prompts demasiado largos y meta-instruccionales

Otra dificultad fue usar prompts largos, explicativos y muy “meta”. En la práctica, para autocompletado dentro del editor funcionaron mejor instrucciones cortas, directas y pegadas al punto exacto donde se quería generar código.

## 4. Qué funcionó mejor

### 4.1 Estrategia contract-first

El mejor avance llegó cuando se cambió de enfoque:

1. primero interfaz,
2. después `enum PriceType`,
3. después tests,
4. y solo después implementación.

Ese orden redujo la ambigüedad y dio a Copilot anclas más fuertes que el archivo legacy.

### 4.2 Interfaz y nombres descriptivos

Copilot respondió razonablemente bien cuando ya existían:
- `PriceCalculatorService`,
- `PriceType`,
- `@Component(service = PriceCalculatorService.class)`,
- y nombres de método más expresivos como `calculateFinalPrice(...)` e `isPriceValidForType(...)`.

### 4.3 Tests como ancla fuerte

El test del caso básico (`10, 5, 20, 4 -> 52.5`) fue el mejor mecanismo para detectar implementaciones incorrectas. Una vez convertido el ejemplo del README en un test ejecutable, las propuestas erráticas de Copilot dejaron de ser opinables y pasaron a ser verificables.

## 5. Qué se habría podido hacer mejor desde el principio

### 5.1 Sacar el prompting del archivo legacy

La generación habría sido más limpia si el prompting se hubiese colocado únicamente en:
- `PriceCalculatorService.java`
- `PriceCalculatorServiceImpl.java`

y no dentro del legacy.

### 5.2 Definir explícitamente la fórmula de negocio

En lugar de dar solo un ejemplo esperado, habría sido mejor fijar la regla de negocio como fórmula o pseudofórmula inequívoca. Eso habría reducido iteraciones y evitado que Copilot “inventase” semánticas como impuestos o descuentos.

### 5.3 Trocear la generación

Funcionó mejor pedir las piezas por separado:
- primero firma,
- luego validaciones,
- luego cálculo,
- luego `switch` de `PriceType`,
- y finalmente tests.

Pedir “todo el servicio completo” de una sola vez generó más ruido.

### 5.4 Añadir restricciones negativas explícitas

También ayudó formular “no hacer” concretos:
- no usar `calc/proc/getI`,
- no usar `String type`,
- no usar `Object.class`,
- no devolver `null`,
- no mezclar acceso a configuración con cálculo.

## 6. Diferencias observadas entre interfaz, implementación y tests

### Interfaz
Fue la parte que mejor sugirió Copilot una vez existía el Javadoc CLEAR y se pidió una firma moderna.

### Implementación
Fue la parte más problemática. Copilot tendía a:
- copiar o reformular la lógica legacy,
- o inventar fórmulas no justificadas por el contrato.

### Tests
Los tests ayudaron mucho, pero llegaron algo tarde. Cuando se introdujeron, mejoraron claramente la calidad del proceso porque permitieron validar objetivamente cada intento.

## 7. Aprendizajes sobre uso de Copilot en Java/AEM

En este laboratorio, Copilot funcionó mejor cuando se usó como asistente de:
- estructura,
- renombrado,
- esqueletos de interfaz,
- generación inicial de tests,
- y refactorizaciones guiadas.

Funcionó peor cuando se le dejó deducir lógica de negocio subespecificada.

En Java/AEM, donde importan mucho:
- contratos OSGi,
- separación por capas,
- patrones de servicio,
- y exactitud en nombres y tipos,

parece especialmente importante fijar primero el contrato y después la implementación.

## 8. Observaciones sobre AEM Mocks y testabilidad

Aunque el laboratorio recomienda AEM Mocks para los tests unitarios, en este caso concreto el servicio refactorizado era un servicio Java puro, sin dependencias reales de AEM en su lógica. Por eso, para depurar el comportamiento del cálculo era más simple usar primero instanciación directa y dejar AEM Mocks para validar el encaje OSGi.

Esto sugiere una lección práctica: no siempre conviene introducir toda la infraestructura de test desde el primer minuto si el problema principal aún está en la lógica de negocio.

## 9. Comparación legacy vs refactorizado

| Aspecto | Legacy | Refactorizado |
|---------|--------|---------------|
| Método principal | `calc()` | `calculateFinalPrice()` |
| Registro OSGi | `Object.class` | `PriceCalculatorService.class` |
| División por cero | Retorno `null` y manejo defectuoso | `IllegalArgumentException` |
| Tipos funcionales | `"A"`, `"B"`, `"C"` | `enum PriceType` |
| Documentación | Inexistente | Javadoc + contrato |
| Responsabilidades | Mezcladas | Más separadas |
| Testabilidad | Baja | Mejor, con interfaz y tests |

## 10. Mini auditoría de seguridad

Durante el proceso se identificaron y corrigieron o documentaron varios puntos de seguridad y robustez:

- El servicio legacy exponía el componente como `Object.class`, lo cual es una mala práctica de diseño OSGi.
- El método `getI()` mezclaba lógica de negocio con acceso a `System.getProperty(...)`.
- El legacy ocultaba errores devolviendo `""` o `null`, dificultando el diagnóstico.
- El logging `LOG.error("err", e)` era demasiado pobre.
- El refactor introdujo validación explícita de inputs (`NaN`, infinitos, divisor cero).

No se detectó en este ejercicio uso de `getAdministrativeResourceResolver()`, credenciales embebidas ni acceso privilegiado al JCR, pero sí varios indicadores de código heredado poco robusto.

## 11. Conclusión final

La principal lección de este laboratorio es que Copilot no sustituye la definición precisa del contrato. Cuando la lógica de negocio está clara, ayuda bastante a acelerar estructura, nombres, esqueletos y tests. Cuando el contrato es ambiguo, tiende a completar de forma plausible pero no necesariamente correcta.

El proceso mejoró claramente cuando se pasó a un enfoque **contract-first, code-second**:
- interfaz primero,
- tests después,
- implementación al final.

Como conclusión general, en un proyecto Java/AEM con OSGi conviene usar Copilot como apoyo para refactorizar conscientemente, pero manteniendo siempre el control humano sobre:
- el contrato,
- la arquitectura,
- la semántica de negocio,
- y la validación mediante tests.
