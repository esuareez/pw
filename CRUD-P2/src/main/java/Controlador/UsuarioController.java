package Controlador;

import Modelos.Usuario;
import Servicios.UsuarioServ;
import Util.BaseController;
import io.javalin.Javalin;

public class UsuarioController extends BaseController {
    public UsuarioController(Javalin app) {
        super(app);
    }
    public void aplicarRutas(){
        app.get("/login",ctx -> {
            ctx.redirect("/Templates/Usuario/Login.html");
        });

        app.post("/login",ctx -> {
            String usuario = ctx.formParam("usuario");
            String password = ctx.formParam("password");
            System.out.println(usuario+" "+password);
            Usuario user = UsuarioServ.getInstance().getUsuarioporUsuario(usuario);
            for(var item : UsuarioServ.getInstance().getUsuarioList()){
                System.out.println(item.getId()+" "+item.getNombre()+" "+item.getRol());
            }
            if( user != null) {
                if(user.getPassword().equals(password)){
                    ctx.sessionAttribute("usuario",user);
                    if(user.getRol().equalsIgnoreCase( "cliente"))
                    {
                        ctx.redirect("/");
                    }else{
                        if(user.getRol().equalsIgnoreCase("admin")){
                            ctx.redirect("/admin/inventario");
                        }
                    }
                }
            }
        });

        app.get("/signup", ctx -> {
            ctx.redirect("/Templates/Usuario/SignUp.html");
        });

        app.post("/signup", ctx -> {
            String usuario = ctx.formParam("usuario");
            String nombre = ctx.formParam("nombre");
            String password = ctx.formParam("password");

            if(UsuarioServ.getInstance().getUsuarioporUsuario(usuario) != null){
                ctx.redirect("/signup");
            }else{
                Usuario user = new Usuario(usuario,nombre,password,"cliente");
                UsuarioServ.getInstance().crearUsuario(user);
                //Usuario u = UsuarioServ.getInstance().getUsuarioporUsuario(user.getUser());
                //System.out.println(u.getId()+" "+u.getUser()+" "+u.getRol());
                ctx.redirect("/login");
            }
        });

        app.get("/logout",ctx -> {
            ctx.sessionAttribute("usuario");
        });

    }
}
