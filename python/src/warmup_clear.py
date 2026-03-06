# Calcular suma total de lista de precios
# - Validar que precios sea lista no vacía
# - Filtrar valores None o negativos
# - Retornar suma redondeada a 2 decimales
# Ejemplo: calcular_total([10.5, 20.0, 30.75]) -> 61.25 def calcular_total(precios):
    if not isinstance(precios, list) or len(precios) == 0:
        raise ValueError("La lista de precios debe ser no vacía.")

    total = sum(p for p in precios if isinstance(p, (int, float)) and p >= 0)

    return round(total, 2)
