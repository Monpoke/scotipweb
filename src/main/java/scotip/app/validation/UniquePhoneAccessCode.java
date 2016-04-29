package scotip.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Pierre on 26/04/2016.
 */
@Target({TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UniquePhoneAccessCodeValidator.class)
@Documented
public @interface UniquePhoneAccessCode {
    String message() default "The access code is already used.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}