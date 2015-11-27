package org.kodejava.example.jdom;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;

public class JDOMIntegerAttributeValue {
    public static void main(String[] args) {
        String xml = "<root><table width=\"100\"/></root>";

        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(new StringReader(xml));

            Element child = document.getRootElement().getChild("table");
            int tableWidth = child.getAttribute("width").getIntValue();
            System.out.println("tableWidth = " + tableWidth);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
