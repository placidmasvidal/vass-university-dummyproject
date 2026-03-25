---
ID: PR-REF-GP-001
Área / Práctica: Refactoring
Subárea: Golden Paths
Nivel de madurez: 🟡 Intermedio
Tipo de prompt: Guía
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Golden Path: Refactoring Seguro con Tests como Red

## Propósito

Proceso seguro para refactorizar código legacy usando tests existentes como red de seguridad, mejorando código sin romper funcionalidad.

## Cuándo Usar

- Refactorizar código legacy
- Mejorar código difícil de mantener
- Reducir complejidad sin cambiar comportamiento

---

## Proceso

### Fase 1: Baseline (15 min)

1. **Ejecutar tests existentes:**
   ```bash
   pytest test_module.py -v
   # TODOS deben pasar - esto es tu baseline
   ```

2. **Documentar estado actual:**
   - ¿Qué tests existen?
   - ¿Qué cubren?
   - ¿Qué falta?

3. **Abrir archivos:**
   - Código a refactorizar
   - Tests existentes
   - Documentación (si existe)

### Fase 2: Refactoring Incremental (90-120 min)

**Principio**: Refactorizar en pasos pequeños, validando después de cada paso.

#### Paso 1: Mejora pequeña

```python
# Refactorizar: Mejorar nombre de variable X a nombre_descriptivo
# Mantener comportamiento exacto
# Tests deben seguir pasando
```

1. Hacer cambio pequeño
2. Ejecutar tests inmediatamente
3. Si pasan → Continuar
4. Si fallan → Revertir y ajustar

#### Paso 2: Extraer función/método

```python
# Extraer lógica compleja a función separada
# Mantener comportamiento
# Tests como validación
```

#### Paso 3: Mejorar estructura

Continuar paso a paso, siempre validando.

### Fase 3: Actualizar Tests (30 min)

Mientras refactorizas, mejora tests:
```python
# Mejorar tests después de refactoring:
# - Agregar casos edge descubiertos
# - Mejorar legibilidad
# - Aumentar cobertura si necesario
```

### Fase 4: Documentación (15 min)

Actualizar documentación con cambios:
```markdown
# Documentar refactoring realizado:
# - Qué se cambió
# - Por qué se cambió
# - Qué mejoras se lograron
```

---

## Reglas de Oro

1. **Tests primero**: Siempre tener tests pasando antes de refactorizar
2. **Pasos pequeños**: Cambios incrementales, no grandes refactorings
3. **Validar constantemente**: Ejecutar tests después de cada cambio
4. **Revertir rápido**: Si tests fallan, revertir inmediatamente

---

**Versión**: 1.0
