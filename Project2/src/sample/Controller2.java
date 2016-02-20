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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Controller2 implements Initializable {



    @FXML
    JFXButton btn4;
    @FXML
    JFXButton btncancel;

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

    Long cedula2, telf2;
    Integer num;
    String table = "";


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
                   String nomb = (nombre.getText());
                    String apell = (apellido.getText());
                    String ced = (cedula.getText());
                    String telf = (telefono.getText());
                    String dir = (direccion.getText());

                    table = "Socios";
                    num = 0;
                    Integer vnum = 0;

                    try {
                        num = bd.countRows(table);
                    }catch (Exception e) {
                        System.out.println("Error");
                    }


                    if (num==0){
                        vnum = 1;
                    }else{
                       vnum = bd.buscarUltimoId("socios","idSocios");
                    }


                    cedula2 = Long.parseLong(ced);
                    telf2 = Long.parseLong(telf);

                    System.out.println(nomb+" "+apell+" "+ced+" "+telf+""+dir);
                    //System.out.println(num);

                    //Agregando datos, falta la clave.

                    bd.agregarDatosSocios(vnum,nomb,apell,cedula2,telf2,dir);


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
