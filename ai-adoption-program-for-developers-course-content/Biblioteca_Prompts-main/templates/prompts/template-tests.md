---
ID: PR-QA-UT-001
Área / Práctica: Testing y Calidad
Subárea: Unit Testing
Nivel de madurez: 🟡 Intermedio
Tipo de prompt: Generación
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Template: Prompt para Generar Tests

## Propósito

Template para generar prompts efectivos al crear tests unitarios, de integración o E2E usando IA.

## Template Base

```
Generar suite completa de tests para [código/función/clase a testear]

CÓDIGO A TESTEAR:
[Código que necesita tests]

CONTEXTO:
- Framework de testing: [pytest/jest/junit/etc.]
- Cobertura objetivo: [X]% (ideal >90%)
- Tipo de tests: [unitarios/integración/E2E]

FUNCIONALIDAD A CUBRIR:
- [Funcionalidad principal 1]
- [Funcionalidad principal 2]
- [Casos edge importantes]

CASOS DE PRUEBA NECESARIOS:

1. Casos Exitosos:
   - [Caso 1: input normal] → [output esperado]
   - [Caso 2: input alternativo] → [output esperado]

2. Casos Edge:
   - [Edge case 1: límite mínimo]
   - [Edge case 2: límite máximo]
   - [Edge case 3: valor nulo/vacío]
   - [Edge case 4: formato incorrecto]

3. Casos de Error:
   - [Error 1: input inválido] → [excepción esperada]
   - [Error 2: condición de error] → [excepción esperada]

REQUISITOS DE TESTS:
- Tests independientes (no dependen de orden de ejecución)
- Tests determinísticos (mismo resultado siempre)
- Usar mocks apropiadamente para dependencias externas
- Tests legibles y mantenibles
- Nombres descriptivos: test_[descripción_caso]

ESTRUCTURA ESPERADA:
- [ ] Setup/teardown si es necesario
- [ ] Tests organizados por funcionalidad
- [ ] Assertions claras y descriptivas
- [ ] Mocks configurados apropiadamente
```

---

## Variaciones por Tipo de Test

### Template para Tests Unitarios

```
Generar tests unitarios para función [nombre_funcion]

FUNCIÓN:
[Código de la función]

FRAMEWORK:
[pytest/jest/etc.]

CUBRIR:
- Todos los paths de código
- Casos normales
- Casos edge
- Errores esperados

MOCKS NECESARIOS:
- [Dependencia 1: qué mockear y cómo]
- [Dependencia 2: qué mockear y cómo]
```

### Template para Tests de Integración

```
Generar tests de integración para [módulo/componente]

COMPONENTES INTEGRADOS:
- [Componente 1]
- [Componente 2]
- [Base de datos/APIs externas]

FLUJO A TESTEAR:
1. [Paso 1 del flujo]
2. [Paso 2 del flujo]
3. [Resultado esperado]

CONFIGURACIÓN:
- Ambiente de testing: [descripción]
- Datos de prueba: [qué datos usar]
- Cleanup: [cómo limpiar después]
```

### Template para Tests E2E

```
Generar tests end-to-end para flujo [nombre_flujo]

FLUJO COMPLETO:
1. [Acción inicial]
2. [Acción intermedia]
3. [Acción final]
4. [Verificación de resultado]

COMPONENTES INVOLUCRADOS:
- Frontend: [si aplica]
- Backend: [servicios]
- Base de datos: [verificaciones]
- Servicios externos: [mocks o stubs]

ESCENARIOS:
- Happy path: [flujo exitoso completo]
- Error path: [qué pasa si falla en X punto]
```

---

## Ejemplo de Uso Completo

```
Generar suite completa de tests para función calcular_precio_con_descuento

CÓDIGO A TESTEAR:
def calcular_precio_con_descuento(precio_base: float, descuento_porcentual: float, aplicar_iva: bool = True) -> Decimal:
    """
    Calcula precio final con descuento y opcionalmente IVA.
    """
    if precio_base < 0:
        raise ValueError("precio_base debe ser >= 0")
    if not (0 <= descuento_porcentual <= 1):
        raise ValueError("descuento debe estar entre 0 y 1")
    
    precio_con_descuento = precio_base * (1 - descuento_porcentual)
    
    if aplicar_iva:
        precio_final = precio_con_descuento * 1.19
    else:
        precio_final = precio_con_descuento
    
    return Decimal(str(precio_final)).quantize(Decimal('0.01'))

CONTEXTO:
- Framework: pytest
- Cobertura objetivo: >90%
- Tipo: Tests unitarios

CASOS DE PRUEBA:

1. Casos Exitosos:
   - calcular_precio_con_descuento(1000, 0.10, True) → 1071.00
     (precio 1000, 10% descuento, con IVA 19%)
   - calcular_precio_con_descuento(500, 0.0, False) → 500.00
     (sin descuento, sin IVA)

2. Casos Edge:
   - precio_base = 0 → debe retornar 0.00
   - descuento_porcentual = 1.0 (100%) → precio con descuento 0, luego IVA si aplica
   - descuento_porcentual = 0.0 (0%) → sin descuento
   - aplicar_iva = True vs False

3. Casos de Error:
   - precio_base < 0 → ValueError("precio_base debe ser >= 0")
   - descuento_porcentual > 1 → ValueError("descuento debe estar entre 0 y 1")
   - descuento_porcentual < 0 → ValueError("descuento debe estar entre 0 y 1")

REQUISITOS:
- Tests independientes
- Determinísticos
- Nombres: test_calcular_precio_[descripcion]
- Usar Decimal para comparaciones monetarias
- Assertions claras

ESTRUCTURA:
- test_calcular_precio_con_descuento_y_iva
- test_calcular_precio_sin_iva
- test_calcular_precio_con_descuento_100_porciento
- test_calcular_precio_con_precio_cero
- test_error_precio_negativo
- test_error_descuento_fuera_rango
```

---

## Tips para Tests Efectivos

1. **Cobertura completa** - Cubrir todos los paths de código
2. **Casos edge** - No solo happy path, también casos límite
3. **Tests significativos** - Tests que realmente validan comportamiento
4. **Independencia** - Tests no deben depender unos de otros
5. **Mantenibilidad** - Tests deben ser fáciles de actualizar

---

**Versión**: 1.0  
**Última actualización**: 2026-01-15
