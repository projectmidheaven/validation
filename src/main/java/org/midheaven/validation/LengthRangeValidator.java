package org.midheaven.validation;

import org.midheaven.lang.Countable;
import org.midheaven.math.Interval;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public final class LengthRangeValidator<T> implements Validator<T> {


    public static <C> LengthRangeValidator<C> in(Interval<Integer> interval){
        return new LengthRangeValidator<>(interval);
    }

    private final RangeValidator<Integer> rangeValidator;

    private LengthRangeValidator(Interval<Integer> interval){
        this.rangeValidator = RangeValidator.in(interval);
    }

    @Override
    public Validation validate(T candidate) {
        if (candidate == null){
            return Validation.valid();
        }

        var length = lengthOf(candidate);

        return rangeValidator.validate(length).map(r -> r.prefix("length"));
    }

    private int lengthOf(T candidate) {

        if (candidate instanceof CharSequence s){
            return s.length();
        } else if (candidate instanceof Collection<?> c){
            return c.size();
        } else if (candidate instanceof Countable c){
            return c.count().toInt();
        } else if (candidate instanceof Map<?,?> m){
            return m.size();
        } else if (candidate.getClass().isArray()){
            return Array.getLength(candidate);
        }
        return 0;
    }
}
