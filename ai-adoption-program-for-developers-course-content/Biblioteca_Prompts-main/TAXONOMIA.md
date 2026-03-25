# Taxonomía de la Biblioteca de Prompts

## Áreas Principales

| Código | Área | Descripción |
|--------|------|-------------|
| DEV | Desarrollo | Desarrollo de software (Backend, Frontend, Full-stack) |
| QA | Testing y Calidad | Testing, calidad de código, code review |
| SEC | Seguridad | Seguridad, auditoría, vulnerabilidades |
| DOC | Documentación | Documentación técnica, onboarding |
| REF | Refactoring | Mejora de código, reducción de deuda técnica |
| ONB | Onboarding | Integración de nuevos desarrolladores |
| SUP | Sustentabilidad | Mantenimiento y evolución del programa |
| WKF | Workflows | Procesos y flujos de trabajo completos |

## Subáreas

| Código | Subárea | Descripción |
|--------|---------|-------------|
| BE | Backend | Desarrollo backend (APIs, servicios, lógica de negocio) |
| FE | Frontend | Desarrollo frontend (UI, componentes, interacciones) |
| FS | Full-stack | Desarrollo full-stack |
| UT | Unit Testing | Tests unitarios |
| IT | Integration Testing | Tests de integración |
| CO | Code Review | Revisión de código |
| IM | Implementación | Implementación de features, código nuevo |
| AU | Auditoría | Auditoría de código, seguridad, calidad |
| GE | Generación | Generación de código, documentación |
| AN | Análisis | Análisis de código, debugging, diagnóstico |
| DB | Debugging | Depuración y resolución de bugs |
| PL | Planificación | Planificación de features, arquitectura |
| RE | Refactoring | Refactorización de código |
| TE | Templates | Plantillas reutilizables |
| CH | Checklists | Listas de verificación |
| GP | Golden Paths | Guías paso a paso optimizadas |
| PB | Playbooks | Procesos y metodologías completas |
| PP | Promptpacks | Colecciones de prompts por tecnología |
| WF | Workflows | Flujos de trabajo agénticos |

## Niveles de Madurez

| Emoji | Nivel | Descripción |
|-------|-------|-------------|
| 🟢 | Básico | Guías introductorias, fundamentos, primeros pasos |
| 🟡 | Intermedio | Procesos completos, múltiples pasos, técnicas avanzadas |
| 🔴 | Avanzado | Agentes especializados, workflows complejos, optimizaciones |

## Tipos de Prompt

| Tipo | Descripción | Ejemplos |
|------|-------------|----------|
| Análisis | Analiza código, identifica problemas, diagnostica bugs | Code review, debugging, auditoría |
| Generación | Genera código, documentación, tests | Implementación, templates, promptpacks |
| Revisión | Revisa y valida código, calidad, seguridad | Checklists, code review |
| Guía | Proporciona procesos paso a paso, metodologías | Golden paths, playbooks, workflows |

## Compatibilidad

| Herramienta | Descripción |
|-------------|-------------|
| ChatGPT | Compatible con ChatGPT (OpenAI) |
| GitHub Copilot Chat | Compatible con GitHub Copilot Chat |
| Azure OpenAI | Compatible con Azure OpenAI |
| Claude | Compatible con Claude (Anthropic) |
| Universal | Compatible con todas las herramientas de IA |

## Mapeo de Tipos de Archivo a Clasificación

### Templates
- **Tipo**: Generación / Guía
- **Área**: Depende del contenido (DEV, QA, SEC, etc.)
- **Subárea**: Depende del contenido (IM, CO, DB, etc.)

### Agentes
- **Tipo**: Generación / Análisis
- **Área**: Depende del agente (DEV, QA, SEC, DOC, REF, ONB)
- **Subárea**: Generalmente GE (Generación) o AN (Análisis)
- **Nivel**: 🔴 Avanzado

### Checklists
- **Tipo**: Revisión
- **Área**: QA, SEC, DEV
- **Subárea**: CO (Code Review), AU (Auditoría)
- **Nivel**: 🟡 Intermedio

### Golden Paths
- **Tipo**: Guía
- **Área**: DEV, QA, REF
- **Subárea**: IM (Implementación), UT (Unit Testing), RE (Refactoring)
- **Nivel**: 🟡 Intermedio

### Playbooks
- **Tipo**: Guía
- **Área**: DEV, QA, REF, SUP
- **Subárea**: WF (Workflows), PB (Playbooks)
- **Nivel**: 🟡 Intermedio / 🔴 Avanzado

### Promptpacks
- **Tipo**: Generación
- **Área**: DEV
- **Subárea**: PP (Promptpacks)
- **Nivel**: 🟢 Básico / 🟡 Intermedio
- **Nota**: Específicos por tecnología (Python, Java, JavaScript, etc.)

## Ejemplos de IDs

- `PR-DEV-IM-001` - Desarrollo, Implementación, primer prompt
- `PR-QA-CO-001` - Testing/Calidad, Code Review, primer prompt
- `PR-SEC-AU-001` - Seguridad, Auditoría, primer prompt
- `PR-DOC-ON-001` - Documentación, Onboarding, primer prompt
- `PR-REF-RE-001` - Refactoring, Refactoring, primer prompt
- `PR-WKF-WF-001` - Workflows, Workflows, primer prompt
