package za.co.pp.controller.validation;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import za.co.pp.exception.PrettyParadiseClientException;

public class ProductValidator {


    public static void validateEmailAddress(final String emailAddress) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(emailAddress)){
            throw new PrettyParadiseClientException(String.format("Invalid email address - %s", emailAddress), HttpStatus.BAD_REQUEST);
        }
    }
}
