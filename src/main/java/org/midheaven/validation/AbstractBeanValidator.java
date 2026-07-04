package org.midheaven.validation;

import org.midheaven.lang.reflection.Mirror;
import org.midheaven.lang.reflection.ParametricTypeReference;
import org.midheaven.lang.reflection.Property;
import org.midheaven.lang.reflection.TypeReference;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractBeanValidator<T> implements Validator<T> {
    
    private final Mirror<T> mirror;
    private final List<Validator<T>> propertiesValidators = new LinkedList<>();
    
    protected AbstractBeanValidator(){
        var typeParameters = Mirror.reflect(this.getClass()).parameterizedType().orElseThrow();
        
        ParametricTypeReference<T> type = ParametricTypeReference.from(TypeReference.of(typeParameters.getActualTypeArguments()[0]));
        this.mirror = Mirror.reflect(type);
    }

    protected final <P> BeanPropertyValidationBuilder<T,P> ensure(Function<T, P> fieldSelector){
        var property = this.mirror.properties().get(fieldSelector).orElseThrow();

        var builder = new BeanPropertyValidationBuilder<T,P>(property);
        addValidator(property, builder.propertyValidator);
        return builder;
    }

    <P> void addValidator(Property property, CompositeValidator<P> validator){
        this.propertiesValidators.add(new PropertyValidator<>(property, validator));
    }

    @Override
    public final Validation validate(T candidate) {
        var validation = Validation.valid();

        for (var validator : propertiesValidators){
            validation = validation.concat(validator.validate(candidate));
        }

        return validation;
    }
}


record PropertyValidator<T, P>(
    Property property,
    Validator<P> validator
    ) implements Validator<T> {
    
    @SuppressWarnings("unchecked")
    @Override
    public Validation validate(T candidate) {
        var value = property.getValue(candidate).orNull();
        return validator.validate((P)value).map(r -> r.prefix(property.name().toLowerCase()));
    }
}