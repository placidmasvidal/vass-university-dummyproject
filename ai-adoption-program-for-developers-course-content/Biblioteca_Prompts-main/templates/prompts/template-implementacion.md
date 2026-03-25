---
ID: PR-DEV-IM-001
Área / Práctica: Desarrollo
Subárea: Implementación
Nivel de madurez: 🟡 Intermedio
Tipo de prompt: Generación
Compatibilidad: ChatGPT, GitHub Copilot Chat
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Template: Prompt para Implementación

## Propósito

Template reutilizable para generar prompts efectivos al implementar código nuevo o features.

## Template Base

```
Implementar [tipo de código: función/clase/endpoint/módulo] para [propósito específico]

CONTEXTO:
- Problema a resolver: [descripción clara del problema]
- Dominio de negocio: [contexto del dominio]
- Proyecto: [nombre del proyecto o módulo]

REQUISITOS:
- [Requisito funcional 1]
- [Requisito funcional 2]
- [Requisito no funcional 1: performance/seguridad/escalabilidad]

LENGUAJE Y TECNOLOGÍA:
- Lenguaje: [Python/Java/JavaScript/etc.] versión [X]
- Framework: [FastAPI/Django/Spring/etc.] versión [X]
- Librerías: [librerías específicas a usar]

EJEMPLOS:
- Input: [ejemplo concreto de input]
- Output esperado: [ejemplo concreto de output]
- Casos de uso:
  * Caso 1: [descripción] → [resultado esperado]
  * Caso 2: [descripción] → [resultado esperado]

SUPUESTOS:
- [Supuesto 1 sobre los datos/entrada]
- [Supuesto 2 sobre el entorno]
- [NO asumir: qué no debemos asumir]

RESULTADO ESPERADO:
- Tipo de retorno: [tipo de dato]
- Formato: [formato específico si aplica]
- Comportamiento: [qué debe hacer exactamente]
- Manejo de errores: [cómo manejar errores]

PATRONES A SEGUIR:
- Similar a: [función/clase similar existente en el proyecto]
- Usar patrón: [patrón específico del proyecto]
- Convenciones: [convenciones de nombres, estilo, etc.]

VALIDACIONES NECESARIAS:
- [Validación 1]
- [Validación 2]

NOTAS ADICIONALES:
- [Cualquier información adicional relevante]
```

---

## Variaciones por Tipo de Código

### Template para Función

```
Implementar función [nombre_funcion] que [descripción clara]

Parámetros:
- [param1]: [tipo] - [descripción]
- [param2]: [tipo] - [descripción]

Retorna:
- [tipo] - [descripción del retorno]

Ejemplos:
  nombre_funcion([input1], [input2]) → [output1]
  nombre_funcion([input3], [input4]) → [output2]

Manejo de errores:
- Si [condición], lanzar [excepción] con mensaje [mensaje]
```

### Template para Clase

```
Implementar clase [NombreClase] que [responsabilidad principal]

Responsabilidades:
- [Responsabilidad 1]
- [Responsabilidad 2]

Métodos públicos:
- [método1]([params]) → [retorno] - [descripción]
- [método2]([params]) → [retorno] - [descripción]

Propiedades:
- [propiedad1]: [tipo] - [descripción]

Patrones a usar:
- [Patrón específico: Repository, Factory, etc.]
```

### Template para Endpoint/API

```
Implementar endpoint [HTTP_METHOD] [ruta]

Descripción: [qué hace el endpoint]

Request:
- Body: [estructura del body]
- Headers: [headers necesarios]
- Params: [parámetros de ruta/query]

Response:
- Success (200): [estructura de respuesta exitosa]
- Error (400): [estructura de error de validación]
- Error (500): [estructura de error del servidor]

Validaciones:
- [Validación 1]
- [Validación 2]

Autenticación/Autorización:
- [Requisitos de auth si aplica]
```

---

## Ejemplo de Uso Completo

```
Implementar función calcular_precio_final para calcular precio de producto con impuestos y descuentos

CONTEXTO:
- Problema: Necesitamos calcular precio final de productos en e-commerce
- Dominio: Sistema de ventas online
- Módulo: pricing_calculator.py

REQUISITOS:
- Aplicar IVA (19% en Chile)
- Aplicar descuento si aplica
- Redondear a 2 decimales
- Precio final nunca puede ser negativo

LENGUAJE:
- Python 3.10+
- Type hints obligatorios
- Usar Decimal para cálculos monetarios

EJEMPLOS:
- calcular_precio_final(1000, 0.10, aplicar_iva=True) → 1309.00
  (precio base 1000, descuento 10%, con IVA)
- calcular_precio_final(500, 0.0, aplicar_iva=False) → 500.00
  (sin descuento, sin IVA)

SUPUESTOS:
- precio_base >= 0
- descuento_porcentual entre 0 y 1 (0-100%)
- aplicar_iva es booleano

RESULTADO:
- Retorna: Decimal (precio final redondeado a 2 decimales)
- Lanza ValueError si precio_base < 0 o descuento fuera de rango

PATRONES:
- Similar a: calcular_precio_con_envio() en mismo módulo
- Usar Decimal para precisión monetaria
- Seguir PEP 8

VALIDACIONES:
- precio_base debe ser >= 0
- descuento_porcentual debe estar entre 0 y 1
- Validar que precio final no sea negativo después de cálculos
```

---

## Tips para Usar el Template

1. **Completar todos los campos** - Más contexto = mejor código generado
2. **Ser específico en ejemplos** - Ejemplos concretos ayudan mucho
3. **Mencionar patrones existentes** - Copilot aprenderá del contexto del proyecto
4. **Incluir validaciones** - Especificar qué validar ayuda a generar código robusto
5. **Iterar si necesario** - Refinar el prompt si el resultado no es óptimo

---

**Versión**: 1.0  
**Última actualización**: 2026-01-15
