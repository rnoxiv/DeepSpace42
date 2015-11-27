package org.kodejava.example.jdom;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.IOException;

public class JDOMRemoveElement {
    public static void main(String[] args) {
        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(new File("userinfo.xml"));

            //
            // The lines below output the original userinfo.xml content
            //
            // <?xml version="1.0" encoding="UTF-8"?>
            // <rows>
            //   <row>
            //     <firstname>Alice</firstname>
            //     <lastname>Mallory</lastname>
            //     <address>Sunset Road</address>
            //   </row>
            // </rows>
            //            
            XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
            out.output(doc, System.out);

            //
            // Remove the address element from Alice information. First we
            // get the row element from the root element, and finally 
            // remove the address from the row. And the result will be:
            //
            // <?xml version="1.0" encoding="UTF-8"?>
            // <rows>
            //   <row>
            //     <firstname>Alice</firstname>
            //     <lastname>Mallory</lastname>
            //   </row>
            // </rows>
            //
            doc.getRootElement().getChild("row").removeChild("address");
            out.output(doc, System.out);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
