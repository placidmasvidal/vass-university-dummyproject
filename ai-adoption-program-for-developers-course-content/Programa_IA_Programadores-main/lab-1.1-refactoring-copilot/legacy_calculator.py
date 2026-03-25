# Archivo: legacy_calculator.py
# Función legacy para refactorizar en Lab 1.1   

def calc(a, b, c, d):
    """
    Función legacy con problemas de legibilidad y mantenibilidad.
    
    Esta función necesita ser refactorizada usando Copilot de forma consciente.
    """
    x = a * b
    y = c / d
    if y == 0:
        return None
    z = (x + y) * 2
    if z < 0:
        z = abs(z)
    result = z / 2
    return round(result, 2)


# Ejemplos de uso (para entender qué hace):
if __name__ == "__main__":
    # Caso 1: Valores básicos
    result1 = calc(10, 5, 20, 4)
    print(f"calc(10, 5, 20, 4) = {result1}")
    
    # Caso 2: Con valores diferentes
    result2 = calc(2, 3, 10, 2)
    print(f"calc(2, 3, 10, 2) = {result2}")
    
    # Caso 3: Divisor cero (debería retornar None)
    result3 = calc(10, 5, 20, 0)
    print(f"calc(10, 5, 20, 0) = {result3}")
    
    # Ejecuta estos ejemplos para entender el comportamiento
    # Luego refactoriza usando Copilot con técnicas aprendidas
