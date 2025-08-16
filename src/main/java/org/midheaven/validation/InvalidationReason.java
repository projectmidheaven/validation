package org.midheaven.validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface InvalidationReason {

    static InvalidationReason error(String key, Object ... parameters){
        return new SimpleInvalidationReason(InvalidationSeverity.ERROR, key, parameters);
    }

    static InvalidationReason warn(String key, Object ... parameters){
        return new SimpleInvalidationReason(InvalidationSeverity.WARN, key, parameters);
    }

    InvalidationSeverity severity();
    String reasonKey();
    List<Object> reasonParameters();

    InvalidationReason prefix(String prefix);
}


record SimpleInvalidationReason(InvalidationSeverity severity, String reasonKey, Object[] parameters) implements InvalidationReason{

    @Override
    public List<Object> reasonParameters() {
        return Collections.unmodifiableList(Arrays.asList(parameters));
    }

    @Override
    public InvalidationReason prefix(String prefix) {
        return new SimpleInvalidationReason(severity, prefix + "." + reasonKey, parameters);
    }
}