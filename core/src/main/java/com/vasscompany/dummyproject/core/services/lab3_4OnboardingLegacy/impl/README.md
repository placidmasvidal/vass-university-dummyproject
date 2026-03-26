# Lab 3.4: Onboarding en Código Legacy (Java / AEM-like)

## Objetivo

Usar IA para explorar, comprender y documentar una zona de **código legacy** dentro de un
proyecto **Java / AEM 6.5 / OSGi**, generando una guía de onboarding útil para que otro
开发ador pueda empezar a trabajar con seguridad en ese módulo.

El foco de este laboratorio no es implementar una feature nueva, sino **reducir la curva de
entrada** sobre código heredado: entender responsabilidades, dependencias, flujos,
configuración, riesgos y puntos de extensión.

---

## Duración Estimada

**2-3 horas**

---

## Prerequisitos

- ✅ Haber completado los labs anteriores de prompting y workflow agéntico
- ✅ GitHub Copilot o herramienta equivalente configurada
- ✅ Proyecto `vass-university-dummyproject` disponible localmente
- ✅ Entender conceptos base de Java, Maven, OSGi DS, Sling y AEM
- ✅ Poder compilar localmente con:

```bash
mvn clean install
```

---

## Contexto del Ejercicio

Te incorporas a un proyecto AEM con estructura realista y necesitas empezar a trabajar sobre un
módulo heredado del que **no existe documentación suficiente**.

Ese módulo legacy encaja dentro de una arquitectura por capas, con:

- capa de presentación en `ui.apps`
- modelos, servlets y servicios en `core`
- configuraciones OSGi en `ui.config`
- tests de integración en `it.tests`
- tests funcionales en `ui.tests`

El proyecto dummy sigue este enfoque multi-módulo AEM y separa claramente código Java,
configuración y despliegue empaquetado en `all`, lo que condiciona cómo debes analizar el
módulo legacy y documentarlo para otros desarrolladores.

Además, el proyecto promueve patrones habituales de AEM/OSGi como arquitectura por capas,
inyección de dependencias, servicios OSGi, servlets Sling y uso de service users para acceso al
repositorio, por lo que tu onboarding no debe quedarse en “qué hace el código”, sino también en
“cómo encaja arquitectónicamente” y “cómo debería evolucionarse sin romper el sistema”.

El laboratorio original pide precisamente usar IA para **explorar código legacy y generar una guía
de onboarding y documentación de arquitectura**. Aquí haremos lo mismo, pero aterrizado a un
caso Java/AEM realista.

---

## Escenario Propuesto

Imagina que te asignan el mantenimiento de un módulo legacy llamado, por ejemplo:

`RequestTrackingService`

Este módulo participa en un flujo típico de AEM:

1. Un **Servlet Sling** recibe una petición HTTP.
2. Un **servicio OSGi** valida datos y ejecuta lógica de negocio.
3. El servicio persiste o consulta información en el **JCR**.
4. Parte del comportamiento depende de una **configuración OSGi** en `ui.config`.
5. Puede haber integraciones externas, logging, colas internas o notificaciones.
6. Existen tests parciales o desactualizados.

Tu objetivo no es refactorizar todavía, sino **entender el módulo como si fueras una persona nueva
entrando al proyecto** y dejar una documentación que sirva para onboarding técnico.

---

## Qué Debes Conseguir al Final

Al acabar este lab debes ser capaz de responder con claridad preguntas como estas:

- ¿Cuál es la responsabilidad exacta del módulo legacy?
- ¿Qué clases son centrales y cuáles son auxiliares?
- ¿Dónde empieza el flujo y dónde termina?
- ¿Qué servlets, servicios, modelos o schedulers intervienen?
- ¿Qué configuraciones OSGi afectan al comportamiento?
- ¿Qué rutas JCR, service users o permisos están implicados?
- ¿Qué dependencias externas o internas tiene?
- ¿Qué riesgos o deuda técnica detectas?
- ¿Cómo debería orientarse el trabajo de una persona nueva que tenga que tocar ese código?

---

## Workflow Agéntico Adaptado a Java/AEM

### PLAN: Exploración estructurada

Antes de pedir explicaciones a la IA, identifica el perímetro del análisis:

- paquete Java principal a estudiar
- interfaces y clases `impl`
- servlets relacionados
- configuraciones OSGi asociadas
- posibles rutas JCR implicadas
- tests existentes
- módulos Maven afectados

**Tu plan debe cubrir como mínimo:**

- entrada al flujo
- lógica de negocio central
- acceso a datos o JCR
- configuración
- testing
- despliegue y runmodes

---

### ACT: Análisis asistido

Usa IA como **Agente Onboarding** para recorrer el módulo de forma sistemática.

La IA debe ayudarte a:

- resumir la responsabilidad de cada clase
- detectar dependencias entre clases
- explicar el flujo extremo a extremo
- identificar configuraciones necesarias para que funcione
- inferir casos de uso principales
- marcar zonas ambiguas o de riesgo
- generar documentación navegable y útil para nuevos miembros del equipo

---

### OBSERVE: Validación de comprensión

No des por buena la explicación solo porque “suena bien”. Valida:

- que la documentación coincide con el código real
- que no inventa comportamientos inexistentes
- que las dependencias citadas realmente se usan
- que las configuraciones OSGi existen o tendrían sentido en `ui.config`
- que el flujo descrito encaja con el patrón arquitectónico del proyecto

---

### REFLECT: Refinamiento del onboarding

Refina la documentación para que sea realmente útil en onboarding:

- separa visión general y detalle técnico
- añade ejemplos prácticos
- destaca decisiones de arquitectura
- documenta deuda técnica y riesgos
- deja recomendaciones claras para la primera semana de una persona nueva

---

## Paso 1: Delimitar el Módulo Legacy (20 min)

**Tareas:**

1. Elige un paquete o submódulo legacy del proyecto dummy que vas a analizar
2. Localiza sus clases principales
3. Clasifícalas por rol:
    - servlet
    - servicio OSGi
    - modelo / DTO
    - helper / util
    - adaptador / cliente externo
    - configuración OSGi
    - test
4. Identifica en qué módulos Maven vive cada pieza (`core`, `ui.config`, `it.tests`, etc.)
5. Haz un primer mapa rápido del flujo

**Salida esperada:**

Crea un archivo inicial en este directorio:

- `onboarding_legacy_inventario.md`

Con esta estructura mínima:

```md
# Inventario del módulo legacy

## Paquetes analizados
- ...

## Clases principales
- Servlet: ...
- Servicio: ...
- DTO/Model: ...
- Config: ...
- Test: ...

## Dependencias detectadas
- ...

## Dudas abiertas
- ...
```

---

## Paso 2: Prompt de Exploración para el Agente Onboarding (20 min)

Diseña un prompt fuerte para que la IA no haga una lectura superficial.

### Prompt recomendado

```text
Actúa como un arquitecto técnico senior de Java/AEM 6.5 especializado en onboarding de código legacy.

Voy a darte varias clases de un módulo heredado de un proyecto AEM multi-módulo con `core`, `ui.config`, `ui.content`, `it.tests` y `ui.tests`.

Quiero que analices el módulo con estos objetivos:
1. Explicar qué responsabilidad tiene cada clase.
2. Reconstruir el flujo extremo a extremo desde la entrada HTTP o trigger inicial.
3. Identificar dependencias OSGi, acceso al JCR, configuración y posibles integraciones externas.
4. Detectar deuda técnica, acoplamientos, riesgos y puntos de extensión.
5. Generar documentación de onboarding para un desarrollador nuevo.

Restricciones:
- No inventes comportamientos que no estén soportados por el código.
- Distingue claramente entre hechos observados e inferencias.
- Usa terminología propia de AEM/OSGi/Sling.
- Señala si ves anti-patrones como lógica en servlets, uso incorrecto de ResourceResolver, config hardcodeada o baja testabilidad.

Devuélveme la salida en secciones:
- Resumen ejecutivo
- Componentes y responsabilidades
- Flujo funcional
- Configuración y dependencias
- Riesgos / deuda técnica
- Guía de onboarding para una persona nueva
```

**Consejo:** no analices todo el módulo de golpe si es grande. Hazlo por capas o por flujos.

---

## Paso 3: Reconstrucción del Flujo Técnico (30 min)

Con ayuda del agente, documenta el flujo principal del módulo legacy.

### Qué debes identificar

- punto de entrada (`Servlet`, scheduler, listener, workflow step, servicio invocado por otro)
- validaciones de entrada
- servicio principal que orquesta
- acceso a repositorio o APIs externas
- gestión de errores
- logging
- salida o efecto final

### En AEM, revisa especialmente

- si el servlet está bindeado por path o por resource type
- si el servicio está registrado correctamente con su interfaz
- si usa `@Reference` a otros servicios
- si depende de `@Activate` / `@Modified`
- si accede al JCR con `ResourceResolverFactory`
- si necesita service user
- si existe configuración en `ui.config/.../osgiconfig/...`

Crea el archivo:

- `flujo_legacy.md`

**Formato sugerido:**

```md
# Flujo funcional del módulo legacy

## Trigger de entrada
...

## Secuencia principal
1. ...
2. ...
3. ...

## Componentes implicados
- Servlet: ...
- Service: ...
- Config: ...
- Repository/JCR: ...

## Errores y manejo actual
...

## Observaciones
...
```

---

## Paso 4: Documentación de Arquitectura del Módulo (30 min)

Ahora transforma el análisis en una documentación reutilizable para onboarding.

Crea el archivo:

- `arquitectura_modulo_legacy.md`

### Debe incluir

#### 1. Propósito del módulo
Qué resuelve y para qué parte del negocio o del site existe.

#### 2. Encaje arquitectónico
Cómo encaja en la arquitectura general del proyecto AEM:

- en qué módulo vive
- qué expone
- qué consume
- qué configura
- qué despliega

#### 3. Componentes clave
Lista de clases y rol de cada una.

#### 4. Dependencias técnicas
- otros servicios OSGi
- APIs externas
- rutas JCR
- configs OSGi
- librerías relevantes

#### 5. Runmodes y configuración
Describe si cambia por `config`, `config.author`, `config.publish`, `config.dev`, etc.,
porque el proyecto separa configuraciones por runmode en `ui.config`.

#### 6. Testing actual
Documenta qué cobertura existe:

- tests unitarios en `core`
- tests de integración en `it.tests`
- tests funcionales en `ui.tests`

El proyecto ya contempla esos módulos como parte natural de la validación.

#### 7. Riesgos y deuda técnica
- acoplamiento alto
- clase demasiado grande
- lógica mezclada
- hardcodes
- mala gestión de excepciones
- baja trazabilidad
- falta de tests
- seguridad mejorable

---

## Paso 5: Guía de Onboarding para un Nuevo Desarrollador (30 min)

Este es el entregable más importante.

Crea el archivo:

- `guia_onboarding_legacy.md`

### Debe responder de forma práctica a estas preguntas

#### ¿Qué debo leer primero?
Orden recomendado de lectura del código.

#### ¿Cómo levanto y pruebo este módulo?
Incluye comandos útiles, por ejemplo:

```bash
mvn clean install
mvn clean install -PautoInstallBundle
mvn clean verify -Pit
```

Estos comandos son coherentes con el proyecto dummy y su ciclo de build/deploy.

#### ¿Dónde están las configuraciones?
Indica el paquete/configuración esperable en `ui.config`.

#### ¿Qué puntos suelen romperse?
Lista incidencias probables o zonas frágiles.

#### ¿Qué no debería tocar sin entender antes?
Por ejemplo:

- contratos de interfaz OSGi
- nombre de properties OSGi
- rutas JCR críticas
- bindings de servlet
- service users / permisos
- DTOs usados por integraciones externas

#### ¿Cómo sabré si he roto algo?
Qué tests ejecutar y qué validaciones manuales hacer.

#### ¿Qué primeras mejoras recomendarías?
No implementarlas aún, solo priorizarlas.

---

## Paso 6: Checklist de Validación Técnica (15 min)

Antes de cerrar la documentación, revisa si has cubierto estos puntos:

- [ ] Se entiende la responsabilidad del módulo en una sola lectura
- [ ] Hay mapa de clases y dependencias
- [ ] El flujo principal está descrito paso a paso
- [ ] Las configuraciones OSGi relevantes están documentadas
- [ ] Se menciona si hay acceso al JCR y con qué patrón
- [ ] Se documentan riesgos reales, no genéricos
- [ ] La guía de onboarding ayuda de verdad a una persona nueva
- [ ] Se separan claramente hechos observados de inferencias

---

## Paso 7: Reflexión Final (15 min)

Crea el archivo:

- `reflexion_lab_3.4.md`

### Preguntas de reflexión

- ¿Qué te ayudó más la IA a descubrir: estructura, flujo o deuda técnica?
- ¿Dónde fue más útil y dónde menos fiable?
- ¿Qué partes del análisis tuviste que corregir manualmente?
- ¿Qué información faltaba en el código para que el onboarding fuera realmente bueno?
- ¿Qué documentación adicional dejarías en el proyecto real después de este ejercicio?
- ¿Qué diferencia notaste entre usar IA para implementar y usarla para comprender legacy?

---

## Checklist de Seguridad para el Onboarding

Aunque este lab no es de security puro, una buena guía de onboarding en AEM debe alertar de
riesgos frecuentes:

- [ ] ¿Se usa `getServiceResourceResolver()` y no accesos administrativos?
- [ ] ¿Hay service user mapping documentado cuando aplica? El proyecto usa este patrón como
  enfoque recomendado.
- [ ] ¿Se exponen datos sensibles en logs?
- [ ] ¿Hay paths JCR hardcodeados sin contexto?
- [ ] ¿Hay configuraciones sensibles en código en vez de OSGi?
- [ ] ¿El servlet valida inputs antes de delegar?
- [ ] ¿La gestión de errores deja trazabilidad suficiente?

---

## Qué Buen Resultado Tiene Este Lab

Se considera un buen resultado si al entregar el ejercicio:

1. Una persona que no conocía el módulo puede entenderlo en 10-15 minutos.
2. La documentación separa visión funcional, visión técnica y riesgos.
3. El análisis menciona clases reales, configuraciones reales y flujos plausibles.
4. Queda claro qué tocar primero, qué evitar y cómo validar cambios.
5. La documentación sirve como base para futuros labs de bugfix, refactor o hardening.

---

## Entregables

Al finalizar, este directorio debería contener como mínimo:

- ✅ `README.md` - Guía del laboratorio
- ✅ `onboarding_legacy_inventario.md` - Inventario del módulo y dudas abiertas
- ✅ `flujo_legacy.md` - Reconstrucción del flujo funcional/técnico
- ✅ `arquitectura_modulo_legacy.md` - Documentación de arquitectura del módulo
- ✅ `guia_onboarding_legacy.md` - Guía práctica para nuevos desarrolladores
- ✅ `reflexion_lab_3.4.md` - Reflexión sobre el proceso asistido con IA

**Opcionalmente:**

- `glosario_modulo_legacy.md`
- `riesgos_modulo_legacy.md`
- `diagrama_secuencia_legacy.md`
- `faq_onboarding_legacy.md`

---

## Siguientes Pasos Naturales

Este laboratorio deja preparado el terreno para trabajos posteriores sobre el mismo módulo:

- corrección de bugs
- refactor incremental
- ampliación funcional
- endurecimiento de seguridad
- aumento de cobertura de tests
- documentación operativa

En un proyecto real, esta documentación sería especialmente valiosa antes de abrir una tarea de
cambio sobre código heredado con poca trazabilidad.

---

**Versión**: 1.0
