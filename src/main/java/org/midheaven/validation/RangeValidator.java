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
        var validation = Validation.valid();
        if (candidate == null){
            return validation;
        }

        if (!interval.contains(candidate)){
            if (interval.mininum().isInfinity()){
                // (oo , x)
                return validation.addReason(InvalidationReason.error("greater.than", interval.maximum().value().orNull()));
            } else if (interval.maximum().isInfinity()){
                 // (x , oo)
                return validation.addReason(InvalidationReason.error("less.than", interval.mininum().value().orNull()));
            }

            return validation.addReason(InvalidationReason.error("out.of.range", interval.mininum().value().orNull(), interval.maximum().value().orNull()));
        }
        return validation;
    }

}
