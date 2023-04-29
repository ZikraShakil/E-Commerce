package com.example.e_commerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {


    public static boolean placeOrder(customer customer,product product){
        String getOrderId = "SELECT MAX(group_order_id)+1 id FROM orders";

        databaseConnection connection = new databaseConnection();

        try {
            ResultSet rs = connection.getQueryTable(getOrderId);
            if(rs.next()){
                String placeOrder = "INSERT INTO ORDERS (group_order_id,costomer_id,product_id)VALUES("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                return databaseConnection.updateDB(placeOrder) != 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public static int placeMultipleOrder(customer customer, ObservableList<product> productList){
        String getOrderId = "SELECT MAX(group_order_id)+1 id FROM orders";

        databaseConnection connection = new databaseConnection();

        try {
            ResultSet rs = connection.getQueryTable(getOrderId);
            int count = 0;
            if(rs.next()){
                for (product product : productList){
                    String placeOrder = "INSERT INTO ORDERS (group_order_id,customer_id,product_id)VALUES("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                    count+=databaseConnection.updateDB(placeOrder);
                }
                return  count;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }
}