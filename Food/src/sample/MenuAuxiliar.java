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


public class MenuAuxiliar implements Initializable {

    @FXML
    JFXButton btnañadir;
    @FXML
    JFXButton btneliminar;
    @FXML
    JFXButton btnmodificar;
    @FXML
    JFXTextField idAuxiliar;
    @FXML
    JFXTextField idEmpleado;
    @FXML
    JFXTextField fecha;
    @FXML
    JFXTextField fecha2;
    @FXML
    JFXButton btnbuscar;


    @FXML
    private TableColumn<Aux, Integer> idEmpleadotable1;

    @FXML
    private TableColumn<Aux, Integer> idAuxtable;

    @FXML
    private TableColumn<Aux, String> fechatable;


    @FXML
    private TableView<Aux> Auxiliar;


    Integer num = 0;
    String table = "auxiliar";
    String tableTwo = "cocinero";
    String tableFour = "empleados";
    String columnOne = "Empleados_idEmpleados";
    String column = "idEmpleados";
    String columnFive = "idAuxiliar";




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
        fecha2.setDisable(true);

        cargarTabla();


        btnañadir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;

                String idemp = (idEmpleado.getText());
                String date = (fecha.getText());
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
                        bd.agregarDatosAuxiliar(vnum,date,idepp);
                        fecha.setText("");
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


                if(idAuxiliar.getText().length()==0){
                    /*alert.showAndWait();*/
                   System.out.println("Vacio");
                }else{

                    Integer idaux = Integer.parseInt(idAuxiliar.getText());

                    val = bd.consultar(idaux, table, columnFive);



                    if(val!=1){

                        System.out.println("no ta");
                        //alert.showAndWait();

                    }else{

                        btnmodificar.setDisable(false);
                        btneliminar.setDisable(false);
                        btnbuscar.setDisable(true);
                        fecha2.setDisable(false);
                        idAuxiliar.setDisable(true);
                    }


                }

            }
        });



        btnmodificar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String date = (fecha2.getText());
                String aux = idAuxiliar.getText();
                Integer aux1 = Integer.parseInt(aux);
                bd.actDatosAuxiliar(aux1,date);
                btnmodificar.setDisable(true);
                btneliminar.setDisable(true);
                btnbuscar.setDisable(false);
                idAuxiliar.setDisable(false);
                fecha2.setText("");
                idAuxiliar.setText("");
                cargarTabla();


            }
        });





        btneliminar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String idaux = idAuxiliar.getText();
                Integer id = Integer.parseInt(idaux);
                bd.eliminar(id,table,columnFive);
                btnmodificar.setDisable(true);
                btneliminar.setDisable(true);
                btnbuscar.setDisable(false);
                idAuxiliar.setDisable(false);
                fecha2.setText("");
                idAuxiliar.setText("");
                cargarTabla();


            }
        });





    }




    //                                          Clase de Auxiliar.
    public static class Aux{

        private final SimpleIntegerProperty idempleado;
        private final SimpleIntegerProperty idauxi;
        private final SimpleStringProperty date;


        private Aux(Integer idaux, String fecha, Integer idepp) {
            this.idempleado =  new SimpleIntegerProperty(idepp);
            this.idauxi = new SimpleIntegerProperty(idaux);
            this.date =  new SimpleStringProperty(fecha);

        }

        public Integer getIdAuxiliar() {
            return idauxi.get();
        }

        public void setIdAuxiliar(Integer idaux) {
            idauxi.set(idaux);
        }

        public Integer getIdEmpleado() {
            return idempleado.get();
        }

        public void setIdEmpleado(Integer idepp) {
            idempleado.set(idepp);
        }

        public String getFecha(){
            return date.get();
        }

        public void setFecha(String fecha){
            date.set(fecha);
        }




    }




    public List<Aux> getAllAuxiliar(){
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
            String recordQuery = ("Select * from auxiliar");
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                Integer idaux = rs.getInt("idAuxiliar");
                String fecha = rs.getString("FechaNacimiento");
                String idemp = rs.getString("Empleados_idEmpleados");
                Integer idepp = Integer.parseInt(idemp);
                ll.add(new Aux(idaux, fecha, idepp));
            }

        }
        catch(ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(MenuEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ll;
    }


    public  void cargarTabla(){

        idAuxtable.setCellValueFactory(new PropertyValueFactory<Aux, Integer>("IdAuxiliar"));
        fechatable.setCellValueFactory(new PropertyValueFactory<Aux, String>("fecha"));
        idEmpleadotable1.setCellValueFactory(new PropertyValueFactory<Aux, Integer>("IdEmpleado"));



        Service<ObservableList<Aux>> service = new Service<ObservableList<Aux>>() {
            @Override
            protected Task<ObservableList<Aux>> createTask() {
                return new Task<ObservableList<Aux>>() {
                    @Override
                    protected ObservableList<Aux> call() throws Exception {
                        return FXCollections.observableArrayList(getAllAuxiliar());
                    }
                };
            }
        };

        Auxiliar.itemsProperty().bind(service.valueProperty());
        service.start();
    }







}






