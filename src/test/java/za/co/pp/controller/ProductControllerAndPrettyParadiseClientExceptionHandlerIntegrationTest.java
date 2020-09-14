package za.co.pp.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import za.co.pp.data.repository.ProductRepository;
import za.co.pp.exception.Problem;
import za.co.pp.service.ProductService;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerAndPrettyParadiseClientExceptionHandlerIntegrationTest {
    @MockBean
    private ProductService productService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void canCatchHttpServerErrorExceptionAndReturnZalandoProblemResponse(){
        when(productService.getAllProducts()).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<Problem> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/products", Problem.class);

        assertZalandoProblemResponse(responseEntity);
    }

    private void assertZalandoProblemResponse(final ResponseEntity<Problem> responseEntity) {
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.name());
        assertThat(responseEntity.getBody().getDetail()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

}
