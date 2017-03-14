package es.jco.examenandroidflp;

import java.io.Serializable;

/**
 * Created by CSI2 on 13/01/2017.
 */
public class Usuario implements Serializable{

    private Integer id, tipo;
    private String nombre, email;


    public Usuario(Integer id, Integer tipo, String nombre, String email) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
