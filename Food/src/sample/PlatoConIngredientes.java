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


public class PlatoConIngredientes implements Initializable {

    @FXML
    JFXButton btnañadir;
    @FXML
    JFXButton btneliminar;
    @FXML
    JFXButton btnmodificar;
    @FXML
    JFXTextField ingredientePlato;
    @FXML
    JFXTextField nombrePlato;
    @FXML
    JFXTextField ingredientePlatoBusq;
    @FXML
    JFXTextField nombrePlatoBusq;
    @FXML
    JFXTextField ingredientePlato2;
    @FXML
    JFXTextField nombrePlato2;
    @FXML
    JFXButton btnbuscar;


    @FXML
    private TableColumn<PCI, String> ingredienteTable;

    @FXML
    private TableColumn<PCI, String> nombrePlatoTable;


    @FXML
    private TableView<PCI> PlatoConIngredientes;


    Integer num = 0;
    String tableThree = "plato";
    String columnThree = "Nombre";
    String columnTwo = "idCocinero";
    String tableTwo = "ingrediente";
    String columnBusqName = "Plato_Nombre";
    String columnBusqIng = "Ingrediente_Nombre";
    String table = "platoconingredientes";




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
        cargarTabla();



        btnañadir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;

                String nombre = (nombrePlato.getText());
                String ing = (ingredientePlato.getText());
                Integer val1 = 0; Integer val2 = 0;
                Integer value = 0; Integer value2 = 0;



                //Para buscar si existe en plato
                value = bd.buscarIdString(tableThree,columnThree,nombre);

                //Para buscar si existe en ing.
                value2 = bd.buscarIdString(tableTwo, columnThree, ing);

                System.out.println(value);
                if(value==1&&value2==1){

                    System.out.println("Existe en ambas tablas");

                    num = 0;
                    Integer vnum = 0;

                    //Agregando datos, falta la clave.
                    bd.agregarDatosPCI(ing,nombre);
                    nombrePlato.setText("");
                    ingredientePlato.setText("");
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
                    String ing = (ingredientePlatoBusq.getText());


                    // Consultar en la tabla de CCP
                    val = bd.consultarString(nombre, tableThree, columnThree);
                    // Consultar en la tabla de CCP
                    val2 = bd.consultarString(ing, tableTwo, columnThree);



                    if(val==1 && val2==1){


                        Integer val1 = 0; Integer val2 = 0;
                        Integer value = 0; Integer value2 = 0;

                        Integer t = 0;



                        t = bd.consultarPCI(ing,nombre,"platoconingredientes",columnBusqIng, columnBusqName);

                        System.out.println("Es "+t);

                        if(t==1){

                            btnmodificar.setDisable(false);
                            btneliminar.setDisable(false);
                            btnbuscar.setDisable(true);
                            nombrePlato2.setDisable(false);
                            ingredientePlato2.setDisable(false);
                            ingredientePlatoBusq.setDisable(true);
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
                String ing = (ingredientePlato2.getText());
                String nombreOriginal = nombrePlatoBusq.getText();
                String ingOriginal = ingredientePlatoBusq.getText();

                bd.datosPCI(ing,nombre,ingOriginal,nombreOriginal);

                btnmodificar.setDisable(true);
                btneliminar.setDisable(true);
                btnbuscar.setDisable(false);
                nombrePlatoBusq.setDisable(false);
                ingredientePlatoBusq.setDisable(false);
                nombrePlato2.setDisable(true);
                ingredientePlato2.setDisable(true);
                nombrePlatoBusq.setText("");
                ingredientePlatoBusq.setText("");
                nombrePlato2.setText("");
                ingredientePlato2.setText("");
                cargarTabla();


            }
        });




        btneliminar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String nombre = (nombrePlatoBusq.getText());
                String ing = (ingredientePlatoBusq.getText());
                bd.eliminarPCI(ing,nombre,table,columnBusqIng,columnBusqName);
                btnmodificar.setDisable(true);
                btneliminar.setDisable(true);
                btnbuscar.setDisable(false);
                nombrePlatoBusq.setDisable(false);
                ingredientePlatoBusq.setDisable(false);
                nombrePlato2.setDisable(true);
                ingredientePlato2.setDisable(true);
                nombrePlatoBusq.setText("");
                ingredientePlatoBusq.setText("");
                nombrePlato2.setText("");
                ingredientePlato.setText("");
                cargarTabla();

            }
        });




    }



    //                                          Clase de PCI.
    public static class PCI{

        private final SimpleStringProperty IngredientePlato;
        private final SimpleStringProperty NombrePlato;


        private PCI(String ing, String nombre) {
            this.IngredientePlato =  new SimpleStringProperty(ing);
            this.NombrePlato = new SimpleStringProperty(nombre);

        }


        public String getIngredientePlato() {
            return IngredientePlato.get();
        }

        public void setIngredientePlato(String ing) {
            IngredientePlato.set(ing);
        }


        public String getNombrePlato() {
            return NombrePlato.get();
        }

        public void setTipo(String nombre) {
            NombrePlato.set(nombre);
        }



    }





    public List<PCI> getAllPCI(){
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
            String recordQuery = ("Select * from platoconingredientes");
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                String nombre = rs.getString("Plato_Nombre");
                String ingrediente = rs.getString("Ingrediente_Nombre");

                ll.add(new PCI(ingrediente,nombre));
            }

        }
        catch(ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(MenuEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ll;
    }




    public  void cargarTabla(){

        nombrePlatoTable.setCellValueFactory(new PropertyValueFactory<PCI, String>("NombrePlato"));
        ingredienteTable.setCellValueFactory(new PropertyValueFactory<PCI, String>("IngredientePlato"));



        Service<ObservableList<PCI>> service = new Service<ObservableList<PCI>>() {
            @Override
            protected Task<ObservableList<PCI>> createTask() {
                return new Task<ObservableList<PCI>>() {
                    @Override
                    protected ObservableList<PCI> call() throws Exception {
                        return FXCollections.observableArrayList(getAllPCI());
                    }
                };
            }
        };

        PlatoConIngredientes.itemsProperty().bind(service.valueProperty());
        service.start();

    }







}







