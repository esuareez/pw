package Modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="miusuario")
public class Usuario {
    @Id
    @GeneratedValue
    private int id;
    private String user;
    private String nombre;
    private String password;
    private String rol;

    public Usuario() {
    }

    public Usuario(String user, String nombre, String password, String rol) {
        this.user = user;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
    }

    public Usuario(int id, String user, String nombre, String password, String rol) {
        this.id = id;
        this.user = user;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
