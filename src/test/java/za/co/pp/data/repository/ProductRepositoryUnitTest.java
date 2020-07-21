package za.co.pp.data.repository;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.pp.data.entity.ProductEntity;

import javax.sql.DataSource;
import java.util.List;

import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;
import static za.co.pp.data.utils.DbSetupCommonOperations.CREATE_SCHEMA;
import static za.co.pp.data.utils.DbSetupCommonOperations.CREATE_TABLE_PRODUCT;
import static za.co.pp.data.utils.DbSetupCommonOperations.INSERT_TWO_PRODUCT_RECORDS;
import static za.co.pp.data.utils.DbSetupCommonOperations.DELETE_TABLE_PRODUCT;
import static za.co.pp.data.utils.DbSetupCommonOperations.DELETE_SCHEMA_PRETTY_PARADISE;

@SpringBootTest
public class ProductRepositoryUnitTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DataSource dataSource;

    @Test
    void canLoadContext(){}

    @BeforeEach
    void setup() {
        Operation operation = sequenceOf(
//                DELETE_TABLE_PRODUCT,
//                DELETE_SCHEMA_PRETTY_PARADISE,
                CREATE_SCHEMA,
                CREATE_TABLE_PRODUCT,
                INSERT_TWO_PRODUCT_RECORDS);
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();
    }

//    @AfterEach
//    void cleanup() {
//        Operation operation = sequenceOf(
//                );
//        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
//        dbSetup.launch();
//    }

    @Test
    public void canGetAllProducts(){
        List<ProductEntity> productEntities = productRepository.findAll();
        assertThat(productEntities.size()).isNotZero();
    }
}
