package com.example.e_commerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class productList {

    private TableView<product> productTable;



    public VBox createTable(ObservableList data){

        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));


        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        // dummy data.


        productTable = new TableView<>();
        productTable.setItems(data);
        productTable.getColumns().addAll(id,name,price);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vBox = new VBox();

        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(productTable);
        return vBox;

    }


    public VBox getdummyTable(){
        ObservableList<product> data = FXCollections.observableArrayList();
        data.add(new product(2,"iPhone",44500) );
        data.add(new product(5,"iPhone 14",84500) );
        return createTable(data);
    }

    public VBox getAllProducts(){
        ObservableList<product> data = product.getProducts();
        return createTable(data);
    }

    public product getSelectedProduct(){
        return  productTable.getSelectionModel().getSelectedItem();
    }


    public  VBox getCartItems(ObservableList<product> data){
        return createTable(data);
    }





}