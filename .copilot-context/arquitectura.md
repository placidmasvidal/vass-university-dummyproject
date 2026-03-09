# Arquitectura del Proyecto AEM - VASS University Dummy Project

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

