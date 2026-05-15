# GitHub Copilot Context

## Repository profile
This repository is an Adobe Experience Manager (AEM) 6.5 multi-module Maven project based on the AEM Project Archetype. Use AEM, Sling, OSGi Declarative Services, HTL, JCR, and Core Components conventions in all suggestions.

## Module map
Main modules and responsibilities:
- `all/`: deployment package aggregator
- `core/`: Java code, OSGi services, Sling Models, servlets, filters, listeners, schedulers
- `ui.apps/`: components, HTL, dialogs, clientlibs, templates, i18n
- `ui.apps.structure/`: base repository structure under `/apps` and `/conf`
- `ui.config/`: OSGi configuration by runmode
- `ui.content/`: initial or sample content
- `it.tests/`: Java integration tests
- `ui.tests/`: Cypress UI tests

## Architectural rules
- Respect Maven module boundaries and AEM layering
- Keep business logic in OSGi services under `core`
- Keep HTL focused on presentation
- Keep Sling Models thin and component-oriented
- Use configuration instead of hardcoded environment values
- Reuse Core Components through proxies when possible
- Prefer minimal, production-ready changes over broad rewrites

## Java and AEM conventions
- Prefer Sling Models for component backing logic
- Prefer OSGi services for reusable business logic
- Prefer `@SlingServletResourceTypes` over path-based servlets
- Use OSGi DS annotations such as `@Component`, `@Reference`, `@Activate`, `@Modified`, and `@Deactivate`
- Use `@Designate` and typed OSGi config interfaces for configurable services
- Use `@OSGiService`, `@SlingObject`, and `@ValueMapValue` where appropriate
- If repository write or privileged access is needed, use service users and least privilege
- Always close `ResourceResolver` instances obtained from factories
- Use try-with-resources when applicable

## Packaging conventions
Typical package layout under `core/src/main/java/...`:
- `models/`
- `services/`
- `services/impl/`
- `servlets/`
- `filters/`
- `listeners/`
- `schedulers/`

Place new classes in the correct package and keep names explicit and conventional.

## HTL and component guidance
- Keep logic out of HTL when it becomes non-trivial
- Expose data to HTL through Sling Models
- Keep dialogs, templates, policies, and clientlibs aligned with AEM conventions
- Prefer maintainable component structure over inline shortcuts

## Patterns to prefer
- Layered architecture
- Dependency injection
- Sling Model pattern
- Adapter pattern via `adaptTo(...)`
- OSGi service abstraction
- Resource type servlet binding
- Scheduler configuration through OSGi
- Structured JSON error responses for APIs

## Anti-patterns to avoid
Do not generate code that introduces these problems:
- God components or oversized Sling Models
- Manual service lookup instead of dependency injection
- Hardcoded content paths, hosts, usernames, or passwords
- Admin sessions or unnecessarily privileged access
- Business logic embedded in HTL
- Path-based servlets when resource-type binding is viable
- Unclosed `ResourceResolver` instances
- Mixing rendering, business logic, and integration logic in the same class

## Testing expectations
- Use JUnit 5 and Mockito for unit tests
- Keep tests focused and close to class responsibility
- Assume Cypress for end-to-end UI tests in `ui.tests/`
- Preserve existing test style where possible

## Build conventions
Common commands in this repository:
- `mvn clean install`
- `mvn clean install -PautoInstallSinglePackage`
- `mvn clean install -PautoInstallSinglePackagePublish`
- `mvn clean install -PautoInstallBundle`
- `mvn clean verify -Pit`

Assume local defaults unless code indicates otherwise:
- Author: `localhost:4502`
- Publish: `localhost:4503`

## Code generation behavior
When proposing changes:
1. Mention the target module and probable path.
2. Match existing AEM and Java conventions.
3. Include imports for Java snippets.
4. Prefer concrete, compilable examples.
5. Call out required OSGi configuration when relevant.
6. Call out service user mapping when relevant.
7. Warn when a suggestion breaks AEM best practices.

## Commit and branch conventions
Follow repository conventions when suggesting commits:
- Conventional commits: `<type>(<scope>): <description>`
- Types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`, `perf`
- Use short imperative descriptions
- Branch naming: `<type>/<issue-number>-<short-description>`

## Preferred assistant style
- Be precise and implementation-oriented
- Prefer AEM-specific guidance over generic Java advice
- Explain where each file should live
- Note trade-offs only when they matter

---

## Extended architecture and conventions context

The following sections are synchronized from repository context files so Copilot can read additional project-specific details.

### From .copilot-context/arquitectura.md


## 1. Información General

### 1.1 Identificación del Proyecto
- **Nombre**: VASS.University.dummyproject
- **GroupId**: `com.vasscompany`
- **ArtifactId**: `vass-university-dummyproject`
- **Versión**: 1.0-SNAPSHOT
- **Arquetipo AEM**: `aem-project-archetype:50`
- **Versión AEM**: 6.5.8-1.0
- **Core Components**: 2.25.4

### 1.2 Origen del Proyecto
Este proyecto fue generado utilizando el siguiente comando Maven:

```bash
mvn -B org.apache.maven.plugins:maven-archetype-plugin:3.2.1:generate \
  "-D archetypeGroupId=com.adobe.aem" \
  "-D archetypeArtifactId=aem-project-archetype" \
  "-D archetypeVersion=50" \
  "-D appTitle=ICEX.Scholarships" \
  "-D appId=icex-scholarships" \
  "-D artifactId=icex-scholarships" \
  "-D groupId=com.icex.scholarships" \
  "-D package=com.icex.scholarships" \
  "-D version=1.0-SNAPSHOT" \
  "-D aemVersion=6.5.8-1.0" \
  "-D includeDispatcherConfig=n" \
  "-D frontendModule=none" \
  "-D includeExamples=n" \
  "-D includeErrorHandler=n" \
  "-D includeCommerce=n"
```

**Configuraciones del arquetipo aplicadas:**
- ✅ Versión AEM 6.5.8
- ❌ Sin configuración de Dispatcher
- ❌ Sin módulo frontend dedicado (React/Angular/Webpack)
- ❌ Sin ejemplos incluidos
- ❌ Sin manejo de errores personalizado
- ❌ Sin módulo de comercio electrónico

---

## 2. Arquitectura de Módulos Maven

El proyecto sigue una **arquitectura multi-módulo Maven** estándar para AEM. Cada módulo tiene responsabilidades claramente definidas:

```
vass-university-dummyproject (reactor POM)
│
├── all/                     # Paquete agregador de despliegue
├── core/                    # Lógica Java (OSGi bundles)
├── ui.apps/                 # Componentes, templates, clientlibs
├── ui.apps.structure/       # Estructura del repositorio
├── ui.config/               # Configuraciones OSGi específicas
├── ui.content/              # Contenido de ejemplo/inicial
├── it.tests/                # Tests de integración (Java)
└── ui.tests/                # Tests funcionales UI (Cypress)
```

---

## 3. Descripción Detallada de Módulos

### 3.1 **core** - Bundle Java OSGi
**Artifact**: `vass-university-dummyproject.core`  
**Packaging**: `bundle` (OSGi)  
**Responsabilidad**: Contiene toda la lógica de negocio Java del proyecto.

#### Estructura de paquetes:
```
com.vasscompany.dummyproject.core/
├── filters/          # Filtros de Sling (request/response processing)
├── listeners/        # Event listeners (JCR, OSGi, Workflow)
├── models/           # Sling Models (lógica de componentes)
├── schedulers/       # Tareas programadas (cron jobs)
├── services/         # Servicios OSGi reutilizables
└── servlets/         # Servlets HTTP (endpoints)
```

#### Tecnologías clave:
- **OSGi Declarative Services** (DS): Inyección de dependencias y servicios
- **Sling Models**: Vinculación de lógica Java con componentes AEM
- **Apache Sling Servlets**: Endpoints REST/HTTP personalizados
- **BND Maven Plugin**: Generación de manifiestos OSGi
- **JUnit 5 + Mockito**: Testing unitario

#### Dependencias principales:
- `org.osgi.*` - Framework OSGi
- `javax.jcr.*` - Java Content Repository API
- `org.apache.sling.*` - Apache Sling APIs
- `com.adobe.aem.*` - AEM SDK APIs

---

### 3.2 **ui.apps** - Aplicaciones y Componentes
**Artifact**: `vass-university-dummyproject.ui.apps`  
**Packaging**: `content-package`  
**Ruta JCR**: `/apps/vass-university-dummyproject`

#### Contenido típico:
```
/apps/vass-university-dummyproject/
├── components/               # Componentes Sling/HTL
│   ├── page/                # Componentes de página
│   ├── content/             # Componentes de contenido
│   └── structure/           # Componentes estructurales
├── clientlibs/              # Client Libraries (CSS/JS)
│   ├── clientlib-base/      # Estilos y scripts base
│   └── clientlib-site/      # Estilos y scripts del sitio
├── templates/               # Plantillas editables
└── i18n/                    # Diccionarios de internacionalización
```

#### Características:
- **HTL (HTML Template Language)**: Motor de plantillas para componentes
- **Client Libraries**: Gestión optimizada de CSS/JS
- **Editable Templates**: Sistema de plantillas moderno de AEM
- **Component Policies**: Configuración de componentes

---

### 3.3 **ui.apps.structure** - Estructura del Repositorio
**Artifact**: `vass-university-dummyproject.ui.apps.structure`  
**Packaging**: `content-package`  
**Responsabilidad**: Define la estructura base del repositorio JCR.

#### Contenido:
```
/apps/vass-university-dummyproject/         # Contenedor de la aplicación
/conf/vass-university-dummyproject/         # Configuraciones específicas del sitio
/conf/global/                               # Configuraciones globales
```

**Propósito**: Asegurar que las rutas necesarias existan antes de desplegar otros paquetes.

---

### 3.4 **ui.config** - Configuraciones OSGi
**Artifact**: `vass-university-dummyproject.ui.config`  
**Packaging**: `content-package`  
**Ruta JCR**: `/apps/vass-university-dummyproject/osgiconfig`

#### Estructura por runmode:
```
/apps/vass-university-dummyproject/osgiconfig/
├── config/                  # Configuraciones comunes (todos los runmodes)
├── config.author/           # Específicas de Author
├── config.publish/          # Específicas de Publish
├── config.dev/              # Entorno de desarrollo
├── config.stage/            # Entorno de staging
└── config.prod/             # Entorno de producción
```

#### Ejemplos de configuraciones:
- Configuraciones de servicios OSGi personalizados
- Configuraciones de Sling Resource Resolver Factory
- Configuraciones de seguridad y permisos
- Configuraciones de replicación

---

### 3.5 **ui.content** - Contenido de Ejemplo
**Artifact**: `vass-university-dummyproject.ui.content`  
**Packaging**: `content-package`  
**Ruta JCR**: `/content/vass-university-dummyproject`

#### Contenido típico:
- Páginas de ejemplo y estructura de sitio inicial
- Assets de demostración
- Experience Fragments
- Content Fragments (si se usan)

**Nota**: En producción, este módulo puede omitirse o contener solo contenido esencial inicial.

---

### 3.6 **all** - Paquete Agregador de Despliegue
**Artifact**: `vass-university-dummyproject.all`  
**Packaging**: `content-package`  
**Responsabilidad**: Empaqueta TODOS los módulos del proyecto en un único archivo ZIP desplegable.

#### Empaqueta:
```
all.zip
├── core.jar                           # Bundle OSGi
├── ui.apps.zip                        # Paquete de aplicaciones
├── ui.apps.structure.zip              # Estructura base
├── ui.config.zip                      # Configuraciones OSGi
├── ui.content.zip                     # Contenido inicial
└── core.wcm.components.all.zip        # Core Components (dependencia)
```

**Uso principal**: Despliegue en AEM mediante Package Manager o CI/CD.

---

### 3.7 **it.tests** - Tests de Integración Java
**Artifact**: `vass-university-dummyproject.it.tests`  
**Packaging**: `jar`  
**Tecnología**: JUnit 5 + HTTP Client

#### Propósito:
- Validar servicios OSGi en instancia AEM real
- Probar servlets y endpoints HTTP
- Verificar configuraciones y comportamientos del sistema

#### Ejecución:
```bash
mvn clean verify -Pit
```

**Requisitos**: Instancia AEM corriendo en `localhost:4502`.

---

### 3.8 **ui.tests** - Tests Funcionales de UI
**Artifact**: `vass-university-dummyproject.ui.tests`  
**Tecnología**: Cypress (JavaScript)

#### Propósito:
- Tests end-to-end del sitio web
- Validación de componentes en el navegador
- Regression testing de funcionalidades clave

#### Estructura:
```
ui.tests/
├── test-module/
│   ├── cypress/
│   │   ├── e2e/              # Test specs
│   │   ├── fixtures/         # Datos de prueba
│   │   └── support/          # Utilidades y comandos custom
│   ├── cypress.config.js
│   └── package.json
└── Dockerfile                # Contenedor para CI/CD
```

#### Ejecución:
```bash
cd ui.tests/test-module
npm install
npm test
```

---

## 4. Flujo de Compilación y Despliegue

### 4.1 Build Completo
```bash
mvn clean install
```

**Resultado**: 
- Compila todos los módulos
- Ejecuta tests unitarios
- Genera paquetes en `target/` de cada módulo

### 4.2 Build + Deploy a Author
```bash
mvn clean install -PautoInstallSinglePackage
```

**Proceso**:
1. Compila todos los módulos
2. Empaqueta `all.zip`
3. Despliega a `http://localhost:4502` vía HTTP
4. Instala el paquete automáticamente

### 4.3 Build + Deploy a Publish
```bash
mvn clean install -PautoInstallSinglePackagePublish
```

**Target**: `http://localhost:4503`

### 4.4 Deploy Solo del Bundle (Core)
```bash
mvn clean install -PautoInstallBundle
```

**Ventaja**: Rápido para desarrollo iterativo de servicios Java.

### 4.5 Deploy de un Solo Paquete
```bash
cd ui.apps
mvn clean install -PautoInstallPackage
```

---

## 5. Arquitectura de Ejecución (Runtime)

### 5.1 Diagrama de Componentes

```
┌─────────────────────────────────────────────────────────┐
│                    AEM Author Instance                  │
│  ┌───────────────────────────────────────────────────┐  │
│  │          Apache Felix OSGi Container              │  │
│  │  ┌────────────────────────────────────────────┐   │  │
│  │  │   vass-university-dummyproject.core.jar    │   │  │
│  │  │  ┌──────────┐  ┌──────────┐  ┌──────────┐ │   │  │
│  │  │  │ Services │  │ Servlets │  │  Models  │ │   │  │
│  │  │  └──────────┘  └──────────┘  └──────────┘ │   │  │
│  │  └────────────────────────────────────────────┘   │  │
│  │  ┌────────────────────────────────────────────┐   │  │
│  │  │   Adobe AEM Core Components Bundle         │   │  │
│  │  └────────────────────────────────────────────┘   │  │
│  └───────────────────────────────────────────────────┘  │
│  ┌───────────────────────────────────────────────────┐  │
│  │          JCR (Java Content Repository)            │  │
│  │  /apps/vass-university-dummyproject/              │  │
│  │  ├── components/                                  │  │
│  │  ├── clientlibs/                                  │  │
│  │  └── templates/                                   │  │
│  │  /conf/vass-university-dummyproject/              │  │
│  │  /content/vass-university-dummyproject/           │  │
│  └───────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────┘
```

### 5.2 Flujo de Renderizado de Página

```
[Usuario] → [AEM Dispatcher] → [AEM Publish]
                                      ↓
                            [Sling Request Processing]
                                      ↓
                            [HTL Template Rendering]
                                      ↓
                            [Sling Model Resolution]
                                      ↓
                      [OSGi Services (core bundle)]
                                      ↓
                      [JCR Content Repository]
                                      ↓
                            [HTML Response]
```

---

## 6. Patrones y Mejores Prácticas Aplicados

### 6.1 Separación de Concerns
- **core**: Lógica de negocio pura (Java)
- **ui.apps**: Presentación y UI (HTL, CSS, JS)
- **ui.config**: Configuración del entorno
- **ui.content**: Datos de contenido

### 6.2 Gestión de Configuración por Runmode
```
config           → Todas las instancias
config.author    → Solo Author
config.publish   → Solo Publish
config.dev       → Solo Desarrollo
config.prod      → Solo Producción
```

### 6.3 Versionado de Paquetes
- **Semantic Versioning**: `MAJOR.MINOR.PATCH`
- **Snapshots**: Para desarrollo (`1.0-SNAPSHOT`)
- **Releases**: Para producción (`1.0.0`)

### 6.4 Uso de Core Components
Reutilización de componentes certificados por Adobe:
- **Ventaja**: Menos código custom, mayor estabilidad
- **Versión**: 2.25.4
- **Proxy Pattern**: Extensión sin modificar originales

### 6.5 OSGi Declarative Services (DS)
```java
@Component(service = MyService.class)
public class MyServiceImpl implements MyService {
    @Reference
    private OtherService otherService;
    
    @Activate
    protected void activate(Config config) {
        // Inicialización
    }
}
```

**Ventajas**:
- Inyección de dependencias automática
- Gestión del ciclo de vida por OSGi
- Configuración dinámica sin reinicio

---

## 7. Tecnologías y Frameworks Clave

### 7.1 Backend
| Tecnología | Versión | Propósito |
|-----------|---------|-----------|
| **Apache Maven** | 3.x | Build y gestión de dependencias |
| **OSGi Framework** | R7+ | Modularización y servicios |
| **Apache Sling** | - | Framework web RESTful |
| **JCR (Jackrabbit Oak)** | - | Repositorio de contenido |
| **HTL (Sightly)** | - | Motor de plantillas |
| **Sling Models** | - | Vinculación Java-HTL |

### 7.2 Frontend
| Tecnología | Propósito |
|-----------|-----------|
| **HTL** | Templates server-side |
| **Client Libraries** | Gestión de CSS/JS |
| **Granite UI** | Componentes de diálogos |
| **Coral UI** | Framework UI de Adobe |

### 7.3 Testing
| Framework | Tipo |
|----------|------|
| **JUnit 5** | Unit testing (Java) |
| **Mockito** | Mocking |
| **Cypress** | E2E testing (UI) |

### 7.4 Build Tools
- **BND Maven Plugin**: Generación de manifiestos OSGi
- **FileVault Package Maven Plugin**: Empaquetado de contenido
- **Maven Surefire**: Ejecución de tests

---

## 8. Estructura de Directorios del Proyecto

```
vass-university-dummyproject/
│
├── .copilot-context/              # Contexto para GitHub Copilot
│   └── arquitectura.md            # Este documento
│
├── all/                           # Módulo agregador
│   ├── pom.xml
│   └── src/main/content/
│
├── core/                          # Bundle Java OSGi
│   ├── pom.xml
│   ├── src/main/java/
│   │   └── com/vasscompany/dummyproject/core/
│   │       ├── filters/
│   │       ├── listeners/
│   │       ├── models/
│   │       ├── schedulers/
│   │       ├── services/
│   │       └── servlets/
│   ├── src/test/java/           # Tests unitarios
│   └── target/                  # Artefactos compilados
│
├── ui.apps/                       # Componentes y templates
│   ├── pom.xml
│   ├── src/main/content/jcr_root/
│   │   └── apps/vass-university-dummyproject/
│   │       ├── clientlibs/
│   │       ├── components/
│   │       ├── templates/
│   │       └── i18n/
│   └── target/
│
├── ui.apps.structure/             # Estructura del repositorio
│   ├── pom.xml
│   └── src/main/content/jcr_root/
│
├── ui.config/                     # Configuraciones OSGi
│   ├── pom.xml
│   └── src/main/content/jcr_root/apps/.../osgiconfig/
│       ├── config/
│       ├── config.author/
│       ├── config.publish/
│       ├── config.dev/
│       └── config.prod/
│
├── ui.content/                    # Contenido inicial
│   ├── pom.xml
│   └── src/main/content/jcr_root/
│       └── content/vass-university-dummyproject/
│
├── it.tests/                      # Tests de integración
│   ├── pom.xml
│   ├── src/main/java/           # Test runners
│   └── src/test/java/           # Test cases
│
├── ui.tests/                      # Tests UI funcionales
│   ├── Dockerfile
│   ├── pom.xml
│   └── test-module/
│       ├── cypress/
│       ├── cypress.config.js
│       └── package.json
│
├── python/                        # Scripts auxiliares (no AEM)
│   ├── src/
│   ├── tests/
│   └── requirements.txt
│
├── pom.xml                        # POM reactor principal
├── archetype.properties           # Propiedades del arquetipo
├── README.md                      # Documentación del proyecto
└── LICENSE                        # Licencia del proyecto
```

---

## 9. Configuración del Entorno de Desarrollo

### 9.1 Requisitos Previos
- **Java JDK**: 11 (recomendado para AEM 6.5)
- **Apache Maven**: 3.6+
- **AEM Author**: `http://localhost:4502`
- **AEM Publish** (opcional): `http://localhost:4503`
- **Node.js**: 16+ (para ui.tests)
- **IDE**: IntelliJ IDEA / Eclipse con plugins AEM

### 9.2 Credenciales por Defecto
```properties
aem.host=localhost
aem.port=4502
sling.user=admin
sling.password=admin
vault.user=admin
vault.password=admin
```

**⚠️ Seguridad**: Cambiar en entornos de producción.

### 9.3 Configuración en `pom.xml`
Sobrescribir propiedades:
```bash
mvn clean install -PautoInstallSinglePackage \
  -Daem.host=dev-server.company.com \
  -Daem.port=4502 \
  -Dvault.user=deployer \
  -Dvault.password=secret123
```

---

## 10. Extensibilidad y Personalización

### 10.1 Añadir un Nuevo Componente
1. Crear estructura en `ui.apps/src/main/content/jcr_root/apps/.../components/`
2. Añadir HTL template (`.html`)
3. Crear dialog XML (`.content.xml`)
4. Crear Sling Model en `core/src/main/java/.../models/`
5. Desplegar con `mvn clean install -PautoInstallPackage`

### 10.2 Añadir un Nuevo Servicio OSGi
1. Crear interfaz en `core/src/main/java/.../services/`
2. Implementar con `@Component(service = MyService.class)`
3. Configurar en `ui.config/osgiconfig/config/`
4. Desplegar con `mvn clean install -PautoInstallBundle`

### 10.3 Añadir Client Library
1. Crear carpeta en `ui.apps/.../clientlibs/clientlib-custom/`
2. Añadir `.content.xml` con categorías
3. Añadir archivos CSS/JS
4. Incluir en página: `<sly data-sly-use.clientlib="/libs/granite/sightly/templates/clientlib.html" data-sly-call="${clientlib.css @ categories='custom-category'}"/>`

---

## 11. Pipeline CI/CD (Sugerido)

### 11.1 Fases del Pipeline
```
[Commit] → [Build] → [Unit Tests] → [Package] → [Deploy Dev] → [Integration Tests] → [Deploy QA] → [UI Tests] → [Deploy Prod]
```

### 11.2 Comandos Clave por Fase
| Fase | Comando |
|------|---------|
| **Build** | `mvn clean compile` |
| **Unit Tests** | `mvn test` |
| **Package** | `mvn package` |
| **Integration Tests** | `mvn verify -Pit` |
| **Deploy Dev** | `mvn install -PautoInstallSinglePackage -Daem.host=dev.company.com` |
| **UI Tests** | `cd ui.tests/test-module && npm test` |

### 11.3 Herramientas Recomendadas
- **Jenkins / GitLab CI / GitHub Actions**: Orquestación
- **SonarQube**: Calidad de código
- **Nexus / Artifactory**: Gestión de artefactos
- **Cloud Manager** (AEM as a Cloud Service): Despliegue gestionado

---

## 12. Consideraciones de Seguridad

### 12.1 Gestión de Usuarios de Servicio
El proyecto incluye configuración para usuarios de servicio OSGi:
```json
{
  "scripts": [
    "create service user vass-system with path /home/users/system/vass/vass-system",
    "add principal vass-system to group administrators"
  ],
  "user.mapping": [
    "vass-university-dummyproject.core:vass-system-subservice=vass-system"
  ]
}
```

**Ubicación**: `ui.config/src/main/content/jcr_root/apps/.../osgiconfig/config/org.apache.sling.serviceusermapping.impl.ServiceUserMapperImpl.amended-vass.cfg.json`

### 12.2 Mejores Prácticas
- ✅ Usar Service Users en lugar de admin
- ✅ Aplicar principio de mínimo privilegio
- ✅ Validar entradas en servlets
- ✅ Usar CSRF tokens en formularios
- ✅ Configurar Content Security Policy (CSP)

---

## 13. Migración y Evolución

### 13.1 Actualizar a AEM as a Cloud Service
**Cambios requeridos**:
- Eliminar runmodes específicos de instalación
- Actualizar a Java 11+
- Revisar APIs deprecadas
- Añadir módulo `analyse` para validación
- Configurar Cloud Manager pipelines

### 13.2 Actualizar Core Components
```xml
<core.wcm.components.version>2.25.4</core.wcm.components.version>
```

Actualizar versión en `pom.xml` principal y ejecutar:
```bash
mvn clean install -U
```

### 13.3 Actualizar Arquetipo
Para regenerar con nueva versión:
```bash
mvn archetype:generate -DarchetypeVersion=51
```

⚠️ **Nota**: Requiere merge manual de cambios custom.

---

## 14. Recursos y Referencias

### 14.1 Documentación Oficial
- [AEM 6.5 Documentation](https://experienceleague.adobe.com/docs/experience-manager-65.html)
- [AEM Project Archetype](https://github.com/adobe/aem-project-archetype)
- [AEM Core Components](https://github.com/adobe/aem-core-wcm-components)
- [HTL Specification](https://github.com/adobe/htl-spec)

### 14.2 Tutoriales
- [WKND Tutorial](https://experienceleague.adobe.com/docs/experience-manager-learn/getting-started-wknd-tutorial-develop/overview.html)
- [AEM Developer Series](https://experienceleague.adobe.com/docs/experience-manager-learn/cloud-service/overview.html)

### 14.3 Comunidad
- [Adobe Experience League Community](https://experienceleaguecommunities.adobe.com/t5/adobe-experience-manager/ct-p/adobe-experience-manager-community)
- [Stack Overflow - AEM](https://stackoverflow.com/questions/tagged/aem)

---

## 15. Glosario de Términos AEM

| Término | Descripción |
|---------|-------------|
| **AEM** | Adobe Experience Manager - CMS empresarial |
| **OSGi** | Framework de modularización Java |
| **Sling** | Framework web RESTful sobre JCR |
| **JCR** | Java Content Repository - API de repositorio |
| **HTL** | HTML Template Language (antes Sightly) |
| **Sling Model** | POJO Java vinculado a recursos Sling |
| **Client Library** | Gestión de CSS/JS en AEM |
| **Content Package** | ZIP con contenido JCR desplegable |
| **Bundle** | JAR con metadatos OSGi |
| **Runmode** | Modo de ejecución (author/publish/dev/prod) |
| **Component** | Unidad reutilizable de UI |
| **Template** | Estructura de página predefinida |
| **Policy** | Configuración de componente |
| **Overlay** | Sobrescritura de funcionalidad OOTB |
| **Proxy Component** | Extensión de Core Component |

---

## 16. Diagrama de Dependencias entre Módulos

```
                    ┌─────────────────┐
                    │   vass-univ.    │
                    │  dummyproject   │
                    │  (reactor POM)  │
                    └────────┬────────┘
                             │
         ┌───────────────────┼───────────────────┐
         │                   │                   │
         ▼                   ▼                   ▼
    ┌────────┐          ┌────────┐         ┌──────────┐
    │  core  │          │ui.apps │         │ui.config │
    │(bundle)│          │        │         │          │
    └────┬───┘          └───┬────┘         └────┬─────┘
         │                  │                   │
         │                  │                   │
         └──────────────────┼───────────────────┘
                            │
                            ▼
                      ┌──────────┐
                      │ui.apps.  │
                      │structure │
                      └─────┬────┘
                            │
                            ▼
                      ┌──────────┐
                      │ui.content│
                      └─────┬────┘
                            │
                            ▼
                       ┌─────────┐
                       │   all   │
                       │ (deploy)│
                       └─────────┘
                            ▲
                            │
                     (contiene todos
                      los módulos)
```

**Tests** (independientes):
```
it.tests  ──→ Valida instancia AEM en runtime
ui.tests  ──→ Valida UI en navegador
```

---

## 17. Checklist de Desarrollo

### ✅ Antes de Commit
- [ ] Código compila sin errores: `mvn clean compile`
- [ ] Tests unitarios pasan: `mvn test`
- [ ] Código formateado según estándares
- [ ] Sin credenciales hardcodeadas
- [ ] Logs apropiados (no System.out.println)

### ✅ Antes de Deploy
- [ ] Tests de integración pasan: `mvn verify -Pit`
- [ ] Paquete se genera correctamente: `mvn package`
- [ ] Configuraciones por runmode validadas
- [ ] Service Users configurados

### ✅ Antes de Release
- [ ] Tests UI pasan: `npm test` (ui.tests)
- [ ] Documentación actualizada
- [ ] Versión actualizada (no -SNAPSHOT)
- [ ] Changelog generado

---

## 18. Contacto y Soporte

**Equipo de Desarrollo**: VASS University  
**Proyecto**: vass-university-dummyproject  
**Versión de este Documento**: 1.0  
**Última Actualización**: 2026-03-09

---

> **Nota**: Este documento describe la arquitectura base generada por el arquetipo AEM 50. A medida que el proyecto evolucione, debe actualizarse para reflejar personalizaciones y extensiones específicas del proyecto.


---

### From .copilot-context/patrones.md


## Índice
1. [Patrones de Arquitectura Enterprise](#1-patrones-de-arquitectura-enterprise)
2. [Patrones de Diseño GoF Implementados](#2-patrones-de-diseño-gof-implementados)
3. [Patrones Específicos de AEM/OSGi](#3-patrones-específicos-de-aemosgi)
4. [Patrones de Integración](#4-patrones-de-integración)
5. [Patrones de Diseño de APIs](#5-patrones-de-diseño-de-apis)
6. [Anti-Patrones a Evitar](#6-anti-patrones-a-evitar)
7. [Mejores Prácticas de Implementación](#7-mejores-prácticas-de-implementación)

---

## 1. Patrones de Arquitectura Enterprise

### 1.1 **Layered Architecture (Arquitectura por Capas)**

El proyecto implementa una clara separación de responsabilidades en capas:

```
┌──────────────────────────────────────┐
│   Capa de Presentación (ui.apps)    │  ← HTL templates, components
├──────────────────────────────────────┤
│   Capa de Aplicación (core)         │  ← Sling Models, Servlets
├──────────────────────────────────────┤
│   Capa de Servicios (core.services) │  ← Business logic
├──────────────────────────────────────┤
│   Capa de Datos (JCR Repository)    │  ← Content persistence
└──────────────────────────────────────┘
```

**Ubicación en el proyecto**:
- **Presentación**: `ui.apps/src/main/content/jcr_root/apps/.../components/`
- **Aplicación**: `core/src/main/java/.../models/` y `core/.../servlets/`
- **Servicios**: `core/src/main/java/.../services/`
- **Configuración**: `ui.config/src/main/content/.../osgiconfig/`

**Ventajas**:
- ✅ Separación clara de responsabilidades
- ✅ Facilita el testing unitario de cada capa
- ✅ Permite cambios en una capa sin afectar otras
- ✅ Reutilización de servicios en múltiples componentes

---

### 1.2 **Dependency Injection (Inyección de Dependencias)**

Implementado mediante **OSGi Declarative Services** y **Sling Models Injectors**.

#### Ejemplo 1: Inyección en Sling Model
```java
// core/src/main/java/.../models/HelloWorldModel.java
@Model(adaptables = Resource.class)
public class HelloWorldModel {
    
    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @SlingObject
    private Resource currentResource;
    
    @SlingObject
    private ResourceResolver resourceResolver;
    
    @PostConstruct
    protected void init() {
        // Lógica de inicialización
    }
}
```

#### Ejemplo 2: Inyección en Servicio OSGi
```java
// core/src/main/java/.../services/impl/FavoritesServiceImpl.java
@Component(service = {FavoritesService.class}, immediate = true)
public class FavoritesServiceImpl implements FavoritesService {
    
    // Las dependencias se inyectan automáticamente por OSGi
    // @Reference
    // private OtherService otherService;
}
```

**Anotaciones clave**:
- `@SlingObject` - Inyecta objetos Sling (ResourceResolver, Request, Response)
- `@ValueMapValue` - Inyecta propiedades del nodo JCR
- `@OSGiService` - Inyecta servicios OSGi
- `@Reference` - Inyección de servicios en componentes OSGi

**Ubicación**: `core/src/main/java/.../models/` y `core/.../services/`

---

### 1.3 **Repository Pattern**

Aunque no está explícitamente implementado con clases Repository, el acceso al JCR actúa como un Repository Pattern implícito.

**Cómo se usa en el proyecto**:
```java
// Acceso a recursos mediante ResourceResolver (abstracción del repositorio)
@SlingObject
private ResourceResolver resourceResolver;

// El ResourceResolver actúa como interfaz de acceso al repositorio
Resource resource = resourceResolver.getResource("/content/mypage");
```

**Ventajas**:
- ✅ Abstracción del almacenamiento de datos (JCR)
- ✅ Facilita el testing mediante mocks
- ✅ Desacopla la lógica de negocio del acceso a datos

---

## 2. Patrones de Diseño GoF Implementados

### 2.1 **Adapter Pattern** ⭐ (Más importante en AEM)

El patrón Adapter es fundamental en Sling/AEM. Permite adaptar un objeto a otro tipo.

#### Ejemplo en el proyecto:
```java
// Adaptando ResourceResolver a PageManager
PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

// Adaptando Resource a HelloWorldModel
@Model(adaptables = Resource.class)
public class HelloWorldModel { ... }
```

**Uso en HTL**:
```html
<!-- ui.apps/.../components/helloworld/helloworld.html -->
<div data-sly-use.model="com.vasscompany.dummyproject.core.models.HelloWorldModel">
    ${model.message}
</div>
```

**Flujo**:
1. HTL solicita adaptar el `Resource` actual
2. Sling busca un Sling Model registrado para `Resource`
3. Instancia `HelloWorldModel` e inyecta dependencias
4. Retorna el modelo adaptado

**Ventajas**:
- ✅ Separación entre representación (HTL) y lógica (Java)
- ✅ Reutilización de modelos en múltiples componentes
- ✅ Testabilidad independiente

**Ubicación**: `core/src/main/java/.../models/HelloWorldModel.java`

---

### 2.2 **Service Locator Pattern** (Implícito en OSGi)

OSGi implementa un Service Registry que actúa como Service Locator.

#### Ejemplo:
```java
@Component(service = {FavoritesService.class}, immediate = true)
public class FavoritesServiceImpl implements FavoritesService {
    // OSGi registra este servicio automáticamente
}

// Uso mediante @Reference (inyección) o mediante BundleContext.getServiceReference()
```

**Ventajas**:
- ✅ Desacoplamiento entre consumidores y proveedores de servicios
- ✅ Gestión dinámica del ciclo de vida
- ✅ Permite múltiples implementaciones del mismo servicio

**Ubicación**: `core/src/main/java/.../services/`

---

### 2.3 **Template Method Pattern**

Implementado en servlets y schedulers mediante clases base abstractas.

#### Ejemplo en SimpleServlet:
```java
// core/src/main/java/.../servlets/SimpleServlet.java
public class SimpleServlet extends SlingSafeMethodsServlet {
    
    @Override
    protected void doGet(final SlingHttpServletRequest req,
                        final SlingHttpServletResponse resp) 
                        throws ServletException, IOException {
        // Implementación específica del método GET
        final Resource resource = req.getResource();
        resp.getWriter().write("Title = " + resource.getValueMap().get(JCR_TITLE));
    }
}
```

**Ventajas**:
- ✅ Define el esqueleto del algoritmo (en clase base)
- ✅ Permite sobrescribir pasos específicos
- ✅ Reduce código duplicado

**Ubicación**: `core/src/main/java/.../servlets/SimpleServlet.java`

---

### 2.4 **Observer Pattern** (Event Listeners)

Implementado mediante `ResourceChangeListener` y otros event listeners de OSGi.

#### Ejemplo:
```java
// core/src/main/java/.../listeners/SimpleResourceListener.java
@Component(service = ResourceChangeListener.class, immediate = true)
public class SimpleResourceListener implements ResourceChangeListener {
    
    @Override
    public void onChange(List<ResourceChange> changes) {
        changes.forEach(change -> {
            logger.debug("Resource event: {} at: {}", 
                        change.getType(), change.getPath());
        });
    }
}
```

**Tipos de eventos en AEM**:
- `ResourceChangeListener` - Cambios en recursos JCR
- `EventHandler` - Eventos OSGi genéricos
- `WorkflowListener` - Eventos de workflows
- `ReplicationEventListener` - Eventos de replicación

**Ventajas**:
- ✅ Desacoplamiento entre emisores y receptores de eventos
- ✅ Múltiples observadores pueden reaccionar al mismo evento
- ✅ Extensibilidad sin modificar código existente

**Ubicación**: `core/src/main/java/.../listeners/SimpleResourceListener.java`

---

### 2.5 **Chain of Responsibility Pattern** (Filters)

Los Sling Filters implementan este patrón mediante la cadena de filtros.

#### Ejemplo:
```java
// core/src/main/java/.../filters/LoggingFilter.java
@Component(service = Filter.class,
           property = {
               EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST,
           })
@ServiceRanking(-700)
public class LoggingFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                        FilterChain filterChain) throws IOException, ServletException {
        // Pre-processing
        logger.debug("request for {}", slingRequest.getRequestPathInfo().getResourcePath());
        
        // Pasar al siguiente filtro en la cadena
        filterChain.doFilter(request, response);
        
        // Post-processing (si se necesita)
    }
}
```

**Cadena de filtros típica en AEM**:
```
Request → SecurityFilter → LoggingFilter → AuthenticationFilter → ... → Servlet
```

**Ventajas**:
- ✅ Cada filtro procesa la petición y decide si continuar
- ✅ Orden configurable mediante `@ServiceRanking`
- ✅ Fácil añadir/remover filtros sin afectar otros

**Ubicación**: `core/src/main/java/.../filters/LoggingFilter.java`

---

### 2.6 **Strategy Pattern**

Aunque no hay ejemplo directo, este patrón se usa implícitamente en:

#### Ejemplo conceptual en el proyecto:
```java
// Diferentes estrategias de procesamiento según el tipo
public interface ProcessingStrategy {
    void process(Resource resource);
}

@Component(service = ProcessingStrategy.class, property = "type=image")
public class ImageProcessingStrategy implements ProcessingStrategy { ... }

@Component(service = ProcessingStrategy.class, property = "type=video")
public class VideoProcessingStrategy implements ProcessingStrategy { ... }
```

**Uso común en AEM**:
- Estrategias de caché (dispatcher vs. CDN)
- Estrategias de renderizado (SSR vs. CSR)
- Estrategias de autenticación (SAML vs. OAuth)

---

### 2.7 **Builder Pattern** (Lombok @Builder)

Usado implícitamente con Lombok `@Data`.

#### Ejemplo en el proyecto:
```java
// core/src/main/java/.../models/favorites/beans/FavoritesModel.java
@Data
public class FavoritesModel {
    private String id;
    private String url;
    private String title;
    private String type;
    private String site;
    private String user;
}

// Uso (si se añade @Builder):
// FavoritesModel favorite = FavoritesModel.builder()
//     .id("123")
//     .url("/en/products")
//     .title("Product Page")
//     .build();
```

**Ventajas**:
- ✅ Construcción fluida de objetos complejos
- ✅ Código más legible
- ✅ Validación en construcción

**Ubicación**: `core/src/main/java/.../models/favorites/beans/FavoritesModel.java`

---

## 3. Patrones Específicos de AEM/OSGi

### 3.1 **Sling Model Pattern** ⭐⭐⭐

Patrón fundamental en AEM para vincular lógica Java con componentes HTL.

#### Estructura completa:
```java
@Model(
    adaptables = {Resource.class, SlingHttpServletRequest.class},
    adapters = {HelloWorldModel.class},
    resourceType = "vass-university-dummyproject/components/helloworld",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class HelloWorldModel {
    
    @ValueMapValue
    private String title;
    
    @SlingObject
    private Resource resource;
    
    @SlingObject
    private ResourceResolver resourceResolver;
    
    @OSGiService
    private SomeService someService;
    
    @PostConstruct
    protected void init() {
        // Lógica de inicialización después de todas las inyecciones
    }
    
    // Getters expuestos a HTL
    public String getTitle() {
        return title;
    }
}
```

**Uso en HTL**:
```html
<div data-sly-use.model="com.vasscompany.dummyproject.core.models.HelloWorldModel">
    <h1>${model.title}</h1>
</div>
```

**Ventajas**:
- ✅ Separación clara entre presentación y lógica
- ✅ Inyección automática de dependencias
- ✅ Testabilidad mejorada
- ✅ Reutilización entre componentes

**Ubicación**: `core/src/main/java/.../models/`

---

### 3.2 **OSGi Component Lifecycle Pattern**

Gestión del ciclo de vida mediante anotaciones OSGi.

#### Ejemplo completo:
```java
@Component(
    service = {MyService.class},
    immediate = true,
    configurationPolicy = ConfigurationPolicy.REQUIRE
)
@Designate(ocd = MyService.Config.class)
public class MyServiceImpl implements MyService {
    
    @ObjectClassDefinition(name = "My Service Configuration")
    public @interface Config {
        @AttributeDefinition(name = "Service Enabled")
        boolean enabled() default true;
        
        @AttributeDefinition(name = "API Endpoint")
        String apiEndpoint() default "https://api.example.com";
    }
    
    private Config config;
    
    @Activate
    protected void activate(Config config) {
        this.config = config;
        // Inicialización cuando el servicio se activa
    }
    
    @Modified
    protected void modified(Config config) {
        this.config = config;
        // Reconfiguración cuando cambian las propiedades
    }
    
    @Deactivate
    protected void deactivate() {
        // Limpieza antes de desactivar el servicio
    }
}
```

**Ciclo de vida**:
```
REGISTERED → ACTIVATE → ACTIVE → MODIFIED (opcional) → DEACTIVATE → UNREGISTERED
```

**Ubicación**: `core/src/main/java/.../services/`  
**Configuración**: `ui.config/.../osgiconfig/config/*.cfg.json`

---

### 3.3 **Servlet Resource Type Binding Pattern**

Vinculación de servlets a resource types específicos.

#### Ejemplo:
```java
// core/src/main/java/.../servlets/SimpleServlet.java
@Component(service = { Servlet.class })
@SlingServletResourceTypes(
    resourceTypes = "vass-university-dummyproject/components/page",
    methods = HttpConstants.METHOD_GET,
    extensions = "txt"
)
public class SimpleServlet extends SlingSafeMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) {
        // Maneja: /content/mypage.txt para recursos de tipo "page"
    }
}
```

**Alternativas de binding**:
- `@SlingServletPaths` - Por ruta absoluta (⚠️ menos recomendado)
- `@SlingServletResourceTypes` - Por resource type (✅ recomendado)
- `@SlingServletSelectors` - Por selectores adicionales

**Ubicación**: `core/src/main/java/.../servlets/`

---

### 3.4 **Scheduler Configuration Pattern**

Tareas programadas con configuración dinámica.

#### Ejemplo:
```java
// core/src/main/java/.../schedulers/SimpleScheduledTask.java
@Designate(ocd = SimpleScheduledTask.Config.class)
@Component(service = Runnable.class)
public class SimpleScheduledTask implements Runnable {
    
    @ObjectClassDefinition(name = "A scheduled task")
    public static @interface Config {
        @AttributeDefinition(name = "Cron-job expression")
        String scheduler_expression() default "*/30 * * * * ?";
        
        @AttributeDefinition(name = "Concurrent task")
        boolean scheduler_concurrent() default false;
        
        @AttributeDefinition(name = "A parameter")
        String myParameter() default "";
    }
    
    @Override
    public void run() {
        // Lógica ejecutada según el cron expression
    }
    
    @Activate
    protected void activate(final Config config) {
        // Configuración inicial
    }
}
```

**Expresiones cron comunes**:
- `0 0 2 * * ?` - Cada día a las 2:00 AM
- `*/5 * * * * ?` - Cada 5 segundos
- `0 0/15 * * * ?` - Cada 15 minutos

**Ubicación**: `core/src/main/java/.../schedulers/`

---

### 3.5 **Service User Mapping Pattern** ⭐

Patrón de seguridad para acceso con privilegios limitados.

#### Configuración:
```json
// ui.config/.../osgiconfig/config/org.apache.sling.serviceusermapping.impl.ServiceUserMapperImpl.amended-vass-system.cfg.json
{
  "user.mapping": [
    "vass-university-dummyproject.core:vass-system-subservice=vass-system"
  ]
}
```

#### Inicialización del usuario:
```json
// org.apache.sling.jcr.repoinit.RepositoryInitializer-vass-system.cfg.json
{
  "scripts": [
    "create service user vass-system with path /home/users/system/vass/vass-system",
    "add principal vass-system to group administrators"
  ]
}
```

#### Uso en código:
```java
@Reference
private ResourceResolverFactory resolverFactory;

Map<String, Object> authInfo = new HashMap<>();
authInfo.put(ResourceResolverFactory.SUBSERVICE, "vass-system-subservice");

try (ResourceResolver serviceResolver = resolverFactory.getServiceResourceResolver(authInfo)) {
    // Operaciones con privilegios del service user
}
```

**Ventajas**:
- ✅ Seguridad mejorada (principio de mínimo privilegio)
- ✅ Evita usar usuarios admin/system
- ✅ Auditoría clara de accesos

**Ubicación**: `ui.config/.../osgiconfig/config/`

---

## 4. Patrones de Integración

### 4.1 **REST API Client Pattern**

Implementado en servicios que consumen APIs externas.

#### Ejemplo (conceptual basado en FavoritesService):
```java
@Component(service = {FavoritesService.class}, immediate = true)
public class FavoritesServiceImpl implements FavoritesService {
    
    @Reference
    private HttpClientBuilderFactory httpClientFactory;
    
    @Override
    public FavoritesRespModel addFavorite(SlingHttpServletRequest request) {
        FavoritesModel favorite = buildFavoriteFromRequest(request);
        
        // Serializar a JSON
        String json = new Gson().toJson(favorite);
        
        // Llamar API externa
        HttpPost httpPost = new HttpPost("https://api.example.com/favorites");
        httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        
        try (CloseableHttpClient httpClient = httpClientFactory.newBuilder().build()) {
            HttpResponse response = httpClient.execute(httpPost);
            // Procesar respuesta
        }
        
        return responseModel;
    }
}
```

**Mejores prácticas**:
- ✅ Usar `HttpClientBuilderFactory` de AEM
- ✅ Configurar timeouts
- ✅ Implementar retry logic
- ✅ Manejar errores HTTP apropiadamente
- ✅ Logging de peticiones/respuestas

**Ubicación**: `core/src/main/java/.../services/favorites/impl/`

---

### 4.2 **Data Transfer Object (DTO) Pattern**

Objetos simples para transferir datos entre capas.

#### Ejemplo:
```java
// core/src/main/java/.../models/favorites/beans/FavoritesModel.java
@Data
public class FavoritesModel {
    private String id;
    private String url;
    private String title;
    private String type;
    private String site;
    private String user;
}

// Request DTO
@Data
public class FavoritesReqModel {
    private String url;
    private String title;
}

// Response DTO
@Data
public class FavoritesRespModel {
    private boolean success;
    private String message;
    private FavoritesModel data;
}
```

**Ventajas**:
- ✅ Separación entre representación interna y externa
- ✅ Validación de datos centralizada
- ✅ Serialización/deserialización clara

**Ubicación**: `core/src/main/java/.../models/favorites/`

---

### 4.3 **Gateway Pattern** (Servlet como API Gateway)

Servlets que actúan como puntos de entrada para APIs.

#### Ejemplo (conceptual):
```java
@Component(service = { Servlet.class })
@SlingServletPaths(value = "/bin/api/favorites")
public class FavoritesServlet extends SlingAllMethodsServlet {
    
    @Reference
    private FavoritesService favoritesService;
    
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) 
            throws ServletException, IOException {
        
        // Validación
        if (!isValidRequest(request)) {
            response.setStatus(400);
            return;
        }
        
        // Delegación al servicio
        FavoritesRespModel result = favoritesService.addFavorite(request);
        
        // Respuesta
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(result));
    }
}
```

**Responsabilidades del Gateway**:
- 🔐 Autenticación/Autorización
- ✅ Validación de entrada
- 📊 Rate limiting
- 📝 Logging de requests
- 🔄 Routing a servicios internos

---

## 5. Patrones de Diseño de APIs

### 5.1 **RESTful Resource Naming**

Convenciones para naming de servlets/endpoints.

#### Ejemplos correctos:
```
GET    /bin/api/favorites           → Listar favoritos
POST   /bin/api/favorites           → Crear favorito
GET    /bin/api/favorites/{id}      → Obtener favorito específico
PUT    /bin/api/favorites/{id}      → Actualizar favorito
DELETE /bin/api/favorites/{id}      → Eliminar favorito

GET    /bin/api/users/{id}/favorites → Favoritos de un usuario
```

#### Ejemplos incorrectos (❌):
```
/bin/getFavorites               ❌ No usar verbos en la URL
/bin/favorite                   ❌ Usar plural
/bin/api/Favorites              ❌ No usar mayúsculas
/bin/api/favorites.json         ❌ Extensión innecesaria (usar Accept header)
```

---

### 5.2 **API Versioning Pattern**

Estrategias para versionar APIs.

#### Opción 1: URL Path Versioning (✅ Recomendado)
```java
@SlingServletPaths(value = "/bin/api/v1/favorites")
public class FavoritesV1Servlet { ... }

@SlingServletPaths(value = "/bin/api/v2/favorites")
public class FavoritesV2Servlet { ... }
```

#### Opción 2: Header Versioning
```java
@Component(service = { Servlet.class })
@SlingServletPaths(value = "/bin/api/favorites")
public class FavoritesServlet extends SlingAllMethodsServlet {
    
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        String apiVersion = request.getHeader("API-Version");
        if ("2.0".equals(apiVersion)) {
            // Lógica v2
        } else {
            // Lógica v1 (default)
        }
    }
}
```

---

### 5.3 **Error Response Pattern**

Estructura estandarizada para respuestas de error.

#### Implementación:
```java
@Data
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private long timestamp;
    private String path;
    
    public static ErrorResponse of(int status, String error, String message, String path) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(status);
        response.setError(error);
        response.setMessage(message);
        response.setTimestamp(System.currentTimeMillis());
        response.setPath(path);
        return response;
    }
}

// Uso en servlet
response.setStatus(400);
response.setContentType("application/json");
ErrorResponse errorResp = ErrorResponse.of(
    400, 
    "Bad Request", 
    "Missing required parameter: url",
    request.getRequestURI()
);
response.getWriter().write(new Gson().toJson(errorResp));
```

#### Respuesta JSON:
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Missing required parameter: url",
  "timestamp": 1709730000000,
  "path": "/bin/api/favorites"
}
```

---

## 6. Anti-Patrones a Evitar

### 6.1 ❌ **Dios Component (God Component)**

**Problema**: Componente con demasiadas responsabilidades.

```java
// ❌ MAL - Componente que hace TODO
@Model(adaptables = Resource.class)
public class ProductModel {
    public String getTitle() { ... }
    public List<Product> getRelatedProducts() { ... }
    public String calculatePrice() { ... }
    public void sendEmail() { ... }              // ❌ No debería estar aquí
    public void logToAnalytics() { ... }         // ❌ No debería estar aquí
    public String callExternalAPI() { ... }      // ❌ No debería estar aquí
}
```

**Solución**: Delegar responsabilidades a servicios.

```java
// ✅ BIEN
@Model(adaptables = Resource.class)
public class ProductModel {
    @OSGiService
    private ProductService productService;
    
    @OSGiService
    private EmailService emailService;
    
    @OSGiService
    private AnalyticsService analyticsService;
    
    public String getTitle() {
        return productService.getTitle(resource);
    }
}
```

---

### 6.2 ❌ **Service Locator en vez de Dependency Injection**

**Problema**: Buscar servicios manualmente en vez de inyectarlos.

```java
// ❌ MAL
@Model(adaptables = Resource.class)
public class MyModel {
    public void doSomething() {
        BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();
        ServiceReference ref = context.getServiceReference(MyService.class.getName());
        MyService service = (MyService) context.getService(ref);
        service.execute();
    }
}
```

**Solución**: Usar inyección de dependencias.

```java
// ✅ BIEN
@Model(adaptables = Resource.class)
public class MyModel {
    @OSGiService
    private MyService myService;
    
    public void doSomething() {
        myService.execute();
    }
}
```

---

### 6.3 ❌ **Hardcoded Paths**

**Problema**: Rutas absolutas hardcodeadas en código.

```java
// ❌ MAL
Resource resource = resourceResolver.getResource("/content/mysite/en/products");
```

**Solución**: Usar configuraciones OSGi.

```java
// ✅ BIEN
@Component(service = {MyService.class})
@Designate(ocd = MyService.Config.class)
public class MyServiceImpl implements MyService {
    
    @ObjectClassDefinition(name = "My Service Config")
    public @interface Config {
        @AttributeDefinition(name = "Content Root Path")
        String contentRootPath() default "/content/mysite";
    }
    
    private String contentRootPath;
    
    @Activate
    protected void activate(Config config) {
        this.contentRootPath = config.contentRootPath();
    }
}
```

---

### 6.4 ❌ **No cerrar ResourceResolvers**

**Problema**: Memory leaks por no cerrar ResourceResolvers.

```java
// ❌ MAL
ResourceResolver resolver = resolverFactory.getServiceResourceResolver(authInfo);
Resource resource = resolver.getResource("/content/mypage");
// ❌ Nunca se cierra → memory leak
```

**Solución**: Usar try-with-resources.

```java
// ✅ BIEN
try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(authInfo)) {
    Resource resource = resolver.getResource("/content/mypage");
    // Procesamiento
} // Se cierra automáticamente
```

---

### 6.5 ❌ **Usar admin ResourceResolver**

**Problema**: Usar sesión administrativa en código de aplicación.

```java
// ❌ MAL (deprecated y peligroso)
ResourceResolver adminResolver = resolverFactory.getAdministrativeResourceResolver(null);
```

**Solución**: Usar Service Users.

```java
// ✅ BIEN
Map<String, Object> authInfo = new HashMap<>();
authInfo.put(ResourceResolverFactory.SUBSERVICE, "myapp-subservice");
try (ResourceResolver serviceResolver = resolverFactory.getServiceResourceResolver(authInfo)) {
    // Operaciones con privilegios limitados
}
```

---

### 6.6 ❌ **Lógica de negocio en HTL**

**Problema**: Lógica compleja en templates.

```html
<!-- ❌ MAL -->
<div data-sly-test="${properties.price > 100 && properties.discount > 0.2 && properties.stock > 0}">
    ${properties.price * (1 - properties.discount)}
</div>
```

**Solución**: Mover lógica a Sling Model.

```java
// ✅ BIEN
@Model(adaptables = Resource.class)
public class ProductModel {
    @ValueMapValue
    private double price;
    
    @ValueMapValue
    private double discount;
    
    public boolean isEligibleForPromotion() {
        return price > 100 && discount > 0.2 && stock > 0;
    }
    
    public double getFinalPrice() {
        return price * (1 - discount);
    }
}
```

```html
<!-- ✅ BIEN -->
<div data-sly-use.model="com.example.ProductModel" data-sly-test="${model.eligibleForPromotion}">
    ${model.finalPrice}
</div>
```

---

## 7. Mejores Prácticas de Implementación

### 7.1 **Sling Model Best Practices**

#### ✅ Usar defaultInjectionStrategy
```java
@Model(
    adaptables = Resource.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class MyModel {
    // No necesitas @Inject(optional=true) en cada campo
}
```

#### ✅ Preferir adaptables específicos
```java
// ✅ BIEN - Más específico
@Model(adaptables = Resource.class)

// ⚠️ Menos óptimo - Demasiado genérico
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class})
```

#### ✅ Usar @PostConstruct para inicialización
```java
@Model(adaptables = Resource.class)
public class MyModel {
    @ValueMapValue
    private String title;
    
    private String processedTitle;
    
    @PostConstruct
    protected void init() {
        // Toda la inicialización aquí
        processedTitle = title != null ? title.toUpperCase() : "";
    }
}
```

---

### 7.2 **OSGi Service Best Practices**

#### ✅ Definir interfaces antes de implementaciones
```java
// ✅ BIEN
public interface ProductService {
    Product getProduct(String id);
}

@Component(service = {ProductService.class})
public class ProductServiceImpl implements ProductService {
    @Override
    public Product getProduct(String id) { ... }
}
```

#### ✅ Usar @Designate para configuraciones
```java
@Component(service = {MyService.class})
@Designate(ocd = MyService.Config.class)
public class MyServiceImpl implements MyService {
    
    @ObjectClassDefinition(name = "My Service Configuration")
    public @interface Config {
        @AttributeDefinition(name = "API Key", description = "External API Key")
        String apiKey() default "";
    }
}
```

#### ✅ Configurar ubicación de configuraciones
```
ui.config/
└── osgiconfig/
    ├── config/                    → Todas las instancias
    ├── config.author/             → Solo Author
    ├── config.publish/            → Solo Publish
    ├── config.dev/                → Solo Dev
    └── config.prod/               → Solo Producción
```

---

### 7.3 **HTL Template Best Practices**

#### ✅ Usar data-sly-use para cargar modelos
```html
<!-- ✅ BIEN -->
<div data-sly-use.model="com.vasscompany.dummyproject.core.models.HelloWorldModel">
    ${model.message}
</div>
```

#### ✅ Usar data-sly-test para condicionales
```html
<!-- ✅ BIEN -->
<div data-sly-test="${model.hasContent}">
    ${model.content}
</div>

<!-- ❌ MAL -->
<div data-sly-test="${model.content != null && model.content != ''}">
```

#### ✅ Usar data-sly-list para iteraciones
```html
<!-- ✅ BIEN -->
<ul data-sly-list.item="${model.items}">
    <li>${item.title}</li>
</ul>
```

---

### 7.4 **Servlet Best Practices**

#### ✅ Usar @SlingServletResourceTypes en vez de paths
```java
// ✅ BIEN - Vinculado a resource type
@SlingServletResourceTypes(
    resourceTypes = "myapp/components/page",
    methods = HttpConstants.METHOD_GET
)

// ⚠️ Menos flexible - Vinculado a path absoluta
@SlingServletPaths(value = "/bin/myservlet")
```

#### ✅ Validar inputs
```java
@Override
protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {
    String param = request.getParameter("param");
    
    if (param == null || param.isEmpty()) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return;
    }
    
    // Procesar
}
```

#### ✅ Manejar excepciones apropiadamente
```java
@Override
protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
    try {
        // Lógica
    } catch (IllegalArgumentException e) {
        logger.error("Invalid argument", e);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    } catch (Exception e) {
        logger.error("Unexpected error", e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
```

---

### 7.5 **Testing Best Practices**

#### ✅ Usar AEM Mocks
```java
@ExtendWith(AemContextExtension.class)
class HelloWorldModelTest {
    
    private final AemContext context = AppAemContext.newAemContext();
    
    @Test
    void testGetMessage() {
        context.addModelsForClasses(HelloWorldModel.class);
        context.load().json("/com/vasscompany/test.json", "/content");
        
        Resource resource = context.resourceResolver().getResource("/content/test");
        HelloWorldModel model = resource.adaptTo(HelloWorldModel.class);
        
        assertNotNull(model);
        assertEquals("Expected message", model.getMessage());
    }
}
```

---

## 8. Checklist de Revisión de Código

### ✅ Arquitectura
- [ ] ¿Hay separación clara entre capas (presentación, aplicación, servicios)?
- [ ] ¿Los Sling Models solo contienen lógica de presentación?
- [ ] ¿La lógica de negocio está en servicios OSGi?
- [ ] ¿Se usan interfaces para servicios?

### ✅ Inyección de Dependencias
- [ ] ¿Se usa `@OSGiService`, `@SlingObject`, `@ValueMapValue`?
- [ ] ¿No hay Service Locator patterns?
- [ ] ¿Se usa `@PostConstruct` para inicialización?

### ✅ Gestión de Recursos
- [ ] ¿Se cierran todos los `ResourceResolver` con try-with-resources?
- [ ] ¿No se usa `getAdministrativeResourceResolver()`?
- [ ] ¿Se usan Service Users en vez de admin?

### ✅ Seguridad
- [ ] ¿Se validan todos los inputs de servlets?
- [ ] ¿Se sanitizan outputs en HTL?
- [ ] ¿Se usan Service Users con mínimos privilegios?
- [ ] ¿Las configuraciones sensibles están en OSGi config?

### ✅ Performance
- [ ] ¿Se evitan queries costosas en Sling Models?
- [ ] ¿Se usan índices en queries JCR/Oak?
- [ ] ¿Se cachean resultados cuando es apropiado?

### ✅ Testing
- [ ] ¿Hay tests unitarios para Sling Models?
- [ ] ¿Hay tests unitarios para servicios OSGi?
- [ ] ¿Se usan AEM Mocks apropiadamente?

---

## 9. Recursos de Referencia

### Documentación Oficial
- [Sling Models](https://sling.apache.org/documentation/bundles/models.html)
- [OSGi Declarative Services](https://docs.osgi.org/specification/osgi.cmpn/7.0.0/service.component.html)
- [HTL Specification](https://github.com/adobe/htl-spec)
- [AEM Core Components](https://github.com/adobe/aem-core-wcm-components)

### Libros Recomendados
- "Design Patterns: Elements of Reusable Object-Oriented Software" (Gang of Four)
- "Patterns of Enterprise Application Architecture" (Martin Fowler)
- "Clean Code" (Robert C. Martin)

---

**Versión del Documento**: 1.0  
**Última Actualización**: 2026-03-09  
**Mantenido por**: VASS University Team

> **Nota**: Este documento debe actualizarse conforme el proyecto evolucione y se implementen nuevos patrones.


---

### From .copilot-context/convenciones.md


## 1. Objetivo
Este documento define las convenciones de código y estilo que GitHub Copilot debe seguir al generar código para este repositorio AEM.

---

## 2. Java y AEM

- Usar Java 21.
- Seguir las convenciones estándar de Java (nombresCamelCase, clases en PascalCase, constantes en MAYÚSCULAS_SNAKE_CASE).
- Un archivo por clase pública.
- Mantener los imports ordenados y sin imports no usados.
- Evitar `var` (cuando no sea obligatorio) para mantener claridad.
- No usar APIs de Java marcadas como deprecated en AEM 6.5.

### 2.1 Estructura por capas

- Presentación: HTL + Sling Models en `core/.../models`.
- Servicios: lógica de negocio en `core/.../services` y `core/.../services/impl`.
- Integración: acceso a APIs externas encapsulado en servicios específicos.
- Acceso a repositorio JCR a través de `ResourceResolver` y servicios, no desde HTL.

### 2.2 Sling Models

- Usar `@Model` con `adaptables = { Resource.class, SlingHttpServletRequest.class }` cuando aplique.
- Usar `@ValueMapValue`, `@SlingObject` y `@OSGiService` para inyectar dependencias.
- Mantener los modelos orientados a la vista: exponer getters simples para HTL.
- Evitar lógica de negocio pesada en los modelos, delegarla a servicios.

### 2.3 Servicios OSGi

- Usar `@Component(service = X.class)` para declarar servicios.
- Usar `@Designate` y `@ObjectClassDefinition` para configuración OSGi cuando haya parámetros.
- Usar `@Activate`, `@Modified` y `@Deactivate` para gestionar el ciclo de vida.
- No usar Service Locator manual (`BundleContext`, `getServiceReference`) salvo casos excepcionales.
- Preferir interfaces + implementación (`FooService` + `FooServiceImpl`).

### 2.4 Servlets

- Usar `@SlingServletResourceTypes` siempre que sea posible.
- Evitar `@SlingServletPaths` salvo casos muy concretos.
- Devolver respuestas JSON bien estructuradas para APIs (DTOs dedicados).

---

## 3. Repositorio JCR y Service Users

- Nunca usar sesiones admin ni credenciales hardcoded.
- Para operaciones de escritura, usar `ResourceResolverFactory` con `SUBSERVICE`.
- Asegurarse de cerrar siempre `ResourceResolver` en bloques try-with-resources.
- No hardcodear rutas como `/content/...`, usar configuración OSGi o constantes bien nombradas.

---

## 4. Estilo de código

- Métodos cortos y con una sola responsabilidad.
- Nombrar métodos de forma descriptiva (qué hacen, no cómo).
- Evitar comentarios redundantes; preferir nombres claros.
- Preferir `Optional` y null checks explícitos frente a `NullPointerException`.
- Usar logging con SLF4J, no `System.out.println`.

---

## 5. Tests

- Usar JUnit 5 para tests unitarios.
- Usar Mockito para mocks.
- Mantener tests cerca de la clase bajo prueba.
- Evitar tests frágiles dependientes de datos concretos de contenido salvo que sea imprescindible.

---

## 6. Convenciones específicas de este proyecto

- Mantener la estructura de módulos Maven tal como está en `arquitectura.md`.
- Cuando se añadan componentes, servicios o schedulers nuevos, seguir los ejemplos y patrones definidos en `patrones.md`.
- Si se necesita una nueva convención, documentarla aquí antes de usarla de forma extensiva.

---

## Commit workflow reference

## Commit message rules
- Use the conventional commit format: `<type>(<scope>): <description>`
- Types: feat, fix, docs, style, refactor, test, chore, perf
- Keep the description concise (under 50 characters)
- Use imperative mood (e.g., "add" not "added" or "adds")
- Don't end with a period
- Use lowercase for the first word unless it's a proper noun
- Provide more details in the commit body if needed, separated by a blank line

## Branch naming conventions
- Use kebab-case (lowercase with hyphens)
- Follow the pattern: `<type>/<issue-number>-<short-description>`
- Types: feature, bugfix, hotfix, release, support
- Example: `feature/123-add-dark-mode`

## Pull request guidelines
- Link related issues using keywords (Fixes #123, Closes #456)
- Provide a clear description of changes
- Add screenshots for UI changes
- Ensure all CI checks pass before requesting review
- Keep PRs focused and small when possible