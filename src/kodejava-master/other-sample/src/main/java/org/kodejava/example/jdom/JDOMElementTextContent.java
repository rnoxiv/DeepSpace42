package org.kodejava.example.jdom;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;

public class JDOMElementTextContent {
    public static void main(String[] args) {
        String xml =
                "<root>" +
                        "    <paragraph>" +
                        "        Lorem ipsum dolor sit amet, consectetur adipisicing " +
                        "        elit, sed do eiusmod tempor incididunt ut labore et " +
                        "        dolore magna aliqua. Ut enim ad minim veniam, quis " +
                        "        nostrud exercitation ullamco laboris nisi ut aliquip " +
                        "        ex ea commodo consequat. Duis aute irure dolor in " +
                        "        reprehenderit in voluptate velit esse cillum dolore " +
                        "        eu fugiat nulla pariatur. Excepteur sint occaecat " +
                        "        cupidatat non proident, sunt in culpa qui officia " +
                        "        deserunt mollit anim id est laborum." +
                        "    </paragraph>" +
                        "</root>";

        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(new StringReader(xml));

            Element root = document.getRootElement();
            Element paragraph = root.getChild("paragraph");

            //
            // Returns the textual content directly held under this element as 
            // a string. This includes all text within this single element, 
            // including whitespace and CDATA sections if they exist.
            //
            String content = paragraph.getText();
            System.out.println("content = " + content);

            //
            // Returns the textual content of this element with all surrounding
            // whitespace removed. If no textual value exists for the element,
            // or if only whitespace exists, the empty string is returned.
            //
            String contentTrimmed = paragraph.getTextTrim();
            System.out.println("contentTrimmed = " + contentTrimmed);

            //
            // Returns the textual content of this element with all surrounding
            // whitespace removed and internal whitespace normalized to a single
            // space.If no textual value exists for the element, or if only
            // whitespace exists, the empty string is returned.
            //
            String contentNormalize = paragraph.getTextNormalize();
            System.out.println("contentNormalize = " + contentNormalize);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
