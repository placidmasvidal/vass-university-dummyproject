# Lab 2.2: Refactoring Asistido con Preservación de Tests

## Objetivo

Refactorizar un módulo completo manteniendo tests como red de seguridad, usando IA para refactorizar código Y actualizar documentación simultáneamente.

## Duración

**2-3 horas**

## Contexto

Tienes un módulo legacy `order_processor.py` que funciona pero necesita refactoring:
- Código difícil de mantener
- Falta documentación
- Tests existen pero podrían mejorar

**Objetivo**: Refactorizar manteniendo funcionalidad exacta, mejorando código Y documentación.

---

## Instrucciones

### Paso 1: Preparar Contexto (15 min)

1. Abre archivos relacionados:
   - `order_processor.py` (código a refactorizar)
   - `test_order_processor.py` (tests existentes)
   - `order_processor.md` (documentación a crear/actualizar)
   - `.copilot-context/patrones.md` (patrones del proyecto)

2. Ejecuta tests para baseline:
```bash
pytest test_order_processor.py -v
# Todos deben pasar
```

### Paso 2: Refactorizar con Tests como Red (60 min)

**Técnica**: Refactorizar código mientras tests se ejecutan.

```python
# Refactorizar order_processor.py manteniendo comportamiento exacto:
# 1. Mejorar nombres de variables/funciones
# 2. Extraer funciones/métodos
# 3. Mejorar estructura
# 4. Agregar documentación
# 
# CRÍTICO: Tests deben seguir pasando en cada paso
# 
# Usar tests como validación continua
```

**Proceso:**
1. Hacer cambio pequeño en código
2. Ejecutar tests (deben pasar)
3. Si fallan, revertir y ajustar
4. Continuar con siguiente cambio

**Usa Copilot para:**
- Sugerir refactorings seguros
- Mantener tests actualizados
- Generar documentación mientras refactorizas

### Paso 3: Actualizar Documentación Simultáneamente (30 min)

Mientras refactorizas, actualiza documentación:
```markdown
# Actualizar order_processor.md con:
# - Nueva estructura después de refactoring
# - Explicación de mejoras realizadas
# - Ejemplos de uso actualizados
```

### Paso 4: Mejorar Tests (30 min)

Usa Copilot para mejorar tests:
```python
# Mejorar test_order_processor.py:
# - Agregar tests para casos edge descubiertos
# - Mejorar legibilidad de tests
# - Aumentar cobertura si necesario
# - Mantener todos los tests existentes funcionando
```

---

## Criterios de Aceptación

- [ ] Código refactorizado y mejorado
- [ ] TODOS los tests originales pasan
- [ ] Cobertura de tests mantenida o mejorada
- [ ] Documentación actualizada y completa
- [ ] Código más legible y mantenible
- [ ] Funcionalidad exacta preservada

---

**Versión**: 1.0
