package com.faza.java.networking;

public class Main {

    public static void main(String[] args) {
        showDataMySQL();
        showDataLPostgreSQL();
    }

    public static void showDataMySQL() {
        JDBCMySQL jdbcMySQL = new JDBCMySQL();
        boolean isSuccess = jdbcMySQL.createConnection();

        System.out.println("Hasil create connection MySQL " + isSuccess);

        if (isSuccess) {
            jdbcMySQL.showData();
        }
    }

    public static void showDataLPostgreSQL() {
        JDBCPostgreSQL jdbcPostgreSQL = new JDBCPostgreSQL();
        boolean isSuccess = jdbcPostgreSQL.createConnection();

        System.out.println("Hasil create connection PostgreSQL " + isSuccess);
    }
}