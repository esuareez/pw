package Servicios;

import Modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class fakeServices {
    private static fakeServices instancia;
    private List<Usuario> listaUsuarios = new ArrayList<>();

    /**
     * Constructor privado.
     */
    private fakeServices(){
        //anadiendo los usuarios.
        listaUsuarios.add(new Usuario("admin", "admin", "admin"));
        listaUsuarios.add(new Usuario("eliam","Eliam Pimentel", "1234"));
    }

    public static fakeServices getInstancia(){
        if(instancia==null){
            instancia = new fakeServices();
        }
        return instancia;
    }

    /**
     * Permite autenticar los usuarios. Lo ideal es sacar en
     * @param usuario
     * @param password
     * @return
     */
    public Usuario autheticarUsuario(String usuario, String password){
        //simulando la busqueda en la base de datos.
        return new Usuario(usuario, usuario, password);
    }
}
