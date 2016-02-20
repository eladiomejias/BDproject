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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class SociosSearch implements Initializable {



    @FXML
    JFXButton btn4;
    @FXML
    JFXButton btncancel;

    @FXML
    JFXButton search;

    @FXML
    JFXTextField nombre;
    @FXML
    JFXTextField apellido;
    @FXML
    JFXTextField cedula;
    @FXML
    JFXTextField telefono;
    @FXML
    JFXTextArea direccion;

    @FXML
    JFXTextField socio;


    Long cedula2, telf2;
    Integer num;
    String table = "";
    Integer idsocio = 0;



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

        //Connect to data base
        Database bd = new Database("jdbc:mysql://localhost:3306/mydb","java","password", "mydb");

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ha ocurrido un error");
        alert.setContentText("Asegurese de que los datos en idSocio estan o son validos.");


        nombre.setDisable(true); cedula.setDisable(true); direccion.setDisable(true);
        telefono.setDisable(true); apellido.setDisable(true); btn4.setDisable(true);


        search.setOnAction(new EventHandler<ActionEvent>() {

            Integer val = 0;
            public void handle(ActionEvent event) {
                Parent root;


                if(socio.getText().length()==0){
                    alert.showAndWait();
                }else{

                    idsocio = Integer.parseInt(socio.getText());

                    val = bd.consultar(idsocio, "socios", "idSocios");



                    if(val!=1){

                        System.out.println("no ta");
                        alert.showAndWait();

                    }else{

                        socio.setDisable(true);search.setDisable(true);

                        nombre.setDisable(false); cedula.setDisable(false); direccion.setDisable(false);
                        telefono.setDisable(false); apellido.setDisable(false); btn4.setDisable(false);

                    }


                }

            }
        });



        btn4.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {

                    String nomb = (nombre.getText());
                    String apell = (apellido.getText());
                    String ced = (cedula.getText());
                    String telf = (telefono.getText());
                    String dir = (direccion.getText());

                    table = "Socios";

                    cedula2 = Long.parseLong(ced);
                    telf2 = Long.parseLong(telf);

                    bd.actDatosSocios(idsocio,nomb,apell,cedula2,telf2,dir);


                    root = FXMLLoader.load(getClass().getResource("sample.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Biblioteca - Principal");
                    stage.setScene(new Scene(root, 520, 470));
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