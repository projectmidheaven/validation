package org.midheaven.validation;

import org.midheaven.collections.Enumerable;

import java.util.function.Function;

public sealed interface Validation permits ValidValidation, SingleReasonInvalidValidation, MultipleReasonsInvalidValidation {
    
    static Validation valid() {
        return ValidValidation.instance();
    }
    
    /**
     * Ads and {@link InvalidationReason} to the {@code Validation}.
     *
     * @param reason the reason to add
     * @return and invalid validation containing the given reason
     */
    Validation addReason(InvalidationReason reason);
    
    /**
     * Return all invalidation reason in this {@code Validation}.
     *
     * @return invalidation reason in this {@code Validation}.
     */
    Enumerable<InvalidationReason> reasons();
    
    /**
     * Returns a new {@code Validation} object containing the reasons in both {@code this} and {@code other} validations.
     * @param other the other {@code Validation} to merge
     * @return the merge {@code Validation}
     */
    Validation merge(Validation other);
    
    /**
     * Applies a transformation to all reasons and returns a new {@code Validation} object containing the new reasons
     * @param transform the transformation to apply
     * @return the transformed {@code Validation}
     */
    Validation map(Function<InvalidationReason, InvalidationReason> transform);
    
    /**
     * Tests if any reason is present. IF it is throws a {@link ValidationException}
     */
    void withoutReasons() throws ValidationException;
    
    /**
     * Tests if there are no invalidation reasons
     */
    boolean isValid();
}
