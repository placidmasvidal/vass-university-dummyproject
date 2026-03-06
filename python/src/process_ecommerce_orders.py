# Procesar pedidos e-commerce:
# Validar stock disponible para cada item
# calcular el total con impuestos (19% IVA)
# Aplicar descuentos promocionales si aplican
# Generar número de seguimiento único en formato: PED-YYYYMMDD-XXXX
# Retornar objeto Pedido con estado "pendiente"

# El lenguaje usado es Python 3.10+ con type hints obligatorios
# hay que seguir la convencion PEP 8
# Hay que utilizar pandas DataFrame para manejar los datos de los pedidos y el stock
# Hay que emplead pydantic para la validación de esquemas

# Un ejemplo de uso seria:
# procesar_pedidos([{'item': 'A', 'cantidad': 2}], usuario='juan') -> Pedido(pendiente, total=...)

# Hay que tener en cuenta los siguientes supuestos:
# La lista de pedidos puede estar vacía
# Los datos pueden contener items con cantidad cero
# No asumir que todos los usuarios son válidos

# Retorna: Pedido, objeto con estado y total calculado
# lanza excepciones en siguientes casos:
# ValueError: si stock insuficiente
#ValueError si usuario no existe

#Crea el código necesario para implementar esta funcionalidad, asegurándote de incluir validaciones y manejo de excepciones según lo descrito.
from datetime import datetime
import pandas as pd
from pydantic import BaseModel, ValidationError, validator
import uuid

class Pedido(BaseModel):
    estado: str
    total: float
    seguimiento: str

    @validator('estado')
    def validar_estado(cls, v):
        if v not in ['pendiente', 'procesado', 'enviado']:
            raise ValueError("Estado debe ser 'pendiente', 'procesado' o 'enviado'.")
        return v

def procesar_pedidos(pedidos: list[dict], usuario: str) -> Pedido:
    # Validar que la lista de pedidos no esté vacía
    if not pedidos:
        raise ValueError("La lista de pedidos no puede estar vacía.")

    # Simulación de stock disponible (en un caso real, esto vendría de una base de datos)
    stock_disponible = {'A': 10, 'B': 5, 'C': 0}  # Ejemplo de stock

    total = 0.0

    for pedido in pedidos:
        item = pedido.get('item')
        cantidad = pedido.get('cantidad', 0)

        # Validar que el item exista en el stock
        if item not in stock_disponible:
            raise ValueError(f"El item '{item}' no existe en el stock.")

        # Validar que la cantidad sea mayor a cero
        if cantidad <= 0:
            raise ValueError(f"La cantidad para el item '{item}' debe ser mayor a cero.")

        # Validar que haya suficiente stock
        if stock_disponible[item] < cantidad:
            raise ValueError(f"Stock insuficiente para el item '{item}'. Disponible: {stock_disponible[item]}, Requerido: {cantidad}.")

        # Simulación de precios (en un caso real, esto vendría de una base de datos)
        precios = {'A': 100.0, 'B': 50.0, 'C': 20.0}  # Ejemplo de precios

        total += precios[item] * cantidad

    # Calcular total con impuestos (19% IVA)
    total_con_iva = total * 1.19

    # Generar número de seguimiento único
    seguimiento = f"PED-{datetime.now().strftime('%Y%m%d')}-{str(uuid.uuid4())[:4]}"

    return Pedido(estado="pendiente", total=round(total_con_iva, 2), seguimiento=seguimiento)


