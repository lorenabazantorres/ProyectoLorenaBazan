/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.text.SimpleDateFormat;
import java.util.Date;


public class PrendasDTO {
    
    //ATRIBUTOS
    private String referencia;
    private String nombrearticulo;
    private double precio;
    private String descripcion;
    private Date temporada;
    Tallaje talla;
    
    public String imprimir() {
        String resmetodo = "";
        resmetodo += this.referencia + ";";
        resmetodo += this.nombrearticulo + ";";
        resmetodo += this.precio + ";";
        resmetodo += this.descripcion + ";";
        resmetodo += this.getfecha_temporada_toString();
        resmetodo += ";" + this.talla;
        return resmetodo;
    }
    
    //-------------
    //CONSTRUCTORES
    public PrendasDTO(String referencia, String nombrearticulo, 
            double precio, String descripcion, Date temporada, Tallaje talla) {
        this.referencia = referencia;
        this.nombrearticulo = nombrearticulo;
        this.precio = precio;
        this.descripcion = descripcion;
        this.temporada = temporada;
        this.talla = talla;
    }

    public PrendasDTO() {
    }

    public PrendasDTO(String nombrearticulo, double precio, 
            String descripcion, Date temporada, Tallaje talla) {
        this.nombrearticulo = nombrearticulo;
        this.precio = precio;
        this.descripcion = descripcion;
        this.temporada = temporada;
        this.talla = talla;
    }
    
    //---
    //G&S
    public String getReferencia() {
        return referencia;
    }

    public String getfecha_temporada_toString() {
        SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
        return df.format(temporada);
    }
    
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNombre() {
        return nombrearticulo;
    }

    public void setNombre(String nombrearticulo) {
        this.nombrearticulo = nombrearticulo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getTemporada() {
        return temporada;
    }

    public void setTemporada(Date temporada) {
        this.temporada = temporada;
    }

    public Tallaje getTalla() {
        return talla;
    }

    public void setTalla(Tallaje talla) {
        this.talla = talla;
    }
      
    //--------
    //TOSTRING
    @Override
    public String toString() {
       String resultado = referencia + "\t" + nombrearticulo + "\t\t" + precio + "\t" + descripcion + "\t\t" + this.getfecha_temporada_toString() + "\t" + talla;
        return resultado;
    }
    
}
