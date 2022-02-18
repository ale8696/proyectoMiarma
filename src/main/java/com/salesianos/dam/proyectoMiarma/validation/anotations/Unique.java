package com.salesianos.dam.proyectoMiarma.validation.anotations;

import com.salesianos.dam.proyectoMiarma.validation.validators.UniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {

    String message() default "El nick ser único";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
