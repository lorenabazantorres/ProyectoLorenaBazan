
package MYSQL;

import Objetos.ClientesDTO;
import java.sql.SQLException;
import java.util.List;


public interface ClientesDAO {
    
    int actualizar(ClientesDTO cliente) throws SQLException;
    
    List<ClientesDTO> OdenarApellidos() throws SQLException;
    
    List<ClientesDTO> listar() throws SQLException;
    
    int insertar(ClientesDTO cliente) throws SQLException ;
    
    int borrarporId(ClientesDTO clientes) throws SQLException ;
    
    ClientesDTO buscarUsuario(String usuario, String contrasena) throws SQLException ;
}
