---
ID: PR-REF-RE-001
Área / Práctica: Refactoring
Subárea: Refactoring
Nivel de madurez: 🟡 Intermedio
Tipo de prompt: Generación
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Template: Prompt para Refactoring

## Propósito

Template para generar prompts efectivos al refactorizar código existente, mejorando legibilidad y mantenibilidad sin cambiar funcionalidad.

## Template Base

```
Refactorizar [código/módulo/función] para mejorar [objetivo específico: legibilidad/mantenibilidad/performance]

CÓDIGO ACTUAL:
[Código a refactorizar o referencia al archivo]

OBJETIVOS DEL REFACTORING:
- [Objetivo 1: mejorar nombres de variables]
- [Objetivo 2: reducir complejidad ciclomática]
- [Objetivo 3: extraer funciones/métodos]
- [Objetivo 4: mejorar documentación]

REQUISITOS CRÍTICOS:
- ✅ Mantener funcionalidad EXACTA
- ✅ Tests existentes deben seguir pasando
- ✅ No cambiar API pública
- ✅ No cambiar comportamiento visible

TESTS COMO RED DE SEGURIDAD:
- Archivo de tests: [ruta]
- Tests que validan comportamiento: [lista de tests relevantes]

MEJORAS ESPECÍFICAS:
1. [Mejora específica 1]
   - Qué cambiar: [descripción]
   - Por qué: [razón]
   
2. [Mejora específica 2]
   - Qué cambiar: [descripción]
   - Por qué: [razón]

PATRONES A APLICAR:
- [Patrón 1: extraer función, renombrar, etc.]
- [Patrón 2: aplicar principio SOLID específico]

NO CAMBIAR:
- [Qué NO debe cambiar]
- [Restricciones específicas]

VALIDACIÓN:
Después de refactoring:
- [ ] Todos los tests pasan
- [ ] Comportamiento es idéntico
- [ ] Código es más legible
- [ ] Complejidad reducida
```

---

## Variaciones por Tipo de Refactoring

### Template para Mejorar Nombres

```
Refactorizar nombres de variables/funciones en [código] para mejorar legibilidad

CÓDIGO:
[Código actual]

CAMBIOS DE NOMBRES:
- [variable_vieja] → [variable_nueva] - [razón]
- [funcion_vieja] → [funcion_nueva] - [razón]

MANTENER:
- Funcionalidad exacta
- Tests pasando
```

### Template para Extraer Función

```
Extraer lógica de [función grande] a función separada

FUNCIÓN ORIGINAL:
[Código de función grande]

LÓGICA A EXTRAER:
[Sección específica del código]

NUEVA FUNCIÓN:
- Nombre: [nombre_descriptivo]
- Parámetros: [lista]
- Retorno: [tipo]
- Responsabilidad: [qué hace]

MANTENER:
- Funcionalidad exacta
- Tests existentes pasan
- Llamadas a función original siguen funcionando
```

### Template para Reducir Complejidad

```
Reducir complejidad ciclomática de [función/método]

CÓDIGO ACTUAL:
[Código con alta complejidad]

COMPLEJIDAD ACTUAL:
- [Medición si disponible]
- Problemas: [nested ifs, múltiples returns, etc.]

OBJETIVO:
- Reducir a complejidad < [número objetivo]
- Mantener funcionalidad exacta

ESTRATEGIA:
- [Estrategia 1: early returns]
- [Estrategia 2: extraer funciones]
- [Estrategia 3: simplificar lógica]
```

---

## Ejemplo de Uso Completo

```
Refactorizar función process_order para mejorar legibilidad y reducir complejidad

CÓDIGO ACTUAL:
def process_order(data):
    x = data.get('items')
    y = data.get('customer')
    if x:
        if y:
            z = 0
            for i in x:
                z += i.get('price', 0) * i.get('qty', 0)
            if z > 0:
                if y.get('type') == 'premium':
                    z = z * 0.9
                return {'total': z, 'status': 'ok'}
            else:
                return {'error': 'invalid'}
        else:
            return {'error': 'no customer'}
    else:
        return {'error': 'no items'}

OBJETIVOS:
- Mejorar nombres de variables (x, y, z son poco descriptivos)
- Reducir anidamiento (demasiados ifs anidados)
- Extraer lógica de cálculo a función separada
- Mejorar legibilidad general

REQUISITOS:
- ✅ Funcionalidad exacta mantenida
- ✅ Tests en test_order_processor.py deben pasar
- ✅ API pública no cambia (mismo input/output)

MEJORAS:
1. Renombrar variables:
   - x → items
   - y → customer
   - z → total_price
   - i → item

2. Reducir anidamiento:
   - Usar early returns para validaciones
   - Extraer validaciones a funciones auxiliares

3. Extraer cálculo:
   - Función calcular_total_pedido(items, customer_type)
   - Retorna precio total con descuentos aplicados

PATRONES:
- Early returns para validaciones
- Guard clauses pattern
- Single Responsibility Principle

VALIDACIÓN:
- Todos los tests en test_order_processor.py pasan
- Comportamiento idéntico
- Código más legible
- Complejidad ciclomática reducida de [X] a [Y]
```

---

## Tips para Refactoring Efectivo

1. **Tests primero** - Asegurar que tests pasan antes de refactorizar
2. **Pasos pequeños** - Refactorizar incrementalmente, no todo de una vez
3. **Validar constantemente** - Ejecutar tests después de cada cambio
4. **Mantener comportamiento** - No agregar features durante refactoring
5. **Documentar cambios** - Explicar qué cambió y por qué

---

**Versión**: 1.0  
**Última actualización**: 2026-01-15
