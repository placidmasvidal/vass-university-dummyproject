# Convenciones de Código y Estilo - VASS University Dummy Project

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
