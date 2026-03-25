---
ID: PR-DEV-PB-002
Área / Práctica: Desarrollo
Subárea: Playbooks
Nivel de madurez: 🔴 Avanzado
Tipo de prompt: Guía
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Playbook: Bug Fix Agéntico

## Workflow Plan-Act-Observe-Reflect para Bug Fix

### PLAN - Diagnóstico Asistido

```
Analizar bug reportado: [descripción]

CONTEXTO:
- Sistema: [descripción]
- Servicios involucrados: [lista]
- Síntomas: [descripción]
- Frecuencia: [siempre/ocasional/esporádico]

GENERA PLAN DE DIAGNÓSTICO:
1. Hipótesis posibles (top 3-5)
2. Archivos/código a revisar
3. Logs/datos a analizar
4. Tests necesarios para reproducir
5. Estrategia de debugging paso a paso
```

### ACT - Investigación y Fix

**Fase 1: Investigación**
```
Revisar código buscando [hipótesis específica]:
- [Archivo 1]: Buscar [patrón específico]
- [Archivo 2]: Verificar [comportamiento específico]
- [Archivo 3]: Analizar [lógica específica]

¿Dónde está el problema? ¿Qué está causando [síntoma]?
```

**Fase 2: Reproducir**
```
Crear test que reproduce el bug:
- Condiciones necesarias
- Test debe fallar demostrando bug
- Debe ser determinístico o probabilístico
```

**Fase 3: Fix**
```
Implementar fix para [problema identificado]:
- Solución: [descripción]
- Asegurar: [requisitos]
- Mantener: [funcionalidad que no debe cambiar]

Fix debe hacer que test de reproducción pase.
```

### OBSERVE - Validación

```
Validar fix:

1. ¿Test de reproducción pasa?
2. ¿Tests existentes pasan?
3. ¿No hay regresiones?
4. ¿Fix es robusto?
5. ¿Casos edge considerados?
```

### REFLECT - Refinamiento

```
Reflexionar sobre fix:
- ¿Es la mejor solución?
- ¿Hay mejor manera?
- ¿Qué aprendimos?
- ¿Cómo prevenir similar?
```

---

**Tiempo típico**: 2-4 horas según complejidad

---

**Versión**: 1.0
