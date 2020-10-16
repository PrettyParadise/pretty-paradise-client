package za.co.pp.controller.validation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import za.co.pp.exception.PrettyParadiseClientException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class ProductValidatorUnitTest {

    @Test
    void canCatchInvalidEmailAddressWithTrailingDot(){
        try {
            ProductValidator.validateEmailAddress("invalid@gmail.com.");
            fail("ProductValidator failed to catch invalid email address: invalid@gmail.com.");
        } catch (PrettyParadiseClientException prettyParadiseClientException){
            assertThat(prettyParadiseClientException.getMessage()).isEqualToIgnoringCase("Invalid email address - invalid@gmail.com.");
            assertThat(prettyParadiseClientException.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

    }

    @Test
    void canCatchInvalidEmailAddressWithInvalidSymbol(){
        try {
            ProductValidator.validateEmailAddress("invalid#gmail.com");
            fail("ProductValidator failed to catch invalid email address: invalid#gmail.com.");
        } catch (PrettyParadiseClientException prettyParadiseClientException){
            assertThat(prettyParadiseClientException.getMessage()).isEqualToIgnoringCase("Invalid email address - invalid#gmail.com");
            assertThat(prettyParadiseClientException.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

    }

    @Test
    void canCatchInvalidEmailAddressWithLeadingDot(){
        try {
            ProductValidator.validateEmailAddress(".invalid@gmail.com");
            fail("ProductValidator failed to catch invalid email address: .invalid@gmail.com");
        } catch (PrettyParadiseClientException prettyParadiseClientException){
            assertThat(prettyParadiseClientException.getMessage()).isEqualToIgnoringCase("Invalid email address - .invalid@gmail.com");
        }

    }

    @Test
    void canCatchInvalidEmailAddressNotContainingSymbol(){
        try {
            ProductValidator.validateEmailAddress("invalidgmail.com");
            fail("ProductValidator failed to catch invalid email address: invalidgmail.com");
        } catch (PrettyParadiseClientException prettyParadiseClientException){
            assertThat(prettyParadiseClientException.getMessage()).isEqualToIgnoringCase("Invalid email address - invalidgmail.com");
            assertThat(prettyParadiseClientException.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

    }

    @Test
    void canCatchInvalidEmailAddressWithInvalidLocalPart(){
        try {
            ProductValidator.validateEmailAddress("in\"valid@gmail.com.");
            fail("ProductValidator failed to catch invalid email address: inin\"valid@gmail.com.");
        } catch (PrettyParadiseClientException prettyParadiseClientException){
            assertThat(prettyParadiseClientException.getMessage()).isEqualToIgnoringCase("Invalid email address - in\"valid@gmail.com.");
            assertThat(prettyParadiseClientException.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

    }

    @Test
    void doesNotThrowExceptionForValidEmailAddress(){
        assertThatCode(()-> ProductValidator.validateEmailAddress("valid@gmail.com")).doesNotThrowAnyException();
    }

}
