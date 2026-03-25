# Biblioteca de Prompts

Bienvenido a la biblioteca de prompts de VASS University. Esta biblioteca contiene artefactos reutilizables organizados y catalogados para facilitar el desarrollo asistido por IA.

## ¿Qué es esta biblioteca?

Esta biblioteca contiene **36 artefactos reutilizables** organizados en diferentes categorías:

- **Templates**: Plantillas para generar prompts efectivos
- **Agentes**: Configuraciones de agentes especializados
- **Checklists**: Listas de verificación para calidad y seguridad
- **Golden Paths**: Guías paso a paso optimizadas
- **Playbooks**: Procesos y metodologías completas
- **Promptpacks**: Colecciones de prompts por tecnología

## Estructura

```
artefactos-reutilizables/
├── INDEX.md                    # Índice maestro
├── TAXONOMIA.md                # Taxonomía de clasificación
├── TEMPLATE-NUEVO-PROMPT.md    # Template para nuevos prompts
├── README.md                   # Este archivo
├── agentes/                    # Agentes especializados
├── checklists/                 # Listas de verificación
├── golden-paths/               # Guías paso a paso
├── playbooks/                  # Procesos completos
├── promptpacks/                # Prompts por tecnología
├── templates/                  # Templates reutilizables
└── indices/                    # Índices por área y tipo
```

## Navegación

### Por Área de Práctica

- [Desarrollo](indices/desarrollo.md) - 14 artefactos
- [Testing y Calidad](indices/testing.md) - 6 artefactos
- [Seguridad](indices/seguridad.md) - 2 artefactos
- [Documentación](indices/documentacion.md) - 1 artefacto
- [Refactoring](indices/refactoring.md) - 5 artefactos
- [Onboarding](indices/onboarding.md) - 2 artefactos
- [Sustentabilidad](indices/sustentabilidad.md) - 1 artefacto
- [Workflows](indices/workflows.md) - 2 artefactos

### Por Tipo de Artefacto

- [Templates](indices/templates.md) - 10 artefactos
- [Agentes](indices/agentes.md) - 6 artefactos
- [Checklists](indices/checklists.md) - 3 artefactos
- [Golden Paths](indices/golden-paths.md) - 4 artefactos
- [Playbooks](indices/playbooks.md) - 5 artefactos
- [Promptpacks](indices/promptpacks.md) - 8 artefactos

### Índice Completo

Ver el [índice maestro](INDEX.md) para una lista completa de todos los artefactos.

## Metadatos

Cada artefacto incluye metadatos estructurados al inicio del archivo:

```yaml
---
ID: PR-DEV-IM-001
Área / Práctica: Desarrollo
Subárea: Implementación
Nivel de madurez: 🟡 Intermedio
Tipo de prompt: Generación
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---
```

### Campos de Metadatos

- **ID**: Identificador único del artefacto (formato: `PR-[ÁREA]-[SUBÁREA]-[NÚMERO]`)
- **Área / Práctica**: Área principal (Desarrollo, Testing, Seguridad, etc.)
- **Subárea**: Subcategoría específica (Implementación, Code Review, etc.)
- **Nivel de madurez**: 🟢 Básico, 🟡 Intermedio, 🔴 Avanzado
- **Tipo de prompt**: Análisis, Generación, Revisión, Guía
- **Compatibilidad**: Herramientas de IA compatibles
- **Autor**: Creador o equipo responsable
- **Versión**: Versión del artefacto
- **Fecha**: Fecha de creación o última actualización
- **Estado**: Draft, Aprobado, Deprecado

## Cómo Usar

### 1. Buscar un Artefacto

- Usa el [índice maestro](INDEX.md) para ver todos los artefactos
- Navega por [área](indices/) o [tipo](indices/) según tu necesidad
- Consulta la [taxonomía](TAXONOMIA.md) para entender la clasificación

### 2. Usar un Artefacto

1. Abre el archivo del artefacto
2. Lee los metadatos para entender su propósito y compatibilidad
3. Copia y adapta el contenido según tu necesidad
4. Sigue las instrucciones y ejemplos incluidos

### 3. Contribuir un Nuevo Artefacto

1. Usa el [template para nuevos prompts](TEMPLATE-NUEVO-PROMPT.md)
2. Completa todos los metadatos
3. Asigna un ID único siguiendo la [taxonomía](TAXONOMIA.md)
4. Coloca el archivo en la carpeta apropiada
5. Actualiza los índices correspondientes

## Sistema de IDs

Los IDs siguen el formato: `PR-[ÁREA]-[SUBÁREA]-[NÚMERO]`

**Ejemplos:**
- `PR-DEV-IM-001` - Desarrollo, Implementación, primer prompt
- `PR-QA-CO-001` - Testing/Calidad, Code Review, primer prompt
- `PR-SEC-AU-001` - Seguridad, Auditoría, primer prompt

Ver la [taxonomía](TAXONOMIA.md) para códigos completos de áreas y subáreas.

## Niveles de Madurez

- **🟢 Básico**: Guías introductorias, fundamentos, primeros pasos
- **🟡 Intermedio**: Procesos completos, múltiples pasos, técnicas avanzadas
- **🔴 Avanzado**: Agentes especializados, workflows complejos, optimizaciones

## Compatibilidad

Los artefactos son compatibles con diferentes herramientas de IA:

- **ChatGPT**: Compatible con ChatGPT (OpenAI)
- **GitHub Copilot Chat**: Compatible con GitHub Copilot Chat
- **Azure OpenAI**: Compatible con Azure OpenAI
- **Universal**: Compatible con todas las herramientas de IA

## Mantenimiento

### Actualización de Artefactos

1. Actualiza la versión en los metadatos
2. Actualiza la fecha
3. Documenta los cambios en el archivo
4. Actualiza el índice maestro si cambia la clasificación

### Deprecación

Si un artefacto está obsoleto:

1. Cambia el estado a "Deprecado" en los metadatos
2. Agrega una nota explicando por qué está deprecado
3. Sugiere alternativas si existen
4. Mantén el archivo para referencia histórica

## Recursos Relacionados

- [Taxonomía](TAXONOMIA.md) - Sistema de clasificación completo
- [Template para Nuevos Prompts](TEMPLATE-NUEVO-PROMPT.md) - Guía para crear nuevos artefactos
- [Índice Maestro](INDEX.md) - Lista completa de artefactos

## Contacto y Soporte

Para preguntas, sugerencias o contribuciones:
- Equipo AI-DLC
- Consulta los metadatos de cada artefacto para el autor específico

---

**Versión de la biblioteca**: v1.0  
**Última actualización**: 2026-01-15  
**Total de artefactos**: 36
