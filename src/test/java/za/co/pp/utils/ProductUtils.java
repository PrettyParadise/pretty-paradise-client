package za.co.pp.utils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;

import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static za.co.pp.utils.DbSetupCommonOperations.CREATE_SCHEMA;
import static za.co.pp.utils.DbSetupCommonOperations.CREATE_TABLE;
import static za.co.pp.utils.DbSetupCommonOperations.DROP_SCHEMA_IF_EXISTS;
import static za.co.pp.utils.DbSetupCommonOperations.insertSeedData;

public class ProductUtils {

    public static byte[] getTestProductImageByteArray() throws Exception {
        File productImageFile = ResourceUtils.getFile("classpath:product_image.jpg");
        InputStream inputStream = new FileInputStream(productImageFile);
        return IOUtils.toByteArray(inputStream);
    }

    public static void createDatabaseAndPopulateProductsTable(DataSource dataSource) throws Exception {
        Operation operations = sequenceOf(
                DROP_SCHEMA_IF_EXISTS,
                CREATE_SCHEMA,
                CREATE_TABLE,
                insertSeedData()
        );

        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operations);
        dbSetup.launch();
    }

}
