package za.co.pp.controller;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import za.co.pp.exception.Problem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static za.co.pp.utils.ProductUtils.createDatabaseAndPopulateProductsTable;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CacheControllerIntegrationTest {
    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DataSource dataSource;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() throws Exception {
        createDatabaseAndPopulateProductsTable(dataSource);
    }

    @Test
    @DisplayName("Given a product with the requested product ID does not exists, " +
            "when cart/{productId} endpoint hit, " +
            "then a zalando problem is returned")
    void canGetBadRequestForInvalidProductId() {
        ResponseEntity<Problem> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/cart/{productId}", null, Problem.class, 4L);

        assertZalandoProblemResponse(responseEntity, HttpStatus.NOT_FOUND, "A product with the specified ID does not exist.");
    }

    @Test
    @DisplayName("Given an invalid email address, " +
            "when cart/{emailAddress} endpoint hit," +
            "then a zalando problem is returned")
    void canGetBadRequestForInvalidEmailAddress(){
        ResponseEntity<Problem> responseEntity = restTemplate.exchange("http://localhost:" + port + "/cart/{emailAddress}", HttpMethod.PUT, null, Problem.class, ".invalid@gmail.com");

        assertZalandoProblemResponse(responseEntity, HttpStatus.BAD_REQUEST, "Invalid email address - .invalid@gmail.com");

    }

    @Test
    @DisplayName("Given a product with the requested product ID exists, " +
            "when cart/{productId} endpoint hit, " +
            "then a 200 OK response is returned")
    void canGet200OkResponseForAddingItemsToCart() {
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/cart/{productId}", null, Void.class, 3L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Given a valid email address, " +
            "when cart/{emailAddress} endpoint hit," +
            "then a 200 OK response is returned")
    void canGet200OkResponseForSendingEmailRequest(){
        doNothing().when(javaMailSender).send(mimeMessage -> {});

        ResponseEntity<Problem> responseEntity = restTemplate.exchange("http://localhost:" + port + "/cart/{emailAddress}", HttpMethod.PUT, null, Problem.class, "invalid@gmail.com");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void assertZalandoProblemResponse(final ResponseEntity<Problem> responseEntity, HttpStatus httpStatus, String detail) {
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatus);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(httpStatus);
        assertThat(responseEntity.getBody().getDetail()).isEqualTo(detail);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(httpStatus.name());
    }

}
