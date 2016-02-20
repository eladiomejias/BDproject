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


public class DeletePrestamo implements Initializable {


    @FXML
    JFXButton btncancel;

    @FXML
    JFXButton btndelete;

    @FXML
    JFXTextField idprestamo;


    String id = "";
    Integer val = 0;

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
        alert.setContentText("Dato vacio o no válido.");


        btndelete.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {


                    if(idprestamo.getText().length()==0){
                        alert.showAndWait();
                    }else {


                        id = (idprestamo.getText());

                        val = bd.consultarLibros(id, "prestamos", "idPrestamos");

                        if(val!=1){

                            System.out.println("no ta");
                            alert.showAndWait();

                        }else{

                            bd.eliminarPrestamoIndv(id);

                            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
                            Stage stage = new Stage();
                            stage.setTitle("My New Stage Title");
                            javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                            stage.getIcons().add(icon);
                            stage.setScene(new Scene(root, 520, 470));
                            stage.show();
                            //hide this current window (if this is whant you want
                            ((Node)(event.getSource())).getScene().getWindow().hide();

                        }

                    }




                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });





    }




}