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
    private List<ProductoPedido> productoPedidoList = new ArrayList<>();

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
    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    /*
    ProductoPedido
     */

    public ProductoPedido getProductoPedidoporProducto(Producto producto){
        return productoPedidoList.stream().filter(e -> e.getProducto().getId() == producto.getId()).findFirst().orElse(null);
    }
    public ProductoPedido getProductoPedidoporPedido(Pedido pedido){
        return productoPedidoList.stream().filter(e -> e.getPedido().getId() == pedido.getId()).findFirst().orElse(null);
    }
    public List<ProductoPedido> getProductoPedidoList() {
        return productoPedidoList;
    }

    public List<ProductoPedido> getProductosPedido(Usuario usuario){
        List<ProductoPedido> lista = new ArrayList<>();
        Pedido pedido = getPedidoporUsuario(usuario);
        if(pedido == null)
            return null;
        for(var item : productoPedidoList){
            if(item.getPedido().getId() == pedido.getId()){
                lista.add(item);
            }
        }
        return lista;
    }

    public ProductoPedido addProducto(Producto producto, Usuario usuario){
        Pedido pedido = getPedidoporUsuario(usuario);
        if(pedido == null || pedido.getEstado() == 2)
        {
            pedido = new Pedido(usuario);
            crearPedido(pedido);
        }
        ProductoPedido pp = getProductoPedidoporPedido(pedido);
        if(pp == null || getProductoPedidoporProducto(pp.getProducto()) == null){
            pp = new ProductoPedido(pedido,producto,1);
            productoPedidoList.add(pp);
        }else{
            if(getProductoPedidoporProducto(pp.getProducto()) != null && pp.getProducto().getCantidad() > pp.getCantidad())
                pp.setCantidad(pp.getCantidad()+1);

        }
        return pp;
    }

    public void removeProducto(Producto producto, Usuario usuario){
        Pedido pedido = getPedidoporUsuario(usuario);
        if(pedido != null && pedido.getEstado() == 1)
        {
           for(var item : getProductoPedidoList()){
               if(item.getPedido().getId() == pedido.getId() && item.getProducto().getId() == producto.getId()){
                   productoPedidoList.remove(item);
                   break;
               }
           }
        }
    }

    public int getTotalProductosenCarrito(Usuario usuario){
        int total = 0;
        Pedido pedido = null;
        for(var item : getPedidoList()) {
            if(item.getUsuario().getId() == usuario.getId() && item.getEstado() == 1){
                pedido = item;
            }
        }
        ProductoPedido pp = getProductoPedidoporPedido(pedido);
        if(pp == null || pedido == null)
            return 0;
        for(var item : getProductoPedidoList()){
            if(item.getPedido().getId() == pp.getPedido().getId()){
                total+=item.getCantidad();
            }
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
