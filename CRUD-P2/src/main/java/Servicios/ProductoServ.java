package Servicios;

import Modelos.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoServ {
    private static ProductoServ instancia;

    private List<Producto> productoList = new ArrayList<>();

    private Producto product = new Producto(1,"Peptobismol",250,"Sirve para el dolor de estómago.");
    private Producto product1 = new Producto(2,"Winasorb",25,"Dolor de cabeza.");

    public ProductoServ(){
        productoList.add(product);
        productoList.add(product1);
    }

    public static ProductoServ getInstance(){
        if(instancia==null){
            instancia = new ProductoServ();
        }
        return instancia;
    }

    public List<Producto> getProductoList(){
        return productoList;
    }

    public Producto getProductoporNombre(String nombre){
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
