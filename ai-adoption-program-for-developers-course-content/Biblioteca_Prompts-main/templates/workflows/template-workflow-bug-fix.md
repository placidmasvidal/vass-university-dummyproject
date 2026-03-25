---
ID: PR-WKF-WF-001
Área / Práctica: Workflows
Subárea: Workflows
Nivel de madurez: 🟡 Intermedio
Tipo de prompt: Guía
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Template: Workflow Bug Fix

## Propósito

Template reutilizable para workflow agéntico de resolución de bugs complejos.

## Estructura

### PLAN - Diagnóstico

```
Analizar bug: [descripción]

CONTEXTO:
- Sistema: [descripción]
- Componentes: [lista]
- Síntomas: [descripción]

GENERA PLAN:
1. Hipótesis posibles
2. Archivos a revisar
3. Estrategia de debugging
4. Tests para reproducir
```

### ACT - Investigación y Fix

```
Fase 1: Investigar
[Prompt de investigación específica]

Fase 2: Reproducir
Crear test que reproduce bug

Fase 3: Fix
Implementar fix para problema identificado
```

### OBSERVE

```
Validar fix:
- Test de reproducción pasa
- Tests existentes pasan
- No hay regresiones
```

### REFLECT

```
Reflexionar:
- ¿Es la mejor solución?
- ¿Qué aprendimos?
- ¿Cómo prevenir similar?
```

---

**Ver**: `playbooks/workflows/bug-fix-agéntico.md` para detalles

---

**Versión**: 1.0
