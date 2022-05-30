package Modelo;

public class Usuario {
    String usuario;
    String password;

    public Usuario(){

    }

    public Usuario(String usuario,String password)
    {
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
