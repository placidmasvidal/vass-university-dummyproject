def calcular_total(precios):
    """
    Calcula el total de una lista de precios.

    Parámetros:
    precios (list of float): Lista de precios, cada precio debe ser >= 0.

    Retorna:
    float: Total calculado, redondeado a 2 decimales.

    Excepciones:
    ValueError: Si algún precio es negativo o si la lista está vacía.

    Ejemplos:
    >>> calcular_total([10.0, 20.0, 30.0])
    60.0
    >>> calcular_total([5.5, 4.5])
    10.0
    >>> calcular_total([])
    Traceback (most recent call last):
        ...
