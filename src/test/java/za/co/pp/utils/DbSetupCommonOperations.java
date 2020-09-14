package za.co.pp.utils;

import com.ninja_squad.dbsetup.operation.Operation;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.Operations.sql;
import static za.co.pp.utils.ProductUtils.getTestProductImageByteArray;

public class DbSetupCommonOperations {

    public static Operation CREATE_SCHEMA = sql("CREATE SCHEMA pretty_paradise");
    public static Operation CREATE_TABLE = sql("CREATE TABLE pretty_paradise.product(" +
            "product_id BIGINT AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(50)," +
            "price DECIMAL UNSIGNED," +
            "image MEDIUMBLOB)");

    public static Operation insertSeedData() throws Exception {
        return sequenceOf(
                insertInto("pretty_paradise.product")
                    .columns("product_id", "name", "price", "image")
                    .values(1, "pink and pretty", 20.00, getTestProductImageByteArray())
                    .build(),
                insertInto("pretty_paradise.product")
                        .columns("product_id", "name", "price", "image")
                        .values(2, "purple and popping", 20.00, getTestProductImageByteArray())
                        .build(),
                insertInto("pretty_paradise.product")
                        .columns("product_id", "name", "price", "image")
                        .values(3, "gray and glitter", 20.00, getTestProductImageByteArray())
                        .build()
                );
    }
}
