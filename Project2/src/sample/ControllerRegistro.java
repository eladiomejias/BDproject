package sample;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
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


public class ControllerRegistro implements Initializable {


    @FXML
    JFXButton btncancel;

    @FXML
    JFXButton btnsocios;

    @FXML
    JFXButton btnprestamos;

    @FXML
    JFXButton btnlibros;


    @FXML
    private void handlebutton(ActionEvent event)throws IOException{
        System.out.println("hola");
        Parent paren = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scen = new Scene(paren);
        Stage stag = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stag.hide();
        stag.setScene(scen);
        stag.show();
    }


    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        //Connect to data base
        Database bd = new Database("jdbc:mysql://localhost:3306/mydb","java","password", "mydb");



        btnsocios.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {

                    System.out.println("Etapa 2");
                    root = FXMLLoader.load(getClass().getResource("registrousuario.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("Biblioteca - Registro de Usuarios");
                    stage.setScene(new Scene(root, 700, 400));
                    stage.show();

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

                    System.out.println("Etapa 2");
                    root = FXMLLoader.load(getClass().getResource("registroprestamos.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("Biblioteca - Registro de Libros");
                    stage.setScene(new Scene(root, 840, 450));
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

                    System.out.println("Etapa 2");
                    root = FXMLLoader.load(getClass().getResource("registerprestamos.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("Biblioteca - Registro de Prestamos");
                    stage.setScene(new Scene(root, 800, 450));
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
