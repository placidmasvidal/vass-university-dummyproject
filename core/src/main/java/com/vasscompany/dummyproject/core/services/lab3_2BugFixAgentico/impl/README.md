# Lab 3.2: Bug Fix Agéntico con Diagnóstico Asistido (Java / AEM Like)

## Objetivo

Resolver un **bug complejo e intermitente** usando un workflow agéntico completo:

**Plan → Act → Observe → Reflect**

En esta adaptación a **Java / AEM-like**, el objetivo no es depurar una aplicación genérica,
sino investigar un problema realista en un flujo distribuido dentro de un proyecto AEM:

- entrada HTTP vía **Sling Servlet**,
- lógica de negocio en **servicios OSGi**,
- persistencia o trazabilidad en repositorio,
- disparo de notificaciones,
- configuración externa mediante **OSGi config**,
- y tests que reproduzcan el fallo antes del fix.

La meta del laboratorio es aprender a usar IA como apoyo para:

- formular hipótesis técnicas,
- reconstruir un flujo multiarchivo,
- encontrar la **root cause** real,
- diseñar un fix robusto,
- validar el resultado,
- y dejar evidencia técnica del bug y de su resolución.

---

## Duración Estimada

**2-3 horas**

---

## Prerequisitos

- ✅ Haber completado el lab 3.1 o estar familiarizado con el workflow agéntico
- ✅ Proyecto `vass-university-dummyproject` compilando localmente
- ✅ Herramienta IA disponible (Copilot Chat, ChatGPT, Azure OpenAI o equivalente)
- ✅ Conocimiento básico de:
    - Java 21
    - OSGi Declarative Services
    - Sling Servlets
    - JUnit 5 / Mockito / AEM Mocks
    - estructura Maven multi-módulo del proyecto

Comprobación mínima:

```bash
mvn clean install
```

---

## Contexto del Proyecto

Este laboratorio debe resolverse respetando la arquitectura del proyecto dummy:

- módulo `core` para la lógica Java,
- servicios en `core/.../services/`,
- servlets en `core/.../servlets/`,
- configuración OSGi en `ui.config/.../osgiconfig/`,
- tests unitarios en `core/src/test/java`,
- tests de integración en `it.tests`,
- posible validación end-to-end en `ui.tests` si aplica.

No trabajes como si fuera una app Spring Boot ni como si todo ocurriera en una sola clase.
El bug debe analizarse como un problema de **interacción entre capas**, algo muy habitual en AEM.

---

## Bug Reportado del Laboratorio

### Reporte funcional

> **"Las solicitudes se procesan dos veces ocasionalmente"**

### Síntomas observados

- A veces una misma petición genera **dos registros** o dos cambios de estado.
- En algunos casos también se envían **dos emails** de confirmación.
- No ocurre siempre.
- No hay un patrón evidente al reproducirlo manualmente.
- El problema aparece sobre todo en escenarios con reintentos, doble clic o concurrencia.

### Por qué este bug es interesante

Porque raramente se explica por un solo archivo. En un proyecto AEM-like puede estar causado por:

- ausencia de **idempotencia** en el servicio,
- condición de carrera entre “comprobar si existe” y “crear” el registro,
- doble invocación del servlet,
- reintentos de un job o notificación,
- evento duplicado,
- lógica de persistencia no atómica,
- excepciones capturadas de forma silenciosa,
- tests que no cubren concurrencia,
- logging insuficiente para seguir el flujo.

Este laboratorio entrena justo ese tipo de investigación.

---

## Escenario Java / AEM-like Propuesto

Vas a trabajar sobre un flujo equivalente a este:

1. Un **Servlet Sling** recibe una petición `POST` para crear una solicitud.
2. El servlet delega en `RequestProcessingService`.
3. El servicio valida los datos y construye una operación de alta.
4. Un repositorio o servicio de persistencia registra la solicitud.
5. Un servicio de notificación dispara el email o el evento asociado.
6. El resultado se devuelve al cliente.

### Comportamiento esperado

Para una misma petición funcional, el sistema debería:

- procesarla **una sola vez**,
- crear **un único registro**,
- enviar **una única notificación**,
- y responder con trazabilidad clara del resultado.

### Comportamiento defectuoso

Bajo ciertas condiciones, la misma solicitud se procesa dos veces.

Consecuencias posibles:

- duplicados en repositorio,
- doble trazabilidad,
- doble email,
- estados inconsistentes,
- comportamiento no determinista.

---

## Estructura Sugerida del Laboratorio

```text
core/src/main/java/com/vasscompany/dummyproject/core/services/lab3_2BugFixAgentico/
├── RequestSubmission.java
├── RequestProcessingResult.java
├── RequestProcessingService.java
├── RequestRepositoryService.java
├── RequestNotificationService.java
└── impl/
    ├── RequestProcessingServiceImpl.java
    ├── RequestRepositoryServiceImpl.java
    ├── RequestNotificationServiceImpl.java
    └── README.md

core/src/main/java/com/vasscompany/dummyproject/core/servlets/lab3_2BugFixAgentico/
└── RequestSubmissionServlet.java

core/src/test/java/com/vasscompany/dummyproject/core/services/lab3_2BugFixAgentico/impl/
├── RequestProcessingServiceImplTest.java
├── RequestProcessingServiceConcurrencyTest.java
└── RequestNotificationServiceImplTest.java

ui.config/src/main/content/jcr_root/apps/vass-university-dummyproject/osgiconfig/config/
└── com.vasscompany.dummyproject.core.services.lab3_2BugFixAgentico.impl.RequestProcessingServiceImpl.cfg.json

.copilot-context/
└── bug_lab_3_2_context.md
```

> Los nombres concretos pueden variar, pero mantén el patrón:
> **entrada HTTP → servicio principal → persistencia → notificación → validación/test**.

---

## Hipótesis Técnicas Iniciales

Antes de investigar el código, parte de hipótesis plausibles.

### Hipótesis 1: falta de idempotencia

La operación de crear solicitud no protege contra reenvíos de la misma petición.

Ejemplos:

- el frontend hace doble submit,
- el usuario pulsa dos veces,
- hay un retry del cliente,
- o una pasarela intermedia repite la llamada.

### Hipótesis 2: race condition

El servicio hace algo parecido a esto:

```java
if (!repository.exists(requestId)) {
    repository.save(request);
}
```

Si dos hilos entran casi a la vez, ambos podrían pasar la comprobación y ambos guardar.

### Hipótesis 3: reintento de notificación o job

La persistencia se ejecuta una vez, pero el bloque de notificación o evento se dispara dos veces,
generando la sensación de doble procesamiento.

### Hipótesis 4: servlet o filtro reinyectando la petición

Puede haber una doble llamada indirecta:

- retry de cliente,
- reenvío desde JS,
- reintento técnico,
- o mala separación entre dos capas.

### Hipótesis 5: logging actual insuficiente

El problema no es solo el bug, sino que no hay correlación suficiente para saber:

- cuántas veces entra el servlet,
- cuántas veces procesa el servicio,
- cuántas veces persiste,
- y cuántas veces se notifica.

---

## Resultado Esperado

Al finalizar deberías haber completado, como mínimo:

- diagnóstico razonado del bug,
- test que reproduzca el problema,
- fix implementado,
- validación de no regresión,
- documentación técnica del incidente.

### Entregables sugeridos

```text
lab3_2_diagnostico.md
lab3_2_fix_notes.md
core/.../RequestProcessingServiceImpl.java
core/.../RequestProcessingServiceImplTest.java
core/.../RequestProcessingServiceConcurrencyTest.java
```

---

## Workflow Agéntico del Laboratorio

---

## Fase 1 — PLAN (30-45 min)

En esta fase usarás la IA como asistente de diagnóstico.

No le pidas el fix todavía. Primero debe ayudarte a:

1. entender el bug,
2. formular hipótesis,
3. localizar archivos,
4. decidir qué logs faltan,
5. y proponer una estrategia de reproducción.

### Tareas

1. Lee el bug reportado.
2. Localiza todos los archivos implicados.
3. Resume el flujo extremo a extremo.
4. Identifica puntos donde puede duplicarse la operación.
5. Prepara un archivo de contexto para la IA.

### Archivo de contexto sugerido

```text
.copilot-context/bug_lab_3_2_context.md
```

### Contenido sugerido del contexto

```markdown
# Bug Lab 3.2

## Bug reportado
Las solicitudes se procesan dos veces ocasionalmente.

## Síntomas
- Duplicados de registro
- A veces doble email
- Ocurre de forma intermitente

## Flujo esperado
1. POST al servlet
2. Delegación al servicio OSGi
3. Validación
4. Persistencia
5. Notificación
6. Respuesta única

## Archivos relevantes
- RequestSubmissionServlet.java
- RequestProcessingService.java
- RequestProcessingServiceImpl.java
- RequestRepositoryService.java
- RequestRepositoryServiceImpl.java
- RequestNotificationService.java
- RequestNotificationServiceImpl.java
- cfg.json del servicio
- tests existentes

## Hipótesis iniciales
- falta de idempotencia
- race condition
- notificación duplicada
- retry no controlado
- logging insuficiente
```

### Prompt recomendado para PLAN

```text
Actúa como un ingeniero senior especializado en Java, AEM 6.5+/AEM-like,
OSGi Declarative Services, Sling Servlets y debugging de sistemas concurrentes.

Estoy investigando este bug:
"Las solicitudes se procesan dos veces ocasionalmente"

CONTEXTO DEL PROYECTO:
- Proyecto Maven multi-módulo AEM-like
- Servlet Sling de entrada
- Servicio OSGi principal de procesamiento
- Persistencia tipo repositorio/servicio
- Servicio de notificación
- Configuración OSGi
- Tests con JUnit 5 y AEM Mocks

SÍNTOMAS:
- El bug es intermitente
- A veces hay dos registros
- A veces también doble email
- No siempre se reproduce

QUIERO QUE GENERES:
1. Hipótesis técnicas plausibles ordenadas por probabilidad
2. Archivos y métodos a revisar primero
3. Logging adicional que conviene añadir para diagnosticar
4. Tests que podrían reproducir el fallo
5. Estrategia de debugging paso a paso
6. Señales que diferenciarían un problema de idempotencia, concurrencia o notificación duplicada

Importante:
- No propongas Spring Boot
- Piensa en términos AEM/OSGi
- Separa claramente servlet, servicio, persistencia y notificación
```

### Output esperado de la fase PLAN

Debes obtener de la IA un plan que responda, como mínimo, a estas preguntas:

- ¿Dónde es más probable que esté el bug?
- ¿Qué hipótesis conviene validar primero?
- ¿Qué instrumentación mínima hay que añadir?
- ¿Qué test debería escribirse antes del fix?

---

## Fase 2 — ACT (60-90 min)

Aquí empieza la investigación real y, después, la implementación del fix.

La fase ACT conviene dividirla en 3 bloques.

---

### ACT.1 — Investigación guiada

### Qué debes revisar

#### 1. Servlet

Busca cosas como:

- ausencia de validación o correlación de petición,
- múltiples llamadas al mismo servicio,
- lógica duplicada,
- respuesta enviada antes de completar el flujo,
- falta de identificador de petición.

#### 2. Servicio principal

Es el candidato más fuerte.

Busca cosas como:

- `exists + save` no atómico,
- ausencia de control de idempotencia,
- no uso de `requestId` o `correlationId`,
- invocación duplicada de persistencia o notificación,
- código no thread-safe.

#### 3. Persistencia / repositorio

Revisa si:

- el criterio de unicidad es débil,
- el guardado permite duplicados,
- no hay comprobaciones por clave funcional,
- hay condiciones de carrera entre lectura y escritura.

#### 4. Notificación

Verifica si el doble síntoma puede venir de aquí:

- email enviado dos veces aunque la persistencia sea única,
- reintento sin deduplicación,
- evento publicado dos veces,
- doble listener.

#### 5. Configuración OSGi

Comprueba si:

- hay flags de retry activos,
- hay configuración inconsistente entre entornos,
- hay propiedades mal nombradas o valores ambiguos.

### Prompt recomendado para investigar código

```text
Estoy revisando este código para el bug:
"Las solicitudes se procesan dos veces ocasionalmente"

Analiza el siguiente fragmento y dime:
1. Qué riesgo ves
2. Si hay problemas de concurrencia o idempotencia
3. Si podría explicar el doble procesamiento
4. Qué cambio propondrías
5. Qué test escribirías para demostrar el problema

[COPIA AQUÍ RequestProcessingServiceImpl.java y, si hace falta,
fragmentos del servlet, repositorio o notificación]
```

---

### ACT.2 — Crear el test que reproduce el bug

Antes del fix, debes tener una forma de demostrar el fallo.

### Estrategias válidas

#### Opción A: test determinístico con dobles controlados

Diseña mocks o fakes para forzar la condición problemática.

Ejemplo:

- `repository.exists()` devuelve `false` en dos hilos,
- ambos hilos ejecutan `save()`,
- el test demuestra doble persistencia.

#### Opción B: test de concurrencia

Usa utilidades Java como:

- `ExecutorService`
- `CountDownLatch`
- `CyclicBarrier`
- `AtomicInteger`

para lanzar varias ejecuciones simultáneas sobre el mismo identificador funcional.

#### Opción C: test probabilístico controlado

Si no puedes hacerlo 100% determinístico, intenta acotar al máximo la ventana crítica.

### Prompt recomendado para el test

```text
Necesito crear un test en Java que reproduzca este bug intermitente:
"Las solicitudes se procesan dos veces ocasionalmente"

CONTEXTO:
- Servicio principal: RequestProcessingServiceImpl
- Posible causa: falta de idempotencia / race condition
- Stack de tests: JUnit 5, Mockito, AEM Mocks si aporta valor

Quiero que propongas un test que:
1. Reproduzca el doble procesamiento
2. Sea lo más determinístico posible
3. Verifique duplicado de persistencia o notificación
4. Sirva como red de seguridad antes del fix

Sugiere también qué dependencias mockear y qué sincronización usar.
```

### Señales de que el test está bien planteado

- falla antes del fix,
- pasa después del fix,
- demuestra el problema real,
- no depende de sleeps arbitrarios si pueden evitarse,
- y deja claro si el duplicado está en persistencia, negocio o notificación.

---

### ACT.3 — Implementar el fix

Solo después de entender el bug y reproducirlo.

### Tipos de fix plausibles

#### Fix tipo 1: idempotencia por clave funcional

Usar un identificador único de operación:

- `requestId`,
- `correlationId`,
- combinación funcional de campos,
- token idempotente recibido o generado.

#### Fix tipo 2: guardado atómico

Evitar el patrón inseguro:

```java
if (!exists) {
    save();
}
```

Y sustituirlo por una operación única o protegida.

#### Fix tipo 3: deduplicación de notificaciones

Si la persistencia está bien pero la notificación se dispara dos veces,
la solución puede estar en esa capa.

#### Fix tipo 4: bloqueo o sincronización bien justificada

Puede ser válido, pero úsalo con prudencia.
No se trata de meter `synchronized` sin entender el impacto.

### Prompt recomendado para el fix

```text
Ya tengo identificada la causa probable del bug y un test que lo reproduce.
Necesito implementar un fix robusto en Java / AEM-like.

Objetivo:
- evitar doble procesamiento
- preservar el comportamiento correcto
- no introducir regresiones
- mantener el diseño limpio por capas

Analiza este código y propón un fix concreto.
Explica:
1. Qué cambio harías
2. Por qué resuelve el bug
3. Qué trade-offs tiene
4. Qué casos edge cubre
5. Qué tests adicionales conviene añadir

[COPIA AQUÍ el código del servicio y el test que reproduce el fallo]
```

---

## Fase 3 — OBSERVE (20-30 min)

Ahora toca validar que el fix realmente resuelve el problema.

### Checklist de validación

1. ¿El test de reproducción pasa ahora?
2. ¿Los tests existentes siguen pasando?
3. ¿Se evita el doble guardado?
4. ¿Se evita la doble notificación?
5. ¿No se ha roto el flujo normal?
6. ¿El logging deja mejor trazabilidad que antes?
7. ¿La solución sigue siendo mantenible?

### Validaciones recomendadas

- ejecutar tests unitarios del servicio,
- ejecutar el test de concurrencia,
- revisar logs con `requestId` o `correlationId`,
- probar escenarios nominales y duplicados,
- verificar que el servlet no asume responsabilidades indebidas.

### Prompt recomendado para OBSERVE

```text
He implementado este fix para evitar doble procesamiento.
Necesito que revises si la validación es suficiente.

Evalúa:
1. Si el fix realmente cubre la causa raíz
2. Si los tests propuestos son adecuados
3. Qué regresiones podrían quedar fuera
4. Qué edge cases faltan por comprobar
5. Si el logging y la trazabilidad son suficientes

[COPIA AQUÍ resumen del fix + tests]
```

---

## Fase 4 — REFLECT (10-15 min)

La última fase no es opcional. Aquí conviertes la resolución del bug en aprendizaje reutilizable.

### Preguntas de reflexión

- ¿La root cause estaba donde pensabas al principio?
- ¿El bug era de concurrencia, idempotencia o un síntoma mezclado?
- ¿Qué señales del código deberían haberte alertado antes?
- ¿Qué diseño habría evitado este problema desde el inicio?
- ¿Qué patrón o práctica quieres incorporar en futuros desarrollos?

### Prompt recomendado para REFLECT

```text
Ayúdame a reflexionar sobre este bug ya resuelto.

Quiero un análisis breve con:
1. Root cause real
2. Por qué el bug era difícil de detectar
3. Qué decisión de diseño lo hizo posible
4. Qué patrón o práctica habría evitado el problema
5. Qué checklist preventiva puedo usar en futuras PRs
```

### Resultado esperado de REFLECT

Debes dejar un pequeño documento, por ejemplo:

```text
lab3_2_fix_notes.md
```

con apartados como:

- resumen del bug,
- causa raíz,
- fix aplicado,
- tests añadidos,
- prevención futura.

---

## Ejemplos de Evidencia que Deberías Producir

### 1. Evidencia de hipótesis

- lista razonada de posibles causas,
- descarte de hipótesis incorrectas,
- puntos del flujo revisados.

### 2. Evidencia de reproducción

- test fallando antes del fix,
- logs o métricas que muestran duplicidad.

### 3. Evidencia de corrección

- test pasando después del fix,
- ausencia de duplicados,
- no regresiones.

### 4. Evidencia de calidad técnica

- separación por capas mantenida,
- no se mete lógica de negocio en el servlet,
- nombres claros,
- logs útiles,
- documentación mínima del incidente.

---

## Criterios de Aceptación

- [ ] Se ha identificado correctamente la **root cause**
- [ ] Se han revisado todas las capas relevantes del flujo
- [ ] Existe al menos un test que reproduce el bug
- [ ] El fix hace que ese test pase
- [ ] Los tests existentes siguen pasando
- [ ] No se introducen regresiones obvias
- [ ] El fix respeta la arquitectura Java / AEM-like del proyecto
- [ ] El logging y la trazabilidad han mejorado
- [ ] Se documenta el bug y su resolución

---

## Qué se considera una buena solución en este lab

Una buena solución no es solo “hacer que deje de fallar”.

Se considera buena si:

- resuelve la causa real y no solo el síntoma,
- está soportada por tests,
- es explicable en una review,
- encaja con la arquitectura del proyecto,
- y deja una base mejor para futuros cambios.

---

## Qué NO deberías hacer

- ❌ aplicar un parche sin reproducir antes el bug
- ❌ añadir sleeps arbitrarios como solución
- ❌ meter lógica de negocio en el servlet
- ❌ ocultar excepciones relevantes con logs pobres
- ❌ usar sincronización global sin justificarla
- ❌ considerar resuelto el problema solo porque “ya no lo veo” manualmente

---

## Sugerencia de Entrega Final

Tu entrega de este laboratorio puede incluir:

1. **Diagnóstico**
    - hipótesis
    - flujo investigado
    - causa raíz

2. **Implementación**
    - fix aplicado
    - clases modificadas
    - configuración ajustada si procede

3. **Validación**
    - test de reproducción
    - tests de no regresión
    - resultados

4. **Reflexión**
    - qué aprendiste
    - cómo evitar bugs similares

---

## Resumen

Este laboratorio te entrena en una habilidad crítica del desarrollo real:

> **resolver bugs complejos con contexto, método y evidencia**

En proyectos AEM/Java los fallos intermitentes rara vez viven en una sola clase.
Por eso el foco aquí está en usar IA con disciplina para investigar un flujo completo,
reproducir el bug, resolverlo con seguridad y dejar trazabilidad técnica del aprendizaje.

---

**Versión**: 1.0  
**Adaptación**: Java / AEM-like para `vass-university-dummyproject`
