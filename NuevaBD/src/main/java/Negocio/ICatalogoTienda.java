/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Excepciones.AccesoDatosEx;
import Objetos.EmpleadosDTO;
import Objetos.OrderDTO;
import Objetos.PrendasDTO;
import java.util.List;

/**
 *
 * @author LORENA
 */
public interface ICatalogoTienda {

    public String iniciar(String nombreArchivo);
    
    void ordenarfichero(String nombreCatalogo, String catalogoordenado);
         
    double calcularTotalPrecio(List<OrderDTO> arrayArticulosCompra);
    
    int contadorArticulos(List<OrderDTO> arrayArticulosCompra);
    
     double maxPrecioArticulo(List<OrderDTO> arrayArticulosCompra); 
    
    String listarRecurso(String nombreRecurso);
   
    void agregarPrendaCatalogo(PrendasDTO prenda, String nombreArchivo);

      void agregarPrendaCarrito(PrendasDTO obj, String nombreArchivo);
      
     void agregarCompra(OrderDTO obj, String nombreArchivo);
  
     PrendasDTO buscarPrenda(String nombreRecurso, String ref) ;

     PrendasDTO comprarPrenda(String nombreRecurso, String referencia);

}
