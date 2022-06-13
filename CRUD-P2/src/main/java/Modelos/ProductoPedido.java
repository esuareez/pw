package Modelos;

public class ProductoPedido {

    private Pedido pedido;
    private Producto producto;
    private int cantidad;

    public ProductoPedido() {
    }

    public ProductoPedido(Pedido pedido, Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.pedido = pedido;
    }
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
