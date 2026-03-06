"""
Tests para procesar_pedidos: cubre casos felices, excepciones y edge cases.
Requisitos: pytest, python 3.10+
"""

import re
import pytest
import sys
from pathlib import Path

# Agregar src al path para importar el módulo
sys.path.insert(0, str(Path(__file__).parent.parent / "src"))

from process_ecommerce_orders_clear import (
    procesar_pedidos,
    Pedido,
    ItemPedido,
    USUARIOS_VALIDOS,
    CATALOGO_PRECIOS,
    STOCK_DISPONIBLE,
)


# ============================================================================
# TESTS: CASOS FELICES (HAPPY PATH)
# ============================================================================

def test_procesar_pedidos_caso_simple():
    """Test: Un item, usuario válido, stock suficiente."""
    resultado = procesar_pedidos(
        [{"item": "A", "cantidad": 2}],
        usuario="juan",
    )

    assert isinstance(resultado, Pedido)
    assert resultado.estado == "pendiente"
    assert resultado.subtotal == 200.0  # 2 * 100

    # Con la política actual hay 10% de descuento a partir de 200
    assert resultado.descuento_aplicado == 20.0
    assert resultado.iva == 34.20
    assert resultado.total == 214.20


def test_procesar_pedidos_multiples_items():
    """Test: Múltiples items diferentes."""
    resultado = procesar_pedidos(
        [
            {"item": "A", "cantidad": 1},
            {"item": "B", "cantidad": 2},
        ],
        usuario="maria",
    )

    subtotal_esperado = 100.0 + (50.0 * 2)  # 100 + 100 = 200
    assert resultado.subtotal == subtotal_esperado
    assert resultado.estado == "pendiente"


def test_procesar_pedidos_todos_usuarios_validos():
    """Test: Verificar que todos los usuarios válidos funcionan."""
    for usuario in USUARIOS_VALIDOS:
        resultado = procesar_pedidos(
            [{"item": "A", "cantidad": 1}],
            usuario=usuario,
        )
        assert resultado.estado == "pendiente"
        assert resultado.total > 0


def test_procesar_pedidos_con_descuento_10():
    """Test: Subtotal >= 200 debe aplicar 10% descuento."""
    resultado = procesar_pedidos(
        [
            {"item": "A", "cantidad": 2},  # 200
        ],
        usuario="juan",
    )

    assert resultado.subtotal == 200.0
    assert resultado.descuento_aplicado == 20.0  # 10% de 200
    assert resultado.total == round((200.0 - 20.0) * 1.19, 2)


def test_procesar_pedidos_con_descuento_15():
    """Test: Subtotal >= 500 debe aplicar 15% descuento."""
    resultado = procesar_pedidos(
        [
            {"item": "A", "cantidad": 5},  # 500
        ],
        usuario="juan",
    )

    assert resultado.subtotal == 500.0
    assert resultado.descuento_aplicado == 75.0  # 15% de 500
    assert resultado.total == round((500.0 - 75.0) * 1.19, 2)


# ============================================================================
# TESTS: EDGE CASES
# ============================================================================

def test_procesar_pedidos_lista_vacia():
    """Test: Lista vacía debe crear pedido válido con total 0."""
    resultado = procesar_pedidos(
        [],
        usuario="juan",
    )

    assert isinstance(resultado, Pedido)
    assert resultado.estado == "pendiente"
    assert resultado.total == 0.0
    assert resultado.subtotal == 0.0
    assert resultado.descuento_aplicado == 0.0


def test_procesar_pedidos_cantidad_cero():
    """Test: Cantidad 0 debe ignorarse (no contar en pedido)."""
    resultado = procesar_pedidos(
        [
            {"item": "A", "cantidad": 0},
            {"item": "B", "cantidad": 1},
        ],
        usuario="juan",
    )

    # Solo B (1 * 50 = 50)
    assert resultado.subtotal == 50.0


def test_procesar_pedidos_todos_cantidad_cero():
    """Test: Si todos los items tienen cantidad 0, total debe ser 0."""
    resultado = procesar_pedidos(
        [
            {"item": "A", "cantidad": 0},
            {"item": "B", "cantidad": 0},
        ],
        usuario="juan",
    )

    assert resultado.total == 0.0


def test_procesar_pedidos_items_repetidos():
    """Test: Items repetidos se deben agregar."""
    resultado = procesar_pedidos(
        [
            {"item": "A", "cantidad": 2},
            {"item": "A", "cantidad": 3},
        ],
        usuario="juan",
    )

    # Debe sumar: 2 + 3 = 5
    assert resultado.subtotal == 500.0  # 5 * 100


def test_procesar_pedidos_items_repetidos_multiples():
    """Test: Múltiples repeticiones de varios items."""
    resultado = procesar_pedidos(
        [
            {"item": "A", "cantidad": 1},
            {"item": "B", "cantidad": 1},
            {"item": "A", "cantidad": 1},
            {"item": "B", "cantidad": 1},
        ],
        usuario="juan",
    )

    # A: 1 + 1 = 2 -> 200
    # B: 1 + 1 = 2 -> 100
    # Total: 300
    assert resultado.subtotal == 300.0


# ============================================================================
# TESTS: EXCEPCIONES - ERRORES ESPERADOS
# ============================================================================

def test_procesar_pedidos_usuario_invalido():
    """Test: Usuario no válido debe lanzar ValueError."""
    with pytest.raises(ValueError, match="Usuario 'pepe' no existe"):
        procesar_pedidos(
            [{"item": "A", "cantidad": 1}],
            usuario="pepe",
        )


def test_procesar_pedidos_stock_insuficiente():
    """Test: Stock insuficiente debe lanzar ValueError."""
    with pytest.raises(ValueError, match="Stock insuficiente para item 'A'"):
        procesar_pedidos(
            [{"item": "A", "cantidad": 999}],
            usuario="juan",
        )


def test_procesar_pedidos_item_no_existe():
    """Test: Item no válido debe lanzar ValueError."""
    with pytest.raises(ValueError, match="Item 'Z' no existe en catálogo"):
        procesar_pedidos(
            [{"item": "Z", "cantidad": 1}],
            usuario="juan",
        )


def test_procesar_pedidos_cantidad_negativa():
    """Test: Cantidad negativa en la lista de entrada se ignora al procesar."""
    resultado = procesar_pedidos(
        [
            {"item": "A", "cantidad": -5},
            {"item": "B", "cantidad": 1},
        ],
        usuario="juan",
    )

    # Solo debe contabilizar B (1 * 50 = 50)
    assert resultado.subtotal == 50.0
    assert resultado.total > 0


# ============================================================================
# TESTS: VALIDACIÓN DEL MODELO PYDANTIC
# ============================================================================

def test_modelo_item_pedido_valido():
    """Test: ItemPedido con datos válidos."""
    item = ItemPedido(item="A", cantidad=5)
    assert item.item == "A"
    assert item.cantidad == 5


def test_modelo_item_pedido_item_invalido():
    """Test: ItemPedido rechaza item no válido."""
    with pytest.raises(ValueError, match="Item 'Z' no existe"):
        ItemPedido(item="Z", cantidad=5)


def test_modelo_item_pedido_cantidad_negativa():
    """Test: ItemPedido rechaza cantidad negativa."""
    with pytest.raises(ValueError, match="Cantidad no puede ser negativa"):
        ItemPedido(item="A", cantidad=-1)


def test_modelo_pedido_seguimiento_valido():
    """Test: Pedido acepta seguimiento con formato correcto."""
    pedido = Pedido(
        estado="pendiente",
        total=100.0,
        seguimiento="PED-20260306-ABC1",
        subtotal=84.03,
        descuento_aplicado=0.0,
        iva=15.97,
    )
    assert pedido.seguimiento == "PED-20260306-ABC1"


def test_modelo_pedido_seguimiento_invalido():
    """Test: Pedido rechaza seguimiento con formato incorrecto."""
    with pytest.raises(ValueError, match="no cumple formato"):
        Pedido(
            estado="pendiente",
            total=100.0,
            seguimiento="INVALID-FORMAT",
            subtotal=84.03,
            descuento_aplicado=0.0,
            iva=15.97,
        )


def test_modelo_pedido_estado_invalido():
    """Test: Pedido rechaza estado no válido."""
    with pytest.raises(ValueError, match="Estado debe ser"):
        Pedido(
            estado="completado",
            total=100.0,
            seguimiento="PED-20260306-ABC1",
            subtotal=84.03,
            descuento_aplicado=0.0,
            iva=15.97,
        )


# ============================================================================
# TESTS: CÁLCULOS MATEMÁTICOS
# ============================================================================

def test_calculo_iva_correcto():
    """Test: IVA debe ser exactamente 19%."""
    resultado = procesar_pedidos(
        [{"item": "A", "cantidad": 1}],
        usuario="juan",
    )

    # Subtotal: 100
    # Descuento: 0 (no aplica)
    # Subtotal con desc: 100
    # IVA: 100 * 0.19 = 19
    assert resultado.iva == 19.0
    assert resultado.total == 119.0


def test_calculo_total_con_descuento():
    """Test: Total con descuento debe calcularse correctamente."""
    resultado = procesar_pedidos(
        [{"item": "A", "cantidad": 2}],  # subtotal 200, 10% desc
        usuario="juan",
    )

    # Subtotal: 200
    # Descuento: 20 (10% de 200)
    # Subtotal desc: 180
    # IVA: 180 * 0.19 = 34.20
    # Total: 180 + 34.20 = 214.20
    assert resultado.subtotal == 200.0
    assert resultado.descuento_aplicado == 20.0
    assert resultado.iva == 34.20
    assert resultado.total == 214.20


def test_redondeo_monetario():
    """Test: Todos los valores monetarios deben redondearse a 2 decimales."""
    resultado = procesar_pedidos(
        [{"item": "B", "cantidad": 3}],  # 150, no aplica desc
        usuario="juan",
    )

    # Subtotal: 150
    # Descuento: 0
    # IVA: 150 * 0.19 = 28.50
    # Total: 150 + 28.50 = 178.50
    assert len(str(resultado.total).split('.')[-1]) <= 2
    assert len(str(resultado.iva).split('.')[-1]) <= 2


# ============================================================================
# TESTS: REGRESIÓN Y COMBINACIONES COMPLEJAS
# ============================================================================

def test_caso_complejo_multiples_descuentos():
    """Test: Caso complejo con múltiples items y descuento aplicado."""
    resultado = procesar_pedidos(
        [
            {"item": "A", "cantidad": 3},  # 300
            {"item": "B", "cantidad": 2},  # 100
            {"item": "C", "cantidad": 5},  # 100
            # Total: 500 -> 15% descuento
        ],
        usuario="carlos",
    )

    assert resultado.subtotal == 500.0
    assert resultado.descuento_aplicado == 75.0
    assert resultado.total == round((500.0 - 75.0) * 1.19, 2)


def test_formato_seguimiento_unico():
    """Test: Cada pedido debe generar un seguimiento único."""
    pedido1 = procesar_pedidos(
        [{"item": "A", "cantidad": 1}],
        usuario="juan",
    )
    pedido2 = procesar_pedidos(
        [{"item": "A", "cantidad": 1}],
        usuario="juan",
    )

    assert pedido1.seguimiento != pedido2.seguimiento
    assert re.match(r"^PED-\d{8}-[A-Z0-9]{4}$", pedido1.seguimiento)
    assert re.match(r"^PED-\d{8}-[A-Z0-9]{4}$", pedido2.seguimiento)
