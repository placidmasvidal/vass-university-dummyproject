# Lab 1.3: Code Review Asistido - Identificar Problemas en PR Sugerida por IA (Java / AEM)

## Objetivo

Realizar un code review completo de una Pull Request generada con IA (Copilot),
identificando problemas de seguridad, calidad, lógica, mantenibilidad y cumplimiento
con buenas prácticas de **Java 11 + AEM 6.5 + OSGi DS**.

El objetivo no es revisar Python puro, sino practicar un review realista dentro del
contexto del proyecto dummy: servicios OSGi en `core`, configuración OSGi en `ui.config`,
y pruebas unitarias con JUnit 5/AEM Mocks.

## Duración Estimada

**2-3 horas** (trabajo asíncrono entre sesiones)

## Prerequisitos

- ✅ Sesión 1.3 completada (Seguridad y Políticas)
- ✅ Labs 1.1 y 1.2 completados
- ✅ Conocimiento básico de code review practices
- ✅ Conocimiento básico de Java 11, OSGi DS y AEM 6.5
- ✅ Proyecto `vass-university-dummyproject` compilando localmente (`mvn clean install`)

## Contexto del Ejercicio

Un compañero de equipo ha creado una PR para añadir autenticación JWT a un endpoint
custom de AEM usando Copilot. La implementación incluye:

- un **servicio OSGi** para login, registro y validación de token,
- un **servlet Sling** expuesto por ruta,
- unos **tests unitarios mínimos**,
- y una **configuración sensible** duplicada en código y en OSGi config.

Tu tarea es revisar esa PR como si fueras el reviewer responsable del proyecto,
identificando:

1. Problemas de seguridad
2. Problemas de calidad de código
3. Problemas de lógica y concurrencia
4. Incumplimientos de estándares Java/AEM
5. Mejoras recomendables antes de merge

Debes usar el checklist de auditoría aprendido y proporcionar feedback constructivo,
accionable y bien documentado.

---

## PR para Revisar

### Descripción de la PR

**Título**: `Agregar módulo de autenticación JWT para servlet interno`

**Autor**: `[Compañero de equipo]`

**Descripción**:

> "Implementé autenticación JWT con Copilot para un servlet interno de AEM.
> Incluye login, registro, validación de token y configuración básica."

### Archivos Cambiados

1. `JwtAuthenticationServiceImpl.java` - Servicio OSGi principal de autenticación
2. `JwtAuthenticationServlet.java` - Servlet HTTP de login/registro/validación
3. `JwtAuthenticationServiceImplTest.java` - Tests unitarios
4. `ui.config/.../JwtAuthenticationServiceImpl.cfg.json` - Configuración sensible añadida

---

## Instrucciones Paso a Paso

### Paso 1: Revisión Inicial y Contexto (15 min)

**Tareas:**

1. Lee la descripción de la PR completamente
2. Revisa los archivos cambiados (ver código abajo)
3. Identifica qué responsabilidad debería pertenecer al servlet y cuál al servicio
4. Identifica el scope real del cambio: autenticación, persistencia, configuración y exposición HTTP
5. Anota de entrada cualquier red flag obvio

**Archivos a revisar:**
- `JwtAuthenticationServiceImpl.java`
- `JwtAuthenticationServlet.java`
- `JwtAuthenticationServiceImplTest.java`
- `JwtAuthenticationServiceImpl.cfg.json`

---

### Paso 2: Revisión de Seguridad (30 min)

**Tarea:**
Usa el checklist de seguridad de la sesión 1.3, adaptado a Java/AEM.

#### Checklist de Seguridad

**Credenciales y Secretos:**
- [ ] ¿Hay secretos JWT hardcoded en código o en config?
- [ ] ¿Las credenciales están externalizadas correctamente en OSGi config?
- [ ] ¿Se evita duplicar secretos entre código y configuración?
- [ ] ¿Se evita guardar passwords en logs, respuestas o estructuras inseguras?

**Datos Sensibles:**
- [ ] ¿Los logs exponen passwords, tokens o datos personales?
- [ ] ¿Las respuestas del servlet exponen detalles internos innecesarios?
- [ ] ¿El payload del JWT incluye más información de la necesaria?

**Autenticación y Autorización:**
- [ ] ¿La lógica de autenticación es segura?
- [ ] ¿El hashing de passwords es adecuado?
- [ ] ¿Se valida correctamente expiración e integridad del token?
- [ ] ¿Hay protección frente a brute force o abuso?
- [ ] ¿El servlet controla quién puede invocarlo?

**AEM / OSGi / JCR:**
- [ ] ¿Se usa `getAdministrativeResourceResolver()`? (No debería)
- [ ] ¿Se usan Service Users con mínimos privilegios si hay acceso al repositorio?
- [ ] ¿El servlet está expuesto por path absoluto (`/bin/...`) sin justificación?
- [ ] ¿Se evita mezclar autenticación custom con malas prácticas de AEM?

**Configuración:**
- [ ] ¿La configuración por defecto es segura?
- [ ] ¿Los valores sensibles tienen defaults inseguros?
- [ ] ¿La PR añade configuraciones que no deberían versionarse tal cual?

**Documenta cada problema encontrado con:**
- Ubicación (archivo y línea aproximada)
- Tipo de problema
- Severidad (Crítico, Alto, Medio, Bajo)
- Descripción
- Impacto
- Recomendación de solución

---

### Paso 3: Revisión de Calidad de Código (30 min)

#### Checklist de Calidad

**Estructura y Organización:**
- [ ] ¿La lógica de negocio está en el servicio y no en el servlet?
- [ ] ¿Hay separación clara entre capa HTTP y capa de servicio?
- [ ] ¿Hay responsabilidades mezcladas (auth, persistencia, logging, serialización)?
- [ ] ¿Hay código duplicado o constantes repetidas?

**Legibilidad:**
- [ ] ¿Los nombres son descriptivos?
- [ ] ¿Las clases y métodos tienen Javadoc o comentarios útiles?
- [ ] ¿El flujo es fácil de seguir?

**Buenas Prácticas Java/AEM:**
- [ ] ¿Se registra correctamente el `@Component` con su interfaz?
- [ ] ¿Se usan `@Reference`, `@Activate` y configuración OSGi de forma adecuada?
- [ ] ¿Se evitan `System.out.println` y logs inseguros?
- [ ] ¿Se manejan errores de forma robusta?
- [ ] ¿Hay validación de entrada?
- [ ] ¿Se usan tipos y APIs modernas (`java.time`, colecciones seguras, etc.)?

**Lógica y Robustez:**
- [ ] ¿La lógica de login es correcta?
- [ ] ¿Se manejan `null`, blanks y casos edge?
- [ ] ¿Hay problemas de thread-safety por usar estado mutable en un servicio singleton?
- [ ] ¿Hay bugs potenciales por concurrencia o por datos en memoria?

---

### Paso 4: Revisión de Tests (20 min)

#### Checklist de Tests

- [ ] ¿Los tests cubren funcionalidad principal?
- [ ] ¿Hay tests de error y casos edge?
- [ ] ¿Hay tests de token expirado / token inválido?
- [ ] ¿Hay tests para registros duplicados?
- [ ] ¿Los tests son independientes entre sí?
- [ ] ¿Se usa JUnit 5 correctamente?
- [ ] ¿Haría falta AEM Mocks o Mockito según el tipo de clase?
- [ ] ¿La cobertura es suficiente para un cambio de seguridad?

---

### Paso 5: Revisión de Estándares y Políticas (15 min)

#### Checklist de Políticas

- [ ] ¿Sigue la arquitectura por capas del proyecto?
- [ ] ¿Respeta patrones típicos de AEM/OSGi?
- [ ] ¿La configuración sensible debería ir en `ui.config` con otro tratamiento?
- [ ] ¿La PR tiene una descripción suficiente para un cambio de seguridad?
- [ ] ¿Se debería exigir revisión adicional por tratarse de autenticación?
- [ ] ¿Está permitido usar IA para este cambio? (sí, pero requiere review reforzado)

---

### Paso 6: Documentar Feedback (30 min)

**Tarea:**
Crea un documento de code review completo.

**Crea archivo:** `code_review_lab_1.3.md`

**Estructura sugerida:**

```markdown
# Code Review: PR - Agregar módulo de autenticación JWT para servlet interno

## Resumen Ejecutivo
- Estado general: [Aprobado con cambios / Requiere cambios / Rechazado]
- Problemas críticos encontrados: [número]
- Problemas de seguridad: [número]
- Problemas de calidad: [número]
- Riesgos específicos AEM/OSGi: [número]

## Problemas Críticos (Deben corregirse antes de merge)

### [Título del problema]
- **Archivo**: `JwtAuthenticationServiceImpl.java:45`
- **Severidad**: Crítico
- **Descripción**: [Descripción detallada]
- **Impacto**: [Qué puede pasar si no se corrige]
- **Recomendación**: [Cómo corregirlo]
- **Ejemplo de código corregido**: [Si aplica]

## Problemas de Seguridad

[Repetir estructura para cada problema]

## Problemas de Calidad y Mantenibilidad

[Repetir estructura para cada problema]

## Problemas Específicos de AEM / OSGi

[Repetir estructura para cada problema]

## Mejoras Sugeridas (No bloqueantes)

[Mejoras opcionales pero recomendadas]

## Puntos Positivos

[Qué está bien planteado o qué se podría rescatar]

## Recomendación Final

[Estado final y justificación]
```

---

### Paso 7: Usar Copilot para Mejorar el Review (20 min)

**Tarea:**
Usa Copilot para ayudarte a:

1. **Generar ejemplos de código corregido:**
```java
// Dado este código con problemas de seguridad:
// [pegar código problemático]
//
// Generar versión corregida que:
// - externalice secretos vía configuración OSGi
// - no use getAdministrativeResourceResolver()
// - use hashing seguro para passwords
// - maneje errores adecuadamente
// - siga buenas prácticas Java/AEM
```

2. **Identificar problemas adicionales:**
```java
// Revisa este servicio/servlet AEM y detecta:
// - vulnerabilidades de seguridad
// - problemas de concurrencia
// - malas prácticas OSGi
// - errores de diseño entre servlet y servicio
// [pegar código a revisar]
```

3. **Generar tests faltantes:**
```java
// Generar tests JUnit 5 para cubrir:
// - token expirado
// - token inválido
// - usuario duplicado
// - password incorrecto
// - concurrencia básica en registro/login
// - validación de inputs nulos o vacíos
```

---

## Código de la PR (Para Revisar)

### JwtAuthenticationServiceImpl.java

```java
package com.vasscompany.dummyproject.core.services.lab1_3CodeReview.impl;

import com.vasscompany.dummyproject.core.services.lab1_3CodeReview.JwtAuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component(service = JwtAuthenticationService.class, immediate = true)
public class JwtAuthenticationServiceImpl implements JwtAuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationServiceImpl.class);

    private static final String SECRET_KEY = "my-secret-key-12345";
    private static final long TOKEN_EXPIRATION_MILLIS = 24 * 60 * 60 * 1000L;

    private final Map<String, Map<String, String>> usersDb = new HashMap<>();

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Activate
    protected void activate() {
        Map<String, String> adminUser = new HashMap<>();
        adminUser.put("password", hashPassword("password"));
        adminUser.put("email", "admin@company.com");
        adminUser.put("role", "admin");
        usersDb.put("admin", adminUser);
    }

    @Override
    public String hashPassword(final String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (Exception e) {
            LOG.error("Error hashing password", e);
            return null;
        }
    }

    @Override
    public String createToken(final String username, final String password) {
        if (!usersDb.containsKey(username)) {
            return null;
        }

        Map<String, String> user = usersDb.get(username);
        if (!user.get("password").equals(hashPassword(password))) {
            return null;
        }

        return Jwts.builder()
                .claim("username", username)
                .claim("email", user.get("email"))
                .claim("role", user.get("role"))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_MILLIS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    @Override
    public Claims verifyToken(final String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOG.error("Invalid token: {}", token, e);
            return null;
        }
    }

    @Override
    public boolean registerUser(final String username, final String password, final String email) {
        if (usersDb.containsKey(username)) {
            return false;
        }

        try {
            ResourceResolver adminResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
            LOG.info("Registering user {} with password {} and email {}", username, password, email);

            Map<String, String> newUser = new HashMap<>();
            newUser.put("password", hashPassword(password));
            newUser.put("email", email);
            newUser.put("role", "user");
            usersDb.put(username, newUser);

            adminResolver.close();
            return true;
        } catch (Exception e) {
            LOG.error("Error registering user", e);
            return false;
        }
    }

    @Override
    public Map<String, Object> login(final String username, final String password) {
        String token = createToken(username, password);
        Map<String, Object> response = new HashMap<>();

        if (token != null) {
            response.put("token", token);
            response.put("user", username);
            response.put("message", "Login correcto");
        } else {
            response.put("error", "Credenciales inválidas para usuario " + username);
        }

        return response;
    }
}
```

### JwtAuthenticationServlet.java

```java
package com.vasscompany.dummyproject.core.servlets.lab1_3CodeReview;

import com.google.gson.Gson;
import com.vasscompany.dummyproject.core.services.lab1_3CodeReview.JwtAuthenticationService;
import io.jsonwebtoken.Claims;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(service = Servlet.class)
@SlingServletPaths("/bin/dummy/auth")
public class JwtAuthenticationServlet extends SlingAllMethodsServlet {

    @Reference
    private transient JwtAuthenticationService jwtAuthenticationService;

    @Override
    protected void doPost(final SlingHttpServletRequest request,
                          final SlingHttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        response.setContentType("application/json");

        if ("register".equals(action)) {
            boolean created = jwtAuthenticationService.registerUser(username, password, email);
            response.getWriter().write("{\"created\":" + created + "}");
            return;
        }

        Map<String, Object> loginResult = jwtAuthenticationService.login(username, password);
        response.getWriter().write(new Gson().toJson(loginResult));
    }

    @Override
    protected void doGet(final SlingHttpServletRequest request,
                         final SlingHttpServletResponse response) throws ServletException, IOException {

        String token = request.getParameter("token");
        Claims claims = jwtAuthenticationService.verifyToken(token);
        Map<String, Object> result = new HashMap<>();

        if (claims != null) {
            result.put("valid", true);
            result.put("claims", claims);
        } else {
            result.put("valid", false);
            result.put("reason", "Token inválido o expirado");
        }

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(result));
    }
}
```

### JwtAuthenticationServiceImplTest.java

```java
package com.vasscompany.dummyproject.core.services.lab1_3CodeReview.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationServiceImplTest {

    private final JwtAuthenticationServiceImpl service = new JwtAuthenticationServiceImpl();

    @Test
    void testCreateTokenSuccess() {
        service.activate();
        String token = service.createToken("admin", "password");
        assertNotNull(token);
    }

    @Test
    void testCreateTokenInvalidUser() {
        service.activate();
        String token = service.createToken("nonexistent", "password");
        assertNull(token);
    }

    @Test
    void testVerifyTokenSuccess() {
        service.activate();
        String token = service.createToken("admin", "password");
        assertNotNull(service.verifyToken(token));
    }

    @Test
    void testRegisterUser() {
        service.activate();
        boolean result = service.registerUser("newuser", "password123", "new@test.com");
        assertTrue(result);
    }
}
```

### Cambios en configuración OSGi

```json
// ui.config/src/main/content/jcr_root/apps/vass-university-dummyproject/osgiconfig/config/
// com.vasscompany.dummyproject.core.services.lab1_3CodeReview.impl.JwtAuthenticationServiceImpl.cfg.json
{
  "auth.enabled": true,
  "jwt.secret": "my-secret-key-12345"
}
```

---

## Entregables

Al finalizar el lab, debes entregar:

1. ✅ `code_review_lab_1.3.md` - Code review completo documentado
2. ✅ Tabla resumen de problemas encontrados
3. ✅ Ejemplos de código corregido para los problemas críticos
4. ✅ Recomendación final: `Aprobar` / `Aprobar con cambios` / `Rechazar`
5. ✅ Breve reflexión sobre cómo te ayudó Copilot durante el review

---

## Criterios de Aceptación

El lab se considera completo cuando:

- [ ] Se identificaron al menos 5 problemas de seguridad
- [ ] Se identificaron al menos 5 problemas de calidad o mantenibilidad
- [ ] Se identificaron problemas específicos de AEM/OSGi
- [ ] Todos los problemas están documentados con ubicación, severidad e impacto
- [ ] Se proporcionaron ejemplos de código corregido para problemas críticos
- [ ] Se usó el checklist de seguridad de forma sistemática
- [ ] Se utilizó Copilot para reforzar el review (detección, correcciones o tests)
- [ ] La recomendación final está justificada profesionalmente

---

## Evaluación

### Rúbrica de Evaluación

| Criterio | Excelente (4) | Bueno (3) | Satisfactorio (2) | Necesita Mejora (1) |
|----------|---------------|-----------|-------------------|---------------------|
| **Identificación de Problemas de Seguridad** | Identificó 8+ problemas relevantes, incluyendo riesgos AEM | Identificó 5-7 problemas relevantes | Identificó 3-4 problemas básicos | Identificó <3 problemas o irrelevantes |
| **Identificación de Problemas de Calidad** | Identificó 8+ problemas bien argumentados | Identificó 5-7 problemas relevantes | Identificó 3-4 problemas básicos | Identificó <3 problemas |
| **Detección de Riesgos AEM/OSGi** | Detectó correctamente problemas de servlet, config, OSGi y JCR | Detectó varios riesgos específicos | Detectó solo algunos | Apenas detectó riesgos AEM |
| **Documentación del Review** | Review excelente, muy claro y accionable | Review bueno y bien estructurado | Review correcto pero básico | Review superficial |
| **Uso de Copilot** | Uso estratégico para detectar y corregir | Uso bueno en algunas partes | Uso básico | Uso mínimo o inexistente |

**Puntuación mínima para aprobar: 12/20 (60%)**

---

## Problemas Clave a Identificar (Guía)

### Seguridad (deberías encontrar estos y más)

1. ❌ **`SECRET_KEY` hardcoded** en el servicio
2. ❌ **Secreto duplicado** en configuración OSGi
3. ❌ **Hashing inseguro** de passwords (`SHA-256` sin salt ni algoritmo adecuado)
4. ❌ **Password y datos sensibles en logs**
5. ❌ **Token completo en logs** al fallar validación
6. ❌ **Uso de `getAdministrativeResourceResolver()`**
7. ❌ **Servlet expuesto por `/bin/...` sin controles adicionales**
8. ❌ **Sin rate limiting ni controles anti brute force**
9. ❌ **Sin validación de entrada** (`null`, blank, email inválido, etc.)
10. ❌ **Mensajes de error demasiado informativos**

### Calidad / Mantenibilidad (deberías encontrar estos y más)

1. ❌ **Base de datos en memoria** en un servicio singleton OSGi
2. ❌ **Uso de `HashMap` mutable** con riesgo de concurrencia
3. ❌ **Tests incompletos** para un cambio de seguridad
4. ❌ **Tests acoplados al estado interno del servicio**
5. ❌ **Sin mocks / sin aislamiento adecuado**
6. ❌ **Falta validación de email y username**
7. ❌ **Uso de `Date` y APIs legacy** en lugar de `java.time`
8. ❌ **Responsabilidades mezcladas** en servicio y servlet
9. ❌ **Serialización manual / respuesta JSON poco robusta**
10. ❌ **Manejo de errores genérico e inconsistente**

### AEM / OSGi (deberías encontrar estos y más)

1. ❌ **Path-based servlet** en lugar de un binding más controlado si no está justificado
2. ❌ **Configuración sensible versionada con valor real**
3. ❌ **Uso inseguro del repositorio** para una operación que además ni persiste realmente
4. ❌ **Ausencia de Service User** si realmente hubiera acceso a JCR
5. ❌ **Diseño poco alineado con capas y patrones del proyecto**

---

## Tips y Ayuda

### Si te quedas atascado

1. **No encuentras problemas de seguridad:**
   - Revisa el checklist de sesión 1.3
   - Busca secretos hardcoded, logs sensibles, hashing débil, endpoints expuestos y uso inseguro del repositorio
   - Usa Copilot: `Identifica vulnerabilidades en este servicio OSGi y este servlet de AEM`

2. **No sabes cómo corregir un problema:**
   - Pide a Copilot una versión segura con configuración OSGi, Service User y separación servlet/servicio
   - Consulta OWASP y buenas prácticas de AEM
   - Piensa si el problema pertenece a código, configuración o diseño

3. **El review te queda superficial:**
   - Revisa línea por línea
   - Separa seguridad, calidad, concurrencia, AEM/OSGi y tests
   - Justifica cada hallazgo con impacto real

---

## Recursos Adicionales

- Revisa la sesión 1.3 sobre seguridad
- Consulta tu checklist interno de PR asistidas por IA
- Revisa OWASP Top 10 para autenticación y gestión de credenciales
- Contrasta el código con los patrones del proyecto dummy (servicios OSGi, configuración en `ui.config`, separación por capas)

---

**Versión**: 1.0  
**Adaptación**: Java / AEM like para `vass-university-dummyproject`
