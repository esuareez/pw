package Main;

import Controlador.PedidoController;
import Controlador.ProductoController;
import Controlador.UsuarioController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

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
        app.start(7000);

        new ProductoController(app).aplicarRutas();
        new UsuarioController(app).aplicarRutas();
        new PedidoController(app).aplicarRutas();

    }
}
