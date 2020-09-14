package za.co.pp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
@Slf4j
public class PrettyParadiseClientExceptionHandler {

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Problem> handleHttpServerErrorException(HttpServerErrorException e){
        log.error(e.getMessage(), e);
        Problem problem = new Problem(e.getStatusCode(), e.getStatusCode().name(), e.getStatusCode().getReasonPhrase());
        return new ResponseEntity(problem, getZalandoProblemHttpHeaders(), e.getStatusCode());
    }

    private HttpHeaders getZalandoProblemHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        return httpHeaders;
    }

}
