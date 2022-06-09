package Main;

import Controlador.ProductoController;
import Servicios.PedidoServ;
import Servicios.ProductoServ;
import Modelos.Producto;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create(config ->{
            config.addStaticFiles(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/publico";
                staticFileConfig.location = Location.CLASSPATH;
            });
            //config.registerPlugin(new RouteOverviewPlugin("/rutas")); //aplicando plugins de las rutas
            //config.enableCorsForAllOrigins();
        });
        app.start(8000);

        new ProductoController(app).aplicarRutas();

    }
}
