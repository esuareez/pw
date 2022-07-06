package Servicios;

import Modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class UsuarioServ extends GestionDb<Usuario> {

    private static UsuarioServ instancia;
    private List<Usuario> usuarioList = new ArrayList<>();

    // ADMIN
    // private Usuario admin = new Usuario(1,"admin","admin","admin","admin");
    // private Usuario user = new Usuario(2,"eliam","eliam","123","cliente");
    public UsuarioServ() {
        super(Usuario.class);
        // usuarioList.add(admin);
        // usuarioList.add(user);
    }

    public static UsuarioServ getInstance() {
        if (instancia == null)
            return new UsuarioServ();
        return instancia;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public Usuario getUsuarioporUsuario(String user){
        return usuarioList.stream().filter(e -> e.getUserName().equalsIgnoreCase(user)).findFirst().orElse(null);
    }

    public Usuario getUsuarioporId(int id){
        return usuarioList.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    public void crearUsuario(Usuario usuario){
        if(getUsuarioporUsuario(usuario.getUserName()) != null)
            exit(-1);
        usuario.setId(makeId());
        usuarioList.add(usuario);
        for(var item : usuarioList)
        {
            System.out.println(item.getNombre()+" "+item.getRol());
        }
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
