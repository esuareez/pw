package Controlador;

import Modelos.Producto;
import Modelos.ProductoPedido;
import Modelos.Usuario;
import Servicios.PedidoServ;
import Servicios.ProductoServ;
import Servicios.UsuarioServ;
import Util.BaseController;
import io.javalin.Javalin;
import ognl.ObjectElementsAccessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class ProductoController extends BaseController {

    public ProductoController(Javalin app) {
        super(app);
    }
    private int total;
    public void aplicarRutas(){
        app.routes(() -> {
            Map<String,Object> modelo = new HashMap<>();
            path("/", () ->{
                before(ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    if(user != null){
                        total = PedidoServ.getInstance().getTotalProductosenCarrito(user); // total en carrito
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
                    }
                });
                get("/",ctx -> {
                    List<Producto> productoList = ProductoServ.getInstance().getProductoList();
                    modelo.put("productos",productoList);
                    modelo.put("carrito",total);
                    ctx.render("publico/Templates/Productos/index.html",modelo);
                });
            });
            path("/admin/", () -> {
                before(ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    if(user != null){
                        total = PedidoServ.getInstance().getTotalProductosenCarrito(user); // total en carrito
                        modelo.put("isLogin",1);
                        modelo.put("usuario",user.getNombre());
                        if(user.getRol().equalsIgnoreCase("cliente")){
                            ctx.redirect("/");
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
                get("/crearProducto",ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    ctx.render("publico/Templates/Productos/crearProducto.html",modelo);
                });
                post("/crearProducto", ctx -> {
                    String nombre = ctx.formParam("nombre");
                    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
                    double precio = Double.parseDouble(ctx.formParam("precio"));
                    String descripcion = ctx.formParam("descripcion");
                    int estado = Integer.parseInt(ctx.formParam("estado"));
                    Producto producto = new Producto(nombre,cantidad,precio,descripcion,estado);
                    ProductoServ.getInstance().crearProducto(producto);
                    ctx.redirect("/");
                });
                get("/inventario",ctx -> {
                    List<Producto> productoList = ProductoServ.getInstance().getProductoList();
                    modelo.put("productos",productoList);
                    ctx.render("publico/Templates/Productos/Inventario.html", modelo);
                });
                get("/producto/editar/{id}",ctx -> {
                    Producto producto = ProductoServ.getInstance().getProductoporID(ctx.pathParamAsClass("id",Integer.class).get());
                    if(producto == null)
                        ctx.redirect("/inventario");
                    modelo.put("producto",producto);
                    ctx.render("publico/Templates/Productos/editarProducto.html",modelo);
                });
                post("/editarProducto",ctx -> {
                    int id = Integer.parseInt(ctx.formParam("id"));
                    String nombre = ctx.formParam("nombre");
                    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
                    double precio = Double.parseDouble(ctx.formParam("precio"));
                    String descripcion = ctx.formParam("descripcion");
                    int estado = Integer.parseInt(ctx.formParam("estado"));
                    Producto producto = new Producto(id,nombre,cantidad,precio,descripcion,estado);
                    ProductoServ.getInstance().editarProducto(producto);
                    ctx.redirect("/inventario");
                });
                get("/producto/eliminar/{id}",ctx -> {
                    Producto producto = ProductoServ.getInstance().getProductoporID(ctx.pathParamAsClass("id",Integer.class).get());
                    if(producto == null)
                        ctx.redirect("/inventario");
                    ProductoServ.getInstance().deleteProducto(producto);
                    ctx.render("/inventario",modelo);
                });
            });
        });
    }
}
