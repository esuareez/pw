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

public class PedidoServ extends GestionDb<Pedido> {
    private static PedidoServ instancia;
    private List<Pedido> pedidoList = new ArrayList<>();

    public static PedidoServ getInstance(){
        if(instancia==null){
            instancia = new PedidoServ();
        }
        return instancia;
    }

    public PedidoServ() {
        super(Pedido.class);
    }

    public Pedido getPedidoCarroporUsuario(Usuario usuario, List<Pedido> pedList){
        return pedList.stream().filter(e -> e.getUsuario().getId() == usuario.getId() && e.getEstado() == 1).findFirst().orElse(null);
    }

    public List<ProductoPedido> getProductosDePedido(Usuario usuario){
        Pedido aux = getPedidoCarroporUsuario(usuario,findAll());
        List<ProductoPedido> lista = new ArrayList<>();
        for(var productoped : ProductoPedidoServ.getInstance().findAll()){
            if(productoped.getPedido().getId() == aux.getId())
                lista.add(productoped);
        }

        for(var item : lista)
            System.out.println(item.getProducto().getNombre());
        return lista;
    }

    public Pedido completarPedido(Pedido p){
        Pedido pedido = find(p.getId());
        double total = 0;
        if(pedido == null)
        {
            System.out.println("El pedido no existe.");
            return null;
        }
        pedido.setEstado(2);
        for( var item : ProductoPedidoServ.getInstance().findAll()){
            if(item.getPedido().getId() == pedido.getId()){
                total += (item.getProducto().getPrecio() * item.getCantidad());
                item.getProducto().setCantidad((item.getProducto().getCantidad()-item.getCantidad()));
                ProductoServ.getInstance().editar(item.getProducto());
            }
        }
        pedido.setTotal(total);
        editar(pedido);
        return pedido;
    }
    /*

    ProductoPedido

     */

    public void addProducto(Producto producto, Usuario usuario, int cantidad) {
        Pedido pedido = getPedidoCarroporUsuario(usuario,findAll());

       if (pedido == null) {
           Pedido p = crear(new Pedido(usuario));
           ProductoPedidoServ.getInstance().crear(new ProductoPedido(p,producto, cantidad));
        } else {
           System.out.println(pedido.getId()+" "+pedido.getEstado()+" ");
            boolean tieneProducto = false;
            for(var product : ProductoPedidoServ.getInstance().findAll()){
                if(product.getProducto().getId() == producto.getId() && product.getProducto().getCantidad() >= product.getCantidad() && product.getPedido().getUsuario().getId() == usuario.getId()
                && product.getPedido().getEstado() == 1){
                    if(product.getCantidad()+cantidad <= product.getProducto().getCantidad()){
                        product.setCantidad(product.getCantidad()+cantidad);
                        ProductoPedidoServ.getInstance().editar(product);
                    }
                    tieneProducto = true;
                }
            }
            if(tieneProducto == false){
                ProductoPedidoServ.getInstance().crear(new ProductoPedido(pedido,producto,cantidad));
            }
        }
    }

    public void editarCarro(Producto producto, Usuario usuario, int cantidad){
        Pedido pedido = getPedidoCarroporUsuario(usuario,findAll());

        for(var product : ProductoPedidoServ.getInstance().findAll()){
            if(product.getProducto().getId() == producto.getId() && pedido.getEstado() == 1
                    && product.getPedido().getId() == pedido.getId()){
                product.setCantidad(cantidad);
                ProductoPedidoServ.getInstance().editar(product);
            }
        }

    }

    public void removeProducto(Producto producto, Usuario usuario){
       Pedido pedido = getPedidoCarroporUsuario(usuario, findAll());
        if(pedido != null)
        {
           for(var item : ProductoPedidoServ.getInstance().findAll()){
               if(item.getProducto().getId() == producto.getId() && item.getPedido().getUsuario().getId() == usuario.getId()){
                   ProductoPedidoServ.getInstance().eliminar(item.getId());
                   break;
               }
           }
        }
    }

    public int getTotalProductosenCarrito(Usuario usuario){
        int total = 0;
        Pedido pedido = getPedidoCarroporUsuario(usuario,findAll());
        if(pedido == null)
            return total;

        for(var item : ProductoPedidoServ.getInstance().findAll()){
            if(item.getPedido().getId() == pedido.getId())
                total += item.getCantidad();
        }
        return total;
    }

}

