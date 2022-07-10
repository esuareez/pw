package Modelos;

import Servicios.PedidoServ;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="mipedido")
public class Pedido {

    @Id
    @GeneratedValue
    private int id;
    private Date fecha;
    private int estado;
    private double total;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    //private List<ProductoPedido> productoPedido;

    public Pedido() {
    }

    public Pedido(Usuario usuario) {
        this.usuario = usuario;
        //this.productoPedido = productoPedido;
        this.fecha = Date.from(Instant.now());
        this.estado = 1;
        this.total = 0;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    /*public List<ProductoPedido> getProductoPedido() {
        return productoPedido;
    }
    public void setProductoPedido(List<ProductoPedido> productoPedido) {
        this.productoPedido = productoPedido;
    }*/
}
