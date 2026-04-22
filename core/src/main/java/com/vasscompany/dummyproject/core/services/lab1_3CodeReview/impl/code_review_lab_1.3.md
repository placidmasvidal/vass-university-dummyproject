# Code Review - Lab 1.3
## PR: Agregar módulo de autenticación JWT para servlet interno

## Resumen ejecutivo
Tras revisar la PR, mi conclusión es que **no la aprobaría en su estado actual** y pediría cambios antes de mergearla.

La idea general se entiende: se ha querido montar una autenticación JWT sencilla con un servicio OSGi, un servlet y unos tests mínimos. Pero precisamente por tocar autenticación, gestión de credenciales, tokens y exposición HTTP, el listón aquí tiene que ser alto, y ahora mismo la implementación tiene varios problemas serios de seguridad y además varias malas prácticas claras de AEM.

A nivel general, lo que he visto es esto:

- **Problemas críticos**: 5
- **Problemas de seguridad**: 9
- **Problemas de calidad / mantenibilidad**: 8
- **Problemas específicos de AEM / OSGi**: 5

Mi recomendación final sería: **Rechazar / Requiere cambios importantes antes de merge**.

---

## Tabla resumen de hallazgos

| # | Archivo | Severidad | Tipo | Hallazgo |
|---|---|---|---|---|
| 1 | `JwtAuthenticationServiceImpl.java` | Crítico | Seguridad | `SECRET_KEY` hardcoded en código |
| 2 | `JwtAuthenticationServiceImpl.java` | Crítico | Seguridad | Hashing de password inseguro (`SHA-256` sin salt) |
| 3 | `JwtAuthenticationServiceImpl.java` | Crítico | Seguridad / AEM | Uso de `getAdministrativeResourceResolver()` |
| 4 | `JwtAuthenticationServiceImpl.java` | Crítico | Seguridad | Se logan password y email en claro |
| 5 | `JwtAuthenticationServiceImpl.java` | Crítico | Seguridad | Se loga el token completo al fallar validación |
| 6 | `JwtAuthenticationServlet.java` | Alto | Seguridad | Servlet expuesto por `/bin/...` sin controles adicionales |
| 7 | `JwtAuthenticationServiceImpl.cfg.json` | Alto | Seguridad | Secreto JWT versionado en `ui.config` |
| 8 | `JwtAuthenticationServiceImpl.java` | Alto | Calidad | Base de datos en memoria en un servicio singleton |
| 9 | `JwtAuthenticationServiceImpl.java` | Alto | Calidad | `HashMap` mutable sin control de concurrencia |
| 10 | `JwtAuthenticationServlet.java` | Medio | Calidad | Falta validación de inputs |
| 11 | `JwtAuthenticationServiceImplTest.java` | Alto | Testing | Tests demasiado escasos para un cambio de seguridad |
| 12 | `JwtAuthenticationServiceImpl.java` | Medio | Seguridad | Mensajes de error demasiado informativos |

---

## Problemas críticos (bloqueantes)

### 1. Secreto JWT hardcoded en código
- **Archivo**: `JwtAuthenticationServiceImpl.java`
- **Severidad**: Crítico
- **Descripción**: El `SECRET_KEY` está metido directamente en el código fuente.
- **Impacto**: Cualquiera con acceso al repo, a un artefacto o incluso a un log accidental podría llegar a firmar tokens válidos.
- **Recomendación**: Externalizarlo a configuración OSGi segura y no dejar nunca un valor real en código ni en ficheros versionados.

**Ejemplo orientativo de corrección:**
```java
@ObjectClassDefinition(name = "JWT Authentication Service")
public @interface Config {
    @AttributeDefinition(name = "JWT secret")
    String jwt_secret();
}

@Activate
protected void activate(final Config config) {
    this.secretKey = config.jwt_secret();
}
```

---

### 2. Hashing de contraseñas inseguro
- **Archivo**: `JwtAuthenticationServiceImpl.java`
- **Severidad**: Crítico
- **Descripción**: Se usa `SHA-256` directo para el password, sin salt y sin un algoritmo específico para contraseñas.
- **Impacto**: Muy vulnerable a ataques de diccionario, rainbow tables y cracking offline.
- **Recomendación**: Usar BCrypt, PBKDF2 o Argon2. Para passwords no vale cualquier hash genérico.

**Ejemplo orientativo de corrección:**
```java
String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
boolean valid = BCrypt.checkpw(rawPassword, hashedPassword);
```

---

### 3. Uso de `getAdministrativeResourceResolver()`
- **Archivo**: `JwtAuthenticationServiceImpl.java`
- **Severidad**: Crítico
- **Descripción**: Se usa una sesión administrativa para registrar usuarios.
- **Impacto**: Es una mala práctica grave en AEM y rompe el principio de mínimo privilegio.
- **Recomendación**: Si realmente hubiera que acceder al repositorio, usar un **Service User** con subservice específico y permisos mínimos.

**Ejemplo orientativo de corrección:**
```java
Map<String, Object> authInfo = new HashMap<>();
authInfo.put(ResourceResolverFactory.SUBSERVICE, "jwt-auth-service");

try (ResourceResolver serviceResolver = resourceResolverFactory.getServiceResourceResolver(authInfo)) {
    // lógica con permisos mínimos
}
```

---

### 4. Logging de password y email en claro
- **Archivo**: `JwtAuthenticationServiceImpl.java`
- **Severidad**: Crítico
- **Descripción**: En el registro de usuario se están dejando en logs el username, el password y el email.
- **Impacto**: Fuga directa de credenciales y de datos personales.
- **Recomendación**: No logar nunca passwords ni tokens ni datos sensibles. Como mucho, un log mínimo y sin contenido sensible.

**Ejemplo orientativo de corrección:**
```java
LOG.info("Registering user {}", username);
```

---

### 5. Logging del token completo cuando falla la validación
- **Archivo**: `JwtAuthenticationServiceImpl.java`
- **Severidad**: Crítico
- **Descripción**: Ante token inválido se registra el token completo en el log.
- **Impacto**: Si ese token sigue siendo reutilizable o alguien accede a logs, el problema se amplía bastante.
- **Recomendación**: No registrar nunca el token entero. Si hiciera falta trazar algo, como mucho un identificador parcial o un hash/truncado.

**Ejemplo orientativo de corrección:**
```java
LOG.warn("Invalid token received");
```

---

## Problemas de seguridad

### 6. Secreto duplicado y versionado en `ui.config`
- **Archivo**: `JwtAuthenticationServiceImpl.cfg.json`
- **Severidad**: Alto
- **Descripción**: Además de estar hardcodeado en Java, el secreto también aparece en el `.cfg.json` con un valor realista.
- **Impacto**: Se incrementa el riesgo de exposición en Git, paquetes y despliegues.
- **Recomendación**: No versionar secretos reales. Dejar plantillas o gestionar el valor fuera del código.

### 7. No hay protección frente a brute force
- **Archivo**: `JwtAuthenticationServlet.java` / diseño global
- **Severidad**: Alto
- **Descripción**: No se ve ningún control de rate limiting, bloqueo temporal o protección equivalente.
- **Impacto**: El endpoint es vulnerable a ataques de fuerza bruta.
- **Recomendación**: Añadir limitación por IP/usuario o, mejor aún, no reinventar autenticación si ya existe un mecanismo corporativo.

### 8. Mensajes de error demasiado informativos
- **Archivo**: `JwtAuthenticationServiceImpl.java`
- **Severidad**: Medio
- **Descripción**: En login fallido se devuelve `Credenciales inválidas para usuario X`.
- **Impacto**: Facilita enumeración de usuarios.
- **Recomendación**: Responder siempre con mensajes genéricos.

### 9. El JWT incluye más datos de los necesarios
- **Archivo**: `JwtAuthenticationServiceImpl.java`
- **Severidad**: Medio
- **Descripción**: El token mete `email` y `role` sin que esté clara la necesidad mínima.
- **Impacto**: Cuanta más información viaje en el token, más exposición si se filtra.
- **Recomendación**: Incluir solo claims imprescindibles.

### 10. No hay validación de entrada
- **Archivo**: `JwtAuthenticationServlet.java`
- **Severidad**: Medio
- **Descripción**: No se validan `null`, vacíos, formato de email, etc.
- **Impacto**: Abre la puerta a errores, respuestas inconsistentes y posibles problemas futuros si esto evolucionara a persistencia real.
- **Recomendación**: Validar inputs antes de llamar al servicio y devolver `400 Bad Request` cuando corresponda.

---

## Problemas de calidad y mantenibilidad

### 11. Base de datos en memoria dentro de un servicio OSGi singleton
- **Archivo**: `JwtAuthenticationServiceImpl.java`
- **Severidad**: Alto
- **Descripción**: `usersDb` es un `Map` en memoria dentro del propio servicio.
- **Impacto**: Los datos no persisten entre reinicios y el comportamiento sería inconsistente entre instancias.
- **Recomendación**: O bien dejar claro que es una demo puramente aislada, o separar completamente la lógica demo de cualquier simulación de entorno real.

### 12. Riesgo de concurrencia
- **Archivo**: `JwtAuthenticationServiceImpl.java`
- **Severidad**: Alto
- **Descripción**: El servicio es singleton y usa un `HashMap` mutable compartido.
- **Impacto**: Puede haber condiciones de carrera en escenarios concurrentes.
- **Recomendación**: Evitar estado mutable compartido o usar estructuras seguras y diseño thread-safe.

### 13. Responsabilidades mezcladas
- **Archivo**: `JwtAuthenticationServiceImpl.java` y `JwtAuthenticationServlet.java`
- **Severidad**: Medio
- **Descripción**: Se mezclan autenticación, pseudo-persistencia, logging, construcción de respuestas y lógica HTTP.
- **Impacto**: Hace el código más frágil y más difícil de probar y mantener.
- **Recomendación**: Separar mejor capa HTTP, capa de servicio y modelo de respuesta.

### 14. Manejo de errores inconsistente
- **Archivo**: varios
- **Severidad**: Medio
- **Descripción**: Unos métodos devuelven `null`, otros `boolean`, otros un `Map` con mensaje de error.
- **Impacto**: El contrato es poco claro y fácil de romper.
- **Recomendación**: Homogeneizar respuestas y errores.

### 15. Construcción de JSON demasiado manual
- **Archivo**: `JwtAuthenticationServlet.java`
- **Severidad**: Bajo
- **Descripción**: Hay respuestas hechas a mano y otras con `Gson`, sin un modelo común.
- **Impacto**: Inconsistencia y menor mantenibilidad.
- **Recomendación**: Usar DTOs claros para request/response.

### 16. Uso de APIs legacy (`Date`)
- **Archivo**: `JwtAuthenticationServiceImpl.java`
- **Severidad**: Bajo
- **Descripción**: Se usa `Date` con cálculo manual de expiración.
- **Impacto**: No es lo más limpio ni lo más moderno.
- **Recomendación**: Preferir `java.time`.

---

## Problemas específicos de AEM / OSGi

### 17. Servlet expuesto por path absoluto `/bin/...`
- **Archivo**: `JwtAuthenticationServlet.java`
- **Severidad**: Alto
- **Descripción**: Se expone como path servlet global sin una justificación fuerte.
- **Impacto**: Aumenta la superficie de ataque y es menos controlado que otras opciones.
- **Recomendación**: Si el caso lo permite, preferir binding por `resourceType`/selector. Si tiene que ser por path, justificarlo y endurecer controles.

### 18. Falta el patrón correcto de Service User
- **Archivo**: diseño global
- **Severidad**: Alto
- **Descripción**: Si de verdad hubiera acceso al repo, falta subservice, mapping y permisos mínimos.
- **Impacto**: No sigue el patrón estándar de AEM para acceso seguro.
- **Recomendación**: Definir service user y permisos mínimos.

### 19. La configuración sensible no debería estar así en `ui.config`
- **Archivo**: `.cfg.json`
- **Severidad**: Alto
- **Descripción**: Para un valor tan sensible, dejarlo de esta forma versionada no es buena práctica.
- **Impacto**: Riesgo de exposición en código y despliegues.
- **Recomendación**: Tratar los secretos fuera del código versionado.

### 20. Los tests no reflejan un escenario real AEM
- **Archivo**: `JwtAuthenticationServiceImplTest.java`
- **Severidad**: Medio
- **Descripción**: La clase se instancia a mano y no se mockean dependencias AEM como `ResourceResolverFactory`.
- **Impacto**: Los tests dan una falsa sensación de cobertura.
- **Recomendación**: Usar Mockito o AEM Mocks según el caso.

---

## Revisión de tests

Los tests actuales me parecen claramente insuficientes para un cambio que toca autenticación.

Ahora mismo cubren solo lo más básico:
- creación de token correcta,
- usuario inexistente,
- verificación básica,
- registro simple.

Pero faltan casos importantes como:
- token expirado,
- token inválido o manipulado,
- registro duplicado,
- password incorrecta,
- inputs nulos o vacíos,
- comportamiento cuando falla el acceso al repositorio,
- escenarios concurrentes,
- comprobación de que no se logan datos sensibles.

Aquí yo pediría ampliar bastante la batería de tests antes de aceptar la PR.

---

## Puntos positivos

No todo está mal. También hay algunas cosas rescatables:

- La intención del cambio se entiende rápido.
- El servicio y el servlet están separados al menos a nivel de clases, aunque luego la separación se quede corta.
- Como ejercicio didáctico, el ejemplo está bien porque concentra muchos smells típicos de código generado con IA sin revisión suficiente.

---

## Recomendación final

Mi recomendación sería **rechazar la PR en su estado actual**.

No lo digo porque la idea sea mala, sino porque al tocar autenticación el margen para "ya lo arreglaremos luego" aquí es muy pequeño. Hay varios problemas bloqueantes: secretos expuestos, hashing inseguro, uso de sesión administrativa, logs con datos sensibles y una exposición HTTP poco controlada.

Antes de plantear el merge yo pediría, como mínimo:

1. sacar secretos y tratarlos correctamente,
2. rehacer el tratamiento de passwords,
3. eliminar por completo `getAdministrativeResourceResolver()`,
4. limpiar logs y mensajes de error,
5. revisar la exposición del servlet,
6. reforzar tests,
7. replantear si esta autenticación custom tiene sentido en AEM o si debería apoyarse en mecanismos ya existentes.

---

## Reflexión personal sobre el review asistido con IA

En este laboratorio, Copilot o una IA pueden ayudar bastante a detectar smells, proponer versiones corregidas o incluso sugerir tests que faltan. Pero justamente este ejercicio me parece buen ejemplo de por qué **no basta con aceptar lo que genera la IA porque “parece que funciona”**.

Mi sensación aquí es que la IA puede acelerar, sí, pero en temas de autenticación, seguridad y patrones AEM el review humano sigue siendo imprescindible. Donde más útil la veo es como apoyo para contrastar ideas, no como sustituto del criterio técnico.
