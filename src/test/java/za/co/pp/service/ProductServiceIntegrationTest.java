package za.co.pp.service;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import za.co.pp.data.domain.ProductDomainObject;
import za.co.pp.exception.PrettyParadiseClientException;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static za.co.pp.utils.ProductUtils.createDatabaseAndPopulateProductsTable;
import static za.co.pp.utils.ProductUtils.getTestProductImageByteArray;

@SpringBootTest
public class ProductServiceIntegrationTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setup() throws Exception{
        createDatabaseAndPopulateProductsTable(dataSource);
    }

    @Test
    void canGetProductDomainObjectGivenAValidProductId() throws Exception{
        ProductDomainObject productDomainObject = this.productService.getProductDomainObject(1L);

        assertThat(productDomainObject).isNotNull();
        assertThat(productDomainObject.getId()).isEqualTo(1);
        assertThat(productDomainObject.getName()).isEqualToIgnoringCase("pink and pretty");
        assertThat(productDomainObject.getPrice()).isEqualTo(20.00);
        assertThat(Arrays.equals(productDomainObject.getImage(), getTestProductImageByteArray())).isTrue();
    }

    @Test
    void canThrowAPrettyParadiseClientExceptionForAnInvalidProductId() {
        PrettyParadiseClientException prettyParadiseClientException =
                assertThrows(PrettyParadiseClientException.class, () -> this.productService.getProductDomainObject(4L));

        assertThat(prettyParadiseClientException.getMessage()).isEqualToIgnoringCase("ProductEntity with productId 4 does not exist");
        assertThat(prettyParadiseClientException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void canGetAllProducts() {

        List<ProductDomainObject> products = this.productService.getAllProductDomainObjects();

        assertThat(products.size()).isEqualTo(3);
    }
}
