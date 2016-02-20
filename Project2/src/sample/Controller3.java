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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Controller3 implements Initializable {

    @FXML
    Button btncancel;
    @FXML
    Button btn4;

    @FXML
    JFXTextField titulo;
    @FXML
    JFXTextField añoedicion;
    @FXML
    JFXTextField editorial;
    @FXML
    JFXTextField autor;
    @FXML
    JFXTextField isbn;
    @FXML
    JFXTextField año;
    @FXML
    JFXCheckBox deterioro;


    String tit, añoedi, edit, au, is, year;
    Integer det;
    Integer año1, año2;




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

        btn4.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {

                    String tit = (titulo.getText());
                    String añoedi = (añoedicion.getText());
                    String edit = (editorial.getText());
                    String au = (autor.getText());
                    String is = (isbn.getText());
                    String year = (año.getText());

                    año1 = Integer.parseInt(añoedi);
                    año2 = Integer.parseInt(year);

                   if(deterioro.isSelected()){
                        det = 1;
                    } else {
                       det = 0;
                    }



                    //Agregando datos, falta la clave.

                    bd.agregarDatosLibros(tit,año1,edit,au,is,año2,det);



                    System.out.println("Etapa 2");
                    root = FXMLLoader.load(getClass().getResource("sample.fxml"));
                    Stage stage = new Stage();
                    javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("My New Stage Title");
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
