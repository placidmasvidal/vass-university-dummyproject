"""
Módulo: Procesamiento de Pedidos E-commerce
Descripción: Implementación completa de validación, cálculo y generación de pedidos.
Requisitos: Python 3.10+, pandas, pydantic, PEP 8, type hints obligatorios.
"""

import re
from datetime import datetime
from typing import Optional
import uuid

import pandas as pd
from pydantic import BaseModel, field_validator, model_validator


# ============================================================================
# CATÁLOGOS EN MEMORIA (Definición de supuestos)
# ============================================================================

USUARIOS_VALIDOS: set[str] = {"juan", "maria", "carlos", "ana"}

CATALOGO_PRECIOS: dict[str, float] = {
    "A": 100.0,
    "B": 50.0,
    "C": 20.0,
}

STOCK_DISPONIBLE: dict[str, int] = {
    "A": 10,
    "B": 5,
    "C": 15,
}

# Política de descuentos: aplicar X% si subtotal >= monto
POLITICA_DESCUENTOS: list[tuple[float, float]] = [
    (200.0, 0.10),  # 10% si subtotal >= 200
    (500.0, 0.15),  # 15% si subtotal >= 500
    (1000.0, 0.20),  # 20% si subtotal >= 1000
]

IVA_PORCENTAJE: float = 0.19


# ============================================================================
# MODELOS PYDANTIC
# ============================================================================

class ItemPedido(BaseModel):
    """Validador para cada línea de pedido."""
    item: str
    cantidad: int

    @field_validator("item")
    @classmethod
    def validar_item(cls, v: str) -> str:
        """Validar que el item existe en catálogo."""
        if v not in CATALOGO_PRECIOS:
            raise ValueError(f"Item '{v}' no existe en catálogo.")
        return v

    @field_validator("cantidad")
    @classmethod
    def validar_cantidad(cls, v: int) -> int:
        """Validar que cantidad es no-negativa."""
        if v < 0:
            raise ValueError("Cantidad no puede ser negativa.")
        return v


class Pedido(BaseModel):
    """Modelo de salida: Pedido procesado."""
    estado: str
    total: float
    seguimiento: str
    subtotal: float
    descuento_aplicado: float
    iva: float

    @field_validator("estado")
    @classmethod
    def validar_estado(cls, v: str) -> str:
        """Validar que estado sea válido."""
        if v not in ["pendiente", "procesado", "enviado"]:
            raise ValueError(
                "Estado debe ser 'pendiente', 'procesado' o 'enviado'."
            )
        return v

    @field_validator("total")
    @classmethod
    def validar_total(cls, v: float) -> float:
        """Validar que total sea no-negativo y redondeado a 2 decimales."""
        if v < 0:
            raise ValueError("Total no puede ser negativo.")
        return round(v, 2)

    @field_validator("seguimiento")
    @classmethod
    def validar_seguimiento(cls, v: str) -> str:
        """Validar que seguimiento cumple regex: PED-YYYYMMDD-XXXX."""
        if not re.match(r"^PED-\d{8}-[A-Z0-9]{4}$", v):
            raise ValueError(
                f"Seguimiento '{v}' no cumple formato: PED-YYYYMMDD-XXXX"
            )
        return v


# ============================================================================
# FUNCIÓN PRINCIPAL
# ============================================================================

def procesar_pedidos(
    pedidos: list[dict],
    usuario: str,
) -> Pedido:
    """
    Procesa un pedido e-commerce validando stock, aplicando descuentos e IVA.

    Args:
        pedidos: Lista de diccionarios con keys 'item' y 'cantidad'.
        usuario: Nombre del usuario solicitante.

    Returns:
        Pedido: Objeto con estado, totales y número de seguimiento.

    Raises:
        ValueError: Si usuario no existe, stock insuficiente o datos inválidos.
    """

    # 1. VALIDAR USUARIO
    if usuario not in USUARIOS_VALIDOS:
        raise ValueError(
            f"Usuario '{usuario}' no existe. Válidos: {USUARIOS_VALIDOS}"
        )

    # 2. MANEJAR LISTA VACÍA
    if not pedidos:
        # Crear pedido válido con total 0
        seguimiento = _generar_seguimiento()
        return Pedido(
            estado="pendiente",
            total=0.0,
            seguimiento=seguimiento,
            subtotal=0.0,
            descuento_aplicado=0.0,
            iva=0.0,
        )

    # 3. NORMALIZAR Y VALIDAR LÍNEAS DE PEDIDO
    df_pedidos = pd.DataFrame(pedidos)

    # Filtrar líneas con cantidad > 0
    df_pedidos = df_pedidos[df_pedidos["cantidad"] > 0]

    if df_pedidos.empty:
        # Todas las líneas tenían cantidad 0
        seguimiento = _generar_seguimiento()
        return Pedido(
            estado="pendiente",
            total=0.0,
            seguimiento=seguimiento,
            subtotal=0.0,
            descuento_aplicado=0.0,
            iva=0.0,
        )

    # 4. AGREGAR ITEMS REPETIDOS
    df_pedidos = (
        df_pedidos.groupby("item", as_index=False)
        .agg({"cantidad": "sum"})
    )

    # 5. VALIDAR CADA LÍNEA CON PYDANTIC
    for _, fila in df_pedidos.iterrows():
        ItemPedido(item=fila["item"], cantidad=fila["cantidad"])

    # 6. VALIDAR STOCK TOTAL
    subtotal = 0.0
    for _, fila in df_pedidos.iterrows():
        item = fila["item"]
        cantidad = fila["cantidad"]
        precio = CATALOGO_PRECIOS[item]

        if STOCK_DISPONIBLE[item] < cantidad:
            raise ValueError(
                f"Stock insuficiente para item '{item}'. "
                f"Disponible: {STOCK_DISPONIBLE[item]}, Requerido: {cantidad}."
            )

        subtotal += precio * cantidad

    # 7. APLICAR DESCUENTOS
    descuento_aplicado = _calcular_descuento(subtotal)
    subtotal_con_descuento = subtotal - descuento_aplicado

    # 8. CALCULAR IVA Y TOTAL
    iva = subtotal_con_descuento * IVA_PORCENTAJE
    total = subtotal_con_descuento + iva

    # 9. GENERAR SEGUIMIENTO
    seguimiento = _generar_seguimiento()

    # 10. RETORNAR PEDIDO VALIDADO
    return Pedido(
        estado="pendiente",
        total=round(total, 2),
        seguimiento=seguimiento,
        subtotal=round(subtotal, 2),
        descuento_aplicado=round(descuento_aplicado, 2),
        iva=round(iva, 2),
    )


# ============================================================================
# FUNCIONES UTILIDAD
# ============================================================================

def _generar_seguimiento() -> str:
    """
    Genera número de seguimiento único con formato: PED-YYYYMMDD-XXXX.

    Returns:
        str: Número de seguimiento válido.
    """
    fecha = datetime.now().strftime("%Y%m%d")
    codigo_aleatorio = str(uuid.uuid4()).replace("-", "").upper()[:4]
    return f"PED-{fecha}-{codigo_aleatorio}"


def _calcular_descuento(subtotal: float) -> float:
    """
    Calcula descuento aplicable según política.

    Args:
        subtotal: Monto antes de descuento.

    Returns:
        float: Monto a descontar.
    """
    descuento_porcentaje = 0.0

    for monto_minimo, porcentaje in POLITICA_DESCUENTOS:
        if subtotal >= monto_minimo:
            descuento_porcentaje = porcentaje

    return subtotal * descuento_porcentaje


# ============================================================================
# EJEMPLO DE USO
# ============================================================================

if __name__ == "__main__":
    # Caso 1: Happy path
    print("=== CASO 1: Happy Path ===")
    pedido1 = procesar_pedidos(
        [{"item": "A", "cantidad": 2}],
        usuario="juan",
    )
    print(f"Pedido: {pedido1}\n")

    # Caso 2: Múltiples items
    print("=== CASO 2: Múltiples Items ===")
    pedido2 = procesar_pedidos(
        [
            {"item": "A", "cantidad": 1},
            {"item": "B", "cantidad": 2},
            {"item": "C", "cantidad": 5},
        ],
        usuario="maria",
    )
    print(f"Pedido: {pedido2}\n")

    # Caso 3: Items repetidos (se agregan)
    print("=== CASO 3: Items Repetidos ===")
    pedido3 = procesar_pedidos(
        [
            {"item": "A", "cantidad": 2},
            {"item": "A", "cantidad": 3},
        ],
        usuario="carlos",
    )
    print(f"Pedido: {pedido3}\n")

    # Caso 4: Con descuento
    print("=== CASO 4: Con Descuento ===")
    pedido4 = procesar_pedidos(
        [
            {"item": "A", "cantidad": 5},
            {"item": "B", "cantidad": 3},
        ],
        usuario="ana",
    )
    print(f"Pedido: {pedido4}\n")
