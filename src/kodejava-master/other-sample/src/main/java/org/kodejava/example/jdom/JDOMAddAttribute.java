package org.kodejava.example.jdom;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.IOException;

public class JDOMAddAttribute {
    public static void main(String[] args) {
        Document doc = new Document();
        Element root = new Element("root");

        //
        // Create <row userid="alice"></row>
        //
        Element child = new Element("row").setAttribute("userid", "alice");

        //
        // Create <name firstname="Alice" lastname="Starbuzz"></name>
        //
        Element name = new Element("name")
                .setAttribute("firstname", "Alice")
                .setAttribute("lastname", "Starbuzz");

        child.addContent(name);
        root.addContent(child);
        doc.addContent(root);

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            outputter.output(doc, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
