package MYSQL;


import static MYSQL.Conexion.close;
import static MYSQL.Conexion.getConnection;
import Objetos.ClientesDTO;
import Objetos.EmpleadosDTO;
import java.sql.*;
import java.util.*;

public class EmpleadosDAOJDBC implements EmpleadosDAO{

    /*
- AUTOMCOMMIT: SIEMPRE QUE POR DEFECTO QUERAMOS HACER UN SELECT, UPDATE... CUANDO SE EJECUTA LA CONSULTA (EXECUTEQUERY)
LO HACE EN EL MOMENTO (valor por defecto verdadero/falso, nuestro objetivo es que este falso)

-COMMIT: PERMITE CUANDO HAYAMOS TERMINADO LA TRANSACCION, SE LLEVEN A CABO EN LA BBDD
    
-ROLLBACK: PERMITE VOLVER HACIA ATRAS EN CASO DE FALLO EN LA EJECUCION DE LA TRANSACCION
    
Crear una conexion fuera de los metodos y los metodos detecten desde donde se ha creado la conexion
     */
    
    private static final String SQL_SELECT = "SELECT * FROM empleados";
    private static final String SQL_ORDENARDESC = "SELECT * FROM empleados ORDER BY nombre DESC";
    private static final String SQL_INSERT = "INSERT INTO empleados"
            + "(dni, nombre, apellidos) VALUES "
            + "(?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE empleados SET "
            + "nombre= ?, "
            + "apellidos= ? "
            + "WHERE dni=?";

    private static final String SQL_DELETE = "DELETE FROM empleados  "
            + "WHERE dni=?";
    
    private static final String SQL_BUSCAR = "SELECT * FROM empleados WHERE dni='?' ";

    /*------------------------------------------*/
    //Declaramos var privada de tipo connection: crea conexion desde fuera de los propios metodos
    private Connection conexionTransaccional;

    //Constructor vacio
    public EmpleadosDAOJDBC() {
        //Local
     
    }
    //Constructor lleno que recibe parametro de tipo connection
    public EmpleadosDAOJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }

    
    @Override
    public EmpleadosDTO buscarEmpleado(String usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            //1. ESTABLECER LA CONEXION
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConnection();

            //2. PREPARED STATEMENT
            String consulta = "SELECT * from empleados where dni = ? ";

            stmt = conn.prepareStatement(consulta);

            stmt.setString(1, usuario);
            
            
//         !!!!!!!!!!!! VER VARIABLES
//            String tempSQL = stmt.toString();
//            int i1 = tempSQL.indexOf(":") + 2;
//            tempSQL = tempSQL.substring(i1);
//            System.out.println(tempSQL);
            
            //ejecutamos la consulta
            ResultSet registros = stmt.executeQuery();

            if (registros.next()) {
               
                EmpleadosDTO empleadotmp = new EmpleadosDTO(registros.getString("dni"), registros.getString("nombre"), registros.getString("apellidos"));
                return empleadotmp;
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
    //Diferenciar en cada uno de los metodos si la conexion del get viene desde fuera o desde dentro
    public int actualizar(EmpleadosDTO empleado) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0; //num registros

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConnection(); 
            stmt = conn.prepareStatement(SQL_UPDATE);

            //Ejecutar los insert con los interrogantes
            stmt.setString(1, empleado.getDni()); //segundo interrogante
            stmt.setString(2, empleado.getNombre());
            stmt.setString(3, empleado.getApellidos());

            //ejecutamos la consulta
            registros = stmt.executeUpdate();

        } finally {
            close(stmt);
            if (this.conexionTransaccional == null) {
                close(conn);
            }
        }
        return registros;

    }
    
    @Override
    public List<EmpleadosDTO> OrdenarDescNombre() throws SQLException {
        //CREAMOS NUESTROS OBJETOS A NULL
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        //String [] arr = new String[3];
        List<EmpleadosDTO> empleado = new ArrayList<>();

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConnection(); 
            stmt = conn.prepareStatement(SQL_ORDENARDESC);
            rs = stmt.executeQuery();

            while (rs.next()) {

                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");

                empleado.add(new EmpleadosDTO(dni, nombre, apellidos));
            }
        } finally { //ejecuta siempre
            close(rs);
            close(stmt);
            //Si la conn es nula, quiere decir que viene desde dento, asi que la cierro
            if (this.conexionTransaccional == null) {
                close(conn);
            }
        }

        return empleado;
    }
    

    @Override
    public List<EmpleadosDTO> seleccionar() throws SQLException {
        //CREAMOS NUESTROS OBJETOS A NULL
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        //String [] arr = new String[3];
        List<EmpleadosDTO> empleado = new ArrayList<>();

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConnection(); 
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {

                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");

                empleado.add(new EmpleadosDTO(dni, nombre, apellidos));
            }
        } finally { //ejecuta siempre
            close(rs);
            close(stmt);
            //Si la conn es nula, quiere decir que viene desde dento, asi que la cierro
            if (this.conexionTransaccional == null) {
                close(conn);
            }
        }

        return empleado;
    }

    @Override
    public int insertar(EmpleadosDTO empleado) throws SQLException {

        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0; //num registros

        try {
            //1. ESTABLECER LA CONEXION
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConnection(); 

            //2. PREPARED STATEMENT
            stmt = conn.prepareStatement(SQL_INSERT);

            //Ejecutar los insert con los interrogantes
            stmt.setString(1, empleado.getDni()); //segundo interrogante
            stmt.setString(2, empleado.getNombre());
            stmt.setString(3, empleado.getApellidos());

            //ejecutamos la consulta
            registros = stmt.executeUpdate();

        } finally { //close
//            stmt.close();
//            conn.close();
            close(stmt);
            if (this.conexionTransaccional == null) {
                close(conn);
            }
        }
        return registros;
    }

    @Override
    public int borrarporId(EmpleadosDTO empleado) throws SQLException {

        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0; //num registros

        try {
            //1. ESTABLECER LA CONEXION
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConnection(); 

            //2. PREPARED STATEMENT
            stmt = conn.prepareStatement(SQL_DELETE);

            stmt.setString(1, empleado.getDni());

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
