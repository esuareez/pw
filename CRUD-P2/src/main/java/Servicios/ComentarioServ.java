package Servicios;

import Modelos.Comentario;
import Modelos.ProductoPedido;

public class ComentarioServ extends GestionDb<Comentario>{
    private static ComentarioServ instancia;


    public static ComentarioServ getInstance(){
        if(instancia==null){
            instancia = new ComentarioServ();
        }
        return instancia;
    }

    public ComentarioServ() {
        super(Comentario.class);
    }
}
