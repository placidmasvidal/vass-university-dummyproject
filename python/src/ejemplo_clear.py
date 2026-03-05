# Calcular comisión de venta basada en monto y tipo de cliente

#

# CONTEXTO: Sistema de ventas con comisiones diferenciadas por tipo de cliente

#

# REGLAS DE NEGOCIO:

# - Cliente Premium (tipo='premium'): 15% comisión

# - Cliente Regular (tipo='regular'): 10% comisión

# - Cliente Nuevo (tipo='nuevo'): 5% comisión, máximo $500

#

# LENGUAJE: Python 3.10+, type hints, PEP 8, validaciones con ValueError

#

# PARÁMETROS:

# monto (float): Monto de la venta, debe ser > 0

# tipo_cliente (str): 'premium', 'regular' o 'nuevo'

#

# RETORNA:

# float: Comisión calculada, nunca negativa, redondeada a 2 decimales

#

# EXCEPCIONES:

# ValueError: Si tipo_cliente no es válido o monto <= 0

#

# EJEMPLOS:

# >>> calcular_comision(1000.0, 'premium')

# 150.0

# >>> calcular_comision(500.0, 'regular')

# 50.0

# >>> calcular_comision(20000.0, 'nuevo') # Máximo $500

# 500.0

def calcular_comision(monto, tipo_cliente):
    if monto <= 0:
        raise ValueError("El monto debe ser mayor a 0.")

    tipo_cliente = tipo_cliente.lower()

    if tipo_cliente == 'premium':
        comision = monto * 0.15
    elif tipo_cliente == 'regular':
        comision = monto * 0.10
    elif tipo_cliente == 'nuevo':
        comision = min(monto * 0.05, 500)
    else:
        raise ValueError("Tipo de cliente no válido. Debe ser 'premium', 'regular' o 'nuevo'.")

    return round(max(comision, 0), 2)
