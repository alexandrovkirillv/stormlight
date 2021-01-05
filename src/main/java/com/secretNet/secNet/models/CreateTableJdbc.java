package com.secretNet.secNet.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableJdbc {
    static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/sec";
    static final String JDBC_DRIVER = "org.postgresql.Driver";

    static final String USER = "secuser";
    static final String PASSWORD = "secpass";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            System.out.println("Registering JDBC driver...");
            Class.forName(JDBC_DRIVER);

            System.out.println("Creating connection to database...");
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

            System.out.println("Creating table in selected database...");
            statement = connection.createStatement();

            String SQL = "CREATE TABLE post " +
                    "(id SERIAL not NULL, " +
                    " title VARCHAR(50), " +
                    " anons VARCHAR (50), " +
                    " full_text VARCHAR (200), " +
                    " views INTEGER, " +
                    " PRIMARY KEY (id))";

            statement.executeUpdate(SQL);
            System.out.println("Table successfully created...");
        }finally {
            if(statement!=null){
                statement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }
    }
}