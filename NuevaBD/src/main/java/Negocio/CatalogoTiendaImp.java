/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import AccesoDatos.AccesoDatosImpl;
import AccesoDatos.*;
import Excepciones.*;
import MYSQL.*;
import Objetos.OrderDTO;
import Objetos.PrendasDTO;
import java.io.*;
import java.util.*;

public class CatalogoTiendaImp implements ICatalogoTienda {

    //ATRIBUTOS
    private IAccesoDatos datos;
    private List<ClientesDAO> clientes = null;
    private List<PrendasDTO> prendas = null;
    private List<OrderDTO> ventas = null;


    //CONSTRUCTOR
    public CatalogoTiendaImp() {
        this.datos = new AccesoDatosImpl();
    }

//----------------------------------------------------------------------------
//INICIAR FICHERO
    @Override
    public String iniciar(String nombreCatalogo) {
        try {
            if (this.datos.existeRecurso(nombreCatalogo)) {
                this.datos.borrarRecurso(nombreCatalogo); 
                this.datos.crearRecurso(nombreCatalogo);
            } else {
                this.datos.crearRecurso(nombreCatalogo);
            }
        } catch (AccesoDatosEx ex) {
            ex.printStackTrace();
            System.out.println("Error al inicializar el catalogo");
        }
        return "Catalogo incializado correctamente";
    }

    @Override
    public void ordenarfichero(String nombreCatalogo, String catalogoordenado) {
        File f_entrada = new File(nombreCatalogo);
        File f_salida = new File(catalogoordenado);
        LinkedList<String> lista = new LinkedList<String>();
        try {
            FileReader fr = new FileReader(f_entrada);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(f_salida);
            PrintWriter pw = new PrintWriter(fw);

            String linea = null;
            while ((linea = br.readLine()) != null) {
                lista.add(linea);
            }
            Collections.sort(lista);
            Iterator iter = lista.iterator();
            String cadena;
             
            System.out.println("\n--CATALOGO ORDENADO ASCENDENTEMENTE POR REFERENCIA--");
            System.out.println("-----------------------------------------------------\n");
               
            while (iter.hasNext()) {
                cadena = (String) iter.next();
                pw.println(cadena);
                System.out.println(cadena);
            }

            br.close();
            fr.close();
            pw.close();
            fw.close();
        } catch (FileNotFoundException e) {

            System.err.println("No se ha encontrado el fichero");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void agregarPrendaCatalogo(PrendasDTO obj, String nombreArchivo) {
        try {
            if (this.datos.existeRecurso(nombreArchivo)) {
                this.datos.agregarArticulo(obj, nombreArchivo, true);
            } else {
                System.out.println("Catalogo no inicializado");
            }
        } catch (EscrituraDatosEx ex) {
            System.out.println("Error al agregar una nueva prenda al catálogo");
            ex.printStackTrace(System.out);
        } catch (AccesoDatosEx ex) {
            System.out.println("Error al agregar una nueva prenda al catálogo");
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public void agregarPrendaCarrito(PrendasDTO obj, String nombreArchivo) {
        try {
            if (this.datos.existeRecurso(nombreArchivo)) {
                this.datos.agregarArticulo(obj, nombreArchivo, true);
            } else {
                System.out.println("Catalogo no inicializado");
            }
        } catch (EscrituraDatosEx ex) {
            System.out.println("Error al agregar una nueva prenda al carrito");
            ex.printStackTrace(System.out);
        } catch (AccesoDatosEx ex) {
            System.out.println("Error al agregar una nueva prenda al carrito");
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public void agregarCompra(OrderDTO compra, String nombreArchivo) {
        try {
            if (this.datos.existeRecurso(nombreArchivo)) {
                this.datos.agregarCompra(compra, nombreArchivo, true);
            } else {
                System.out.println("Catalogo no inicializado");
            }
        } catch (EscrituraDatosEx ex) {
            System.out.println("Error al agregar una nueva venta al catálogo");
            ex.printStackTrace(System.out);
        } catch (AccesoDatosEx ex) {
            System.out.println("Error al agregar una nueva venta al catálogo");
            ex.printStackTrace(System.out);
        }
    }
    
    @Override
    public String listarRecurso(String nombreRecurso) {
        List<PrendasDTO> prendas = new ArrayList<>();
        try {
            prendas = datos.listarRecurso(nombreRecurso);
            prendas.forEach(prenda -> {
                prenda.imprimir();
                System.out.println(prenda.getReferencia() + " ; " + prenda.getNombre() + " ; " + prenda.getPrecio() + " ;" + prenda.getDescripcion() + " ;" + prenda.getfecha_temporada_toString() + " ;" + prenda.getTalla());
            });

        } catch (LecturaDatosEx e) {
            System.out.println("Error listando el catalogo");
            e.printStackTrace(System.out);
        }
        return "";
    }
    
    @Override
    public PrendasDTO comprarPrenda(String nombreRecurso, String referencia) {
        File archivo = new File(nombreRecurso);
        List<PrendasDTO> listaprendas = null;
        PrendasDTO prendaencontrada = null;

        try {
            if (listaprendas == null) {
                listaprendas = this.datos.listarRecurso(nombreRecurso);
            }

            int i_ArrayPrendas = 0;
            while (i_ArrayPrendas < listaprendas.size() && prendaencontrada == null) {

                if (listaprendas.get(i_ArrayPrendas).getReferencia().equalsIgnoreCase(referencia)) {
                    prendaencontrada = listaprendas.get(i_ArrayPrendas);
                }
                i_ArrayPrendas++;
            }

        } catch (LecturaDatosEx ex) {
            ex.printStackTrace();
            System.out.println("Error al buscar la referencia");

        }
        return prendaencontrada;
    }

    
    @Override
    public PrendasDTO buscarPrenda(String nombreRecurso, String numref) {
        File archivo = new File(nombreRecurso);
        PrendasDTO prenda = null;

        List<PrendasDTO> ArrayPrendas = new ArrayList<>();
        try {
            ArrayPrendas = this.datos.listarRecurso(nombreRecurso);
            int i_ArrayPrendas = -1;
            while (i_ArrayPrendas < ArrayPrendas.size() - 1 && prenda == null) {
                i_ArrayPrendas++;
                if (ArrayPrendas.get(i_ArrayPrendas).getReferencia().equalsIgnoreCase(numref)) {
                    prenda = ArrayPrendas.get(i_ArrayPrendas);
                }
            }
        } catch (LecturaDatosEx ex) {
            ex.printStackTrace();
            System.out.println("Error al buscar el cliente a través del DNI");

        }
        return prenda;
    }
//

    @Override
    public double calcularTotalPrecio(List<OrderDTO> arrayArticulosCompra) {
        List<PrendasDTO> prendas = new ArrayList<>();
        double total = 0.0;

        for (OrderDTO n_compra : arrayArticulosCompra) {
            double preciocompra = n_compra.getCantidad() * n_compra.getRef().getPrecio();
            total += preciocompra;
        }
        return total;
    }

    @Override
    public int contadorArticulos(List<OrderDTO> arrayArticulosCompra) {
        List<PrendasDTO> prendas = new ArrayList<>();
        int cont = 0;

        for (OrderDTO n_compra : arrayArticulosCompra) {
            cont += n_compra.getCantidad();
        }

        return cont;
    }

    public double maxPrecioArticulo(List<OrderDTO> arrayArticulosCompra) {
        List<PrendasDTO> prendas = new ArrayList<>();
        double maxprecio = 0.0;

        for (OrderDTO n_compra : arrayArticulosCompra) {

            if (n_compra.getRef().getPrecio() > maxprecio) {
                maxprecio = n_compra.getRef().getPrecio();
            }
        }

        return maxprecio;
    }

}
