def is_discount_real(prices, discount, current_price):
    if len(prices) != 6:
        raise ValueError("El array de precios debe contener exactamente 6 valores.")

    average_price = sum(prices) / len(prices)
    discounted_price = average_price * (1 - discount / 100)

    return discounted_price <= current_price
