package Modelos;

import jakarta.persistence.*;
import org.hibernate.persister.collection.OneToManyPersister;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name="miproductopedido")
public class ProductoPedido {

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "ID_PEDIDO")
    private Pedido pedido;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;
    private int cantidad;

    public ProductoPedido() {
    }



    public ProductoPedido(Pedido pedido, Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.pedido = pedido;
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

    @NotNull
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(@NotNull Pedido pedido) {
        this.pedido = pedido;
    }
}
