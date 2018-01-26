package com.goit.jdbc.app.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcDeveloperDao implements DAOBase<Developer, Long> {
    private Properties properties;

    private String dbUrl;
    private String dbUser;
    private String dbPass;
    private String dbDriver;

    private Connection connection;
    private Statement statement;
    private PreparedStatement addPreparedStatement;
    private PreparedStatement updatePrStatement;

    public JdbcDeveloperDao() {
        readSettings();
        initDbConnection();
        initStatements();
    }

    private void initStatements() {
        try {
            statement = connection.createStatement();
            addPreparedStatement = connection.prepareStatement(
                    "INSERT INTO developer (first_name, last_name, specialty) " +
                            "VALUES (?, ?, ?);");
            updatePrStatement = connection.prepareStatement("UPDATE developer set first_name=?, last_name=?, specialty=? where id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void add(Developer developer) {
        try {
            addPreparedStatement.setString(1, developer.getFirstName());
            addPreparedStatement.setString(2, developer.getLastName());
            addPreparedStatement.setString(3, developer.getSpecialty());
            addPreparedStatement.executeUpdate();

            ResultSet rs = statement.executeQuery("SELECT max(id) FROM developer;");
            if (rs.first()) {
                developer.setId(rs.getLong("max(id)"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Developer getById(Long id) {
        String sqlQwery = "SELECT first_name,last_name,specialty FROM developer WHERE id =" + id + ";";
        Developer developer = new Developer();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sqlQwery);
            if (resultSet.first()) {

                developer.setId(id);
                developer.setFirstName(resultSet.getString("first_name"));
                developer.setLastName(resultSet.getString("last_name"));
                developer.setSpecialty(resultSet.getString("specialty"));
                return developer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    public List<Developer> getAll() {

        List<Developer> developerList = new ArrayList<Developer>();

        String sqlQwery = "SELECT id,first_name,last_name,specialty FROM developer;";

        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sqlQwery);
            while (resultSet.next()) {
                Developer developer = new Developer();
                developer.setId(resultSet.getLong("id"));
                developer.setFirstName(resultSet.getString("first_name"));
                developer.setLastName(resultSet.getString("last_name"));
                developer.setSpecialty(resultSet.getString("specialty"));
                developerList.add(developer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return developerList;
    }

    public void update(Developer developer) {
        try {
            updatePrStatement.setString(1, developer.getFirstName());
            updatePrStatement.setString(2, developer.getLastName());
            updatePrStatement.setString(3, developer.getSpecialty());
            updatePrStatement.setLong(4, developer.getId());
            updatePrStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Developer developer) {
        String delete = "delete from developer where id=";
        delete += developer.getId();
        try {
            statement.executeUpdate(delete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /*Developer dev1 = new Developer();
        dev1.setFirstName("Egor");
        dev1.setLastName("Jackson");
        dev1.setSpecialty("seo");
        System.out.println(dev1);
        new JdbcDeveloperDao().add(dev1);
        System.out.println(dev1);*/
//        Developer dev1 = new Developer();
//        dev1.setId(1);
//        //  new JdbcDeveloperDao().delete(dev1);
//        Developer developer1 = new JdbcDeveloperDao().getById(20L);
//        System.out.println(developer1);
        JdbcDeveloperDao jdbcDao = new JdbcDeveloperDao();
        Developer dev1 = jdbcDao.getById(3L);
        System.out.println(dev1);
        dev1.setFirstName("Ivan");
        dev1.setLastName("Ivanov");
        dev1.setSpecialty("CopyPaster");
        jdbcDao.update(dev1);
        List<Developer> developerList = jdbcDao.getAll();
        for(Developer dev: developerList){
            System.out.println(dev.toString());
        }
    }
}
