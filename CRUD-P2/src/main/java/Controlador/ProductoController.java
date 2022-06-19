package Controlador;

import Modelos.Pedido;
import Modelos.Producto;
import Modelos.ProductoPedido;
import Modelos.Usuario;
import Servicios.PedidoServ;
import Servicios.ProductoServ;
import Servicios.UsuarioServ;
import Util.BaseController;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

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
                        total = 0;
                        modelo.put("isLogin",0);
                        modelo.put("rol",null);
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
                        ctx.sessionAttribute("tc",total);
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
                        total = 0;
                        modelo.put("isLogin",0);
                        ctx.redirect("/login");
                    }
                });
                get("/crearProducto",ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    modelo.put("carrito",total);
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
                    modelo.put("carrito",total);
                    ctx.render("publico/Templates/Productos/Inventario.html", modelo);
                });
                get("/producto/editar/{id}",ctx -> {
                    Producto producto = ProductoServ.getInstance().getProductoporID(ctx.pathParamAsClass("id",Integer.class).get());
                    if(producto == null)
                        ctx.redirect("/admin/inventario");
                    modelo.put("producto",producto);
                    modelo.put("carrito",total);
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
                    ctx.redirect("/admin/inventario");
                });
                get("/producto/eliminar/{id}",ctx -> {
                    Producto producto = ProductoServ.getInstance().getProductoporID(ctx.pathParamAsClass("id",Integer.class).get());
                    if(producto == null)
                        ctx.redirect("/admin/inventario");
                    ProductoServ.getInstance().deleteProducto(producto);
                    ctx.redirect("/admin/inventario");
                });

                get("/pedidos",ctx ->{
                    List<Pedido> pedidoList = PedidoServ.getInstance().getPedidoList();
                    modelo.put("pedidos",pedidoList);
                    modelo.put("carrito",total);
                    ctx.render("publico/Templates/Pedidos/Historial.html",modelo);
                });

                get("/pedido/{id}", ctx -> {
                    Pedido pedido = PedidoServ.getInstance().getPedidoporId(ctx.pathParamAsClass("id",Integer.class).get());
                    List<ProductoPedido> pp = pedido.getProductoPedido();
                    modelo.put("productos",pp);
                    modelo.put("carrito",total);
                    ctx.render("publico/Templates/Pedidos/DetallesProducto.html",modelo);
                });

                get("/lista-usuarios",ctx -> {
                    List<Usuario> usuarios = UsuarioServ.getInstance().getUsuarioList();
                    modelo.put("usuarios",usuarios);
                    ctx.render("publico/Templates/Usuario/ListaUsuario.html",modelo);
                });
            });
        });
    }
}
