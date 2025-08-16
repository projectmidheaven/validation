package org.midheaven.validation;

import org.midheaven.lang.Countable;
import org.midheaven.lang.Strings;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public final class NonEmptyValidator<T> implements Validator<T> {

    @Override
    public Validation validate(T candidate) {
        var validation = Validation.valid();

        if (candidate == null){
            return validation.addReason(InvalidationReason.error("is.null"));
        } else if (candidate instanceof CharSequence charSequence && Strings.isBlank(charSequence)){
            return validation.addReason(InvalidationReason.error("is.blank"));
        } else if (candidate instanceof Collection<?> collection && collection.isEmpty()){
            return validation.addReason(InvalidationReason.error("is.empty"));
        } else if (candidate instanceof Countable c && c.isEmpty()){
            return validation.addReason(InvalidationReason.error("is.empty"));
        } else if (candidate instanceof Iterable<?> iterable && !iterable.iterator().hasNext()){
            return validation.addReason(InvalidationReason.error("is.empty"));
        } else if (candidate instanceof Map<?,?> map && map.isEmpty()){
            return validation.addReason(InvalidationReason.error("is.empty"));
        } else if (candidate.getClass().isArray() && Array.getLength(candidate) == 0){
            return validation.addReason(InvalidationReason.error("is.empty"));
        }

        return validation;
    }
}
