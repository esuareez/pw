package Controlador;

import Modelos.*;
import Servicios.*;
import Util.BaseController;
import io.javalin.Javalin;

import java.io.IOException;
import java.util.Base64;
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
                    if(ctx.cookie("USESSION") != null && ctx.cookie("UPSESSION") != null){
                        Usuario u = UsuarioServ.getInstance().getUsuarioporUsuario(ctx.cookie("USESSION"), UsuarioServ.getInstance().findAll());
                        if(u != null){
                            if(u.getPassword().equalsIgnoreCase(ctx.cookie("UPSESSION"))){
                                ctx.sessionAttribute("usuario",u);
                            }
                        }

                    }
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
                    List<Producto> productoList = ProductoServ.getInstance().findAll();
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
                //Eliminar comentario (Lado Admin)
                get("/producto/{idp}/comentario/eliminar/{id}",ctx -> {
                    Producto producto = ProductoServ.getInstance().find(ctx.pathParamAsClass("idp",Integer.class).get());
                    ComentarioServ.getInstance().eliminar(Integer.parseInt(ctx.pathParam("id")));
                    ctx.redirect("/user/producto/view/"+producto.getId());
                });

                //Productos
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
                    List<Producto> lista = ProductoServ.getInstance().findAll();
                    Producto producto = ProductoServ.getInstance().getProductoporNombre(nombre,lista);
                    if(producto != null)
                    {
                        ctx.redirect("/crearProducto");
                    }else{
                        Producto p = ProductoServ.getInstance().crear(new Producto(nombre,cantidad,precio,descripcion,estado));
                        ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                            try {
                                byte[] bytes = uploadedFile.getContent().readAllBytes();
                                String encodedString = Base64.getEncoder().encodeToString(bytes);
                                Foto foto = new Foto(p,uploadedFile.getFilename(), uploadedFile.getContentType(), encodedString);
                                FotoServices.getInstancia().crear(foto);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        ctx.redirect("/admin/inventario");
                    }
                    //ProductoServ.getInstance().crearProducto(producto);

                });
                get("/inventario",ctx -> {
                    List<Producto> productoList = ProductoServ.getInstance().findAll();
                    modelo.put("productos",productoList);
                    modelo.put("carrito",total);
                    ctx.render("publico/Templates/Productos/Inventario.html", modelo);
                });
                get("/producto/editar/{id}",ctx -> {
                    Producto producto = ProductoServ.getInstance().find(ctx.pathParamAsClass("id",Integer.class).get());
                    List<Foto> fotos = FotoServices.getInstancia().findAll();
                    boolean tiene = false;
                    if(producto == null)
                        ctx.redirect("/admin/inventario");
                    for(var item : fotos){
                        if(item.getProducto().getId() == producto.getId())
                            tiene = true;
                    }
                    if(!tiene)
                        modelo.put("img",0);
                    else
                        modelo.put("img",1);
                    modelo.put("producto",producto);
                    modelo.put("fotos",fotos);
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
                    ProductoServ.getInstance().editar(producto);
                    ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                        try {
                            byte[] bytes = uploadedFile.getContent().readAllBytes();
                            String encodedString = Base64.getEncoder().encodeToString(bytes);
                            if(uploadedFile.getFilename().equalsIgnoreCase("") || uploadedFile.getFilename() == null){

                            }else{
                                Foto foto = new Foto(producto,uploadedFile.getFilename(), uploadedFile.getContentType(), encodedString);
                                FotoServices.getInstancia().crear(foto);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    ctx.redirect("/admin/inventario");
                });
                get("/producto/eliminar/{idp}/imagen/{id}", ctx -> {
                    int idproducto = Integer.parseInt(ctx.pathParam("idp"));
                    try {
                        Foto foto = FotoServices.getInstancia().find(ctx.pathParamAsClass("id", Long.class).get());
                        if(foto!=null){
                            FotoServices.getInstancia().eliminar(foto.getId());
                        }
                    }catch (Exception e){
                        System.out.println("Error: "+e.getMessage());
                    }
                   ctx.redirect("/admin/producto/editar/"+idproducto);
                    //ctx.redirect("/admin/inventario");
                });
                get("/producto/eliminar/{id}",ctx -> {
                    Producto producto = ProductoServ.getInstance().find(ctx.pathParamAsClass("id",Integer.class).get());
                    if(producto == null)
                        ctx.redirect("/admin/inventario");
                    ProductoServ.getInstance().deleteProducto(producto);
                    ctx.redirect("/admin/inventario");
                });

                get("/pedidos",ctx ->{
                    List<Pedido> pedidoList = PedidoServ.getInstance().findAll();
                    modelo.put("pedidos",pedidoList);
                    modelo.put("carrito",total);
                    ctx.render("publico/Templates/Pedidos/Historial.html",modelo);
                });

                get("/pedido/{id}", ctx -> {
                    Pedido pedido = PedidoServ.getInstance().find(ctx.pathParamAsClass("id",Integer.class).get());
                    List<ProductoPedido> pp = ProductoPedidoServ.getInstance().findAll();
                    modelo.put("productos",pp);
                    modelo.put("pedido",pedido);
                    modelo.put("carrito",total);
                    ctx.render("publico/Templates/Pedidos/DetallesProducto.html",modelo);
                });

                get("/lista-usuarios",ctx -> {
                    List<Usuario> usuarios = UsuarioServ.getInstance().findAll();
                    modelo.put("usuarios",usuarios);
                    ctx.render("publico/Templates/Usuario/ListaUsuario.html",modelo);
                });
            });
        });
    }
}
