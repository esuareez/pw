package Modelos;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;

@Entity
public class Comentario {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    private String comentario;

    private Date fecha;

    public Comentario(@NotNull Producto producto, @NotNull Usuario usuario, String comentario) {
        this.producto = producto;
        this.usuario = usuario;
        this.comentario = comentario;
        this.fecha = Date.from(Instant.now());
    }

    public Comentario() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(@NotNull Producto producto) {
        this.producto = producto;
    }

    @NotNull
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(@NotNull Usuario usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
