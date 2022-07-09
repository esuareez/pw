package Servicios;

import Modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class UsuarioServ extends GestionDb<Usuario> {

    private static UsuarioServ instancia;

    public UsuarioServ() {
        super(Usuario.class);
    }

    public static UsuarioServ getInstance() {
        if (instancia == null)
            return new UsuarioServ();
        return instancia;
    }

    public Usuario getUsuarioporUsuario(String user, List<Usuario> usList){
        return usList.stream().filter(e -> e.getUserName().equalsIgnoreCase(user)).findFirst().orElse(null);
    }

}
