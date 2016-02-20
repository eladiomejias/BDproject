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
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class RegistroUsuarioCont implements Initializable {


    @FXML
    private TableColumn<Socio, Integer> idSocios;

    @FXML
    private TableColumn<Socio, String> nombre;

    @FXML
    private TableColumn<Socio, String> telefono;

    @FXML
    private TableColumn<Socio, String> cedula;

    @FXML
    private TableColumn<Socio, String> apellido;

    @FXML
    private TableColumn<Socio, String> direccion;

    @FXML
    private TableView<Socio> tableView;



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

        // Este metodo busca los get and setter.

        idSocios.setCellValueFactory(new PropertyValueFactory<Socio, Integer>("id"));
        nombre.setCellValueFactory(new PropertyValueFactory<Socio, String>("nombre"));
        apellido.setCellValueFactory(new PropertyValueFactory<Socio, String>("apellido"));
        cedula.setCellValueFactory(new PropertyValueFactory<Socio, String>("ced"));
        telefono.setCellValueFactory(new PropertyValueFactory<Socio, String>("telf"));
        direccion.setCellValueFactory(new PropertyValueFactory<Socio, String>("direccion"));

        //tableView.getItems().setAll();


        Service<ObservableList<Socio>> service = new Service<ObservableList<Socio>>() {
            @Override
            protected Task<ObservableList<Socio>> createTask() {
                return new Task<ObservableList<Socio>>() {
                    @Override
                    protected ObservableList<Socio> call() throws Exception {
                        return FXCollections.observableArrayList(getAllSocios());
                    }
                };
            }
        };

        tableView.itemsProperty().bind(service.valueProperty());
        service.start();



    }


    //                                          Clase de socio.
    public static class Socio{

        private final SimpleIntegerProperty idsocio;
        private final SimpleStringProperty nombre;
        private final SimpleStringProperty apellido;
        private final SimpleStringProperty cedula;
        private final SimpleStringProperty telefono;
        private final SimpleStringProperty direccion;


        private Socio(Integer id, String nomb, String apell, String ced, String telf, String dir) {
            this.idsocio =  new SimpleIntegerProperty(id);
            this.nombre = new SimpleStringProperty(nomb);
            this.apellido = new SimpleStringProperty(apell);
            this.cedula =  new SimpleStringProperty(ced);
            this.telefono =  new SimpleStringProperty(telf);
            this.direccion = new SimpleStringProperty(dir);

        }

        public String getNombre() {
            return nombre.get();
        }

        public void setNombre(String fName) {
            nombre.set(fName);
        }

        public String getApellido() {
            return apellido.get();
        }

        public void setApellido(String fName) {
            apellido.set(fName);
        }

        public Integer getId() {
            return idsocio.get();
        }

        public void setId(Integer id) {
            idsocio.set(id);
        }

        public String getCed(){
            return cedula.get();
        }

        public void setCed(String ced){
            cedula.set(ced);
        }


        public String getTelf(){
            return telefono.get();
        }

        public void setTelf(String telf){
            telefono.set(telf);
        }


        public String getDireccion() {
            return direccion.get();
        }

        public void setDireccion(String dir) {
            direccion.set(dir);
        }




    }

/*
    public List<Socio> getAllSocios(){
        List ll = new LinkedList();
        ll.add(new Socio("1", "Eladio", "Mejias", "2432726111", "04244681148","Residencias Aseprovica"));
        return ll;
    }
*/


    public List<Socio> getAllSocios(){
        List ll = new LinkedList();
        Statement st;
        ResultSet rs;
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String pass = "root";
        String driver = "com.mysql.jdbc.Driver";

        try(Connection connection = DriverManager.getConnection(url, user, pass)) {
            Class.forName(driver);
            st = connection.createStatement();
            String recordQuery = ("Select * from socios");
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                Integer id = rs.getInt("idSocios");
                String name = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                String cedula = rs.getString("Cedula");
                String telf = rs.getString("Telefono");
                String direccion = rs.getString("Direccion");
                ll.add(new Socio(id, name, apellido, cedula, telf, direccion));
                System.out.println(id +","+ name +","+ apellido +","+ cedula +","+ telf +" "+direccion+"");
            }

        }
        catch(ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(RegistroUsuarioCont.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ll;
    }



}
