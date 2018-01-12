package com.goit.jdbc.app;

import java.sql.*;
import java.util.List;

public class Storage {
    private String connectionURL = "jdbc:mysql://localhost/hib_test";
    private String user = "hib_user";
    private String pass = "hib_pass";

    private Connection connection;
    private Statement statement;

    private PreparedStatement selectSt;
    private PreparedStatement updateSt;

    public Storage() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
        }

        try {
            connection = DriverManager.getConnection(connectionURL, user, pass);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Storage storage = new Storage();
    }
}
