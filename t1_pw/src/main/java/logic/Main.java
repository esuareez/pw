package logic;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException{
        /*Scanner input = new Scanner(System.in);
        String url = input.nextLine();*/

        Document doc = Jsoup.connect("https://tecnotuto.com/internet/como-agregar-y-editar-imagenes-en-formularios-de-google/").get();

        Elements p = doc.getElementsByTag("p");
        int cantP = 0; // Cantidad de <p> en el HTML
        for(Element q : p) {
            cantP+=1;
            q.getAllElements().next();
        }
        System.out.println("Cantidad de <p> que hay en el HTML: "+cantP);

        Elements img = doc.getElementsByTag("img");
        int cantImg = 0; // Cantidad de <img> en el HTML
        for(Element im : img) {
            cantImg+=1;
            im.getAllElements().next();
        }
        System.out.println("Cantidad de <img> que hay en el HTML: "+cantImg);

        Elements form = doc.getElementsByTag("form");
        int cantForm = 0, cantPost = 0, cantGet = 0; // Cantidad de <p> en el HTML
        for(Element frm : form) {
            switch(frm.attr("method"))
            {
                case "post":
                    cantPost+=1;
                    break;
                case "get":
                    cantGet+=1;
                    break;
            }

            cantForm+=1;
            frm.getAllElements().next();

        }
        System.out.println("Cantidad de form con el método 'GET' que hay en el HTML: "+cantGet);
        System.out.println("Cantidad de form con el método 'POST' que hay en el HTML: "+cantPost);
        System.out.println("Cantidad de form que hay en el HTML: "+cantForm);
    }
}
