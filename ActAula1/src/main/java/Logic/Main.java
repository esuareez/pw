package Logic;

import Controlador.UsuarioController;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.http.staticfiles.Location;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/recursos";
                staticFileConfig.location = Location.CLASSPATH;
            });
        });
        app.start(1000);

        new UsuarioController(app).rutas();
    }


}

