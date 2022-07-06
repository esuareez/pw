package Modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name="miusuario")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String userName;
    private String nombre;
    private String password;
    private String rol;

    public Usuario() {
    }

    public Usuario(String userName, String nombre, String password, String rol) {
        this.userName = userName;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
    }

    public Usuario(int id, String userName, String nombre, String password, String rol) {
        this.id = id;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String user) {
        this.userName = user;
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
