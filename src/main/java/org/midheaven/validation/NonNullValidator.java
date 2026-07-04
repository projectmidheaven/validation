package org.midheaven.validation;

public final class NonNullValidator<T> implements Validator<T> {

    @Override
    public Validation validate(T candidate) {
  
        if (candidate == null){
            return Validation.invalid(InvalidationReason.error("is.null"));
        }

        return Validation.valid();
    }
}
