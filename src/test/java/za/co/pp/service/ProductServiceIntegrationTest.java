package za.co.pp.service;

import javax.sql.DataSource;
import java.util.List;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.pp.data.domain.ProductDomainObject;
import za.co.pp.data.dto.Product;
import za.co.pp.data.mapper.ProductMapper;
import za.co.pp.data.repository.ProductRepository;
import za.co.pp.utils.DbSetupCommonOperations;
import za.co.pp.utils.ProductUtils;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;
import static za.co.pp.utils.DbSetupCommonOperations.CREATE_SCHEMA;
import static za.co.pp.utils.DbSetupCommonOperations.CREATE_TABLE;
import static za.co.pp.utils.DbSetupCommonOperations.insertSeedData;
import static za.co.pp.utils.ProductUtils.createDatabaseAndPopulateProductsTable;

@SpringBootTest
public class ProductServiceIntegrationTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private DataSource dataSource;

    @Test
    void canGetAllProducts() throws Exception{
        createDatabaseAndPopulateProductsTable(dataSource);

        List<ProductDomainObject> products = this.productService.getAllProducts();

        assertThat(products.size()).isEqualTo(3);
    }
}
