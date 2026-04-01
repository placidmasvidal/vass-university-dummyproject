# Lab 3.3: Refactoring de Módulo con Planificación y Validación Automática — Java / AEM Like

## Objetivo

Refactorizar un **módulo completo** del proyecto dummy usando un workflow agéntico
**Plan → Act → Observe → Reflect**, manteniendo el comportamiento funcional y mejorando
la estructura, mantenibilidad y testabilidad de una implementación **Java + AEM-like**.

En este laboratorio no se trata de “reescribir por gusto”, sino de aprender a usar IA para:

- analizar deuda técnica real,
- diseñar un plan de refactor seguro,
- ejecutar cambios incrementales,
- validar continuamente con tests,
- medir mejoras obtenidas,
- y documentar el resultado con criterio técnico.

---

## Duración Estimada

**3-4 horas**

---

## Prerequisitos

- ✅ Lecciones 1 y 2 completadas
- ✅ Haber practicado prompts de implementación, debugging y code review
- ✅ Proyecto `vass-university-dummyproject` compilando localmente
- ✅ GitHub Copilot Chat, Azure OpenAI o herramienta equivalente disponible
- ✅ Conocimiento básico de:
    - Java 21
    - AEM / Sling / OSGi Declarative Services
    - tests con JUnit 5, Mockito y AEM Mocks
    - principios SOLID y separación por responsabilidades

Verificación mínima:

```bash
mvn clean install
```

---

## Contexto del Proyecto

El laboratorio debe resolverse respetando la arquitectura del proyecto dummy:

- **Módulo `core`** para lógica Java y servicios OSGi
- **Servelts Sling** solo para capa HTTP / entrada-salida
- **Servicios OSGi** para lógica de negocio
- **`ui.config`** para configuración de comportamiento
- **`ui.apps` / `ui.content`** para recursos de aplicación y contenido
- **`it.tests`** y tests unitarios como red de seguridad

El objetivo no es hacer un refactor “de Java puro”, sino uno plausible dentro de un proyecto
AEM real: clases cohesionadas, configuración externa, logging útil, dependencias inyectadas
por OSGi y un módulo fácil de revisar en una PR.

---

## Escenario del Laboratorio

Debes refactorizar un módulo legacy responsable de **procesar solicitudes de alta de usuarios
procedentes del portal**.

Actualmente existe una implementación demasiado grande y acoplada, por ejemplo:

```text
LegacyPortalRequestProcessorServiceImpl
```

Esta clase realiza demasiadas tareas a la vez:

- valida parámetros de entrada,
- normaliza datos,
- decide reglas de negocio,
- persiste el resultado,
- envía notificaciones,
- construye mensajes de log,
- y devuelve respuestas pensadas para el servlet.

Además, presenta problemas típicos:

- métodos demasiado largos,
- condiciones anidadas,
- dependencias ocultas,
- lógica duplicada,
- nombres poco expresivos,
- bajo aislamiento para tests,
- y mezcla entre lógica de dominio e infraestructura.

Tu misión es **refactorizar el módulo completo sin romper el comportamiento observable**.

---

## Resultado Esperado del Laboratorio

Al finalizar, deberías haber transformado el módulo hacia una estructura parecida a esta:

```text
core/
└── src/main/java/com/vasscompany/dummyproject/core/
    ├── models/lab3_3RefactoringModulo/
    │   ├── PortalRequest.java
    │   ├── PortalRequestResult.java
    │   └── ValidationIssue.java
    ├── services/lab3_3RefactoringModulo/
    │   ├── PortalRequestProcessorService.java
    │   ├── PortalRequestValidator.java
    │   ├── PortalRequestNormalizer.java
    │   ├── PortalRequestRepository.java
    │   ├── PortalRequestNotifier.java
    │   └── ProcessingAuditService.java
    ├── services/lab3_3RefactoringModulo/impl/
    │   ├── PortalRequestProcessorServiceImpl.java
    │   ├── PortalRequestValidatorImpl.java
    │   ├── PortalRequestNormalizerImpl.java
    │   ├── InMemoryPortalRequestRepositoryImpl.java
    │   ├── PortalRequestNotifierImpl.java
    │   └── ProcessingAuditServiceImpl.java
    └── servlets/lab3_3RefactoringModulo/
        └── PortalRequestServlet.java

core/
└── src/test/java/com/vasscompany/dummyproject/core/
    ├── services/lab3_3RefactoringModulo/impl/
    │   ├── PortalRequestProcessorServiceImplTest.java
    │   ├── PortalRequestValidatorImplTest.java
    │   ├── PortalRequestNormalizerImplTest.java
    │   └── PortalRequestNotifierImplTest.java
    └── servlets/lab3_3RefactoringModulo/
        └── PortalRequestServletTest.java

ui.config/
└── src/main/content/jcr_root/apps/vass-university-dummyproject/osgiconfig/config/
    └── com.vasscompany.dummyproject.core.services.lab3_3RefactoringModulo.impl.PortalRequestProcessorServiceImpl.cfg.json
```

No es obligatorio usar exactamente estos nombres, pero sí llegar a una solución equivalente:

- más modular,
- más fácil de testear,
- con dependencias explícitas,
- y con responsabilidades claramente separadas.

---

## Workflow Agéntico del Laboratorio

## Fase 1 — PLAN (45-60 min)

Aquí debes usar la IA como **arquitecto y analista**, no como generador ciego de código.

### Qué debes hacer

1. Leer el módulo actual completo
2. Identificar responsabilidades mezcladas
3. Detectar deuda técnica y riesgos
4. Identificar comportamiento que debe preservarse
5. Revisar tests existentes o definir tests de caracterización
6. Diseñar un plan de refactor incremental
7. Definir puntos de validación tras cada cambio

### Prompt recomendado

```text
Actúa como arquitecto de software senior especializado en Java 21, AEM, OSGi Declarative Services y refactoring seguro de módulos legacy.

Tengo un módulo AEM-like que procesa solicitudes del portal y quiero refactorizarlo sin cambiar su comportamiento observable.

CONTEXTO DEL PROYECTO:
- Java 21
- Maven multi-módulo
- Arquitectura AEM-like
- Servicios OSGi
- Sling Servlets
- Tests con JUnit 5, Mockito y AEM Mocks
- Configuración en ui.config

OBJETIVO:
Refactorizar un módulo legacy que hoy mezcla validación, normalización, reglas de negocio, persistencia, notificación y logging.

NECESITO:
1. Identificar responsabilidades actuales del módulo
2. Detectar problemas de diseño, deuda técnica y code smells
3. Proponer una arquitectura refactorizada por componentes
4. Proponer interfaces y clases nuevas
5. Definir el orden más seguro de refactorización
6. Sugerir tests de caracterización y red de seguridad
7. Indicar riesgos típicos en AEM/OSGi durante el refactor
8. Generar checklist de validación tras cada paso

Importante:
- No propongas Spring Boot
- Mantén el servlet como capa fina
- La lógica debe ir en servicios OSGi
- No rompas compatibilidad funcional
- Prioriza cambios incrementales y verificables
```

### Entregable de esta fase

Crea un documento como:

- `plan_lab_3.3.md`

Debe incluir:

- mapa actual de responsabilidades,
- problemas detectados,
- componentes destino,
- estrategia paso a paso,
- tests a mantener/crear,
- métricas o indicadores a observar.

### Señales de una buena planificación

- El refactor se puede ejecutar en pasos pequeños
- Hay una red de seguridad antes de tocar el código
- Se distingue entre refactor y cambio funcional
- Se detectan dependencias peligrosas o acoplamientos ocultos
- Queda claro qué métricas mejorarán y cómo comprobarlo

---

## Fase 2 — ACT (90-120 min)

Ejecuta el refactor de forma incremental y validando continuamente.

### Estrategia recomendada

#### Paso 1 — Congelar comportamiento actual

Antes de refactorizar:

- ejecuta tests existentes,
- identifica huecos de cobertura,
- añade tests de caracterización si el módulo legacy no está bien cubierto.

Ejemplos de tests de caracterización:

- validación de campos obligatorios,
- comportamiento con entradas vacías o nulas,
- reglas de rechazo / aceptación,
- trazabilidad de logs,
- envío o no envío de notificaciones,
- códigos o mensajes devueltos al servlet.

#### Paso 2 — Extraer modelos de dominio simples

Extrae objetos que mejoren la semántica del módulo:

- `PortalRequest`
- `PortalRequestResult`
- `ValidationIssue`

Evita seguir pasando `Map<String, Object>` o múltiples parámetros dispersos.

#### Paso 3 — Extraer validación

Mueve las reglas de validación a un servicio específico:

- `PortalRequestValidator`

#### Paso 4 — Extraer normalización

Separa limpieza y normalización de datos:

- trimming,
- mayúsculas/minúsculas,
- defaults,
- transformación de formatos.

Servicio sugerido:

- `PortalRequestNormalizer`

#### Paso 5 — Separar persistencia y notificación

Aísla dependencias externas y efectos secundarios:

- `PortalRequestRepository`
- `PortalRequestNotifier`
- `ProcessingAuditService`

#### Paso 6 — Reducir el orquestador

Convierte `PortalRequestProcessorServiceImpl` en un **coordinador claro**:

1. recibe petición,
2. normaliza,
3. valida,
4. decide flujo,
5. persiste,
6. notifica,
7. audita,
8. devuelve resultado estructurado.

#### Paso 7 — Ajustar el servlet

El servlet debe quedarse solo con:

- lectura de parámetros,
- llamada al servicio principal,
- transformación del resultado a JSON/HTTP,
- gestión mínima de errores de entrada/salida.

### Prompt recomendado para implementación

```text
Actúa como desarrollador senior Java/AEM especializado en refactoring incremental.

Quiero ejecutar este plan de refactor sobre un módulo legacy del proyecto dummy.

OBJETIVO:
- Mantener el comportamiento observable
- Extraer responsabilidades por servicios
- Mejorar testabilidad y legibilidad
- Mantener patrones AEM/OSGi

RESTRICCIONES:
- No usar Spring
- No mezclar lógica de negocio en servlets
- Mantener SLF4J
- Mantener compatibilidad razonable con tests existentes
- Usar interfaces cuando aporte claridad

TRABAJA ASÍ:
1. Propón el primer cambio seguro
2. Explica por qué es seguro
3. Muestra el código modificado
4. Indica cómo validarlo
5. Espera al siguiente paso conceptual
```

---

## Fase 3 — OBSERVE (30-45 min)

Después de cada bloque de cambios debes observar si el sistema mejora realmente.

### Validaciones mínimas

#### Tests

```bash
mvn -q -DskipTests=false test
```

Si tienes integración disponible:

```bash
mvn verify -Pit
```

#### Checks manuales recomendados

- El servlet sigue devolviendo respuestas equivalentes
- Los casos límite siguen respondiendo como antes
- No se rompen configuraciones OSGi
- El logging sigue siendo útil y no excesivo
- No se introducen `ResourceResolver` sin cierre correcto
- No aparecen dependencias circulares entre servicios

### Métricas que puedes comparar

Antes vs después:

- longitud de clases,
- longitud media de métodos,
- número de responsabilidades por clase,
- complejidad ciclomática aproximada,
- cobertura de tests,
- facilidad para mockear dependencias,
- claridad de nombres y puntos de extensión.

### Prompt recomendado para observación

```text
Analiza este refactor y evalúa si realmente mejora el módulo.

Quiero que revises:
1. Cohesión por clase
2. Acoplamiento entre componentes
3. Testabilidad
4. Legibilidad
5. Riesgos restantes
6. Posibles simplificaciones adicionales
7. Si algún cambio parece refactor cosmético en lugar de mejora real

Devuélveme una evaluación crítica, no complaciente.
```

---

## Fase 4 — REFLECT (20-30 min)

Esta fase es clave. No basta con “funciona”. Debes entender **por qué ahora está mejor**.

### Preguntas de reflexión

- ¿Qué responsabilidades estaban mezcladas al inicio?
- ¿Qué cambio produjo la mayor mejora?
- ¿Qué parte siguió siendo difícil de refactorizar y por qué?
- ¿Qué tests faltaban y descubriste durante el proceso?
- ¿Hubo algún momento en el que casi cambiaste comportamiento sin querer?
- ¿Qué le dirías a un reviewer para justificar el refactor?

### Entregable recomendado

Crea un documento como:

- `reflexion_lab_3.3.md`

Incluye:

- resumen del problema inicial,
- plan seguido,
- cambios realizados,
- validaciones ejecutadas,
- mejoras medibles,
- riesgos restantes,
- próximos pasos si el módulo siguiera evolucionando.

---

## Ejemplo de Caso Legacy AEM-Like

Puedes imaginar un punto de partida parecido a este:

```java
@Component(service = PortalRequestProcessorService.class)
public class LegacyPortalRequestProcessorServiceImpl implements PortalRequestProcessorService {

    @Override
    public String process(Map<String, String> params) {
        // 200+ líneas:
        // - valida
        // - normaliza
        // - construye mensajes
        // - persiste
        // - llama a email
        // - hace logging
        // - decide el HTTP-like result
        return "OK";
    }
}
```

El objetivo no es solo trocear esta clase, sino **mejorar el diseño** para que:

- sea mantenible,
- permita añadir nuevas reglas,
- facilite testing aislado,
- y soporte futuras revisiones sin miedo a romper cosas.

---

## Criterios de Éxito

Tu laboratorio estará bien resuelto si consigues:

- ✅ comportamiento funcional preservado,
- ✅ tests pasando,
- ✅ clases más cohesionadas,
- ✅ dependencias explícitas,
- ✅ menor complejidad accidental,
- ✅ mejor legibilidad,
- ✅ documentación clara del refactor,
- ✅ PR fácil de revisar.

No estará bien resuelto si:

- ❌ cambias funcionalidad sin detectarlo,
- ❌ sustituyes una clase monstruo por muchas clases vacías sin valor,
- ❌ introduces abstracciones innecesarias,
- ❌ rompes el modelo AEM/OSGi del proyecto,
- ❌ dejas el servlet con lógica de negocio,
- ❌ no puedes demostrar mejora con alguna evidencia.

---

## Entregables

Al terminar, deberías tener algo equivalente a:

### Código

- módulo refactorizado en `core`
- posibles configs OSGi en `ui.config`
- tests unitarios actualizados o creados
- opcionalmente tests de integración

### Documentación

- `plan_lab_3.3.md`
- `reflexion_lab_3.3.md`
- notas de validación o checklist de evidencia

### Evidencias recomendadas

- salida de tests,
- diff representativo del refactor,
- lista de clases antes/después,
- tabla simple de mejoras observadas.

---

## Checklist Final

Antes de dar el laboratorio por terminado, comprueba:

- [ ] El comportamiento observable sigue siendo el esperado
- [ ] Hay tests que protegen el refactor
- [ ] El servlet no contiene lógica de negocio relevante
- [ ] Las responsabilidades están mejor separadas
- [ ] La nomenclatura de clases y métodos es más clara
- [ ] El logging es útil y no ruidoso
- [ ] La configuración está externalizada cuando corresponde
- [ ] El módulo es más fácil de extender
- [ ] Puedes explicar claramente por qué el diseño ahora es mejor

---

## Qué estás aprendiendo realmente

Este laboratorio no trata solo sobre refactorización.

Te está enseñando a usar IA como apoyo para:

- analizar código legacy con criterio,
- distinguir refactor de cambio funcional,
- planificar cambios complejos en pasos seguros,
- usar tests como red de seguridad real,
- y justificar técnicamente una mejora arquitectónica.

Eso es exactamente lo que marca la diferencia entre “usar IA para picar código” y
**trabajar con IA como desarrollador senior en un proyecto real**.

---

**Versión**: 1.0  
**Adaptación**: Java / AEM Like para `vass-university-dummyproject`
