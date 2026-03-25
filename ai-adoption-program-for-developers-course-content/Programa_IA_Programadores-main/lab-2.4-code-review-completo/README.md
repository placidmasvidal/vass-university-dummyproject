# Lab 2.4: Code Review Completo de PR Real

## Objetivo

Realizar code review completo de una Pull Request real usando IA para identificar bugs, mejoras y deuda técnica de forma sistemática.

## Duración

**2-3 horas**

## Contexto

Tienes una PR real de tu proyecto (o PR proporcionada) que necesita review completo:
- Revisar funcionalidad
- Identificar bugs
- Sugerir mejoras
- Identificar deuda técnica
- Validar seguridad y calidad

**Usar IA para asistir en todo el proceso de review.**

---

## Instrucciones

### Paso 1: Preparación (15 min)

1. Abre PR en GitHub/GitLab
2. Revisa descripción y contexto
3. Abre archivos cambiados en VS Code
4. Abre archivos relacionados para contexto

### Paso 2: Review Estructural con IA (30 min)

**Usa Copilot Chat:**

```
Revisar esta PR estructuralmente:

Título: [título PR]
Descripción: [descripción]

Archivos cambiados:
- [archivo1]
- [archivo2]

¿La estructura es apropiada?
¿Sigue patrones del proyecto?
¿Hay código duplicado?
¿La organización es clara?
```

### Paso 3: Review de Bugs con IA (40 min)

```
Analizar código de esta PR buscando bugs:

Archivos:
[listar archivos]

Buscar:
- Lógica incorrecta
- Casos edge no manejados
- Race conditions
- Memory leaks potenciales
- Problemas de performance

Proporcionar lista de bugs encontrados con ubicación.
```

### Paso 4: Review de Seguridad (30 min)

Usa checklist de seguridad (`checklists/seguridad-ia.md`) con IA:

```
Auditar seguridad de esta PR:

[pegar código relevante]

Buscar:
- Credenciales/secretos
- Vulnerabilidades OWASP Top 10
- Validación de inputs
- Exposición de datos sensibles
```

### Paso 5: Review de Calidad y Deuda Técnica (40 min)

```
Analizar calidad del código y deuda técnica:

[pegar código]

Revisar:
- Legibilidad y mantenibilidad
- Complejidad ciclomática
- Code smells
- Oportunidades de refactoring
- Documentación
```

### Paso 6: Documentar Review Completo (25 min)

Crea documento de review estructurado:

```markdown
# Code Review: [Título PR]

## Resumen
- Estado: [Aprobar / Aprobar con cambios / Requiere cambios]
- Bugs encontrados: [número]
- Problemas de seguridad: [número]
- Mejoras sugeridas: [número]

## Bugs Encontrados
[lista con IA asistida]

## Problemas de Seguridad
[lista con IA asistida]

## Mejoras de Calidad
[lista con IA asistida]

## Deuda Técnica Identificada
[lista con IA asistida]

## Recomendación Final
[con justificación]
```

---

## Criterios de Aceptación

- [ ] Review completo y sistemático
- [ ] Mínimo 3 bugs identificados (o justificar que no hay)
- [ ] Mínimo 2 problemas de seguridad identificados (o justificar)
- [ ] Mínimo 3 mejoras de calidad sugeridas
- [ ] Deuda técnica documentada
- [ ] Review documentado profesionalmente
- [ ] IA usada efectivamente en todo el proceso

---

**Versión**: 1.0
