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
public class PrettyParadiseClientExceptionHandlerUnitTest {
    @Autowired
    private PrettyParadiseClientExceptionHandler prettyParadiseClientExceptionHandler;

    @Test
    void canReturnProblemResponseWhenInternalServerErrorIsCaught(){
        final ResponseEntity<Problem> responseEntity =
                prettyParadiseClientExceptionHandler.handleHttpServerErrorException(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertZalandoProblemResponse(responseEntity);
    }

    private void assertZalandoProblemResponse(final ResponseEntity<Problem> responseEntity) {
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.name());
        assertThat(responseEntity.getBody().getDetail()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

}
