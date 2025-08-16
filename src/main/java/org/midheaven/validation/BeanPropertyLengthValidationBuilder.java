package org.midheaven.validation;

import org.midheaven.math.Interval;

public class BeanPropertyLengthValidationBuilder<T, P> extends BeanPropertyValidationBuilder<T,P> {

    private final BeanPropertyValidationBuilder<T, P> parent;

    BeanPropertyLengthValidationBuilder(BeanPropertyValidationBuilder<T, P> parent) {
        super(parent.property);
        this.parent = parent;
    }

    BeanPropertyLengthValidationBuilder<T,P> isLessThan(int max){
        parent.propertyValidator.add(LengthRangeValidator.in((Interval.ranging(Integer.class).fromInfinity().to(max).exclusive())));
        return this;
    }

    BeanPropertyLengthValidationBuilder<T,P> isLessOrEqualTo(int max){
        parent.propertyValidator.add(LengthRangeValidator.in(Interval.ranging(Integer.class).fromInfinity().to(max).inclusive()));
        return this;
    }

    public BeanPropertyLengthValidationBuilder<T,P> isGreaterThan(int min){
        parent.propertyValidator.add(LengthRangeValidator.in((Interval.ranging(Integer.class).from(min).exclusive().toInfinity())));
        return this;
    }

    public BeanPropertyLengthValidationBuilder<T,P> isGreaterOrEqualTo(int min){
        parent.propertyValidator.add(LengthRangeValidator.in(Interval.ranging(Integer.class).from(min).inclusive().toInfinity()));
        return this;
    }

    public BeanPropertyLengthValidationBuilder<T,P> isBetween(int min, int max){
        parent.propertyValidator.add(LengthRangeValidator.in(Interval.ranging(Integer.class).from(min).inclusive().to(max).inclusive()));
        return this;
    }

    public  BeanPropertyLengthValidationBuilder<T,P> is(int value) {
        parent.propertyValidator.add(LengthRangeValidator.in(Interval.ranging(Integer.class).from(value).inclusive().to(value).inclusive()));
        return this;
    }
}
