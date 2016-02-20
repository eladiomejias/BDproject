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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.*;



import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class MenuPlato implements Initializable {

    @FXML
    JFXButton btnañadir;
    @FXML
    JFXButton btneliminar;
    @FXML
    JFXButton btnmodificar;
    @FXML
    JFXTextField nombrePlato;
    @FXML
    JFXTextField precioPlato;
    @FXML
    JFXTextField tipoPlato;
    @FXML
    JFXTextField nombrePlato2;
    @FXML
    JFXTextField precioPlato2;
    @FXML
    JFXTextField tipoPlato2;
    @FXML
    JFXButton btnbuscar;


    @FXML
    private TableColumn<Plato, String> nombreplatotable;

    @FXML
    private TableColumn<Plato, String> tipoplatotable;

    @FXML
    private TableColumn<Plato, String> precioplatotable;


    @FXML
    private TableView<Plato> Platos;


    Integer num = 0;
    String table = "plato";
    String tableTwo = "auxiliar";
    String tableFour = "plato";
    String column = "Nombre";




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
        Database bd = new Database("jdbc:mysql://localhost:3306/food","java","password", "food");

        btneliminar.setDisable(true);
        btnmodificar.setDisable(true);
        tipoPlato2.setDisable(true);
        precioPlato2.setDisable(true);


        cargarTabla();


        btnañadir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;

                String nombre = (nombrePlato.getText());
                String tipo = (tipoPlato.getText());
                String precio = (precioPlato.getText());
                Integer val1 = 0; Integer val2 = 0;
                Integer value = 0;

                System.out.println(""+nombre+""+tipo+""+precio);


                //Para buscar si existe en plato
                value = bd.buscarIdString(table,column,nombre);
                System.out.println(value);
                if(value==0){

                        System.out.println("vacio se puede seguir");

                        num = 0;
                        Integer vnum = 0;

                        //Agregando datos, falta la clave.
                        bd.agregarDatosPlato(nombre,tipo,precio);
                        tipoPlato.setText("");
                        nombrePlato.setText("");
                        precioPlato.setText("");
                        cargarTabla();

                    }else{
                        System.out.println("usado elimine o ingrese otro id");

                    }




            }
        });





        btnbuscar.setOnAction(new EventHandler<ActionEvent>() {

            Integer val = 0;
            public void handle(ActionEvent event) {
                Parent root;


                if(nombrePlato2.getText().length()==0){
                    /*alert.showAndWait();*/
                    System.out.println("Vacio");
               }else{

                    String nombre = (nombrePlato2.getText());

                    val = bd.consultarString(nombre, table, column);



                    if(val!=1){

                        System.out.println("No existe");

                    }else{

                        btnmodificar.setDisable(false);
                        btneliminar.setDisable(false);
                        btnbuscar.setDisable(true);
                        nombrePlato2.setDisable(true);
                        tipoPlato2.setDisable(false);
                        precioPlato2.setDisable(false);


                    }


                }

            }
        });



        btnmodificar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String nombre = (nombrePlato2.getText());
                String precio = (precioPlato2.getText());
                String tipo = tipoPlato2.getText();

                bd.actDatosPlato(tipo,precio,nombre);
                btnmodificar.setDisable(true);
                btneliminar.setDisable(true);
                btnbuscar.setDisable(false);
                tipoPlato2.setDisable(true);
                nombrePlato2.setDisable(false);
                precioPlato2.setDisable(true);
                tipoPlato2.setText("");
                nombrePlato2.setText("");
                precioPlato2.setText("");
                cargarTabla();


            }
        });





        btneliminar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String nombre = nombrePlato2.getText();
                bd.eliminarString(nombre,table,column);
                btnmodificar.setDisable(true);
                btneliminar.setDisable(true);
                btnbuscar.setDisable(false);
                nombrePlato2.setDisable(false);
                precioPlato2.setDisable(false);
                tipoPlato2.setDisable(false);
                precioPlato2.setText("");
                nombrePlato2.setText("");
                cargarTabla();


            }
        });





    }




    //                                          Clase de Cocinero.
    public static class Plato{

        private final SimpleStringProperty nombreplato;
        private final SimpleStringProperty precioplato;
        private final SimpleStringProperty tipoplato;


        private Plato(String nombre, String tipo, String precio) {
            this.nombreplato =  new SimpleStringProperty(nombre);
            this.precioplato = new SimpleStringProperty(precio);
            this.tipoplato =  new SimpleStringProperty(tipo);

        }

        public String getNombrePlato() {
            return nombreplato.get();
        }

        public void setNombrePlato(String nombre) {
            nombreplato.set(nombre);
        }

        public String getTipo() {
            return tipoplato.get();
        }

        public void setTipo(String tipo) {
            tipoplato.set(tipo);
        }

        public String getPrecio(){
            return precioplato.get();
        }

        public void setPrecio(String precio){
            precioplato.set(precio);
        }




    }




    public List<Plato> getAllPlato(){
        List ll = new LinkedList();
        Statement st;
        ResultSet rs;
        String url = "jdbc:mysql://localhost:3306/food";
        String user = "root";
        String pass = "root";
        String driver = "com.mysql.jdbc.Driver";

        try(Connection connection = DriverManager.getConnection(url, user, pass)) {
            Class.forName(driver);
            st = connection.createStatement();
            String recordQuery = ("Select * from plato");
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                String nombre = rs.getString("Nombre");
                String tipo = rs.getString("Tipo");
                String precio = rs.getString("Precio");

                ll.add(new Plato(nombre, tipo, precio));
            }

        }
        catch(ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(MenuEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ll;
    }


    public  void cargarTabla(){

        nombreplatotable.setCellValueFactory(new PropertyValueFactory<Plato, String>("NombrePlato"));
        tipoplatotable.setCellValueFactory(new PropertyValueFactory<Plato, String>("Tipo"));
        precioplatotable.setCellValueFactory(new PropertyValueFactory<Plato, String>("Precio"));



        Service<ObservableList<Plato>> service = new Service<ObservableList<Plato>>() {
            @Override
            protected Task<ObservableList<Plato>> createTask() {
                return new Task<ObservableList<Plato>>() {
                    @Override
                    protected ObservableList<Plato> call() throws Exception {
                        return FXCollections.observableArrayList(getAllPlato());
                    }
                };
            }
        };

        Platos.itemsProperty().bind(service.valueProperty());
        service.start();

    }




}







