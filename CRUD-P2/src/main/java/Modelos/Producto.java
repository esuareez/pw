package Modelos;

import java.util.random.RandomGenerator;

public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private String descripcion;
    private int estado;
    private int cantidad;

    public Producto(){
    }

    public Producto(String nombre, int cantidad, double precio, String descripcion, int estado){
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Producto(int id, String nombre, int cantidad, double precio, String descripcion, int estado)
    {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public void mezclar(Producto producto){
        nombre = producto.getNombre();
        precio = producto.getPrecio();
        descripcion = producto.getDescripcion();
        estado = producto.getEstado();
    }

    public int getId() {
        return id;
    }

    public void setId(int id){ this.id = id; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
