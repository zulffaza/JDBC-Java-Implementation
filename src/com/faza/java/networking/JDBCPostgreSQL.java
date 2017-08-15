package com.faza.java.networking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 */
public class JDBCPostgreSQL {

    private Connection connection;

    public JDBCPostgreSQL() {
        try {
            Class.forName("org.postgresql.Driver");

            System.out.println("Driver create successfull");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }

        connection = null;
    }

    public boolean createConnection() {
        String url = "jdbc:postgresql://localhost:5432/Mahasiswa";
        String username = "postgres";
        String password = "D4ITA2015";

        boolean isSuccess = true;

        try {
            connection = DriverManager.getConnection(url, username, password); // Membuat koneksi ke database

            System.out.println("Connection Successfull");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();

            isSuccess = false;
        }

        return isSuccess;
    }
}
