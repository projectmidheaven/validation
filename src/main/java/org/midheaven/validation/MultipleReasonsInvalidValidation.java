package org.midheaven.validation;

import org.midheaven.collections.Enumerable;
import org.midheaven.collections.Sequence;
import org.midheaven.lang.ValueClass;

import java.util.function.Function;

@ValueClass
record MultipleReasonsInvalidValidation(Enumerable<InvalidationReason> reasons) implements Validation {
    
    private MultipleReasonsInvalidValidation(MultipleReasonsInvalidValidation other, InvalidationReason additionalReason) {
        this(other.reasons.concat(Enumerable.single(additionalReason)));
    }
    
    private MultipleReasonsInvalidValidation(MultipleReasonsInvalidValidation a, MultipleReasonsInvalidValidation b) {
        this(a.reasons.concat(b.reasons));
    }
    
    MultipleReasonsInvalidValidation(InvalidationReason a, InvalidationReason b) {
        this(Sequence.builder().withSize(2).of(a, b));
    }
    
    
    @Override
    public Validation addReason(InvalidationReason reason) {
        return new MultipleReasonsInvalidValidation(this, reason);
    }
    
    @Override
    public Validation merge(Validation other) {
        return switch (other) {
            case ValidValidation ignored -> this;
            case SingleReasonInvalidValidation s -> new MultipleReasonsInvalidValidation(this, s.reason());
            case MultipleReasonsInvalidValidation m -> new MultipleReasonsInvalidValidation(this, m);
        };
    }
    
    @Override
    public Validation map(Function<InvalidationReason, InvalidationReason> transform) {
        return new MultipleReasonsInvalidValidation(this.reasons.map(transform));
    }
    
    @Override
    public void withoutReasons() {
        throw new ValidationException(this);
    }
    
    @Override
    public boolean isValid() {
        return false;
    }
    
}
