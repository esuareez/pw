package Controlador;

import Modelos.Pedido;
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
                    if(user != null){
                        int total = PedidoServ.getInstance().getTotalProductosenCarrito(user); // total en carrito
                        ctx.sessionAttribute("tc",total);
                        modelo.put("isLogin",1);
                        modelo.put("usuario",user.getNombre());
                        if(user.getRol().equalsIgnoreCase("cliente")){
                            modelo.put("rol","cliente");
                        }else{
                            if(user.getRol().equalsIgnoreCase("admin")){
                                modelo.put("rol","admin");
                            }
                        }
                    }else{
                        modelo.put("isLogin",0);
                        ctx.redirect("/login");
                    }
                });

                get("/mi-carrito", ctx -> {
                    double ptotal = 0;
                    Usuario user = ctx.sessionAttribute("usuario");
                    if(user == null)
                    {
                        ctx.redirect("/");
                    }else{
                        Pedido pedido = PedidoServ.getInstance().getPedidoCarroporUsuario(user);
                        if(pedido == null){
                            modelo.put("isEmpty",0);
                            ctx.render("publico/Templates/Pedidos/Carrito.html",modelo);
                        }else{
                            //List<ProductoPedido> pp = pedido.getProductoPedido();
                            List<ProductoPedido> pp = null;
                            int id = 0;
                            if(pedido != null){
                                for( var item : pp){
                                        ptotal += (item.getProducto().getPrecio() * item.getCantidad());
                                }
                                modelo.put("isEmpty",1);
                                modelo.put("total",ptotal);
                                modelo.put("carrito",pp);
                                modelo.put("pedido",pedido.getId());
                                ctx.render("publico/Templates/Pedidos/Carrito.html",modelo);
                            }else{
                                modelo.put("isEmpty",0);
                                ctx.render("publico/Templates/Pedidos/Carrito.html",modelo);
                            }
                        }
                    }
                });
                post("/add/{id}",ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    Producto producto = ProductoServ.getInstance().getProductoporID(ctx.pathParamAsClass("id",Integer.class).get());
                    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
                    PedidoServ.getInstance().addProducto(producto,user,cantidad);
                    ctx.redirect("/");
                });
                post("/edit/{id}",ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    Producto producto = ProductoServ.getInstance().getProductoporID(ctx.pathParamAsClass("id",Integer.class).get());
                    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
                    PedidoServ.getInstance().editarCarro(producto,user,cantidad);
                    ctx.redirect("/user/mi-carrito");
                });
                get("/remove/{id}", ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    Producto producto = ProductoServ.getInstance().getProductoporID(ctx.pathParamAsClass("id",Integer.class).get());
                    PedidoServ.getInstance().removeProducto(producto,user);
                    ctx.redirect("/user/mi-carrito");
                });

                get("/procesar/{id}", ctx -> {
                    Pedido pedido = PedidoServ.getInstance().getPedidoporId(ctx.pathParamAsClass("id",Integer.class).get());
                    if(pedido == null || pedido.getEstado() == 2){
                        ctx.redirect("/");
                    }else{
                        PedidoServ.getInstance().completarPedido(pedido);
                        ctx.redirect("/user/mi-carrito");
                    }
                });

            });
        });
    }
}
