---
ID: PR-QA-PB-001
Área / Práctica: Testing y Calidad
Subárea: Playbooks
Nivel de madurez: 🟡 Intermedio
Tipo de prompt: Guía
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Playbook: Code Review Asistido por IA

## Propósito

Guía completa para realizar code reviews efectivos usando IA (GitHub Copilot, Azure OpenAI) para identificar problemas, sugerir mejoras y validar calidad.

## Cuándo Usar

- ✅ Revisar Pull Requests generadas con IA
- ✅ Revisar código propio antes de commit
- ✅ Revisar código de compañeros
- ✅ Auditoría de calidad y seguridad

---

## Proceso de Code Review Asistido

### Fase 1: Preparación (5 min)

#### 1.1 Contexto Inicial
- [ ] Lee descripción completa de la PR
- [ ] Revisa archivos cambiados
- [ ] Entiende objetivo del cambio
- [ ] Identifica scope y impacto

#### 1.2 Configuración de Herramientas
- [ ] Abre PR en VS Code
- [ ] Abre archivos relacionados para contexto
- [ ] Configura herramientas de análisis (linters, security scanners)

---

### Fase 2: Revisión Estructural (10 min)

#### 2.1 Revisión Rápida con IA

**Usa Copilot Chat o Azure OpenAI:**

```
Revisar esta Pull Request y proporcionar análisis estructural:
- ¿La estructura del código es apropiada?
- ¿Sigue patrones del proyecto?
- ¿Hay código duplicado?
- ¿La organización es clara?

Archivos principales cambiados:
[Listar archivos]
```

#### 2.2 Checklist Estructural

- [ ] Código bien organizado y estructurado
- [ ] Sigue patrones del proyecto
- [ ] No hay duplicación innecesaria
- [ ] Separación de responsabilidades clara
- [ ] Arquitectura apropiada para el cambio

---

### Fase 3: Revisión de Seguridad (15 min)

#### 3.1 Análisis de Seguridad con IA

**Prompt para IA:**

```
Analizar código por vulnerabilidades de seguridad comunes:
- Credenciales o secretos hardcoded
- SQL injection, XSS, CSRF risks
- Validación de inputs
- Manejo seguro de errores
- Exposición de información sensible

Código a revisar:
[Pegar código o referenciar archivos]

Proporcionar:
1. Lista de vulnerabilidades encontradas
2. Severidad de cada una
3. Recomendaciones de corrección
```

#### 3.2 Checklist de Seguridad (Ver `checklists/seguridad-ia.md`)

- [ ] Sin credenciales/secretos hardcoded
- [ ] Sin datos sensibles expuestos
- [ ] Validación de inputs adecuada
- [ ] Manejo de errores seguro
- [ ] No hay vulnerabilidades comunes (OWASP Top 10)

---

### Fase 4: Revisión de Calidad (20 min)

#### 4.1 Análisis de Calidad con IA

**Prompt para IA:**

```
Revisar calidad del código:
- Legibilidad y mantenibilidad
- Nombres descriptivos
- Complejidad ciclomática
- Documentación adecuada
- Tests y cobertura
- Manejo de casos edge

Código:
[Pegar código]

Proporcionar:
1. Problemas de calidad identificados
2. Sugerencias de mejora
3. Ejemplos de código mejorado
```

#### 4.2 Checklist de Calidad

- [ ] Código legible y bien estructurado
- [ ] Nombres descriptivos
- [ ] Documentación adecuada (docstrings, comentarios)
- [ ] Type hints incluidos (si aplica)
- [ ] Tests presentes y pasan
- [ ] Cobertura adecuada
- [ ] Maneja casos edge

---

### Fase 5: Revisión de Lógica y Bugs (15 min)

#### 5.1 Identificación de Bugs con IA

**Prompt para IA:**

```
Identificar posibles bugs y problemas lógicos:
- Lógica incorrecta
- Casos edge no manejados
- Race conditions potenciales
- Memory leaks
- Performance issues

Código:
[Pegar código específico]

Proporcionar:
1. Bugs potenciales
2. Casos edge no cubiertos
3. Problemas de performance
4. Recomendaciones
```

#### 5.2 Checklist de Lógica

- [ ] Lógica correcta y completa
- [ ] Casos edge manejados
- [ ] Validaciones apropiadas
- [ ] Manejo de errores robusto
- [ ] No hay bugs obvios
- [ ] Performance aceptable

---

### Fase 6: Revisión de Tests (10 min)

#### 6.1 Análisis de Tests con IA

**Prompt para IA:**

```
Analizar tests unitarios:
- ¿Cubren funcionalidad principal?
- ¿Hay casos edge cubiertos?
- ¿Tests son independientes?
- ¿Usan mocks apropiadamente?
- ¿Cobertura es adecuada?

Tests:
[Pegar código de tests]

Proporcionar:
1. Gaps en cobertura
2. Tests faltantes recomendados
3. Mejoras sugeridas
```

#### 6.2 Checklist de Tests

- [ ] Tests cubren funcionalidad principal
- [ ] Casos edge incluidos
- [ ] Tests independientes y determinísticos
- [ ] Mocks usados apropiadamente
- [ ] Cobertura >80% (ideal >90%)
- [ ] Tests son mantenibles

---

### Fase 7: Documentación del Review (10 min)

#### 7.1 Estructurar Feedback

**Template de Code Review:**

```markdown
# Code Review: [Título PR]

## Resumen
- Estado: [Aprobado / Aprobar con cambios / Requiere cambios]
- Problemas críticos: [número]
- Problemas de seguridad: [número]
- Problemas de calidad: [número]

## Problemas Críticos (Bloqueantes)

### [Título]
- **Archivo**: `archivo.py:45`
- **Severidad**: Crítico
- **Descripción**: [Detalle]
- **Recomendación**: [Solución]
- **Código sugerido**: [Si aplica]

## Problemas de Seguridad
[Repetir estructura]

## Problemas de Calidad
[Repetir estructura]

## Mejoras Sugeridas (Opcionales)
[Mejoras no bloqueantes]

## Puntos Positivos
[Destacar qué está bien]

## Recomendación Final
[Estado y razón]
```

---

## Prompts Reutilizables

### Prompt Genérico de Review

```
Realizar code review completo de esta Pull Request:

Archivos principales:
[Listar archivos]

Revisar:
1. Seguridad (vulnerabilidades, secretos, validaciones)
2. Calidad (legibilidad, estructura, documentación)
3. Lógica (bugs, casos edge, validaciones)
4. Tests (cobertura, calidad, casos)
5. Estándares (PEP 8, convenciones del proyecto)

Proporcionar feedback estructurado con:
- Problemas encontrados con severidad
- Ubicación específica (archivo:línea)
- Recomendaciones de corrección
- Ejemplos de código mejorado cuando aplica
```

### Prompt de Seguridad Específico

```
Auditar seguridad de este código Python:

[Pegar código]

Buscar específicamente:
- Credenciales hardcoded (passwords, API keys, tokens)
- SQL injection risks
- XSS risks
- Validación de inputs
- Manejo seguro de secretos
- Exposición de información en errores/logs

Listar vulnerabilidades encontradas con:
- Tipo de vulnerabilidad
- Línea exacta
- Severidad (Crítico/Alto/Medio/Bajo)
- Descripción del riesgo
- Código corregido sugerido
```

---

## Checklist Completo de Review

### Seguridad (Crítico)
- [ ] Sin credenciales/secretos
- [ ] Sin datos sensibles
- [ ] Validación inputs adecuada
- [ ] Sin vulnerabilidades OWASP Top 10
- [ ] Manejo errores seguro

### Calidad
- [ ] Código legible y mantenible
- [ ] Sigue estándares del proyecto
- [ ] Documentación adecuada
- [ ] Type hints incluidos
- [ ] Sin código duplicado

### Funcionalidad
- [ ] Lógica correcta
- [ ] Casos edge manejados
- [ ] Tests presentes y pasan
- [ ] Cobertura adecuada
- [ ] Performance aceptable

### Integración
- [ ] No rompe funcionalidad existente
- [ ] Compatible con cambios recientes
- [ ] Migraciones/scripts correctos (si aplica)
- [ ] Documentación actualizada

---

## Mejores Prácticas

### 1. Ser Constructivo
- ✅ Proporcionar feedback específico y accionable
- ✅ Incluir ejemplos de código mejorado
- ✅ Explicar el "por qué" de las sugerencias
- ❌ No ser personal o agresivo

### 2. Priorizar
- Problemas críticos primero (seguridad, bugs)
- Problemas de calidad segundo
- Mejoras opcionales al final

### 3. Usar IA como Asistente
- ✅ IA identifica problemas, tú decides qué es importante
- ✅ Revisa siempre las sugerencias de IA
- ✅ No confíes ciegamente en IA
- ✅ Combina análisis de IA con tu expertise

### 4. Documentar Bien
- Feedback claro y específico
- Ubicaciones exactas de problemas
- Recomendaciones accionables
- Ejemplos cuando ayudan

---

## Herramientas Complementarias

### Análisis Estático
- **Python**: `bandit`, `pylint`, `mypy`, `black`
- **JavaScript**: `eslint`, `prettier`, `typescript`
- **General**: `sonarqube`, `codeclimate`

### Seguridad
- **Dependencies**: `safety`, `npm audit`, `pip-audit`
- **Secrets**: `git-secrets`, `truffleHog`
- **Vulnerabilities**: OWASP Dependency-Check

### Tests
- **Cobertura**: `pytest-cov`, `jest --coverage`
- **Calidad**: `mutmut` (mutation testing)

---

## Ejemplo Completo

### PR a Revisar
Feature: Agregar endpoint de autenticación JWT

### Review Generado con IA

```
# Code Review: Agregar endpoint autenticación JWT

## Resumen
- Estado: Aprobar con cambios
- Problemas críticos: 2
- Problemas de seguridad: 3
- Problemas de calidad: 4

## Problemas Críticos

### 1. Secret Key Hardcoded
- **Archivo**: `auth.py:12`
- **Severidad**: Crítico
- **Descripción**: SECRET_KEY está hardcoded en código
- **Riesgo**: Exposición de secretos en repositorio
- **Recomendación**: Usar variable de entorno
- **Código sugerido**:
```python
import os
SECRET_KEY = os.getenv("JWT_SECRET_KEY")
if not SECRET_KEY:
    raise ValueError("JWT_SECRET_KEY no configurado")
```

[Continuar con más problemas...]
```

---

**Versión**: 1.0  
**Última actualización**: 2026-01-15  
**Nivel**: 2 - IA Multi-tarea
