package Controlador;

import Modelos.Producto;
import Modelos.ProductoPedido;
import Modelos.Usuario;
import Servicios.PedidoServ;
import Servicios.ProductoServ;
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

    public void aplicarRutas(){
        app.routes(() -> {
            path("/user/", () -> {
                Map<String,Object> modelo = new HashMap<>();
                before(ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    if(user == null || user.getRol().equalsIgnoreCase("cliente")){
                        modelo.put("rol","cliente");
                    }else{
                        if(user.getRol().equalsIgnoreCase("admin")){
                            modelo.put("rol","admin");
                        }
                    }
                    int total = PedidoServ.getInstance().getTotalProductosenCarrito(user);
                    ctx.sessionAttribute("tc",total);
                });
                get("/mi-carrito", ctx -> {
                    double ptotal = 0;
                    Usuario user = ctx.sessionAttribute("usuario");
                    if(user == null)
                    {
                        ctx.redirect("/");
                    }else{
                        List<ProductoPedido> pp = PedidoServ.getInstance().getProductosPedido(user);
                        for( var item : pp){
                            ptotal += (item.getProducto().getPrecio() * item.getCantidad());
                        }
                        if(pp == null){
                            ctx.redirect("/");
                        }else{
                            modelo.put("total",ptotal);
                            modelo.put("carrito",pp);
                            ctx.render("publico/Templates/Pedidos/Carrito.html",modelo);
                        }
                    }
                });
                get("/add/{id}",ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    Producto producto = ProductoServ.getInstance().getProductoporID(ctx.pathParamAsClass("id",Integer.class).get());
                    PedidoServ.getInstance().addProducto(producto,user);
                    ctx.redirect("/");
                });
                get("/remove/{id}", ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    Producto producto = ProductoServ.getInstance().getProductoporID(ctx.pathParamAsClass("id",Integer.class).get());
                    PedidoServ.getInstance().removeProducto(producto,user);
                    ctx.redirect("/user/mi-carrito");
                });

            });
        });
    }
}