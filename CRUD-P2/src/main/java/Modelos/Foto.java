package Modelos;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

@Entity
public class Foto {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "ID_PRODUCTO")
    Producto producto;

    @Lob
    private String img;

    public Foto(@NotNull Producto producto) {
        this.producto = producto;
    }

    public Foto() {

    }

    @NotNull
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(@NotNull Producto producto) {
        this.producto = producto;
    }
}
