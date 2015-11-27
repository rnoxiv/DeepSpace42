package org.kodejava.example.jdom;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class JDOMDocumentToString {
    public static void main(String[] args) {
        Document document = new Document();
        Element root = new Element("rows");

        //
        // Creating a child for the root element. Here we can see how to
        // set the text of an xml element.
        //
        Element child = new Element("row");
        child.addContent(new Element("firstname").setText("Alice"));
        child.addContent(new Element("lastname").setText("Mallory"));
        child.addContent(new Element("address").setText("Sunset Road"));

        //
        // Add the child to the root element and add the root element as
        // the document content.
        //
        root.addContent(child);
        document.setContent(root);

        //
        // Create an XMLOutputter object with pretty formatter. Calling
        // the outputString(Document doc) method convert the document
        // into string data.
        //
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        String xmlString = outputter.outputString(document);
        System.out.println(xmlString);
    }
}
