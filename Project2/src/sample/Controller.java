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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Controller implements Initializable {

    @FXML
    Button btnregistro;

    @FXML
    JFXButton btn2;

    @FXML
    JFXButton btnlibros;

    @FXML
    JFXButton btnprestamos;

    @FXML
    JFXButton btn4;




    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {



        //Connect to data base
        Database bd = new Database("jdbc:mysql://localhost:3306/mydb","java","password", "mydb");


        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "java";
        String password = "password";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        btn2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("Biblioteca - Añadir socio");
                    stage.setScene(new Scene(root, 410, 350));
                    stage.show();

                   // bd.agregarDatosSocio();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        btnlibros.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("sample3.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("Biblioteca - Añadir Libros");
                    stage.setScene(new Scene(root, 410, 330));
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        btnregistro.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("sampleregistro.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("Biblioteca - Registro de Datos");
                    stage.setScene(new Scene(root, 410, 330));
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        btnprestamos.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {

                    root = FXMLLoader.load(getClass().getResource("Presta.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("Biblioteca - Añadir Prestamo");
                    stage.setScene(new Scene(root, 410, 330));
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        btn4.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("Modificar.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("Biblioteca - Modificar");
                    stage.setScene(new Scene(root, 410, 300));
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




    }


    // Clase de socio.
    public static class Socio{

        private final SimpleIntegerProperty idsocio;
        private final SimpleStringProperty nombre;
        private final SimpleStringProperty apellido;
        private final SimpleLongProperty cedula;
        private final SimpleLongProperty telefono;
        private final SimpleStringProperty direccion;


        private Socio(Integer id, String fName, String lName, Long ced, Long telf, String dir) {
            this.idsocio =  new SimpleIntegerProperty(id);
            this.nombre = new SimpleStringProperty(fName);
            this.apellido = new SimpleStringProperty(lName);
            this.cedula =  new SimpleLongProperty(ced);
            this.telefono =  new SimpleLongProperty(telf);
            this.direccion = new SimpleStringProperty(dir);

        }

        public String getFirstName() {
            return nombre.get();
        }

        public void setFirstName(String fName) {
            nombre.set(fName);
        }

        public String getLastName() {
            return apellido.get();
        }

        public void setLastName(String fName) {
            apellido.set(fName);
        }

        public Integer getId() {
            return idsocio.get();
        }

        public void setId(Integer id) {
            idsocio.set(id);
        }

        public Long getCed(){
            return cedula.get();
        }

        public void setCed(Long ced){
            cedula.set(ced);
        }


        public Long getTelf(){
            return telefono.get();
        }

        public void setTelf(Long telf){
            telefono.set(telf);
        }


        public String getDireccion() {
            return direccion.get();
        }

        public void setDireccion(String dir) {
            direccion.set(dir);
        }




    }







}
