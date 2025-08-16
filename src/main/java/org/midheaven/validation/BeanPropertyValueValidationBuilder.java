package org.midheaven.validation;

import org.midheaven.math.Interval;

import java.util.Comparator;

public class BeanPropertyValueValidationBuilder <T, P> extends BeanPropertyValidationBuilder<T,P> {

    private final BeanPropertyValidationBuilder<T, P> parent;

    BeanPropertyValueValidationBuilder(BeanPropertyValidationBuilder<T, P> parent) {
        super(parent.property);
        this.parent = parent;
    }

    public BeanPropertyValueValidationBuilder<T,P> isLessThan(P max){
        parent.propertyValidator.add(RangeValidator.in((Interval.ranging(comparator()).fromInfinity().to(max).exclusive())));
        return this;
    }

    public BeanPropertyValueValidationBuilder<T,P> isLessOrEqualTo(P max){
        parent.propertyValidator.add(RangeValidator.in(Interval.ranging(comparator()).fromInfinity().to(max).inclusive()));
        return this;
    }

    public BeanPropertyValueValidationBuilder<T,P> isGreaterThan(P min){
        parent.propertyValidator.add(RangeValidator.in((Interval.ranging(comparator()).from(min).exclusive().toInfinity())));
        return this;
    }

    public BeanPropertyValueValidationBuilder<T,P> isGreaterOrEqualTo(P min){
        parent.propertyValidator.add(RangeValidator.in(Interval.ranging(comparator()).from(min).inclusive().toInfinity()));
        return this;
    }

    public BeanPropertyValueValidationBuilder<T,P> isBetween(P min, P max){
        parent.propertyValidator.add(RangeValidator.in(Interval.ranging(comparator()).from(min).inclusive().to(max).inclusive()));
        return this;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Comparator<P> comparator(){
        return (a,b) -> ((Comparable)a).compareTo(b);
    }
}
