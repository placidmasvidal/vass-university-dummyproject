# Reflexión - Lab 1.2 (Implementación con Prompting + TDD)

## 1) Contexto y objetivo
En este laboratorio se buscaba implementar `ShippingCalculatorServiceImpl` de forma guiada por tests (TDD) y apoyada por autocompletado de Copilot.  
El objetivo no era solo “que compile”, sino conseguir una implementación **correcta respecto al contrato de tests y README**, con mensajes y reglas deterministas.

## 2) Qué funcionó bien
- **Prompts con restricciones fuertes** (alcance “SOLO este método”, no cambiar firma/imports, no inventar reglas) mejoraron la precisión.
- **Anclas numéricas explícitas** (ej.: `EXPRESS (250km) -> 2`, `PREMIUM (500, 2.5) -> 46.00`) redujeron ambigüedad y ayudaron a converger al comportamiento esperado.
- **Implementación incremental por método** (vaciar solo el `return`, mantener validaciones existentes) dio mejores resultados que pedir todo de golpe.
- En `validateShipment`, al fijar formato de respuesta y mensajes esperados por test, Copilot produjo una solución alineada y estable.

## 3) Qué no funcionó / problemas detectados
- Con prompts genéricos, Copilot tendió a generar lógica “plausible” pero no contractual (p. ej., añadir días por tramo en `calculateDeliveryTime` cuando el test ancla exigía valor fijo por tipo).
- Si no se declara explícitamente **qué variable influye y cuál no**, el modelo completa con heurísticas comunes del dominio (hallucination coding “razonable”).
- También se observó riesgo de “enganche a patrones previos” (legacy o defaults típicos) cuando faltan límites claros en el prompt.

## 4) Lecciones aprendidas
1. En prompting para autocompletado, **la precisión semántica gana a la extensión**: mejor pocas reglas inequívocas que texto largo ambiguo.
2. Los tests actúan como **contrato ejecutable**: la IA debe apuntar a pasar esos casos, no a “parecer correcta”.
3. Los ejemplos ancla son claves para funciones con fórmulas: fijan el resultado y evitan interpretaciones.
4. Pedir “sin inventar” no basta: hay que indicar **fuente de verdad** (“prioriza tests sobre suposiciones”).
5. Mensajes de error y validaciones deben tratarse como parte del contrato, no como detalle secundario.

## 5) Buenas prácticas accionables para próximos labs
- Prompt por método con plantilla mínima:
    - alcance exacto (`Implementa SOLO ...`)
    - restricciones técnicas (`no cambies firma/imports/otros métodos`)
    - fuente de verdad (`README + tests`, priorizar tests)
    - 1–2 casos ancla con entrada/salida exacta
    - formato de errores esperado (cuando aplique)
- Implementar en ciclos cortos:
    1) completar método,
    2) ejecutar test objetivo,
    3) ajustar prompt si falla por ambigüedad.
- Evitar prompts “de contexto global” para autocompletado local: degradan foco.
- Mantener comentarios guía justo encima del método (reglas y anclas), no solo al inicio del archivo.

## 6) Conclusión
El laboratorio confirma que Copilot aporta mucho valor en TDD cuando se le da un marco **acotado, verificable y no ambiguo**.  
El salto de calidad no vino de “más texto”, sino de **mejor contrato en el prompt**: reglas explícitas + anclas + prioridad a tests.  
Resultado: implementación más confiable, menos iteraciones ciegas y mayor control sobre el comportamiento final.
