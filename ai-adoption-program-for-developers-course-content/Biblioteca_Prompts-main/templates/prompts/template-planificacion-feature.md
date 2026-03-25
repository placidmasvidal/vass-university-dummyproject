---
ID: PR-DEV-PL-001
Área / Práctica: Desarrollo
Subárea: Planificación
Nivel de madurez: 🟡 Intermedio
Tipo de prompt: Análisis
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Template: Prompt de Planificación de Feature

## Uso
Usar este template para generar prompts de planificación antes de implementar features complejas.

## Template

```
Como arquitecto de software senior, analiza este ticket y genera plan detallado de implementación.

TICKET:
[Título del ticket]
[Descripción completa]

REQUISITOS FUNCIONALES:
- [Requisito 1]
- [Requisito 2]
- [Requisito 3]

REQUISITOS NO FUNCIONALES:
- Performance: [requisitos]
- Seguridad: [requisitos]
- Escalabilidad: [requisitos]

CONTEXTO DEL PROYECTO:
- Stack tecnológico: [tecnologías]
- Arquitectura: [patrón arquitectónico]
- Patrones usados: [patrones específicos]
- Estructura actual: [descripción breve]
- Archivos relacionados: [lista de archivos clave]

RESTRICCIONES:
- [Restricción 1]
- [Restricción 2]

GENERA PLAN DETALLADO CON:

1. ANÁLISIS DE REQUISITOS
   - Descomposición de requisitos
   - Dependencias identificadas
   - Casos edge identificados

2. DISEÑO DE SOLUCIÓN
   - Arquitectura propuesta
   - Componentes/Clases necesarias
   - Interfaces/Contratos
   - Flujo de datos

3. PLAN DE IMPLEMENTACIÓN
   - Paso 1: [Descripción detallada]
   - Paso 2: [Descripción detallada]
   - Paso 3: [Descripción detallada]
   - [Agregar más pasos según complejidad]

4. PLAN DE TESTING
   - Tests unitarios necesarios
   - Tests de integración necesarios
   - Casos edge a testear
   - Objetivo de cobertura

5. PLAN DE DOCUMENTACIÓN
   - Documentación de código necesaria
   - Documentación de API (si aplica)
   - Actualizaciones a README/docs

6. IDENTIFICACIÓN DE RIESGOS
   - Riesgos técnicos
   - Mitigaciones propuestas

7. ESTIMACIÓN
   - Tiempo estimado por paso
   - Tiempo total estimado

Formato de salida: Markdown estructurado con secciones claras.
```

---

## Ejemplo de Uso

```
Como arquitecto de software senior, analiza este ticket y genera plan detallado de implementación.

TICKET:
Agregar autenticación JWT al sistema

REQUISITOS FUNCIONALES:
- Login con email/password
- Generación de token JWT
- Validación de token en requests
- Refresh token mechanism

REQUISITOS NO FUNCIONALES:
- Performance: <100ms para validación
- Seguridad: Tokens expiran en 1 hora
- Escalabilidad: Sin estado en servidor

CONTEXTO DEL PROYECTO:
- Stack: Python 3.10+, FastAPI, PostgreSQL
- Arquitectura: Clean Architecture con capas
- Patrones: Repository, Dependency Injection
- Estructura: app/domain, app/application, app/infrastructure

[Continuar con template...]
```

---

**Versión**: 1.0  
**Última actualización**: 2026-01-15
