package ru.javaops.startup.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {})
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@NotBlank
@NoHtml
@Size(min = 1, max = 32)
public @interface Code {
    String message() default "{err.msg.wrongCode}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
