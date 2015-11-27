package org.kodejava.example.jdom;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class JDOMTraversingElement {
    public static void main(String[] args) {
        String xml =
                "<root>" +
                        "    <country name=\"Japan\" capital=\"Tokyo\"/>" +
                        "    <country name=\"France\" capital=\"Paris\"/>" +
                        "    <country name=\"Italy\" capital=\"Rome\"/>" +
                        "    <country name=\"England\" capital=\"London\"/>" +
                        "    <country name=\"Indonesia\" capital=\"Jakarta\"/>" +
                        "    <city name=\"Denpasar\"/>" +
                        "    <city name=\"Bangkok\"/>" +
                        "    <city name=\"Mumbai\"/>" +
                        "    <city name=\"Delhi\"/>" +
                        "</root>";

        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(
                    new ByteArrayInputStream(xml.getBytes()));

            //
            // Getting the root element
            //
            Element root = document.getRootElement();

            //
            // Getting the first child
            //
            Element country = root.getChild("country");
            System.out.println("Name = " + country.getAttribute("name").getValue());
            System.out.println("Capital = " + country.getAttribute("capital").getValue());
            System.out.println("----------------------------------------");

            //
            // Getting all children of the root
            //
            List<Element> elements = root.getChildren();
            for (Element element : elements) {
                if (element.getName().equals("country")) {
                    System.out.println(MessageFormat.format("{0} -> {1}",
                            element.getAttribute("name").getValue(),
                            element.getAttribute("capital").getValue()));
                } else if (element.getName().equals("city")) {
                    System.out.println(element.getAttribute("name").getValue());
                }
            }
            System.out.println("----------------------------------------");

            //
            // Getting all children of the root named city
            //
            List<Element> cities = root.getChildren("city");
            for (Element city : cities) {
                System.out.println(city.getAttribute("name").getValue());
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
