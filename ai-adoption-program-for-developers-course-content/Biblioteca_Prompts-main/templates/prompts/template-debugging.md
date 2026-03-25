---
ID: PR-DEV-DB-001
Área / Práctica: Desarrollo
Subárea: Debugging
Nivel de madurez: 🟡 Intermedio
Tipo de prompt: Análisis
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Template: Prompt para Debugging

## Propósito

Template para generar prompts efectivos al debuggear problemas usando IA para identificar root cause y sugerir fixes.

## Template Base

```
Analizar y diagnosticar este bug: [descripción del bug]

SÍNTOMAS:
- [Síntoma 1: qué se observa]
- [Síntoma 2: comportamiento incorrecto]
- [Cuándo ocurre: siempre/ocasional/esporádico]
- [Frecuencia: cada vez / X% de las veces]

CONTEXTO:
- Sistema: [descripción del sistema]
- Componentes involucrados: [lista de módulos/servicios]
- Flujo afectado: [descripción del flujo de trabajo]

CÓDIGO RELEVANTE:
[Pegar código relacionado o referenciar archivos]

LOGS/ERRORES:
[Si hay logs o mensajes de error, incluirlos aquí]

COMPORTAMIENTO ESPERADO:
[Qué debería suceder en condiciones normales]

COMPORTAMIENTO ACTUAL:
[Qué está sucediendo realmente]

HIPÓTESIS INICIALES:
- [Hipótesis 1: posible causa]
- [Hipótesis 2: posible causa]
- [Hipótesis 3: posible causa]

DATOS ADICIONALES:
- [Datos de entrada cuando ocurre el bug]
- [Configuración relevante]
- [Ambiente: dev/staging/prod]

ANÁLISIS SOLICITADO:
1. ¿Cuál es el root cause más probable?
2. ¿Dónde está el problema exactamente? (archivo, línea, función)
3. ¿Qué está causando este comportamiento?
4. ¿Hay otros lugares con problemas similares?
5. ¿Cómo reproducir el bug de forma confiable?
6. ¿Qué fix recomiendas?
```

---

## Variaciones por Tipo de Bug

### Template para Bug de Lógica

```
Debuggear bug de lógica: [descripción]

CÓDIGO:
[Código con lógica sospechosa]

INPUT DE PRUEBA:
[Input que causa el problema]

OUTPUT ESPERADO:
[Qué debería retornar]

OUTPUT ACTUAL:
[Qué está retornando realmente]

PREGUNTA ESPECÍFICA:
¿Dónde está el error en la lógica? ¿Qué condición o cálculo está incorrecto?
```

### Template para Bug de Integración

```
Debuggear bug de integración entre [componente1] y [componente2]

COMPONENTE 1:
[Código/referencia del componente 1]

COMPONENTE 2:
[Código/referencia del componente 2]

PUNTO DE INTEGRACIÓN:
[Cómo se integran - API, eventos, cola, etc.]

ERROR OBSERVADO:
[Qué falla en la integración]

ANÁLISIS:
- ¿El problema es en la comunicación?
- ¿Es un problema de formato de datos?
- ¿Es un problema de timing/sincronización?
- ¿Es un problema de configuración?
```

### Template para Bug de Performance

```
Debuggear problema de performance: [descripción]

SÍNTOMA:
- [Operación que es lenta]
- Tiempo actual: [X segundos]
- Tiempo esperado: [Y segundos]

CÓDIGO RELEVANTE:
[Código de la operación lenta]

CONTEXTO:
- Volumen de datos: [tamaño]
- Frecuencia: [cuán seguido se ejecuta]
- Ambiente: [hardware/recursos disponibles]

ANÁLISIS:
- ¿Dónde está el cuello de botella?
- ¿Hay loops innecesarios?
- ¿Hay queries N+1?
- ¿Hay operaciones bloqueantes?
- ¿Qué optimizaciones recomiendas?
```

---

## Ejemplo de Uso Completo

```
Analizar y diagnosticar este bug: Los emails de confirmación no se envían cuando se crea un pedido

SÍNTOMAS:
- Pedidos se crean correctamente (aparecen en BD)
- Emails de confirmación NO se envían
- No hay errores visibles en logs
- Ocurre siempre (100% de los casos)

CONTEXTO:
- Sistema: E-commerce con microservicios
- Componentes: order_service, email_service, notification_queue
- Flujo: Pedido creado → Evento a cola → Email service procesa → Email enviado

CÓDIGO RELEVANTE:
# order_service.py - Creación de pedido
def create_order(order_data):
    order = Order.create(order_data)
    notification_queue.publish('order_created', order.id)
    return order

# email_service.py - Procesamiento de eventos
def process_order_created(event):
    order_id = event['order_id']
    order = get_order(order_id)
    send_confirmation_email(order)

LOGS:
[No hay logs de error, pero tampoco logs de emails enviados]

COMPORTAMIENTO ESPERADO:
1. Pedido creado
2. Evento 'order_created' publicado a cola
3. Email service recibe evento
4. Email de confirmación enviado

COMPORTAMIENTO ACTUAL:
1. Pedido creado ✓
2. [¿Evento publicado? - desconocido]
3. [¿Email service recibe evento? - desconocido]
4. Email NO enviado ✗

HIPÓTESIS:
1. Evento no se está publicando correctamente
2. Email service no está suscrito a la cola
3. Email service tiene bug y no procesa eventos
4. Configuración de cola incorrecta

ANÁLISIS SOLICITADO:
1. ¿Dónde está el problema exactamente?
2. ¿Qué está fallando en el flujo?
3. ¿Cómo verificar cada paso del flujo?
4. ¿Qué fix recomiendas?
```

---

## Tips para Debugging Efectivo

1. **Ser específico** - Más detalles = mejor diagnóstico
2. **Incluir contexto completo** - No solo el código, sino el flujo completo
3. **Mencionar hipótesis** - Ayuda a la IA a enfocarse
4. **Incluir logs/errores** - Mensajes de error son muy útiles
5. **Pedir análisis paso a paso** - Mejor que solo pedir "cuál es el bug"

---

**Versión**: 1.0  
**Última actualización**: 2026-01-15
