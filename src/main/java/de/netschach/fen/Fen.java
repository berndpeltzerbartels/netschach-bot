package de.netschach.fen;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = FenValidator.class)
public @interface Fen {
    String message() default "value is not valid FEN";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
