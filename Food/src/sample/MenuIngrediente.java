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


public class MenuIngrediente implements Initializable {

    @FXML
    JFXButton btnañadir;
    @FXML
    JFXButton btneliminar;
    @FXML
    JFXButton btnmodificar;
    @FXML
    JFXTextField nombreIng;
    @FXML
    JFXTextField cantidadIng;
    @FXML
    RadioButton dp1;
    @FXML
    RadioButton ds1;
    @FXML
    RadioButton cf1;
    @FXML
    RadioButton dp2;
    @FXML
    RadioButton ds2;
    @FXML
    RadioButton cf2;
    @FXML
    JFXTextField nombreIng2;
    @FXML
    JFXTextField cantidadIng2;
    @FXML
    JFXButton btnbuscar;


    @FXML
    private TableColumn<Ingrediente, String> nombreIngrediente;

    @FXML
    private TableColumn<Ingrediente, Integer> cantidadIngrediente;

    @FXML
    private TableColumn<Ingrediente, Integer> dpIngrediente;

    @FXML
    private TableColumn<Ingrediente, Integer> dsIngrediente;

    @FXML
    private TableColumn<Ingrediente, Integer> cfIngrediente;

    @FXML
    private TableView<Ingrediente> Ingrediente;


    Integer num = 0;
    String table = "ingrediente";
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
        cantidadIng2.setDisable(true);
        dp2.setDisable(true);
        ds2.setDisable(true);
        cf2.setDisable(true);
        cargarTabla();




        btnañadir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;

                String nombre = (nombreIng.getText());
                String cantidad = (cantidadIng.getText());
                Integer cant = Integer.parseInt(cantidad);

                //Para los radios.
                Integer radiodp = 0;
                Integer radiods = 0;
                Integer radiocf = 0;

                if(dp1.isSelected()){
                    System.out.println("aaa");
                    radiodp = 1;
                }else if(ds1.isSelected()){
                    radiods = 1;
                }else if(cf1.isSelected()){
                    radiocf = 1;
                }


                Integer val1 = 0; Integer val2 = 0;
                Integer value = 0;



                //Para buscar si existe en plato
                value = bd.buscarIdString(table,column,nombre);
                System.out.println(value);
                if(value==0){

                    System.out.println("vacio se puede seguir");

                    num = 0;
                    Integer vnum = 0;

                    //Agregando datos, falta la clave.
                    bd.agregarDatosIngrediente(nombre,cant,radiodp, radiods, radiocf);
                    nombreIng.setText("");
                    cantidadIng.setText("");
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


                if(nombreIng2.getText().length()==0){
                    /*alert.showAndWait();*/
                    System.out.println("Vacio");
                }else{

                    String nombre = (nombreIng2.getText());

                    val = bd.consultarString(nombre, table, column);



                    if(val!=1){

                        System.out.println("No existe");

                    }else{

                        btnmodificar.setDisable(false);
                        btneliminar.setDisable(false);
                        btnbuscar.setDisable(true);
                        nombreIng2.setDisable(true);
                        cantidadIng2.setDisable(false);
                        dp2.setDisable(false);
                        ds2.setDisable(false);
                        cf2.setDisable(false);


                    }


                }

            }
        });




        btnmodificar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String nombre = (nombreIng2.getText());
                String cantidad = (cantidadIng2.getText());
                Integer cant = Integer.parseInt(cantidad);
                //Para los radios.
                Integer radiodp = 0;
                Integer radiods = 0;
                Integer radiocf = 0;

                if(dp2.isSelected()){
                    System.out.println("aaa");
                    radiodp = 1;
                }else if(ds2.isSelected()){
                    radiods = 1;
                }else if(cf2.isSelected()){
                    radiocf = 1;
                }


                bd.actDatosIngrediente(nombre,cant,radiodp, radiods, radiocf);
                btnmodificar.setDisable(true);
                btneliminar.setDisable(true);
                btnbuscar.setDisable(false);
                nombreIng2.setDisable(false);
                cantidadIng2.setDisable(true);
                dp2.setDisable(true);
                ds2.setDisable(true);
                cf2.setDisable(true);
                cantidadIng2.setText("");
                nombreIng2.setText("");
                cargarTabla();


            }
        });












        btneliminar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String nombre = nombreIng2.getText();
                bd.eliminarString(nombre,table,column);
                btnmodificar.setDisable(true);
                btneliminar.setDisable(true);
                btnbuscar.setDisable(false);
                nombreIng2.setDisable(false);
                dp2.setDisable(true);
                ds2.setDisable(true);
                cf2.setDisable(true);
                cantidadIng2.setText("");
                nombreIng2.setText("");
                cargarTabla();


            }
        });





    }



    //                                          Clase de Cocinero.
    public static class Ingrediente{

        private final SimpleStringProperty nombreIngre;
        private final SimpleIntegerProperty cantidadIngre;
        private final SimpleIntegerProperty dpIngre;
        private final SimpleIntegerProperty dsIngre;
        private final SimpleIntegerProperty cfIngre;


        private Ingrediente(String nombre, Integer cantidad, Integer dp, Integer ds, Integer cf) {
            this.nombreIngre =  new SimpleStringProperty(nombre);
            this.cantidadIngre = new SimpleIntegerProperty(cantidad);
            this.dpIngre =  new SimpleIntegerProperty(dp);
            this.dsIngre = new SimpleIntegerProperty(ds);
            this.cfIngre = new SimpleIntegerProperty(cf);



        }

        public String getNombreIngre() {
            return nombreIngre.get();
        }

        public void setNombreIngre(String nombre) {
            nombreIngre.set(nombre);
        }

        public Integer getCantidad() {
            return cantidadIngre.get();
        }

        public void setTipo(Integer cantidad) {
            cantidadIngre.set(cantidad);
        }

        public Integer getDP(){
            return dpIngre.get();
        }

        public void setDP(Integer dp){
            dpIngre.set(dp);
        }


        public Integer getDS(){
            return dsIngre.get();
        }

        public void setDS(Integer ds){
            dsIngre.set(ds);
        }


        public Integer getCF(){
            return cfIngre.get();
        }

        public void setCF(Integer cf){
            cfIngre.set(cf);
        }


    }





    public List<Ingrediente> getAllPlato(){
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
            String recordQuery = ("Select * from ingrediente");
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                String nombre = rs.getString("Nombre");
                Integer cant = rs.getInt("Cantidad");
                Integer dp = rs.getInt("DP");
                Integer ds = rs.getInt("DS");
                Integer cf = rs.getInt("CF");



                ll.add(new Ingrediente(nombre, cant, dp, ds, cf));
            }

        }
        catch(ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(MenuEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ll;
    }




    public  void cargarTabla(){

        nombreIngrediente.setCellValueFactory(new PropertyValueFactory<Ingrediente, String>("NombreIngre"));
        cantidadIngrediente.setCellValueFactory(new PropertyValueFactory<Ingrediente, Integer>("Cantidad"));
        dpIngrediente.setCellValueFactory(new PropertyValueFactory<Ingrediente, Integer>("DP"));
        dsIngrediente.setCellValueFactory(new PropertyValueFactory<Ingrediente, Integer>("DS"));
        cfIngrediente.setCellValueFactory(new PropertyValueFactory<Ingrediente, Integer>("CF"));




        Service<ObservableList<Ingrediente>> service = new Service<ObservableList<Ingrediente>>() {
            @Override
            protected Task<ObservableList<Ingrediente>> createTask() {
                return new Task<ObservableList<Ingrediente>>() {
                    @Override
                    protected ObservableList<Ingrediente> call() throws Exception {
                        return FXCollections.observableArrayList(getAllPlato());
                    }
                };
            }
        };

        Ingrediente.itemsProperty().bind(service.valueProperty());
        service.start();

    }








}







