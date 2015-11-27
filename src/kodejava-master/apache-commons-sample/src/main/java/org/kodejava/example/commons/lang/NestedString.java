package org.kodejava.example.commons.lang;

import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class NestedString {
    public static void main(String[] args) {
        String helloHtml = "<html>" +
                "<head>" +
                "   <title>Hello World from Java</title>" +
                "<body>" +
                "Hello, today is: " + new Date() +
                "</body>" +
                "</html>";

        String title = StringUtils.substringBetween(helloHtml, "<title>", "</title>");
        String content = StringUtils.substringBetween(helloHtml, "<body>", "</body>");

        System.out.println("title = " + title);
        System.out.println("content = " + content);
    }
}
