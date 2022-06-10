package Modelos;

public class Usuario {
    private int id;
    private String user;
    private String password;
    private String rol;

    public Usuario() {
    }

    public Usuario(String user, String password, String rol) {
        this.user = user;
        this.password = password;
        this.rol = rol;
    }

    public Usuario(int id, String user, String password, String rol) {
        this.id = id;
        this.user = user;
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
}
