---
ID: PR-DEV-PB-001
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

# Playbook: Feature Completa con Workflow Agéntico

## Proceso Plan-Act-Observe-Reflect

### PLAN (10-15 min)
```
Como arquitecto de software, analiza este ticket y crea plan detallado:

Ticket: [descripción]

Requisitos:
- [requisito 1]
- [requisito 2]

Contexto del proyecto:
- Stack: [tecnologías]
- Patrones: [patrones usados]
- Estructura: [estructura actual]

Genera plan con:
1. Análisis de requisitos
2. Diseño de solución (arquitectura, componentes)
3. Plan de implementación (pasos detallados)
4. Plan de testing (qué testear)
5. Plan de documentación
6. Identificación de dependencias
```

### ACT (60-120 min)
```
Implementar feature según plan generado.

Plan a seguir:
[pegar plan generado]

Implementar paso por paso:
1. [Paso 1 del plan]
2. [Paso 2 del plan]
...

Por cada paso:
- Generar código
- Generar tests correspondientes
- Actualizar documentación
- Verificar integración
```

### OBSERVE (15-20 min)
```
Validar implementación de feature:

Checks a realizar:
1. ¿Todos los tests pasan?
2. ¿Cobertura >80%?
3. ¿Linter sin errores?
4. ¿Sigue plan original?
5. ¿Cumple requisitos del ticket?

Si hay problemas, documentarlos para REFLECT.
```

### REFLECT (10-15 min)
```
Reflexionar sobre implementación:

Problemas encontrados:
- [problema 1]
- [problema 2]

Refinamientos necesarios:
- [mejora 1]
- [mejora 2]

Generar código corregido para problemas identificados.
Iterar hasta validación exitosa.
```

---

**Tiempo total**: 2-3 horas  
**Entregables**: Feature completa + tests + docs + PR lista
