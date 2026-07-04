package org.midheaven.validation;

import java.util.List;

public class PropertyInvalidationReason implements InvalidationReason {
    
    public static PropertyInvalidationReason of(String entityName, String entityFieldName, InvalidationReason reason) {
        return new PropertyInvalidationReason(reason, entityName, entityFieldName);
    }
    
    private final InvalidationReason original;
    private  final String entityName;
    private final String entityFieldName;
    
    private PropertyInvalidationReason(InvalidationReason invalidationReason, String entityName, String entityFieldName) {
        this.original = invalidationReason;
        this.entityName = entityName;
        this.entityFieldName = entityFieldName;
    }
    
    @Override
    public String toString() {
        return entityName + "." + entityFieldName + " is invalid due to " + original.toString();
    }
    
    public String entityName() {
        return entityName;
    }
    
    public String entityFieldName() {
        return entityFieldName;
    }
    
    @Override
    public InvalidationSeverity severity() {
        return original.severity();
    }
    
    @Override
    public String reasonKey() {
        return original.reasonKey();
    }
    
    @Override
    public List<Object> reasonParameters() {
        return original.reasonParameters();
    }
    
    @Override
    public InvalidationReason prefix(String prefix) {
        return new PropertyInvalidationReason(original.prefix(prefix), this.entityName, entityFieldName);
    }
}
