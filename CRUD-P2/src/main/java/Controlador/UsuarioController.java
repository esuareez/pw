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
            System.out.println(user.getUser()+" "+user.getPassword()+" "+user.getRol());
            if( user != null) {
                if(user.getPassword().equals(password)){
                    ctx.sessionAttribute("usuario",user);
                    if(user.getRol().equalsIgnoreCase( "cliente"))
                    {
                        ctx.redirect("/");
                    }else{
                        if(user.getRol().equalsIgnoreCase("admin")){
                            ctx.redirect("/inventario");
                        }
                    }
                }
            }
        });
    }
}
