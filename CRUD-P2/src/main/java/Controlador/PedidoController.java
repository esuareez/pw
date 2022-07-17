package Controlador;

import Modelos.*;
import Servicios.*;
import Util.BaseController;
import io.javalin.Javalin;
import org.jasypt.util.text.AES256TextEncryptor;

import java.util.ArrayList;
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
                    /*if((ctx.cookie("USESSION") != null && ctx.cookie("UPSESSION") != null) ||
                            (ctx.cookie("USESSION").equalsIgnoreCase("") && ctx.cookie("UPSESSION").equalsIgnoreCase(""))){
                        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
                        textEncryptor.setPassword("encpUss");
                        String name = textEncryptor.decrypt(ctx.cookie("USESSION"));
                        AES256TextEncryptor textEncryptor1 = new AES256TextEncryptor();
                        textEncryptor1.setPassword("encpPss");
                        String pass = textEncryptor1.decrypt(ctx.cookie("UPSESSION"));
                        Usuario u = UsuarioServ.getInstance().getUsuarioporUsuario(name, UsuarioServ.getInstance().findAll());
                        if(u != null){
                            if(u.getPassword().equalsIgnoreCase(pass)){
                                ctx.sessionAttribute("usuario",u);
                            }
                        }
                    }*/
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
                // Ver producto
                get("/producto/view/{id}",ctx -> {
                    Producto producto = ProductoServ.getInstance().find(ctx.pathParamAsClass("id",Integer.class).get());
                    Usuario user = ctx.sessionAttribute("usuario");
                    if(user != null)
                        modelo.put("usuario",user);
                    else
                        modelo.put("usuario","");
                    List<Foto> fotos = FotoServices.getInstancia().findAll();
                    List<Comentario> comentarios = ComentarioServ.getInstance().findAll();
                    for(var item : fotos){
                        if(item.getProducto().getId() == producto.getId()){
                            Foto foto = item;
                            System.out.println(foto.getId()+" "+foto.getProducto().getNombre());
                            modelo.put("foto",foto);
                            break;
                        }
                    }
                    int cantidad = 0;
                    for(var item : comentarios){
                        if(item.getProducto().getId() == producto.getId())
                            cantidad++;
                    }
                    modelo.put("producto",producto);
                    modelo.put("fotos",fotos);
                    modelo.put("carrito",total);
                    modelo.put("comentarios",comentarios);
                    modelo.put("cantidad",cantidad);

                    ctx.render("publico/Templates/Productos/View.html",modelo);
                });
                // Comentar producto
                post("/producto/comentario/{id}", ctx -> {
                    Producto producto = ProductoServ.getInstance().find(ctx.pathParamAsClass("id",Integer.class).get());
                    Usuario user = ctx.sessionAttribute("usuario");
                    if(user == null){
                        ctx.redirect("/login");
                    }
                    String comentario = ctx.formParam("comentario");
                    ComentarioServ.getInstance().crear(new Comentario(producto,user,comentario));
                    ctx.redirect("/user/producto/view/"+producto.getId());
                });

                /*// Pedidos
                get("/mi-carrito", ctx -> {
                    double ptotal = 0;
                    Usuario user = ctx.sessionAttribute("usuario");
                    if(user == null)
                    {
                        ctx.redirect("/");
                    }else{
                        List<Pedido> lista = PedidoServ.getInstance().findAll();
                        Pedido pedido = PedidoServ.getInstance().getPedidoCarroporUsuario(user, lista);
                        if(pedido == null){
                            modelo.put("isEmpty",0);
                            ctx.render("publico/Templates/Pedidos/Carrito.html",modelo);
                        }else{
                            List<ProductoPedido> pp = PedidoServ.getInstance().getProductosDePedido(user);
                            if(pedido != null){
                                for( var item : pp){
                                    if(pedido.getId() == item.getPedido().getId())
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
                    Producto producto = ProductoServ.getInstance().find(ctx.pathParamAsClass("id",Integer.class).get());
                    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
                    PedidoServ.getInstance().addProducto(producto,user,cantidad);
                    ctx.redirect("/");
                });
                post("/edit/{id}",ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    Producto producto = ProductoServ.getInstance().find(ctx.pathParamAsClass("id",Integer.class).get());
                    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
                    PedidoServ.getInstance().editarCarro(producto,user,cantidad);
                    ctx.redirect("/user/mi-carrito");
                });
                get("/remove/{id}", ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    Producto producto = ProductoServ.getInstance().find(ctx.pathParamAsClass("id",Integer.class).get());
                    //ProductoPedidoServ.getInstance().eliminar(producto.getId());
                    PedidoServ.getInstance().removeProducto(producto,user);
                    ctx.redirect("/user/mi-carrito");
                });*/

                get("/procesar", ctx -> {
                    List<ProductoPedido> lista = ctx.sessionAttribute("carrito");
                    Usuario user = ctx.sessionAttribute("usuario");
                    ProductoPedidoServ.getInstance()._completarPedido(lista,user);
                    ctx.sessionAttribute("carrito",null);
                    ctx.redirect("/mi-carrito");
                });

            });
        });
    }
}
