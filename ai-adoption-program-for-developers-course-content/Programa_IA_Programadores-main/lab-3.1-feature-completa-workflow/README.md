# Lab 3.1: Feature Completa desde Ticket hasta PR (Workflow Agéntico)

## Objetivo

Implementar una feature completa usando workflow agéntico Plan-Act-Observe-Reflect, desde análisis del ticket hasta PR lista para merge.

## Duración

**4-5 horas**

## Contexto

Ticket: "Agregar sistema de notificaciones por email para pedidos"

**Requisitos:**
- Enviar email cuando pedido es creado
- Enviar email cuando pedido cambia de estado
- Templates de email configurables
- Logging de emails enviados

---

## Workflow Agéntico Completo

### PLAN (30-45 min)

**Usa Copilot Chat o Azure OpenAI:**

```
Como arquitecto de software, analiza este ticket y crea plan detallado:

TICKET: Agregar sistema de notificaciones por email para pedidos

REQUISITOS:
- Enviar email cuando pedido es creado
- Enviar email cuando pedido cambia de estado  
- Templates de email configurables
- Logging de emails enviados

CONTEXTO DEL PROYECTO:
- Stack: Python 3.10+, FastAPI, PostgreSQL
- Patrones: Repository, Dependency Injection
- Estructura: app/domain, app/application, app/infrastructure

GENERA PLAN DETALLADO CON:
1. Análisis de requisitos
2. Diseño de solución (componentes, interfaces)
3. Plan de implementación paso a paso
4. Plan de testing
5. Plan de documentación
6. Identificación de dependencias y riesgos
```

**Output esperado:** Plan completo en markdown

### ACT (120-180 min)

**Implementar según plan generado:**

```
Implementar feature según este plan:

[Pegar plan generado]

Implementar paso por paso. Por cada paso:
1. Implementar código
2. Generar tests correspondientes
3. Actualizar documentación
4. Verificar integración

Usar workflow Plan-Act-Observe-Reflect para cada componente.
```

**Proceso:**
1. Implementar componente 1 según plan
2. Generar tests para componente 1
3. Validar componente 1 (OBSERVE)
4. Si hay problemas, REFLECT y ajustar
5. Continuar con siguiente componente

### OBSERVE (30 min)

```
Validar implementación completa:

Checks:
1. ¿Todos los tests pasan?
2. ¿Cobertura >80%?
3. ¿Linter sin errores?
4. ¿Sigue plan original?
5. ¿Cumple requisitos del ticket?
6. ¿Integra correctamente con sistema existente?

Si hay problemas, documentarlos para REFLECT.
```

### REFLECT (30-45 min)

```
Reflexionar sobre implementación:

Problemas encontrados:
- [problema 1]
- [problema 2]

Refinamientos necesarios:
- [mejora 1]
- [mejora 2]

Generar código corregido para problemas identificados.
Iterar hasta validación exitosa completa.
```

---

## Entregables

- ✅ Feature implementada completamente
- ✅ Tests unitarios e integración
- ✅ Documentación completa
- ✅ PR lista para merge
- ✅ Plan documentado
- ✅ Reflexión del proceso

---

## Criterios de Aceptación

- [ ] Feature funciona según requisitos
- [ ] Todos los tests pasan (cobertura >80%)
- [ ] Código sigue plan original (o mejorado)
- [ ] Documentación completa
- [ ] PR lista y aprobada en code review
- [ ] Workflow Plan-Act-Observe-Reflect usado completamente

---

**Versión**: 1.0
