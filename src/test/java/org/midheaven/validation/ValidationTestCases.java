package org.midheaven.validation;

import org.junit.jupiter.api.Test;
import org.midheaven.collections.Enumerable;
import org.midheaven.math.Interval;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTestCases {

    @Test
    public void nonEmpty_isConsistent(){
        var nonEmptyValidator = new NonEmptyValidator<>();

        assertTrue(nonEmptyValidator.validate("Something").isValid());
        assertInvalid(nonEmptyValidator.validate(""));
        assertInvalid(nonEmptyValidator.validate(" "));
        assertInvalid(nonEmptyValidator.validate(null));
        assertInvalid(nonEmptyValidator.validate(List.of()));
        assertInvalid(nonEmptyValidator.validate(Set.of()));
        assertInvalid(nonEmptyValidator.validate(Map.of()));
        assertInvalid(nonEmptyValidator.validate(new Object[0]));
        assertInvalid(nonEmptyValidator.validate(Enumerable.empty()));
    }

    @Test
    public void length_isConsistent(){
        var lengthValidator = LengthRangeValidator.in(Interval.ranging(Integer.class).from(2).inclusive().to(3).inclusive());

        assertTrue(lengthValidator.validate(null).isValid());
        assertTrue(lengthValidator.validate("sun").isValid());
        assertTrue(lengthValidator.validate(List.of(1, 2,3)).isValid());
        assertTrue(lengthValidator.validate(Set.of(1,2)).isValid());
        assertTrue(lengthValidator.validate(Map.of(1, "a", 2, "b")).isValid());
        assertTrue(lengthValidator.validate(new Object[]{1,2}).isValid());
        assertTrue(lengthValidator.validate(Enumerable.from(List.of(1,2,3))).isValid());
        
        assertInvalid(lengthValidator.validate(""));
        assertInvalid(lengthValidator.validate("a"));
        assertInvalid(lengthValidator.validate("abcd"));
        assertInvalid(lengthValidator.validate(List.of()));
        assertInvalid(lengthValidator.validate(Set.of()));
        assertInvalid(lengthValidator.validate(Map.of()));
        assertInvalid(lengthValidator.validate(new Object[0]));
        assertInvalid(lengthValidator.validate(Enumerable.empty()));

        assertEquals(
            "length.out.of.range",
            lengthValidator.validate("")
                .reasons()
                .first()
                .map(InvalidationReason::reasonKey)
                .orNull()
        );

    }
    
    private void assertInvalid(Validation validation){
        assertFalse(validation.isValid());
        assertThrows(ValidationException.class, validation::withoutReasons);
    }

    @Test
    public void bean_isConsistent(){
        var validator = new BeanSampleValidator();

        var bean = new BeanSample();
        bean.setAge(2);
        bean.setBirthday(LocalDate.now().minusYears(2));
        bean.setParents(List.of(new BeanSample(), new BeanSample()));
        bean.setName("Sample Bean");

        var validation = validator.validate(bean);
        assertTrue(validation.isValid());

        bean = new BeanSample();
        
        assertInvalid(validator.validate(bean));
    }
}

class BeanSampleValidator extends AbstractBeanValidator<BeanSample> {

    BeanSampleValidator(){
       // ensure(BeanSample::getAge).isPresent().andValue().isBetween(1, 100);
        ensure(BeanSample::getBirthday).isPresent();
        ensure(BeanSample::getParents).isNonEmpty().andLength().is(2);
        ensure(BeanSample::getName).isNonEmpty().andLength().isBetween(3, 12);
    }
}

