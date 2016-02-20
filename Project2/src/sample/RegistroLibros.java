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


public class RegistroLibros implements Initializable {


    @FXML
    private TableColumn<Libros, String> isbn;

    @FXML
    private TableColumn<Libros, String> titulo;

    @FXML
    private TableColumn<Libros, String> editorial;

    @FXML
    private TableColumn<Libros, String> autor;

    @FXML
    private TableColumn<Libros, Integer> deterioro;

    @FXML
    private TableColumn<Libros, Integer> añoedicion;

    @FXML
    private TableColumn<Libros, Integer> año;

    @FXML
    private TableView<Libros> tableView;



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

        isbn.setCellValueFactory(new PropertyValueFactory<Libros, String>("Isbn"));
        titulo.setCellValueFactory(new PropertyValueFactory<Libros, String>("Titulo"));
        editorial.setCellValueFactory(new PropertyValueFactory<Libros, String>("Editorial"));
        autor.setCellValueFactory(new PropertyValueFactory<Libros, String>("autor"));
        deterioro.setCellValueFactory(new PropertyValueFactory<Libros, Integer>("Deterioro"));
        añoedicion.setCellValueFactory(new PropertyValueFactory<Libros, Integer>("AñoEdicion"));
        año.setCellValueFactory(new PropertyValueFactory<Libros, Integer>("Año"));

        //tableView.getItems().setAll();


        Service<ObservableList<Libros>> service = new Service<ObservableList<Libros>>() {
            @Override
            protected Task<ObservableList<Libros>> createTask() {
                return new Task<ObservableList<Libros>>() {
                    @Override
                    protected ObservableList<Libros> call() throws Exception {
                        return FXCollections.observableArrayList(getAllLibros());
                    }
                };
            }
        };

        tableView.itemsProperty().bind(service.valueProperty());
        service.start();



    }


    //                                          Clase de socio.
    public static class Libros{

        private final SimpleStringProperty isbn;
        private final SimpleStringProperty titulo;
        private final SimpleStringProperty editorial;
        private final SimpleStringProperty autor;
        private final SimpleIntegerProperty deterioro;
        private final SimpleIntegerProperty anoedicion;
        private final SimpleIntegerProperty ano;



        private Libros(String isb, String tit, String edit, String aut, Integer det, Integer anoedic, Integer ano1) {
            this.isbn =  new SimpleStringProperty(isb);
            this.titulo = new SimpleStringProperty(tit);
            this.editorial = new SimpleStringProperty(edit);
            this.autor =  new SimpleStringProperty(aut);
            this.deterioro =  new SimpleIntegerProperty(det);
            this.anoedicion =  new SimpleIntegerProperty(anoedic);
            this.ano =  new SimpleIntegerProperty(ano1);

        }

        public String getIsbn() {
            return isbn.get();
        }

        public void setIsbn(String isb) {
            isbn.set(isb);
        }

        public String getTitulo() {
            return titulo.get();
        }

        public void setTitulo(String tit) {
            titulo.set(tit);
        }

        public String getEditorial() {
            return editorial.get();
        }

        public void setEditorial(String edit) {
            editorial.set(edit);
        }

        public String getAutor(){
            return autor.get();
        }

        public void setAutor(String aut){
            autor.set(aut);
        }


        public Integer getDeterioro(){
            return deterioro.get();
        }

        public void setDeterioro(Integer det){
            deterioro.set(det);
        }


        public Integer getAñoEdicion() {
            return anoedicion.get();
        }

        public void setAñoEdicion(Integer anoedic) {
            anoedicion.set(anoedic);
        }


        public Integer getAño() {
            return ano.get();
        }

        public void setAño(Integer ano1) {
            ano.set(ano1);
        }





    }


    public List<Libros> getAllLibros(){
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
            String recordQuery = ("Select * from libros");
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                String isbn = rs.getString("ISBN");
                String titulo = rs.getString("Titulo");
                String editorial = rs.getString("Editorial");
                String autor = rs.getString("Autor");
                Integer deterioro = rs.getInt("Deterioro");
                Integer añoedicion = rs.getInt("AñoEdicion");
                Integer año = rs.getInt("Año");

                ll.add(new Libros(isbn, titulo, editorial, autor, deterioro, añoedicion, año));
                System.out.println(isbn +","+ titulo +","+ editorial +","+ autor +","+ deterioro +" "+añoedicion+" "+año+"");
            }

        }
        catch(ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(RegistroLibros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ll;
    }



}
