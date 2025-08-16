package org.midheaven.validation;

public interface Validator<T> {
    
    Validation validate(T candidate);
    
    /**
     * Creates a new {@code Validation} composed of {@code this} and the given {@code Validator}
     *
     * @param other the validator to compose with
     * @return the composed validator
     */
    default Validator<T> compose(Validator<T> other){
        return new CompositeValidator<>(this, other);
    }
}
