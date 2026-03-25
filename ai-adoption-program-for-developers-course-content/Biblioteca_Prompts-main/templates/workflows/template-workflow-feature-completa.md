---
ID: PR-WKF-WF-002
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

# Template: Workflow Feature Completa

## Propósito

Template reutilizable para ejecutar workflow agéntico completo de feature: desde ticket hasta PR lista.

## Estructura del Workflow

### PLAN

```
Como arquitecto de software senior, analiza este ticket y genera plan detallado.

TICKET:
[Título y descripción completa]

REQUISITOS:
- Funcionales: [lista]
- No funcionales: [performance, seguridad, etc.]

CONTEXTO:
- Stack: [tecnologías]
- Arquitectura: [patrón]
- Patrones: [patrones del proyecto]

GENERA PLAN CON:
1. Análisis de requisitos
2. Diseño de solución
3. Plan de implementación paso a paso
4. Plan de testing
5. Plan de documentación
6. Identificación de riesgos
```

### ACT

```
Implementar feature según este plan:

[Pegar plan generado]

Implementar paso por paso. Por cada paso:
1. Implementar código
2. Generar tests
3. Actualizar documentación
4. Verificar integración
```

### OBSERVE

```
Validar implementación:

Checks:
- [ ] Tests pasan todos
- [ ] Cobertura >80%
- [ ] Linter sin errores
- [ ] Sigue plan original
- [ ] Cumple requisitos
```

### REFLECT

```
Reflexionar sobre implementación:

Problemas encontrados:
- [problema 1]
- [problema 2]

Refinamientos:
- [mejora 1]
- [mejora 2]

Generar código corregido.
```

---

**Ver**: `playbooks/workflows/feature-completa.md` para detalles completos

---

**Versión**: 1.0
