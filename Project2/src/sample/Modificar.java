package sample;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class Modificar implements Initializable {


    @FXML
    JFXButton btncancel;


    @FXML
    JFXButton btnlibros;


    @FXML
    JFXButton mdsocios;


    @FXML
    JFXButton deletesocio;

    @FXML
    JFXButton deletelibro;

    @FXML
    JFXButton deleteprestamo;




    @FXML
    private void handlebutton(ActionEvent event)throws IOException{
        Parent paren = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scen = new Scene(paren);
        Stage stag = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stag.hide();
        stag.setScene(scen);
        stag.show();
    }


    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {


        btnlibros.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("LibrosSearch.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("Biblioteca - Modificar Libro");
                    stage.setScene(new Scene(root, 450, 420));
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        mdsocios.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("SociosSearch.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("Biblioteca - Modificar Socio");
                    stage.setScene(new Scene(root, 425, 420));
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        deletesocio.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("DeleteSocio.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("Biblioteca - Eliminar Socio");
                    stage.setScene(new Scene(root, 325, 225));
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        deleteprestamo.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("DeletePrestamo.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("Biblioteca - Eliminar Prestamo");
                    stage.setScene(new Scene(root, 325, 225));
                    stage.show();

                    //hide this current window (if this is whant you want
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        deletelibro.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("DeleteLibros.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("Biblioteca - Eliminar Libro");
                    stage.setScene(new Scene(root, 325, 225));
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