package org.midheaven.validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    
    @Override
    public String toString() {
        return "SimpleInvalidationReason["
            + "reasonKey=" + reasonKey + ","
            + "severity=" + severity + ","
            + "parameters=[" + Arrays.stream(parameters).map(Object::toString).collect(Collectors.joining(",")) + "],"
            ;
    }
}