---
ID: PR-DEV-GP-001
Área / Práctica: Desarrollo
Subárea: Golden Paths
Nivel de madurez: 🟡 Intermedio
Tipo de prompt: Guía
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Golden Path: Implementar Feature Nueva con Copilot

## Propósito

Este golden path describe el proceso paso a paso para implementar una nueva feature usando GitHub Copilot de forma consciente y efectiva, desde la planificación hasta el commit final.

## Cuándo Usar Este Path

- ✅ Implementar nueva funcionalidad desde cero
- ✅ Agregar endpoint/método nuevo a sistema existente
- ✅ Crear módulo o componente nuevo
- ✅ Extender funcionalidad existente significativamente

## Duración Estimada

**Tiempo con Copilot**: 2-4 horas (dependiendo de complejidad)  
**Tiempo sin Copilot**: 4-8 horas (estimación, varía mucho)

---

## Fase 1: Planificación y Diseño (15-30 min)

### Paso 1.1: Entender Requisitos

**Tareas:**
- [ ] Lee el ticket/requisito completamente
- [ ] Identifica inputs, outputs, reglas de negocio
- [ ] Identifica casos edge y validaciones necesarias
- [ ] Identifica dependencias con código existente

**Output:**
- Documento breve con requisitos clarificados
- Lista de casos de uso
- Lista de casos edge

### Paso 1.2: Explorar Código Existente

**Tareas:**
- [ ] Explora módulos/archivos relacionados
- [ ] Identifica patrones y convenciones del proyecto
- [ ] Identifica funciones/clases que puedes reutilizar
- [ ] Abre archivos relacionados en VS Code (contexto para Copilot)

**Usa Copilot para explorar:**
```python
# Explicar cómo funciona este módulo [nombre_modulo]
# ¿Qué patrones usa? ¿Qué convenciones sigue?
# ¿Cómo se estructura el código?
```

**Output:**
- Archivos clave abiertos en VS Code
- Notas sobre patrones y convenciones
- Lista de código reutilizable

### Paso 1.3: Diseñar Estructura

**Tareas:**
- [ ] Define qué funciones/clases necesitas
- [ ] Define interfaces/contratos (tipos de datos)
- [ ] Define estructura de archivos

**Usa Copilot para ayudar:**
```python
# Diseñar estructura para feature [nombre_feature]
# 
# Necesito:
# - Función para [responsabilidad 1]
# - Función para [responsabilidad 2]
# - Clase para [responsabilidad 3]
# 
# ¿Qué estructura seguiría patrones del proyecto?
```

**Output:**
- Estructura de código planificada
- Lista de funciones/clases con responsabilidades

---

## Fase 2: Implementación con Prompting CLEAR (60-120 min)

### Paso 2.1: Crear Archivo Base con Prompt CLEAR

**Tareas:**
1. Crea nuevo archivo: `nombre_feature.py`
2. Escribe prompt CLEAR completo como comentario al inicio:

```python
"""
Módulo: [Nombre del Módulo]

Context: [Descripción del problema que resuelve, dominio de negocio]

Funcionalidad Principal:
- [Función 1]: [Qué hace]
- [Función 2]: [Qué hace]

Patrones y Convenciones:
- Seguir estructura de [módulo similar]
- Usar [framework/librería específica]
- Seguir convenciones de [estándar]

Ejemplos de Uso:
- [Ejemplo concreto 1]
- [Ejemplo concreto 2]
"""

# Ahora implementa las funciones usando prompts CLEAR individuales
```

### Paso 2.2: Implementar Función Principal

**Para cada función, sigue este proceso:**

#### 2.2.1: Escribe Prompt CLEAR Completo

```python
# Implementar función [nombre_funcion]
# 
# Context: [Por qué existe, qué problema resuelve]
# 
# Language: Python 3.10+, type hints, PEP 8, usar [librerías específicas]
# 
# Examples:
#   nombre_funcion(input1, input2) -> output1
#   nombre_funcion(input3, input4) -> output2
# 
# Assumptions:
#   - [Supuesto 1]
#   - [Supuesto 2]
#   - [No asumir que...]
# 
# Returns:
#   tipo: [Qué retorna, formato]
# 
# Raises:
#   ValueError: Si [condición]
#   TypeError: Si [condición]
def nombre_funcion(param1: tipo, param2: tipo) -> tipo:
    ...
```

#### 2.2.2: Deja que Copilot Genere Implementación

- ✅ Espera sugerencia de Copilot
- ✅ **NO aceptes inmediatamente**
- ✅ Revisa la sugerencia

#### 2.2.3: Revisa y Refina

**Checklist de revisión:**
- [ ] ¿La lógica es correcta?
- [ ] ¿Maneja casos edge mencionados?
- [ ] ¿Sigue convenciones del proyecto?
- [ ] ¿Tiene type hints?
- [ ] ¿Está bien documentada?

**Si no es óptima:**
- Refina el prompt
- Agrega más ejemplos
- Especifica más detalles
- Pide regeneración

#### 2.2.4: Itera Hasta Obtener Código Correcto

Repite 2.2.2 y 2.2.3 hasta que el código sea satisfactorio.

### Paso 2.3: Implementar Funciones Auxiliares

**Repite proceso 2.2 para cada función auxiliar necesaria.**

**Tip:** Usa el contexto de la función principal para ayudar a Copilot:
```python
# Implementar función auxiliar para [propósito]
# Esta función será usada por nombre_funcion_principal para [qué]
# Debe seguir el mismo patrón que [función similar existente]
```

### Paso 2.4: Agregar Validaciones y Manejo de Errores

**Usa Copilot para mejorar:**
```python
# Mejorar función [nombre] agregando:
# - Validación de inputs (tipos, rangos, formatos)
# - Manejo de errores apropiado
# - Mensajes de error descriptivos
# - Lanzar excepciones apropiadas
```

---

## Fase 3: Tests Unitarios (45-90 min)

### Paso 3.1: Crear Archivo de Tests

**Crea:** `test_nombre_feature.py`

### Paso 3.2: Generar Tests con Copilot

**Escribe prompt completo:**
```python
# Generar suite completa de tests unitarios para [módulo/función]
# 
# Context: Necesito validar funcionalidad de [descripción]
# 
# Casos a cubrir:
# 1. Caso básico exitoso: [input] -> [output esperado]
# 2. Caso edge 1: [descripción]
# 3. Caso edge 2: [descripción]
# 4. Caso de error 1: [qué error, qué input]
# 5. Caso de error 2: [qué error, qué input]
# 
# Requisitos:
# - Usar pytest
# - Nombre funciones: test_[descripcion_caso]
# - Cobertura objetivo: >90%
# - Tests independientes
# - Usar fixtures si aplica
# 
# Ejemplos de casos específicos:
# - test_caso_basico_exitoso()
# - test_caso_con_valores_limite()
# - test_error_con_input_invalido()
import pytest
from nombre_modulo import nombre_funcion

def test_...():
    ...
```

### Paso 3.3: Ejecutar y Validar Tests

```bash
# Ejecutar tests
pytest test_nombre_feature.py -v

# Ver cobertura
pytest --cov=nombre_modulo test_nombre_feature.py

# Ver cobertura detallada
pytest --cov=nombre_modulo --cov-report=html test_nombre_feature.py
```

**Tareas:**
- [ ] Todos los tests pasan
- [ ] Cobertura >80% (ideal >90%)
- [ ] Tests son legibles y mantenibles

### Paso 3.4: Agregar Tests Faltantes

**Si falta cobertura, usa Copilot:**
```python
# Generar tests adicionales para cubrir estos casos:
# - [Caso no cubierto 1]
# - [Caso no cubierto 2]
# - Casos de integración si aplica
```

---

## Fase 4: Refinamiento y Mejoras (30-60 min)

### Paso 4.1: Revisión de Código Generado

**Checklist de revisión:**
- [ ] ¿Hay código duplicado que se puede extraer?
- [ ] ¿Los nombres son descriptivos?
- [ ] ¿La estructura es clara y mantenible?
- [ ] ¿Sigue PEP 8 (o estándares del proyecto)?
- [ ] ¿Documentación es completa?

### Paso 4.2: Refactorización con Copilot

**Si encuentras mejoras:**
```python
# Refactorizar esta función para:
# - Extraer constante mágica [nombre_constante]
# - Mejorar nombre de variable [variable] a [nombre_mejor]
# - Simplificar lógica compleja
# - Mejorar legibilidad general
```

### Paso 4.3: Optimización (Opcional)

**Si es necesario optimizar:**
```python
# Optimizar esta función para mejor performance:
# - [Qué optimizar específicamente]
# - Mantener legibilidad
# - Agregar comentarios explicando optimización
```

---

## Fase 5: Auditoría de Seguridad (15-30 min)

### Paso 5.1: Checklist de Seguridad

**Usa checklist completo:** `checklists/seguridad-ia.md`

**Verificaciones críticas:**
- [ ] No hay credenciales o secretos hardcoded
- [ ] No hay datos sensibles
- [ ] Validación de inputs adecuada
- [ ] Manejo de errores seguro (no expone información)
- [ ] No hay vulnerabilidades comunes (SQLi, XSS, etc.)

### Paso 5.2: Herramientas de Verificación

```bash
# Python
bandit -r nombre_modulo/

# Dependencias
safety check
pip-audit
```

### Paso 5.3: Corregir Problemas Encontrados

**Si encuentras problemas, usa Copilot para corregir:**
```python
# Corregir problema de seguridad: [descripción]
# - [Cambio específico 1]
# - [Cambio específico 2]
# Mantener funcionalidad, solo mejorar seguridad
```

---

## Fase 6: Documentación (15-30 min)

### Paso 6.1: Mejorar Docstrings

**Usa Copilot para generar docstrings completos:**
```python
# Generar docstring completo en formato Google/NumPy para función [nombre]
# Incluir: descripción, args, returns, raises, ejemplos de uso
```

### Paso 6.2: Documentación de Módulo

**Si es necesario, crea README:**
```markdown
# Generar README para módulo [nombre]
# Incluir: propósito, instalación, uso básico, ejemplos, API reference
```

---

## Fase 7: Integración y Commit (15 min)

### Paso 7.1: Verificar Integración

**Tareas:**
- [ ] Código se integra correctamente con código existente
- [ ] No rompe tests existentes
- [ ] Linter pasa sin errores
- [ ] Formatter aplicado correctamente

### Paso 7.2: Commit Final

**Mensaje de commit sugerido:**
```
feat: [descripción breve de feature]

- Implementa [función principal]
- Agrega [funcionalidad específica]
- Incluye tests con cobertura >80%
- Documentación completa

[Ticket/Issue: #123]
```

**Verificaciones finales:**
- [ ] Código revisado completamente
- [ ] Tests pasan todos
- [ ] Seguridad verificada
- [ ] Documentación completa
- [ ] Listo para PR

---

## Checklist Final Completo

Antes de considerar la feature completa:

### Funcionalidad
- [ ] Feature implementada según requisitos
- [ ] Todos los casos de uso cubiertos
- [ ] Casos edge manejados
- [ ] Validaciones implementadas

### Calidad
- [ ] Tests unitarios completos (>80% cobertura)
- [ ] Todos los tests pasan
- [ ] Código sigue estándares (PEP 8, etc.)
- [ ] Type hints incluidos
- [ ] Documentación completa

### Seguridad
- [ ] Auditoría de seguridad completada
- [ ] No hay vulnerabilidades conocidas
- [ ] No hay datos sensibles
- [ ] Validación de inputs adecuada

### Integración
- [ ] Se integra correctamente con código existente
- [ ] No rompe funcionalidad existente
- [ ] Linter/formatter pasa
- [ ] Listo para code review

---

## Troubleshooting

### Problema: Copilot no entiende el contexto

**Solución:**
- Abre más archivos relacionados en VS Code
- Agrega más contexto al prompt
- Descompón el problema en pasos más pequeños
- Proporciona ejemplos más específicos

### Problema: Código generado tiene bugs

**Solución:**
- Refina el prompt con más detalles
- Agrega ejemplos de casos edge
- Escribe tests primero y luego pide implementación
- Itera y refina múltiples veces

### Problema: Código no sigue convenciones del proyecto

**Solución:**
- Agrega ejemplos de código existente al prompt
- Especifica convenciones explícitamente
- Revisa y ajusta manualmente después
- Usa formatter/linter para corregir

---

## Métricas de Éxito

### Tiempo
- **Con Copilot**: 2-4 horas (feature completa)
- **Sin Copilot**: 4-8 horas (estimación)

### Calidad
- Cobertura de tests: >80%
- Bugs encontrados en code review: <2 críticos
- Seguridad: 0 vulnerabilidades críticas

### Adopción
- Usa prompting CLEAR: ✅
- Revisa código antes de aceptar: ✅
- Tests generados con Copilot: ✅
- Auditoría de seguridad: ✅

---

## Recursos Relacionados

- `promptpacks/python/basico/fundamentos.md` - Prompts base
- `checklists/seguridad-ia.md` - Checklist de seguridad
- `playbooks/code-review-ia.md` - Code review asistido

---

**Versión**: 1.0  
**Última actualización**: 2026-01-15  
**Nivel**: Básico (Nivel 1)  
**Tiempo total estimado**: 3-6 horas
