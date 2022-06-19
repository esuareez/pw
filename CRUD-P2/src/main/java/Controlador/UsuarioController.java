package Controlador;

import Modelos.Usuario;
import Servicios.UsuarioServ;
import Util.BaseController;
import io.javalin.Javalin;


import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.*;
public class UsuarioController extends BaseController {
    public UsuarioController(Javalin app) {
        super(app);
    }
    public void aplicarRutas(){

        app.routes(() -> {
            path("", () -> {
                get("/login",ctx -> {
                    ctx.redirect("/Templates/Usuario/Login.html");
                });

                post("/login",ctx -> {
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
                        }else{
                            ctx.redirect("/login");
                        }
                    }
                });

                get("/signup", ctx -> {
                    ctx.redirect("/Templates/Usuario/SignUp.html");
                });

                post("/signup", ctx -> {
                    String usuario = ctx.formParam("usuario");
                    String nombre = ctx.formParam("nombre");
                    String password = ctx.formParam("password");
                    if(UsuarioServ.getInstance().getUsuarioporUsuario(usuario) != null){
                        ctx.redirect("/signup");
                    }else{
                        Usuario user = new Usuario(usuario,nombre,password,"cliente");
                        UsuarioServ.getInstance().crearUsuario(user);
                        ctx.redirect("/login");
                    }
                });

                get("/logout",ctx -> {
                    ctx.sessionAttribute("usuario",null);
                    ctx.sessionAttribute("tc",null);
                    ctx.redirect("/");
                });
            });
        });
    }
}
