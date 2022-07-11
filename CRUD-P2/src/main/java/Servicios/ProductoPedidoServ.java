package Servicios;

import Modelos.Pedido;
import Modelos.Producto;
import Modelos.ProductoPedido;

import java.util.ArrayList;
import java.util.List;

public class ProductoPedidoServ extends GestionDb<ProductoPedido>{
    private static ProductoPedidoServ instancia;


    public static ProductoPedidoServ getInstance(){
        if(instancia==null){
            instancia = new ProductoPedidoServ();
        }
        return instancia;
    }

    public ProductoPedidoServ() {
        super(ProductoPedido.class);
        for(int item = 0; item < findAll().size(); item++){

        }
    }


}
