package Main;

import Servicios.ProductoServ;
import Modelos.Producto;


public class Main {
    public static void main(String[] args) {
        System.out.println("Productos registrados.");
        for(var item :ProductoServ.getInstance().getProductoList())
        {
            System.out.println(item.getNombre()+" "+item.getPrecio()+" "+item.getEstado()+" "+item.getDescripcion());
        }
        System.out.println("\nProductos luego de editar.");
        Producto producto = new Producto(2,"Winasorb Ultra",35,"Dolor del cuerpo.");
        ProductoServ.getInstance().editarProducto(producto);
        for(var item :ProductoServ.getInstance().getProductoList())
        {
            System.out.println(item.getNombre()+" "+item.getPrecio()+" "+item.getEstado()+" "+item.getDescripcion());
        }
        System.out.println("\nProductos actuales.");
        ProductoServ.getInstance().deleteProducto(producto);
        for(var item :ProductoServ.getInstance().getProductoList())
        {
            if(item.getEstado()>0)
                System.out.println(item.getNombre()+" "+item.getPrecio()+" "+item.getEstado()+" "+item.getDescripcion());
        }
    }
}
