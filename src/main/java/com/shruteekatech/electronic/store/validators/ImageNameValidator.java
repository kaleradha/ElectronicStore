package com.shruteekatech.electronic.store.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value.isEmpty()){
            return false;
        }else{

        return true;
    }
}
}
