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

    public List<Producto> getProductoList(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select * from producto",Producto.class);
        List<Producto> listaProducto = query.getResultList();
        return listaProducto;
    }

    public Producto getProductoporNombre(String nombre){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select e from Producto where e.nombre like :nombre");
        List<Producto> listaProducto = query.getResultList();

        return productoList.stream().filter(e -> e.getNombre().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }

    public Producto getProductoporID(int id){
        return productoList.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    public Producto crearProducto(Producto producto){
        if(getProductoporNombre(producto.getNombre()) != null)
            return null;
        producto.setId(makeId());
        productoList.add(producto);
        return producto;
    }

    public Producto editarProducto(Producto p)
    {
        Producto producto = getProductoporID(p.getId());
        if(producto == null)
        {
            System.out.println("El producto no existe.");
            return null;
        }
        producto.mezclar(p);
        return producto;
    }

    public Producto deleteProducto(Producto p)
    {
        Producto producto = getProductoporID(p.getId());
        if(producto == null)
        {
            System.out.println("El producto no existe.");
            return null;
        }
        producto.setEstado(0); // 0 - Eliminado.
        return producto;
    }

    /*
        Generar ID aleatorio para el producto
     */

    public int makeId(){
        int num, min = 100000, max = 999999;
        num = (int)Math.floor(Math.random()*(max-min+1)+min);
        while(getProductoporID(num) != null)
        {
            num = (int)Math.floor(Math.random()*(max-min+1)+min);
        }
        return num;
    }

}
