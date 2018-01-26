package com.goit.jdbc.app.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class JdbcDeveloperDao implements DAOBase<Developer, Long> {
    private Properties properties;

    private String dbUrl;
    private String dbUser;
    private String dbPass;
    private String dbDriver;

    private Connection connection;

    public JdbcDeveloperDao() {
        readSettings();
        initDbConnection();
    }

    private void readSettings() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("settings.ini"));
            dbDriver = properties.getProperty("db.driver");
            dbUrl = properties.getProperty("db.url");
            dbUser = properties.getProperty("db.user");
            dbPass = properties.getProperty("db.pass");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDbConnection() {
        try {
            Class.forName(dbDriver);
        } catch (Exception e) {
        }

        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JdbcDeveloperDao();
    }

    public void add(Developer developer) {

    }

    public Developer getById(Long id) {
        return null;
    }

    public List<Developer> getAll() {
        return null;
    }

    public void update(Developer developer) {

    }

    public void delete(Developer developer) {

    }
}
