package logic;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.InputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException{
        /*InputStream stream = System.in;
        Scanner scanner = new Scanner(stream);
        String url = scanner.next();
        scanner.close();*/


        Document doc = Jsoup.connect("https://www.maltamorena.com.do/registro-usuario").get();


        Elements lines = doc.getAllElements();
        int cantLines = 0;
        for(Element line : lines)
        {
            cantLines++;
            line.getAllElements().next();
        }
        System.out.println("Cantidad de lineas que hay en el HTML: "+cantLines);


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
                    cantPost++;
                    break;
                default:
                    cantGet++;
                    break;
            }
            cantForm+=1;
            frm.getAllElements().next();

        }
        System.out.println("\n\nInputs");
        for (Element frm: form){
            var input = frm.getElementsByTag("input");
            for(Element f: input)
            {
                System.out.println(f);
                String inputype = input.attr("type");
                System.out.println("Tipo: " + inputype + "\n");
            }

        }


        System.out.println("Cantidad de form con el método 'GET' que hay en el HTML: "+cantGet);
        System.out.println("Cantidad de form con el método 'POST' que hay en el HTML: "+cantPost);
        System.out.println("Cantidad de form que hay en el HTML: "+cantForm);
    }
}
