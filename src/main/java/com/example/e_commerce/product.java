package com.example.e_commerce;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class product {

    private SimpleIntegerProperty id;

    private SimpleStringProperty name;

    private SimpleDoubleProperty price;

    public product(int id, String name, double price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    public int getId() {
        return id.get();
    }

    public static ObservableList<product> getProducts(){
        String query = "SELECT id,name,price FROM product";
        return fetchData(query);
    }

    public static ObservableList<product> fetchData(String query){

        ObservableList data = FXCollections.observableArrayList();

        databaseConnection connection = new databaseConnection();

        try {
            ResultSet rs = connection.getQueryTable(query);
            while (rs.next()){
                product product = new product(rs.getInt("id"),rs.getString("name"),rs.getDouble("price"));
                data.add(product);
            }
            return data;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;



    }




    public String getName() {
        return name.get();
    }

    public double getPrice() {
        return price.get();
    }





}