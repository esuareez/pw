package Servicios;

import Modelos.Pedido;
import Modelos.Producto;
import Modelos.ProductoPedido;
import Modelos.Usuario;

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
    }

    private List<ProductoPedido> list = new ArrayList<>();

    public ProductoPedido getProductoenLista(Producto producto, List<ProductoPedido> list){
        return list.stream().filter(e -> e.getProducto().getId() == producto.getId()).findFirst().orElse(null);
    }
    public List<ProductoPedido> _addProducto(List<ProductoPedido> lista, Producto producto, int cantidad) {
        if (lista == null) {
            ProductoPedido productoPedido = new ProductoPedido(producto,cantidad);
            lista.add(productoPedido);
        } else {
            ProductoPedido productoPedido = getProductoenLista(producto,lista);
            if(productoPedido != null){
                if(productoPedido.getProducto().getId() == producto.getId() && productoPedido.getProducto().getCantidad() >= productoPedido.getCantidad()){
                    if(productoPedido.getCantidad()+cantidad <= productoPedido.getProducto().getCantidad()){
                        productoPedido.setCantidad(productoPedido.getCantidad()+cantidad);
                    }
                }
            }else{
                productoPedido = new ProductoPedido(producto,cantidad);
                lista.add(productoPedido);
            }
        }
        return lista;
    }
    public List<ProductoPedido> _editarCarro(List<ProductoPedido> lista, Producto producto, int cantidad){
        ProductoPedido productoPedido = getProductoenLista(producto,lista);
        productoPedido.setCantidad(cantidad);
        return lista;
    }
    public List<ProductoPedido> _removeProducto(List<ProductoPedido> lista, Producto producto){
        ProductoPedido productoPedido = getProductoenLista(producto,lista);
        lista.remove(productoPedido);
        return lista;
    }
    public int _getTotalProductosenCarrito(List<ProductoPedido> lista){
        int total = 0;
        for(var item : lista){
            total += item.getCantidad();
        }
        return total;
    }
    public void _completarPedido(List<ProductoPedido> lista, Usuario usuario){
        Pedido pedido = PedidoServ.getInstance().crear(new Pedido(usuario));
        double total = 0;
        pedido.setEstado(2);
        for( var item : lista){
            total += (item.getProducto().getPrecio() * item.getCantidad());
            item.getProducto().setCantidad((item.getProducto().getCantidad()-item.getCantidad()));
            ProductoServ.getInstance().editar(item.getProducto());
        }
        pedido.setTotal(total);
        PedidoServ.getInstance().editar(pedido);

    }


}
