package org.midheaven.validation;

import org.midheaven.lang.reflection.Property;
import org.midheaven.math.Interval;

public class BeanPropertyValidationBuilder<T, P> {

    final Property property;
    final CompositeValidator<P> propertyValidator = CompositeValidator.empty();

    BeanPropertyValidationBuilder(Property property) {
        this.property = property;
    }

    public BeanPropertyValidationBuilder<T,P> isPresent(){
        propertyValidator.add(new NonNullValidator<>());
        return this;
    }

    public BeanPropertyValidationBuilder<T,P> isNonEmpty() {
        propertyValidator.add(new NonEmptyValidator<>());
        return this;
    }

    public BeanPropertyLengthValidationBuilder<T,P> andLength(){
        return new BeanPropertyLengthValidationBuilder<>(this);
    }

    public BeanPropertyValidationBuilder<T,P> isIn(Interval<P> interval){
        propertyValidator.add(RangeValidator.in(interval));
        return this;
    }

    public  BeanPropertyValueValidationBuilder<T,P> andValue(){
        if (!Comparable.class.isAssignableFrom(this.property.valueType())){
            throw new IllegalArgumentException("Property " + property.name() + " is not Comparable");
        }
        return new BeanPropertyValueValidationBuilder<>(this);
    }

    public BeanPropertyValidationBuilder<T,P> andPasses(Validator<P> otherPropertyValidator){
        propertyValidator.add(otherPropertyValidator);
        return this;
    }
}
