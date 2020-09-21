package za.co.pp.utils;

import com.ninja_squad.dbsetup.operation.Operation;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.Operations.sql;
import static com.ninja_squad.dbsetup.Operations.truncate;
import static za.co.pp.utils.ProductUtils.getTestProductImageByteArray;

public class DbSetupCommonOperations {

    public static final Operation CREATE_SCHEMA = sql("CREATE SCHEMA pretty_paradise");
    public static final Operation CREATE_TABLE = sql("CREATE TABLE pretty_paradise.product(" +
            "product_id BIGINT AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(50)," +
            "price DECIMAL UNSIGNED," +
            "image MEDIUMBLOB)");

    public static final Operation CREATE_SPRING_SESSION_TABLE = sql("CREATE TABLE pretty_paradise.SPRING_SESSION (" +
            "PRIMARY_ID CHAR(36) NOT NULL," +
            "SESSION_ID CHAR(36) NOT NULL," +
            "CREATION_TIME BIGINT NOT NULL," +
            "LAST_ACCESS_TIME BIGINT NOT NULL," +
            "MAX_INACTIVE_INTERVAL INT NOT NULL," +
            "EXPIRY_TIME BIGINT NOT NULL," +
            "PRINCIPAL_NAME VARCHAR(100)," +
            "CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)" +
            ") ENGINE=InnoDB ROW_FORMAT=DYNAMIC;");

    public static final Operation CREATE_SPRING_SESSION_ATTRIBUTES_TABLE = sql("CREATE TABLE pretty_paradise.SPRING_SESSION_ATTRIBUTES (" +
            "SESSION_PRIMARY_ID CHAR(36) NOT NULL," +
            "ATTRIBUTE_NAME VARCHAR(200) NOT NULL," +
            "ATTRIBUTE_BYTES BLOB NOT NULL," +
            "CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME)," +
            "CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE" +
            ") ENGINE=InnoDB ROW_FORMAT=DYNAMIC;");

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

    public static  final Operation DROP_SCHEMA_IF_EXISTS = sql("DROP SCHEMA IF EXISTS pretty_paradise CASCADE");

}
