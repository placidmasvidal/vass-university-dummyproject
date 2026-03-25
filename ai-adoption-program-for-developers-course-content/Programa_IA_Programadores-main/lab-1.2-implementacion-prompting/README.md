# Lab 1.2: Implementación de Feature Nueva con Prompting Consciente

## Objetivo

Implementar una feature nueva desde cero usando GitHub Copilot con técnicas de prompting efectivo aprendidas, demostrando uso consciente y estratégico.

## Duración Estimada

**3-4 horas** (trabajo asíncrono entre sesiones)

## Prerequisitos

- ✅ Sesión 1.2 completada
- ✅ Lab 1.1 completado (o al menos iniciado)
- ✅ Dominio de principios CLEAR
- ✅ Conocimiento de Python básico-intermedio

## Contexto del Ejercicio

Eres parte de un equipo desarrollando un sistema de gestión de pedidos para un e-commerce. Necesitas implementar un nuevo módulo de cálculo de envío que:

1. Calcula el costo de envío basado en distancia y peso
2. Aplica descuentos según tipo de cliente
3. Calcula tiempo estimado de entrega
4. Valida restricciones de envío

Debes implementar esto desde cero usando Copilot con prompting consciente.

---

## Requisitos de la Feature

### Funcionalidad Principal

**Módulo: `shipping_calculator.py`**

Debe contener las siguientes funciones:

1. **`calcular_costo_envio(distancia_km, peso_kg, tipo_cliente)`**
   - Calcula costo base según distancia y peso
   - Aplica descuentos según tipo de cliente
   - Retorna costo final en pesos chilenos

2. **`calcular_tiempo_entrega(distancia_km, tipo_envio)`**
   - Calcula días hábiles estimados
   - Considera diferentes tipos de envío
   - Retorna número de días

3. **`validar_envio(peso_kg, dimensiones_cm, destino)`**
   - Valida restricciones de peso y dimensiones
   - Valida si se puede enviar al destino
   - Retorna (es_valido, mensaje_error)

### Reglas de Negocio

**Costo de Envío:**
- Base: $1000 por cada 100km
- Peso: +$500 por cada kg adicional después del primer kg
- Descuentos por tipo cliente:
  - Premium: 20% descuento
  - Regular: 10% descuento
  - Nuevo: Sin descuento
- Mínimo: $2000 (costo nunca menor a esto)

**Tiempo de Entrega:**
- Estándar: 1 día por cada 100km (mínimo 2 días)
- Express: 0.5 días por cada 100km (mínimo 1 día)
- Económico: 2 días por cada 100km (mínimo 4 días)
- Solo días hábiles (lunes-viernes)

**Validaciones:**
- Peso máximo: 30kg
- Dimensiones máximas: 150cm x 150cm x 150cm
- Solo envíos dentro de Chile (destino debe ser código válido)

---

## Instrucciones Paso a Paso

### Paso 1: Diseño y Planificación (30 min)

**Tarea:**
Antes de escribir código, planifica:

1. **Estructura del módulo**: ¿Qué funciones necesitas? ¿Qué clases?
2. **Datos de entrada/salida**: ¿Qué parámetros? ¿Qué retornan?
3. **Casos de uso**: Escribe ejemplos concretos de uso
4. **Casos edge**: ¿Qué casos límite hay que manejar?

**Crea archivo:** `planificacion_lab_1.2.md`

**Template sugerido:**
```markdown
# Planificación: Módulo de Cálculo de Envío

## Funciones Principales
1. calcular_costo_envio(...)
   - Inputs: ...
   - Outputs: ...
   - Ejemplos: ...

2. calcular_tiempo_entrega(...)
   ...

3. validar_envio(...)
   ...

## Casos Edge
- Peso 0 o negativo
- Distancia muy grande
- Tipo cliente inválido
- ...

## Estructura de Código
- ¿Usar clases o funciones?
- ¿Qué constantes necesito?
- ¿Qué validaciones hacer?
```

### Paso 2: Implementación con Prompting CLEAR (90 min)

**Tarea:**
Implementa cada función usando técnicas CLEAR:

#### Función 1: `calcular_costo_envio`

**Escribe prompt CLEAR completo antes de implementar:**
```python
# Calcular costo de envío para pedido de e-commerce
#
# Context: Sistema de e-commerce necesita calcular costo de envío
# basado en distancia, peso y tipo de cliente, aplicando descuentos
#
# Language: Python 3.10+, type hints, PEP 8, usar Decimal para dinero
#
# Ejemplos:
#   calcular_costo_envio(500, 2.5, 'premium')
#   -> Distancia 500km, peso 2.5kg, cliente premium
#   -> Base: 5000 (500/100 * 1000)
#   -> Peso extra: 1500 (1.5kg extra * 500)
#   -> Subtotal: 6500
#   -> Descuento 20%: 1300
#   -> Total: 5200
#
#   calcular_costo_envio(50, 0.5, 'nuevo')
#   -> Base: 1000 (mínimo por distancia)
#   -> Peso: 0 (menor a 1kg)
#   -> Subtotal: 1000
#   -> Sin descuento (nuevo)
#   -> Total: 2000 (mínimo costo)
#
# Assumptions:
#   - distancia_km >= 0
#   - peso_kg > 0
#   - tipo_cliente es 'premium', 'regular' o 'nuevo'
#   - Costo mínimo es $2000
#
# Returns:
#   float: Costo final en pesos chilenos, redondeado a 0 decimales
#
# Raises:
#   ValueError: Si tipo_cliente no es válido o valores inválidos
def calcular_costo_envio(distancia_km: float, peso_kg: float, tipo_cliente: str) -> float:
    ...
```

**Ahora deja que Copilot genere la implementación**

**Itera si es necesario:**
- Si el código no es correcto, refina el prompt
- Si falta algo, agrega más detalles
- Si hay errores, corrige y pide regeneración

#### Función 2: `calcular_tiempo_entrega`

**Repite el proceso:**
1. Escribe prompt CLEAR completo
2. Deja que Copilot implemente
3. Revisa y refina
4. Itera hasta obtener código correcto

**Tips:**
- Incluye ejemplos con diferentes tipos de envío
- Considera días hábiles (puedes usar biblioteca `datetime` o simplificar)
- Menciona los mínimos por tipo de envío

#### Función 3: `validar_envio`

**Mismo proceso:**
- Prompt CLEAR con ejemplos
- Implementación con Copilot
- Refinamiento iterativo

### Paso 3: Tests Unitarios Completos (60 min)

**Tarea:**
Crea suite completa de tests usando Copilot:

**Crea archivo:** `test_shipping_calculator.py`

**Usa prompting para generar tests:**
```python
# Generar tests unitarios completos para shipping_calculator
#
# Context: Necesito tests para validar todas las funciones del módulo
# de cálculo de envío
#
# Requirements:
# - Tests para calcular_costo_envio: casos normales, edge cases, errores
# - Tests para calcular_tiempo_entrega: todos los tipos de envío
# - Tests para validar_envio: validaciones de peso, dimensiones, destino
# - Usar pytest
# - Cobertura objetivo: 90%+
#
# Ejemplos de casos a cubrir:
# - Costo con diferentes tipos de cliente
# - Costo mínimo se aplica correctamente
# - Tiempo de entrega con diferentes tipos
# - Validación rechaza peso excesivo
# - Validación rechaza dimensiones excesivas
# - Validación rechaza destino inválido
import pytest
from shipping_calculator import calcular_costo_envio, calcular_tiempo_entrega, validar_envio

# [Deja que Copilot genere los tests]
```

**Ejecuta tests y verifica:**
```bash
pytest test_shipping_calculator.py -v
pytest --cov=shipping_calculator test_shipping_calculator.py
```

### Paso 4: Documentación y Type Hints (30 min)

**Tarea:**
1. Asegúrate que todas las funciones tengan docstrings completos
2. Verifica type hints en todas las funciones
3. Genera documentación de uso del módulo

**Usa Copilot para mejorar documentación:**
```python
# Mejorar docstrings de todas las funciones siguiendo formato Google
# Incluir: descripción, args, returns, raises, ejemplos
```

### Paso 5: Refinamiento y Optimización (30 min)

**Tarea:**
1. Revisa el código generado
2. Identifica mejoras posibles:
   - ¿Hay código duplicado?
   - ¿Se pueden extraer constantes?
   - ¿La lógica es clara?
   - ¿Se pueden mejorar nombres?

3. **Usa Copilot para refactorizar:**
```python
# Refactorizar código para mejorar legibilidad y mantenibilidad
# - Extraer constantes mágicas
# - Mejorar nombres de variables si es necesario
# - Simplificar lógica compleja
# - Asegurar PEP 8 compliance
```

### Paso 6: Auditoría Completa (20 min)

**Checklist de auditoría:**
- [ ] Seguridad: No hay datos sensibles
- [ ] Seguridad: No hay secretos hardcoded
- [ ] Calidad: Lógica correcta
- [ ] Calidad: Manejo de errores adecuado
- [ ] Calidad: Tests pasan todos
- [ ] Calidad: Cobertura >80%
- [ ] Calidad: PEP 8 compliant
- [ ] Política: Sigue estándares del proyecto
- [ ] Documentación: Completa y clara

---

## Entregables

Al finalizar el lab, debes entregar:

1. ✅ `shipping_calculator.py` - Módulo completo implementado
2. ✅ `test_shipping_calculator.py` - Suite completa de tests
3. ✅ `planificacion_lab_1.2.md` - Planificación inicial
4. ✅ Screenshots/notas de prompts usados e iteraciones
5. ✅ Reporte de cobertura de tests

---

## Criterios de Aceptación

El lab se considera completo cuando:

- [ ] Las 3 funciones están implementadas y funcionan correctamente
- [ ] Todos los tests pasan (100% pass rate)
- [ ] Cobertura de tests >80%
- [ ] Código usa type hints correctamente
- [ ] Docstrings completos en todas las funciones
- [ ] Prompts CLEAR documentados para cada función
- [ ] Auditoría de seguridad completada
- [ ] Código sigue PEP 8
- [ ] Funciones manejan casos edge apropiadamente

---

## Evaluación

### Rúbrica de Evaluación

| Criterio | Excelente (4) | Bueno (3) | Satisfactorio (2) | Necesita Mejora (1) |
|----------|---------------|-----------|-------------------|---------------------|
| **Prompts CLEAR** | Prompts excepcionales, muy descriptivos, todos los elementos | Prompts buenos con mayoría de elementos | Prompts básicos con algunos elementos | Prompts incompletos |
| **Implementación** | Código excepcional, robusto, bien estructurado | Código bueno, funcional, bien estructurado | Código funcional con algunas mejoras | Código básico, falta estructura |
| **Tests** | Suite completa, alta cobertura, casos edge | Tests buenos con buena cobertura | Tests básicos funcionales | Tests incompletos |
| **Uso de Copilot** | Uso estratégico excelente, múltiples iteraciones | Uso bueno con iteraciones | Uso básico | Uso pasivo |
| **Calidad General** | Código producción-ready | Código bueno con mejoras menores | Código funcional | Código necesita trabajo |

**Puntuación mínima para aprobar: 12/20 (60%)**

---

## Tips y Ayuda

### Si te quedas atascado:

1. **Copilot no entiende las reglas de negocio:**
   - Agrega más ejemplos numéricos específicos
   - Descompón el problema en pasos más pequeños
   - Escribe pseudocódigo primero y luego pide implementación

2. **Tests fallan:**
   - Verifica que entendiste correctamente los requisitos
   - Revisa casos edge que no consideraste
   - Compara outputs esperados vs. reales

3. **Código muy complejo:**
   - Pide a Copilot que simplifique
   - Descompón en funciones más pequeñas
   - Extrae lógica repetida

### Recursos Adicionales

- Revisa sesión 1.2 sobre prompting efectivo
- Consulta `promptpacks/python/basico/implementacion.md`
- Revisa ejemplos de golden paths

---

**Versión**: 1.0  
