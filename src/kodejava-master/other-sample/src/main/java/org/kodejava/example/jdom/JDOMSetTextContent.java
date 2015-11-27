package org.kodejava.example.jdom;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.IOException;
import java.io.StringReader;

public class JDOMSetTextContent {
    public static void main(String[] args) {
        String xml =
                "<root>" +
                        "    <description>" +
                        "    </description>" +
                        "</root>";

        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(new StringReader(xml));

            Element root = document.getRootElement();
            Element description = root.getChild("description");

            //
            // Adding a text content to the description element. The string
            // will automatically escaped so we don't have to use the &amp;lt; and
            // &amp;gt; symbol.
            //
            description.setText("This is an <strong>IMPORTANT</strong> " +
                    "description");

            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(document, System.out);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
