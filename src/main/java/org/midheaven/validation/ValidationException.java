package org.midheaven.validation;

public final class ValidationException extends RuntimeException {

    private final Validation validation;

    ValidationException(Validation validation) {
        this.validation = validation;
    }

    public Validation validation(){
        return validation;
    }
}
