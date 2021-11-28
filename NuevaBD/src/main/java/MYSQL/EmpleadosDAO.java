
package MYSQL;

import Objetos.EmpleadosDTO;
import java.sql.SQLException;
import java.util.List;


public interface EmpleadosDAO {
    
    int actualizar(EmpleadosDTO empleado) throws SQLException;
    
    List<EmpleadosDTO> seleccionar() throws SQLException;
    
    int insertar(EmpleadosDTO empleado) throws SQLException ;
    
    int borrarporId(EmpleadosDTO empleado) throws SQLException;
    
    List<EmpleadosDTO> OrdenarDescNombre() throws SQLException;
    
    EmpleadosDTO buscarEmpleado(String usuario) throws SQLException ;
    
}
