package com.vasscompany.dummyproject.core.services;

import com.vasscompany.dummyproject.core.services.lab1_1RefactoringCopilot.impl.LegacyPriceCalculatorServiceImpl;

public class LegacyPriceCalculatorDebugMain {

    public static void main(String[] args) {
        LegacyPriceCalculatorServiceImpl svc = new LegacyPriceCalculatorServiceImpl();

        System.out.println("calc(10, 5, 20, 4)   = " + svc.calc(10, 5, 20, 4));
        System.out.println("calc(2, 3, 10, 2)    = " + svc.calc(2, 3, 10, 2));
        System.out.println("calc(10, 5, 20, 0)   = " + svc.calc(10, 5, 20, 0));
        System.out.println("calc(-10, 5, 20, 4)  = " + svc.calc(-10, 5, 20, 4));

        System.out.println("proc(\"A\", 500, null) = " + svc.proc("A", 500, null));
        System.out.println("proc(\"C\", -1, \"cfg\") = " + svc.proc("C", -1, "cfg"));

        // Opcional: probar getI()
        System.setProperty("demo.key", "valor-demo");
        System.out.println("getI(\"demo\", \"key\") = " + svc.getI("demo", "key"));
    }
}
