# Patrones de Diseño y Arquitectura - VASS University Dummy Project

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

