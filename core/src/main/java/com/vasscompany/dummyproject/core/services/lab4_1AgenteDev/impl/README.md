# Lab 4.1: Implementación con Agente Dev Optimizado (Java / AEM)

## Objetivo

Implementar una **feature completa de Java / AEM** usando un **Agente Dev especializado**,
no como autocompletado pasivo, sino como un colaborador guiado por contexto, restricciones
arquitectónicas y criterios de calidad.

En este laboratorio vas a usar IA para producir de forma coordinada:

- código de negocio
- configuración OSGi
- endpoint de acceso
- tests unitarios
- documentación técnica

La meta no es “que la IA programe sola”, sino **aprender a dirigirla bien** en un proyecto
AEM realista con arquitectura por capas, servicios OSGi, configuración externa y tests
mantenibles.

---

## Duración Estimada

**2-3 horas**

---

## Prerequisitos

- ✅ Labs anteriores completados hasta 2.4
- ✅ Copilot Chat o herramienta IA equivalente disponible
- ✅ Proyecto `vass-university-dummyproject` compilando localmente
- ✅ Conocimiento básico de Java, Maven, OSGi DS, Sling y AEM 6.5
- ✅ Haber revisado `arquitectura.md`, `patrones.md` y el `pom.xml` del proyecto

---

## Qué significa “Agente Dev Optimizado” en este lab

En el repositorio original del curso este laboratorio propone implementar una feature usando
un agente especializado de desarrollo. En esta adaptación a Java/AEM, ese agente debe estar
**entrenado por contexto**, es decir, debe trabajar con instrucciones claras sobre:

- arquitectura multi-módulo del proyecto
- separación por capas
- patrones AEM / OSGi
- convenciones de tests
- restricciones de seguridad y mantenibilidad

No vas a lanzar prompts sueltos y genéricos. Vas a preparar un **prompt maestro / system prompt
operativo** que convierta a la IA en un desarrollador Java/AEM disciplinado para este proyecto.

---

## Contexto del Ejercicio

Vas a implementar una feature AEM realista llamada:

# **Page Publication Readiness**

La feature debe permitir evaluar si una página de AEM está “lista para publicar” según una
serie de validaciones configurables.

### Comportamiento esperado

Dada una página AEM, el sistema debe comprobar, como mínimo:

- que el recurso existe y es una página válida
- que tiene `jcr:title`
- que tiene `cq:template`
- que la plantilla está permitida por configuración
- que ciertas propiedades requeridas existen en `jcr:content`
- que opcionalmente no haya una fecha `onTime` futura si la política lo considera bloqueo

La solución debe exponer este diagnóstico mediante:

1. **Servicio OSGi** reutilizable
2. **Servlet Sling** para consultar el estado de una página en JSON
3. **Configuración OSGi** para parametrizar reglas
4. **Tests unitarios** con AEM Mocks
5. **Documentación técnica** de uso y diseño

---

## Por qué esta feature encaja con Java / AEM

Este ejercicio fuerza al agente a trabajar correctamente con elementos muy habituales en AEM:

- lectura de contenido JCR mediante `Resource` / `Page`
- lógica en capa de servicio, no en el servlet
- configuración externa en `ui.config`
- diseño orientado a interfaces
- tests sobre contenido mockeado
- contrato JSON claro para diagnóstico

Es una feature suficientemente pequeña para un lab, pero suficientemente rica como para validar
si el agente realmente entiende el proyecto.

---

## Entregables

Al finalizar, deberías tener al menos estos archivos o equivalentes:

### En `core/src/main/java/...`

- `services/lab4_1AgenteDev/PagePublicationReadinessService.java`
- `services/lab4_1AgenteDev/impl/PagePublicationReadinessServiceImpl.java`
- `services/lab4_1AgenteDev/model/PagePublicationReadinessResult.java`
- `servlets/lab4_1AgenteDev/PagePublicationReadinessServlet.java`

### En `core/src/test/java/...`

- `services/lab4_1AgenteDev/impl/PagePublicationReadinessServiceImplTest.java`
- `servlets/lab4_1AgenteDev/PagePublicationReadinessServletTest.java`

### En `ui.config/...`

- Configuración OSGi del servicio (`.cfg.json`)

### En documentación

- `page-publication-readiness.md`
- `reflexion_lab_4.1.md`

---

## Estructura sugerida de la solución

```text
core/
└── src/main/java/com/vasscompany/dummyproject/core/
    ├── services/lab4_1AgenteDev/
    │   ├── PagePublicationReadinessService.java
    │   ├── model/
    │   │   └── PagePublicationReadinessResult.java
    │   └── impl/
    │       └── PagePublicationReadinessServiceImpl.java
    └── servlets/lab4_1AgenteDev/
        └── PagePublicationReadinessServlet.java

core/
└── src/test/java/com/vasscompany/dummyproject/core/
    ├── services/lab4_1AgenteDev/impl/
    │   └── PagePublicationReadinessServiceImplTest.java
    └── servlets/lab4_1AgenteDev/
        └── PagePublicationReadinessServletTest.java

ui.config/
└── src/main/content/jcr_root/apps/vass-university-dummyproject/osgiconfig/config/
    └── com.vasscompany.dummyproject.core.services.lab4_1AgenteDev.impl.PagePublicationReadinessServiceImpl.cfg.json
```

---

## Paso 1: Preparar el contexto del agente (20 min)

Antes de pedir implementación, abre simultáneamente en tu IDE:

- `arquitectura.md`
- `patrones.md`
- `pom.xml`
- este `README.md`

Y crea, si te ayuda, un archivo de trabajo local como:

```text
.copilot-context/agente-dev-java-aem.md
```

Ahí puedes pegar una versión adaptada del agente para este proyecto.

### Qué debe saber el agente

Tu agente debe asumir estas reglas:

- proyecto Maven multi-módulo
- la lógica de negocio vive en `core/services`
- el servlet debe ser fino y delegar en el servicio
- la configuración debe salir de OSGi, no de constantes hardcodeadas
- los tests deben usar JUnit 5, Mockito y/o AEM Mocks cuando aplique
- no debe usar APIs obsoletas ni patrones inseguros
- debe generar código legible, explicable y mantenible

---

## Paso 2: Crear el prompt maestro del Agente Dev (25 min)

Crea un prompt de sistema o prompt largo reutilizable para la IA.

### Prompt base sugerido

```text
Actúa como un desarrollador senior Java / AEM 6.5 especializado en proyectos Maven multi-módulo con OSGi Declarative Services, Sling Servlets y tests con JUnit 5 + AEM Mocks.

Contexto del proyecto:
- Proyecto: vass-university-dummyproject
- Arquitectura por capas: presentación (ui.apps), aplicación (servlets/models), servicios (core/services), configuración (ui.config)
- La lógica de negocio debe residir en servicios OSGi con interfaz
- La configuración debe venir de OSGi config, no hardcodeada
- Debes respetar separación de responsabilidades, null safety, testabilidad y mantenibilidad
- Debes proponer nombres semánticos, Javadoc cuando aporte valor y tests útiles
- No uses getAdministrativeResourceResolver()
- No metas lógica de negocio en el servlet
- No hardcodes paths de contenido salvo que el ejercicio lo exija explícitamente
- Si hace falta acceder al repositorio, usa APIs de Sling/AEM correctas y diseña pensando en AEM Mocks

Tarea:
Implementar la feature "Page Publication Readiness".

La solución debe incluir:
1. Interfaz de servicio
2. Implementación OSGi
3. DTO/resultado de validación
4. Servlet GET que devuelva JSON
5. Configuración OSGi
6. Tests unitarios
7. Documentación técnica breve

Reglas funcionales mínimas:
- Validar que el recurso existe
- Validar que es una página válida
- Validar jcr:title
- Validar cq:template
- Validar que la plantilla esté permitida por configuración
- Validar propiedades requeridas configurables
- Poder devolver lista de errores y lista de warnings
- Exponer un boolean final: readyForPublication

Reglas de implementación:
- El servlet debe delegar en el servicio
- El resultado debe ser serializable a JSON de forma clara
- La configuración OSGi debe permitir cambiar templates permitidas y propiedades requeridas
- Los tests deben cubrir happy path, faltas de propiedades, plantilla no permitida y recurso inexistente

Entrega el resultado en bloques por archivo, indicando ruta de cada archivo.
Si detectas ambigüedades, toma decisiones razonables y explícalas brevemente.
```

### Qué debes comprobar del prompt

- [ ] ¿Define el rol de la IA con precisión?
- [ ] ¿Incluye contexto real del proyecto?
- [ ] ¿Marca restricciones técnicas concretas?
- [ ] ¿Define claramente entregables?
- [ ] ¿Evita respuestas demasiado genéricas?

---

## Paso 3: Diseñar la feature con la IA antes de codificar (20 min)

Antes de pedir código, pide al agente una **propuesta de diseño**.

### Prompt sugerido

```text
Antes de generar código, propón el diseño de la feature Page Publication Readiness para este proyecto Java/AEM.

Necesito:
- lista de clases/interfaces a crear
- responsabilidad de cada una
- contrato del DTO de salida
- propuesta de configuración OSGi
- estrategia de tests
- riesgos de diseño a evitar

No generes aún la implementación completa; primero dame el diseño propuesto y justifícalo.
```

### Qué debes revisar en la respuesta

- ¿La lógica principal vive en el servicio?
- ¿El DTO tiene campos útiles para depuración?
- ¿La configuración OSGi está bien pensada?
- ¿La estrategia de tests cubre casos reales?
- ¿El servlet se mantiene delgado?

**No sigas al siguiente paso hasta validar manualmente este diseño.**

---

## Paso 4: Implementación guiada por artefactos (60-75 min)

Ahora sí, pide generación por bloques de archivos.

### Orden recomendado

1. Interfaz del servicio
2. DTO de resultado
3. Implementación OSGi
4. Servlet
5. Configuración OSGi
6. Tests del servicio
7. Tests del servlet
8. Documentación técnica

### Importante

No aceptes todo de golpe sin revisar. Trabaja iterativamente:

1. Pide 1 o 2 archivos
2. Revisa semántica y arquitectura
3. Ajusta con prompts correctivos
4. Continúa con el siguiente bloque

### Ejemplos de prompts correctivos útiles

```text
Reescribe esta implementación para reducir complejidad ciclomática y extraer validaciones privadas con nombres semánticos.
```

```text
Ajusta el servlet para que no contenga lógica de negocio y solo haga parsing mínimo de la request + delegación + construcción de respuesta.
```

```text
Rehaz estos tests para usar AEM Mocks con contenido cargado en memoria en lugar de mocks excesivos de bajo nivel.
```

```text
Haz que la configuración OSGi sea más expresiva y fácil de mantener en distintos runmodes.
```

---

## Paso 5: Archivos concretos que deberías obtener

### 5.1 Interfaz de servicio

La interfaz debería exponer algo parecido a:

```java
PagePublicationReadinessResult evaluatePage(String pagePath, ResourceResolver resourceResolver);
```

o bien una variante que reciba `Resource` o `Page` si decides que el contrato es mejor así.

### 5.2 DTO de resultado

El resultado debería incluir al menos:

- `boolean readyForPublication`
- `String pagePath`
- `List<String> errors`
- `List<String> warnings`
- `List<String> validatedProperties`
- `String template`

### 5.3 Implementación OSGi

La implementación debe:

- usar `@Component(service = PagePublicationReadinessService.class)`
- usar `@Designate` y `@ObjectClassDefinition` si introduces configuración tipada
- leer configuración de templates permitidas y propiedades requeridas
- validar en métodos privados bien nombrados
- devolver resultado determinista y testeable

### 5.4 Servlet

El servlet debe:

- ser `GET`
- exponer JSON
- obtener la página desde el request actual o desde un parámetro controlado
- delegar en el servicio
- devolver respuesta clara y consistente

### 5.5 Configuración OSGi

La config puede incluir propiedades como:

- `enabled`
- `allowedTemplates`
- `requiredPageProperties`
- `failWhenOnTimeIsFuture`
- `includeWarnings`

### 5.6 Tests del servicio

Debes cubrir como mínimo:

- página válida y lista para publicar
- página sin `jcr:title`
- página sin `cq:template`
- plantilla no permitida
- propiedad requerida ausente
- recurso inexistente
- `onTime` futura cuando la política lo bloquea

### 5.7 Tests del servlet

Debes cubrir como mínimo:

- respuesta `200` con JSON correcto
- caso de path inválido
- caso de error controlado
- serialización coherente del resultado

---

## Paso 6: Validación técnica (20-30 min)

### Validación mínima local

Ejecuta lo que aplique en tu entorno:

```bash
mvn -q -DskipTests=false test
```

Si el ejercicio crece más y añades más módulos o validaciones:

```bash
mvn clean install
```

### Checklist técnico

- [ ] ¿Compila el código?
- [ ] ¿Los tests pasan?
- [ ] ¿Los nombres de clases y métodos tienen semántica?
- [ ] ¿La complejidad es razonable?
- [ ] ¿La lógica está donde toca?
- [ ] ¿La configuración está externalizada?
- [ ] ¿El JSON devuelto es claro?

---

## Paso 7: Auditoría manual del trabajo del agente (20 min)

Después de usar IA, debes revisar manualmente la solución. Este paso es obligatorio.

### Checklist de revisión AEM / Java

#### Arquitectura
- [ ] ¿El servlet delega realmente en el servicio?
- [ ] ¿La interfaz del servicio está bien definida?
- [ ] ¿La implementación no mezcla demasiadas responsabilidades?
- [ ] ¿El DTO evita acoplar el contrato a clases internas de AEM?

#### OSGi
- [ ] ¿El `@Component` registra la interfaz correcta?
- [ ] ¿La configuración está modelada con `@ObjectClassDefinition` si procede?
- [ ] ¿Se maneja correctamente `@Activate` / `@Modified` si aplica?

#### Seguridad
- [ ] ¿No hay credenciales hardcodeadas?
- [ ] ¿No se usa `getAdministrativeResourceResolver()`?
- [ ] ¿No se exponen internals sensibles en la respuesta JSON?
- [ ] ¿Los inputs están validados?

#### Calidad
- [ ] ¿La implementación evita null handling confuso?
- [ ] ¿Los mensajes de error son útiles?
- [ ] ¿Los tests son legibles?
- [ ] ¿La documentación coincide con el comportamiento real?

---

## Paso 8: Documentación técnica simultánea (15 min)

Pide al agente una documentación breve pero útil.

### Prompt sugerido

```text
Genera una documentación técnica breve para la feature Page Publication Readiness.

Debe incluir:
- objetivo
- clases principales
- flujo de ejecución
- configuración OSGi
- ejemplo de respuesta JSON
- cómo se prueba
- limitaciones conocidas

Quiero una documentación realista para proyecto Java/AEM, no texto genérico.
```

### Contenido mínimo esperado en `page-publication-readiness.md`

- Descripción funcional
- Diseño de clases
- Configuración OSGi
- Ejemplo de request y response
- Estrategia de tests
- Posibles extensiones futuras

---

## Paso 9: Reflexión sobre el uso del agente (15 min)

Crea el archivo:

```text
reflexion_lab_4.1.md
```

Responde al menos:

1. ¿Qué parte hizo mejor el agente: diseño, implementación, tests o documentación?
2. ¿En qué parte hubo que corregir más a la IA?
3. ¿Qué instrucciones del prompt fueron más útiles?
4. ¿Qué errores típicos cometió la IA?
5. ¿Qué harías diferente al preparar el agente la próxima vez?
6. ¿Cuánto tiempo ahorraste frente a hacerlo sin agente especializado?

---

## Criterios de Aceptación

El laboratorio se considera completado cuando:

- [ ] Existe una feature funcional de Page Publication Readiness
- [ ] Hay interfaz + implementación + servlet + config + tests + documentación
- [ ] La lógica principal está en la capa de servicio
- [ ] La configuración está externalizada en OSGi
- [ ] Los tests cubren happy path y casos edge relevantes
- [ ] La salida JSON es clara y coherente
- [ ] Se ha usado un prompt maestro / agente especializado
- [ ] Se documentó la reflexión sobre el proceso

---

## Qué se evalúa realmente en este lab

No solo se evalúa el código final. También se evalúa si has aprendido a:

- preparar contexto útil para la IA
- guiar la implementación por etapas
- revisar críticamente lo generado
- evitar el uso pasivo de sugerencias
- conseguir una salida consistente en varios artefactos

---

## Rúbrica de Evaluación

| Criterio | Excelente (4) | Bueno (3) | Satisfactorio (2) | Necesita Mejora (1) |
|----------|---------------|-----------|-------------------|---------------------|
| **Preparación del Agente** | Contexto muy completo, restricciones claras, prompt reutilizable | Buen contexto y prompt útil | Prompt aceptable pero genérico | Prompt pobre o ambiguo |
| **Diseño de la Solución** | Diseño claro, desacoplado, muy alineado con AEM | Diseño correcto con pocos ajustes | Diseño funcional con varias mejoras posibles | Diseño confuso o mal distribuido |
| **Calidad del Código** | Legible, robusto, bien separado y mantenible | Bueno con pequeños problemas | Funcional pero mejorable | Acoplado, frágil o poco claro |
| **Tests** | Cobertura amplia y casos edge bien elegidos | Tests buenos con cobertura razonable | Tests básicos pero útiles | Tests insuficientes o ausentes |
| **Uso Estratégico de IA** | Iterativo, crítico y bien guiado | Buen uso con varias correcciones | Uso aceptable pero poco dirigido | Uso pasivo |
| **Documentación y Reflexión** | Muy útiles, concretas y honestas | Buen nivel general | Cumplen de forma básica | Superficiales o incompletas |

**Puntuación mínima para aprobar: 14/24**

---

## Errores típicos a evitar

- Pedir “hazme la feature completa” sin contexto
- Aceptar código largo sin revisarlo por bloques
- Permitir lógica de negocio dentro del servlet
- No externalizar reglas en OSGi config
- Hacer tests demasiado mockeados y poco expresivos
- Documentar algo distinto de lo realmente implementado
- No revisar manualmente seguridad, null safety y naming

---

## Extensiones Opcionales (Bonus)

Si terminas antes, puedes ampliar la feature con alguna de estas mejoras:

1. **Selector por idioma o site root**
    - Validar requisitos distintos según árbol de contenido

2. **Warnings enriquecidos**
    - Diferenciar bloqueantes vs. observaciones no bloqueantes

3. **Integración con Sling Model**
    - Exponer el resultado en un componente HTL de diagnóstico

4. **Compatibilidad por runmode**
    - Variar configuración por `config.author` y `config.publish`

5. **Más detalle de pruebas**
    - Añadir tests parametrizados o fixtures de contenido más ricos

---

## Consejos prácticos

### Si la IA propone demasiado acoplamiento a AEM
Pídele que separe mejor el contrato de servicio del mecanismo de acceso HTTP.

### Si el servlet sale demasiado gordo
Haz que devuelva solo parseo mínimo + delegación + serialización.

### Si los tests son pobres
Pide explícitamente:

```text
Reescribe los tests para que sean expresivos, independientes y basados en AEM Mocks con contenido representativo.
```

### Si la documentación sale genérica
Pega los nombres reales de clases y exige ejemplos reales de JSON.

---

## Resultado esperado al finalizar

Al acabar este laboratorio deberías haber comprobado que un “agente dev” útil no es solo una
IA que genera código, sino una IA que trabaja con:

- contexto del proyecto
- restricciones explícitas
- validación humana intermedia
- entregables múltiples coherentes entre sí

Ese es el salto importante de esta lección.

---

**Versión**: 1.0
