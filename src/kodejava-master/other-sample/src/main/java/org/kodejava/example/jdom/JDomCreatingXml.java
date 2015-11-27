package org.kodejava.example.jdom;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.FileWriter;
import java.io.IOException;

public class JDomCreatingXml {
    public static void main(String[] args) {
        //
        // <rows>
        //     <row>
        //         <firstname>Alice</firstname>
        //         <lastname>Starbuzz</lastname>
        //         <address>Sunset Read</address>
        //     </row>
        // </row>
        //

        Document document = new Document();
        Element root = new Element("rows");

        //
        // Creating a child for the root element. Here we can see how to
        // set the text of an xml element.
        //
        Element child = new Element("row");
        child.addContent(new Element("firstname").setText("Alice"));
        child.addContent(new Element("lastname").setText("Starbuzz"));
        child.addContent(new Element("address").setText("Sunset Road"));

        //
        // Add the child to the root element and add the root element as
        // the document content.
        //
        root.addContent(child);
        document.setContent(root);

        try {
            FileWriter writer = new FileWriter("userinfo.xml");
            XMLOutputter outputter = new XMLOutputter();

            //
            // Set the XLMOutputter to pretty formatter. This formatter
            // use the TextMode.TRIM, which mean it will remove the
            // trailing white-spaces of both side (left and right)
            //
            outputter.setFormat(Format.getPrettyFormat());

            //
            // Write the document to a file and also display it on the
            // screen through System.out.
            //
            outputter.output(document, writer);
            outputter.output(document, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
