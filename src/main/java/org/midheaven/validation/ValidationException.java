package org.midheaven.validation;

import java.util.stream.Collectors;

public final class ValidationException extends RuntimeException {

    private final Validation validation;

    public ValidationException(Validation validation) {
        super(validation.reasons().map(InvalidationReason::toString).collect(Collectors.joining(",")));
        this.validation = validation;
    }
    
    public ValidationException(InvalidationReason reason) {
        super(reason.toString());
        this.validation = Validation.invalid(reason);
    }

    public Validation validation(){
        return validation;
    }
}
