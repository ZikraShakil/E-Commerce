package com.example.e_commerce;

import java.sql.*;

public class databaseConnection {

    private static final String dbUrl = "jdbc:mysql://localhost:3306/ecommerce";

    private static final String username = "root";

    private static final String password = "Zikra@123#";

    private static Statement getStatement(){

        try{
            Connection connection = DriverManager.getConnection(dbUrl,username,password);
            return connection.createStatement();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getQueryTable(String query){

        try {
            Statement statement = getStatement();
            return statement.executeQuery(query);

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static int updateDB(String query){
        try {
            Statement statement = getStatement();
            return statement.executeUpdate(query);

        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }

    public static void main(String[] args) {
        databaseConnection con = new databaseConnection();

        ResultSet rs = con.getQueryTable("SELECT * FROM customer");

        if(rs != null){
            System.out.println("Connection Successful");
        }else {
            System.out.println("Connection Failed");
        }
    }

}