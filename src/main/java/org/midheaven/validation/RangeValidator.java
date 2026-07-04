package org.midheaven.validation;

import org.midheaven.math.Interval;

public final class RangeValidator<T> implements Validator<T> {


    public static <C> RangeValidator<C> in(Interval<C> interval){
        return new RangeValidator<>(interval);
    }

    private final Interval<T> interval;

    private RangeValidator(Interval<T> interval){
        this.interval = interval;
    }

    @Override
    public Validation validate(T candidate) {
        if (candidate != null && !interval.contains(candidate)){
            if (interval.minimum().isInfinity()){
                // (oo , x)
                return Validation.invalid(InvalidationReason.error("greater.than", interval.maximum().value().orNull()));
            } else if (interval.maximum().isInfinity()){
                 // (x , oo)
                return Validation.invalid(InvalidationReason.error("less.than", interval.minimum().value().orNull()));
            }
            // (x, y)
            return Validation.invalid(InvalidationReason.error("out-of-range", interval.minimum().value().orNull(), interval.maximum().value().orNull()));
        }
        return Validation.valid();
    }

}
