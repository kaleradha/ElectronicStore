package com.shruteekatech.electronic.store.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=ImageNameValidator.class)
public @interface  ImageNameValid {

    String message() default "Invalid Image address!!";

    //represents group of constraints
  Class<?>[] groups() default {};
    //represents additional information about annotation
    Class<? extends Payload>[] payload() default {};
}
