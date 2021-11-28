/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

/**
 *
 * @author LORENA
 */
public class ClientesDTO {
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String usuario;
    private String contrasena;

    //-----------
    //CONSTRUCTOR
   
    public ClientesDTO() {
    }

    public ClientesDTO(String dni, String nombre, String apellidos, String email, String telefono, String usuario, String contrasena) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public ClientesDTO(String nombre, String apellidos, String email, String telefono, String usuario, String contrasena) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public ClientesDTO(String dni) {
        this.dni = dni;
    }

    public ClientesDTO(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    
    
    //---
    //G&S
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    //---------
    //TOSTRING

    @Override
    public String toString() {
     String resultado = dni + "\t" + nombre + "\t\t" + apellidos+ "\t\t" + email+ "\t\t" + telefono+ 
             "\t" + usuario+ "\t\t" + contrasena;
     return resultado;
    }
   
    
}
