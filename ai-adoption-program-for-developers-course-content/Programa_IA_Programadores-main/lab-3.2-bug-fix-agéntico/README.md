# Lab 3.2: Resolución de Bug Complejo con Diagnóstico Asistido

## Objetivo

Resolver un bug complejo usando workflow agéntico: diagnóstico asistido, fix con validación, iteración hasta resolución.

## Duración

**2-3 horas**

## Contexto

Bug reportado: "Los pagos se procesan dos veces ocasionalmente"

**Sistema complejo con:**
- Múltiples servicios involucrados
- Base de datos distribuida
- Cola de mensajes
- Lógica de negocio compleja

---

## Workflow Agéntico

### PLAN - Diagnóstico (45 min)

```
Analizar este bug complejo: "Pagos se procesan dos veces ocasionalmente"

CONTEXTO:
- Sistema de pagos distribuido
- Múltiples servicios: payment_service, order_service, notification_service
- Base de datos: PostgreSQL con transacciones
- Cola de mensajes: RabbitMQ

SÍNTOMAS:
- Ocurre ocasionalmente (no siempre)
- Afecta pagos procesados
- No hay patrón claro de reproducción

GENERA PLAN DE DIAGNÓSTICO:
1. Hipótesis posibles (race conditions, idempotencia, transacciones)
2. Archivos a revisar
3. Logs a analizar
4. Tests a crear para reproducir
5. Estrategia de debugging
```

### ACT - Investigación y Fix (90 min)

**Fase 1: Investigación asistida**
```
Revisar código de payment_service buscando:
- Posibles race conditions
- Problemas de idempotencia
- Manejo de transacciones
- Manejo de mensajes duplicados

[Pegar código relevante]

¿Dónde está el problema? ¿Qué está causando el doble procesamiento?
```

**Fase 2: Crear test que reproduce bug**
```
Crear test que reproduce el bug de doble procesamiento:
- Simular condición de carrera
- Test debe fallar demostrando el bug
- Test debe ser determinístico o al menos probabilístico
```

**Fase 3: Implementar fix**
```
Implementar fix para problema identificado:
- Asegurar idempotencia
- Prevenir doble procesamiento
- Mantener funcionalidad correcta

Fix debe hacer que test de reproducción pase.
```

### OBSERVE - Validación (30 min)

```
Validar fix del bug:

1. ¿Test de reproducción pasa ahora?
2. ¿Tests existentes siguen pasando?
3. ¿No hay regresiones?
4. ¿Fix es correcto y robusto?
5. ¿Se han considerado todos los casos edge?
```

### REFLECT - Refinamiento (15 min)

```
Reflexionar sobre fix:

- ¿El fix es la mejor solución?
- ¿Hay mejor manera de resolverlo?
- ¿Qué aprendimos sobre el bug?
- ¿Cómo prevenir bugs similares?
```

---

## Criterios de Aceptación

- [ ] Root cause identificado correctamente
- [ ] Test que reproduce bug creado
- [ ] Fix implementado y funciona
- [ ] Todos los tests pasan
- [ ] No hay regresiones
- [ ] Documentación del bug y fix

---

**Versión**: 1.0
