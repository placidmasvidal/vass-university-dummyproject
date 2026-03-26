# Lab 4.3: Refactoring Arquitectónico con Agente Refactor (Java / AEM)

## Objetivo

Refactorizar un módulo **Java / AEM** con ayuda de un **Agente Refactor** especializado,
centrado no en “limpiar código por estética”, sino en **mejorar la arquitectura sin romper
el comportamiento funcional**.

En este laboratorio vas a usar IA para:

- diagnosticar problemas de diseño y acoplamiento
- proponer una arquitectura objetivo razonable para AEM
- dividir responsabilidades en servicios y colaboradores claros
- preservar compatibilidad funcional con tests como red de seguridad
- medir mejoras reales de mantenibilidad

La meta no es reescribir todo desde cero. La meta es aprender a usar IA para conducir un
**refactor seguro, incremental y justificable** dentro de un proyecto AEM realista.

---

## Duración Estimada

**3 horas**

---

## Prerequisitos

- ✅ Labs 4.1 y 4.2 completados, o al menos entendidos
- ✅ Copilot Chat o herramienta IA equivalente disponible
- ✅ Proyecto `vass-university-dummyproject` compilando localmente
- ✅ Conocimiento básico de Java, OSGi DS, Sling, AEM Mocks y JUnit 5
- ✅ Haber revisado `arquitectura.md`, `patrones.md` y el `pom.xml` del proyecto
- ✅ Tener disponible una feature existente sobre la que refactorizar

---

## Qué significa “Agente Refactor” en este lab

En el laboratorio original del curso se plantea refactorizar un módulo con un agente
especializado. En esta adaptación a Java/AEM, ese agente debe actuar como un **arquitecto
pragmático de refactor**, no como un generador impulsivo de código nuevo.

Eso implica que la IA debe ser capaz de:

- leer una implementación actual y detectar responsabilidades mezcladas
- distinguir deuda técnica real de simples preferencias de estilo
- proponer refactors incrementales y reversibles
- respetar contratos públicos, configuración OSGi y tests existentes
- sugerir estructuras más idiomáticas para AEM / OSGi

Tu trabajo consiste en **darle contexto, restricciones y criterios de éxito** para que el
refactor tenga sentido en un proyecto real.

---

## Contexto del Ejercicio

Vas a tomar como punto de partida la feature construida en los labs anteriores:

# **Page Publication Readiness**

En su primera versión funcional, esta feature ya permite evaluar si una página AEM está lista
para publicarse. Sin embargo, con el crecimiento natural del código, la implementación ha
acabado concentrando demasiadas responsabilidades en una sola clase.

### Problemas típicos del punto de partida

Imagina una clase como `PagePublicationReadinessServiceImpl` que actualmente:

- resuelve la página desde el path recibido
- valida si el recurso existe
- comprueba propiedades de `jcr:content`
- evalúa si la plantilla es válida
- interpreta configuración OSGi
- construye el resultado final
- decide warnings y errores
- registra logs detallados
- quizá incluso contiene utilidades privadas de acceso al repositorio

Funciona, pero empieza a presentar señales de diseño problemático:

- clase demasiado grande
- métodos largos y difíciles de leer
- demasiados `if/else` encadenados
- lógica de validación mezclada con lógica de orquestación
- difícil de extender con nuevas reglas
- tests excesivamente acoplados a detalles internos
- mayor riesgo de romper algo al tocar una validación

Tu objetivo es refactorizar esta solución hacia una arquitectura más clara, modular y
extensible, **sin cambiar el comportamiento observable** salvo pequeñas mejoras justificadas.

---

## Objetivo arquitectónico del refactor

La idea no es inventar una arquitectura compleja, sino moverse hacia una estructura simple
pero sólida para AEM.

### Arquitectura objetivo sugerida

```text
PagePublicationReadinessServiceImpl
        │
        ▼
PagePublicationReadinessEvaluator
        │
        ├── PageExistenceValidator
        ├── TemplatePolicyValidator
        ├── RequiredPropertiesValidator
        ├── PublicationDateValidator
        └── ResultAssembler / ResultFactory
```

### Qué buscamos conseguir

- un **servicio OSGi orquestador** pequeño y legible
- **validadores con responsabilidad única**
- reglas fáciles de añadir o quitar
- configuración OSGi aplicada de forma centralizada y clara
- menor complejidad ciclomática en la clase principal
- tests más específicos y menos frágiles

---

## Por qué este refactor encaja con Java / AEM

En AEM es muy habitual que una primera implementación “correcta” nazca demasiado monolítica,
porque resolver contenido, aplicar configuración, adaptar recursos y devolver un DTO parece
cómodo en una sola clase. Pero a medida que la feature crece, ese enfoque empieza a penalizar:

- cuesta aislar reglas para testearlas
- cuesta reutilizar lógica entre servlet, scheduler o workflow step
- cuesta introducir nuevas validaciones sin tocar demasiadas cosas
- cuesta separar acceso a contenido, política y ensamblado de resultados

Este laboratorio te entrena justamente para usar IA en uno de los escenarios donde más puede
aportar: **refactors guiados por arquitectura y mantenibilidad**.

---

## Qué se considera un buen resultado

El resultado del laboratorio no se mide por “cuántos archivos nuevos hay”, sino por mejoras
claras y defendibles.

### Mejoras medibles esperadas

- reducción del tamaño de la clase principal
- reducción del número de ramas condicionales en el servicio principal
- validadores con nombres semánticos y propósito claro
- tests reorganizados por responsabilidad
- posibilidad de añadir una nueva regla con cambios mínimos
- menor duplicación de lógica
- mejora de legibilidad y capacidad de explicación del diseño

---

## Entregables

Al finalizar el laboratorio deberías tener, como mínimo, algo equivalente a esto:

### En `core/src/main/java/...`

- `services/lab4_1AgenteDev/PagePublicationReadinessService.java`
- `services/lab4_1AgenteDev/impl/PagePublicationReadinessServiceImpl.java` refactorizado
- `services/lab4_3AgenteRefactor/evaluator/PagePublicationReadinessEvaluator.java`
- `services/lab4_3AgenteRefactor/validators/PageValidator.java`
- una o varias implementaciones de validadores:
    - `PageExistenceValidator.java`
    - `TemplatePolicyValidator.java`
    - `RequiredPropertiesValidator.java`
    - `PublicationDateValidator.java`
- `services/lab4_3AgenteRefactor/model/ValidationIssue.java` si aporta claridad
- `services/lab4_3AgenteRefactor/model/ValidationContext.java` si decides encapsular entradas

### En `core/src/test/java/...`

- tests actualizados del servicio principal
- tests unitarios para validadores individuales
- tests de regresión funcional para garantizar compatibilidad

### En documentación

- `arquitectura_refactor_lab_4.3.md`
- `reflexion_lab_4.3.md`
- opcional: tabla antes/después con métricas o decisiones de diseño

---

## Estructura sugerida

```text
core/
└── src/main/java/com/vasscompany/dummyproject/core/
    ├── services/lab4_1AgenteDev/
    │   ├── PagePublicationReadinessService.java
    │   └── impl/
    │       └── PagePublicationReadinessServiceImpl.java
    └── services/lab4_3AgenteRefactor/
        ├── evaluator/
        │   └── PagePublicationReadinessEvaluator.java
        ├── validators/
        │   ├── PageValidator.java
        │   ├── PageExistenceValidator.java
        │   ├── TemplatePolicyValidator.java
        │   ├── RequiredPropertiesValidator.java
        │   └── PublicationDateValidator.java
        └── model/
            ├── ValidationContext.java
            └── ValidationIssue.java

core/
└── src/test/java/com/vasscompany/dummyproject/core/
    ├── services/lab4_1AgenteDev/impl/
    │   └── PagePublicationReadinessServiceImplTest.java
    └── services/lab4_3AgenteRefactor/
        └── validators/
            ├── PageExistenceValidatorTest.java
            ├── TemplatePolicyValidatorTest.java
            └── RequiredPropertiesValidatorTest.java
```

---

## Paso 1: Preparar el contexto del Agente Refactor (15 min)

Antes de pedir ningún cambio, abre en el IDE:

- `arquitectura.md`
- `patrones.md`
- `pom.xml`
- el servicio actual de `PagePublicationReadiness`
- sus tests
- este `README.md`

Si te ayuda, crea además un archivo auxiliar como:

```text
.copilot-context/agente-refactor-java-aem.md
```

### Qué debe saber el agente

Tu agente debe asumir estas reglas:

- el proyecto sigue arquitectura por capas
- la lógica de negocio debe vivir en `core/services`
- el servlet no debe absorber lógica de validación
- la configuración debe seguir viniendo de OSGi
- el refactor debe ser incremental y conservar comportamiento
- no debe introducir sobreingeniería innecesaria
- los tests son parte del refactor, no una tarea separada opcional
- debe preferir clases pequeñas, nombres semánticos y responsabilidades claras

---

## Paso 2: Diseñar el prompt maestro del Agente Refactor (20 min)

Crea un prompt largo o system prompt para convertir la IA en un perfil de refactor
arquitectónico Java/AEM.

### Prompt base sugerido

```text
Actúa como un arquitecto de software senior especializado en Java, AEM 6.5, OSGi Declarative Services, Sling y refactorización incremental de código legacy.

Contexto del proyecto:
- Proyecto: vass-university-dummyproject
- Arquitectura Maven multi-módulo
- Capas: ui.apps (presentación), core (servicios/servlets/models), ui.config (configuración OSGi), it.tests y ui.tests
- El código debe ser mantenible, testeable y consistente con patrones AEM/OSGi
- Debes evitar sobreingeniería y priorizar cambios reversibles

Objetivo:
Refactorizar la feature Page Publication Readiness para mejorar arquitectura, extensibilidad y testabilidad sin alterar el comportamiento funcional observable.

Restricciones:
- Conserva la interfaz pública existente salvo mejora claramente justificada
- No metas lógica de negocio en servlet ni en DTOs
- Mantén configuración OSGi como fuente de reglas
- Evita dependencias innecesarias
- Diseña pensando en JUnit 5 y AEM Mocks
- No uses APIs inseguras o obsoletas
- Propón refactor en pasos pequeños y verificables

Necesito que:
1. diagnostiques problemas del diseño actual
2. propongas arquitectura objetivo
3. definas plan de refactor paso a paso
4. generes código por bloques de archivo
5. expliques qué tests deben proteger cada paso
6. señales posibles riesgos de regresión

Si detectas que un refactor aporta poca mejora o complica innecesariamente el diseño, dilo explícitamente.
```

### Qué debes comprobar del prompt

- [ ] ¿El rol del agente está bien definido?
- [ ] ¿Las restricciones del proyecto están claras?
- [ ] ¿Se pide refactor incremental y no reescritura total?
- [ ] ¿Se exige preservar comportamiento y tests?
- [ ] ¿Se previene la sobreingeniería?

---

## Paso 3: Pedir primero el diagnóstico, no el código (20 min)

Antes de dejar que la IA toque la implementación, pídele un **análisis crítico del diseño actual**.

### Prompt sugerido

```text
Analiza esta implementación actual de PagePublicationReadinessServiceImpl desde un punto de vista arquitectónico.

Quiero que identifiques:
- responsabilidades mezcladas
- métodos con demasiadas decisiones
- acoplamientos innecesarios
- piezas difíciles de testear
- posibles extracciones de clases o colaboradores
- riesgos si se deja crecer así

Devuélveme:
1. diagnóstico priorizado
2. propuesta de arquitectura objetivo
3. plan de refactor en pasos pequeños
4. puntos donde necesito tests de regresión antes de tocar nada
```

### Resultado esperado

Deberías obtener algo parecido a esto:

| Problema detectado | Impacto | Refactor sugerido | Prioridad |
|--------------------|---------|-------------------|-----------|
| Servicio principal demasiado grande | Baja mantenibilidad | Extraer evaluador y validadores | Alta |
| Validación de template mezclada con resolución de página | Difícil de leer y testear | Crear `TemplatePolicyValidator` | Alta |
| Construcción del resultado dispersa | Riesgo de inconsistencias | Introducir ensamblador o factoría | Media |
| Dependencia fuerte de estructura JCR en muchos métodos | Tests frágiles | Encapsular contexto de validación | Media |
| Logs y lógica entremezclados | Ruido y acoplamiento | Simplificar puntos de logging | Baja |

No sigas si la IA propone una reescritura total sin red de seguridad.

---

## Paso 4: Congelar comportamiento con tests antes del refactor (20 min)

Antes de aplicar cambios grandes, asegúrate de que el comportamiento actual está protegido.

### Tareas

1. Revisa los tests existentes del lab 4.2
2. Añade tests de regresión si falta cobertura funcional
3. Identifica casos críticos que no deben cambiar

### Casos que deberían estar cubiertos como mínimo

- página inexistente
- recurso no adaptable a `Page`
- falta `jcr:title`
- falta `cq:template`
- plantilla no permitida por configuración
- falta alguna propiedad obligatoria
- caso completamente válido
- caso con warnings pero sin errores bloqueantes, si tu diseño lo contempla

### Regla de oro

**No refactorices primero y pruebes después.**

La IA puede ayudarte a refactorizar rápido, pero eso solo es seguro si antes tienes una red de
seguridad suficientemente buena.

---

## Paso 5: Diseñar la arquitectura objetivo con la IA (25 min)

Una vez diagnosticado el diseño, pide al agente una **propuesta concreta de estructura**.

### Prompt sugerido

```text
Propón una arquitectura objetivo para refactorizar PagePublicationReadiness.

Necesito:
- lista de clases y responsabilidades
- qué interfaz mantener y cuáles crear nuevas
- cómo separar orquestación, validación y ensamblado de resultado
- cómo mantener compatibilidad con tests existentes
- cómo evitar sobreingeniería

Entrega la propuesta en formato:
1. diagrama textual
2. tabla de clases
3. orden recomendado de implementación
```

### Ejemplo de tabla útil

| Clase | Responsabilidad | Depende de | Comentario |
|------|------------------|------------|------------|
| `PagePublicationReadinessServiceImpl` | Punto de entrada OSGi | Evaluator | Queda fino |
| `PagePublicationReadinessEvaluator` | Orquestar validaciones | Lista de validadores | Coordina el flujo |
| `PageValidator` | Contrato común | - | Permite extender reglas |
| `TemplatePolicyValidator` | Validar plantilla | Config | Regla aislada |
| `RequiredPropertiesValidator` | Validar propiedades requeridas | Config + Page | Regla aislada |
| `ValidationContext` | Encapsular entradas y config | - | Reduce firmas largas |

---

## Paso 6: Ejecutar el refactor en pasos pequeños (40 min)

Aplica el refactor de forma incremental. Un posible orden seguro es este:

### Iteración 1: extraer contexto y resultado auxiliar

- introduce `ValidationContext` si las firmas empiezan a ser largas
- extrae estructuras de soporte sin tocar la lógica de negocio

### Iteración 2: extraer una primera regla clara

- por ejemplo `TemplatePolicyValidator`
- mueve la lógica tal cual, sin mejorar demasiadas cosas a la vez
- verifica que los tests siguen pasando

### Iteración 3: extraer el resto de validadores

- `PageExistenceValidator`
- `RequiredPropertiesValidator`
- `PublicationDateValidator`

### Iteración 4: introducir el evaluador / orquestador

- deja la clase OSGi principal como fachada fina
- delega el flujo al evaluador

### Iteración 5: simplificar nombres, logs y duplicaciones

- renombra métodos si mejora la comprensión
- elimina ramas repetidas
- racionaliza logging

### En cada iteración, comprueba:

- [ ] ¿Los tests siguen pasando?
- [ ] ¿La API pública sigue siendo coherente?
- [ ] ¿La clase principal quedó más simple que antes?
- [ ] ¿El nuevo colaborador tiene una responsabilidad clara?
- [ ] ¿Se puede explicar fácilmente por qué existe esa clase?

---

## Paso 7: Pedir revisión crítica del refactor al propio agente (15 min)

Cuando termines una primera versión, no te quedes solo con “compila y pasa tests”. Pide a la IA
que revise el propio diseño resultante.

### Prompt sugerido

```text
Revisa críticamente este refactor ya aplicado.

Quiero que evalúes:
- si realmente se redujo el acoplamiento
- si alguna clase quedó artificial o innecesaria
- si existe sobreingeniería
- si la distribución de responsabilidades tiene sentido en AEM
- si los tests cubren bien la nueva arquitectura
- qué simplificarías todavía

Devuélveme:
1. puntos fuertes
2. puntos débiles
3. simplificaciones posibles
4. riesgos futuros si la feature sigue creciendo
```

---

## Paso 8: Documentar el antes / después (20 min)

Crea un archivo como `arquitectura_refactor_lab_4.3.md` con una comparación clara.

### Incluye, como mínimo

#### 1. Situación inicial

- número aproximado de responsabilidades en la clase principal
- problemas de diseño detectados
- riesgos de mantenimiento

#### 2. Solución aplicada

- clases extraídas
- nuevo flujo de validación
- decisiones de diseño relevantes

#### 3. Mejoras observadas

| Aspecto | Antes | Después |
|---------|-------|---------|
| Clase principal | Monolítica | Fachada + evaluador |
| Reglas de validación | Embebidas en una clase | Validadores aislados |
| Testabilidad | Tests amplios y frágiles | Tests pequeños y específicos |
| Extensibilidad | Añadir regla implica tocar clase central | Añadir validador nuevo |
| Legibilidad | Baja | Mayor claridad |

#### 4. Reflexión sobre el uso de IA

En `reflexion_lab_4.3.md`, responde por ejemplo:

- ¿La IA diagnosticó bien los problemas o hubo que corregirla?
- ¿En qué fase aportó más valor: diagnóstico, diseño o implementación?
- ¿Propuso sobreingeniería en algún momento?
- ¿Qué decisiones tuviste que tomar tú manualmente?
- ¿Qué señales te hicieron confiar o desconfiar de sus sugerencias?

---

## Checklist de calidad del refactor

Antes de dar el lab por finalizado, comprueba:

- [ ] El comportamiento funcional principal se mantiene
- [ ] Los tests siguen pasando
- [ ] La clase principal tiene menos responsabilidades
- [ ] Las nuevas clases tienen un propósito claro y explicable
- [ ] La configuración OSGi sigue siendo la fuente de reglas
- [ ] No se ha movido lógica de negocio al servlet
- [ ] No se ha introducido complejidad accidental
- [ ] Añadir una nueva regla de validación sería ahora más fácil
- [ ] El diseño final se puede explicar en 2-3 minutos con claridad

---

## Qué no deberías hacer

Evita estos errores típicos al refactorizar con IA:

- ❌ reescribir todo de golpe
- ❌ aceptar una arquitectura “bonita” pero innecesariamente compleja
- ❌ introducir patrones por moda
- ❌ romper tests y confiar en que luego se arreglarán
- ❌ mezclar refactor con cambios funcionales grandes sin separarlos
- ❌ extraer clases con nombres vagos como `Helper`, `Manager`, `ProcessorUtil`

---

## Resultado esperado del laboratorio

Al acabar, deberías tener una feature equivalente funcionalmente, pero con una arquitectura
más sostenible.

No se trata de tener “más clases”, sino de tener:

- mejores límites entre responsabilidades
- código más fácil de leer
- validaciones más fáciles de extender
- tests más útiles
- menor coste de cambio futuro

Ese es exactamente el tipo de trabajo donde un buen **Agente Refactor** puede multiplicar tu
productividad, siempre que lo dirijas con criterio arquitectónico y no como un generador ciego.
