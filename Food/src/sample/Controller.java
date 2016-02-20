package sample;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;



import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Controller implements Initializable {

    @FXML
    JFXButton btnempleado;
    @FXML
    JFXButton btncocinero;
    @FXML
    JFXButton btnauxiliar;
    @FXML
    JFXButton btningrediente;
    @FXML
    JFXButton btnplato;
    @FXML
    JFXButton btnregistro;
    @FXML
    JFXButton btnrelacion1;
    @FXML
    JFXButton btnrelacion2;





    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {



        //Connect to data base
        Database bd = new Database("jdbc:mysql://localhost:3306/food","java","password", "food");


        String url = "jdbc:mysql://localhost:3306/food";
        String username = "java";
        String password = "password";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }


        btnempleado.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("MenuEmpleados.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Empleados - Menu");
                    stage.setScene(new Scene(root, 700, 520));
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        btncocinero.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("MenuCocinero.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Cocinero - Menu");
                    stage.setScene(new Scene(root, 700, 520));
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




        btnauxiliar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("MenuAuxiliar.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Auxiliar - Menu");
                    stage.setScene(new Scene(root, 700, 520));
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });





        btnplato.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("MenuPlato.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Platos - Menu");
                    stage.setScene(new Scene(root, 700, 550));
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        btningrediente.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("MenuIngrediente.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Ingredientes - Menu");
                    stage.setScene(new Scene(root, 700, 550));
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        btnrelacion1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("CocineroConPlato.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Empleados - Menu");
                    stage.setScene(new Scene(root, 700, 570));
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        btnrelacion2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("PlatoConIngredientes.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Empleados - Menu");
                    stage.setScene(new Scene(root, 700, 570));
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });






    }




}
