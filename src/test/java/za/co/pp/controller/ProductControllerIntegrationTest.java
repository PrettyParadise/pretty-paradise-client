package za.co.pp.controller;

import javax.sql.DataSource;
import java.util.Base64;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import za.co.pp.data.dto.Product;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;
import static za.co.pp.utils.DbSetupCommonOperations.CREATE_SCHEMA;
import static za.co.pp.utils.DbSetupCommonOperations.DROP_SCHEMA_IF_EXISTS;
import static za.co.pp.utils.DbSetupCommonOperations.CREATE_SPRING_SESSION_ATTRIBUTES_TABLE;
import static za.co.pp.utils.DbSetupCommonOperations.CREATE_SPRING_SESSION_TABLE;
import static za.co.pp.utils.ProductUtils.createDatabaseAndPopulateProductsTable;
import static za.co.pp.utils.ProductUtils.getTestProductImageByteArray;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegrationTest {

    private final DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate;

    @Autowired
    public ProductControllerIntegrationTest(final DataSource dataSource, final TestRestTemplate restTemplate) {
        this.dataSource = dataSource;
        this.restTemplate = restTemplate;
    }

    @Test
    void canGetAllProducts() throws Exception {
        createDatabaseAndPopulateProductsTable(dataSource);

        ResponseEntity<Product[]> products = this.restTemplate.getForEntity("http://localhost:" + port + "/products", Product[].class);

        assertThat(products.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(products.getBody()).hasSize(3);
        assertValuesOfEachProduct(products.getBody());
    }


    private void assertValuesOfEachProduct(Product[] products) throws Exception {
        String encodedTestImage = getTestProductEncodedImage();
        assertThat(products[0].getId()).isEqualTo(1L);
        assertThat(products[0].getName()).isEqualTo("pink and pretty");
        assertThat(products[0].getPrice()).isEqualTo(20.00);
        assertThat(products[0].getEncodedImage()).isEqualTo(encodedTestImage);

        assertThat(products[1].getId()).isEqualTo(2L);
        assertThat(products[1].getName()).isEqualTo("purple and popping");
        assertThat(products[1].getPrice()).isEqualTo(20.00);
        assertThat(products[1].getEncodedImage()).isEqualTo(encodedTestImage);

        assertThat(products[2].getId()).isEqualTo(3L);
        assertThat(products[2].getName()).isEqualTo("gray and glitter");
        assertThat(products[2].getPrice()).isEqualTo(20.00);
        assertThat(products[2].getEncodedImage()).isEqualTo(encodedTestImage);
    }

    private String getTestProductEncodedImage() throws Exception {
        return Base64.getEncoder().encodeToString(getTestProductImageByteArray());
    }
}
