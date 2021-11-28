/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import Excepciones.AccesoDatosEx;
import Excepciones.EscrituraDatosEx;
import Excepciones.LecturaDatosEx;
import Objetos.OrderDTO;
import Objetos.PrendasDTO;
import Objetos.Tallaje;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LORENA
 */
public class AccesoDatosImpl implements IAccesoDatos {

    @Override
    public boolean existeRecurso(String nombreArchivo) throws AccesoDatosEx {
        File archivo = new File(nombreArchivo);

        return archivo.exists();
    }

    @Override
    public void agregarArticulo(PrendasDTO obj, String nombreArchivo, boolean anexar) throws EscrituraDatosEx {
        File archivo = new File(nombreArchivo); //****
        try {
            if (archivo.exists()) {
                PrintWriter salida = new PrintWriter(new FileWriter(nombreArchivo, anexar));
                salida.println(obj.imprimir());
                salida.close();
            }
        } catch (IOException ex) {
            System.out.println("No se pudo escribir sobre el archivo");
            throw new EscrituraDatosEx("Excepción escribiendo un nuevo artículo");
        }
    }
    
    
    @Override
    public void agregarCompra(OrderDTO compra, String nombreArchivo, boolean anexar) throws EscrituraDatosEx {
        File archivo = new File(nombreArchivo); //****
        try {
            if (archivo.exists()) {
                PrintWriter salida = new PrintWriter(new FileWriter(nombreArchivo, anexar));
                salida.println(compra.imprimir());
                salida.close();
            }

        } catch (IOException ex) {
            System.out.println("No se pudo escribir sobre el archivo");
            throw new EscrituraDatosEx("Excepción escribiendo un nuevo artículo");
        }
    }

    @Override
    public void crearRecurso(String nombreRecurso) throws AccesoDatosEx {
        File archivo = new File(nombreRecurso);

        try {
            PrintWriter salida = new PrintWriter(new FileWriter(archivo));
            salida.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            throw new AccesoDatosEx("Excepción intentando crear el archivo");
        }
    }
////
    @Override
    public List<PrendasDTO> listarRecurso(String nombreRecurso) throws LecturaDatosEx {
        File archivo = new File(nombreRecurso);
        PrendasDTO prendaN = null;
        String[] prenda = new String[5];
        List<PrendasDTO> prendas = new ArrayList<>();

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/mm/yyyy");

        try {
            BufferedReader entrada = new BufferedReader(new FileReader(archivo));
            String lectura = null;
            while ((lectura = entrada.readLine()) != null) {
                
                prenda = lectura.split(";");
                
                String referencia = prenda[0];
                String nombrearticulo = prenda[1];
                double precio = Double.parseDouble(prenda[2]);
                String descripcion = prenda[3];
                Date fecha_temporada = formatoFecha.parse(prenda[4]);
                Tallaje talla = Tallaje.valueOf(prenda[5]);
                prendaN = new PrendasDTO(referencia, nombrearticulo, precio, descripcion, fecha_temporada, talla); 
                prendas.add(prendaN);

            }
            entrada.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);

        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            throw new LecturaDatosEx("Excepción leyendo el fichero...");
        } catch (ParseException ex) {
            ex.printStackTrace(System.out); //***
            throw new LecturaDatosEx("Excepcion listando el archivo");  
        }
        return prendas;
    }
    
    
    @Override
    public String borrarRecurso(String nombreRecurso) throws AccesoDatosEx {
        File archivo = new File(nombreRecurso);
        String msj = "";

        if (existeRecurso(nombreRecurso)) {
            archivo.delete();
            msj = "Recurso borrado con éxito";
        } else {
            msj = "No se ha podido borrar el recurso";
        }
        return msj;
    }
    
}
