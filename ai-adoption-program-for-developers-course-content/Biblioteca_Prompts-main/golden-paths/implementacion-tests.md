---
ID: PR-QA-GP-001
Área / Práctica: Testing y Calidad
Subárea: Golden Paths
Nivel de madurez: 🟡 Intermedio
Tipo de prompt: Guía
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Golden Path: Implementación + Tests Simultáneos

## Propósito

Proceso optimizado para implementar código y generar tests simultáneamente usando IA, reduciendo tiempo total y mejorando cobertura.

## Cuándo Usar

- Implementar nueva funcionalidad
- Agregar features a módulos existentes
- Extender APIs o endpoints

---

## Proceso

### Fase 1: Preparación (10 min)

1. **Abrir archivos simultáneamente:**
   - Archivo de implementación (ej: `feature.py`)
   - Archivo de tests (ej: `test_feature.py`)
   - Archivo relacionado si existe (ej: `models.py`)

2. **Crear contexto:**
   ```markdown
   # .copilot-context/feature.md
   Feature: [nombre]
   Requisitos: [lista]
   Patrones: [patrones a seguir]
   ```

### Fase 2: Implementación + Tests (60-90 min)

**Técnica clave**: Escribir en ambos archivos alternativamente.

#### Paso 1: Escribir función con prompt CLEAR

En `feature.py`:
```python
# Implementar función [nombre]
# [Prompt CLEAR completo]
def nombre_funcion(...):
    ...
```

#### Paso 2: Inmediatamente escribir test

En `test_feature.py` (mientras Copilot sugiere implementación):
```python
# Test para nombre_funcion
# Casos: básico, edge case 1, edge case 2, error
def test_nombre_funcion_basico():
    ...
```

**Copilot verá ambos archivos y sugerirá código y tests relacionados.**

#### Paso 3: Iterar simultáneamente

- Aceptar sugerencia de código → Copilot sugiere test relacionado
- Aceptar sugerencia de test → Copilot mejora código para pasarlo
- Continuar hasta función completa

### Fase 3: Validación Simultánea (15 min)

```bash
# Ejecutar tests en watch mode
pytest test_feature.py -w

# Mientras tests corren, refinar código y tests
# Ver feedback inmediato
```

### Fase 4: Refinamiento (20 min)

Usa IA para mejorar ambos:
```python
# Mejorar feature.py y test_feature.py simultáneamente:
# - Agregar casos edge
# - Mejorar cobertura
# - Optimizar código
```

---

## Ventajas

- **Tiempo reducido**: 2-3 horas vs. 4-5 horas secuencial
- **Mejor cobertura**: Tests generados junto con código
- **Sincronización**: Código y tests siempre alineados

---

**Versión**: 1.0
