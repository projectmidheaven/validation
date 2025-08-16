package org.midheaven.validation;

public final class NonNullValidator<T> implements Validator<T> {

    @Override
    public Validation validate(T candidate) {
        var validation = Validation.valid();

        if (candidate == null){
            return validation.addReason(InvalidationReason.error("is.null"));
        }

        return validation;
    }
}
