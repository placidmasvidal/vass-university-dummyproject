# Lab 4.2: Suite Completa de Tests con Agente QA (Java / AEM)

## Objetivo

Generar una **suite completa de tests** usando un **Agente QA especializado** en Java / AEM,
capaz de producir pruebas útiles, mantenibles y alineadas con la arquitectura real del
proyecto, no simples tests superficiales para “subir cobertura”.

En esta adaptación, el foco está en aprender a dirigir una IA para que piense como un
perfil QA técnico dentro de un proyecto AEM:

- entendiendo capas y responsabilidades
- identificando riesgos funcionales y técnicos
- proponiendo la estrategia correcta según el tipo de pieza
- generando tests unitarios, de integración y de contrato cuando aplique
- cubriendo edge cases típicos de AEM / OSGi

La meta es que la IA te ayude a construir una **batería de tests verdaderamente útil**,
con cobertura alta y con capacidad real de detectar regresiones.

---

## Duración Estimada

**2 horas**

---

## Prerequisitos

- ✅ Lab 4.1 completado o, como mínimo, entendido
- ✅ Copilot Chat o herramienta IA equivalente disponible
- ✅ Proyecto `vass-university-dummyproject` compilando localmente
- ✅ Conocimiento básico de JUnit 5, Mockito y AEM Mocks
- ✅ Haber revisado `arquitectura.md`, `patrones.md` y el `pom.xml` del proyecto
- ✅ Tener clara la diferencia entre unit tests, integration tests y UI tests en AEM

---

## Qué significa “Agente QA” en este lab

En el laboratorio original del curso se plantea usar un agente QA para generar una suite
completa de pruebas. En esta adaptación a Java/AEM, ese agente no debe limitarse a crear
`assertEquals` triviales, sino actuar como un **QA técnico especializado** en:

- servicios OSGi
- Sling Servlets
- modelos o DTOs de soporte
- configuraciones OSGi
- comportamiento sobre contenido JCR simulado
- contratos JSON
- regresiones entre author y publish cuando proceda

Tu trabajo consiste en **darle el contexto suficiente** para que proponga la estrategia
correcta de pruebas y luego iterar con él hasta obtener una suite sólida.

---

## Contexto del Ejercicio

Vas a trabajar sobre una feature ya existente o recién implementada en el lab anterior:

# **Page Publication Readiness**

La feature evalúa si una página AEM está lista para publicarse y devuelve un diagnóstico
con validaciones como:

- existencia de la página
- presencia de `jcr:title`
- presencia de `cq:template`
- plantilla permitida por configuración
- propiedades obligatorias en `jcr:content`
- validación opcional de fechas o condiciones de bloqueo

La solución funcional está compuesta, como mínimo, por:

1. **Servicio OSGi** con la lógica de validación
2. **DTO / modelo de resultado**
3. **Servlet Sling** que expone el resultado en JSON
4. **Configuración OSGi** con reglas parametrizables

Tu objetivo en este laboratorio no es implementar la feature, sino construir su
**suite completa de tests con ayuda del Agente QA**.

---

## Por qué este enfoque encaja con Java / AEM

En AEM no todos los tests son iguales, y uno de los errores más frecuentes al usar IA es
pedir “hazme tests” sin definir bien qué nivel corresponde a cada pieza.

Este laboratorio sirve para entrenar precisamente eso:

- **Servicio OSGi** → tests unitarios con AEM Mocks y/o Mockito
- **Servlet** → tests de contrato JSON y manejo de errores
- **Configuración** → tests sobre activación y comportamiento configurado
- **Integración** → validación opcional en instancia AEM real (`it.tests`)
- **UI / E2E** → solo si aporta valor, no por defecto

Así practicas un uso más maduro de la IA: no solo generar código, sino decidir qué probar,
con qué profundidad y con qué herramienta.

---

## Entregables

Al finalizar el laboratorio deberías tener, como mínimo, lo siguiente:

### En `core/src/test/java/...`

- `services/lab4_1AgenteDev/impl/PagePublicationReadinessServiceImplTest.java`
- `servlets/lab4_1AgenteDev/PagePublicationReadinessServletTest.java`
- tests adicionales para DTOs o utilidades si aportan valor

### Opcional en `it.tests/...`

- test de integración para validar el endpoint en una instancia AEM real

### En documentación

- `qa-strategy-page-publication-readiness.md`
- `reflexion_lab_4.2.md`

---

## Estructura sugerida

```text
core/
└── src/test/java/com/vasscompany/dummyproject/core/
    ├── services/lab4_1AgenteDev/impl/
    │   └── PagePublicationReadinessServiceImplTest.java
    └── servlets/lab4_1AgenteDev/
        └── PagePublicationReadinessServletTest.java

it.tests/
└── src/test/java/com/vasscompany/dummyproject/it/tests/lab4_2AgenteQa/
    └── PagePublicationReadinessEndpointIT.java

core/src/main/java/com/vasscompany/dummyproject/core/services/lab4_2AgenteQa/impl/
└── README.md
```

---

## Paso 1: Preparar el contexto del Agente QA (15 min)

Antes de pedir tests, abre simultáneamente en el IDE:

- `arquitectura.md`
- `patrones.md`
- `pom.xml`
- el código de la feature (`Service`, `Impl`, `Servlet`, `DTO`)
- este `README.md`

Si te resulta cómodo, crea además un archivo auxiliar como:

```text
.copilot-context/agente-qa-java-aem.md
```

Ahí puedes dejar el prompt maestro del agente para reutilizarlo durante el lab.

### Qué debe saber el agente

Tu agente debe asumir como mínimo estas reglas:

- el proyecto es Maven multi-módulo
- los servicios viven en `core`
- la configuración vive en `ui.config`
- los tests unitarios van en `core/src/test/java`
- los tests de integración Java van en `it.tests`
- se usa JUnit 5 y AEM Mocks
- el servlet debe probarse como contrato HTTP/JSON, no mezclando toda la lógica del servicio
- no debe proponer tests frágiles ni excesivamente acoplados a detalles internos irrelevantes

---

## Paso 2: Diseñar el prompt maestro del Agente QA (20 min)

Crea un prompt largo o system prompt para convertir la IA en un QA técnico Java/AEM.

### Prompt base sugerido

```text
Actúa como un QA Automation Engineer senior especializado en Java, AEM 6.5, JUnit 5, Mockito, AEM Mocks y testing de Sling Servlets.

Contexto del proyecto:
- Proyecto Maven multi-módulo: vass-university-dummyproject
- Arquitectura por capas: presentación (ui.apps), aplicación (servlets/models), servicios (core/services), configuración (ui.config)
- La lógica de negocio reside en servicios OSGi
- Los servlets deben ser finos y delegar en servicios
- El proyecto dispone de AEM Mocks para tests unitarios y módulo it.tests para integración

Tu objetivo es diseñar y generar una suite de tests útil, mantenible y con alta capacidad de detección de regresiones.

Reglas:
- No generes tests superficiales solo para aumentar cobertura
- Prioriza escenarios de negocio, validaciones, edge cases y manejo de errores
- Separa correctamente tests unitarios de tests de integración
- Usa nombres de test descriptivos
- Evita mocks innecesarios si AEM Mocks resuelve mejor el caso
- Señala riesgos no cubiertos y sugiere pruebas adicionales cuando proceda
- Si detectas problemas de diseño que dificultan el testing, indícalos antes de proponer la suite

Tarea:
Generar la estrategia y la suite de tests para la feature Page Publication Readiness.
Incluye:
1. matriz de escenarios
2. tests unitarios del servicio
3. tests del servlet
4. propuesta de test de integración opcional
5. cobertura de casos negativos y edge cases
6. breve explicación de por qué cada bloque de pruebas aporta valor
```

### Qué debería producir una buena respuesta del agente

Una buena respuesta no solo devuelve código. También debería devolverte:

- mapa de riesgos
- matriz de pruebas por componente
- propuesta de cobertura mínima
- sugerencias de refactor si el código es difícil de probar
- tests ordenados por prioridad

---

## Paso 3: Pedir primero la estrategia, no el código (15 min)

Antes de dejar que la IA escriba tests, pídele una **estrategia QA**.

### Ejemplo de prompt de estrategia

```text
Analiza esta feature Java/AEM y propón una estrategia de testing completa.
Quiero que distingas claramente:
- qué probar con unit tests
- qué probar con AEM Mocks
- qué probar con integración real
- qué no merece la pena probar a este nivel

Además, construye una tabla con:
- componente
- riesgo
- escenarios críticos
- tipo de test recomendado
- prioridad
```

### Resultado esperado

Deberías obtener algo similar a esto:

| Componente | Riesgo principal | Escenarios críticos | Tipo de test | Prioridad |
|------------|------------------|---------------------|--------------|-----------|
| Servicio OSGi | Falsos positivos/negativos de readiness | página válida, página inexistente, falta `jcr:title`, plantilla no permitida | Unit + AEM Mocks | Alta |
| DTO resultado | Contrato inconsistente | flags, mensajes, lista de errores | Unit | Media |
| Servlet | JSON incorrecto / códigos HTTP erróneos | parámetro faltante, página no encontrada, respuesta exitosa | Unit | Alta |
| Config OSGi | Reglas no aplicadas | plantillas permitidas, propiedades obligatorias | Unit | Alta |
| Endpoint real | Diferencias entre mocks y runtime | serialización real, registro servlet, wiring OSGi | Integration | Media |

No pases al código hasta que esta estrategia tenga sentido.

---

## Paso 4: Generar tests unitarios del servicio (35 min)

Empieza por el componente más importante: el servicio OSGi.

### Qué debe cubrir la suite del servicio

Como mínimo:

- página válida → resultado positivo
- recurso inexistente → resultado negativo claro
- recurso no adaptable a `Page` → resultado negativo
- falta `jcr:title` → fallo esperado
- falta `cq:template` → fallo esperado
- plantilla no permitida → fallo esperado
- falta de propiedades obligatorias configuradas → fallo esperado
- combinación de varios errores → resultado con múltiples motivos
- configuración vacía o nula → comportamiento por defecto bien definido

### Prompt sugerido para el servicio

```text
Genera tests unitarios JUnit 5 para PagePublicationReadinessServiceImpl.

Quiero:
- uso de AEM Mocks cuando aporte valor para construir contenido JCR simulado
- nombres de tests expresivos
- preparación clara del contexto
- validación tanto de booleanos como de mensajes de error o lista de validaciones
- cobertura de escenarios positivos, negativos y edge cases

Asume que el servicio se registra vía OSGi y puede requerir configuración de activación.
Si necesitas simular configuración, muestra cómo registrarla correctamente en el test.
```

### Recomendaciones prácticas

- Usa `AemContext` para montar árboles de contenido realistas
- Registra el servicio con `context.registerInjectActivateService(...)`
- Mantén helpers de creación de páginas para evitar duplicación
- No pruebes detalles privados; prueba comportamiento observable

---

## Paso 5: Generar tests del servlet (25 min)

Una vez cubierto el servicio, genera la suite del servlet.

### Qué debe cubrir la suite del servlet

Como mínimo:

- falta del parámetro `path` o equivalente → `400 Bad Request`
- path inexistente → respuesta controlada
- llamada válida → `200 OK` con JSON correcto
- error inesperado del servicio → `500 Internal Server Error`
- JSON contiene campos esperados del contrato
- el servlet delega en el servicio, no replica lógica de negocio

### Prompt sugerido para el servlet

```text
Genera tests unitarios para un Sling Servlet GET que expone el resultado de Page Publication Readiness en JSON.

Quiero que los tests verifiquen:
- código HTTP
- content type
- campos JSON relevantes
- validación de parámetros de entrada
- manejo de errores del servicio

Usa AEM Mocks y Mockito cuando sea apropiado.
No metas en estos tests validaciones detalladas de negocio que ya pertenecen al servicio.
```

### Buen criterio QA aquí

La pregunta no es solo “¿devuelve algo?”, sino:

- ¿devuelve el código correcto?
- ¿devuelve el contrato esperado?
- ¿fallos previsibles están bien representados?
- ¿queda claro dónde termina la responsabilidad del servlet?

---

## Paso 6: Evaluar si hace falta integración real (10 min)

No todo necesita un test de integración, pero debes aprender a decidirlo.

### Sí merece la pena si quieres validar

- registro real del servlet
- wiring OSGi completo
- serialización real en runtime
- diferencias entre mocks y AEM real
- despliegue correcto de configuración

### Puede no merecer la pena si

- todo el riesgo principal ya está cubierto por unit tests
- el comportamiento es puramente determinista y bien aislado
- el coste de mantenimiento supera el beneficio

### Ejemplo de objetivo para test de integración

```text
Validar que el endpoint /bin/page-readiness responde correctamente en una instancia AEM local con una página de prueba y configuración OSGi desplegada.
```

Si decides hacerlo, colócalo en `it.tests` y ejecútalo con:

```bash
mvn clean verify -Pit
```

---

## Paso 7: Revisar cobertura útil, no solo porcentaje (10 min)

El laboratorio original habla de superar el 90% de cobertura. En esta adaptación,
ese objetivo sigue siendo válido como referencia, pero **no debe perseguirse a ciegas**.

### Preguntas que debes hacerte

- ¿la suite detectaría una regresión real en las reglas de readiness?
- ¿se cubren errores de configuración?
- ¿se cubren inputs inválidos?
- ¿el servlet está probado como contrato y no solo como método?
- ¿hay tests redundantes que solo inflan cobertura?

### Señales de una buena suite

- cubre caminos felices y fallos realistas
- usa datos de prueba legibles
- minimiza acoplamiento innecesario
- permite entender el comportamiento del sistema leyendo los tests
- falla de forma diagnóstica cuando algo se rompe

---

## Paso 8: Pedir al Agente QA una auditoría de la propia suite (10 min)

Una vez generados los tests, vuelve a usar la IA como revisor.

### Prompt sugerido de auditoría

```text
Revisa esta suite de tests como si fueras un QA lead.
Indica:
- huecos de cobertura
- tests redundantes
- smells de test design
- dependencias frágiles
- escenarios negativos que faltan
- mejoras prioritarias antes de dar la suite por buena
```

Esto te ayuda a no aceptar la primera tanda de tests sin espíritu crítico.

---

## Criterios de Aceptación

El laboratorio se considera completo cuando:

- [ ] Existe una estrategia de testing documentada antes de escribir tests
- [ ] El servicio OSGi tiene una suite útil de tests unitarios
- [ ] El servlet tiene tests de contrato y manejo de errores
- [ ] Se usan AEM Mocks adecuadamente cuando aportan valor
- [ ] La suite cubre escenarios positivos, negativos y edge cases
- [ ] Se diferencian correctamente unit tests e integration tests
- [ ] La cobertura es alta y razonable (objetivo orientativo: >90%)
- [ ] Hay reflexión documentada sobre el uso del Agente QA

---

## Entregable de Reflexión

Crea un archivo:

```text
reflexion_lab_4.2.md
```

Incluye como mínimo:

- cómo estructuraste el prompt del agente
- qué tipo de tests propuso bien desde el principio
- qué tipo de tests propuso mal o de forma superficial
- qué correcciones hiciste manualmente
- qué aprendiste sobre testing en Java/AEM con IA
- qué harías distinto en el siguiente laboratorio

---

## Evaluación

### Rúbrica orientativa

| Criterio | Excelente (4) | Bueno (3) | Satisfactorio (2) | Necesita Mejora (1) |
|----------|---------------|-----------|-------------------|---------------------|
| **Estrategia QA** | Diferencia perfectamente capas, riesgos y niveles de prueba | Estrategia sólida con pocos huecos | Estrategia básica pero válida | Estrategia confusa o superficial |
| **Tests del servicio** | Cobertura excelente de negocio y edge cases | Buena cobertura con pocos huecos | Cobertura funcional básica | Tests insuficientes o triviales |
| **Tests del servlet** | Contrato HTTP/JSON y errores muy bien cubiertos | Buena cobertura de contrato | Cobertura parcial | Tests pobres o acoplados |
| **Uso del Agente QA** | Uso estratégico, iterativo y crítico | Buen uso con cierta revisión | Uso básico | Uso pasivo, aceptando todo |
| **Reflexión** | Clara, crítica y con aprendizajes accionables | Buena reflexión | Reflexión básica | Reflexión superficial o ausente |

**Puntuación mínima para aprobar: 12/20**

---

## Tips y Ayuda

### Si la IA propone tests pobres

- pídele primero la estrategia y no el código
- delimita mejor el nivel de test
- añade el código real del servicio/servlet al contexto
- exige cobertura de edge cases y manejo de errores

### Si la IA mezcla responsabilidades

Recuerda:

- el **servicio** se prueba por lógica de negocio
- el **servlet** se prueba por contrato HTTP/JSON y validación de inputs
- la **integración** se usa para validar wiring/runtime, no para repetir todo lo unitario

### Si te genera demasiados mocks

Pídele explícitamente que prefiera:

- `AemContext` para contenido y recursos
- `Mockito` solo donde aporte aislamiento claro
- helpers legibles en tests en lugar de setups enormes repetidos

---

## Extensión Opcional (Bonus)

Si completas el laboratorio antes de tiempo, prueba una o varias de estas extensiones:

1. **Mutation thinking**: pide a la IA qué cambios en el código romperían la suite y cuáles no
2. **Test data builders**: refactoriza la suite para mejorar legibilidad
3. **Contract snapshot controlado**: verifica JSON de salida con enfoque mantenible
4. **Comparativa de agentes**: genera la estrategia con dos prompts distintos y compara resultados
5. **Cobertura de configuración**: añade tests específicos para activación OSGi y defaults

---

## Relación con la arquitectura del proyecto

Este laboratorio encaja con el proyecto dummy porque su arquitectura separa claramente:

- servicios OSGi en `core`
- configuración en `ui.config`
- tests unitarios en `core/src/test/java`
- tests de integración en `it.tests`
- tests funcionales UI en `ui.tests`

Eso permite decidir con criterio **qué probar en cada módulo** y evita meter toda la
estrategia QA en una sola capa.

---

## Cierre

Este laboratorio no trata solo de “hacer más tests”.

Trata de aprender a usar la IA como un **QA técnico con criterio**, capaz de ayudarte a:

- pensar en riesgos
- diseñar cobertura útil
- separar niveles de prueba
- detectar huecos antes de que lleguen a producción

Si al terminar sientes que podrías volver a aplicar este flujo sobre cualquier servicio,
servlet o feature pequeña de AEM, el laboratorio habrá cumplido su objetivo.

---

**Versión**: 1.0
