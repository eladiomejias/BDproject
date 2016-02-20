package sample;
import java.awt.*;
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
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;


public class LibrosSearch implements Initializable {


    @FXML
    JFXButton btncancel;


    @FXML
    JFXButton search;

    @FXML
    JFXTextField isbn;

    @FXML
    JFXButton btn4;

    @FXML
    JFXTextField titulo;
    @FXML
    JFXTextField añoedicion;
    @FXML
    JFXTextField editorial;
    @FXML
    JFXTextField autor;
    @FXML
    JFXTextField isbn1;
    @FXML
    JFXTextField año;

    @FXML
    JFXCheckBox deterioro;

    String isb;



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

        Database bd = new Database("jdbc:mysql://localhost:3306/mydb","java","password", "mydb");

        titulo.setDisable(true); año.setDisable(true); añoedicion.setDisable(true);
        autor.setDisable(true); isbn1.setDisable(true); autor.setDisable(true);
        editorial.setDisable(true); deterioro.setDisable(true); btn4.setDisable(true);


        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ha ocurrido un error");
        alert.setContentText("Asegurese de que los datos en ISBN y idSocio estan o son validos.");



        search.setOnAction(new EventHandler<ActionEvent>() {


            Integer val = 0;

            public void handle(ActionEvent event) {
                Parent root;

                    if(isbn.getText().length()==0){
                        alert.showAndWait();
                    }else{

                        isb = isbn.getText();

                        val = bd.consultarLibros(isb, "libros", "ISBN");

                        if(val!=1){

                            System.out.println("no ta");
                            alert.showAndWait();

                        }else{

                            isbn.setDisable(true); search.setDisable(true);

                            titulo.setDisable(false); año.setDisable(false); añoedicion.setDisable(false);
                            autor.setDisable(false); isbn1.setDisable(false); autor.setDisable(false);
                            editorial.setDisable(false); deterioro.setDisable(false); btn4.setDisable(false);

                        }

                    }


            }
        });




        btn4.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {

                    String tit = (titulo.getText());
                    String añoedi = (añoedicion.getText());
                    String edit = (editorial.getText());
                    String au = (autor.getText());
                    String is = (isbn1.getText());
                    String year = (año.getText());

                    Integer det;
                    Integer año1 = Integer.parseInt(añoedi);
                    Integer año2 = Integer.parseInt(year);


                    if(deterioro.isSelected()){
                        det = 1;
                    } else {
                        det = 0;
                    }

                    bd.actDatosLibros(tit,año1,edit,au,is,año2,det,isb);



                    root = FXMLLoader.load(getClass().getResource("sample.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
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