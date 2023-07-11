package com.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Initialization {

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String query = "";

    static Logger logger = LogManager.getLogger(Initialization.class);
    static Properties prop =new Properties();

    public void connect(){
        try {
            prop.load(new FileInputStream("src/main/resources/initialization.properties"));
            Class.forName(prop.getProperty("driverAddress"));
            connect = DriverManager.getConnection(prop.getProperty("db.url"), prop.getProperty("db.userId"),
                    prop.getProperty("db.password"));
            statement = connect.createStatement();
        } catch (ClassNotFoundException e) {
            logger.fatal("JDBC driver initialization issue ");
        } catch (SQLException e) {
            logger.fatal("Sql Exception : " + e.getMessage());
        } catch (FileNotFoundException e) {
            logger.fatal("couldn't find properties file: "+e.getMessage());
        } catch (IOException e) {
            logger.fatal("I/O exception issue with properties file: "+e.getMessage());
        }
    }

    public void readDataBase() {
        connect();
        query = "Select * From clients";
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int clientId = Integer.parseInt(resultSet.getString("client_id"));
                String name = resultSet.getString("name");
                String city = resultSet.getString("city");
                String phone = resultSet.getString("phone");
                System.out.println("Client Id: " + clientId);
                System.out.println("Name: " + name);
                System.out.println("City: " + city);
                System.out.println("Phone Number: " + phone);
            }
        } catch (SQLException e) {
            logger.error("SQL Exception: " + e.getMessage());
        }
        close();
    }

    public void insertIntoDataBase() {
        try {
            connect();
            query = "INSERT into clients (client_id,name,address,city,state,phone)" + "values(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, 13);
            preparedStatement.setString(2, "Narayana");
            preparedStatement.setString(3, "2323N woodlawn Blvd Apt#221");
            preparedStatement.setString(4, "Missouri");
            preparedStatement.setString(5, "KS");
            preparedStatement.setString(6, "945-444-9879");
            preparedStatement.execute();
            close();
        } catch (SQLException e) {
            logger.error("SQL Excepetion: " + e.getMessage());
        } catch (Exception e) {
            logger.error(e);
            logger.error("Error message: " + e.getMessage());
            logger.error("error trace: " + e.getStackTrace());
        }
    }

    public void updateDataBase(int id, String name) {
        connect();

        try {
            query = "UPDATE clients SET name = ? WHERE client_id = ?";
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot update Name field in respective id :" + e);
        }
        close();
    }

    public void deleteInDataBase(int id) {
        connect();
        query = "DELETE From clients WHERE client_id = " + id;
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error("SQL Exception: couldn't complete delete operation");
        }
        close();
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            logger.error(" printing from SQL Exception: " + e.getMessage());
        } catch (Exception e) {

        }
    }
}