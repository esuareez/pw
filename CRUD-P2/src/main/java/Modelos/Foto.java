package Modelos;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity
public class Foto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String mimeType;
   @ManyToOne
   @NotNull
   @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;
    @Lob
    private String fotoBase64;

    public Foto() {
    }

    public Foto(@NotNull Producto producto, String nombre, String mimeType, String fotoBase64){
        this.producto = producto;
        this.nombre = nombre;
        this.mimeType = mimeType;
        this.fotoBase64 = fotoBase64;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @NotNull
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(@NotNull Producto producto) {
        this.producto = producto;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }
}
