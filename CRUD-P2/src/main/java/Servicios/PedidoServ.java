package Servicios;

import Controlador.ProductoController;
import Modelos.Pedido;
import Modelos.Producto;
import Modelos.ProductoPedido;

import java.sql.Date;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
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
    Pedido pedido = new Pedido(1,productoPedidoList,"Eliam Pimentel");
    ProductoPedido pp = new ProductoPedido(1,pedido, ProductoServ.getInstance().getProductoporID(1),5 );
    ProductoPedido pp1 = new ProductoPedido(1,pedido, ProductoServ.getInstance().getProductoporID(2),2 );

    public PedidoServ() {
        productoPedidoList.add(pp);
        productoPedidoList.add(pp1);
        pedidoList.add(pedido);

    }

    public Pedido getPedidoporId(int id){
        return pedidoList.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

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
    public List<ProductoPedido> getProductoPedidoList() {
        return productoPedidoList;
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
