# Lab 4.5: Documentación Completa con Agente Docs (Java / AEM)

## Objetivo

Generar documentación completa y útil para una feature **Java / AEM-like** usando un **Agente Docs** especializado, cubriendo tanto la visión de API/consumo como la documentación técnica, operativa y funcional del módulo.

En este laboratorio no se trata solo de “escribir un README bonito”, sino de aprender a usar IA para producir documentación **consistente con el código**, **alineada con la arquitectura del proyecto** y **útil para varios perfiles**: desarrolladores, integradores, QA y mantenimiento.

---

## Duración Estimada

**2 horas**

---

## Prerequisitos

- ✅ Haber completado o entendido los laboratorios de la lección 4
- ✅ GitHub Copilot / ChatGPT / herramienta equivalente disponible
- ✅ Conocer la estructura básica del proyecto dummy AEM
- ✅ Tener localizados los archivos de contexto del proyecto (`arquitectura.md`, `patrones.md`, `pom.xml`)

---

## Contexto del Ejercicio

En el proyecto dummy de VASS University, la lógica Java vive en el módulo `core`, los endpoints HTTP suelen implementarse como **Servlets Sling**, la lógica reutilizable se concentra en **servicios OSGi**, y la configuración se externaliza en `ui.config`. Además, el proyecto dispone de tests unitarios, tests de integración e incluso tests funcionales UI en módulos separados.

En este laboratorio vas a documentar una feature AEM realista ya implementada o parcialmente implementada, por ejemplo un flujo como:

- un **Servlet** expuesto en `/bin/...`
- que valida la petición
- delega en un **servicio OSGi**
- usa DTOs / modelos de request y response
- puede depender de configuración OSGi
- devuelve JSON para integraciones internas o front-end

La meta es que el resultado no sea una documentación genérica, sino una documentación que un compañero de equipo pueda usar para:

1. entender qué hace la feature,
2. integrarse contra ella,
3. probarla,
4. operarla en AEM,
5. mantenerla y evolucionarla.

---

## Qué vas a documentar

Debes escoger una feature **Java / AEM-like** del proyecto dummy. Puede ser una real si ya existe, o una feature de laboratorio creada en sesiones anteriores.

### Opción recomendada

Documenta una feature con esta estructura:

- `core/src/main/java/com/vasscompany/dummyproject/core/servlets/.../CatalogSyncServlet.java`
- `core/src/main/java/com/vasscompany/dummyproject/core/services/.../CatalogSyncService.java`
- `core/src/main/java/com/vasscompany/dummyproject/core/services/.../impl/CatalogSyncServiceImpl.java`
- `core/src/test/java/.../CatalogSyncServiceImplTest.java`
- `ui.config/src/main/content/jcr_root/apps/.../osgiconfig/...cfg.json`

### Comportamiento funcional sugerido

Un endpoint tipo `POST /bin/dummyproject/catalog/sync` que:

- recibe un `siteId`, `language` y `force`
- valida los parámetros
- sincroniza o recalcula contenido derivado
- devuelve un JSON con:
    - `success`
    - `message`
    - `processedItems`
    - `warnings`
    - `executionTimeMs`

> Puedes adaptar el dominio (catálogo, favoritos, inventario, notificaciones, precios, publicación, validación de contenido, etc.) siempre que el caso siga siendo verosímil en AEM.

---

## Objetivo concreto del laboratorio

Usar un **Agente Docs** para producir, a partir del código y del contexto de arquitectura, un conjunto de documentos sincronizados entre sí:

1. **README funcional/técnico del módulo**
2. **Especificación OpenAPI** del endpoint o endpoints
3. **Guía de integración** para consumidores del endpoint
4. **Guía operativa** para mantenimiento y despliegue
5. **Notas de testing** para QA / validación manual

---

## Estructura de archivos sugerida

Crea o actualiza estos artefactos en tu laboratorio:

```text
core/src/main/java/com/vasscompany/dummyproject/core/services/lab4_5AgenteDocs/
├── CatalogSyncService.java
├── impl/
│   ├── CatalogSyncServiceImpl.java
│   └── README.md
├── api/
│   └── catalog-sync-openapi.yaml
└── docs/
    ├── guia-integracion-catalog-sync.md
    ├── guia-operacion-catalog-sync.md
    └── notas-testing-catalog-sync.md
```

> Para este lab, el entregable mínimo obligatorio en este paquete es el `README.md`, pero el ejercicio está pensado para que practiques generando el resto de documentos también.

---

## Instrucciones

### Paso 1: Reunir contexto antes de pedir documentación (15 min)

Abre simultáneamente:

- el servicio OSGi
- el servlet relacionado
- cualquier DTO / bean / modelo asociado
- el test unitario o de integración existente
- `arquitectura.md`
- `patrones.md`
- `pom.xml`

### Qué debe entender la IA antes de documentar

Haz que la IA tenga claro:

- en qué módulo está el código (`core`, `ui.config`, `it.tests`, etc.)
- qué capa representa cada clase
- qué endpoint existe y cómo se invoca
- qué configuración OSGi necesita
- qué valida el servicio
- qué devuelve en caso de éxito y error
- qué tests existen y qué garantías dan

### Prompt base sugerido para el Agente Docs

```text
Quiero que actúes como un Agente Docs especializado en Java + AEM.

Contexto del proyecto:
- Proyecto AEM multi-módulo con módulos core, ui.apps, ui.config, it.tests y ui.tests.
- Los servicios siguen patrón OSGi con interfaces en core/services e implementaciones en core/services/impl.
- Los endpoints HTTP se implementan como Sling Servlets.
- La configuración se externaliza en ui.config.
- Los tests unitarios usan JUnit 5 y AEM Mocks.

Tu tarea es generar documentación técnica y funcional completa de esta feature.
La documentación debe ser útil para desarrolladores, integradores, QA y mantenimiento.
No inventes comportamiento no respaldado por el código. Si falta información, marca claramente los supuestos.

Artefactos a generar:
1. README técnico-funcional
2. OpenAPI YAML del endpoint
3. Guía de integración
4. Guía operativa
5. Notas de testing
```

---

### Paso 2: Generar primero el README principal (25 min)

Empieza por el archivo principal:

- `core/src/main/java/com/vasscompany/dummyproject/core/services/lab4_5AgenteDocs/impl/README.md`

Este README debe servir como **documento madre** de la feature.

### Contenido mínimo esperado del README

```markdown
# [Nombre de la feature]

## Objetivo
## Alcance
## Arquitectura de la solución
## Flujo de ejecución
## Endpoint(s) disponibles
## Contrato de entrada y salida
## Configuración OSGi necesaria
## Dependencias técnicas
## Estrategia de testing
## Riesgos / limitaciones conocidas
## Ejemplos de uso
## Troubleshooting
```

### Buenas prácticas del README

- Debe distinguir claramente entre:
    - comportamiento funcional,
    - detalles técnicos,
    - configuración,
    - operación.
- Debe incluir ejemplos reales de request/response.
- Debe documentar qué hace el servlet y qué hace el servicio.
- Debe explicar los supuestos, restricciones y errores esperables.
- Debe ser mantenible: otra persona debe poder actualizarlo cuando cambie el código.

---

### Paso 3: Generar la especificación OpenAPI (25 min)

A partir del servlet y del contrato JSON, genera:

- `catalog-sync-openapi.yaml`

### Debe incluir como mínimo

- `openapi: 3.0.x`
- `info`
- `servers` (aunque sea con valores de ejemplo)
- `paths`
- `requestBody`
- `responses`
- `schemas`
- ejemplos de payload
- códigos HTTP documentados

### Ejemplo de prompt para OpenAPI

```text
Genera una especificación OpenAPI 3.0 para este Sling Servlet de AEM.
Documenta el endpoint como una API interna del proyecto.
Incluye:
- request JSON
- response JSON de éxito
- errores 400, 401/403 si aplica, 500
- ejemplos realistas
- nombres de campos exactamente coherentes con el código
```

### Estructura orientativa

```yaml
openapi: 3.0.3
info:
  title: Catalog Sync API
  version: 1.0.0
paths:
  /bin/dummyproject/catalog/sync:
    post:
      summary: Sincroniza catálogo derivado
      requestBody:
        required: true
      responses:
        '200':
          description: Sincronización completada
        '400':
          description: Petición inválida
        '500':
          description: Error interno
```

---

### Paso 4: Generar guía de integración (20 min)

Crea una guía pensada para quien va a consumir el endpoint desde:

- otro backend,
- un servlet AEM,
- una aplicación front,
- Postman / cURL,
- tests de integración.

### Debe incluir

- propósito del endpoint
- cuándo usarlo y cuándo no
- autenticación o restricciones si las hay
- payload mínimo válido
- ejemplos de petición
- ejemplos de respuestas correctas y erróneas
- consideraciones de idempotencia, reintentos y timeouts
- convenciones de logging/correlación si existen

### Ejemplo de snippets útiles

```bash
curl -X POST 'http://localhost:4502/bin/dummyproject/catalog/sync' \
  -H 'Content-Type: application/json' \
  -u admin:admin \
  --data '{
    "siteId": "es",
    "language": "es",
    "force": false
  }'
```

```json
{
  "success": true,
  "message": "Catalog sync finished successfully",
  "processedItems": 42,
  "warnings": [],
  "executionTimeMs": 183
}
```

---

### Paso 5: Generar guía operativa (20 min)

Ahora documenta lo que necesita alguien de mantenimiento o despliegue.

### Debe cubrir

- bundle / módulo implicado
- configuración OSGi requerida y dónde se despliega
- cómo validar que el componente está activo en Felix Console
- cómo probar el endpoint localmente
- qué logs revisar
- qué problemas típicos pueden aparecer
- cómo distinguir fallo funcional de fallo de configuración

### Checklist operativo sugerido

- [ ] El bundle `core` está activo
- [ ] El componente OSGi está satisfecho
- [ ] La config OSGi requerida existe en `ui.config`
- [ ] Los permisos / service user son correctos si aplica
- [ ] El endpoint responde en author
- [ ] Los tests unitarios e integración pasan

---

### Paso 6: Generar notas de testing (15 min)

Genera una guía breve para QA o para validación manual y técnica.

### Debe incluir

- casos felices
- validaciones de error
- datos inválidos
- parámetros ausentes
- comportamiento con configuración incompleta
- qué parte cubren los tests unitarios y cuál requiere test en AEM real

### Cobertura de testing esperada en AEM

- **Unit tests (`core`)**:
    - validación de lógica de negocio
    - mocks de dependencias
    - serialización / DTOs si aplica
- **Integration tests (`it.tests`)**:
    - servlet desplegado en instancia real
    - contratos HTTP básicos
    - configuración efectiva
- **UI tests (`ui.tests`)**:
    - solo si la feature tiene impacto de navegación o interfaz

---

### Paso 7: Validar la consistencia de toda la documentación (15 min)

Antes de dar el lab por cerrado, revisa con IA o manualmente que:

- el README y el OpenAPI no se contradicen
- los nombres de campos JSON coinciden con el código
- los códigos HTTP documentados son reales
- la configuración OSGi documentada existe o, si no existe, está marcada como pendiente
- los ejemplos de cURL son ejecutables
- la guía operativa no presupone cosas inexistentes
- no se han inventado clases, métodos o paths

### Prompt de validación final

```text
Revisa toda esta documentación como si fueras un tech lead de AEM.
Busca inconsistencias entre README, OpenAPI, guía de integración y guía operativa.
Marca:
- contradicciones
- campos no respaldados por código
- configuraciones inventadas
- ejemplos incorrectos
- riesgos de mantenimiento
```

---

## Criterios de Aceptación

El laboratorio se considera completo cuando:

- [ ] Existe un `README.md` técnico-funcional claro y mantenible
- [ ] Existe una especificación OpenAPI coherente con el endpoint
- [ ] Existe guía de integración con ejemplos de uso
- [ ] Existe guía operativa orientada a mantenimiento AEM
- [ ] Existen notas de testing con casos funcionales y técnicos
- [ ] La documentación es consistente entre sí
- [ ] La documentación distingue claramente hechos vs. supuestos
- [ ] La IA se usó como asistente, no como generador ciego

---

## Entregables

Al finalizar el lab, este paquete debería contener como mínimo:

- ✅ `README.md` - documento principal técnico-funcional
- ✅ `catalog-sync-openapi.yaml` - contrato OpenAPI del endpoint
- ✅ `guia-integracion-catalog-sync.md` - guía para consumidores
- ✅ `guia-operacion-catalog-sync.md` - guía para mantenimiento/operación
- ✅ `notas-testing-catalog-sync.md` - guía de validación funcional/técnica
- ✅ `reflexion_lab_4.5.md` - reflexión sobre el uso del Agente Docs

---

## Reflexión final

Crea el archivo:

- `reflexion_lab_4.5.md`

Y responde como mínimo a estas preguntas:

1. ¿Qué parte documentó mejor la IA: README, OpenAPI, guía de integración u operación?
2. ¿En qué puntos inventó demasiado y tuviste que corregir?
3. ¿Qué contexto adicional mejoró más el resultado: arquitectura, patrones, tests o código del servlet?
4. ¿Qué errores de documentación detectaste al contrastar con el código?
5. ¿Cuánto tiempo ahorraste frente a documentarlo todo manualmente?
6. ¿Qué harías distinto al usar un Agente Docs en un proyecto AEM real?

---

## Checklist específico AEM / Java para este laboratorio

### Arquitectura y capas
- [ ] ¿Queda claro qué hace el servlet y qué hace el servicio OSGi?
- [ ] ¿La documentación refleja la separación por capas del proyecto?
- [ ] ¿Se indican los módulos Maven implicados?

### OSGi
- [ ] ¿Se documenta la interfaz del servicio?
- [ ] ¿Se documenta la implementación y su `@Component`?
- [ ] ¿Se documentan configuraciones OSGi relevantes?

### API / Servlet
- [ ] ¿Se documenta el path del servlet correctamente?
- [ ] ¿Se explican parámetros de entrada y respuestas?
- [ ] ¿Se documentan los códigos HTTP relevantes?

### Seguridad
- [ ] ¿Se evitan credenciales reales en ejemplos?
- [ ] ¿Se indican restricciones de acceso si existen?
- [ ] ¿Se evita documentar como válido el uso de admin en producción?
- [ ] ¿Se explican los riesgos de logs con datos sensibles?

### Testing
- [ ] ¿Se diferencia entre unit tests, integration tests y UI tests?
- [ ] ¿Se explican los casos edge importantes?
- [ ] ¿La documentación puede servir para pruebas manuales y automáticas?

---

## Evaluación

### Rúbrica de Evaluación

| Criterio | Excelente (4) | Bueno (3) | Satisfactorio (2) | Necesita mejora (1) |
|----------|---------------|-----------|-------------------|---------------------|
| **Calidad del README** | Muy claro, completo, técnico y funcional | Claro y útil con pocos huecos | Correcto pero superficial | Incompleto o confuso |
| **Calidad del OpenAPI** | Preciso, ejecutable y coherente con el código | Bueno con pequeños ajustes pendientes | Parcialmente correcto | Inconsistente o poco útil |
| **Utilidad operativa** | Muy útil para soporte y mantenimiento | Útil con pequeñas carencias | Básica | Poco accionable |
| **Consistencia documental** | Todos los artefactos están alineados | Casi todo alineado | Algunas contradicciones | Muchas contradicciones |
| **Uso consciente de IA** | Contexto excelente, iteración y validación rigurosa | Buen uso con revisión adecuada | Uso básico con poca validación | Uso pasivo, casi sin revisión |

**Puntuación mínima para aprobar: 12/20**

---

## Consejos prácticos

### Si la IA documenta “demasiado bonito” pero poco real

Pídele que:

- no invente comportamiento no visto en el código,
- marque supuestos explícitamente,
- cite clases y métodos concretos,
- separe “hechos observados” de “recomendaciones”.

### Si el OpenAPI sale demasiado genérico

Dale a la IA:

- el método `doGet` / `doPost`,
- los DTOs,
- ejemplos reales de JSON,
- nombres exactos de campos,
- y errores manejados por el código.

### Si la guía operativa sale pobre

Añade contexto de:

- nombres de bundle,
- configuración OSGi,
- comandos Maven del proyecto,
- rutas típicas de logs,
- y pasos de validación en AEM Author.

---

## Bonus opcional

Si terminas antes, añade uno o varios de estos extras:

1. **Diagrama Mermaid** del flujo servlet → servicio → repositorio / API externa
2. **Tabla de errores** con causa probable y acción correctiva
3. **Colección Postman** de ejemplo
4. **ADR breve** explicando por qué ese endpoint existe y cómo debe evolucionar
5. **Sección de observabilidad** con logs, métricas y trazabilidad recomendadas

---

## Resultado esperado

Al terminar este laboratorio deberías ser capaz de usar IA no solo para escribir código, sino para producir **documentación técnica de calidad profesional**, alineada con un proyecto **Java/AEM**, mantenible y realmente útil para el equipo.

---

**Versión**: 1.0
