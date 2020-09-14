package za.co.pp.data.repository;

import javax.sql.DataSource;

import java.util.List;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import za.co.pp.data.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;
import static za.co.pp.utils.DbSetupCommonOperations.CREATE_SCHEMA;
import static za.co.pp.utils.DbSetupCommonOperations.CREATE_TABLE;
import static za.co.pp.utils.DbSetupCommonOperations.insertSeedData;
import static za.co.pp.utils.ProductUtils.createDatabaseAndPopulateProductsTable;

@SpringBootTest
class ProductRepositoryUnitTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    ProductRepository productRepository;

    @Test
    void canGetAllProducts() throws Exception{
        createDatabaseAndPopulateProductsTable(dataSource);

        List<ProductEntity> productEntities = productRepository.findAll();

        assertThat(productEntities.size()).isEqualTo(3);
    }

}
