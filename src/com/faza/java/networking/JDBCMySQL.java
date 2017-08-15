package com.faza.java.networking;

import java.sql.*;

/**
 * Created by faza on 30/03/17.
 */
public class JDBCMySQL {

    private Connection connection;

    public JDBCMySQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("Driver create successfull");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }

        connection = null;
    }

    public boolean createConnection() {
        String url = "jdbc:mysql://localhost:3306/Mahasiswa";
        String username = "root";
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

    public void showData() {
        Statement statement = null;

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to create statement...");
            e.printStackTrace();
        }

        if (statement != null) {
            String sql = "SELECT * FROM data";
            ResultSet resultSet = null;

            try {
                resultSet = statement.executeQuery(sql);
            } catch (SQLException e) {
                System.out.println("Failed to execute query...");
                e.printStackTrace();
            }

            if (resultSet != null) {
                int id, nrp;
                String name;

                try {
                    while (resultSet.next()) {
                        id = resultSet.getInt(1);
                        nrp = resultSet.getInt(2);
                        name = resultSet.getString(3);

                        System.out.println(id + ". " + nrp + " - " + name);
                    }
                } catch (SQLException e) {
                    System.out.println("Failed to show result data...");
                    e.printStackTrace();
                }

                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}