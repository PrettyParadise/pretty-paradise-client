package za.co.pp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PrettyParadiseClientException extends RuntimeException{
    private HttpStatus httpStatus;

    public PrettyParadiseClientException(final String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
