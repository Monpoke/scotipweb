package scotip.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Pierre on 26/04/2016.
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidLibraryFilesValidator.class)
@Documented
public @interface ValidLibraryFiles {
    String message() default "Library files string is invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}