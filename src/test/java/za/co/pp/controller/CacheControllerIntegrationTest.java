package za.co.pp.controller;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import za.co.pp.exception.Problem;

import static org.assertj.core.api.Assertions.assertThat;
import static za.co.pp.utils.ProductUtils.createDatabaseAndPopulateProductsTable;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CacheControllerIntegrationTest {

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

        assertZalandoProblemResponse(responseEntity);
    }

    @Test
    @DisplayName("Given a product with the requested product ID exists, " +
            "when cart/{productId} endpoint hit, " +
            "then a 200 OK response is returned")
    void canGet200OkResponse() {
        ResponseEntity<Problem> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/cart/{productId}", null, Problem.class, 3L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void assertZalandoProblemResponse(final ResponseEntity<Problem> responseEntity) {
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody().getDetail()).isEqualTo("A product with the specified ID does not exist.");
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(HttpStatus.NOT_FOUND.name());
    }

}
