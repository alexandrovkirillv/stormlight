package com.secretNet.secNet.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdatePostColumn {
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

            String SQL = "ALTER TABLE post ADD COLUMN time text";

            statement.executeUpdate(SQL);

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