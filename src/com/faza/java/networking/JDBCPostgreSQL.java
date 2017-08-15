package com.faza.java.networking;

import java.sql.*;
import java.util.Scanner;

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

    public void createTables() {
        Statement statement = null;

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to create statement...");
            e.printStackTrace();
        }

        if (statement != null) {
            String sql = "CREATE TABLE LOGISTIC_PROVIDER " +
                    "(ID INT PRIMARY KEY NOT NULL, " +
                    "LOGISTIC_CODE TEXT NOT NULL, " +
                    "LOGISTIC_NAME TEXT NOT NULL, " +
                    "FEE REAL, " +
                    "DISCOUNT REAL)";

            try {
                statement.executeUpdate(sql);
                statement.close();

                System.out.println("Finish creating tables");
            } catch (Exception e) {
                System.out.println("Failure on creating tables : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void showAllLogisticProvider() {
        Statement statement = null;

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to create statement...");
            e.printStackTrace();
        }

        if (statement != null) {
            String sql = "SELECT * FROM LOGISTIC_PROVIDER";
            ResultSet resultSet = null;

            try {
                resultSet = statement.executeQuery(sql);
            } catch (SQLException e) {
                System.out.println("Failed to execute query...");
                e.printStackTrace();
            }

            if (resultSet != null)
                printDataFromResultSet(resultSet);

            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertDataToTables() {
        Statement statement = null;

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to create statement...");
            e.printStackTrace();
        }

        if (statement != null) {
            String sql = "INSERT INTO LOGISTIC_PROVIDER " +
                    "(ID, LOGISTIC_CODE, LOGISTIC_NAME, FEE, DISCOUNT) " +
                    "VALUES (1, 'JNE', 'PT. Tiki Jalur Nugraha Ekakurir', 15000, 0.02)";
            String sql2 = "INSERT INTO LOGISTIC_PROVIDER " +
                    "(ID, LOGISTIC_CODE, LOGISTIC_NAME, FEE, DISCOUNT) " +
                    "VALUES (2, 'POS', 'PT. POS INDONESIA', 13500, 0.015)";

            try {
                statement.executeUpdate(sql);
                statement.executeUpdate(sql2);
                statement.close();

                System.out.println("Finish inserting data to tables");
            } catch(Exception e) {
                System.out.println("Failure on inserting data to tables : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void showInputForm() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Logistic Code : ");
        String logisticCode = scanner.nextLine();

        scanner.close();
        showLogisticProvider(logisticCode);
    }

    public void showLogisticProvider(String logisticCode) {
        showLogisticProviderUsingStatement(logisticCode);
        showLogisticProviderUsingPreparedStatement(logisticCode);
    }

    public void showLogisticProviderUsingPreparedStatement(String logisticCode) {
        System.out.println("Using Prepared Statement to get Logistic Provider " + logisticCode);

        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM LOGISTIC_PROVIDER WHERE LOGISTIC_CODE = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println("Failed to create statement...");
            e.printStackTrace();
        }

        if (preparedStatement != null) {
            ResultSet resultSet = null;

            try {
                preparedStatement.setString(1, logisticCode);
                resultSet = preparedStatement.executeQuery();
            } catch (SQLException e) {
                System.out.println("Failed to execute query...");
                e.printStackTrace();
            }

            if (resultSet != null)
                printDataFromResultSet(resultSet);
        }

        try {
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showLogisticProviderUsingStatement(String logisticCode) {
        System.out.println("Using Statement to get Logistic Provider " + logisticCode);

        Statement statement = null;

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to create statement...");
            e.printStackTrace();
        }

        if (statement != null) {
            String sql = "SELECT * FROM LOGISTIC_PROVIDER WHERE LOGISTIC_CODE = '" + logisticCode + "'";
            ResultSet resultSet = null;

            try {
                resultSet = statement.executeQuery(sql);
            } catch (SQLException e) {
                System.out.println("Failed to execute query...");
                e.printStackTrace();
            }

            if (resultSet != null)
                printDataFromResultSet(resultSet);

            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printDataFromResultSet(ResultSet resultSet) {
        int id;
        float fee, discount;
        String code, name;

        try {
            System.out.println("");

            while (resultSet.next()) {
                id = resultSet.getInt("id");
                fee = resultSet.getFloat("fee");
                discount = resultSet.getFloat("discount");
                code = resultSet.getString("logistic_code");
                name = resultSet.getString("logistic_name");

                System.out.println("ID = " + id);
                System.out.println("CODE = " + code);
                System.out.println("NAME = " + name);
                System.out.println("FEE = " + fee);
                System.out.println("discount = " + discount);
                System.out.println("");
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
}
