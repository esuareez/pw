package Main;

import Controlador.PedidoController;
import Controlador.ProductoController;
import Controlador.UsuarioController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import org.h2.tools.Server;

import java.sql.SQLException;

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


        try {
            //Modo servidor H2.
            Server.createTcpServer("-tcpPort",
                    "9092",
                    "-tcpAllowOthers",
                    "-tcpDaemon",
                    "-ifNotExists").start();
            //Abriendo el cliente web. El valor 0 representa puerto aleatorio.
            String status = Server.createWebServer("-trace", "-webPort", "0").start().getStatus();
            //
            System.out.println("Status Web: "+status);
        }catch (SQLException ex){
            System.out.println("Problema con la base de datos: "+ex.getMessage());
        }



    }

}
