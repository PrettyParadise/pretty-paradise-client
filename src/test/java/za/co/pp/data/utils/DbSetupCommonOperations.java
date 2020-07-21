package za.co.pp.data.utils;

import com.ninja_squad.dbsetup.operation.Operation;

import static com.ninja_squad.dbsetup.Operations.sql;

public class DbSetupCommonOperations {

    public static Operation CREATE_SCHEMA = sql("CREATE SCHEMA IF NOT EXISTS pretty_paradise;");

    public static Operation CREATE_TABLE_PRODUCT = sql(
            "CREATE TABLE IF NOT EXISTS pretty_paradise.product (" +
                    "product_id BIGINT PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "price DOUBLE UNSIGNED);");

    public static Operation INSERT_TWO_PRODUCT_RECORDS=sql(
            "INSERT INTO pretty_paradise.product (product_id, name, price) VALUES " +
                    "(1, 'Dangote', 49.50), " +
                    "(2, 'Eer', 49.50);");

    public static Operation DELETE_TABLE_PRODUCT=sql(
            "DELETE FROM IF EXISTS pretty_paradise.product;");

    public static Operation DELETE_SCHEMA_PRETTY_PARADISE=sql(
            "DELETE SCHEMA IF EXISTS pretty_paradise;");
}
