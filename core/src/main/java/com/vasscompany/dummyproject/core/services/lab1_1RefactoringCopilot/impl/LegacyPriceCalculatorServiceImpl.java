package com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot.impl;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Object.class, immediate = true)
public class LegacyPriceCalculatorServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(LegacyPriceCalculatorServiceImpl.class);

    public Double calc(double a, double b, double c, double d) {
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
    }

    public String getI(String p, String k) {
        if (p == null || k == null) return "";
        try {
            Object r = getData(p, k);
            if (r != null) {
                return r.toString();
            }
        } catch (Exception e) {
            LOG.error("err", e);
        }
        return "";
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
