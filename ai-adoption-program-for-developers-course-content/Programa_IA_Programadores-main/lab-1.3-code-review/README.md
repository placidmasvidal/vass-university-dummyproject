# Lab 1.3: Code Review Asistido - Identificar Problemas en PR Sugerida por IA

## Objetivo

Realizar un code review completo de una Pull Request que fue generada usando IA (Copilot), identificando problemas de seguridad, calidad, lógica y mejores prácticas.

## Duración Estimada

**2-3 horas** (trabajo asíncrono entre sesiones)

## Prerequisitos

- ✅ Sesión 1.3 completada (Seguridad y Políticas)
- ✅ Labs 1.1 y 1.2 completados
- ✅ Conocimiento de code review practices
- ✅ Checklist de seguridad IA aprendido

## Contexto del Ejercicio

Un compañero de equipo creó una PR para agregar funcionalidad de autenticación usando Copilot. Tu tarea es hacer code review usando las técnicas aprendidas, identificando:

1. Problemas de seguridad
2. Problemas de calidad de código
3. Problemas de lógica
4. Incumplimiento de estándares
5. Mejoras posibles

Debes usar el checklist de auditoría aprendido y proporcionar feedback constructivo.

---

## PR para Revisar

### Descripción de la PR

**Título**: "Agregar módulo de autenticación JWT"

**Autor**: [Compañero de equipo]

**Descripción**:
"Implementé autenticación JWT usando Copilot. El módulo incluye login, registro y validación de tokens."

### Archivos Cambiados

1. `auth.py` - Módulo principal de autenticación
2. `test_auth.py` - Tests unitarios
3. `config.py` - Configuración (modificado)

---

## Instrucciones Paso a Paso

### Paso 1: Revisión Inicial y Contexto (15 min)

**Tarea:**
1. Lee la descripción de la PR completamente
2. Revisa los archivos cambiados (ver código abajo)
3. Entiende qué se intenta hacer
4. Identifica el scope del cambio

**Archivos a revisar:**
- `auth.py` (proporcionado abajo)
- `test_auth.py` (proporcionado abajo)
- `config.py` (cambios documentados)

### Paso 2: Revisión de Seguridad (30 min)

**Tarea:**
Usa el checklist de seguridad de la sesión 1.3:

#### Checklist de Seguridad

**Credenciales y Secretos:**
- [ ] ¿Hay passwords, API keys, tokens hardcoded?
- [ ] ¿Se usan variables de entorno correctamente?
- [ ] ¿Los secretos están en `.gitignore`?

**Datos Sensibles:**
- [ ] ¿Se exponen datos personales?
- [ ] ¿Los logs contienen información sensible?
- [ ] ¿Las respuestas de error exponen información?

**Autenticación y Autorización:**
- [ ] ¿La lógica de autenticación es segura?
- [ ] ¿Los tokens JWT se manejan correctamente?
- [ ] ¿Hay vulnerabilidades conocidas (SQL injection, XSS, etc.)?

**Configuración:**
- [ ] ¿La configuración por defecto es segura?
- [ ] ¿Los valores sensibles tienen valores por defecto inseguros?

**Documenta cada problema encontrado:**
- Ubicación (archivo, línea)
- Tipo de problema
- Severidad (Crítico, Alto, Medio, Bajo)
- Descripción del problema
- Recomendación de solución

### Paso 3: Revisión de Calidad de Código (30 min)

**Checklist de Calidad:**

**Estructura y Organización:**
- [ ] ¿El código está bien organizado?
- [ ] ¿Las funciones son cohesivas?
- [ ] ¿Hay duplicación de código?

**Legibilidad:**
- [ ] ¿Los nombres son descriptivos?
- [ ] ¿Hay documentación adecuada?
- [ ] ¿El código es fácil de entender?

**Buenas Prácticas:**
- [ ] ¿Sigue PEP 8 (Python)?
- [ ] ¿Usa type hints?
- [ ] ¿Maneja errores apropiadamente?
- [ ] ¿Tiene validaciones de entrada?

**Lógica:**
- [ ] ¿La lógica es correcta?
- [ ] ¿Maneja casos edge?
- [ ] ¿Hay bugs potenciales?

### Paso 4: Revisión de Tests (20 min)

**Checklist de Tests:**

- [ ] ¿Los tests cubren funcionalidad principal?
- [ ] ¿Hay tests para casos edge?
- [ ] ¿Hay tests para casos de error?
- [ ] ¿Los tests son mantenibles?
- [ ] ¿La cobertura es adecuada?
- [ ] ¿Los tests son independientes?

### Paso 5: Revisión de Estándares y Políticas (15 min)

**Checklist de Políticas:**

- [ ] ¿Sigue estándares del proyecto?
- [ ] ¿Sigue convenciones de nombres?
- [ ] ¿Está permitido usar IA en este código? (no está en exclusiones)
- [ ] ¿La PR tiene descripción adecuada?
- [ ] ¿Los commits están bien estructurados?

### Paso 6: Documentar Feedback (30 min)

**Tarea:**
Crea un documento de code review completo:

**Crea archivo:** `code_review_lab_1.3.md`

**Estructura sugerida:**
```markdown
# Code Review: PR - Agregar módulo de autenticación JWT

## Resumen Ejecutivo
- Estado general: [Aprobado con cambios / Requiere cambios / Rechazado]
- Problemas críticos encontrados: [número]
- Problemas de seguridad: [número]
- Problemas de calidad: [número]

## Problemas Críticos (Deben corregirse antes de merge)

### [Título del problema]
- **Archivo**: `auth.py:45`
- **Severidad**: Crítico
- **Descripción**: [Descripción detallada]
- **Impacto**: [Qué puede pasar si no se corrige]
- **Recomendación**: [Cómo corregirlo]
- **Ejemplo de código corregido**: [Si aplica]

## Problemas de Seguridad

[Repetir estructura para cada problema de seguridad]

## Problemas de Calidad

[Repetir estructura para cada problema de calidad]

## Mejoras Sugeridas (Opcionales pero recomendadas)

[Mejoras que no son bloqueantes pero mejoran el código]

## Puntos Positivos

[Destacar qué está bien hecho]

## Recomendación Final

[Estado: Aprobar / Aprobar con cambios / Rechazar]
[Razón de la recomendación]
```

### Paso 7: Usar Copilot para Mejorar el Review (20 min)

**Tarea:**
Usa Copilot para ayudarte a:

1. **Generar ejemplos de código corregido:**
```python
# Dado este código con problema de seguridad:
# [pegar código problemático]
# 
# Generar versión corregida que:
# - Use variables de entorno
# - Maneje errores apropiadamente
# - Siga mejores prácticas de seguridad
```

2. **Identificar problemas adicionales:**
```python
# Revisar este código de autenticación y identificar:
# - Vulnerabilidades de seguridad comunes
# - Problemas de lógica
# - Mejores prácticas no seguidas
# [pegar código a revisar]
```

3. **Generar tests faltantes:**
```python
# Generar tests unitarios para cubrir estos casos edge:
# - Token expirado
# - Token inválido
# - Usuario no existe
# - Password incorrecto
# - Rate limiting
```

---

## Código de la PR (Para Revisar)

### auth.py

```python
# auth.py - Módulo de autenticación JWT
import jwt
import hashlib
from datetime import datetime, timedelta

# Configuración
SECRET_KEY = "my-secret-key-12345"  # Para producción usar variable de entorno
ALGORITHM = "HS256"
TOKEN_EXPIRATION_HOURS = 24

# Base de datos simulada (en producción sería real)
users_db = {
    "admin": {
        "password": "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8",  # "password" hasheado
        "email": "admin@company.com",
        "role": "admin"
    }
}

def hash_password(password):
    """Hash password usando SHA256"""
    return hashlib.sha256(password.encode()).hexdigest()

def create_token(username, password):
    """Crear token JWT para usuario"""
    # Verificar credenciales
    if username not in users_db:
        return None
    
    user = users_db[username]
    if user["password"] != hash_password(password):
        return None
    
    # Crear token
    payload = {
        "username": username,
        "email": user["email"],
        "role": user["role"],
        "exp": datetime.utcnow() + timedelta(hours=TOKEN_EXPIRATION_HOURS)
    }
    
    token = jwt.encode(payload, SECRET_KEY, algorithm=ALGORITHM)
    return token

def verify_token(token):
    """Verificar y decodificar token JWT"""
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        return payload
    except jwt.ExpiredSignatureError:
        print("Token expirado")
        return None
    except jwt.InvalidTokenError:
        print("Token inválido")
        return None

def register_user(username, password, email):
    """Registrar nuevo usuario"""
    if username in users_db:
        return False
    
    users_db[username] = {
        "password": hash_password(password),
        "email": email,
        "role": "user"
    }
    return True

def login(username, password):
    """Login de usuario"""
    token = create_token(username, password)
    if token:
        return {"token": token, "user": username}
    else:
        return {"error": "Credenciales inválidas"}
```

### test_auth.py

```python
# test_auth.py - Tests para módulo de autenticación
import pytest
from auth import create_token, verify_token, register_user, login, hash_password

def test_create_token_success():
    """Test crear token exitoso"""
    token = create_token("admin", "password")
    assert token is not None

def test_create_token_invalid_user():
    """Test crear token con usuario inválido"""
    token = create_token("nonexistent", "password")
    assert token is None

def test_verify_token_success():
    """Test verificar token válido"""
    token = create_token("admin", "password")
    payload = verify_token(token)
    assert payload is not None
    assert payload["username"] == "admin"

def test_register_user():
    """Test registrar usuario"""
    result = register_user("newuser", "password123", "new@test.com")
    assert result is True
```

### Cambios en config.py

```python
# config.py (cambios agregados)
AUTH_ENABLED = True
JWT_SECRET = "my-secret-key-12345"  # Mismo secret hardcoded
```

---

## Entregables

Al finalizar el lab, debes entregar:

1. ✅ `code_review_lab_1.3.md` - Code review completo documentado
2. ✅ Lista de problemas encontrados (tabla resumen)
3. ✅ Ejemplos de código corregido (para problemas críticos)
4. ✅ Recomendación final (Aprobar / Aprobar con cambios / Rechazar)
5. ✅ Reflexión sobre el proceso de review asistido

---

## Criterios de Aceptación

El lab se considera completo cuando:

- [ ] Se identificaron al menos 5 problemas de seguridad
- [ ] Se identificaron al menos 5 problemas de calidad
- [ ] Todos los problemas están documentados con ubicación, severidad y recomendación
- [ ] Se proporcionaron ejemplos de código corregido para problemas críticos
- [ ] Se usó checklist de seguridad sistemáticamente
- [ ] Se usó Copilot para ayudar en el review (identificar problemas, generar correcciones)
- [ ] Review está estructurado y profesional
- [ ] Recomendación final está justificada

---

## Evaluación

### Rúbrica de Evaluación

| Criterio | Excelente (4) | Bueno (3) | Satisfactorio (2) | Necesita Mejora (1) |
|----------|---------------|-----------|-------------------|---------------------|
| **Identificación Problemas Seguridad** | Identificó 8+ problemas, todos críticos/altos | Identificó 5-7 problemas relevantes | Identificó 3-4 problemas básicos | Identificó <3 problemas o muchos irrelevantes |
| **Identificación Problemas Calidad** | Identificó 8+ problemas relevantes | Identificó 5-7 problemas relevantes | Identificó 3-4 problemas básicos | Identificó <3 problemas |
| **Documentación Review** | Review excepcional, muy detallado, bien estructurado | Review bueno, bien documentado | Review básico con documentación adecuada | Review superficial, falta detalle |
| **Uso de Checklist** | Usó checklist sistemáticamente, muy completo | Usó checklist bien, mayoría de items | Usó checklist básicamente | No usó checklist o uso superficial |
| **Uso de Copilot** | Uso excelente para identificar problemas y generar correcciones | Uso bueno para algunas tareas | Uso básico | No usó Copilot efectivamente |

**Puntuación mínima para aprobar: 12/20 (60%)**

---

## Problemas Clave a Identificar (Guía)

### Seguridad (Debes encontrar estos y más):

1. ❌ **SECRET_KEY hardcoded** (crítico)
2. ❌ **Password hashing inseguro** (SHA256 sin salt es vulnerable)
3. ❌ **Mensajes de error informativos** (exponen información)
4. ❌ **No rate limiting** (vulnerable a brute force)
5. ❌ **Token en logs** (riesgo si se loggea)
6. ❌ **No validación de entrada** (SQL injection risk si se usa DB)
7. ❌ **Secret duplicado en config.py**

### Calidad (Debes encontrar estos y más):

1. ❌ **Base de datos en memoria** (no persistente)
2. ❌ **Código duplicado** (SECRET_KEY en múltiples lugares)
3. ❌ **Falta validación de email** en registro
4. ❌ **Tests incompletos** (falta casos edge, errores)
5. ❌ **Falta type hints**
6. ❌ **Falta docstrings completos**
7. ❌ **Manejo de errores básico** (solo prints)

---

## Tips y Ayuda

### Si te quedas atascado:

1. **No encuentras problemas de seguridad:**
   - Revisa sesión 1.3 sobre seguridad
   - Busca patrones comunes: hardcoded secrets, hashing inseguro, validación faltante
   - Usa Copilot: "Identificar vulnerabilidades de seguridad en este código"

2. **No sabes cómo corregir un problema:**
   - Usa Copilot para generar código corregido
   - Consulta documentación de seguridad (OWASP, etc.)
   - Pregunta a facilitador

3. **Review muy superficial:**
   - Usa checklist sistemáticamente
   - Revisa línea por línea
   - Piensa en casos edge y escenarios de ataque

### Recursos Adicionales

- Revisa sesión 1.3 sobre seguridad
- Consulta `checklists/seguridad-ia.md`
- Consulta `checklists/pr-ia-checklist.md` (si está disponible)
- OWASP Top 10 para vulnerabilidades comunes

---

**Versión**: 1.0  
