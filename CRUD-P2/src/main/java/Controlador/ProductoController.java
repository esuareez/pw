package Controlador;

import Modelos.Producto;
import Modelos.Usuario;
import Servicios.ProductoServ;
import Util.BaseController;
import io.javalin.Javalin;
import ognl.ObjectElementsAccessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController extends BaseController {

    public ProductoController(Javalin app) {
        super(app);
    }

    public void aplicarRutas(){
        app.before("/*",ctx -> {

        });
        app.get("/",ctx -> {
            Usuario user = ctx.sessionAttribute("usuario");
            Map<String,Object> modelo = new HashMap<>();
            if(user == null || user.getRol().equalsIgnoreCase("cliente")){
                modelo.put("rol","cliente");
            }else{
                if(user.getRol().equalsIgnoreCase("admin")){
                    modelo.put("rol","admin");
                }
            }
            List<Producto> productoList = ProductoServ.getInstance().getProductoList();
            modelo.put("productos",productoList);
            ctx.render("publico/Templates/Productos/index.html",modelo);
        });

        app.get("/crearProducto",ctx -> {
            Usuario user = ctx.sessionAttribute("usuario");
            Map<String,Object> modelo = new HashMap<>();
            if(user == null || user.getRol().equalsIgnoreCase("cliente")){
                modelo.put("rol","cliente");
            }else{
                if(user.getRol().equalsIgnoreCase("admin")){
                    modelo.put("rol","admin");
                }
            }
            ctx.render("publico/Templates/Productos/crearProducto.html",modelo);
        });

        app.post("/crearProducto", ctx -> {
            String nombre = ctx.formParam("nombre");
            int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
            double precio = Double.parseDouble(ctx.formParam("precio"));
            String descripcion = ctx.formParam("descripcion");
            int estado = Integer.parseInt(ctx.formParam("estado"));
            Producto producto = new Producto(nombre,cantidad,precio,descripcion,estado);
            ProductoServ.getInstance().crearProducto(producto);
            ctx.redirect("/");
        });

        app.get("/inventario",ctx -> {
            Usuario user = ctx.sessionAttribute("usuario");
            Map<String,Object> modelo = new HashMap<>();
            if(user == null || user.getRol().equalsIgnoreCase("cliente")){
                modelo.put("rol","cliente");
            }else{
                if(user.getRol().equalsIgnoreCase("admin")){
                    modelo.put("rol","admin");
                }
            }
            List<Producto> productoList = ProductoServ.getInstance().getProductoList();
            modelo.put("productos",productoList);
            ctx.render("publico/Templates/Productos/Inventario.html", modelo);
        });

        app.get("/producto/editar/{id}",ctx -> {
            Usuario user = ctx.sessionAttribute("usuario");
            Map<String,Object> modelo = new HashMap<>();
            if(user == null || user.getRol().equalsIgnoreCase("cliente")){
                modelo.put("rol","cliente");
            }else{
                if(user.getRol().equalsIgnoreCase("admin")){
                    modelo.put("rol","admin");
                }
            }
            Producto producto = ProductoServ.getInstance().getProductoporID(ctx.pathParamAsClass("id",Integer.class).get());
            System.out.println(producto.getEstado());
            if(producto == null)
                ctx.redirect("/inventario");
            modelo.put("producto",producto);
            ctx.render("publico/Templates/Productos/editarProducto.html",modelo);
        });

        app.post("/editarProducto",ctx -> {
            int id = Integer.parseInt(ctx.formParam("id"));
            String nombre = ctx.formParam("nombre");
            int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
            double precio = Double.parseDouble(ctx.formParam("precio"));
            String descripcion = ctx.formParam("descripcion");
            int estado = Integer.parseInt(ctx.formParam("estado"));
            System.out.println(estado);
            Producto producto = new Producto(id,nombre,cantidad,precio,descripcion,estado);
            ProductoServ.getInstance().editarProducto(producto);
            ctx.redirect("/inventario");
        });

        app.get("/producto/eliminar/{id}",ctx -> {
            Usuario user = ctx.sessionAttribute("usuario");
            Map<String,Object> modelo = new HashMap<>();
            if(user == null || user.getRol().equalsIgnoreCase("cliente")){
                modelo.put("rol","cliente");
            }else{
                if(user.getRol().equalsIgnoreCase("admin")){
                    modelo.put("rol","admin");
                }
            }
            Producto producto = ProductoServ.getInstance().getProductoporID(ctx.pathParamAsClass("id",Integer.class).get());
            if(producto == null)
                ctx.redirect("/inventario");
            ProductoServ.getInstance().deleteProducto(producto);
            ctx.render("/inventario",modelo);
        });
    }
}
