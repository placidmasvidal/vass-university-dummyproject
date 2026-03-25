---
ID: PR-QA-CO-001
Área / Práctica: Testing y Calidad
Subárea: Code Review
Nivel de madurez: 🟡 Intermedio
Tipo de prompt: Revisión
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Template: Prompt para Code Review

## Propósito

Template para generar prompts efectivos al realizar code review asistido por IA, identificando problemas de calidad, seguridad y mejores prácticas.

## Template Base

```
Realizar code review completo de esta Pull Request / código

INFORMACIÓN DE LA PR:
- Título: [título de la PR]
- Descripción: [descripción de la PR]
- Objetivo: [qué se intenta lograr]

ARCHIVOS CAMBIADOS:
- [archivo1.py] - [descripción breve del cambio]
- [archivo2.py] - [descripción breve del cambio]

CONTEXTO DEL PROYECTO:
- Stack: [tecnologías usadas]
- Patrones: [patrones arquitectónicos del proyecto]
- Convenciones: [estándares de codificación]
- Arquitectura: [tipo de arquitectura]

CÓDIGO A REVISAR:
[Pegar código o referenciar archivos específicos]

ÁREAS DE REVISIÓN:
1. Seguridad
   - Vulnerabilidades comunes (OWASP Top 10)
   - Credenciales o secretos expuestos
   - Validación de inputs
   - Manejo seguro de datos sensibles

2. Calidad de Código
   - Legibilidad y mantenibilidad
   - Complejidad ciclomática
   - Duplicación de código
   - Nombres descriptivos
   - Documentación adecuada

3. Funcionalidad
   - Lógica correcta
   - Casos edge manejados
   - Validaciones apropiadas
   - Manejo de errores

4. Tests
   - Cobertura adecuada
   - Calidad de tests
   - Casos edge cubiertos

5. Estándares y Convenciones
   - Sigue PEP 8 / estándares del proyecto
   - Sigue patrones establecidos
   - Consistencia con código existente

6. Performance
   - Operaciones eficientes
   - Queries optimizadas
   - Sin memory leaks

OUTPUT ESPERADO:
Proporcionar feedback estructurado con:
- Problemas encontrados (con severidad: Crítico/Alto/Medio/Bajo)
- Ubicación exacta (archivo:línea)
- Descripción del problema
- Impacto potencial
- Recomendación de solución
- Código sugerido si aplica
```

---

## Variaciones por Tipo de Revisión

### Template para Revisión de Seguridad

```
Auditar seguridad de este código:

CÓDIGO:
[Código a auditar]

BUSCAR ESPECÍFICAMENTE:
- Credenciales hardcoded (passwords, API keys, tokens)
- Vulnerabilidades OWASP Top 10:
  * SQL Injection
  * XSS (Cross-Site Scripting)
  * CSRF
  * Insecure deserialization
  * Security misconfiguration
- Validación de inputs inadecuada
- Exposición de información sensible en errores/logs
- Autenticación/autorización insegura
- Manejo inseguro de secretos

PROPORCIONAR:
- Lista de vulnerabilidades encontradas
- Severidad de cada una (Crítico/Alto/Medio/Bajo)
- Ubicación exacta
- Descripción del riesgo
- Código corregido sugerido
```

### Template para Revisión de Calidad

```
Revisar calidad del código:

CÓDIGO:
[Código a revisar]

MÉTRICAS A EVALUAR:
- Complejidad ciclomática (debe ser < 10 para funciones)
- Duplicación de código
- Legibilidad (nombres, estructura)
- Mantenibilidad
- Documentación

PROBLEMAS COMUNES A BUSCAR:
- Code smells
- Anti-patrones
- Violaciones de principios SOLID
- Código muerto
- Magic numbers

PROPORCIONAR:
- Problemas de calidad identificados
- Sugerencias de mejora
- Refactorings recomendados
- Ejemplos de código mejorado
```

### Template para Revisión de Tests

```
Revisar calidad y cobertura de tests:

TESTS:
[Código de tests a revisar]

CÓDIGO TESTEADO:
[Referencia al código que los tests cubren]

EVALUAR:
- Cobertura de funcionalidad principal
- Casos edge cubiertos
- Calidad de tests (legibilidad, mantenibilidad)
- Uso apropiado de mocks
- Tests independientes y determinísticos
- Tests significativos (no triviales)

PROPORCIONAR:
- Gaps en cobertura identificados
- Tests faltantes recomendados
- Mejoras sugeridas
- Problemas en tests existentes
```

---

## Ejemplo de Uso Completo

```
Realizar code review completo de esta Pull Request

INFORMACIÓN:
- Título: Agregar autenticación JWT
- Descripción: Implementar autenticación usando JWT tokens
- Objetivo: Permitir autenticación de usuarios en la API

ARCHIVOS:
- auth.py - Módulo de autenticación nuevo
- test_auth.py - Tests para autenticación
- config.py - Configuración modificada

CONTEXTO:
- Stack: Python 3.10, FastAPI, PostgreSQL
- Patrones: Repository, Dependency Injection
- Convenciones: PEP 8, type hints obligatorios

CÓDIGO:
[Pegar código de auth.py, test_auth.py, cambios en config.py]

ÁREAS DE REVISIÓN:
1. Seguridad: Buscar vulnerabilidades, secretos, validaciones
2. Calidad: Legibilidad, estructura, documentación
3. Funcionalidad: Lógica correcta, casos edge
4. Tests: Cobertura, calidad de tests
5. Estándares: PEP 8, patrones del proyecto
6. Performance: Eficiencia de operaciones

OUTPUT:
Feedback estructurado con problemas, severidad, ubicación, recomendaciones.
```

---

## Checklist de Revisión con IA

Usar este template junto con checklist de PR:
- Seguridad: `checklists/seguridad-ia.md`
- PR completa: `checklists/pr-ia-checklist.md`
- Calidad: `checklists/calidad-codigo-ia.md`

---

**Versión**: 1.0  
**Última actualización**: 2026-01-15
