package com.vasscompany.dummyproject.core.models.lab1_2ImplementacionPrompting;

public class ShipmentValidationResult {

    private boolean valid;
    private String message;

    public ShipmentValidationResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return "ShipmentValidationResult{valid=" + valid + ", message='" + message + "'}";
    }
}
