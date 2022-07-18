package Main;

import Controlador.PedidoController;
import Controlador.ProductoController;
import Controlador.UsuarioController;
import Modelos.Producto;
import Modelos.Usuario;
import Servicios.BootStrapServices;
import Servicios.ProductoServ;
import Servicios.UsuarioServ;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import org.h2.tools.Server;

import javax.swing.*;
import java.io.File;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        BootStrapServices.getInstancia().init();
        UsuarioServ.getInstance().crear(new Usuario("admin", "admin", "admin", "admin"));
        ProductoServ.getInstance().crear(new Producto("Winasorb",10,15,"dolor",1));

        // Productos.

        for(int i = 0 ; i < 30 ; i++){
            String directorio = System.getProperty("user.dir");
            System.out.println(directorio+"\n");
        }

        Javalin app = Javalin.create(config ->{
            config.addStaticFiles(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/publico";
                staticFileConfig.location = Location.CLASSPATH;
            });
            //config.registerPlugin(new RouteOverviewPlugin("/rutas")); //aplicando plugins de las rutas
            config.enableCorsForAllOrigins();
        });
        app.start(7000);

        new ProductoController(app).aplicarRutas();
        new UsuarioController(app).aplicarRutas();
        new PedidoController(app).aplicarRutas();



    }

}
