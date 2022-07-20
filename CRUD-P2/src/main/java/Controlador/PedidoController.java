package Controlador;

import Modelos.*;
import Servicios.*;
import Util.BaseController;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.before;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.path;

public class PedidoController extends BaseController {
    public PedidoController(Javalin app) {
        super(app);
    }
    private int total;

    public void aplicarRutas(){
        app.routes(() -> {
            path("/user/", () -> {
                Map<String,Object> modelo = new HashMap<>();
                before(ctx -> {

                    Usuario user = ctx.sessionAttribute("usuario");
                    if (user != null) {
                        total = PedidoServ.getInstance().getTotalProductosenCarrito(user); // total en carrito
                        ctx.sessionAttribute("tc", total);
                        modelo.put("isLogin", 1);
                        modelo.put("usuario", user.getNombre());
                        if (user.getRol().equalsIgnoreCase("cliente")) {
                            modelo.put("rol", "cliente");
                        } else {
                            if (user.getRol().equalsIgnoreCase("admin")) {
                                modelo.put("rol", "admin");
                            }
                        }
                    } else {
                            modelo.put("isLogin", 0);
                            ctx.redirect("/login");
                    }

                });

                // Comentar producto
                post("/producto/comentario/{id}", ctx -> {
                    Producto producto = ProductoServ.getInstance().find(ctx.pathParamAsClass("id",Integer.class).get());
                    Usuario user = ctx.sessionAttribute("usuario");
                    if(user == null || user.equals("")){
                        ctx.redirect("/login");
                    }
                    String comentario = ctx.formParam("comentario");
                    ComentarioServ.getInstance().crear(new Comentario(producto,user,comentario));
                    ctx.redirect("/producto/view/"+producto.getId());
                });


                get("/procesar", ctx -> {
                    List<ProductoPedido> lista = ctx.sessionAttribute("carrito");
                    Usuario user = ctx.sessionAttribute("usuario");
                    ProductoPedidoServ.getInstance()._completarPedido(lista,user);
                    lista = null;
                    ctx.sessionAttribute("carrito",lista);
                    ctx.redirect("/mi-carrito");
                });

            });
        });
    }
}
