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


public class CocineroConPlato implements Initializable {

    @FXML
    JFXButton btnañadir;
    @FXML
    JFXButton btneliminar;
    @FXML
    JFXButton btnmodificar;
    @FXML
    JFXTextField idCocinero;
    @FXML
    JFXTextField nombrePlato;
    @FXML
    JFXTextField idCocineroBusq;
    @FXML
    JFXTextField nombrePlatoBusq;
    @FXML
    JFXTextField idCocinero2;
    @FXML
    JFXTextField nombrePlato2;
    @FXML
    JFXButton btnbuscar;


    @FXML
    private TableColumn<CCP, Integer> idCocineroTable;

    @FXML
    private TableColumn<CCP, String> nombrePlatoTable;


    @FXML
    private TableView<CCP> CocineroConPlatos;


    Integer num = 0;
    String tableThree = "plato";
    String columnThree = "Nombre";
    String columnTwo = "idCocinero";
    String tableTwo = "cocinero";
    String columnBusqName = "Plato_Nombre";
    String columnBusqId = "Cocinero_idCocinero";
    String table = "cocineroconplatos";




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
        /*tipoPlato2.setDisable(true);
        precioPlato2.setDisable(true);*/
        cargarTabla();



        btnañadir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;

                String nombre = (nombrePlato.getText());
                String id = (idCocinero.getText());
                Integer idcoc = Integer.parseInt(id);
                Integer val1 = 0; Integer val2 = 0;
                Integer value = 0; Integer value2 = 0;



                //Para buscar si existe en plato
                value = bd.buscarIdString(tableThree,columnThree,nombre);

                value2 = bd.buscarId(tableTwo, columnTwo, idcoc);

                System.out.println(value);
                if(value==1&&value2==1){

                    System.out.println("Existe en ambas tablas");

                    num = 0;
                    Integer vnum = 0;

                    //Agregando datos, falta la clave.
                    bd.agregarDatosCCP(idcoc,nombre);
                    nombrePlato.setText("");
                    idCocinero.setText("");
                    cargarTabla();

                }else{
                    System.out.println("No existe, por lo tanto no se puede seguir");

                }




            }
        });





        btnbuscar.setOnAction(new EventHandler<ActionEvent>() {

            Integer val = 0; Integer val2 = 0;
            public void handle(ActionEvent event) {
                Parent root;


                if(nombrePlatoBusq.getText().length()==0){
                    /*alert.showAndWait();*/
                    System.out.println("Vacio");
                }else{

                    String nombre = (nombrePlatoBusq.getText());
                    String id = (idCocineroBusq.getText());
                    Integer idcoc = Integer.parseInt(id);


                    // Consultar en la tabla de CCP
                    val = bd.consultarString(nombre, tableThree, columnThree);
                    // Consultar en la tabla de CCP
                    val2 = bd.consultar(idcoc, tableTwo, columnTwo);



                    if(val==1 && val2==1){


                        /*String nombre2 = (nombrePlatoBusq.getText());
                        String id2 = (idCocinero2.getText());*/
                        Integer idcoc2 = Integer.parseInt(id);
                        Integer val1 = 0; Integer val2 = 0;
                        Integer value = 0; Integer value2 = 0;

                        Integer t = 0;

                        //System.out.println("Aaaaa "+id+" "+nombre);

                        t = bd.consultarCPP(idcoc2,nombre,"cocineroconplatos",columnBusqId, columnBusqName);

                        //System.out.println("Es "+t);

                        if(t==1){

                            btnmodificar.setDisable(false);
                            btneliminar.setDisable(false);
                            btnbuscar.setDisable(true);
                            nombrePlato2.setDisable(false);
                            idCocinero2.setDisable(false);
                            idCocineroBusq.setDisable(true);
                            nombrePlatoBusq.setDisable(true);


                        }



                    }else{

                        System.out.println("No existe");


                    }


                }

            }
        });





        btnmodificar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String nombre = (nombrePlato2.getText());
                String id = (idCocinero2.getText());
                Integer idc = Integer.parseInt(id);
                String nombreOriginal = nombrePlatoBusq.getText();
                String idOri = idCocineroBusq.getText();
                Integer idOriginal = Integer.parseInt(idOri);



                bd.datosP(idc,nombre, idOriginal, nombreOriginal);

                btnmodificar.setDisable(true);
                btneliminar.setDisable(true);
                btnbuscar.setDisable(false);
                nombrePlatoBusq.setDisable(false);
                idCocineroBusq.setDisable(false);
                nombrePlato2.setDisable(true);
                idCocinero2.setDisable(true);
                nombrePlatoBusq.setText("");
                idCocineroBusq.setText("");
                nombrePlato2.setText("");
                idCocinero2.setText("");
                cargarTabla();


            }
        });




        btneliminar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String nombre = (nombrePlatoBusq.getText());
                String id = (idCocineroBusq.getText());
                Integer idc = Integer.parseInt(id);
                bd.eliminarCpp(idc,nombre, table, columnBusqId, columnBusqName);
                btnmodificar.setDisable(true);
                btneliminar.setDisable(true);
                btnbuscar.setDisable(false);
                nombrePlatoBusq.setDisable(false);
                idCocineroBusq.setDisable(false);
                nombrePlato2.setDisable(true);
                idCocinero2.setDisable(true);
                nombrePlatoBusq.setText("");
                idCocineroBusq.setText("");
                nombrePlato2.setText("");
                idCocinero2.setText("");
                cargarTabla();

            }
        });




    }



    //                                          Clase de CPP.
    public static class CCP{

        private final SimpleIntegerProperty idCocinero;
        private final SimpleStringProperty NombrePlato;


        private CCP(Integer id, String nombre) {
            this.idCocinero =  new SimpleIntegerProperty(id);
            this.NombrePlato = new SimpleStringProperty(nombre);

        }

        public Integer getIdCocinero() {
            return idCocinero.get();
        }

        public void setIdCocinero(Integer nombre) {
            idCocinero.set(nombre);
        }


        public String getNombrePlato() {
            return NombrePlato.get();
        }

        public void setTipo(String nombre) {
            NombrePlato.set(nombre);
        }



    }





    public List<CCP> getAllCCP(){
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
            String recordQuery = ("Select * from cocineroconplatos");
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                Integer id = rs.getInt("Cocinero_idCocinero");
                String nombre = rs.getString("Plato_Nombre");

                ll.add(new CCP(id,nombre));
            }

        }
        catch(ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(MenuEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ll;
    }




    public  void cargarTabla(){

        nombrePlatoTable.setCellValueFactory(new PropertyValueFactory<CCP, String>("NombrePlato"));
        idCocineroTable.setCellValueFactory(new PropertyValueFactory<CCP, Integer>("idCocinero"));



        Service<ObservableList<CCP>> service = new Service<ObservableList<CCP>>() {
            @Override
            protected Task<ObservableList<CCP>> createTask() {
                return new Task<ObservableList<CCP>>() {
                    @Override
                    protected ObservableList<CCP> call() throws Exception {
                        return FXCollections.observableArrayList(getAllCCP());
                    }
                };
            }
        };

        CocineroConPlatos.itemsProperty().bind(service.valueProperty());
        service.start();

    }







}







