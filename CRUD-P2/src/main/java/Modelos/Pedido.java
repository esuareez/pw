package Modelos;

import Servicios.PedidoServ;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class Pedido {

    private int id;
    private List<ProductoPedido> productoPedido;
    private Date fecha;
    private int estado;
    private String nombre;

    public Pedido() {
    }

    public Pedido(List<ProductoPedido> productoPedido, String nombre) {
        //this.id = PedidoServ.getInstance().makeId();
        this.productoPedido = productoPedido;
        this.fecha = Date.from(Instant.now());
        this.estado = 1;
        this.nombre = nombre;
    }

    public Pedido(int id,List<ProductoPedido> productoPedido, String nombre){
        this.id = id;
        this.productoPedido = productoPedido;
        this.fecha = Date.from(Instant.now());
        this.estado = 1;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ProductoPedido> getProductoPedido() {
        return productoPedido;
    }

    public void setProductoPedido(List<ProductoPedido> productoPedido) {
        this.productoPedido = productoPedido;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
