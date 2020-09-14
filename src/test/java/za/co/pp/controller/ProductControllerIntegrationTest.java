package za.co.pp.controller;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.co.pp.data.dto.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static za.co.pp.utils.ProductUtils.createDatabaseAndPopulateProductsTable;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegrationTest {
    private final DataSource dataSource;

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate;

    @Autowired
    public ProductControllerIntegrationTest(final DataSource dataSource, final TestRestTemplate restTemplate) {
        this.dataSource = dataSource;
        this.restTemplate = restTemplate;
    }

    @Test
    void canGetAllProducts() throws Exception{
        createDatabaseAndPopulateProductsTable(dataSource);

        ResponseEntity<Product[]> products =  this.restTemplate.getForEntity("http://localhost:" + port +"/products", Product[].class);

        assertThat(products.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(products.getBody()).hasSize(3);
        assertValuesOfEachProduct(products.getBody());
    }

    @Test
    void canGetProblemResponseWithInternalServerError(){

    }

    private void assertValuesOfEachProduct(Product[] products) {
        assertThat(products[0].getId()).isEqualTo(1L);
        assertThat(products[0].getName()).isEqualTo("pink and pretty");
        assertThat(products[0].getPrice()).isEqualTo(20.00);

        assertThat(products[1].getId()).isEqualTo(2L);
        assertThat(products[1].getName()).isEqualTo("purple and popping");
        assertThat(products[1].getPrice()).isEqualTo(20.00);

        assertThat(products[2].getId()).isEqualTo(3L);
        assertThat(products[2].getName()).isEqualTo("gray and glitter");
        assertThat(products[2].getPrice()).isEqualTo(20.00);
    }
}
