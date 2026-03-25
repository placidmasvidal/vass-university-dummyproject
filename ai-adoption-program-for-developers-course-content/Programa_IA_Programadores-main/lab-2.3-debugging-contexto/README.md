# Lab 2.3: Debugging Complejo con Contexto Multi-archivo

## Objetivo

Debuggear un bug complejo que involucra múltiples archivos usando IA con contexto completo del sistema.

## Duración

**2-3 horas**

## Contexto

Bug reportado: "Los pedidos se procesan pero los emails de confirmación no se envían"

**Sistema involucra:**
- `order_service.py` - Lógica de pedidos
- `email_service.py` - Servicio de emails
- `notification_queue.py` - Cola de notificaciones
- `config.py` - Configuración

**Bug afecta múltiples archivos - necesita contexto completo.**

---

## Instrucciones

### Paso 1: Configurar Contexto Completo (20 min)

1. Abre TODOS los archivos relacionados:
   - Archivos del sistema (order_service, email_service, etc.)
   - Archivos de configuración
   - Archivos de tests relacionados
   - Logs si disponibles

2. Crea archivo de contexto:
```markdown
# .copilot-context/bug-investigation.md

## Bug Reportado
Los pedidos se procesan pero emails no se envían

## Archivos Involucrados
- order_service.py: Crea pedidos
- email_service.py: Envía emails
- notification_queue.py: Cola de notificaciones

## Flujo Esperado
1. Pedido creado → order_service
2. Evento enviado → notification_queue
3. Email enviado → email_service

## Síntomas
- Pedidos se crean correctamente
- Emails NO se envían
- No hay errores visibles en logs
```

### Paso 2: Diagnóstico con IA (60 min)

**Usa Copilot Chat o Azure OpenAI:**

```
Analizar este bug: "Pedidos se procesan pero emails no se envían"

Archivos relevantes:
- order_service.py (líneas X-Y)
- email_service.py (líneas A-B)
- notification_queue.py (líneas M-N)

Flujo esperado:
1. Pedido creado
2. Evento de notificación
3. Email enviado

Síntoma: Paso 1 funciona, paso 3 no ocurre.

¿Dónde está el problema? ¿Qué está fallando?
```

**Copilot analizará el flujo completo con contexto.**

### Paso 3: Identificar Root Cause (30 min)

Basado en análisis de IA:
1. Revisa sugerencias de Copilot
2. Verifica hipótesis
3. Agrega logging temporal si necesario
4. Confirma root cause

### Paso 4: Fix con Contexto Completo (40 min)

```python
# Corregir bug identificado en [archivo]
# 
# Root cause: [descripción]
# Fix: [solución]
# 
# Asegurar que fix no rompe otros componentes
# Verificar integración con [archivo relacionado 1] y [archivo relacionado 2]
```

**Usa contexto completo para asegurar fix correcto.**

### Paso 5: Validación End-to-End (20 min)

1. Ejecutar tests de integración
2. Verificar flujo completo manualmente
3. Confirmar emails se envían
4. Verificar no hay regresiones

---

## Criterios de Aceptación

- [ ] Root cause identificado correctamente
- [ ] Fix implementado y funciona
- [ ] Emails se envían correctamente
- [ ] No hay regresiones en otros componentes
- [ ] Tests pasan todos
- [ ] Documentación del bug y fix actualizada

---

**Versión**: 1.0
