package com.example.e_commerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.stream.Stream;

public class UserInterface {

    GridPane login_page;
    HBox header;
    HBox footer;

    customer logedInCustomer;

    VBox body;
    Label greet;
    Button btn_signIn;

    Button btn_home;

    Button btn_placeOrder = new Button("Place Order");

    ObservableList<product> itemsIncart = FXCollections.observableArrayList();

    productList productList = new productList();

    VBox productPage;
    BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(1000,750);


        root.setTop(header);
        //root.setCenter(login_page);
        body = new VBox();
        root.setCenter(body);
        body.setPadding(new Insets(20));
        body.setAlignment(Pos.CENTER);
        productPage = productList.getAllProducts();
        body.getChildren().add(productPage);

        root.setBottom(footer);
        footer.setAlignment(Pos.CENTER);




        return root;
    }

    public UserInterface(){
        createHeader();
        createLoginPage();
        createFooter();
    }






    private void createLoginPage(){
        // user name label
        Text user_name = new Text("User Name");
        TextField user = new TextField();
        user.setPromptText("Enter Your Mail id");
        // password label
        Text password = new Text("Password");
        PasswordField pass = new PasswordField();
        pass.setPromptText("Enter your Password");
        // setting grid
        login_page = new GridPane();
        login_page.setAlignment(Pos.CENTER);
        // setting background img to center.
        // set padding
        login_page.setHgap(15);
        login_page.setVgap(15);
        // Message label
        Label mess_label = new Label("Hi 😊");
        // Creating Button .
        Button btn_login = new Button("Login");
        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = user.getText();
                String password = pass.getText();
                login login = new login();
                logedInCustomer = login.customerLogin(name,password);
                if(logedInCustomer == null){
                    mess_label.setText("Login Failed !! Please Enter Correct USERNAME or Password :( ");
                }else {
                    mess_label.setText("Welcome "+logedInCustomer.getName());
                    greet.setText("Welcome "+logedInCustomer.getName());
                    header.getChildren().add(greet);
                    body.getChildren().clear();//remove everything
                    body.getChildren().add(productPage);//show products Page .
                }
            }
        });
        // Adding components to login page.
        login_page.add(user_name,0,0);
        login_page.add(user,1,0);
        login_page.add(password,0,1);
        login_page.add(pass,1,1);
        login_page.add(mess_label,0,2);
        login_page.add(btn_login,1,2);

    }

    private  void  createHeader() {

        // Search Button .
        TextField search_text = new TextField();
        search_text.setPromptText("Search Here");
        Button btn_search = new Button("Search");
        search_text.setPrefWidth(180);
        // Sign in button.
        btn_signIn = new Button("Sign In");

        // Home Button.
        btn_home = new Button();
        Image home_img = new Image("C:\\Users\\zikra\\IdeaProjects\\e_commerce\\src\\main\\java\\home.png");
        ImageView view = new ImageView(home_img);
        view.setFitWidth(25);
        view.setFitHeight(21);
        btn_home.setGraphic(view);

        // Creating and Adding image to cart button.
        Button btn_cart = new Button();
        Image img = new Image("C:\\Users\\zikra\\IdeaProjects\\e_commerce\\src\\main\\java\\pngegg.png");
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(21);
        imgView.setFitWidth(25);
        btn_cart.setGraphic(imgView);
        greet = new Label();

        // Adjusting header.
        header = new HBox();
        header.setPadding(new Insets(10));
        header.setSpacing(10);
        header.setFillHeight(true);
        header.setAlignment(Pos.CENTER);

        // Home Button Functionality.

        btn_home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footer.setVisible(true);
            }
        });

        // sign in button  functionality.
        btn_signIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                body.getChildren().clear();//remove everything
                body.getChildren().add(login_page);//show login page.
                header.getChildren().remove(btn_signIn);
            }
        });



        // cart functionality.
        btn_cart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                body.getChildren().clear();
                VBox Cartpge = productList.getCartItems(itemsIncart);
                Cartpge.setSpacing(10);
                Cartpge.setAlignment(Pos.CENTER);
                footer.setVisible(false);
                Cartpge.getChildren().add(btn_placeOrder);
                body.getChildren().add(Cartpge);
            }
        });

        // Place order button  functionality.
        btn_placeOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                if(itemsIncart == null){
                    showDialog("Please Add Some Product !");
                    return;
                }
                if (logedInCustomer == null){
                    showDialog("Please Login to Continue ");
                    return;
                }

                int status = Order.placeMultipleOrder(logedInCustomer,itemsIncart);
                if(status != 0){
                    showDialog("Order Placed Successfully for "+status+" items ");
                }else{
                    showDialog("Order Failed !!");
                }

            }
        });

        // Adding components to header.
        header.getChildren().addAll(btn_home,btn_search, search_text, btn_signIn,btn_cart);
    }

    private  void  createFooter(){
        footer = new HBox();
        footer.setPadding(new Insets(10));
        footer.setSpacing(10);
        Button btn_Buy = new Button("Buy Now");


        btn_Buy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                product currProd = productList.getSelectedProduct();
                if(currProd == null){
                    showDialog("Please Select Some Product !");
                    return;
                }
                if (logedInCustomer == null){
                    showDialog("Please Login to Continue ");
                    return;
                }

                boolean status = Order.placeOrder(logedInCustomer,currProd);
                if(status){
                    showDialog("Order Placed Successfully");
                }else{
                    showDialog("Order Failed !!");
                }
            }
        });

        Button btn_add_cart = new Button("Add to Cart");
        btn_add_cart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                product currProd = productList.getSelectedProduct();
                if(currProd == null){
                    showDialog("Please Select Some Product to add in cart !");
                    return;
                }

                itemsIncart.add(currProd);
                showDialog("Added To Cart !");



            }
        });

        footer.getChildren().addAll(btn_Buy,btn_add_cart);

    }

    private void showDialog(String mess){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText(null);
        alert.setContentText(mess);
        alert.setTitle("Message");
        alert.showAndWait();

    }




}
