package sample;

// Clases para la DB
import java.sql.*;
import java.util.Calendar;

public class Database {

    private String myUrl;
    private String username;
    private String password;
    private String databaseName;


    public Database(String _url, String _username, String _password, String _databaseName){

        myUrl =  _url;
        username =  _username;
        password = _password;
        databaseName = _databaseName;

    }


    public void agregarDatosSocios(Integer code, String nombre, String apellido, Long cedula, Long telf, String direccion ){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            Statement st = connection.createStatement();

            st.executeUpdate("INSERT INTO Socios " +
                    "VALUES ("+code+", "+"'"+nombre+"'"+", "+"'"+apellido+"'"+", "+cedula+", "+telf+","+"'"+direccion+"'"+")");

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


    }

    public static int countRows(String tableName) throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","java","password");
        // select the number of rows in the table
        Statement stmt = null;
        ResultSet rs = null;
        int rowCount = -1;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
            // get the number of rows from the result set
            rs.next();
            rowCount = rs.getInt(1);
        } finally {
            rs.close();
            stmt.close();
        }
        return rowCount;
    }



    public void agregarDatosLibros(String titulo, Integer edicion , String editorial, String autor, String isbn, Integer año, Integer deterioro){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            Statement st = connection.createStatement();

            st.executeUpdate("INSERT INTO Libros " +
                    "VALUES ('"+isbn+"', '"+titulo+"', "+"'"+editorial+"'"+", '"+autor+"', "+deterioro+", "+edicion+", "+año+")");

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


    }


    public int consultar(Integer id, String tabla, String column) {

        int count = 0;

        try (Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            String queryCheck = "SELECT * from " + tabla + " WHERE " + column + " = " + id + "";
            final PreparedStatement ps = connection.prepareStatement(queryCheck);
            final ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                count = count + 1;
            }
            connection.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        System.out.println(count);
        return count;


    }



    public int consultarLibros(String id, String tabla, String column){

        int count = 0;

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            String queryCheck = "SELECT * from "+tabla+" WHERE "+column+" = '"+ id +"'";
            final PreparedStatement ps = connection.prepareStatement(queryCheck);
            final ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                count = count + 1;
            }
            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        System.out.println(count);
        return count;
    }


    public int buscarUltimoId(String tablename, String column){

        int count = 0;
        Statement st;
        ResultSet rs;
        Integer id = 0;

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            st = connection.createStatement();
            String recordQuery = ("Select * from "+tablename);
            rs = st.executeQuery(recordQuery);
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                id = rs.getInt(column);
            }


            System.out.println("aaaaa"+id);

        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        System.out.println(count);

        return id+1;

    }


    // Agregar datos prestamo -----------------------------------------------------------------------



    public void agregarDatosPrestamo(Integer idprestamo, String fechatope , String fechadevo, String isbn, Integer idsocio){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            Statement st = connection.createStatement();

            st.executeUpdate("INSERT INTO prestamos " +
                    "VALUES ("+idprestamo+", '"+fechatope+"', "+"'"+fechadevo+"'"+", '"+isbn+"', "+idsocio+")");

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


    }


    public void actDatosLibros(String titulo, Integer edicion , String editorial, String autor, String isbn, Integer año, Integer deterioro, String cla){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("update`libros` set `titulo` = '"+titulo+"',`editorial` = '"+editorial+"',`autor` = '"+autor+"',`deterioro` = '"+deterioro+"',`añoedicion` = '"+edicion+"',`año` = '"+año+"'  where `ISBN` = '"+cla+"'");
            updateEXP.executeUpdate();

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }


    public void actDatosSocios(Integer idsocio, String nombre , String apellido, Long cedula2, Long telf2, String direccion){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("update`socios` set `nombre` = '"+nombre
                    +"',`apellido` = '"+apellido+"',`cedula` = '"+cedula2
                    +"',`telefono` = '"+telf2+"',`direccion` = '"+direccion
                    +"' where `idSocios` = '"+idsocio+"'");
            updateEXP.executeUpdate();

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }




    public void eliminarSocio(Integer idsocio){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("DELETE FROM socios WHERE idSocios = '"+idsocio+"' ");
            updateEXP.executeUpdate();
            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }



    public void eliminarLibro(String isbn){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("DELETE FROM libros WHERE ISBN = '"+isbn+"' ");
            updateEXP.executeUpdate();
            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }



    public void eliminarPrestamoLibro(String isbn){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("DELETE FROM prestamos WHERE Libros_ISBN = '"+isbn+"' ");
            updateEXP.executeUpdate();
            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }



    public void eliminarPrestamoSocio(Integer idsocio){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("DELETE FROM prestamos WHERE Socios_idSocios = '"+idsocio+"' ");
            updateEXP.executeUpdate();
            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }




    public void eliminarPrestamoIndv(String idprestamo){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("DELETE FROM prestamos WHERE idPrestamos = '"+idprestamo+"' ");
            updateEXP.executeUpdate();
            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }






}









