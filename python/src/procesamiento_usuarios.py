def validar_email(email: str) -> bool:

"""Valida formato de email básico"""

import re

return bool(re.match(r'^[\w\.-]+@[\w\.-]+\.\w+$', email))

def normalizar_nombre(nombre: str) -> str:

"""Capitaliza primera letra de cada palabra"""

return nombre.title()

def filtrar_duplicados_por_email(usuarios: list) -> list:

"""Elimina duplicados manteniendo primero por email"""

vistos = set()

resultado = []

for usuario in usuarios:

if usuario['email'] not in vistos:

vistos.add(usuario['email'])

resultado.append(usuario)

return resultado

# Procesar lista completa de usuarios usando funciones de validación y normalización

# - Validar emails

# - Normalizar nombres

# - Filtrar duplicados

# - Retornar lista ordenada alfabéticamente

def procesar_usuarios_completos(usuarios: list) -> list:
    usuarios_validos = []

    for usuario in usuarios:

        if validar_email(usuario['email']):

            usuario['nombre'] = normalizar_nombre(usuario['nombre'])

            usuarios_validos.append(usuario)

    usuarios_sin_duplicados = filtrar_duplicados_por_email(usuarios_validos)

    return sorted(usuarios_sin_duplicados, key=lambda x: x['nombre'])
