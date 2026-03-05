def calcularVolumen(ancho, alto, profundidad):

# Función que calcula el volumen
    if ancho <= 0 or alto <= 0 or profundidad <= 0:
        raise ValueError("Las dimensiones deben ser mayores a 0.")

    volumen = ancho * alto * profundidad
    return round(volumen, 2)
