package Controlador;

import Modelos.Usuario;
import Servicios.UsuarioServ;
import Util.BaseController;
import io.javalin.Javalin;
import org.jasypt.util.text.AES256TextEncryptor;


import java.util.List;

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
                    String marca = ctx.formParam("mantener");
                    System.out.println("Marcaaa: "+marca);
                    List<Usuario> lista = UsuarioServ.getInstance().findAll();
                    Usuario user = UsuarioServ.getInstance().getUsuarioporUsuario(usuario, lista);
                    if( user != null) {
                        if(user.getPassword().equals(password)){
                            if(marca != null){
                                AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
                                textEncryptor.setPassword("encpUss");
                                String data = textEncryptor.encrypt(user.getUserName());
                                AES256TextEncryptor textEncryptor1 = new AES256TextEncryptor();
                                textEncryptor1.setPassword("encpPss");
                                String data1 = textEncryptor1.encrypt(user.getPassword());
                                ctx.cookie("USESSION",data,604800);
                                ctx.cookie("UPSESSION",data1,604800);
                            }
                            ctx.sessionAttribute("usuario",user);
                            ctx.cookie("USESSION","",604800);
                            ctx.cookie("UPSESSION","",604800);
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
                    }else{
                        ctx.redirect("/signup");
                    }
                });

                get("/signup", ctx -> {
                    ctx.redirect("/Templates/Usuario/SignUp.html");
                });

                post("/signup", ctx -> {
                    String usuario = ctx.formParam("usuario");
                    String nombre = ctx.formParam("nombre");
                    String password = ctx.formParam("password");
                    List<Usuario> lista = UsuarioServ.getInstance().findAll();
                    Usuario user = UsuarioServ.getInstance().getUsuarioporUsuario(usuario, lista);
                    if(user != null){
                        ctx.redirect("/signup");
                    }else{
                        UsuarioServ.getInstance().crear(new Usuario(usuario,nombre,password,"cliente"));
                        ctx.redirect("/login");
                    }
                });

                get("/logout",ctx -> {
                    ctx.sessionAttribute("usuario",null);
                    ctx.removeCookie("USESSION");
                    ctx.removeCookie("UPSESSION");
                    ctx.sessionAttribute("tc",null);
                    ctx.redirect("/");
                });
            });
        });
    }
}
