/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import Excepciones.*;
import Objetos.OrderDTO;
import Objetos.PrendasDTO;
import java.util.List;

/**
 *
 * @author LORENA
 */
public interface IAccesoDatos {

    boolean existeRecurso(String nombreArchivo) throws AccesoDatosEx;

    void agregarArticulo(PrendasDTO obj, String nombreArchivo, boolean anexar) throws EscrituraDatosEx;
    
    void agregarCompra(OrderDTO compra, String nombreArchivo, boolean anexar) throws EscrituraDatosEx;

    void crearRecurso(String nombreRecurso) throws AccesoDatosEx;

    List<PrendasDTO> listarRecurso(String nombreRecurso) throws LecturaDatosEx;
    
    String borrarRecurso(String nombreRecurso) throws AccesoDatosEx;

}
