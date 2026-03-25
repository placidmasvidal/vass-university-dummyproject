---
ID: PR-DEV-PP-001
Área / Práctica: Desarrollo
Subárea: Promptpacks
Nivel de madurez: 🟢 Básico
Tipo de prompt: Generación
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Promptpack Python - Básico: Fundamentos

## Propósito

Este promptpack contiene prompts y técnicas fundamentales para usar GitHub Copilot efectivamente con Python. Está diseñado para desarrolladores que están empezando a usar Copilot de forma consciente (Nivel 1).

## Principios Fundamentales

### 1. Contexto Primero
Siempre proporciona contexto antes de pedir código. Copilot necesita entender:
- ¿Qué problema estás resolviendo?
- ¿Cuál es el dominio de negocio?
- ¿Qué restricciones hay?

### 2. Nombres Descriptivos
Los nombres de variables y funciones deben ser claros y descriptivos. Esto ayuda a Copilot a entender la intención.

### 3. Comentarios Estratégicos
Usa comentarios como prompts naturales. Escribe el "qué" y el "por qué" antes del "cómo".

---

## Templates de Prompts por Tarea

### Template 1: Implementar Función Simple

**Estructura CLEAR:**

```python
# [CONTEXT] Descripción clara de qué hace la función y por qué existe
# 
# [LANGUAGE] Python 3.10+, usar type hints, seguir PEP 8
# 
# [EXAMPLES] Ejemplos concretos de uso:
#   nombre_funcion(input1, input2) -> output_esperado
#   nombre_funcion(input3, input4) -> output_esperado
# 
# [ASSUMPTIONS] Qué asumimos:
#   - Input debe ser válido según...
#   - No asumir que...
# 
# [RESULTS] Qué retorna:
#   - Tipo de dato
#   - Formato
#   - Casos especiales
def nombre_funcion(parametro1: tipo, parametro2: tipo) -> tipo_retorno:
    ...
```

**Ejemplo Real:**

```python
# Calcular precio final de producto con impuesto IVA
# 
# Context: Sistema de e-commerce necesita calcular precio final
# aplicando IVA (19% en Chile) al precio base
# 
# Language: Python 3.10+, usar type hints, Decimal para dinero
# 
# Examples:
#   calcular_precio_con_iva(1000.0) -> 1190.0
#   calcular_precio_con_iva(5000.0) -> 5950.0
# 
# Assumptions:
#   - precio_base >= 0
#   - IVA es 19% fijo
#   - Retornar redondeado a 0 decimales
# 
# Returns:
#   float: Precio final con IVA incluido
def calcular_precio_con_iva(precio_base: float) -> float:
    ...
```

### Template 2: Refactorizar Función Legacy

```python
# Refactorizar función legacy para mejorar legibilidad y mantenibilidad
# 
# Función original hace: [describir qué hace]
# 
# Mejoras necesarias:
# - [ ] Mejorar nombres de variables
# - [ ] Agregar documentación
# - [ ] Agregar type hints
# - [ ] Mejorar manejo de errores
# - [ ] Extraer lógica compleja
# 
# Mantener funcionalidad exacta, solo mejorar código
def funcion_refactorizada(...):
    ...
```

### Template 3: Implementar Validación

```python
# Validar [qué se valida] según [reglas de negocio]
# 
# Reglas de validación:
# 1. [Regla 1 con ejemplo]
# 2. [Regla 2 con ejemplo]
# 3. [Regla 3 con ejemplo]
# 
# Casos edge a manejar:
# - [Caso edge 1]: [qué retornar]
# - [Caso edge 2]: [qué retornar]
# 
# Returns:
#   tuple[bool, str]: (es_valido, mensaje_error)
#   Si es válido: (True, "")
#   Si no es válido: (False, "mensaje descriptivo del error")
def validar_...(...):
    ...
```

### Template 4: Implementar Test Unitario

```python
# Generar tests unitarios para función [nombre_funcion]
# 
# Casos a cubrir:
# 1. Caso básico exitoso: [input] -> [output esperado]
# 2. Caso edge 1: [descripción]
# 3. Caso edge 2: [descripción]
# 4. Caso de error: [qué error se espera]
# 
# Usar pytest
# Nombre funciones: test_[descripcion_caso]
# Cobertura objetivo: >90%
import pytest
from modulo import nombre_funcion

def test_...():
    ...
```

---

## Patrones Comunes de Prompting

### Patrón 1: Descomposición de Problema

Si el problema es complejo, descomponerlo en pasos:

```python
# Implementar [función compleja] siguiendo estos pasos:
# 
# Paso 1: [Qué hacer primero]
# Paso 2: [Qué hacer segundo]
# Paso 3: [Qué hacer tercero]
# 
# Cada paso debe:
# - Ser una función/clase separada si es apropiado
# - Tener documentación clara
# - Tener tests unitarios
```

### Patrón 2: Few-Shot Learning

Mostrar ejemplos de lo que quieres:

```python
# Implementar función que sigue este patrón:
# 
# Ejemplos de comportamiento esperado:
# funcion("input1") -> "output1"
# funcion("input2") -> "output2"
# funcion("input3") -> "output3"
# 
# Patrón a seguir: [describir patrón]
def funcion(...):
    ...
```

### Patrón 3: Chain-of-Thought

Pedir que Copilot "piense paso a paso":

```python
# Calcular [resultado complejo] siguiendo esta lógica:
# 
# Paso 1: Calcular [valor intermedio 1]
# Paso 2: Aplicar [operación] usando [valor intermedio 1]
# Paso 3: Validar [condición]
# Paso 4: Si válido, calcular [resultado final]
#         Si no válido, retornar [valor por defecto]
# 
# Incluir comentarios en cada paso explicando la lógica
def calcular_...(...):
    ...
```

---

## Ejemplos Prácticos por Dominio

### Ejemplo 1: Validación de Datos

```python
# Validar RUT chileno (formato XX.XXX.XXX-Y)
# 
# Reglas:
# - Formato: números separados por puntos, guión, dígito verificador
# - Dígito verificador puede ser número o 'K'
# - Ejemplos válidos: "12.345.678-9", "11.111.111-K"
# - Ejemplos inválidos: "12345678-9" (sin puntos), "12.345.678-K" (K solo al final)
# 
# Returns:
#   bool: True si RUT es válido, False si no
def validar_rut_chileno(rut: str) -> bool:
    ...
```

### Ejemplo 2: Transformación de Datos

```python
# Normalizar lista de emails: convertir a minúsculas, eliminar espacios,
# eliminar duplicados, ordenar alfabéticamente
# 
# Input: Lista de strings que pueden tener emails en cualquier formato
# Output: Lista de emails normalizados, sin duplicados, ordenados
# 
# Ejemplos:
#   normalizar_emails(["  User@Example.COM  ", "user@example.com", "other@test.com"])
#   -> ["other@test.com", "user@example.com"]
# 
# Manejar casos edge:
# - Emails inválidos: ignorar (no incluir en resultado)
# - Lista vacía: retornar lista vacía
# - None: retornar lista vacía
def normalizar_emails(emails: list[str]) -> list[str]:
    ...
```

### Ejemplo 3: Cálculo de Negocio

```python
# Calcular comisión de vendedor según monto de venta y tipo de cliente
# 
# Reglas de comisión:
# - Cliente Premium: 15% del monto
# - Cliente Regular: 10% del monto
# - Cliente Nuevo: 5% del monto, máximo $500
# 
# Ejemplos:
#   calcular_comision(1000, "premium") -> 150.0
#   calcular_comision(1000, "regular") -> 100.0
#   calcular_comision(10000, "nuevo") -> 500.0  # Máximo aplicado
# 
# Validaciones:
# - monto debe ser > 0
# - tipo_cliente debe ser "premium", "regular" o "nuevo"
# - Lanzar ValueError si validaciones fallan
# 
# Returns:
#   float: Comisión calculada, nunca negativa
def calcular_comision(monto: float, tipo_cliente: str) -> float:
    ...
```

---

## Anti-Patrones a Evitar

### ❌ Anti-Patrón 1: Prompt Vago

**Mal:**
```python
def calc(a, b):
    ...
```

**Bien:**
```python
# Calcular precio total con descuento porcentual
# precio_base: float, precio sin descuento
# descuento: float entre 0 y 100, porcentaje de descuento
# Retorna: float, precio final redondeado a 2 decimales
def calcular_precio_con_descuento(precio_base: float, descuento: float) -> float:
    ...
```

### ❌ Anti-Patrón 2: Sin Contexto de Negocio

**Mal:**
```python
def process(data):
    ...
```

**Bien:**
```python
# Procesar pedidos de e-commerce: validar stock disponible,
# calcular total con impuestos, aplicar descuentos promocionales
def procesar_pedido(pedido: dict) -> dict:
    ...
```

### ❌ Anti-Patrón 3: Sin Ejemplos

**Mal:**
```python
# Validar email
def validar_email(email):
    ...
```

**Bien:**
```python
# Validar formato de email corporativo
# Ejemplos válidos: "usuario@empresa.com", "nombre.apellido@empresa.com"
# Ejemplos inválidos: "usuario@", "@empresa.com", "sin-arroba.com"
def validar_email(email: str) -> bool:
    ...
```

### ❌ Anti-Patrón 4: Asumir que Copilot Adivina

**Mal:**
```python
def func(x, y):
    # Hacer algo con x e y
    ...
```

**Bien:**
```python
# Multiplicar precio unitario por cantidad, aplicar descuento del 10%
# si cantidad > 10, retornar total redondeado a 2 decimales
def calcular_total(precio_unitario: float, cantidad: int) -> float:
    ...
```

---

## Mejores Prácticas

### 1. Siempre Revisar Antes de Aceptar

- ✅ No aceptes sugerencias sin revisar
- ✅ Lee el código generado completamente
- ✅ Verifica que la lógica es correcta
- ✅ Asegúrate que maneja casos edge

### 2. Iterar y Refinar

- ✅ Si la primera sugerencia no es óptima, refina el prompt
- ✅ Agrega más contexto si es necesario
- ✅ Proporciona ejemplos más específicos
- ✅ Descompón problemas complejos en pasos

### 3. Usar Type Hints

- ✅ Siempre incluye type hints en prompts
- ✅ Esto ayuda a Copilot a generar código más correcto
- ✅ Mejora la legibilidad del código generado

### 4. Documentar Mientras Codificas

- ✅ Escribe docstrings como parte del prompt
- ✅ Copilot puede generar documentación basada en tus comentarios
- ✅ Documentación inline ayuda a mantener contexto

---

## Checklist de Uso

Antes de usar un prompt de este promptpack, verifica:

- [ ] ¿El prompt incluye contexto claro (C de CLEAR)?
- [ ] ¿Especifica lenguaje y convenciones (L de CLEAR)?
- [ ] ¿Incluye ejemplos concretos (E de CLEAR)?
- [ ] ¿Documenta supuestos (A de CLEAR)?
- [ ] ¿Especifica resultados esperados (R de CLEAR)?
- [ ] ¿Usa nombres descriptivos?
- [ ] ¿Incluye type hints?
- [ ] ¿Maneja casos edge?

---

## Recursos Relacionados

- `promptpacks/python/avanzado/` - Para técnicas avanzadas (Nivel 2+)
- `checklists/seguridad-ia.md` - Checklist de seguridad
- `golden-paths/feature-nueva.md` - Golden path completo

---

**Versión**: 1.0  
**Última actualización**: 2026-01-15  
**Nivel**: Básico (Nivel 1)  
**Stack**: Python
