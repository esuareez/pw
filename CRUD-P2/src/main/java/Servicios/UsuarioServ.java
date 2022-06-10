package Servicios;

import Modelos.Pedido;
import Modelos.Producto;
import Modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioServ {

    private static UsuarioServ instancia;
    private List<Usuario> usuarioList = new ArrayList<>();

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    // ADMIN
    private Usuario admin = new Usuario(1,"admin","admin","admin");
    public UsuarioServ(){
        usuarioList.add(admin);
    }

    public static UsuarioServ getInstance() {
        if (instancia == null)
            return new UsuarioServ();
        return instancia;
    }

    public Usuario getUsuarioporUsuario(String user){
        return usuarioList.stream().filter(e -> e.getUser().equalsIgnoreCase(user)).findFirst().orElse(null);
    }

    public Usuario getUsuarioporId(int id){
        return usuarioList.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    public Usuario crearUsuario(Usuario usuario){
        if(getUsuarioporUsuario(usuario.getUser()) != null)
            return null;
        usuario.setId(makeId());
        usuarioList.add(usuario);
        return usuario;
    }

    public int makeId(){
        int num, min = 100000, max = 999999;
        num = (int)Math.floor(Math.random()*(max-min+1)+min);
        while(getUsuarioporId(num) != null)
        {
            num = (int)Math.floor(Math.random()*(max-min+1)+min);
        }
        return num;
    }
}
