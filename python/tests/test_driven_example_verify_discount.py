# Necesito un test para una función para comprobar si un descuento que dicen ofrecer sobre un producto es real.
# Para ello, la función original a testear recibir un parámetro array con 6 valores correspondientes al precio que ha tenido ese producto en los últimos 6 meses,
# un valor numérico que corresponde al descuento que dicen ofrecer sobre el producto.
# y el precio que tiene actualmente con el descuento que dicen aplicar
# la función calcula el precio medio de ese producto los últimos 6 meses, y sobre ese precio medio aplica el descuento que dicen ofrecer,
# y compara el resultado con el precio actual con descuento, si el resultado es igual o menor al precio actual con descuento,
# entonces el descuento es real, en caso contrario no lo es.

import sys
from pathlib import Path

import pytest

# Permite importar módulos desde python/src al ejecutar pytest desde distintas rutas.
sys.path.insert(0, str(Path(__file__).parent.parent / "src"))

from verify_discount import is_discount_real

# Test para la función is_discount_real
def test_is_discount_real() -> None:
    """Valida escenarios reales/no reales y error por número de precios."""
    # Caso 1: Descuento real
    prices = [100, 110, 120, 130, 140, 150]
    discount = 20
    current_price = 120
    assert is_discount_real(prices, discount, current_price) is True

    # Caso 2: Descuento no real
    current_price = 90
    assert is_discount_real(prices, discount, current_price) is False

    # Caso 3: Descuento real con precio actual igual al precio descontado
    current_price = (sum(prices) / len(prices)) * (1 - discount / 100)
    assert is_discount_real(prices, discount, current_price) is True

    # Caso 4: Error por número incorrecto de precios
    with pytest.raises(ValueError, match="exactamente 6 valores"):
        is_discount_real([100, 110], discount, current_price)

print("Todos los tests pasaron correctamente.")
