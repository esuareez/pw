package Servicios;

import Modelos.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

public class ProductoServ extends GestionDb<Producto> {
    private static ProductoServ instancia;

    private List<Producto> productoList = new ArrayList<>();

    public ProductoServ() {
        super(Producto.class);
    }

    public static ProductoServ getInstance(){
        if(instancia==null){
            instancia = new ProductoServ();
        }
        return instancia;
    }

    public Producto getProductoporNombre(String nombre, List<Producto> lista){
        return lista.stream().filter(e -> e.getNombre().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }

    public void deleteProducto(Producto p)
    {
        p.setEstado(0);// 0 - Eliminado.
        editar(p);
    }
}
