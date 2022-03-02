package com.salesianos.dam.proyectoMiarma.validation.validators;

import com.salesianos.dam.proyectoMiarma.users.repository.UserEntityRepository;
import com.salesianos.dam.proyectoMiarma.validation.anotations.Unique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

    @Autowired
    private UserEntityRepository repository;

    @Override
    public void initialize(Unique constraintAnnotation) { }

    @Override
    public boolean isValid(String nick, ConstraintValidatorContext context) {
        return StringUtils.hasText(nick) && !repository.findFirstByNick(nick).isEmpty();
    }
}

