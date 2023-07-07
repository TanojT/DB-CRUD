package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Initialization {
    
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String query = "";

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_invoicing", "root",
                    "mysqlroot@2023");
            statement = connect.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver initialization issue ");
        } catch (SQLException e) {
            System.out.println("Sql Exception : " + e.getMessage());
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
            System.out.println("SQL Exception: " + e.getMessage());
        }
        close();
    }

    public void insertIntoDataBase() {
        try {
            connect();
            query="INSERT into clients (client_id,name,address,city,state,phone)"+"values(?,?,?,?,?,?)";
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
            System.err.println("SQL Excepetion: " + e.getMessage());
        } catch (Exception e){
            System.err.println(e);
            System.err.println("Error message: "+e.getMessage());
            System.err.println("error trace: "+e.getStackTrace());
        }
    }

    public void updateDataBase(int id, String name){
        connect();
        
        query = "UPDATE clients SET name='"+name+"' WHERE client_id ="+id;
        try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			System.err.println("Cannot update Name field in respective id :"+e);
		}
        close();
    }

    public void deleteInDataBase(int id){
        connect();
        query="DELETE From clients WHERE client_id = "+id;
        try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			System.err.println("SQL Exception: couldn't complete delete operation");
		}
        close();
    }

    private void close() {
        try {
            if(resultSet!=null){
                resultSet.close();
            }
            if(statement!=null){
                statement.close();
            }
            if(connect!=null){
                connect.close();
            }
        } catch (SQLException e) {
            System.out.println(" printing from SQL Exception: " + e.getMessage());
        }catch(Exception e){
            
        }
    }
}