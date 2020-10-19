package za.co.pp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import za.co.pp.controller.validation.ProductIdValidator;
import za.co.pp.exception.PrettyParadiseClientException;
import za.co.pp.exception.Problem;
import za.co.pp.service.ProductService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerAndPrettyParadiseClientExceptionHandlerIntegrationTest {
    @MockBean
    private ProductService productService;

    @MockBean
    private ProductIdValidator productIdValidator;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void canCatchHttpServerErrorExceptionAndReturnZalandoProblemResponse(){
        when(productService.getAllProductDomainObjects()).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<Problem> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/products", Problem.class);

        assertZalandoProblemResponse(responseEntity, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
    
    @Test
    void canCatchPrettyParadiseExceptionAndReturnZalandoProblemResponse(){
       doThrow(new PrettyParadiseClientException("A product with the specified ID does not exist.", HttpStatus.NOT_FOUND)).when(productIdValidator).validateProductId(anyLong());

       ResponseEntity<Problem> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/cart/{productId}", null, Problem.class, 4);

       assertZalandoProblemResponse(responseEntity, HttpStatus.NOT_FOUND, "A product with the specified ID does not exist.");
    }

    private void assertZalandoProblemResponse(final ResponseEntity<Problem> responseEntity, HttpStatus httpStatus, String detail) {
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatus);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(httpStatus);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(httpStatus.name());
        assertThat(responseEntity.getBody().getDetail()).isEqualTo(detail);
    }

}
