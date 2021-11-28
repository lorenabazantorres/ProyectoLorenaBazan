package Objetos;
/**
 * Una clase para representar círculos situados sobre el plano.
 * Cada círculo queda determinado por su radio junto con las 
 * coordenadas de su  centro.
 * @version 1.2, 24/12/04
 * @author Rafa Caballero
 */

public class EmpleadosDTO {

    private String dni;
    private String nombre;
    private String apellidos;

    //------------
    //CONSTRUCTORES
    public EmpleadosDTO() {
    }

    public EmpleadosDTO(String dni, String nombre, String apellidos) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public EmpleadosDTO(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public EmpleadosDTO(String dni) {
        this.dni = dni;
    }

    //----
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

    //--------
    //TOSTRING
    @Override
    public String toString() {
        String resultado = dni + "\t" + nombre + "\t\t" + apellidos;
        return resultado;
    }

  
}
