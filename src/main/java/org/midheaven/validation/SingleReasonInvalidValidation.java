package org.midheaven.validation;

import org.midheaven.collections.Enumerable;
import org.midheaven.lang.ValueClass;

import java.util.function.Function;

@ValueClass
record SingleReasonInvalidValidation(InvalidationReason reason) implements Validation {
    
    public Validation addReason(InvalidationReason reason) {
        return new MultipleReasonsInvalidValidation(this.reason, reason);
    }
    
    public Enumerable<InvalidationReason> reasons() {
        return Enumerable.single(reason);
    }
    
    public Validation merge(Validation other) {
        return switch (other) {
            case ValidValidation ignored -> this;
            case SingleReasonInvalidValidation s -> new MultipleReasonsInvalidValidation(this.reason, s.reason);
            case MultipleReasonsInvalidValidation m -> m.addReason(this.reason);
        };
    }
    
    public Validation map(Function<InvalidationReason, InvalidationReason> transform) {
        return new SingleReasonInvalidValidation(transform.apply(this.reason));
    }
    
    public void withoutReasons() {
        throw new ValidationException(this);
    }
    
    public boolean isValid() {
        return false;
    }
    
}
