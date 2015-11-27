package org.kodejava.example.jdom;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.IOException;

public class JDOMRemoveAttribute {
    public static void main(String[] args) {
        SAXBuilder builder = new SAXBuilder();
        try {
            //
            // <root>
            //     <row userid="alice">
            //         <firstname>Alice</firstname>
            //         <lastname>Starbuzz</lastname>
            //     </row>
            // </root>
            //
            Document doc = builder.build(new File("data.xml"));
            XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
            out.output(doc, System.out);

            //
            // Get the root element and find a child named row and remove
            // its attribute named "userid"
            //
            Element root = doc.getRootElement();
            root.getChild("row").removeAttribute("userid");
            out.output(doc, System.out);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
