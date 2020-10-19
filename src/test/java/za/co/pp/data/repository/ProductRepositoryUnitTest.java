package za.co.pp.data.repository;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.pp.data.entity.ProductEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static za.co.pp.utils.ProductUtils.createDatabaseAndPopulateProductsTable;
import static za.co.pp.utils.ProductUtils.getTestProductImageByteArray;

@SpringBootTest
class ProductRepositoryUnitTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() throws Exception {
        createDatabaseAndPopulateProductsTable(dataSource);

    }

    @Test
    void canGetAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();

        assertThat(productEntities.size()).isEqualTo(3);
    }

    @Test
    void canGetProductByProductId() throws Exception {
        ProductEntity productEntity = productRepository.findById(1L).get();

        assertThat(productEntity).isNotNull();
        assertThat(productEntity.getId()).isEqualTo(1);
        assertThat(productEntity.getName()).isEqualToIgnoringCase("pink and pretty");
        assertThat(Arrays.equals(productEntity.getImage(), getTestProductImageByteArray())).isTrue();

    }

}
