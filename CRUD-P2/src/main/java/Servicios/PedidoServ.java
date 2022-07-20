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

