package sample;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class addPrestamo implements Initializable {



    @FXML
    JFXTextField idsocio;

    @FXML
    JFXTextField isbn;

    @FXML
    JFXButton btnpresta;

    @FXML
    JFXTextField fechadevo;

    @FXML
    JFXTextField fechatope;




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

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ha ocurrido un error");
        alert.setContentText("Asegurese de que los datos en ISBN y idSocio estan o son validos.");

        //Setear la fecha.
        mostrarFecha();



        btnpresta.setOnAction(new EventHandler<ActionEvent>() {
            String ftope, fdevo;
            public void handle(ActionEvent event) {
                Parent root;


                    if (isbn.getText().length()==0 || idsocio.getText().length()==0){


                        //System.out.println("vacio");
                        alert.showAndWait();



                    }else{



                        try {

                            String id;
                            String isb;
                            Integer socio, value1, value2;
                            id = idsocio.getText();
                            socio = Integer.parseInt(id);
                            isb = isbn.getText();

                            value1 = bd.consultar(socio, "socios", "idSocios");
                            value2 = bd.consultarLibros(isb, "libros", "ISBN");

                            if (value1!=1  || value2!=1){
                                alert.showAndWait();
                            }else{

                                Integer cant = 0;
                                Integer idprestamo = 0;
                                String table = "prestamos";

                                try {
                                    cant = bd.countRows(table);
                                }catch (Exception e) {
                                    System.out.println("Error");
                                }

                                System.out.println(cant);

                                if (cant==0){
                                    idprestamo = 1;
                                }else{
                                    idprestamo = bd.buscarUltimoId("prestamos","idPrestamos");
                                }

                                System.out.println("Es "+idprestamo);

                               // Tomando values
                                ftope = fechatope.getText();
                                fdevo = fechadevo.getText();

                                System.out.println("Es "+ftope+" es"+fdevo);

                                bd.agregarDatosPrestamo(idprestamo,ftope,fdevo,isb,socio);

                                root = FXMLLoader.load(getClass().getResource("sample.fxml"));
                                Stage stage = new Stage();
                                stage.setTitle("My New Stage Title");
                                stage.setScene(new Scene(root, 353, 300));
                                stage.show();

                                //hide this current window (if this is whant you want
                                ((Node)(event.getSource())).getScene().getWindow().hide();


                            }




                        } catch (IOException e) {
                            e.printStackTrace();
                        }




                    }



                    }

        });



    }



    public void mostrarFecha(){
        //Calendar fecha = Calendar.getInstance();
        ArrayList<String> fechas = new ArrayList<>();
        String date, lastdate, firstdate;

        firstdate = "";
        lastdate = "";
        String listString = "";
        Calendar fecha = new GregorianCalendar();


        for (int i=0;i<5;i++){
            date = "";
            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH);
            int dia = fecha.get(Calendar.DAY_OF_MONTH)+i;
            date = " "+ dia + "/" + (mes+1) + "/" + año;
            fechas.add(date);
        }


        for(int i = 0; i < fechas.size(); i++) {
            lastdate = fechas.get(i);
        }


        for (String s : fechas)
        {
            listString += s + " ";
        }

        fechadevo.setText(listString);
        fechatope.setText(lastdate);



    }





}
