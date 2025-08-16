package org.midheaven.validation;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class CompositeValidator<T> implements Validator<T> {

    public static <S> CompositeValidator<S> compose(Validator<S> a, Validator<S> b) {
        return new CompositeValidator<>(a, b);
    }

    public static <S> CompositeValidator<S> empty() {
        return new CompositeValidator<>();
    }

    private final List<Validator<T>> validators = new LinkedList<>();
    
    private CompositeValidator(){}
    
    CompositeValidator(Validator<T> a, Validator<T> b){
        add(a);
        add(b);
    }
    
    @Override
    public Validation validate(T candidate) {
        var validation = Validation.valid();

        for (var validator : this.validators){
            validation = validation.merge(validator.validate(candidate));
        }
        return validation;
    }
    
    void add(Validator<T> other) {
        if (other instanceof CompositeValidator composite){
            this.validators.addAll(composite.validators);
        } else {
            this.validators.add(other);
        }
    }
}
