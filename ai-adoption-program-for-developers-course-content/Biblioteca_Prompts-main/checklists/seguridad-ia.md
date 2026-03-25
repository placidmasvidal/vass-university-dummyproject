---
ID: PR-SEC-CH-001
Área / Práctica: Seguridad
Subárea: Checklists
Nivel de madurez: 🟡 Intermedio
Tipo de prompt: Revisión
Compatibilidad: Universal
Autor: Equipo AI-DLC
Versión: v1.0
Fecha: 2026-01-15
Estado: Aprobado
---

# Checklist de Seguridad IA

## Propósito

Este checklist debe usarse **SIEMPRE** antes de aceptar cualquier sugerencia de GitHub Copilot o antes de usar código generado por IA. La seguridad es responsabilidad de todos y no es negociable.

---

## Pre-Aceptación: Revisión Rápida (30 segundos)

Antes de aceptar cualquier sugerencia de Copilot, haz esta revisión rápida:

- [ ] ¿La sugerencia tiene sentido en el contexto?
- [ ] ¿Veo algo obviamente incorrecto o sospechoso?
- [ ] ¿Contiene texto o valores que parecen ser datos reales?

**Si alguna respuesta es "sí" o "no estoy seguro", NO aceptes. Revisa más a fondo.**

---

## Revisión Completa de Seguridad (5 minutos)

### 1. Credenciales y Secretos

- [ ] ❌ **NO hay passwords hardcoded**
  - Buscar patrones: `password = "..."`, `passwd = "..."`, `pwd = "..."`
  - Verificar que no hay credenciales en strings literales

- [ ] ❌ **NO hay API keys o tokens hardcoded**
  - Buscar: `api_key = "..."`, `token = "..."`, `secret = "..."`
  - Verificar variables de configuración

- [ ] ❌ **NO hay certificados o claves privadas**
  - Buscar: `.pem`, `.key`, `.p12`, contenido de certificados
  - Verificar que no están embebidos en código

- [ ] ✅ **Se usan variables de entorno para secretos**
  - Verificar: `os.getenv()`, `os.environ`, archivos `.env`
  - Confirmar que `.env` está en `.gitignore`

- [ ] ✅ **No se exponen secretos en logs**
  - Revisar llamadas a `print()`, `logger.info()`, etc.
  - Verificar que no se loggean valores sensibles

**Si encuentras credenciales o secretos:**
1. **NO aceptes la sugerencia**
2. **Reporta inmediatamente** si ya se aceptó código con secretos
3. **Usa versión segura** con variables de entorno

---

### 2. Datos Sensibles e Información Personal (PII)

- [ ] ❌ **NO contiene datos personales reales**
  - Emails reales, nombres reales, teléfonos reales
  - RUTs, documentos de identidad
  - Direcciones físicas reales

- [ ] ❌ **NO contiene información financiera**
  - Números de tarjeta de crédito
  - Información bancaria
  - Datos de transacciones reales

- [ ] ❌ **NO contiene información médica**
  - Historiales médicos
  - Información de salud
  - Datos protegidos por HIPAA (si aplica)

- [ ] ✅ **Usa datos mock/anónimos para ejemplos**
  - Emails: `test@example.com`, `usuario@test.com`
  - Nombres: `Usuario de Prueba`, `Test User`
  - Teléfonos: Números ficticios claramente identificables

**Si encuentras datos sensibles:**
- **NO aceptes la sugerencia**
- Si ya se aceptó, **remueve inmediatamente** y reemplaza con datos mock
- **Reporta** si los datos son de clientes reales

---

### 3. Configuración y Valores por Defecto

- [ ] ❌ **NO hay valores por defecto inseguros**
  - Passwords por defecto: `"admin"`, `"12345"`, `"password"`
  - Puertos expuestos sin autenticación
  - Permisos demasiado amplios por defecto

- [ ] ✅ **Configuración por defecto es segura**
  - Autenticación requerida
  - Permisos mínimos necesarios
  - No expone servicios innecesariamente

- [ ] ❌ **NO hay comentarios con información sensible**
  - Credenciales en comentarios
  - URLs de producción en comentarios
  - Información de infraestructura sensible

---

### 4. Vulnerabilidades de Código

- [ ] ❌ **NO hay riesgo de SQL Injection**
  - Si usa SQL, verificar uso de parámetros preparados
  - No concatenar strings directamente en queries

- [ ] ❌ **NO hay riesgo de XSS (Cross-Site Scripting)**
  - Si genera HTML, verificar sanitización
  - No renderizar input de usuario sin escapar

- [ ] ❌ **NO hay riesgo de Path Traversal**
  - Validar rutas de archivos
  - No permitir `../` en paths de usuario

- [ ] ❌ **NO hay riesgo de Command Injection**
  - Si ejecuta comandos del sistema, validar y sanitizar inputs
  - Usar métodos seguros de ejecución

- [ ] ✅ **Validación de entrada adecuada**
  - Validar tipos de datos
  - Validar rangos y formatos
  - Rechazar inputs inválidos apropiadamente

- [ ] ✅ **Manejo de errores seguro**
  - No exponer información sensible en mensajes de error
  - Logs no contienen información confidencial
  - Errores no revelan estructura interna

---

### 5. Autenticación y Autorización

- [ ] ✅ **Lógica de autenticación es segura**
  - No hay bypass de autenticación
  - Passwords no están en texto plano
  - Usa hashing seguro (bcrypt, argon2, no MD5/SHA1 sin salt)

- [ ] ✅ **Tokens y sesiones se manejan correctamente**
  - Tokens JWT tienen expiración
  - Sesiones tienen timeout
  - No se almacenan tokens en localStorage (si es web app)

- [ ] ✅ **Autorización se verifica adecuadamente**
  - Verificar permisos antes de operaciones sensibles
  - No confiar solo en el frontend para autorización
  - Validar en backend siempre

---

### 6. Políticas Corporativas

- [ ] ✅ **Código no está en lista de exclusiones**
  - Verificar `.copilotignore`
  - Confirmar que no es código legacy crítico
  - Verificar políticas de proyecto

- [ ] ✅ **Sigue estándares de seguridad del proyecto**
  - Revisa documentación de seguridad del proyecto
  - Sigue guías de codificación segura
  - Usa librerías y frameworks aprobados

- [ ] ✅ **No expone información de infraestructura**
  - URLs internas, IPs de servidores
  - Nombres de bases de datos, buckets, etc.
  - Configuración de red interna

---

## Auditoría Post-Aceptación

Si ya aceptaste código generado por IA, realiza esta auditoría:

### Revisión de Código Completo

- [ ] Revisa **todas** las líneas de código generado
- [ ] Busca patrones sospechosos manualmente
- [ ] Ejecuta herramientas de seguridad:
  - `bandit` (Python)
  - `eslint-plugin-security` (JavaScript)
  - `gosec` (Go)
  - `brakeman` (Ruby)

### Verificación de Dependencias

- [ ] Verifica que no se agregaron dependencias no autorizadas
- [ ] Revisa versiones de dependencias por vulnerabilidades conocidas
- [ ] Usa herramientas como `npm audit`, `pip-audit`, `safety`

### Tests de Seguridad

- [ ] Incluye tests que validen comportamientos de seguridad
- [ ] Prueba casos edge y maliciosos
- [ ] Verifica que validaciones funcionan correctamente

---

## Checklist Rápido de Auditoría (Imprimir)

```
┌─────────────────────────────────────────────────────────┐
│     CHECKLIST RÁPIDO - SEGURIDAD IA                     │
├─────────────────────────────────────────────────────────┤
│ □ Sin passwords/API keys hardcoded                      │
│ □ Sin datos personales reales                           │
│ □ Usa variables de entorno para secretos                │
│ □ Sin vulnerabilidades comunes (SQLi, XSS, etc.)        │
│ □ Validación de entrada adecuada                        │
│ □ Manejo de errores seguro                              │
│ □ Autenticación/autoriazación correcta                  │
│ □ No expone información sensible en logs                │
│ □ Sigue políticas corporativas                          │
│ □ Código no está en exclusiones                         │
└─────────────────────────────────────────────────────────┘
```

---

## Qué Hacer si Encuentras Problemas

### Problema Crítico de Seguridad Encontrado

**SI encuentras un problema de seguridad:**

1. **INMEDIATO**: No aceptes la sugerencia o revierte si ya se aceptó
2. **URGENTE**: Reporta a `seguridad@empresa.com` o contacto de seguridad
3. **DOCUMENTA**: Qué código, qué herramienta, qué datos, qué riesgo
4. **CONTÉN**: Si ya está en producción, toma medidas de contención
5. **COMUNÍCATE**: Alerta a tu equipo si es necesario

### Template de Reporte de Incidente

```
INCIDENTE DE SEGURIDAD - IA/COPILOT

Fecha/Hora: [timestamp]
Reportado por: [tu nombre]
Herramienta: GitHub Copilot / Azure OpenAI / [otra]

DESCRIPCIÓN:
[Qué pasó, qué código se generó, qué datos se expusieron]

CÓDIGO AFECTADO:
[Archivo, función, líneas específicas]

RIESGO/IMPACTO:
[Qué tan grave, qué sistemas afectados, qué datos en riesgo]

ACCIÓN TOMADA:
[Qué se hizo para contener/corregir]

ESTADO:
[ ] Contenido
[ ] Corregido
[ ] Investigación en curso
[ ] Cerrado

SEVERIDAD:
[ ] Crítico - Acción inmediata requerida
[ ] Alto - Corregir en 24 horas
[ ] Medio - Corregir en próxima sprint
[ ] Bajo - Corregir cuando sea posible
```

---

## Herramientas de Verificación

### Para Python

```bash
# Bandit - Security linter
pip install bandit
bandit -r src/

# Safety - Check dependencies
pip install safety
safety check

# pip-audit - Audit dependencies
pip install pip-audit
pip-audit
```

### Para JavaScript/TypeScript

```bash
# npm audit
npm audit

# eslint-plugin-security
npm install --save-dev eslint-plugin-security
```

### Pre-commit Hooks

Configura hooks para validar automáticamente:

```yaml
# .pre-commit-config.yaml
repos:
  - repo: https://github.com/PyCQA/bandit
    hooks:
      - id: bandit
        args: ["-r", "src/"]
  - repo: https://github.com/Yelp/detect-secrets
    hooks:
      - id: detect-secrets
```

---

## Mejores Prácticas Continuas

### Desarrollo Seguro

- ✅ **Nunca** pegar código con datos sensibles en herramientas de IA
- ✅ **Siempre** usar datos mock/anónimos para ejemplos
- ✅ **Siempre** revisar código generado antes de aceptar
- ✅ **Siempre** usar variables de entorno para secretos
- ✅ **Siempre** validar inputs de usuario
- ✅ **Siempre** manejar errores de forma segura

### Educación Continua

- ✅ Mantente actualizado sobre vulnerabilidades comunes
- ✅ Revisa OWASP Top 10 regularmente
- ✅ Participa en entrenamientos de seguridad
- ✅ Comparte aprendizajes con el equipo

---

## Recursos Adicionales

- OWASP Top 10: https://owasp.org/www-project-top-ten/
- CWE Top 25: https://cwe.mitre.org/top25/
- `checklists/pr-ia-checklist.md` - Checklist completo de PR
- Políticas corporativas de seguridad (consultar con seguridad)

---

**Versión**: 1.0  
**Última actualización**: 2026-01-15  
**Aplicable a**: Todos los niveles  
**Responsabilidad**: Todos los desarrolladores
