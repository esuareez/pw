package Servicios;

import Controlador.ProductoController;
import Modelos.Pedido;
import Modelos.Producto;
import Modelos.ProductoPedido;
import Modelos.Usuario;

import java.sql.Date;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PedidoServ {
    private static PedidoServ instancia;
    private List<Pedido> pedidoList = new ArrayList<>();

    public static PedidoServ getInstance(){
        if(instancia==null){
            instancia = new PedidoServ();
        }
        return instancia;
    }

    public PedidoServ() {
    }

    public Pedido getPedidoporId(int id){
        return pedidoList.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    public Pedido getPedidoporUsuario(Usuario usuario){
        return pedidoList.stream().filter(e -> e.getUsuario().getId() == usuario.getId()).findFirst().orElse(null);
    }

    public Pedido getPedidoCarroporUsuario(Usuario usuario){
        return pedidoList.stream().filter(e -> e.getUsuario().getId() == usuario.getId() && e.getEstado() == 1).findFirst().orElse(null);
    }
    /*
    Pedido
     */
    public Pedido crearPedido(Pedido pedido){
        int idGenerado = makeId();
        while(getPedidoporId(idGenerado) != null)
            idGenerado = makeId();
        pedido.setId(idGenerado);
        pedidoList.add(pedido);
        return pedido;
    }

    public Pedido deletePedido(Pedido p)
    {
        Pedido pedido = getPedidoporId(p.getId());
        if(pedido == null)
        {
            System.out.println("El pedido no existe.");
            return null;
        }
        pedidoList.remove(pedido);
        return pedido;
    }
    public Pedido completarPedido(Pedido p){
        Pedido pedido = getPedidoporId(p.getId());
        double total = 0;
        if(pedido == null)
        {
            System.out.println("El pedido no existe.");
            return null;
        }
        pedido.setEstado(2);
        for( var item : pedido.getProductoPedido()){
            total += (item.getProducto().getPrecio() * item.getCantidad());
            item.getProducto().setCantidad((item.getProducto().getCantidad()-item.getCantidad()));
        }
        pedido.setTotal(total);
        return pedido;
    }
    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    /*

    ProductoPedido

     */

    public void addProducto(Producto producto, Usuario usuario) {
        Pedido pedido = getPedidoCarroporUsuario(usuario);
        if (pedido == null) {
            List<ProductoPedido> productos = new ArrayList<>();
            ProductoPedido product = new ProductoPedido(producto, 1);
            productos.add(product);
            Pedido p = new Pedido(usuario, productos);
            crearPedido(p);
        } else {
            boolean tieneProducto = false;
            for(var product : pedido.getProductoPedido()){
                if(product.getProducto().getId() == producto.getId() && product.getProducto().getCantidad() >= product.getCantidad()){
                    product.setCantidad(product.getCantidad()+1);
                    tieneProducto = true;
                }
            }
            if(tieneProducto == false){
                ProductoPedido pp = new ProductoPedido(producto,1);
                pedido.getProductoPedido().add(pp);
            }
        }
    }

    public void removeProducto(Producto producto, Usuario usuario){
        Pedido pedido = getPedidoCarroporUsuario(usuario);
        if(pedido != null)
        {
           for(var item : pedido.getProductoPedido()){
               if(item.getProducto().getId() == producto.getId()){
                   pedido.getProductoPedido().remove(item);
                   break;
               }
           }
        }
    }

    public int getTotalProductosenCarrito(Usuario usuario){
        int total = 0;
        Pedido pedido = getPedidoCarroporUsuario(usuario);
        if(pedido == null)
            return total;

        if(pedido.getProductoPedido().size() == 0)
            return total;

        for(var item : pedido.getProductoPedido()){
                total += item.getCantidad();
        }
        return total;
    }

    public int makeId(){
        int num, min = 100000, max = 999999;
        num = (int)Math.floor(Math.random()*(max-min+1)+min);
        while(getPedidoporId(num) != null)
        {
            num = (int)Math.floor(Math.random()*(max-min+1)+min);
        }
        return num;
    }
}
