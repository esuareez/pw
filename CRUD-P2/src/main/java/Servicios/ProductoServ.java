package Servicios;

import Modelos.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoServ {
    private static ProductoServ instancia;

    private List<Producto> productoList = new ArrayList<>();

    private Producto product = new Producto("Peptobismol",20,250,"Sirve para el dolor de estómago.",1);
    private Producto product1 = new Producto("Winasorb",0,25,"Dolor de cabeza.",1);

    public ProductoServ(){
        crearProducto(product);
        deleteProducto(product);
        crearProducto(product1);
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
        System.out.println(producto.getNombre()+" "+producto.getCantidad());
        if(producto == null)
        {
            System.out.println("El producto no existe.");
            return null;
        }
        producto.mezclar(p);
        System.out.println(producto.getNombre()+" "+producto.getCantidad());
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
