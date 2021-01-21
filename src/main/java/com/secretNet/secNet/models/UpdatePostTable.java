package com.secretNet.secNet.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdatePostTable {
    static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/sec";
    static final String JDBC_DRIVER = "org.postgresql.Driver";

    static final String USER = "secuser";
    static final String PASSWORD = "secpass";

    public static void updatePost() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            System.out.println("Registering JDBC driver...");
            Class.forName(JDBC_DRIVER);

            System.out.println("Creating connection to database...");
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

            System.out.println("Creating table in selected database...");
            statement = connection.createStatement();

            String SQL = "SELECT atttypmod FROM pg_attribute " +
                    "WHERE attrelid = 'post'::regclass " +
                    "AND attname = 'full_text'";

            String SQL2 = "UPDATE pg_attribute SET atttypmod = 20000+4 " +
                    "WHERE attrelid = 'post'::regclass " +
                    "AND attname = 'full_text'";

            statement.executeUpdate(SQL);
            statement.executeUpdate(SQL2);

            System.out.println("Table successfully created...");
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}