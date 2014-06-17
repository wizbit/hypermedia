package testing;

import com.marcusspeight.Client;
import com.marcusspeight.DocumentInterface;
import com.marcusspeight.ObjectInterface;
import com.marcusspeight.PropertyInterface;
import com.marcusspeight.impl.Parser;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        try {
            //obj();
            //nested();
            //localLink();
            //remote();
            //styled();

            imdb();

            System.out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static DocumentInterface enter(String url) throws MalformedURLException {
        Parser parser = new Parser(new URL(url));

        return parser.getDocument();
    }
/*
    private static void obj() throws MalformedURLException {
        DocumentInterface doc = enter("http://localhost:8080/obj");

        System.out.println(doc.getObjects());

        PropertyInterface obj = doc.getObjects().get(0);

        System.out.println(obj.getProperties());
        System.out.println(obj.getProperty("name"));

        System.out.println("----");
        System.out.flush();
    }

    private static void nested() throws MalformedURLException {
        DocumentInterface doc = enter("http://localhost:8080/nested");

        System.out.println(doc.getObjects().get(0).getProperties());
        System.out.println((doc.getObjects().get(0).getProperty("actor")).getProperty("name"));

        System.out.println("----");
        System.out.flush();
    }

    private static void localLink() throws MalformedURLException {
        DocumentInterface doc = enter("http://localhost:8080/local-link");

        System.out.println((doc.getObjects().get(0).getProperty("actor")).getProperty("name"));
        System.out.println(doc.getObjects());

        System.out.println("----");
        System.out.flush();
    }

    private static void remote() throws MalformedURLException {
        DocumentInterface doc = enter("http://localhost:8080/remote");

        System.out.println((doc.getObjects().get(0).getProperty("actor")).getProperty("name"));
        System.out.println(doc.getObjects());

        System.out.println("----");
        System.out.flush();
    }

    private static void styled() throws MalformedURLException {
        DocumentInterface doc = enter("http://localhost:8080/styled");

        System.out.println((doc.getObjects().get(0).getProperty("actor")).getProperty("name"));
        System.out.println(doc.getObjects());

        System.out.println("----");
        System.out.flush();
    }
*/
    private static void imdb() throws MalformedURLException {
        DocumentInterface doc = Client.enter("http://www.imdb.com/title/tt0133093/");

        //for(ObjectInterface obj: doc.getObjects()) {
        //    System.out.println(obj.getProperties());
        //}

        System.out.println(doc.getObjects());
        System.out.println(doc.getObjects().get(0).getProperties());
        System.out.println(doc.getObjects().get(0).getProperty("director"));
        System.out.println(((ObjectInterface)doc.getObjects().get(0).getProperty("director")).getProperty("name"));
    }
}
