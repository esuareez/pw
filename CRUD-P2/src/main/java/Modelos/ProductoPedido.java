package Modelos;

import jakarta.persistence.*;
import org.hibernate.persister.collection.OneToManyPersister;

@Entity
@Table(name="miproductopedido")
public class ProductoPedido {

    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    private Pedido pedido;
    @OneToOne
    private Producto producto;
    private int cantidad;

    public ProductoPedido() {
    }

    public ProductoPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;

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

}
