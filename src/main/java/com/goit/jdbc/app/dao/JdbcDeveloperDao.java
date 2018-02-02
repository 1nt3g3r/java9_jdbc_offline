package com.goit.jdbc.app.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class JdbcDeveloperDao implements DAOBase<Developer, Long> {
    public static final int LIMIT = 1000;
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

    public void addDevelopers(List<Developer> developers) {
        for(Developer d: developers) {
            add(d);
        }
//
//        try {
//            connection.setAutoCommit(false);
//            int limit = LIMIT;
//            for (Developer d : developers) {
//
//                addPreparedStatement.setString(1, d.getFirstName());
//                addPreparedStatement.setString(2, d.getLastName());
//                addPreparedStatement.setString(3, d.getSpecialty());
//                addPreparedStatement.addBatch();
//                if (limit <= 0) {
//                    addPreparedStatement.executeBatch();
//                    connection.commit();
//                    limit = LIMIT;
//                } else {
//                    limit--;
//                }
//            }
//            addPreparedStatement.executeBatch();
//            connection.commit();
//            connection.setAutoCommit(true);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
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
        } finally {
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

    public static List<Developer> developerGenerator(int count) {
        ArrayList<Developer> developerList = new ArrayList<Developer>();
        for (int i = 0; i < count; i++) {
            developerList.add(randomDeveloper());
        }
        return developerList;
    }

    public static Developer randomDeveloper() {
        Random r = new Random();
        Developer developer = new Developer();
        developer.setFirstName(Float.toString(r.nextFloat()));
        developer.setLastName(Float.toString(r.nextFloat()));
        developer.setSpecialty(Float.toString(r.nextFloat()));
        return developer;
    }

    public void clear() {
        try {
            statement.executeUpdate("truncate developer");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        JdbcDeveloperDao jdbcDao = new JdbcDeveloperDao();

        List<Developer> developers = developerGenerator(500);
        long timer = System.currentTimeMillis();
        for (Developer d : developers){
            jdbcDao.add(d);
        }
        timer = System.currentTimeMillis() - timer;
        System.out.println("Mills: " + timer);
    }
}
