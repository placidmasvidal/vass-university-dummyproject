# Lab 1.1: Refactorización de Función Legacy con Copilot

## Objetivo

Refactorizar una función legacy usando GitHub Copilot de forma consciente, aplicando principios de uso estratégico y técnicas de contexto priming.

## Duración Estimada

**2-3 horas** (trabajo asíncrono entre sesiones)

## Prerequisitos

- ✅ Sesión 1.1 completada
- ✅ Copilot configurado y funcionando en VS Code
- ✅ Conocimiento básico de Python

## Contexto del Ejercicio

Tienes una función legacy que funciona pero tiene varios problemas:
- Código difícil de leer y mantener
- Sin documentación
- Nombres de variables poco descriptivos
- Lógica compleja sin explicación
- Sin manejo de errores adecuado

Tu tarea es refactorizarla usando Copilot de forma consciente, mejorando legibilidad, mantenibilidad y calidad.

---

## Función Legacy Original

```python
# archivo: legacy_calculator.py
def calc(a, b, c, d):
    x = a * b
    y = c / d
    if y == 0:
        return None
    z = (x + y) * 2
    if z < 0:
        z = abs(z)
    result = z / 2
    return round(result, 2)

# Esta función se usa así:
# result = calc(10, 5, 20, 4)  # Debería retornar algo
```

---

## Instrucciones Paso a Paso

### Paso 1: Análisis y Comprensión (15 min)

**Tarea:**
1. Lee la función legacy cuidadosamente
2. Identifica qué hace realmente la función (intenta ejecutarla con diferentes valores)
3. Identifica problemas:
   - ¿Qué hace la función realmente?
   - ¿Qué problemas de legibilidad tiene?
   - ¿Qué problemas de mantenibilidad tiene?
   - ¿Qué casos edge no maneja?

**Output esperado:**
- Documento breve (o comentarios) explicando qué hace la función
- Lista de problemas identificados
- Casos de prueba que te ayudarán a validar la refactorización

### Paso 2: Diseño del Prompt CLEAR (20 min)

**Tarea:**
Usa la técnica CLEAR aprendida en la sesión 1.2 para diseñar un prompt completo:

- **C**ontext: ¿Qué problema resuelve esta función?
- **L**anguage: Python, tipo hints, PEP 8
- **E**xamples: Ejemplos de uso con inputs y outputs esperados
- **A**ssumptions: ¿Qué asumimos sobre los inputs?
- **R**esults: ¿Qué retorna? ¿Qué formato?

**Crea un archivo nuevo:** `refactored_calculator.py`

**Escribe el prompt completo como comentario:**
```python
# [AQUÍ VA TU PROMPT CLEAR COMPLETO]
# Incluye contexto, ejemplos, supuestos, resultados esperados
```

### Paso 3: Refactorización con Copilot (30 min)

**Tarea:**
1. Con el prompt CLEAR escrito, deja que Copilot sugiera la implementación
2. **NO aceptes la primera sugerencia sin revisar**
3. Si la sugerencia no es óptima, refina el prompt y vuelve a intentar
4. Itera hasta obtener código de calidad

**Criterios de calidad:**
- ✅ Nombres descriptivos de variables y función
- ✅ Documentación clara (docstring)
- ✅ Type hints incluidos
- ✅ Manejo de errores apropiado
- ✅ Lógica clara y fácil de seguir
- ✅ Casos edge manejados

**Ejemplo de estructura esperada:**
```python
def calcular_resultado_final(
    valor_base: float,
    multiplicador: float,
    dividendo: float,
    divisor: float
) -> float:
    """
    [Docstring generado por Copilot basado en tu prompt]
    """
    # Implementación refactorizada
    ...
```

### Paso 4: Validación y Tests (30 min)

**Tarea:**
1. Crea tests unitarios para validar que la función refactorizada hace lo mismo que la original
2. Usa Copilot para generar los tests (con prompt apropiado)
3. Ejecuta los tests y verifica que pasen
4. Prueba casos edge:
   - Divisor cero
   - Números negativos
   - Valores muy grandes/pequeños
   - Tipos incorrectos (si aplica)

**Crea archivo:** `test_refactored_calculator.py`

**Estructura sugerida:**
```python
import pytest
from refactored_calculator import calcular_resultado_final

def test_caso_basico():
    # Test del caso básico
    ...

def test_divisor_cero():
    # Test de caso edge: divisor cero
    ...

def test_valores_negativos():
    # Test con valores negativos
    ...

# Agrega más tests según encuentres casos edge
```

### Paso 5: Comparación y Reflexión (20 min)

**Tarea:**
1. Compara la función original con la refactorizada
2. Mide mejoras:
   - Líneas de código (¿más o menos? ¿por qué?)
   - Legibilidad (¿más fácil de entender?)
   - Mantenibilidad (¿más fácil de modificar?)
   - Robustez (¿maneja más casos edge?)

3. Reflexiona sobre el proceso:
   - ¿Cómo ayudó Copilot?
   - ¿Qué técnicas funcionaron mejor?
   - ¿Qué harías diferente?
   - ¿Cuánto tiempo ahorraste vs. hacerlo manualmente?

**Crea archivo:** `reflexion_lab_1.1.md` con tus observaciones

### Paso 6: Auditoría de Seguridad (15 min)

**Tarea:**
Usa el checklist de seguridad aprendido en sesión 1.3:

- [ ] ¿El código contiene datos sensibles? (No debería)
- [ ] ¿Usa secretos hardcoded? (No debería)
- [ ] ¿Expone información confidencial? (No debería)
- [ ] ¿Sigue políticas de seguridad? (Sí debería)
- [ ] ¿Pasa validaciones de seguridad? (Sí debería)

**Si encuentras problemas, documenta y corrige.**

---

## Entregables

Al finalizar el lab, debes entregar:

1. ✅ `refactored_calculator.py` - Función refactorizada con prompt CLEAR
2. ✅ `test_refactored_calculator.py` - Tests unitarios completos
3. ✅ `reflexion_lab_1.1.md` - Reflexión sobre el proceso
4. ✅ Screenshots o notas del proceso (prompts usados, iteraciones)

---

## Criterios de Aceptación

El lab se considera completo cuando:

- [ ] La función refactorizada es funcionalmente equivalente a la original
- [ ] Todos los tests pasan (incluyendo casos edge)
- [ ] El código tiene documentación clara (docstring)
- [ ] Los nombres de variables/función son descriptivos
- [ ] Se aplicó técnica CLEAR en el prompt
- [ ] Se realizó auditoría de seguridad
- [ ] La reflexión documenta aprendizajes claros
- [ ] El código sigue PEP 8 (verificar con linter)

---

## Evaluación

### Rúbrica de Evaluación

| Criterio | Excelente (4) | Bueno (3) | Satisfactorio (2) | Necesita Mejora (1) |
|----------|---------------|-----------|-------------------|---------------------|
| **Prompt CLEAR** | Prompt completo con todos los elementos, muy descriptivo | Prompt bueno con mayoría de elementos | Prompt básico con algunos elementos | Prompt incompleto o vago |
| **Calidad Código** | Código excepcional: legible, robusto, bien documentado | Código bueno con mejoras claras | Código funcional con mejoras básicas | Código similar a original, pocas mejoras |
| **Tests** | Suite completa de tests, casos edge cubiertos | Tests buenos con algunos casos edge | Tests básicos funcionales | Tests incompletos o faltantes |
| **Uso Consciente de Copilot** | Uso estratégico, múltiples iteraciones, refinamiento | Uso bueno con algunas iteraciones | Uso básico, aceptó sugerencias | Uso pasivo, aceptó sin revisar |
| **Reflexión** | Reflexión profunda con aprendizajes claros y acción | Reflexión buena con algunos aprendizajes | Reflexión básica | Reflexión superficial o faltante |

**Puntuación mínima para aprobar: 12/20 (60%)**

---

## Tips y Ayuda

### Si te quedas atascado:

1. **Copilot no sugiere bien:**
   - Refina tu prompt, agrega más contexto
   - Escribe más código alrededor para dar contexto
   - Intenta descomponer el problema en partes más pequeñas

2. **No entiendes la función original:**
   - Ejecútala con diferentes valores y observa resultados
   - Agrega prints para ver valores intermedios
   - Pregunta a compañeros o facilitador

3. **Tests fallan:**
   - Verifica que entendiste correctamente qué hace la función original
   - Compara outputs de función original vs. refactorizada
   - Revisa casos edge que no consideraste

### Recursos Adicionales

- Revisa sesión 1.2 sobre prompting efectivo
- Consulta `promptpacks/python/basico/fundamentos.md`
- Revisa `checklists/seguridad-ia.md` para auditoría

---

## Extensión Opcional (Bonus)

Si completas el lab antes del tiempo estimado, intenta:

1. **Refactorizar a clase**: Convierte la función en una clase con métodos
2. **Múltiples implementaciones**: Pide a Copilot diferentes enfoques y compara
3. **Optimización**: Pide a Copilot optimizar el código para performance
4. **Documentación extendida**: Genera documentación Sphinx completa

---

**Versión**: 1.0  
