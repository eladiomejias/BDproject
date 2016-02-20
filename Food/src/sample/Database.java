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


    public void agregarDatosEmpleados(Integer code, String nombre, String telefono){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            Statement st = connection.createStatement();

            st.executeUpdate("INSERT INTO empleados " +
                    "VALUES ('"+code+"', '"+nombre+"', '"+telefono+"')");

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


    }




    public void agregarDatosCocinero(Integer code, String year, Integer idepp){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            Statement st = connection.createStatement();

            st.executeUpdate("INSERT INTO cocinero " +
                    "VALUES ('"+code+"', '"+year+"', '"+idepp+"')");

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


    }



    public void agregarDatosAuxiliar(Integer code, String fecha, Integer idepp){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            Statement st = connection.createStatement();

            st.executeUpdate("INSERT INTO auxiliar " +
                    "VALUES ('"+code+"', '"+fecha+"', '"+idepp+"')");

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


    }



    public void agregarDatosPlato(String nombre, String tipo, String precio){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            Statement st = connection.createStatement();

            st.executeUpdate("INSERT INTO plato " +
                    "VALUES ('"+nombre+"', '"+tipo+"', '"+precio+"')");

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


    }




    public void agregarDatosIngrediente(String nombre, Integer cant, Integer dp, Integer ds, Integer cf){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            Statement st = connection.createStatement();

            st.executeUpdate("INSERT INTO ingrediente " +
                    "VALUES ('"+nombre+"', '"+cant+"', '"+dp+"', '"+ds+"', '"+cf+"')");

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


    }



    public void agregarDatosCCP(Integer code, String nombre){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            Statement st = connection.createStatement();

            st.executeUpdate("INSERT INTO cocineroconplatos " +
                    "VALUES ('"+code+"', '"+nombre+"')");

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


    }



    public void agregarDatosPCI(String ing, String nombre){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            Statement st = connection.createStatement();

            st.executeUpdate("INSERT INTO platoconingredientes " +
                    "VALUES ('"+nombre+"', '"+ing+"')");

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


    }





    public static int countRows(String tableName) throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food","java","password");
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


    public int consultarString(String id, String tabla, String column) {

        int count = 0;

        try (Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            String queryCheck = "SELECT * from " + tabla + " WHERE " + column + " = '" + id + "'";
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


    public int consultarCPP(Integer idcoc, String nombre, String tabla, String columnId, String columnNomb) {
        int count = 0;
        Statement st;
        ResultSet rs;
        Integer id = 0;
        String name = "";
        Integer aux = 0;

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            st = connection.createStatement();
            String recordQuery = ("Select * from cocineroconplatos");
            rs = st.executeQuery(recordQuery);
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                id = rs.getInt(columnId);
                name = rs.getString(columnNomb);

                if(id==idcoc&&name.equals(nombre)){
                    System.out.println("Si esta");
                    aux = 1;
                }

                //System.out.println(name);
            }


        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        System.out.println(count);

        return aux;




    }




    public int consultarPCI(String ing, String nombre, String tabla, String columnIng, String columnNomb) {
        int count = 0;
        Statement st;
        ResultSet rs;
        Integer id = 0;
        String name = ""; String ingren = "";
        Integer aux = 0;

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            st = connection.createStatement();
            String recordQuery = ("Select * from platoconingredientes");
            rs = st.executeQuery(recordQuery);
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                ingren = rs.getString(columnIng);
                name = rs.getString(columnNomb);

                if(ingren.equals(ing)&&name.equals(nombre)){
                    System.out.println("Si esta");
                    aux = 1;
                }

                //System.out.println(name);
            }


        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        System.out.println(count);

        return aux;


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




    public void actDatosEmpleado(Integer idemp, String nombre , String telf){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("update`empleados` set `nombre` = '"+nombre
                    +"',`telefono` = '"+telf+"'"+" where `idEmpleados` = '"+idemp+"'");
            updateEXP.executeUpdate();

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    public void actDatosCocinero(Integer idcoc, Integer year){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("update`cocinero` set `AñosServicio` = '"+year
                    +"'"+"where `idCocinero` = '"+idcoc+"'");
            updateEXP.executeUpdate();

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }


    public void actDatosAuxiliar(Integer idaux, String date){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("update`auxiliar` set `FechaNacimiento` = '"+date
                    +"'"+"where `idAuxiliar` = '"+idaux+"'");
            updateEXP.executeUpdate();

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }


    public void actDatosPlato(String tipo, String precio, String nombre){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("update`plato` set `Tipo` = '"+tipo
                    +"', "+"`Precio` = '"+precio+"'  where `Nombre` = '"+nombre+"'");
            updateEXP.executeUpdate();

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }


    public void actDatosIngrediente(String nombre, Integer cant, Integer dp, Integer ds, Integer cf){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("update`ingrediente` set `Nombre` = '"+nombre
                    +"', "+"`Cantidad` = '"+cant+"', `DP` = '"+dp+"', `DS` = '"+ds+"', `CF` = '"+cf+"' where `Nombre` = '"+nombre+"'");
            updateEXP.executeUpdate();

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }


    public void datosP(Integer id, String nombre, Integer idOriginal, String nombreOriginal){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("update`cocineroconplatos` set `Plato_Nombre` = '"+nombre
                    +"', "+"`Cocinero_idCocinero` = "+id+" where `Cocinero_idCocinero` = "+idOriginal+" AND `Plato_Nombre` = '"+nombreOriginal+"'");
            updateEXP.executeUpdate();

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }



    public void datosPCI(String ing, String nombre, String ingOriginal, String nombreOriginal){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("update`platoconingredientes` set `Plato_Nombre` = '"+nombre
                    +"', "+"`Ingrediente_Nombre` = '"+ing+"' where `Ingrediente_Nombre` = '"+ingOriginal+"' AND `Plato_Nombre` = '"+nombreOriginal+"'");
            updateEXP.executeUpdate();

            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }







    public void eliminar(Integer id, String table, String column){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("DELETE FROM "+table+" WHERE "+column+" = '"+id+"' ");
            updateEXP.executeUpdate();
            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }




    public void eliminarCpp(Integer id, String nombre, String table, String columnId, String columnNomb){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("DELETE FROM "+table+" WHERE "+columnId+" = "+id+" AND "+columnNomb+" = '"+nombre+"'");
            updateEXP.executeUpdate();
            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }


    public void eliminarPCI(String ing, String nombre, String table, String columnId, String columnNomb){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("DELETE FROM "+table+" WHERE "+columnId+" = '"+ing+"' AND "+columnNomb+" = '"+nombre+"'");
            updateEXP.executeUpdate();
            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }




    public void eliminarString(String id, String table, String column){

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            PreparedStatement updateEXP = connection.prepareStatement("DELETE FROM "+table+" WHERE "+column+" = '"+id+"' ");
            updateEXP.executeUpdate();
            connection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }




    public int buscarId(String tablename, String column, Integer idepp){

        Statement st;
        ResultSet rs;
        Integer id = 0;
        Integer aux = 0;

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            st = connection.createStatement();
            String recordQuery = ("Select * from "+tablename);
            rs = st.executeQuery(recordQuery);
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                id = rs.getInt(column);
                if(idepp==id){
                    aux = 1;
                }
            }


        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


        return aux;

    }



    public int buscarIdString(String tablename, String column, String code){

        Statement st;
        ResultSet rs;
        String id = "";
        String idTwo = "";
        Integer aux = 0;
        String codeTwo = code.toLowerCase();

        try(Connection connection = DriverManager.getConnection(myUrl, username, password)) {
            st = connection.createStatement();
            String recordQuery = ("Select * from "+tablename);
            rs = st.executeQuery(recordQuery);
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                id = rs.getString(column);
                idTwo = id.toLowerCase();
                if(idTwo.equals(codeTwo)){
                    aux = 1;
                }
            }


        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


        return aux;

    }



}









