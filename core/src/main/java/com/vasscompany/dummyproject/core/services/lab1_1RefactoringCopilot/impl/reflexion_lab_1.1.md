# Reflexión inicial — Lab 1.1 Refactorización de Servicio Legacy con Copilot

## 1. Objetivo del análisis

El objetivo de esta fase ha sido comprender el comportamiento real del servicio legacy `LegacyPriceCalculatorServiceImpl` antes de iniciar su refactorización. El análisis se ha centrado en identificar la lógica efectiva de sus métodos, detectar defectos reales de implementación y localizar problemas de diseño, mantenibilidad y robustez propios de un servicio Java/AEM heredado.

## 2. Comportamiento observado del servicio legacy

### 2.1 Método `calc(double a, double b, double c, double d)`

Tras revisar la implementación, se observa que el método realiza internamente las siguientes operaciones:

1. Multiplica `a * b`
2. Divide `c / d`
3. Si `d == 0`, retorna `null`
4. Suma ambos resultados
5. Multiplica el valor intermedio por `2`
6. Si el resultado es negativo, aplica `Math.abs(...)`
7. Divide posteriormente entre `2`
8. Redondea a dos decimales

Desde el punto de vista algebraico, la multiplicación por `2` y la posterior división entre `2` son redundantes, por lo que la operación real puede simplificarse a:

`resultado = valor absoluto de ((a * b) + (c / d))`, redondeado a dos decimales.

### 2.2 Resultados obtenidos en los casos de prueba solicitados

- `calc(10, 5, 20, 4)` → `55.0`
- `calc(2, 3, 10, 2)` → `11.0`
- `calc(10, 5, 20, 0)` → `null`
- `calc(-10, 5, 20, 4)` → `45.0`
- `proc("A", 500, null)` → `true`
- `proc("C", -1, "cfg")` → `false`

## 3. Problemas identificados

### 3.1 Problemas funcionales en `calc()`

El método presenta una discrepancia entre el comportamiento esperado y el comportamiento real. En la documentación del laboratorio se indica que el caso `calc(10, 5, 20, 4)` “debería retornar 52.5”, pero la implementación actual devuelve `55.0`. Esto evidencia una inconsistencia entre la intención del servicio y su resultado efectivo.

Asimismo, la lógica interna resulta innecesariamente compleja, ya que introduce operaciones redundantes que dificultan la comprensión del cálculo sin aportar valor funcional.

### 3.2 Problema en el control de división por cero

El método contiene un bloque `try/catch` alrededor de la operación `c / d`. Sin embargo, dicho control es técnicamente incorrecto para este caso, ya que en Java la división de valores `double` entre cero no lanza una excepción, sino que produce valores especiales como `Infinity` o `NaN`.

Por tanto, el `try/catch` nunca actúa como mecanismo real de protección frente a divisor cero. La validación correcta debería realizarse antes de la división.

### 3.3 Problema de diseño OSGi en `@Component(service = Object.class)`

La anotación `@Component(service = Object.class, immediate = true)` constituye un problema de diseño porque registra el componente OSGi bajo el tipo genérico `Object` en lugar de hacerlo bajo una interfaz de servicio específica.

Desde el punto de vista técnico, esto reduce la claridad del contrato del componente, empeora su expresividad arquitectónica y dificulta su consumo correcto mediante inyección OSGi con `@Reference`. En un proyecto AEM/OSGi, el patrón adecuado consiste en definir una interfaz de servicio y registrar la implementación con esa interfaz como tipo de servicio expuesto.

### 3.4 Problema de mantenibilidad en `proc(String type, ...)`

El método `proc()` basa su comportamiento en comparaciones literales contra `"A"`, `"B"` y `"C"`. Este enfoque introduce *magic strings*, lo que constituye un problema de mantenibilidad por varias razones:

- no existe seguridad de tipos en compilación;
- un error tipográfico no se detecta hasta runtime;
- los valores válidos del dominio no quedan modelados de forma explícita;
- la evolución futura del código obliga a modificar comparaciones literales dispersas.

La solución más adecuada sería modelar estos valores mediante un `enum`, por ejemplo `PriceType`.

## 4. Bugs reales encontrados

1. Inconsistencia entre el resultado documentado y el resultado real de `calc()`.
2. Uso incorrecto de `try/catch` como supuesto control de división por cero con `double`.
3. Validación del divisor realizada después de ejecutar la división.
4. Lógica aritmética redundante en `calc()` que dificulta la lectura.
5. Registro OSGi incorrecto del servicio como `Object.class`.
6. Uso de *magic strings* en `proc()` en lugar de un tipo fuerte.
7. Nombres de métodos y variables poco semánticos (`calc`, `getI`, `proc`, `x`, `y`, `z`).

## 5. Casos edge no manejados

Durante el análisis también se han identificado casos límite que el servicio no trata de forma explícita:

- entrada con `Double.NaN`;
- entrada con `Double.POSITIVE_INFINITY` o `Double.NEGATIVE_INFINITY`;
- valores extremos de tipo `double`;
- tipos inválidos o en minúscula en `proc()`;
- ausencia de diferenciación entre “dato no encontrado” y “error real” en `getI()`;
- dependencia directa de `System.getProperty(...)` dentro de la lógica del servicio, lo que reduce la testabilidad y mezcla responsabilidades.

## 6. Conclusión

El servicio legacy analizado funciona parcialmente, pero presenta problemas relevantes de claridad, diseño, mantenibilidad y robustez. En particular, destaca la falta de un contrato OSGi adecuado, la presencia de lógica confusa en el cálculo principal, el uso de cadenas mágicas y una validación técnicamente incorrecta de la división por cero.

Como conclusión, el servicio requiere una refactorización estructural que incluya, al menos, la definición de una interfaz de servicio, la simplificación del cálculo principal, la validación explícita de inputs, la sustitución de *magic strings* por un `enum` y una mejor separación entre lógica de negocio y acceso a configuración.
