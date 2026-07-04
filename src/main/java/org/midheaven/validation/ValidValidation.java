package org.midheaven.validation;

import org.midheaven.collections.Enumerable;
import org.midheaven.lang.ValueClass;

import java.util.function.Function;

@ValueClass
final class ValidValidation implements Validation {
    
    private static final ValidValidation ME = new ValidValidation();
    
    static ValidValidation instance(){
        return ME;
    }
    
    ValidValidation(){}
    
    @Override
    public Validation concatReason(InvalidationReason reason) {
        return new SingleReasonInvalidValidation(reason);
    }
    
    @Override
    public Enumerable<InvalidationReason> reasons() {
        return Enumerable.empty();
    }
    
    @Override
    public Validation concat(Validation other) {
        return other;
    }
    
    @Override
    public Validation map(Function<InvalidationReason, InvalidationReason> transform) {
        return this;
    }
    
    @Override
    public void withoutReasons() {
        // no-op
    }
    
    @Override
    public boolean isValid() {
        return true;
    }
}
