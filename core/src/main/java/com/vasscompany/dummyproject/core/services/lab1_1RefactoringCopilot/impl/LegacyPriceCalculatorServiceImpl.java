package com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot.impl;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Object.class, immediate = true)
public class LegacyPriceCalculatorServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(LegacyPriceCalculatorServiceImpl.class);

//CLEAR
// C:
// Este servicio pertenece a la capa core/services de un proyecto AEM y debe encapsular
// lógica de cálculo y validación de precios de forma clara, testeable y reutilizable.
// La refactorización es correctiva: el objetivo no es reproducir exactamente el comportamiento
// defectuoso del código legacy, sino corregirlo para cumplir la regla de negocio esperada
// definida en el laboratorio.
// L:
// El lenguaje usado es Java 21, más información técnica sobre librerías, etc. se puede encontrar en los .md añadidos al .copilot-context en la raíz de este proyecto
// E:
// Cómo ejemplo de uso, toma que calc(10,5,20,4) debería retornar 52.5
// A:
// Se asume que la especificación funcional del laboratorio prevalece sobre la implementación legacy.
// R:
// El método principal retorna el resultado correcto de negocio, redondeado a dos decimales.
// Para el caso calculateFinalPrice(10, 5, 20, 4), el resultado esperado es 52.5.
// El servicio refactorizado no debe mantener comportamientos incorrectos heredados solo por compatibilidad.
// El método nunca debe retornar null.
// Si el divisor es cero, debe lanzar IllegalArgumentException.
// Si algún parámetro es NaN o infinito, debe lanzar IllegalArgumentException.
// El servicio debe quedar preparado para inyección OSGi mediante una interfaz explícita.
/*    public Double calc(double a, double b, double c, double d) {
        double x = a * b;
        double y;
        try {
            y = c / d;
        } catch (Exception e) {
            return null;
        }
        if (d == 0) {
            return null;
        }
        double z = (x + y) * 2;
        if (z < 0) {
            z = Math.abs(z);
        }
        double result = z / 2;
        return Math.round(result * 100.0) / 100.0;
    }*/
    public Double calc(double a, double b, double c, double d) {
        if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Los parámetros no pueden ser NaN");
        }
        if (Double.isInfinite(a) || Double.isInfinite(b) || Double.isInfinite(c) || Double.isInfinite(d)) {
            throw new IllegalArgumentException("Los parámetros no pueden ser infinitos");
        }
        if (d == 0) {
            throw new IllegalArgumentException("El divisor no puede ser cero");
        }

        double x = a * b;
        double y = c / d;
        double z = (x + y) * 2;
        if (z < 0) {
            z = Math.abs(z);
        }
        double result = z / 2;
        return Math.round(result * 100.0) / 100.0;
    }

    private Object getData(String p, String k) {
        return System.getProperty(p + "." + k);
    }

    public boolean proc(String type, double val, String cfg) {
        if (type == null) return false;
        boolean r = false;
        if (type.equals("A")) {
            if (val > 0 && val < 1000) {
                r = true;
            }
        } else if (type.equals("B")) {
            if (val > 100) {
                r = true;
            }
        } else if (type.equals("C")) {
            if (cfg != null && !cfg.isEmpty() && val >= 0) {
                r = true;
            }
        }
        return r;
    }
}
