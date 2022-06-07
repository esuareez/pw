package Modelos;

public class ProductoPedido {

    private int id;
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

    public ProductoPedido(int id, Pedido pedido, Producto producto, int cantidad) {
        this.id = id;
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
