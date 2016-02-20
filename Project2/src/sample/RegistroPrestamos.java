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


public class RegistroPrestamos implements Initializable {


    @FXML
    private TableColumn<Prestamos, String> isbn;

    @FXML
    private TableColumn<Prestamos, String> fechadevolucion;

    @FXML
    private TableColumn<Prestamos, String> fechatope;

    @FXML
    private TableColumn<Prestamos, Integer> idSocios;

    @FXML
    private TableColumn<Prestamos, Integer> idPrestamos;


    @FXML
    private TableView<Prestamos> tableView;



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

        isbn.setCellValueFactory(new PropertyValueFactory<Prestamos, String>("Isbn"));
        fechatope.setCellValueFactory(new PropertyValueFactory<Prestamos, String>("FechaTope"));
        fechadevolucion.setCellValueFactory(new PropertyValueFactory<Prestamos, String>("FechaDevolucion"));
        idSocios.setCellValueFactory(new PropertyValueFactory<Prestamos, Integer>("Idsocio"));
        idPrestamos.setCellValueFactory(new PropertyValueFactory<Prestamos, Integer>("IdPrestamos"));


        //tableView.getItems().setAll();


        Service<ObservableList<Prestamos>> service = new Service<ObservableList<Prestamos>>() {
            @Override
            protected Task<ObservableList<Prestamos>> createTask() {
                return new Task<ObservableList<Prestamos>>() {
                    @Override
                    protected ObservableList<Prestamos> call() throws Exception {
                        return FXCollections.observableArrayList(getAllPrestamos());
                    }
                };
            }
        };

        tableView.itemsProperty().bind(service.valueProperty());
        service.start();



    }


    //       ''''''''''''''''''  Clase de prestamos '''''''''''''''''''''''''''''''''''
    public static class Prestamos{

        private final SimpleStringProperty isbn;
        private final SimpleStringProperty fechatope;
        private final SimpleStringProperty fechadevoluacion;
        private final SimpleIntegerProperty idSocios;
        private final SimpleIntegerProperty idPrestamos;



        private Prestamos(String isb, String fechatop, String fechadevo, Integer idsocio, Integer idprestamo) {
            this.isbn =  new SimpleStringProperty(isb);
            this.fechatope = new SimpleStringProperty(fechatop);
            this.fechadevoluacion = new SimpleStringProperty(fechadevo);
            this.idSocios =  new SimpleIntegerProperty(idsocio);
            this.idPrestamos =  new SimpleIntegerProperty(idprestamo);

        }

        public String getIsbn() {
            return isbn.get();
        }

        public void setIsbn(String isb) {
            isbn.set(isb);
        }

        public String getFechaTope() {
            return fechatope.get();
        }

        public void setFechaTope(String fechatop) {
            fechatope.set(fechatop);
        }

        public String getFechaDevolucion() {
            return fechadevoluacion.get();
        }

        public void setFechaDevoluacion(String fechadevo) {
            fechadevoluacion.set(fechadevo);
        }



        public Integer getIdsocio(){
            return idSocios.get();
        }

        public void setIdsocio(Integer idsocio){
            idSocios.set(idsocio);
        }


        public Integer getIdPrestamos() {
            return idPrestamos.get();
        }

        public void setIdPrestamos(Integer idprestamo) {
            idPrestamos.set(idprestamo);
        }




    }


    public List<Prestamos> getAllPrestamos(){
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
            String recordQuery = ("Select * from prestamos");
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                Integer idprestamo = rs.getInt("idPrestamos");
                String fechatope = rs.getString("FechaTope");
                String fechadevo = rs.getString("FechaDevolucion");
                String isbn = rs.getString("Libros_ISBN");
                Integer idsocio = rs.getInt("Socios_idSocios");


                ll.add(new Prestamos(isbn, fechatope, fechadevo, idsocio, idprestamo));
                System.out.println(isbn +","+ fechatope +","+ fechadevo +","+ idsocio +","+ idprestamo +" ");
            }

        }
        catch(ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(RegistroPrestamos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ll;
    }



}
