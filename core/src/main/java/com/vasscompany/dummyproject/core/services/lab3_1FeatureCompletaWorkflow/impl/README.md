# Lab 3.1: Feature Completa desde Ticket hasta PR (Workflow Agéntico) — Java / AEM Like

## Objetivo

Implementar una **feature completa** dentro del proyecto dummy usando un workflow agéntico
**Plan → Act → Observe → Reflect**, desde el análisis del ticket hasta dejar una **PR lista para merge**,
aterrizada al contexto real de un proyecto **Java + AEM**.

La meta de este laboratorio no es solo “generar código”, sino aprender a trabajar con IA de forma
estructurada durante todo el ciclo de desarrollo:

- análisis funcional y técnico,
- diseño de la solución,
- implementación por capas,
- tests,
- documentación,
- validación,
- y preparación final de la PR.

---

## Duración Estimada

**4-5 horas**

---

## Prerequisitos

- ✅ Lección 1 completada
- ✅ Lección 2 completada
- ✅ GitHub Copilot Chat, Azure OpenAI o herramienta equivalente disponible
- ✅ Proyecto `vass-university-dummyproject` compilando localmente
- ✅ Conocimiento básico de:
    - Java 21
    - OSGi Declarative Services
    - Sling Servlets
    - tests con JUnit 5 / AEM Mocks
    - estructura Maven multi-módulo del proyecto

Comprobación mínima:

```bash
mvn clean install
```

---

## Contexto del Proyecto

Este laboratorio debe resolverse **siguiendo la arquitectura y patrones del proyecto dummy**:

- Arquitectura por capas entre presentación, aplicación/servlets, servicios y acceso a contenido JCR
- Servicios implementados en `core/src/main/java/.../services/`
- Configuración OSGi en `ui.config/.../osgiconfig/`
- Tests de integración en `it.tests`
- Tests UI en `ui.tests`
- Uso de **OSGi Declarative Services**, **inyección de dependencias**, **ResourceResolver** y patrones AEM

No trabajes como si fuera una app Spring Boot o un script Java puro. El objetivo es que la solución
quede **plausible en un proyecto AEM real**.

---

## Ticket del Laboratorio

### Ticket funcional

**"Agregar sistema de notificaciones por email para solicitudes enviadas desde el portal"**

### Requisitos funcionales

- Enviar email cuando una solicitud es creada
- Enviar email cuando la solicitud cambia de estado
- Permitir templates de email configurables
- Registrar trazabilidad de emails enviados

### Requisitos técnicos AEM-like

- La entrada de la solicitud se realizará mediante un **Servlet Sling**
- La lógica de negocio debe vivir en **servicios OSGi**
- La configuración de templates/remitente/destinatarios debe ir por **OSGi config**
- El logging debe usar **SLF4J**
- Los tests deben cubrir servicio y servlet
- La solución debe quedar lista para una **PR revisable**

---

## Resultado Esperado del Laboratorio

Al finalizar, deberías haber construido una feature parecida a esta:

```text
core/
└── src/main/java/com/vasscompany/dummyproject/core/
    ├── models/lab3_1FeatureCompletaWorkflow/
    │   └── RequestNotificationResult.java
    ├── services/lab3_1FeatureCompletaWorkflow/
    │   ├── RequestNotificationService.java
    │   ├── RequestStatusService.java
    │   ├── RequestRepository.java
    │   └── EmailTemplateRenderer.java
    ├── services/lab3_1FeatureCompletaWorkflow/impl/
    │   ├── RequestNotificationServiceImpl.java
    │   ├── RequestStatusServiceImpl.java
    │   ├── InMemoryRequestRepositoryImpl.java
    │   └── EmailTemplateRendererImpl.java
    └── servlets/lab3_1FeatureCompletaWorkflow/
        └── RequestNotificationServlet.java

core/
└── src/test/java/com/vasscompany/dummyproject/core/
    ├── services/lab3_1FeatureCompletaWorkflow/impl/
    │   ├── RequestNotificationServiceImplTest.java
    │   └── RequestStatusServiceImplTest.java
    └── servlets/lab3_1FeatureCompletaWorkflow/
        └── RequestNotificationServletTest.java

ui.config/
└── src/main/content/jcr_root/apps/vass-university-dummyproject/osgiconfig/config/
    └── com.vasscompany.dummyproject.core.services.lab3_1FeatureCompletaWorkflow.impl.RequestNotificationServiceImpl.cfg.json

it.tests/
└── src/test/java/...
    └── RequestNotificationIT.java
```

No es obligatorio usar exactamente estos nombres, pero sí una estructura equivalente y coherente.

---

## Workflow Agéntico Completo

---

## Fase 1 — PLAN (30-45 min)

En esta fase debes usar la IA como **arquitecto y planificador técnico**, no como generador directo
sin control.

### Qué debes hacer

1. Leer el ticket completo
2. Identificar requisitos funcionales y no funcionales
3. Mapear el ticket a componentes AEM reales
4. Diseñar la solución por capas
5. Definir estrategia de testing
6. Definir estrategia de documentación
7. Identificar riesgos técnicos

### Prompt recomendado para IA

Usa este prompt adaptado al proyecto:

```text
Actúa como arquitecto de software senior especializado en Java, AEM 6.5+/AEM-like,
OSGi Declarative Services y Sling.

Analiza este ticket y genera un plan detallado de implementación.

TICKET:
Agregar sistema de notificaciones por email para solicitudes enviadas desde el portal.

REQUISITOS:
- Enviar email cuando una solicitud es creada
- Enviar email cuando la solicitud cambia de estado
- Templates de email configurables
- Logging de emails enviados

CONTEXTO DEL PROYECTO:
- Stack: Java 21, Maven multi-módulo, AEM-like
- Módulos: core, ui.apps, ui.config, ui.content, it.tests, ui.tests
- Patrones: Layered Architecture, Dependency Injection con OSGi, servlets Sling,
  configuración OSGi, separación entre servlet y lógica de negocio
- Logging con SLF4J
- Tests con JUnit 5 y AEM Mocks

GENERA:
1. Análisis funcional del ticket
2. Diseño de solución por componentes
3. Interfaces y clases sugeridas
4. Flujo de datos extremo a extremo
5. Plan de implementación paso a paso
6. Plan de testing unitario e integración
7. Plan de documentación
8. Riesgos técnicos y decisiones de diseño
9. Checklist de validación final antes de PR

Importante:
- No propongas Spring Boot
- No mezcles lógica de negocio en el servlet
- Usa terminología y patrones propios de AEM/OSGi
- Si hace falta persistencia, usa una abstracción tipo repositorio adaptada al contexto AEM
```

### Output esperado

Debes crear un documento markdown, por ejemplo:

- `plan_lab_3.1.md`

Ese plan debe incluir, como mínimo:

- componentes a crear,
- responsabilidades,
- dependencias entre clases,
- orden recomendado de implementación,
- pruebas necesarias,
- y riesgos.

### Señales de buen plan

- El servlet solo orquesta entrada/salida HTTP
- La lógica de negocio vive en servicios OSGi
- La configuración vive en OSGi config
- Los tests están pensados desde el inicio
- Se contempla trazabilidad y manejo de errores

---

## Fase 2 — ACT (120-180 min)

Ahora sí, implementa la solución **siguiendo el plan**.

La clave aquí es **no pedirle a la IA toda la feature de una vez**. Trabaja por bloques pequeños.

### Orden sugerido de implementación

#### Paso 2.1 — Crear contratos e interfaces

Empieza por las interfaces principales:

- `RequestNotificationService`
- `RequestStatusService`
- `RequestRepository`
- `EmailTemplateRenderer`

### Prompt sugerido

```text
Genera primero las interfaces Java para esta feature AEM-like.

Necesito contratos limpios y mantenibles para:
- servicio de notificación de solicitudes,
- servicio de cambio de estado,
- repositorio de solicitudes,
- renderizado de templates de email.

Requisitos:
- Java 21
- nombres claros
- Javadoc útil
- orientado a AEM/OSGi
- evitar acoplamiento innecesario
- no poner lógica en interfaces
```

---

#### Paso 2.2 — Implementar modelos de datos necesarios

Por ejemplo:

- `RequestNotificationResult`
- `RequestData`
- `RequestStatus`
- `NotificationType`

Puedes usar `enum` cuando tenga sentido.

### Buenas prácticas

- Usa nombres semánticos
- Evita `Map<String, Object>` cuando puedas modelarlo mejor
- Centraliza estados y tipos en enums
- Documenta campos ambiguos

---

#### Paso 2.3 — Implementar configuración OSGi

La configuración debería cubrir, como mínimo:

- feature enabled/disabled
- remitente por defecto
- destinatarios por defecto
- asunto para creación
- asunto para cambio de estado
- template para creación
- template para cambio de estado
- si se habilita o no el logging funcional adicional

### Prompt sugerido

```text
Genera la configuración OSGi para un servicio AEM-like de notificaciones por email.

Necesito:
- @ObjectClassDefinition
- @AttributeDefinition
- activate/modified
- valores por defecto razonables
- nombres de propiedades claros

La configuración será usada por un servicio OSGi de notificación de solicitudes.
```

---

#### Paso 2.4 — Implementar servicio principal

Implementa `RequestNotificationServiceImpl`.

### Responsabilidades sugeridas

- validar si la feature está habilitada,
- recibir tipo de evento,
- resolver asunto y template,
- renderizar mensaje,
- invocar mecanismo de envío,
- registrar logs funcionales,
- devolver resultado claro.

### No debería hacer

- parsear directamente la request HTTP,
- contener lógica de servlet,
- usar `System.out.println`,
- depender de strings mágicos,
- tragarse excepciones silenciosamente.

---

#### Paso 2.5 — Implementar servicio de estado

Implementa `RequestStatusServiceImpl` para encapsular el cambio de estado.

Responsabilidades:

- validar transiciones de estado,
- actualizar repositorio,
- disparar notificación cuando aplique.

Esto ayuda a que la lógica de “cambio de estado + notificación” no quede dispersa.

---

#### Paso 2.6 — Implementar repositorio o abstracción de persistencia

No necesitas montar una persistencia compleja real. Lo importante es separar responsabilidad.

Opciones válidas para el laboratorio:

- repositorio en memoria para facilitar el ejercicio,
- abstracción que simule persistencia,
- o una implementación simple adaptada al repositorio JCR si quieres complicarlo más.

Lo importante es que la lógica de notificación **no dependa directamente del servlet**.

---

#### Paso 2.7 — Implementar servlet

Crea un servlet, preferiblemente orientado a resource type si el caso encaja, o por path si el
laboratorio lo requiere.

### Responsabilidades del servlet

- leer parámetros de entrada,
- validar datos mínimos,
- invocar servicio,
- devolver respuesta JSON o texto estructurado,
- mapear errores funcionales a códigos HTTP razonables.

### Recomendación

Si usas path servlet, deja documentado que en proyectos reales AEM suele preferirse resource type
binding cuando aplica.

### Prompt sugerido

```text
Genera un Sling Servlet AEM-like para crear solicitudes y activar notificaciones.

Requisitos:
- Java 21
- usar SlingAllMethodsServlet o SlingSafeMethodsServlet según corresponda
- no meter lógica de negocio en el servlet
- delegar a servicios OSGi
- responder con JSON simple
- validar inputs obligatorios
- logging correcto con SLF4J
- código legible y mantenible
```

---

#### Paso 2.8 — Documentar mientras implementas

No dejes la documentación para el final.

Mientras implementas, ve actualizando:

- `plan_lab_3.1.md`
- `README.md` del laboratorio si anotas decisiones
- `PR_DESCRIPTION_lab_3.1.md`
- notas técnicas o ADR ligera si lo necesitas

---

## Fase 3 — OBSERVE (30 min)

Aquí validas lo que has construido. No es una fase pasiva: es donde verificas si la feature realmente
cumple el ticket y si la implementación sigue siendo coherente.

### Checklist técnico

Revisa lo siguiente:

1. ¿Compila el proyecto?
2. ¿Los tests unitarios pasan?
3. ¿El servlet delega correctamente en servicios?
4. ¿La configuración OSGi está desacoplada del código?
5. ¿Los templates se resuelven correctamente?
6. ¿Se registran logs útiles sin exponer datos sensibles?
7. ¿Las transiciones de estado están validadas?
8. ¿La solución sigue el plan original?
9. ¿La feature está suficientemente documentada?
10. ¿La PR sería entendible por otro desarrollador del equipo?

### Comandos sugeridos

```bash
mvn test
mvn clean install
```

Si decides ampliar:

```bash
mvn -pl core test
mvn -pl it.tests verify
```

### Prompt sugerido para revisión con IA

```text
Actúa como reviewer técnico senior de Java/AEM.

Quiero que valides esta implementación respecto al ticket original.

Revisa:
- cumplimiento funcional,
- separación de responsabilidades,
- riesgos de diseño,
- problemas de testabilidad,
- errores de configuración OSGi,
- code smells,
- puntos débiles para una PR.

Devuélveme:
1. problemas críticos,
2. mejoras recomendadas,
3. si está lista o no para PR.
```

---

## Fase 4 — REFLECT (30-45 min)

Esta fase sirve para **mejorar conscientemente** lo que has construido.

No se trata solo de corregir bugs, sino de analizar:

- qué decidió bien la IA,
- qué tuviste que corregir,
- qué parte de la solución se volvió demasiado compleja,
- qué habría que cambiar antes de abrir PR.

### Preguntas de reflexión

Responde en un archivo, por ejemplo `reflexion_lab_3.1.md`:

- ¿Qué parte resolvió mejor la IA: diseño, implementación, tests o documentación?
- ¿Qué partes tuviste que corregir manualmente?
- ¿Dónde propuso algo demasiado genérico o poco AEM-like?
- ¿Qué riesgos no detectó inicialmente?
- ¿Qué prompt funcionó mejor?
- ¿Qué harías diferente en una segunda iteración?
- ¿Cuánto tiempo ahorraste frente a hacerlo completamente manual?

### Prompt sugerido

```text
Ayúdame a reflexionar sobre este laboratorio de implementación completa en Java/AEM.

Quiero analizar:
- aciertos de la IA,
- errores de la IA,
- correcciones manuales necesarias,
- mejoras futuras del workflow,
- lecciones aprendidas para próximos tickets.

La reflexión debe ser honesta, técnica y concreta.
```

---

## Testing Recomendado

Este laboratorio debe reforzar la idea de que una feature no está “terminada” hasta que está validada.

### 1. Tests unitarios de servicios

Cubre como mínimo:

- envío en creación de solicitud,
- envío en cambio de estado,
- feature deshabilitada por configuración,
- template inexistente o vacío,
- destinatarios vacíos,
- transición de estado no válida,
- repositorio devolviendo error,
- logging de resultado correcto.

### 2. Tests del servlet

Cubre como mínimo:

- request válida,
- parámetros obligatorios ausentes,
- error de validación,
- respuesta correcta HTTP,
- delegación al servicio.

### 3. Tests de integración

Si quieres llevar el laboratorio a un nivel más completo, añade en `it.tests` una prueba de flujo
mínimo end-to-end.

---

## Seguridad y Calidad

Aplica este mini-checklist durante el desarrollo:

- [ ] No usar `getAdministrativeResourceResolver()`
- [ ] No hardcodear emails reales, credenciales o secretos
- [ ] No exponer datos sensibles en logs
- [ ] Validar inputs del servlet antes de invocar servicios
- [ ] No capturar `Exception` de forma genérica sin contexto
- [ ] No mezclar persistencia, render de template y transporte HTTP en una sola clase
- [ ] No dejar configuración funcional hardcodeada dentro del servicio
- [ ] Manejar correctamente estados inválidos y errores funcionales

---

## Entregables

Al finalizar el laboratorio, este paquete o área de trabajo debería contener como mínimo:

- ✅ `plan_lab_3.1.md` — plan generado en la fase PLAN
- ✅ Interfaces y clases de la feature
- ✅ Implementación funcional de la feature
- ✅ Configuración OSGi asociada
- ✅ Tests unitarios de servicios
- ✅ Tests del servlet
- ✅ `PR_DESCRIPTION_lab_3.1.md` — descripción de PR lista para usar
- ✅ `reflexion_lab_3.1.md` — reflexión del proceso

### Ejemplo de PR description

Puedes crear algo así:

```markdown
# PR: Sistema de notificaciones por email para solicitudes

## Qué se ha hecho
- Se añade servicio de notificación por email para creación de solicitudes
- Se añade notificación ante cambio de estado
- Se externaliza configuración en OSGi
- Se añaden tests unitarios y de servlet

## Archivos principales
- RequestNotificationService
- RequestNotificationServiceImpl
- RequestStatusServiceImpl
- RequestNotificationServlet
- Config OSGi asociada

## Cómo probar
1. Desplegar paquete
2. Configurar OSGi
3. Invocar servlet de creación
4. Verificar logs y respuesta

## Riesgos / Consideraciones
- El envío real de email está abstraído / simulado en este laboratorio
- Revisar parametrización antes de usar en un entorno real
```

---

## Criterios de Aceptación

El laboratorio se considera completo cuando:

- [ ] La feature implementada cumple el ticket
- [ ] Existe un plan previo razonable y documentado
- [ ] El código está separado por responsabilidades
- [ ] El servlet no contiene lógica de negocio significativa
- [ ] La configuración está externalizada vía OSGi
- [ ] Los tests pasan correctamente
- [ ] La documentación está sincronizada con el código
- [ ] Existe una PR description reutilizable
- [ ] Se ha realizado reflexión final del proceso
- [ ] Se ha usado explícitamente el workflow Plan → Act → Observe → Reflect

---

## Evaluación

### Este laboratorio habrá sido exitoso si:

- has sido capaz de mantener control sobre la IA durante todo el flujo,
- el resultado parece una feature real de un proyecto Java/AEM,
- la solución es revisable y mantenible,
- y has aprendido a usar IA no solo para “escribir código”, sino para conducir el ciclo completo
  desde ticket hasta PR.

---

## Recomendación de Enfoque

No intentes terminar el laboratorio “de una sentada” pidiendo a la IA una solución monolítica.

Trabaja así:

1. planifica,
2. implementa por piezas,
3. valida cada pieza,
4. corrige,
5. documenta,
6. prepara PR,
7. reflexiona.

Ese es precisamente el aprendizaje más valioso de este lab.

---

**Versión**: 1.0
