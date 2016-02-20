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


public class MenuEmpleados implements Initializable {

    @FXML
    JFXButton btnañadir;
    @FXML
    JFXButton btneliminar;
    @FXML
    JFXButton btnmodificar;
    @FXML
    JFXTextField nombre;
    @FXML
    JFXTextField telefono;
    @FXML
    JFXTextField nombre2;
    @FXML
    JFXTextField telefono2;
    @FXML
    JFXTextField id;
    @FXML
    JFXButton btnbuscar;


    @FXML
    private TableColumn<Empleado, Integer> idtable;

    @FXML
    private TableColumn<Empleado, String> nombretable;

    @FXML
    private TableColumn<Empleado, String> telefonotable;


    @FXML
    private TableView<Empleado> Empleados;


    Integer num = 0;
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
        Database bd = new Database("jdbc:mysql://localhost:3306/food","java","password", "food");

        btneliminar.setDisable(true);
        btnmodificar.setDisable(true);
        nombre2.setDisable(true);
        telefono2.setDisable(true);

        cargarTabla();


        btnañadir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;

                    String nomb = (nombre.getText());
                    String telf = (telefono.getText());

                    table = "Empleados";
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
                        vnum = bd.buscarUltimoId("empleados","idEmpleados");
                    }



                    System.out.println(nomb+" "+telf);

                    //Agregando datos, falta la clave.
                    bd.agregarDatosEmpleados(vnum,nomb,telf);

                    nombre.setText("");
                    telefono.setText("");
                    cargarTabla();



            }
        });






        btnbuscar.setOnAction(new EventHandler<ActionEvent>() {

            Integer val = 0;
            public void handle(ActionEvent event) {
                Parent root;


                if(id.getText().length()==0){
                    /*alert.showAndWait();*/
                    System.out.println("Vacio");
                }else{

                   Integer idempleado = Integer.parseInt(id.getText());

                    val = bd.consultar(idempleado, "empleados", "idEmpleados");



                    if(val!=1){

                        System.out.println("no ta");
                        //alert.showAndWait();

                    }else{

                        btnmodificar.setDisable(false);
                        btneliminar.setDisable(false);
                        btnbuscar.setDisable(true);
                        nombre2.setDisable(false);
                        telefono2.setDisable(false);

                    }


                }

            }
        });





        btnmodificar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                    String nomb = (nombre2.getText());
                    String telf = (telefono2.getText());
                    String idempleado = id.getText();
                    Integer id1 = Integer.parseInt(idempleado);
                    table = "Empleados";
                    bd.actDatosEmpleado(id1,nomb,telf);
                    btnmodificar.setDisable(true);
                    btneliminar.setDisable(true);
                    btnbuscar.setDisable(false);
                    nombre2.setDisable(true);
                    telefono2.setDisable(true);
                    nombre2.setText("");
                    telefono2.setText("");
                    id.setText("");
                    cargarTabla();


            }
        });










    }







    //                                          Clase de Empleados.
    public static class Empleado{

        private final SimpleIntegerProperty idempleado;
        private final SimpleStringProperty nombre;
        private final SimpleStringProperty telefono;


        private Empleado(Integer id, String nomb, String telf) {
            this.idempleado =  new SimpleIntegerProperty(id);
            this.nombre = new SimpleStringProperty(nomb);
            this.telefono =  new SimpleStringProperty(telf);

        }

        public String getNombre() {
            return nombre.get();
        }

        public void setNombre(String fName) {
            nombre.set(fName);
        }

        public Integer getId() {
            return idempleado.get();
        }

        public void setId(Integer id) {
            idempleado.set(id);
        }

        public String getTelf(){
            return telefono.get();
        }

        public void setTelf(String telf){
            telefono.set(telf);
        }




    }




    public List<Empleado> getAllEmpleados(){
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
            String recordQuery = ("Select * from empleados");
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                Integer id = rs.getInt("idEmpleados");
                String name = rs.getString("Nombre");
                String telf = rs.getString("Telefono");
                ll.add(new Empleado(id, name,telf));
                ///System.out.println(id +","+ name +","+ apellido +","+ cedula +","+ telf +" "+direccion+"");
            }

        }
        catch(ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(MenuEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ll;
    }


    public  void cargarTabla(){

        idtable.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("id"));
        nombretable.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
        telefonotable.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telf"));


        //tableView.getItems().setAll();


        Service<ObservableList<Empleado>> service = new Service<ObservableList<Empleado>>() {
            @Override
            protected Task<ObservableList<Empleado>> createTask() {
                return new Task<ObservableList<Empleado>>() {
                    @Override
                    protected ObservableList<Empleado> call() throws Exception {
                        return FXCollections.observableArrayList(getAllEmpleados());
                    }
                };
            }
        };

        Empleados.itemsProperty().bind(service.valueProperty());
        service.start();
    }







}





