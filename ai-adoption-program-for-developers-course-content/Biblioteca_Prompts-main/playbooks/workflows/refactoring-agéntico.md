---
ID: PR-REF-PB-001
Área / Práctica: Refactoring
Subárea: Playbooks
Nivel de madurez: 🔴 Avanzado
Tipo de prompt: Guía
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Playbook: Refactoring Agéntico

## Workflow Plan-Act-Observe-Reflect para Refactoring

### PLAN - Análisis y Planificación

```
Analizar código para refactoring: [módulo/función]

CÓDIGO ACTUAL:
[Pegar código o referencia]

OBJETIVOS DEL REFACTORING:
- [Objetivo 1: mejorar legibilidad]
- [Objetivo 2: reducir complejidad]
- [Objetivo 3: mejorar mantenibilidad]

GENERA PLAN DE REFACTORING:
1. Análisis de código actual (problemas, complejidad, deuda)
2. Objetivos específicos del refactoring
3. Estrategia paso a paso (refactoring incremental)
4. Tests existentes que validan comportamiento
5. Plan de validación después de cada paso
6. Riesgos y mitigaciones
```

### ACT - Ejecución Incremental

**Principio**: Refactorizar en pasos pequeños, validando después de cada uno.

**Paso 1: Mejora pequeña**
```
Refactorizar: [cambio específico pequeño]
- Ejemplo: Mejorar nombre de variable X a nombre_descriptivo
- Mantener comportamiento exacto
- Tests deben seguir pasando
```

**Paso 2: Extracción**
```
Extraer [lógica específica] a función/método separado:
- Nombre: [nombre descriptivo]
- Responsabilidad: [qué hace]
- Mantener comportamiento exacto
```

**Paso 3: Reorganización**
```
Reorganizar estructura:
- [Cambio específico]
- Mantener funcionalidad
```

**Continuar paso a paso...**

### OBSERVE - Validación Continua

Después de CADA paso:
```
Validar paso [N] de refactoring:

1. ¿Tests pasan todos?
2. ¿Comportamiento se mantiene?
3. ¿Mejora lograda (legibilidad, complejidad)?
4. ¿No hay regresiones?
```

Si algo falla:
```
REFLECT: Problema en paso [N]
- ¿Qué salió mal?
- ¿Cómo corregir?
- ¿Revertir o ajustar?
```

### REFLECT - Iteración

```
Reflexionar sobre refactoring:

Mejoras logradas:
- [Mejora 1]
- [Mejora 2]

Problemas encontrados:
- [Problema 1 y solución]

Próximos pasos:
- [Paso siguiente o refinamiento]
```

---

## Reglas de Oro

1. **Tests primero**: Siempre tener tests pasando
2. **Pasos pequeños**: Cambios incrementales
3. **Validar constantemente**: Después de cada paso
4. **Revertir rápido**: Si algo falla, revertir inmediatamente

---

**Tiempo típico**: 2-4 horas según tamaño del código

---

**Versión**: 1.0
