package logic;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException{
        InputStream stream = System.in;
        Scanner scanner = new Scanner(stream);
        System.out.println("Escriba la URL: ");
        String url = scanner.next();
        scanner.close();

        Document doc = Jsoup.connect(url).get();

        /*
        Item A: Indicar la cantidad de lineas del recurso retornado
         */
        Elements lines = doc.getAllElements();
        int cantLines = 0;
        for(Element line : lines)
        {
            cantLines++;
            line.getAllElements().next();
        }

        System.out.println("\nCantidad de lineas que hay en el HTML: "+cantLines);

        /*
        Item B: Indicar la cantidad de párrafos (p) que contiene el documento HTML.
         */
        Elements p = doc.getElementsByTag("p");
        int cantP = 0; // Cantidad de <p> en el HTML
        for(Element q : p) {
            cantP+=1;
            q.getAllElements().next();
        }
        System.out.println("Cantidad de <p> que hay en el HTML: "+cantP);

        /*
        Item C: Indicar la cantidad de imágenes (img) dentro de los párrafos que
                contiene el archivo HTML.
         */
        Elements img = doc.getElementsByTag("img");
        int cantImg = 0; // Cantidad de <img> en el HTML
        for(Element im : img) {
            cantImg+=1;
            im.getAllElements().next();
        }
        System.out.println("Cantidad de <img> que hay en el HTML: "+cantImg);
        /*
        Item D y F: (D) -> Indicar la cantidad de formularios (form) que contiene el HTML por
                    categorizando por el método implementado POST o GET.
                    (F) -> Para cada formulario 'parseado', identificar que el método de envío
                            del formulario sea POST y enviar una petición al servidor con el
                            parámetro llamado asignatura y valor practica1 y un header llamado
                            matricula con el valor correspondiente a matrícula asignada. Debe
                            mostrar la respuesta por la salida estándar.

         */
        Elements form = doc.getElementsByTag("form");
        int cantForm = 0, cantPost = 0, cantGet = 0; // Cantidad de <p> en el HTML
        for(Element frm : form) {
            switch(frm.attr("method"))
            {
                case "post":
                    cantPost++;
                    /*
                        Implementacion del Item F
                     */
                    URL url1 = new URL(url);
                    String ulink;
                    if(url1.getPort() == -1) // Si no tiene puerto
                    {
                        ulink = url1.getProtocol()+"://"+url1.getHost()+frm.attr("action");
                    }else{
                        ulink = url1.getProtocol()+"://"+url1.getHost()+":"+url1.getPort()+frm.attr("action");
                    }

                    Document docs = Jsoup.connect(ulink)
                            .data("asignacion","practica1")
                            .header("matricula","10138836").post();
                    System.out.println("\nVariable y header enviado: \n"+docs.html());
                    break;
                default: // Metodo Get y cuando sea default el formulario
                    cantGet++;
                    break;
            }
            cantForm+=1;
            frm.getAllElements().next();

        }
        System.out.println("\nFormularios\nCantidad de form con el método 'GET' que hay en el HTML: "+cantGet);
        System.out.println("Cantidad de form con el método 'POST' que hay en el HTML: "+cantPost);
        System.out.println("Cantidad de form que hay en el HTML: "+cantForm);

        /*
        Item E: Para cada formulario mostrar los campos del tipo input y su
                respectivo tipo que contiene en el documento HTML.
         */
        System.out.println("\nInputs");
        for (Element frm: form){
            var input = frm.getElementsByTag("input");
            if(input.isEmpty())
                System.out.println("No tiene inputs.");
            else{
                for(Element f: input)
                {
                    System.out.println(f);
                    String inputype = f.attr("type");
                    System.out.println("Tipo: " + inputype + "\n");
                }
            }
        }
    }
}
