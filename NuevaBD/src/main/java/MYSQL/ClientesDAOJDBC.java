package MYSQL;

import static MYSQL.Conexion.close;
import static MYSQL.Conexion.getConnection;

import Objetos.ClientesDTO;
import Objetos.EmpleadosDTO;
import com.mysql.cj.protocol.Resultset;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientesDAOJDBC implements ClientesDAO {

    /*
- AUTOMCOMMIT: SIEMPRE QUE POR DEFECTO QUERAMOS HACER UN SELECT, UPDATE... CUANDO SE EJECUTA LA CONSULTA (EXECUTEQUERY)
LO HACE EN EL MOMENTO (valor por defecto verdadero/falso, nuestro objetivo es que este falso)

-COMMIT: PERMITE CUANDO HAYAMOS TERMINADO LA TRANSACCION, SE LLEVEN A CABO EN LA BBDD
    
-ROLLBACK: PERMITE VOLVER HACIA ATRAS EN CASO DE FALLO EN LA EJECUCION DE LA TRANSACCION
    
Crear una conexion fuera de los metodos y los metodos detecten desde donde se ha creado la conexion
     */
    private static final String SQL_SELECT = "SELECT * FROM clientes";
    private static final String SQL_ORDENAR = "SELECT * FROM clientes ORDER BY apellidos";
    private static final String SQL_INSERT = "INSERT INTO clientes"
            + "(dni,nombre,apellidos,email,telefono,usuario,contrasena) VALUES "
            + "(?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE clientes SET "
            + "nombre = ?, "
            + "apellidos = ?, "
            + "email = ?, "
            + "telefono = ?, "
            + "usuario = ?, "
            + "contrasena = ?  "
            + "WHERE dni = ?";

    private static final String SQL_BUSCAR = "SELECT * FROM clientes WHERE usuario='?' AND contrasena='?'";

    private static final String SQL_DELETE = "DELETE FROM clientes  "
            + "WHERE dni = ? ";

    private Connection conexionTransaccional;

    public ClientesDAOJDBC() {
    }

    public ClientesDAOJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }

    @Override
    public int actualizar(ClientesDTO cliente) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE); 
     
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getUsuario());
            stmt.setString(6, cliente.getContrasena());
            stmt.setString(7, cliente.getDni());

//            String tempSQL = stmt.toString();
//            int i1 = tempSQL.indexOf(":") + 2;
//            tempSQL = tempSQL.substring(i1);
//            System.out.println(tempSQL);

            registros = stmt.executeUpdate();

        } catch (SQLException e) {
        } finally {
            close(stmt);
            if (this.conexionTransaccional == null) {
                close(conn);
            }
        }
        return registros;
    }

    @Override
    public List<ClientesDTO> OdenarApellidos() throws SQLException {
        //CREAMOS NUESTROS OBJETOS A NULL
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        //String [] arr = new String[3];
        List<ClientesDTO> cliente = new ArrayList<>();

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConnection();
            stmt = conn.prepareStatement(SQL_ORDENAR);
            rs = stmt.executeQuery();

            while (rs.next()) {

                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                String usuario = rs.getString("usuario");
                String contrasena = rs.getString("contrasena");

                cliente.add(new ClientesDTO(dni, nombre, apellidos, email, telefono, usuario, contrasena));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            close(rs);
            close(stmt);
            if (this.conexionTransaccional == null) {
                close(conn);
            }
        }
        return cliente;
    }

    @Override
    public List<ClientesDTO> listar() throws SQLException {
        //CREAMOS NUESTROS OBJETOS A NULL
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        //String [] arr = new String[3];
        List<ClientesDTO> cliente = new ArrayList<>();

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {

                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                String usuario = rs.getString("usuario");
                String contrasena = rs.getString("contrasena");

                cliente.add(new ClientesDTO(dni, nombre, apellidos, email, telefono, usuario, contrasena));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            close(rs);
            close(stmt);
            if (this.conexionTransaccional == null) {
                close(conn);
            }
        }
        return cliente;
    }

    @Override
    public ClientesDTO buscarUsuario(String usuario, String contrasena) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            //1. ESTABLECER LA CONEXION
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConnection();

            //2. PREPARED STATEMENT
            String consulta = "SELECT * from clientes where usuario = ? and contrasena = ?";

            stmt = conn.prepareStatement(consulta);

            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);

            ResultSet registros = stmt.executeQuery();

            if (registros.next()) {

                ClientesDTO clientetmp = new ClientesDTO(registros.getString("dni"), registros.getString("nombre"), registros.getString("apellidos"), registros.getString("email"), registros.getString("telefono"), registros.getString("usuario"), registros.getString("contrasena"));
                return clientetmp;
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally { //close
            close(stmt);
            if (this.conexionTransaccional == null) {
                close(conn);
            }
        }
        return null;
    }

    @Override
    public int insertar(ClientesDTO cliente) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0; //num registros

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConnection();

            stmt = conn.prepareStatement(SQL_INSERT);

            stmt.setString(1, cliente.getDni());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellidos());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefono());
            stmt.setString(6, cliente.getUsuario());
            stmt.setString(7, cliente.getContrasena());

//            String tempSQL = stmt.toString();
//            int i1 = tempSQL.indexOf(":") + 2;
//            tempSQL = tempSQL.substring(i1);
//            System.out.println(tempSQL);

            registros = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {

            close(stmt);
            if (this.conexionTransaccional == null) {
                close(conn);
            }
        }

        return registros;

    }

    @Override
    public int borrarporId(ClientesDTO clientes) throws SQLException {

        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0; //num registros

        try {
            //1. ESTABLECER LA CONEXION
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConnection();

            //2. PREPARED STATEMENT
            stmt = conn.prepareStatement(SQL_DELETE);

            stmt.setString(1, clientes.getDni());

            //ejecutamos la consulta
            registros = stmt.executeUpdate();

        } finally { //close
            close(stmt);
            if (this.conexionTransaccional == null) {
                close(conn);
            }
        }
        return registros;
    }

}
