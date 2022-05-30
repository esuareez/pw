package Controlador;

import io.javalin.Javalin;


public class UsuarioController {

    private Javalin app;
    public UsuarioController(Javalin app)
    {
        this.app = app;
    }

    public void rutas(){

        app.get("/",ctx -> {
            if(ctx.cookie("usuario") == null){
                ctx.redirect("formulario.html");
                return;
            }
            ctx.redirect("/inicio");
            return;
        });

        app.post("/login", ctx -> {
           String usuario = ctx.formParam("usuario");
           String password = ctx.formParam("password");
           if(usuario == null || password == null)
           {
               ctx.redirect("/formulario.html");
               return;
           }

           ctx.cookie("usuario",usuario,180);
           ctx.redirect("/inicio");
           return;
        });

        app.get("/inicio", ctx -> {
            if(ctx.cookie("usuario") == null){
                ctx.redirect("formulario.html");
                return;
            }
            ctx.redirect("/inicio.html");
        });
    }

}
