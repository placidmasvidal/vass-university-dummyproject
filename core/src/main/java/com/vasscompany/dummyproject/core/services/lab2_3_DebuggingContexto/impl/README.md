# Lab 2.3: Debugging Complejo con Contexto Multi-archivo (Java / AEM)

## Objetivo

Debuggear un bug complejo que involucra **múltiples archivos y capas del sistema** usando IA
con **contexto completo** del proyecto, en un entorno **Java / AEM-like**.

El objetivo no es adivinar el fallo mirando un único archivo, sino aprender a:

- reconstruir el flujo completo,
- abrir y analizar simultáneamente código, configuración, tests y logs,
- formular buenas preguntas a la IA con contexto suficiente,
- identificar la **root cause real**,
- aplicar un fix seguro,
- y validar que no se han introducido regresiones.

---

## Duración Estimada

**2-3 horas**

---

## Contexto del Ejercicio

En el laboratorio original en Python, el bug reportado es:

> **"Los pedidos se procesan pero los emails de confirmación no se envían"**

En esta adaptación al proyecto dummy, el equivalente será un flujo más cercano a un proyecto
**Java / AEM**, por ejemplo:

> **"Las solicitudes se guardan correctamente en el repositorio o se procesan por el servicio,
> pero el email de confirmación al usuario no se envía"**

### Bug reportado

Un usuario envía una solicitud desde un formulario o desde un servlet de negocio.
El sistema parece procesarla correctamente:

- la petición llega al servlet,
- los datos se validan,
- la solicitud se persiste o se marca como procesada,
- incluso puede quedar registrada en logs,

pero el **email de confirmación** que debería enviarse al usuario **nunca sale**.

### Por qué este bug es interesante

Porque no suele estar en un único punto. Puede deberse a cualquiera de estas causas:

- el servlet no invoca correctamente el servicio de notificación,
- el servicio principal construye mal el evento,
- la cola o el dispatcher de notificaciones no procesa el mensaje,
- el servicio de email se ejecuta con datos incompletos,
- la configuración OSGi tiene una propiedad desactivada o mal nombrada,
- el template o destinatario se resuelve mal,
- el código captura la excepción y la silencia,
- o el log existente no da visibilidad del fallo real.

Este laboratorio está pensado precisamente para entrenar ese tipo de **debugging transversal**.

---

## Relación con la arquitectura del proyecto

La arquitectura del proyecto dummy separa claramente:

- capa de presentación (`ui.apps`),
- capa de aplicación (`core` con models y servlets),
- capa de servicios (`core/services`),
- configuración OSGi (`ui.config`),
- tests unitarios y de integración,
- y despliegue Maven hacia una instancia AEM local.

Además, el proyecto fomenta:

- servicios OSGi registrados por interfaz,
- inyección de dependencias,
- separación de responsabilidades,
- configuración externa en OSGi,
- logging con SLF4J,
- tests unitarios con AEM Mocks,
- y tests de integración en `it.tests`.

Este laboratorio debe respetar ese enfoque: el bug no se investigará solo en la implementación,
sino también en la **interacción entre varias capas**.

---

## Escenario Java / AEM-like propuesto

Trabajarás sobre un flujo funcional parecido a este:

1. Un servlet recibe una petición de registro, contacto o solicitud.
2. Un servicio OSGi procesa la petición.
3. El servicio genera una notificación o un evento de email.
4. Una cola/dispatcher/notifier decide cómo procesar esa notificación.
5. Un servicio de email intenta enviar la confirmación.
6. La configuración OSGi determina si el envío está habilitado y con qué parámetros.

### El bug observable

- la petición principal **sí funciona**,
- los datos parecen procesarse correctamente,
- pero el email al usuario **no se envía**,
- y no hay una excepción evidente o no se ve claramente en logs.

---

## Estructura sugerida del laboratorio

```text
core/src/main/java/com/vasscompany/dummyproject/core/services/lab2_3_DebuggingContexto/
├── RegistrationRequest.java
├── RegistrationResult.java
├── RegistrationService.java
├── EmailNotificationService.java
├── NotificationQueueService.java
└── impl/
    ├── RegistrationServiceImpl.java
    ├── EmailNotificationServiceImpl.java
    ├── NotificationQueueServiceImpl.java
    └── README.md

core/src/main/java/com/vasscompany/dummyproject/core/servlets/lab2_3_DebuggingContexto/
└── RegistrationServlet.java

core/src/test/java/com/vasscompany/dummyproject/core/services/lab2_3_DebuggingContexto/impl/
├── RegistrationServiceImplTest.java
├── EmailNotificationServiceImplTest.java
└── NotificationFlowIntegrationLikeTest.java

ui.config/src/main/content/jcr_root/apps/vass-university-dummyproject/osgiconfig/config/
└── com.vasscompany.dummyproject.core.services.lab2_3_DebuggingContexto.impl.EmailNotificationServiceImpl.cfg.json

.copilot-context/
├── arquitectura.md
├── patrones.md
└── bug-investigation.md
```

> Los nombres concretos pueden variar si prefieres otro dominio funcional, pero mantén el mismo
> patrón: **entrada → servicio principal → cola/notificación → email → configuración**.

---

## Flujo esperado del sistema

Para poder debuggear bien, primero debes tener claro el flujo esperado:

1. `RegistrationServlet` recibe la petición HTTP.
2. `RegistrationServiceImpl` valida y procesa los datos.
3. Se construye un evento o una petición de notificación.
4. `NotificationQueueServiceImpl` la enruta o la encola.
5. `EmailNotificationServiceImpl` prepara asunto, destinatario y cuerpo/template.
6. El sistema envía el email si la configuración OSGi lo permite.
7. El resultado final refleja éxito completo o, como mínimo, deja trazabilidad suficiente.

### Síntomas del bug

- La solicitud se crea o procesa correctamente.
- El usuario no recibe email de confirmación.
- Puede que el servlet responda `200 OK` igualmente.
- Puede que no haya error visible en el navegador.
- Puede que el log actual sea insuficiente para saber dónde se pierde el flujo.

---

## Instrucciones

### Paso 1: Configurar el contexto completo de investigación (20 min)

Antes de preguntarle nada a la IA, prepara el contexto.

### Tareas

1. Abre **TODOS** los archivos relacionados con el flujo:

- `RegistrationServlet.java`
- `RegistrationService.java`
- `RegistrationServiceImpl.java`
- `NotificationQueueService.java`
- `NotificationQueueServiceImpl.java`
- `EmailNotificationService.java`
- `EmailNotificationServiceImpl.java`
- tests relacionados
- configuración OSGi del servicio de email
- logs si existen

2. Crea el archivo:

```text
.copilot-context/bug-investigation.md
```

3. Añade contexto resumido como este:

```markdown
# Bug Investigation

## Bug reportado
La solicitud se procesa correctamente pero el email de confirmación no se envía.

## Archivos involucrados
- RegistrationServlet.java: entrada HTTP
- RegistrationServiceImpl.java: lógica principal
- NotificationQueueServiceImpl.java: enrutado o cola de notificaciones
- EmailNotificationServiceImpl.java: envío de email
- EmailNotificationServiceImpl.cfg.json: configuración OSGi

## Flujo esperado
1. Llega petición al servlet
2. Se procesa la solicitud
3. Se genera evento o petición de notificación
4. Se envía email de confirmación

## Síntomas
- La solicitud principal sí funciona
- El email no se envía
- No hay error claro en la respuesta HTTP
- Logs insuficientes o poco concluyentes
```

4. Si tienes una instancia AEM local corriendo, localiza también:

- `crx-quickstart/logs/error.log`
- logs específicos de la app si los has configurado
- salida de tests de integración si ya existen

### Objetivo de este paso

No empezar el análisis desde una visión parcial. El aprendizaje clave es que los bugs
multiarchivo exigen **contexto compartido entre código, configuración y runtime**.

---

### Paso 2: Diagnóstico con IA usando contexto multiarchivo (60 min)

Con todos los archivos abiertos, utiliza Copilot Chat o la herramienta de IA que estés usando.

### Prompt orientativo

```text
Analiza este bug en un proyecto Java / AEM-like:

Bug reportado:
"La solicitud se procesa correctamente pero el email de confirmación no se envía"

Archivos relevantes:
- RegistrationServlet.java
- RegistrationServiceImpl.java
- NotificationQueueServiceImpl.java
- EmailNotificationServiceImpl.java
- configuración OSGi del servicio de email
- tests relacionados

Flujo esperado:
1. El servlet recibe la petición
2. El servicio procesa la solicitud
3. Se genera una notificación
4. El servicio de email envía confirmación al usuario

Síntomas:
- Paso 1 funciona
- La solicitud parece persistirse o procesarse bien
- El email no llega
- No hay error claro en la respuesta

Quiero que identifiques:
- en qué punto del flujo se rompe el proceso,
- posibles causas raíz,
- qué logs faltan,
- qué pruebas harías para confirmar la hipótesis,
- y qué fix mínimo aplicarías sin romper otros componentes.
```

### Qué deberías pedirle a la IA

No te limites a preguntar “¿dónde está el bug?”. Pide también:

- reconstrucción del flujo completo,
- dependencias entre clases,
- propiedades de configuración relevantes,
- puntos donde una excepción puede estar siendo tragada,
- condiciones que impidan enviar email aunque el proceso principal siga,
- y propuestas de logging diagnóstico.

### Pistas de investigación útiles

Revisa especialmente si ocurre alguna de estas situaciones:

- `emailEnabled` o propiedad equivalente está a `false`,
- nombre de propiedad OSGi no coincide con el usado en código,
- el destinatario queda `null` o vacío,
- el template path es incorrecto,
- la cola no procesa el mensaje por una condición silenciosa,
- `try/catch` demasiado amplio devuelve `false` sin contexto,
- el servicio principal ignora el resultado del envío,
- la excepción se registra con mensaje insuficiente,
- o el flujo usa un `if` que evita el envío en determinados entornos.

---

### Paso 3: Identificar y confirmar la root cause (30 min)

Cuando la IA te proponga hipótesis, no asumas que la primera es correcta.

### Tareas

1. Revisa manualmente el punto del flujo señalado.
2. Comprueba si la hipótesis encaja con los síntomas.
3. Añade logging temporal donde haga falta.
4. Ejecuta el flujo otra vez.
5. Confirma con evidencia la causa raíz.

### Logging temporal recomendado

Puedes añadir trazas temporales como estas:

```java
LOG.info("Processing registration for email={}", request.getEmail());
LOG.debug("Notification created with type={} recipient={}", notificationType, recipient);
LOG.debug("Email service enabled={}", emailEnabled);
LOG.warn("Email notification skipped because recipient is empty");
LOG.error("Unable to send confirmation email for requestId={}", requestId, e);
```

### Ejemplos de root cause plausibles

- La solicitud se procesa, pero `NotificationQueueServiceImpl` nunca llama al servicio de email.
- El servicio de email sí es invocado, pero la configuración OSGi lo deja deshabilitado.
- El email se intenta enviar, pero el destinatario se pierde al mapear la request.
- El código captura una excepción y devuelve éxito parcial sin propagar el fallo.
- La cola exige un tipo de evento distinto al que genera el servicio principal.

### Resultado esperado de esta fase

Debes poder escribir una conclusión del estilo:

> La root cause está en `EmailNotificationServiceImpl` porque la propiedad OSGi usada en
> runtime no coincide con el nombre definido en `cfg.json`, por lo que el servicio queda
> deshabilitado silenciosamente.

O bien:

> La root cause está en `NotificationQueueServiceImpl` porque el flujo descarta eventos con
> tipo `REGISTRATION_CONFIRMATION` al comparar contra un literal incorrecto.

---

### Paso 4: Implementar el fix con visión de sistema (40 min)

Ahora corrige el bug **sin romper el resto del flujo**.

### Principio clave

El fix debe tener en cuenta no solo el archivo donde aparece el error, sino también cómo
impacta en:

- el servlet,
- el servicio principal,
- la cola de notificaciones,
- el servicio de email,
- la configuración,
- y los tests.

### Prompt orientativo para el fix

```java
// Corrige el bug identificado en el flujo de confirmación por email.
//
// Contexto:
// - proyecto Java / AEM-like con servicios OSGi
// - el proceso principal funciona pero el email no se envía
// - root cause confirmada: [descríbela aquí]
//
// Objetivo:
// - aplicar el fix mínimo y seguro
// - mantener compatibilidad con el resto del flujo
// - mejorar logging diagnóstico
// - no introducir dependencias innecesarias
// - actualizar tests si procede
```

### Qué debería incluir el fix

Según la causa encontrada, el fix podría implicar:

- corregir una condición de filtrado,
- alinear nombres entre código y configuración OSGi,
- propagar correctamente el destinatario,
- devolver un resultado de error parcial más explícito,
- eliminar un swallow de excepciones,
- o añadir validaciones y logs útiles.

### Importante

Haz el fix más pequeño posible, pero no dejes el sistema igual de opaco. Si el bug fue difícil
por falta de trazabilidad, mejora también el logging o la observabilidad mínima.

---

### Paso 5: Validación end-to-end (20 min)

Una vez corregido, valida el flujo completo.

### Validaciones recomendadas

1. Ejecuta tests unitarios del paquete:

```bash
mvn -pl core test -Dtest=RegistrationServiceImplTest,EmailNotificationServiceImplTest,NotificationFlowIntegrationLikeTest
```

2. Si tienes tests de integración reales:

```bash
mvn clean verify -Pit
```

3. Si estás trabajando con AEM local:

- despliega el bundle o el paquete,
- reproduce el caso manualmente,
- revisa `error.log`,
- y confirma que ahora el email se intenta enviar o queda trazado correctamente.

### Verificación funcional

Debes comprobar como mínimo que:

- la solicitud sigue procesándose,
- el email de confirmación sí se dispara,
- el servicio no se rompe en otros casos,
- y la información de logs es ahora suficiente para futuras incidencias.

---

## Tests sugeridos para este laboratorio

Aunque el foco sea el debugging, deberías apoyarte en tests.

### Unit tests

Crea o revisa tests para:

- request válida → procesa y genera notificación,
- request sin email → no envía y deja motivo claro,
- servicio de email deshabilitado por config,
- cola que recibe un tipo de evento soportado,
- cola que recibe un tipo inválido,
- excepción en email service → comportamiento esperado,
- y validación de logs o resultados si tu diseño lo permite.

### Test de flujo integrado “like integration”

Aunque no montes una integración real completa, crea un test que conecte:

- `RegistrationServiceImpl`
- `NotificationQueueServiceImpl`
- `EmailNotificationServiceImpl`

para verificar el flujo completo con dobles de prueba o configuración controlada.

### Ejemplo de base para tests

```java
@ExtendWith(AemContextExtension.class)
class NotificationFlowIntegrationLikeTest {

    private final AemContext context = new AemContext();

    @BeforeEach
    void setUp() {
        context.registerInjectActivateService(new EmailNotificationServiceImpl(),
                "enabled", true,
                "defaultSender", "no-reply@dummyproject.local");
        context.registerInjectActivateService(new NotificationQueueServiceImpl());
        context.registerInjectActivateService(new RegistrationServiceImpl());
    }

    @Test
    @DisplayName("Debe procesar la solicitud y disparar el email de confirmación")
    void shouldProcessRegistrationAndTriggerConfirmationEmail() {
        // ...
    }
}
```

---

## Qué archivos deberías mirar de verdad durante el debugging

Para que el laboratorio se parezca a una incidencia real, acostúmbrate a revisar:

### Código

- servlet de entrada,
- interfaces de servicios,
- implementaciones OSGi,
- DTOs o requests/results,
- clases auxiliares de mapeo,
- y cualquier scheduler o listener si la notificación es asíncrona.

### Configuración

- `*.cfg.json` en `ui.config`,
- constantes hardcodeadas,
- flags de habilitación,
- remitente por defecto,
- paths de templates,
- y credenciales simuladas si aplica.

### Runtime y logs

- `crx-quickstart/logs/error.log`,
- consola de OSGi para comprobar componentes activos,
- configuración efectiva del componente,
- y resultado de tests de integración.

---

## Criterios de calidad para Java / AEM durante el debugging

El resultado final del laboratorio debería reflejar buenas prácticas del proyecto:

- ✅ servicios registrados por interfaz
- ✅ configuración externa en OSGi, no hardcodeada
- ✅ logging útil con SLF4J
- ✅ sin `System.out.println` ni `printStackTrace`
- ✅ sin swallowing silencioso de excepciones
- ✅ tests suficientes para reproducir y proteger el fix
- ✅ documentación de la root cause y del fix
- ✅ visión multiarchivo, no parche aislado sin contexto

---

## Entregables

Al finalizar el laboratorio, este paquete debería contener:

- ✅ `RegistrationService.java`
- ✅ `EmailNotificationService.java`
- ✅ `NotificationQueueService.java`
- ✅ implementaciones `*Impl.java` necesarias para reproducir el caso
- ✅ `RegistrationServlet.java` o punto de entrada equivalente
- ✅ tests unitarios y/o de integración del flujo
- ✅ `.copilot-context/bug-investigation.md`
- ✅ `reflexion_lab_2.3.md` con el resumen de la investigación
- ✅ este `README.md`

---

## Reflexión final (15 min)

Crea el archivo `reflexion_lab_2.3.md` con tus observaciones.

### Preguntas sugeridas

- ¿Qué archivo parecía sospechoso al principio y cuál era realmente la root cause?
- ¿Qué hipótesis propuso la IA que sí fueron útiles?
- ¿Qué hipótesis eran plausibles pero incorrectas?
- ¿Qué señal o log fue decisivo para encontrar el problema?
- ¿Qué aprendiste sobre debugging con contexto multiarchivo?
- ¿Qué habría sido mucho más difícil detectar sin abrir configuración y logs además del código?
- ¿Qué mejorarías en la observabilidad del sistema para que este bug fuese trivial la próxima vez?

---

## Criterios de Aceptación

El laboratorio se considera completo cuando:

- [ ] Se ha reconstruido correctamente el flujo completo del sistema
- [ ] Se ha identificado la root cause real con evidencia
- [ ] Se ha aplicado un fix funcional y seguro
- [ ] El email de confirmación vuelve a enviarse o queda correctamente trazado
- [ ] No hay regresiones en el flujo principal
- [ ] Los tests pasan
- [ ] Se ha documentado la investigación y la reflexión final

---

## Tips y Ayuda

- No preguntes a la IA solo por un archivo; dale el flujo completo.
- Si algo “parece funcionar”, verifica igualmente el resultado intermedio.
- Revisa configuración y runtime, no solo Java.
- Cuando un bug es multiarchivo, la ausencia de logs buenos suele ser parte del problema.
- Si Copilot propone un fix demasiado grande, redúcelo a un cambio mínimo verificable.
- Antes de cerrar la incidencia, deja el sistema mejor instrumentado que al empezar.

---

**Versión**: 1.0
