package za.co.pp.controller.validation;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.pp.exception.PrettyParadiseClientException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.fail;
import static za.co.pp.utils.ProductUtils.createDatabaseAndPopulateProductsTable;

@SpringBootTest
class ProductIdValidatorUnitTest {
    @Autowired
    private ProductIdValidator productIdValidator;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setup() throws Exception{
        createDatabaseAndPopulateProductsTable(dataSource);
    }

    @Test
    @DisplayName("Given a product with the requested productId does not exist, " +
            "when the validateProductId method called, " +
            "then a PrettyParadiseException is thrown")
    void givenAProductDoesNotExistThrowPrettyParadiseException() throws Exception{
        try {
            productIdValidator.validateProductId(4L);
            fail("A product with product ID 1 does not exist, an exception should have been thrown");
        } catch (PrettyParadiseClientException ex){
            assertThat(ex.getMessage()).isEqualToIgnoringCase("A product with the specified ID does not exist.");
        }
    }

    @Test
    @DisplayName("Given a product with the requested productId existing, " +
            "hen the validateProductId method called, " +
            "then no exception is thrown")
    void givenAProductExistsAndGetsNoExceptionIsThrown(){
        assertThatCode(() -> productIdValidator.validateProductId(1L)).doesNotThrowAnyException();
    }

}
