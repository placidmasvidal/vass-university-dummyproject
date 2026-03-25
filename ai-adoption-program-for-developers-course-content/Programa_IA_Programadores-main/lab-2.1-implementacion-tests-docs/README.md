# Lab 2.1: Implementación + Tests + Documentación Simultáneos

## Objetivo

Implementar una feature completa generando simultáneamente código, tests unitarios y documentación usando IA con contexto multi-archivo.

## Duración

**3-4 horas**

## Prerequisitos

- ✅ Sesión 2.2 completada
- ✅ Conocimiento de contexto multi-archivo

## Contexto

Implementar módulo de gestión de inventario para e-commerce que incluya:
- Funciones de agregar/quitar productos
- Validación de stock
- Cálculo de totales
- Persistencia básica

**Requisito clave**: Generar código, tests Y documentación AL MISMO TIEMPO.

---

## Instrucciones

### Paso 1: Configurar Contexto Multi-archivo (15 min)

1. Crea estructura de proyecto:
```
inventory_manager/
├── inventory.py      # Código principal
├── test_inventory.py # Tests
├── inventory.md      # Documentación
└── .copilot-context/
    └── arquitectura.md
```

2. Crea archivo de contexto `.copilot-context/arquitectura.md`:
```markdown
# Arquitectura del Proyecto

## Estructura
- Capa de dominio: modelos de negocio
- Capa de aplicación: lógica de negocio
- Capa de persistencia: almacenamiento

## Patrones
- Repository pattern para datos
- Validación en capa de aplicación
```

3. **Abre TODOS los archivos en VS Code simultáneamente**

### Paso 2: Implementación Simultánea (90 min)

**Técnica**: Escribir en múltiples archivos a la vez.

#### En `inventory.py`:
```python
# Módulo de gestión de inventario para e-commerce
# 
# Funcionalidades:
# - Agregar productos al inventario
# - Remover productos del inventario
# - Validar stock disponible
# - Calcular total de productos
# - Persistencia básica en memoria
#
# Patrón: Repository pattern
# Validaciones: Stock nunca negativo, productos deben tener nombre y precio > 0

class InventoryManager:
    ...
```

**Mientras escribes esto, Copilot sugerirá:**
- Código de implementación
- Tests correspondientes en `test_inventory.py` (que también está abierto)
- Documentación en `inventory.md`

**Acepta sugerencias en los 3 archivos simultáneamente**

#### Iteración:
1. Escribe función en `inventory.py`
2. Copilot sugiere test en `test_inventory.py` → Acepta
3. Copilot sugiere documentación en `inventory.md` → Acepta
4. Refina si necesario

### Paso 3: Validación Simultánea (30 min)

Ejecuta tests mientras documentas:
```bash
# Terminal 1: Ejecutar tests en watch mode
pytest test_inventory.py -v --watch

# Mientras tanto, actualiza documentación basado en código
```

### Paso 4: Refinamiento (30 min)

Usa Copilot para mejorar los 3 en paralelo:
```python
# Mejorar implementación, tests y documentación simultáneamente:
# - Agregar casos edge
# - Mejorar documentación
# - Aumentar cobertura de tests
```

---

## Criterios de Aceptación

- [ ] Código implementado y funcional
- [ ] Tests unitarios completos (cobertura >90%)
- [ ] Documentación completa y actualizada
- [ ] Los 3 generados simultáneamente (no secuencialmente)
- [ ] Tests pasan todos
- [ ] Documentación refleja código actual

---

## Evaluación

**Éxito si:**
- Código + Tests + Docs generados en <2 horas (vs. 4+ horas secuencial)
- Calidad equivalente o mejor que desarrollo secuencial
- Documentación y tests están sincronizados con código

---

**Versión**: 1.0
