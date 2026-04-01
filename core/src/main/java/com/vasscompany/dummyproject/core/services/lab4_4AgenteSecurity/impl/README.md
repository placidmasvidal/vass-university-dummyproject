# Lab 4.4: Auditoría de Seguridad con Agente Security

## Objetivo

Realizar una auditoría de seguridad completa sobre una implementación **Java / AEM-like** usando un **Agente Security** especializado, identificando vulnerabilidades, malas prácticas, configuraciones inseguras y riesgos de exposición de datos antes de que el cambio llegue a integración o despliegue.

## Duración

**2 horas**

## Usar

`artefactos-reutilizables/agentes/agente-security.md`

## Entregables

- ✅ Reporte de auditoría con vulnerabilidades encontradas
- ✅ Tabla priorizada por severidad y recomendación
- ✅ Propuesta de correcciones para hallazgos críticos/altos
- ✅ Reflexión breve sobre el uso del Agente Security

---

## Prerequisitos

- ✅ Haber completado los laboratorios previos de prompting, refactor, review y debugging
- ✅ Conocer la estructura del proyecto dummy AEM
- ✅ Entender conceptos básicos de OSGi, Sling, servlets y configuración por runmodes
- ✅ Haber visto buenas prácticas de seguridad para IA y revisión de código

---

## Contexto del Ejercicio

Un compañero de equipo ha implementado una nueva funcionalidad en el proyecto dummy de AEM: un módulo de **solicitud de contacto comercial** expuesto mediante servlet y respaldado por servicios OSGi.

La funcionalidad permite:

- Recibir peticiones desde author o publish
- Validar y persistir datos de solicitud en el repositorio
- Enviar una notificación por email a un buzón interno
- Registrar trazas operativas
- Leer configuración OSGi desde `ui.config`

La feature funciona aparentemente, pero antes de aceptarla en una PR el equipo quiere ejecutar una **auditoría de seguridad asistida por IA**.

Tu trabajo consiste en usar el **Agente Security** para analizar la implementación y localizar riesgos como:

1. Exposición de datos sensibles en logs o respuestas
2. Uso inseguro de `ResourceResolver`
3. Configuración OSGi insegura o con secretos hardcodeados
4. Validación insuficiente de input
5. Riesgos de acceso indebido al JCR
6. Endpoints demasiado abiertos
7. Manejo inseguro de errores
8. Posibles malas prácticas de integración con APIs o email

---

## Escenario AEM-like a Auditar

La PR contiene estos elementos:

1. `ContactRequestServlet.java`
2. `ContactRequestService.java`
3. `ContactRequestServiceImpl.java`
4. `EmailNotificationService.java`
5. `EmailNotificationServiceImpl.java`
6. `ContactRequestModel.java`
7. Configuración OSGi en `ui.config/.../osgiconfig/...`
8. Tests unitarios parciales

### Comportamiento funcional esperado

- El servlet recibe nombre, email, empresa, país y mensaje
- El servicio valida los datos y los persiste bajo una ruta del sitio
- Después dispara notificación por email
- Devuelve JSON con resultado de la operación

### Sospecha del equipo

Hay indicios de que la implementación fue acelerada usando IA y podría contener errores de seguridad típicos:

- secretos o rutas hardcodeadas
- logs con PII
- falta de validación robusta
- service users mal configurados
- uso de APIs AEM desaconsejadas
- respuestas demasiado informativas
- cobertura de tests insuficiente en escenarios de fallo

---

## Objetivo Específico del Lab

Debes actuar como si estuvieras revisando una PR real de AEM y producir una **auditoría accionable**, no una lista superficial de observaciones.

La revisión debe cubrir:

- **Código Java**
- **Diseño AEM/OSGi**
- **Configuración**
- **Logs y errores**
- **Acceso al repositorio**
- **Superficie expuesta del endpoint**
- **Tests de seguridad faltantes**

---

## Instrucciones Paso a Paso

### Paso 1: Preparar el contexto del Agente Security (10 min)

Abre simultáneamente:

- el código Java del módulo
- la configuración OSGi relacionada
- los tests existentes
- `arquitectura.md`
- `patrones.md`
- el archivo del agente `agente-security.md`

Asegúrate de que el agente tenga suficiente contexto sobre:

- estructura por capas del proyecto
- uso de servicios OSGi
- binding de servlets
- buenas prácticas de service users
- configuración por runmodes
- restricciones típicas de seguridad en AEM

### Paso 2: Solicitar una auditoría inicial amplia (20 min)

Lanza una primera auditoría con un prompt de alto nivel.

Ejemplo de enfoque:

```text
Audita esta feature Java/AEM como si fuera una PR real.

Quiero una revisión de seguridad completa sobre:
- servlet
- servicios OSGi
- acceso a JCR
- configuración ui.config
- logs
- manejo de errores
- tests

Busca especialmente:
- secretos hardcodeados
- uso inseguro de ResourceResolver
- exposición de datos sensibles
- endpoints abiertos o mal ligados
- validación insuficiente de entrada
- configuraciones inseguras por defecto
- prácticas AEM desaconsejadas
- ausencia de controles defensivos

Devuélveme:
1. lista de hallazgos
2. severidad
3. explicación del riesgo
4. propuesta de corrección
```

No aceptes una respuesta vaga. Obliga al agente a señalar **ubicación, impacto y remediación**.

### Paso 3: Profundizar por capas (30 min)

Haz una segunda ronda, separando la auditoría por áreas.

#### 3.1 Servlet y superficie expuesta

Pide revisar:

- método HTTP usado
- binding del servlet (path vs resourceType)
- validación de parámetros
- control de content type
- códigos HTTP devueltos
- posible abuso desde publish
- mensajes de error expuestos al cliente

#### 3.2 Servicio OSGi y lógica de negocio

Pide revisar:

- separación de responsabilidades
- validaciones server-side
- sanitización de datos
- manejo de excepciones
- control de nulls y edge cases
- uso correcto de dependencias inyectadas

#### 3.3 Acceso al JCR

Pide revisar:

- si se usa `getServiceResourceResolver()` correctamente
- si existe cierre correcto del resolver
- si se usa subservice en lugar de admin
- si el path de escritura está controlado por configuración
- si hay riesgo de escribir fuera del árbol previsto
- si faltan controles de permisos mínimos

#### 3.4 Configuración OSGi y secretos

Pide revisar:

- si hay emails, credenciales, API keys o rutas sensibles hardcodeadas
- si la configuración por defecto es segura
- si los `cfg.json` exponen información sensible
- si hay runmodes separados para author/publish/dev/prod cuando aplica
- si la configuración es excesivamente permisiva

#### 3.5 Logging y tratamiento de errores

Pide revisar:

- si se registran emails, mensajes o payloads completos
- si se exponen stack traces o detalles internos
- si los logs contienen datos personales innecesarios
- si el error handling oculta fallos críticos o devuelve demasiada información

### Paso 4: Aplicar checklist AEM Security específico (20 min)

Usa esta checklist durante la revisión:

#### Checklist de Seguridad AEM / Java

**Secrets & Config**
- [ ] ¿Hay credenciales, tokens, rutas privadas o emails sensibles hardcodeados?
- [ ] ¿La configuración sensible vive fuera del código?
- [ ] ¿Las configs por defecto son seguras?

**Servlets & Endpoints**
- [ ] ¿El servlet está ligado de forma segura?
- [ ] ¿Se limita correctamente el método HTTP?
- [ ] ¿Se valida el payload antes de procesarlo?
- [ ] ¿La respuesta evita filtrar detalles internos?

**JCR & ResourceResolver**
- [ ] ¿Se usa un subservice específico?
- [ ] ¿Se evita `getAdministrativeResourceResolver()`?
- [ ] ¿Se cierra siempre el resolver?
- [ ] ¿Se aplica mínimo privilegio?

**Input Validation**
- [ ] ¿Se validan campos obligatorios?
- [ ] ¿Se valida formato de email?
- [ ] ¿Se limitan tamaños de campos?
- [ ] ¿Se evita persistir input no saneado?

**Logs & Errors**
- [ ] ¿Los logs evitan PII innecesaria?
- [ ] ¿No se imprimen payloads completos?
- [ ] ¿Las excepciones están tratadas sin filtrar detalles internos?

**Arquitectura & Dependencias**
- [ ] ¿El servicio evita responsabilidades mezcladas?
- [ ] ¿No se usan APIs obsoletas o inseguras?
- [ ] ¿Hay dependencias con uso cuestionable o mal encapsulado?

**Testing**
- [ ] ¿Existen tests negativos de validación?
- [ ] ¿Existen tests de error del resolver / persistencia / email?
- [ ] ¿Existen tests de configuración insegura o faltante?

### Paso 5: Generar el reporte de auditoría (25 min)

Crea un documento final llamado:

`security_audit_lab_4.4.md`

Con esta estructura mínima:

```markdown
# Auditoría de Seguridad - Lab 4.4

## Resumen Ejecutivo
- Estado general: [Aprobable / Aprobable con cambios / No aprobable]
- Hallazgos críticos: [n]
- Hallazgos altos: [n]
- Hallazgos medios: [n]
- Hallazgos bajos: [n]

## Alcance Revisado
- Servlet
- Servicios OSGi
- Configuración ui.config
- Acceso JCR
- Logs
- Tests

## Hallazgos
| ID | Severidad | Archivo | Problema | Riesgo | Recomendación |
|----|-----------|---------|----------|--------|---------------|

## Hallazgos Críticos y Altos
[detalle explicado uno a uno]

## Mejoras Recomendadas
[lista priorizada]

## Tests de Seguridad Faltantes
[casos de test propuestos]

## Recomendación Final
[con justificación]
```

### Paso 6: Pedir propuestas de corrección (10 min)

Para cada hallazgo crítico o alto, pide al agente una propuesta de remediación concreta.

Ejemplos:

```text
Propón una versión corregida del servlet evitando exponer detalles internos y validando correctamente los parámetros.
```

```text
Refactoriza este acceso al repositorio para usar subservice, try-with-resources y mínimo privilegio.
```

```text
Sugiere cómo mover esta configuración sensible desde código a OSGi config segura.
```

### Paso 7: Reflexión final (5 min)

Crea un archivo:

`reflexion_lab_4.4.md`

Incluye:

- qué hallazgos detectó bien el agente
- cuáles tuviste que descubrir tú manualmente
- qué falsos positivos produjo
- qué prompts dieron mejores resultados
- qué checklist te resultó más útil
- qué cambiarías en una PR real tras este ejercicio

---

## Criterios de Aceptación

El laboratorio se considera completo cuando:

- [ ] Se revisó la feature completa, no solo un archivo aislado
- [ ] Se identificaron al menos 2 problemas de seguridad relevantes
- [ ] Cada hallazgo incluye severidad, riesgo y recomendación
- [ ] Se generó un reporte estructurado y profesional
- [ ] Se propusieron correcciones para hallazgos críticos o altos
- [ ] Se identificaron tests de seguridad faltantes
- [ ] Se usó el Agente Security de forma iterativa, no con un único prompt superficial
- [ ] Se documentó una reflexión final útil

---

## Evaluación

### Rúbrica de Evaluación

| Criterio | Excelente (4) | Bueno (3) | Satisfactorio (2) | Necesita Mejora (1) |
|----------|---------------|-----------|-------------------|---------------------|
| **Profundidad de Auditoría** | Auditoría muy completa, multi-capa y contextual | Auditoría buena con cobertura amplia | Auditoría correcta pero parcial | Auditoría superficial |
| **Calidad de Hallazgos** | Hallazgos relevantes, bien priorizados y accionables | Hallazgos útiles con buena justificación | Hallazgos básicos | Hallazgos poco útiles o vagos |
| **Enfoque AEM/OSGi** | Excelente adaptación a riesgos reales de AEM | Buena adaptación al contexto AEM | Adaptación parcial | Enfoque demasiado genérico |
| **Uso del Agente** | Uso iterativo, estratégico y muy bien guiado | Buen uso con refinamientos | Uso básico | Uso pasivo o pobre |
| **Documentación Final** | Reporte profesional, claro y reutilizable | Reporte claro y ordenado | Reporte funcional | Reporte incompleto o desordenado |

**Puntuación mínima para aprobar: 12/20 (60%)**

---

## Pistas de Hallazgos que Deberías Ser Capaz de Detectar

Según cómo esté implementada la feature, deberías estar atento al menos a este tipo de problemas:

1. ❌ Secrets o emails internos hardcodeados en código o config
2. ❌ Uso incorrecto o privilegiado del `ResourceResolver`
3. ❌ Logs con email, mensaje libre o payload completo
4. ❌ Validación insuficiente de input antes de persistir
5. ❌ Respuestas JSON con detalles internos o excepciones
6. ❌ Servlet accesible de manera demasiado amplia
7. ❌ Escritura en JCR sin path controlado por config
8. ❌ Tests insuficientes para fallos de seguridad
9. ❌ Mezcla de responsabilidades que dificulta proteger el flujo
10. ❌ Configuración por defecto válida en dev pero insegura para publish/prod

---

## Tips y Ayuda

### Si te quedas atascado

1. **El agente devuelve observaciones muy genéricas**
    - Reduce el scope y audita por capas
    - Pide siempre severidad, riesgo y corrección
    - Obliga a citar el archivo o fragmento afectado

2. **No encuentras problemas claramente AEM**
    - Revisa uso de `ResourceResolverFactory`
    - Revisa service users y runmodes
    - Revisa binding de servlets y paths
    - Revisa si la lógica debería vivir en servicio y no en servlet

3. **Te salen demasiados falsos positivos**
    - Pide al agente separar hallazgos confirmados de sospechas
    - Contrasta manualmente cada hallazgo con el código
    - No aceptes recomendaciones sin verificar su encaje en AEM

4. **No sabes cómo proponer la corrección**
    - Pide refactor puntual del fragmento afectado
    - Solicita una versión corregida con buenas prácticas OSGi
    - Pide además el test unitario o de validación que faltaría

---

## Recursos Recomendados

- `arquitectura.md`
- `patrones.md`
- `artefactos-reutilizables/agentes/agente-security.md`
- checklist interna de seguridad IA del curso
- notas de labs previos de code review y debugging

---

## Resultado Esperado

Al terminar este laboratorio, deberías ser capaz de usar un agente especializado no solo para “buscar vulnerabilidades”, sino para hacer una **auditoría de seguridad útil en un proyecto AEM realista**, con foco en configuración, repositorio, exposición de endpoints, logs, errores y controles defensivos.

---

**Versión**: 1.0
