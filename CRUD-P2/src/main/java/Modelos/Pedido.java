package Modelos;

import Servicios.PedidoServ;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class Pedido {

    private int id;
    private Date fecha;
    private int estado;
    private double total;
    private Usuario usuario;

    public Pedido() {
    }

    public Pedido(Usuario usuario) {
        //this.id = PedidoServ.getInstance().makeId();
        this.fecha = Date.from(Instant.now());
        this.estado = 1;
        this.total = 0;
        this.usuario = usuario;

    }

    public Pedido(int id, Usuario usuario){
        this.id = id;
        this.fecha = Date.from(Instant.now());
        this.estado = 1;
        this.total = 0;
        this.usuario = usuario;
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
}
