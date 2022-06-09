package Controlador;

import Modelos.Producto;
import Servicios.ProductoServ;
import Util.BaseController;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController extends BaseController {

    public ProductoController(Javalin app) {
        super(app);
    }

    public void aplicarRutas(){
        app.get("/",ctx -> {
            List<Producto> productoList = ProductoServ.getInstance().getProductoList();
            Map<String,Object> modelo = new HashMap<>();
            modelo.put("productos",productoList);
            ctx.render("publico/index.html",modelo);
        });

        app.get("/crearProducto",ctx -> {
            ctx.redirect("/crearProducto.html");
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
            List<Producto> productoList = ProductoServ.getInstance().getProductoList();
            Map<String,Object> modelo = new HashMap<>();
            modelo.put("productos",productoList);
            ctx.render("publico/Inventario.html", modelo);
        });

        app.get("/producto/editar/{id}",ctx -> {
            Producto producto = ProductoServ.getInstance().getProductoporID(ctx.pathParamAsClass("id",Integer.class).get());
            if(producto == null)
                ctx.redirect("/inventario");
            Map<String,Object> modelo = new HashMap<>();
            modelo.put("producto",producto);
            ctx.render("publico/editarProducto.html",modelo);
        });

        app.post("/editarProducto",ctx -> {
            int id = Integer.parseInt(ctx.formParam("id"));
            String nombre = ctx.formParam("nombre");
            int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
            System.out.println(cantidad);
            double precio = Double.parseDouble(ctx.formParam("precio"));
            String descripcion = ctx.formParam("descripcion");
            int estado = Integer.parseInt(ctx.formParam("estado"));
            Producto producto = new Producto(id,nombre,cantidad,precio,descripcion,estado);
            ProductoServ.getInstance().editarProducto(producto);
            Producto p = ProductoServ.getInstance().editarProducto(producto);
            System.out.println(p.getCantidad());
            ctx.redirect("/inventario");
        });

        app.get("/producto/eliminar/{id}",ctx -> {
            Producto producto = ProductoServ.getInstance().getProductoporID(ctx.pathParamAsClass("id",Integer.class).get());
            if(producto == null)
                ctx.redirect("/inventario");
            ProductoServ.getInstance().deleteProducto(producto);
            ctx.redirect("/inventario");
        });
    }
}
