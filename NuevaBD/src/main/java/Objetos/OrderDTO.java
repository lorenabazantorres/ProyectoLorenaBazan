/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author LORENA
 */
public class OrderDTO {

    private Date fecha_compra;
    private int cantidad;
    private ClientesDTO dni_cliente;
    private PrendasDTO ref;
    private String referenciaventa;
    private EmpleadosDTO dni_empleado;

    //--------------
    //CONSTRUCTORES
    public OrderDTO() {

    }

    public OrderDTO(Date fecha_compra, int cantidad, ClientesDTO dni_cliente,
        PrendasDTO ref, EmpleadosDTO dni_empleado) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmSS");
        this.referenciaventa = df.format(fecha_compra);
        this.fecha_compra = fecha_compra;
        this.cantidad = cantidad;
        this.dni_cliente = dni_cliente;
        this.ref = ref;
        this.dni_empleado = dni_empleado;
    }



    //----
    //G&S
    public Date getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(Date fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ClientesDTO getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(ClientesDTO dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public PrendasDTO getRef() {
        return ref;
    }

    public void setRef(PrendasDTO ref) {
        this.ref = ref;
    }

    public String getReferenciaventa() {
        return referenciaventa;
    }

    public void setReferenciaventa(String referenciaventa) {
        this.referenciaventa = referenciaventa;
    }

    public EmpleadosDTO getDni_empleado() {
        return dni_empleado;
    }

    public void setDni_empleado(EmpleadosDTO dni_empleado) {
        this.dni_empleado = dni_empleado;
    }
    
      public String getfecha_compra_toString() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha_compra);
    }

    //---------
    //TOSTRING
    @Override
    public String toString() {
        return "OrderDTO{" + "fecha_compra=" + fecha_compra + ", cantidad=" + cantidad
                + ", dni_cliente=" + dni_cliente.getDni() + ", ref=" + ref.getReferencia()
                + ", referenciaventa=" + referenciaventa + ", dni_empleado=" + dni_empleado.getDni() + '}';
    }
    
     public String imprimir() {
        String resmetodo = "";
        resmetodo += this.getfecha_compra_toString() + ";";
        resmetodo += this.cantidad + ";";
        resmetodo += this.dni_cliente.getDni() + ";";
        resmetodo += this.ref.getReferencia() + ";";
        resmetodo += this.referenciaventa + ";";
        resmetodo += this.dni_empleado.getDni();
        return resmetodo;
    }

}
