package Main;

import Servicios.PedidoServ;
import Servicios.ProductoServ;
import Modelos.Producto;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create(config ->{
            config.addStaticFiles(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/publico";
                staticFileConfig.location = Location.CLASSPATH;
            });
            //config.registerPlugin(new RouteOverviewPlugin("/rutas")); //aplicando plugins de las rutas
            //config.enableCorsForAllOrigins();
        });
        app.start(8000);

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

        /*System.out.println("Productos registrados.");
        for(var item :ProductoServ.getInstance().getProductoList())
        {
            System.out.println(item.getNombre()+" "+item.getPrecio()+" "+item.getEstado()+" "+item.getDescripcion());
        }
        System.out.println("\nProductos luego de editar.");
        Producto producto = new Producto(2,"Winasorb Ultra",50,35,"Dolor del cuerpo.",0);
        ProductoServ.getInstance().editarProducto(producto);
        for(var item :ProductoServ.getInstance().getProductoList())
        {
            System.out.println(item.getNombre()+" "+item.getPrecio()+" "+item.getEstado()+" "+item.getDescripcion());
        }
        System.out.println("\nProductos actuales.");
        ProductoServ.getInstance().deleteProducto(producto);
        for(var item :ProductoServ.getInstance().getProductoList())
        {
            if(item.getEstado()>0)
                System.out.println(item.getNombre()+" "+item.getPrecio()+" "+item.getEstado()+" "+item.getDescripcion());
        }
        System.out.println("\nProductos en el pedido");
        for(var item: PedidoServ.getInstance().getPedidoList()){
            System.out.println(item.getId());
            for(var product : item.getProductoPedido())
            {
                System.out.println("Producto: "+product.getProducto().getNombre()+" Cantidad en el pedido:"+
                        product.getCantidad()+" Id del pedido:"+product.getPedido().getId());
            }
            System.out.println(item.getFecha()+"\n"+item.getNombre());
        }*/
    }
}
