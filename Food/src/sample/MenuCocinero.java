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


public class MenuCocinero implements Initializable {

    @FXML
    JFXButton btnañadir;
    @FXML
    JFXButton btneliminar;
    @FXML
    JFXButton btnmodificar;
    @FXML
    JFXTextField idCocinero;
    @FXML
    JFXTextField idEmpleado;
    @FXML
    JFXTextField años;
    @FXML
    JFXTextField años2;
    @FXML
    JFXButton btnbuscar;


    @FXML
    private TableColumn<Cocinero, Integer> idEmpleadotable;

    @FXML
    private TableColumn<Cocinero, Integer> idCocinerotable;

    @FXML
    private TableColumn<Cocinero, String> añostable;


    @FXML
    private TableView<Cocinero> Cocinero;


    Integer num = 0;
    String table = "cocinero";
    String tableTwo = "auxiliar";
    String tableFour = "empleados";
    String columnOne = "Empleados_idEmpleados";
    String column = "idEmpleados";
    String columnFive = "idCocinero";




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
        años2.setDisable(true);

        cargarTabla();


        btnañadir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;

                String idemp = (idEmpleado.getText());
                String year = (años.getText());
                Integer val1 = 0; Integer val2 = 0;
                Integer value = 0;

                Integer idepp = Integer.parseInt(idemp);

                //Para buscar si existe en empleado
                value = bd.buscarId(tableFour,column,idepp);

                if(value==1){
                    //Para buscar si un id no existe en ninguna de tablas anteriores
                    val1 = bd.buscarId(table,columnOne,idepp);
                    val2 = bd.buscarId(tableTwo,columnOne,idepp);

                    if(val1==0&&val2==0){
                        System.out.println("vacio se puede seguir");

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
                            vnum = bd.buscarUltimoId(table,columnFive);
                        }



                        //Agregando datos, falta la clave.
                        bd.agregarDatosCocinero(vnum,year,idepp);
                        años.setText("");
                        idEmpleado.setText("");
                        cargarTabla();




                    }else{
                        System.out.println("usado elimine o ingrese otro id");
                    }
                }else{
                    System.out.println("no esta");
                }



            }
        });





        btnbuscar.setOnAction(new EventHandler<ActionEvent>() {

            Integer val = 0;
            public void handle(ActionEvent event) {
                Parent root;


                if(idCocinero.getText().length()==0){
                    /*alert.showAndWait();*/
                    System.out.println("Vacio");
                }else{

                    Integer idcoc = Integer.parseInt(idCocinero.getText());

                    val = bd.consultar(idcoc, table, columnFive);



                    if(val!=1){

                        System.out.println("no ta");
                        //alert.showAndWait();

                    }else{

                        btnmodificar.setDisable(false);
                        btneliminar.setDisable(false);
                        btnbuscar.setDisable(true);
                        años2.setDisable(false);
                        idCocinero.setDisable(true);
                    }


                }

            }
        });



        btnmodificar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String year = (años2.getText());
                String idcoc = idCocinero.getText();
                Integer id1 = Integer.parseInt(idcoc);
                Integer year1 = Integer.parseInt(year);

                bd.actDatosCocinero(id1,year1);
                btnmodificar.setDisable(true);
                btneliminar.setDisable(true);
                btnbuscar.setDisable(false);
                idCocinero.setDisable(false);
                años2.setText("");
                idCocinero.setText("");
                cargarTabla();


            }
        });





        btneliminar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String idcoc = idCocinero.getText();
                Integer id = Integer.parseInt(idcoc);
                bd.eliminar(id,table,columnFive);
                btnmodificar.setDisable(true);
                btneliminar.setDisable(true);
                btnbuscar.setDisable(false);
                idCocinero.setDisable(false);
                años2.setText("");
                idCocinero.setText("");
                cargarTabla();


            }
        });





    }




    //                                          Clase de Cocinero.
    public static class Cocinero{

        private final SimpleIntegerProperty idempleado;
        private final SimpleIntegerProperty idcocinero;
        private final SimpleStringProperty años1;


        private Cocinero(Integer idcoc, String años, Integer idepp) {
            this.idempleado =  new SimpleIntegerProperty(idepp);
            this.idcocinero = new SimpleIntegerProperty(idcoc);
            this.años1 =  new SimpleStringProperty(años);

        }

        public Integer getIdCocinero() {
            return idcocinero.get();
        }

        public void setIdCocinero(Integer idcoc) {
            idcocinero.set(idcoc);
        }

        public Integer getIdEmpleado() {
            return idempleado.get();
        }

        public void setIdEmpleado(Integer idepp) {
            idempleado.set(idepp);
        }

        public String getAños(){
            return años1.get();
        }

        public void setAños(String años){
            años1.set(años);
        }




    }




    public List<Cocinero> getAllCocinero(){
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
            String recordQuery = ("Select * from cocinero");
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                Integer idcoc = rs.getInt("idCocinero");
                String years = rs.getString("AñosServicio");
                String idemp = rs.getString("Empleados_idEmpleados");
                Integer idepp = Integer.parseInt(idemp);
                ll.add(new Cocinero(idcoc, years, idepp));
            }

        }
        catch(ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(MenuEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ll;
    }


    public  void cargarTabla(){

        idCocinerotable.setCellValueFactory(new PropertyValueFactory<Cocinero, Integer>("IdCocinero"));
        añostable.setCellValueFactory(new PropertyValueFactory<Cocinero, String>("años"));
        idEmpleadotable.setCellValueFactory(new PropertyValueFactory<Cocinero, Integer>("IdEmpleado"));



        Service<ObservableList<Cocinero>> service = new Service<ObservableList<Cocinero>>() {
            @Override
            protected Task<ObservableList<Cocinero>> createTask() {
                return new Task<ObservableList<Cocinero>>() {
                    @Override
                    protected ObservableList<Cocinero> call() throws Exception {
                        return FXCollections.observableArrayList(getAllCocinero());
                    }
                };
            }
        };

        Cocinero.itemsProperty().bind(service.valueProperty());
        service.start();
    }







}





