package Controlador;

import Modelo.Usuario;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;


public class UsuarioController {

    private Javalin app;
    public UsuarioController(Javalin app)
    {
        this.app = app;
    }

    public void rutas(){

        Usuario user = new Usuario("eliam","123");

        app.before(ctx -> {
            if(ctx.path().startsWith("/formulario.html") || ctx.path().startsWith("/login"))
            {
                return;
            }
            if(ctx.sessionAttribute("usuario") == null){
                ctx.redirect("/formulario.html");
                return;
            }
        });

        app.get("/",ctx -> {
            if(ctx.sessionAttribute("usuario") == null){
                ctx.redirect("formulario.html");
                return;
            }
            Map<String,Object> modelo = new HashMap<>();
            modelo.put("usuario",user);
            ctx.render("/templates/inicio.html",modelo);
        });

        app.post("/login", ctx -> {
           String usuario = ctx.formParam("usuario");
           String password = ctx.formParam("password");
            if((usuario == null || password == null) || !(usuario.equals(user.getUsuario()) && password.equals(user.getPassword())))
            {
                ctx.redirect("/formulario.html");
                return;
            }
            if(usuario.equals(user.getUsuario()) && password.equals(user.getPassword()))
            {
                ctx.sessionAttribute("usuario",usuario);
                Map<String,Object> modelo = new HashMap<>();
                modelo.put("usuario",user);
                ctx.render("/templates/inicio.html",modelo);
            }
        });

        app.get("/inicio", ctx -> {
            if(ctx.sessionAttribute("usuario") == null){
                ctx.redirect("formulario.html");
                return;
            }
            Map<String,Object> modelo = new HashMap<>();
            modelo.put("usuario",user);
            ctx.render("/templates/inicio.html",modelo);
            //ctx.redirect("/inicio.html");
        });
    }

}
