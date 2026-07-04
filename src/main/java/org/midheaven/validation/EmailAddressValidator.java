package org.midheaven.validation;

import org.midheaven.lang.Strings;

import java.util.regex.Pattern;

public final class EmailAddressValidator implements Validator<String> {
    
    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    
    @Override
    public Validation validate(String candidate) {
        if(Strings.isFilled(candidate) && !PATTERN.matcher(candidate).matches()) {
            return Validation.invalid(InvalidationReason.error("invalid.email"));
        }
        
        return Validation.valid();
    }
}
