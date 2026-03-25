# Template: Nuevo Prompt

Este template debe usarse al crear un nuevo artefacto para la biblioteca de prompts corporativa.

## Instrucciones

1. Copia este template
2. Completa todos los metadatos en el frontmatter
3. Reemplaza el contenido de ejemplo con tu artefacto
4. Asigna un ID único siguiendo la [taxonomía](TAXONOMIA.md)
5. Coloca el archivo en la carpeta apropiada
6. Actualiza los índices correspondientes

---

```markdown
---
ID: PR-[ÁREA]-[SUBÁREA]-[NÚMERO]
Área / Práctica: [Desarrollo / Testing y Calidad / Seguridad / Documentación / Refactoring / Onboarding / Sustentabilidad / Workflows]
Subárea: [Ver TAXONOMIA.md para opciones]
Nivel de madurez: [🟢 Básico / 🟡 Intermedio / 🔴 Avanzado]
Tipo de prompt: [Análisis / Generación / Revisión / Guía]
Compatibilidad: [ChatGPT, GitHub Copilot Chat / Universal / etc.]
Autor: [Nombre / Equipo]
Versión: v1.0
Fecha: YYYY-MM-DD
Estado: [Draft / Aprobado / Deprecado]
---

# [Título del Artefacto]

## Propósito

[Descripción clara del propósito del artefacto. ¿Qué problema resuelve? ¿Cuándo usarlo?]

## Cuándo Usar

- ✅ [Situación 1 donde usar este artefacto]
- ✅ [Situación 2 donde usar este artefacto]
- ❌ [Situación donde NO usar este artefacto]

---

## [Sección Principal 1]

[Contenido del artefacto...]

### Subsección

[Detalles adicionales...]

---

## [Sección Principal 2]

[Contenido adicional...]

---

## Ejemplos de Uso

### Ejemplo 1: [Descripción]

```
[Ejemplo concreto de uso]
```

### Ejemplo 2: [Descripción]

```
[Otro ejemplo]
```

---

## Mejores Prácticas

1. [Práctica 1]
2. [Práctica 2]
3. [Práctica 3]

---

## Recursos Relacionados

- [Enlace a artefacto relacionado 1](ruta/archivo.md)
- [Enlace a artefacto relacionado 2](ruta/archivo.md)

---

**Versión**: v1.0  
**Última actualización**: YYYY-MM-DD
```

---

## Guía para Completar Metadatos

### ID

Formato: `PR-[ÁREA]-[SUBÁREA]-[NÚMERO]`

- Consulta [TAXONOMIA.md](TAXONOMIA.md) para códigos de área y subárea
- El número debe ser secuencial dentro de la categoría
- Ejemplo: `PR-DEV-IM-002` (segundo prompt de Desarrollo-Implementación)

### Área / Práctica

Selecciona una de las áreas principales:
- Desarrollo
- Testing y Calidad
- Seguridad
- Documentación
- Refactoring
- Onboarding
- Sustentabilidad
- Workflows

### Subárea

Consulta [TAXONOMIA.md](TAXONOMIA.md) para la lista completa de subáreas disponibles.

### Nivel de Madurez

- **🟢 Básico**: Guías introductorias, fundamentos, primeros pasos
- **🟡 Intermedio**: Procesos completos, múltiples pasos, técnicas avanzadas
- **🔴 Avanzado**: Agentes especializados, workflows complejos, optimizaciones

### Tipo de Prompt

- **Análisis**: Analiza código, identifica problemas, diagnostica bugs
- **Generación**: Genera código, documentación, tests
- **Revisión**: Revisa y valida código, calidad, seguridad
- **Guía**: Proporciona procesos paso a paso, metodologías

### Compatibilidad

Lista las herramientas de IA compatibles:
- `ChatGPT, GitHub Copilot Chat` - Para prompts específicos
- `Universal` - Para checklists y guías generales
- `ChatGPT, GitHub Copilot Chat, Azure OpenAI` - Para múltiples herramientas

### Estado

- **Draft**: En desarrollo, no listo para uso
- **Aprobado**: Listo para uso en producción
- **Deprecado**: Obsoleto, no usar (mantener para referencia)

## Ubicación del Archivo

Coloca el archivo en la carpeta apropiada según el tipo:

- **Templates**: `templates/prompts/` o `templates/workflows/`
- **Agentes**: `agentes/`
- **Checklists**: `checklists/`
- **Golden Paths**: `golden-paths/`
- **Playbooks**: `playbooks/` o `playbooks/workflows/`
- **Promptpacks**: `promptpacks/[tecnologia]/`

## Actualización de Índices

Después de crear el archivo, actualiza:

1. [INDEX.md](INDEX.md) - Agrega el nuevo artefacto a la tabla completa
2. Índice por área - Agrega a `indices/[area].md`
3. Índice por tipo - Agrega a `indices/[tipo].md`

---

**Nota**: Este template es solo una guía. Adapta la estructura según las necesidades específicas de tu artefacto.
