---
ID: PR-QA-CH-002
Área / Práctica: Testing y Calidad
Subárea: Checklists
Nivel de madurez: 🟡 Intermedio
Tipo de prompt: Revisión
Compatibilidad: Universal
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Checklist: PR Asistida por IA

## Propósito

Checklist completo para validar Pull Requests que fueron creadas o asistidas por IA (GitHub Copilot, Azure OpenAI), asegurando calidad, seguridad y consistencia.

## Uso

Revisar cada item antes de aprobar merge de PR asistida por IA.

---

## Pre-Review: Verificación Inicial (5 min)

### Contexto
- [ ] Descripción de PR es clara y completa
- [ ] Objetivo del cambio está documentado
- [ ] Scope del cambio es apropiado
- [ ] Tests relacionados incluidos (si aplica)

### Herramientas
- [ ] IA usada está documentada (GitHub Copilot, Azure OpenAI, etc.)
- [ ] Código generado fue revisado antes de commit
- [ ] Auditoría de seguridad básica realizada

---

## Seguridad (CRÍTICO - Revisar Siempre)

### Credenciales y Secretos
- [ ] ❌ NO hay passwords, API keys, tokens hardcoded
- [ ] ❌ NO hay secretos en comentarios
- [ ] ✅ Usa variables de entorno para secretos
- [ ] ✅ Archivos `.env` están en `.gitignore`
- [ ] ✅ Secrets management usado apropiadamente (si aplica)

### Datos Sensibles
- [ ] ❌ NO contiene datos personales reales (PII)
- [ ] ❌ NO contiene información financiera
- [ ] ❌ NO contiene información médica
- [ ] ✅ Usa datos mock/anónimos para ejemplos
- [ ] ✅ No expone información en logs/respuestas de error

### Vulnerabilidades Comunes
- [ ] ✅ Sin riesgo de SQL injection
- [ ] ✅ Sin riesgo de XSS (Cross-Site Scripting)
- [ ] ✅ Sin riesgo de CSRF
- [ ] ✅ Sin riesgo de path traversal
- [ ] ✅ Sin riesgo de command injection
- [ ] ✅ Validación de inputs adecuada
- [ ] ✅ Sanitización de outputs (si aplica)

### Autenticación y Autorización
- [ ] ✅ Lógica de autenticación es segura
- [ ] ✅ Passwords hasheados apropiadamente (bcrypt, argon2)
- [ ] ✅ Tokens/sesiones manejados correctamente
- [ ] ✅ Autorización verificada en backend
- [ ] ✅ No hay bypass de seguridad

### Configuración
- [ ] ✅ Valores por defecto son seguros
- [ ] ✅ No expone información de infraestructura
- [ ] ✅ Configuración sigue políticas corporativas

---

## Calidad de Código

### Estructura y Organización
- [ ] ✅ Código bien organizado y estructurado
- [ ] ✅ Sigue patrones del proyecto
- [ ] ✅ Sin código duplicado innecesario
- [ ] ✅ Separación de responsabilidades clara
- [ ] ✅ Funciones/clases cohesivas

### Legibilidad
- [ ] ✅ Nombres descriptivos (variables, funciones, clases)
- [ ] ✅ Código fácil de entender sin explicación
- [ ] ✅ Complejidad ciclomática razonable
- [ ] ✅ Sin "magic numbers" (usar constantes)

### Documentación
- [ ] ✅ Docstrings/comentarios presentes donde necesario
- [ ] ✅ Documentación es clara y útil
- [ ] ✅ README actualizado (si aplica)
- [ ] ✅ Cambios documentados en CHANGELOG (si aplica)

### Estándares y Convenciones
- [ ] ✅ Sigue PEP 8 / estándares del proyecto
- [ ] ✅ Type hints incluidos (si aplica Python 3.5+)
- [ ] ✅ Formatter aplicado (black, prettier, etc.)
- [ ] ✅ Linter pasa sin errores críticos

---

## Funcionalidad y Lógica

### Correctitud
- [ ] ✅ Lógica es correcta y completa
- [ ] ✅ Implementa requisitos correctamente
- [ ] ✅ No introduce bugs conocidos
- [ ] ✅ Casos edge manejados apropiadamente

### Validación
- [ ] ✅ Validación de inputs adecuada
- [ ] ✅ Validación de tipos de datos
- [ ] ✅ Validación de rangos/formatos
- [ ] ✅ Manejo de errores robusto

### Performance
- [ ] ✅ Performance es aceptable
- [ ] ✅ No hay memory leaks obvios
- [ ] ✅ Consultas a BD optimizadas (si aplica)
- [ ] ✅ No hay operaciones bloqueantes innecesarias

---

## Tests

### Cobertura
- [ ] ✅ Tests presentes para funcionalidad nueva
- [ ] ✅ Cobertura >80% (ideal >90%)
- [ ] ✅ Tests cubren casos edge
- [ ] ✅ Tests cubren casos de error

### Calidad de Tests
- [ ] ✅ Tests son independientes
- [ ] ✅ Tests son determinísticos (no flaky)
- [ ] ✅ Tests usan mocks apropiadamente
- [ ] ✅ Tests son mantenibles y legibles
- [ ] ✅ Tests pasan todos localmente

### Tipos de Tests
- [ ] ✅ Tests unitarios presentes
- [ ] ✅ Tests de integración (si aplica)
- [ ] ✅ Tests E2E (si aplica)

---

## Integración

### Compatibilidad
- [ ] ✅ No rompe funcionalidad existente
- [ ] ✅ Compatible con cambios recientes
- [ ] ✅ No causa regresiones
- [ ] ✅ Migraciones/scripts correctos (si aplica)

### Dependencias
- [ ] ✅ Dependencias nuevas están justificadas
- [ ] ✅ Versiones de dependencias son apropiadas
- [ ] ✅ Dependencias no tienen vulnerabilidades conocidas
- [ ] ✅ Requirements/dependencies actualizados

### Base de Datos
- [ ] ✅ Migraciones correctas (si aplica)
- [ ] ✅ No hay pérdida de datos
- [ ] ✅ Rollback es posible (si aplica)

---

## Revisión Específica de IA

### Uso de IA
- [ ] ✅ Código generado por IA fue revisado cuidadosamente
- [ ] ✅ No se aceptó código sin revisión
- [ ] ✅ Prompts usados fueron apropiados (si documentados)
- [ ] ✅ Contexto proporcionado a IA fue adecuado

### Problemas Comunes de IA
- [ ] ✅ No hay código genérico/vago de IA
- [ ] ✅ No hay comentarios obviamente generados sin sentido
- [ ] ✅ Código es específico al contexto del proyecto
- [ ] ✅ No hay código que "funciona pero no debería estar así"

### Iteración y Refinamiento
- [ ] ✅ Código fue refinado después de generación inicial
- [ ] ✅ Mejoras aplicadas basadas en feedback
- [ ] ✅ No es primera versión generada sin ajustes

---

## Políticas y Estándares

### Exclusiones
- [ ] ✅ Código no está en lista de exclusiones
- [ ] ✅ No es código legacy crítico (según política)
- [ ] ✅ Permisos para usar IA en este código

### Estándares del Proyecto
- [ ] ✅ Sigue arquitectura del proyecto
- [ ] ✅ Sigue convenciones de nombres
- [ ] ✅ Sigue patrones establecidos
- [ ] ✅ Integra bien con código existente

---

## Checklist Rápido (Imprimir)

```
┌─────────────────────────────────────────────────────────┐
│         CHECKLIST PR ASISTIDA POR IA                    │
├─────────────────────────────────────────────────────────┤
│ SEGURIDAD                                                │
│ □ Sin credenciales/secretos                             │
│ □ Sin datos sensibles                                   │
│ □ Sin vulnerabilidades comunes                          │
│ □ Validación inputs adecuada                            │
│                                                          │
│ CALIDAD                                                  │
│ □ Código bien estructurado                              │
│ □ Documentación adecuada                                │
│ □ Sigue estándares                                      │
│                                                          │
│ FUNCIONALIDAD                                            │
│ □ Lógica correcta                                       │
│ □ Casos edge manejados                                  │
│ □ Tests presentes y pasan                               │
│                                                          │
│ INTEGRACIÓN                                              │
│ □ No rompe funcionalidad existente                      │
│ □ Compatible con cambios                                │
│                                                          │
│ IA                                                       │
│ □ Código revisado cuidadosamente                        │
│ □ Refinado después de generación                        │
│ □ Contexto apropiado usado                              │
└─────────────────────────────────────────────────────────┘
```

---

## Decisiones de Review

### Aprobar
✅ Todos los items críticos cumplidos  
✅ Calidad aceptable  
✅ Tests pasan  
✅ Sin problemas de seguridad

### Aprobar con Cambios
⚠️ Algunos items no críticos faltantes  
⚠️ Mejoras sugeridas pero no bloqueantes  
⚠️ Requiere follow-up después de merge

### Requiere Cambios (No Aprobar)
❌ Items críticos no cumplidos  
❌ Problemas de seguridad  
❌ Bugs significativos  
❌ Tests faltantes o fallando

---

## Proceso Post-Review

### Si Aprobado
- [ ] Merge después de aprobación
- [ ] Monitorear después de deploy
- [ ] Cerrar issue/ticket relacionado

### Si Requiere Cambios
- [ ] Comunicar feedback claramente
- [ ] Proporcionar ejemplos de código mejorado
- [ ] Esperar correcciones
- [ ] Re-review después de cambios

---

**Versión**: 1.0  
**Última actualización**: 2026-01-15  
**Nivel**: 2 - IA Multi-tarea  
**Uso**: Obligatorio para PRs asistidas por IA
