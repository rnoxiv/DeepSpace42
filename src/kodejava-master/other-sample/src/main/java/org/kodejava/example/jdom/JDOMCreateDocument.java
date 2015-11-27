package org.kodejava.example.jdom;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;

public class JDOMCreateDocument {
    public static void main(String[] args) {
        //
        // Creating a document with an Element as the parameter.
        //
        Element element = new Element("root");
        element.setText("Hello World");

        Document document = new Document(element);
        System.out.println("root.getName() = " +
                document.getRootElement().getName());

        //
        // We can also create a document from a file, stream or URL using
        // a SAXBuilder
        //
        SAXBuilder builder = new SAXBuilder();
        try {
            //
            // Build a document from a file using a SAXBuilder.
            // The content of data.xml file:
            //
            // <data>
            //     <row>
            //         <username>alice</username>
            //         <password>secret</password>
            //     </row>
            // </data>
            //
            document = builder.build(new File("data.xml"));
            Element root = document.getRootElement();
            System.out.println("root.getName() = " + root.getName());
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
