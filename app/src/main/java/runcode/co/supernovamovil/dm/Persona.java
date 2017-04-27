package runcode.co.supernovamovil.dm;

/**
 * Created by root on 27/04/17.
 */

public class Persona {

    private String cedula;
    private String nombre;

    public Persona() {
    }

    public Persona(String nombre) {
        this.nombre=nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
