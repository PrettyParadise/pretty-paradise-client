package za.co.pp.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrettyParadiseClientExceptionHandlerUnitTest {
    @Autowired
    private PrettyParadiseClientExceptionHandler prettyParadiseClientExceptionHandler;

    @Test
    void canReturnProblemResponseWhenInternalServerErrorIsCaught(){
        final ResponseEntity<Problem> responseEntity =
                prettyParadiseClientExceptionHandler.handleHttpServerErrorException(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertZalandoProblemResponse(responseEntity, HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode().getReasonPhrase());
    }

    @Test
    void canReturnProblemResponseWhenPrettyParadiseClientExceptionIsCaught(){
        final ResponseEntity<Problem> responseEntity =
                prettyParadiseClientExceptionHandler.handlePrettyParadiseClientException(new PrettyParadiseClientException("Error occurred.", HttpStatus.BAD_REQUEST));

        assertZalandoProblemResponse(responseEntity, HttpStatus.BAD_REQUEST, "Error occurred.");
    }

    private void assertZalandoProblemResponse(final ResponseEntity<Problem> responseEntity, HttpStatus httpStatus, String detail) {
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(httpStatus);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(httpStatus.name());
        assertThat(responseEntity.getBody().getDetail()).isEqualTo(detail);
    }

}
