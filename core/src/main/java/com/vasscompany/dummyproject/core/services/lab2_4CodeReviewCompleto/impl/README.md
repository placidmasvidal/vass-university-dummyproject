# Lab 2.4: Code Review Completo de PR Real (Java / AEM)

## Objetivo

Realizar un **code review completo y profesional** de una Pull Request real de un proyecto
**Java / AEM 6.5**, usando IA como asistente para identificar bugs, riesgos de seguridad,
problemas de diseño, deuda técnica y mejoras de calidad de forma sistemática.

El objetivo no es solo “detectar cosas”, sino aprender a revisar una PR con criterio de
proyecto real:

- funcionalidad correcta
- alineación con la arquitectura del proyecto
- buenas prácticas AEM / OSGi
- seguridad
- testabilidad
- mantenibilidad

---

## Duración Estimada

**2-3 horas**

---

## Prerequisitos

- ✅ Sesiones anteriores completadas
- ✅ Labs 1.3, 2.1, 2.2 y 2.3 completados
- ✅ Copilot Chat o herramienta IA equivalente disponible
- ✅ Conocimiento básico de Java, Maven, OSGi DS y AEM 6.5
- ✅ Proyecto `vass-university-dummyproject` compilando localmente
- ✅ Acceso a una PR real del proyecto dummy o a una PR equivalente proporcionada por el facilitador

---

## Contexto del Ejercicio

En este laboratorio no vas a implementar una feature nueva. Tu trabajo será actuar como
**reviewer principal** de una Pull Request realista dentro del ecosistema Java/AEM del proyecto.

La PR puede afectar una o varias de estas capas:

- **Servicios OSGi** en `core/src/main/java/.../services/`
- **Servlets** en `core/src/main/java/.../servlets/`
- **Sling Models** en `core/src/main/java/.../models/`
- **Configuración OSGi** en `ui.config/src/main/content/jcr_root/apps/.../osgiconfig/`
- **Componentes HTL / clientlibs** en `ui.apps`
- **Tests unitarios** en `core/src/test/java/`
- **Tests de integración** en `it.tests/`

Debes revisar la PR como si fuera a mergearse en un proyecto real del equipo.

---

## Qué se espera de un review completo en Java/AEM

Tu review debe cubrir, como mínimo:

1. **Estructura y arquitectura**
   - ¿El cambio cae en la capa correcta?
   - ¿Respeta la separación entre servlet, modelo, servicio y configuración?
   - ¿Hay lógica de negocio donde no debería?

2. **Bugs y comportamiento funcional**
   - ¿La lógica hace realmente lo que promete la PR?
   - ¿Hay casos edge no manejados?
   - ¿Hay null safety suficiente?
   - ¿Se introducen regresiones?

3. **Seguridad**
   - ¿Hay secretos, rutas o credenciales hardcodeadas?
   - ¿Se usan `ResourceResolver` y permisos correctamente?
   - ¿Se validan inputs en servlets y servicios?
   - ¿Se exponen datos sensibles en logs o respuestas?

4. **Calidad y mantenibilidad**
   - ¿El código es legible?
   - ¿Los nombres tienen semántica?
   - ¿Hay duplicación?
   - ¿La complejidad es razonable?
   - ¿La documentación es suficiente?

5. **Testing**
   - ¿Hay tests unitarios o de integración cuando son necesarios?
   - ¿Los tests cubren happy path, errores y edge cases?
   - ¿El cambio es fácilmente verificable con `mvn test` o `mvn verify`?

6. **Deuda técnica**
   - ¿La PR resuelve el problema rápido pero deja deuda?
   - ¿Hay dependencias no justificadas?
   - ¿Se acopla demasiado a AEM o al JCR?

---

## Selección de la PR

Escoge una de estas opciones:

### Opción A: PR real del proyecto dummy

Usa una PR real creada para este repositorio dummy.

### Opción B: Rama de trabajo propia

Si no tienes una PR real disponible, crea una rama con un cambio suficientemente amplio y
abre una PR local o en tu plataforma Git.

### Opción C: PR proporcionada por facilitador

Si durante el curso os proporcionan una PR común, úsala como base del laboratorio.

---

## Instrucciones Paso a Paso

### Paso 1: Preparación y contexto (15 min)

**Tareas:**

1. Abre la PR en GitHub / GitLab / Azure DevOps
2. Lee el título y la descripción completos
3. Identifica el objetivo funcional del cambio
4. Lista los archivos modificados
5. Abre también archivos relacionados para entender el contexto
6. Revisa si el cambio toca varias capas del proyecto

**Documenta inicialmente:**

- título de la PR
- autor
- objetivo funcional
- alcance
- módulos afectados (`core`, `ui.apps`, `ui.config`, `it.tests`, etc.)

**Preguntas guía:**

- ¿El cambio es pequeño, mediano o grande?
- ¿Afecta solo a Java o también a configuración / contenido / UI?
- ¿La descripción de la PR explica bien el problema y la solución?
- ¿Falta contexto para poder revisar correctamente?

---

### Paso 2: Review estructural con IA (25 min)

Antes de ir línea por línea, usa IA para revisar si la PR está bien planteada a nivel de
arquitectura.

**Prompt sugerido para Copilot / IA:**

```text
Revisa estructuralmente esta Pull Request de un proyecto Java / AEM 6.5.

Contexto del proyecto:
- Arquitectura por capas: Sling Models, Servlets, Servicios OSGi, configuración en ui.config.
- Java 11.
- Maven multi-módulo.
- Convenciones del proyecto: la lógica de negocio debe vivir en servicios; los servlets deben ser finos; la configuración debe venir de OSGi y no de constantes hardcodeadas.

Título de la PR:
[pegar título]

Descripción:
[pegar descripción]

Archivos cambiados:
[listar archivos]

Analiza:
1. Si el cambio está en la capa correcta
2. Si sigue patrones del proyecto
3. Si hay mezcla de responsabilidades
4. Si hay duplicación o diseño mejorable
5. Si la organización del cambio es clara

Devuélveme observaciones concretas y accionables.
```

**Checklist estructural AEM:**

- [ ] ¿La lógica de negocio está en un servicio y no en un servlet o Sling Model?
- [ ] ¿Los servicios están registrados con la interfaz correcta (`@Component(service = MiServicio.class)`)?
- [ ] ¿La configuración dinámica está en `ui.config` y no hardcodeada en Java?
- [ ] ¿Se usan bindings de servlet adecuados (`@SlingServletResourceTypes` preferentemente)?
- [ ] ¿La PR evita acoplar demasiada lógica al repositorio JCR o a paths fijos?
- [ ] ¿La estructura del cambio coincide con la arquitectura multi-módulo del proyecto?

**Output esperado:**

- Lista inicial de observaciones estructurales
- Hipótesis de riesgos arquitectónicos
- Posibles puntos que revisar con más profundidad en pasos posteriores

---

### Paso 3: Review funcional y búsqueda de bugs con IA (35 min)

Ahora céntrate en el comportamiento del código.

**Tareas:**

1. Revisa métodos nuevos y modificados
2. Identifica supuestos no documentados
3. Busca nulls, ramas no cubiertas, estados inconsistentes y edge cases
4. Piensa qué pasa en author, publish y distintos runmodes si aplica
5. Revisa si el flujo falla silenciosamente o devuelve errores ambiguos

**Prompt sugerido:**

```text
Analiza esta Pull Request Java / AEM buscando bugs potenciales.

Archivos a revisar:
[listar archivos]

Busca específicamente:
- lógica incorrecta
- casos edge no manejados
- null pointer risks
- race conditions o problemas de thread safety en servicios OSGi
- uso incorrecto de Optional, streams o colecciones
- fugas de recursos (ResourceResolver, Session, InputStream, Query results)
- problemas de performance (queries, recorridos JCR, adaptTo repetidos, llamadas innecesarias)
- regresiones funcionales entre capas

Para cada problema, indica:
- archivo
- ubicación aproximada
- gravedad
- explicación
- sugerencia de corrección
```

**Checklist funcional / bugs:**

- [ ] ¿Se validan parámetros de entrada?
- [ ] ¿Se manejan `null`, vacíos y formatos inválidos?
- [ ] ¿Hay `catch (Exception)` demasiado amplios?
- [ ] ¿Se tragan excepciones sin contexto?
- [ ] ¿La respuesta del servlet distingue bien entre error de cliente y error interno?
- [ ] ¿Se cierran `ResourceResolver`, `Session`, `InputStream` o `Closeable` cuando aplica?
- [ ] ¿Se usa correctamente `try-with-resources` cuando corresponde?
- [ ] ¿La lógica depende de valores mágicos o strings mágicos?
- [ ] ¿Hay riesgos de consulta o recorrido JCR costoso?
- [ ] ¿El cambio puede comportarse distinto en author y publish?

**Output esperado:**

- Lista de bugs encontrados con ubicación
- Lista de casos edge no cubiertos
- Comentarios de performance o robustez

---

### Paso 4: Review de seguridad específico para Java/AEM (30 min)

Aplica una revisión de seguridad con mentalidad de proyecto real.

**Prompt sugerido:**

```text
Audita la seguridad de esta Pull Request de Java / AEM 6.5.

Contexto:
- Servicios OSGi
- Servlets Sling
- Configuración OSGi en ui.config
- Posible acceso al repositorio JCR

Busca:
- credenciales, secretos o endpoints hardcodeados
- exposición de datos sensibles en logs o respuestas
- validación insuficiente de inputs HTTP
- uso inseguro del repositorio o permisos elevados
- uso incorrecto de ResourceResolver administrativo
- riesgos OWASP relevantes para el tipo de cambio
- configuraciones inseguras por defecto

Devuélveme hallazgos concretos con severidad y recomendación.
```

**Checklist de seguridad AEM:**

**Credenciales / secretos / configuración**
- [ ] ¿Hay passwords, tokens, API keys o secretos hardcodeados?
- [ ] ¿Hay URLs sensibles o rutas internas hardcodeadas sin justificación?
- [ ] ¿La configuración que debe variar por entorno está en OSGi y no en constantes?

**Repositorio y permisos**
- [ ] ¿Se evita `getAdministrativeResourceResolver()`?
- [ ] ¿Se usa Service User si hay acceso al JCR desde backend?
- [ ] ¿Se minimizan permisos y alcance del acceso?

**Servlets / endpoints**
- [ ] ¿Se validan y sanitizan parámetros de entrada?
- [ ] ¿Se controla el método HTTP correcto?
- [ ] ¿Se devuelven códigos HTTP adecuados?
- [ ] ¿Se evita exponer stack traces o mensajes internos?

**Logs y datos sensibles**
- [ ] ¿Los logs evitan PII, tokens, contraseñas, JWT o datos de usuario sensibles?
- [ ] ¿El logging aporta contexto sin filtrar datos delicados?

**Configuración y despliegue**
- [ ] ¿Las configuraciones por runmode están bien ubicadas?
- [ ] ¿La PR evita defaults inseguros en `config`, `config.author`, `config.publish`, etc.?

**Output esperado:**

- Lista de problemas de seguridad
- Severidad por problema
- Recomendación concreta de corrección

---

### Paso 5: Review de calidad, mantenibilidad y deuda técnica (30 min)

Una PR puede funcionar y aun así introducir deuda.

**Prompt sugerido:**

```text
Analiza la calidad de código y deuda técnica de esta PR Java / AEM.

Revisa:
- legibilidad y mantenibilidad
- nombres y semántica
- complejidad ciclomática
- tamaño de métodos y cohesión
- code smells
- oportunidades de refactorización
- documentación y Javadoc
- testabilidad del diseño

Indica problemas reales, no observaciones cosméticas.
```

**Checklist de calidad:**

- [ ] ¿Los nombres de clases, métodos y variables son semánticos?
- [ ] ¿Las clases tienen una única responsabilidad clara?
- [ ] ¿Los métodos largos pueden separarse?
- [ ] ¿Hay duplicación de lógica?
- [ ] ¿Se usan enums / constantes cuando toca?
- [ ] ¿La API pública del servicio está bien definida?
- [ ] ¿Hay Javadoc o comentarios donde realmente aportan?
- [ ] ¿El código es fácil de testear sin arrancar AEM completo?
- [ ] ¿La PR incrementa el acoplamiento innecesariamente?
- [ ] ¿Se apoya demasiado en utilidades estáticas difíciles de mockear?

**Checklist específico AEM:**

- [ ] ¿Los Sling Models hacen solo trabajo de adaptación/presentación?
- [ ] ¿Los servlets son finos y delegan en servicios?
- [ ] ¿No se mezclan detalles HTL/clientlibs con lógica de negocio Java?
- [ ] ¿Las configuraciones OSGi tienen nombres y descripciones claras?
- [ ] ¿La solución sería mantenible por otro desarrollador del equipo?

**Output esperado:**

- Lista de mejoras de calidad
- Lista de puntos de deuda técnica
- Priorización entre bloqueantes y recomendables

---

### Paso 6: Review de tests y verificabilidad (20 min)

Revisa si la PR es verificable de forma razonable.

**Tareas:**

1. Comprueba si el cambio introduce o modifica tests
2. Evalúa si los tests cubren happy path, error path y edge cases
3. Verifica si faltan tests unitarios, de integración o UI
4. Intenta ejecutar localmente lo que sea razonable:
   - `mvn test`
   - `mvn clean install`
   - `mvn verify -Pit` si aplica

**Checklist de tests:**

- [ ] ¿Hay tests unitarios para la lógica nueva o modificada?
- [ ] ¿Se usan AEM Mocks cuando procede?
- [ ] ¿Se testean errores y validaciones?
- [ ] ¿Los tests son legibles y mantenibles?
- [ ] ¿Los tests dependen de estado compartido o son independientes?
- [ ] ¿Se testea la configuración si el cambio depende de ella?
- [ ] ¿Faltan tests de servlet, servicio o integración?

**Prompt sugerido:**

```text
Revisa si los tests de esta PR son suficientes para un proyecto Java / AEM.

Ten en cuenta:
- tests unitarios con JUnit 5
- posible uso de AEM Mocks
- validación de casos edge
- cobertura de errores
- mantenibilidad y claridad de los tests

Indica qué falta y qué tests adicionales recomendarías.
```

**Output esperado:**

- Evaluación de cobertura real
- Lista de tests faltantes
- Riesgos de mergear sin esos tests

---

### Paso 7: Documentar el review completo (25 min)

Ahora redacta un documento profesional con tu review.

**Crea archivo:**
`code_review_lab_2.4.md`

**Estructura sugerida:**

```markdown
# Code Review: [Título de la PR]

## Resumen Ejecutivo
- Estado: [Aprobar / Aprobar con cambios / Requiere cambios]
- Módulos afectados: [lista]
- Bugs encontrados: [número]
- Problemas de seguridad: [número]
- Mejoras de calidad: [número]
- Riesgos principales: [resumen breve]

## Contexto Revisado
- Objetivo funcional de la PR
- Alcance real del cambio
- Archivos revisados

## Problemas Bloqueantes

### [Título del problema]
- **Archivo**: `ruta/archivo.java`
- **Ubicación**: método / bloque / líneas aproximadas
- **Tipo**: Bug / Seguridad / Arquitectura / Test / Performance
- **Severidad**: Crítico / Alto / Medio / Bajo
- **Descripción**: [explicación]
- **Impacto**: [riesgo]
- **Recomendación**: [cómo corregirlo]

## Problemas de Seguridad
[repetir estructura]

## Problemas de Calidad y Deuda Técnica
[repetir estructura]

## Tests Faltantes o Mejorables
[lista]

## Puntos Positivos
- [qué está bien hecho]

## Recomendación Final
[justificación completa del estado final]
```

**Consejo:**
No redactes comentarios genéricos como “esto se puede mejorar”. Cada observación debe ser:

- concreta
- verificable
- accionable
- justificada

---

## Entregables

Al finalizar el lab, debes entregar:

1. ✅ `code_review_lab_2.4.md` con el review completo
2. ✅ Tabla resumen de hallazgos
3. ✅ Lista de bugs encontrados
4. ✅ Lista de problemas de seguridad encontrados o justificación de su ausencia
5. ✅ Lista de mejoras de calidad / deuda técnica
6. ✅ Reflexión personal sobre el uso de IA en el review

---

## Criterios de Aceptación

El laboratorio se considera completo cuando:

- [ ] Se revisó una PR real o equivalente de forma sistemática
- [ ] Se identificaron al menos **3 bugs** o se justificó razonadamente que no los hay
- [ ] Se identificaron al menos **2 riesgos de seguridad** o se justificó razonadamente que no aplican
- [ ] Se propusieron al menos **3 mejoras de calidad**
- [ ] Se documentó deuda técnica cuando exista
- [ ] Se revisó la suficiencia de los tests
- [ ] El review final está redactado profesionalmente
- [ ] La IA se usó como apoyo, no como sustituto del criterio técnico

---

## Evaluación

### Rúbrica de Evaluación

| Criterio | Excelente (4) | Bueno (3) | Satisfactorio (2) | Necesita Mejora (1) |
|----------|---------------|-----------|-------------------|---------------------|
| **Review Estructural** | Detecta problemas de arquitectura con mucho criterio | Detecta varios problemas relevantes | Detecta problemas básicos | Review superficial |
| **Detección de Bugs** | Identifica bugs reales y bien justificados | Identifica varios bugs relevantes | Detecta algunos problemas básicos | Apenas detecta problemas |
| **Seguridad** | Revisión profunda y específica de AEM/OSGi | Revisión sólida con hallazgos útiles | Revisión básica | Revisión superficial o genérica |
| **Calidad y Deuda Técnica** | Hallazgos muy accionables y priorizados | Buenas observaciones de calidad | Observaciones correctas pero básicas | Comentarios vagos o cosméticos |
| **Documentación del Review** | Documento excelente, claro y profesional | Documento bien estructurado | Documento suficiente | Documento pobre o desordenado |
| **Uso de IA** | Uso estratégico, crítico y bien guiado | Uso bueno con prompts adecuados | Uso básico | Uso pasivo o sin criterio |

**Puntuación mínima para aprobar: 12/24 (50%)**

---

## Tips y Ayuda

### Si te quedas atascado

1. **No ves bugs claros**
   - Recorre el flujo completo: entrada → validación → servicio → repositorio/config → respuesta
   - Busca qué pasa con `null`, listas vacías, configuración ausente y errores de infraestructura
   - Revisa cierres de recursos y diferencias entre author/publish

2. **La IA devuelve observaciones demasiado genéricas**
   - Pega fragmentos concretos de código
   - Pide severidad, impacto y recomendación
   - Obliga a la IA a justificar cada hallazgo con evidencia del código

3. **No sabes si algo es realmente bloqueante**
   - Pregunta: ¿puede romper funcionalidad, seguridad, despliegue o mantenibilidad crítica?
   - Si la respuesta es sí, probablemente sea bloqueante o al menos importante

4. **Te cuesta revisar AEM con criterio**
   - Comprueba capa correcta, configuración OSGi, uso del resolver, logging, tests y runmodes
   - Pregunta siempre si el cambio sería seguro y mantenible en un proyecto real del equipo

---

## Reflexión Final

Al terminar, añade una breve reflexión personal:

- ¿Qué tipo de hallazgos te ayudó más a descubrir la IA?
- ¿Qué parte tuviste que validar tú manualmente?
- ¿Qué prompts fueron más útiles?
- ¿Qué señales te hicieron desconfiar de sugerencias demasiado “convincente pero vagas”?
- ¿Cuánto valor real aportó la IA al proceso de review?

---

## Bonus Opcional

Si acabas antes de tiempo:

1. Convierte tu review en comentarios reales de PR listos para publicar
2. Propón snippets corregidos para los problemas más críticos
3. Clasifica hallazgos en:
   - bloqueantes antes de merge
   - recomendables en esta PR
   - deuda técnica para ticket posterior
4. Compara tu review manual con el review asistido por IA y anota diferencias

---

**Versión**: 1.0
