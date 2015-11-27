package org.kodejava.example.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class ServletRequestHeader extends HttpServlet implements Servlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        Enumeration enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            //
            // Get request header name
            //
            String name = (String) enumeration.nextElement();

            //
            // Get request header value
            //
            String value = request.getHeader(name);
            writer.println("Header [" + name + " = " + value + "<br/>");

            //
            // Read request values, for header information that have multiple
            // values.
            //
            Enumeration values = request.getHeaders(name);
            while (values.hasMoreElements()) {
                value = (String) values.nextElement();
                writer.println("&nbsp;&nbsp;&nbsp;Header [" + name + " = " + value + "<br/>");
            }
        }
        writer.close();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        //
        // Do Nothing!
        //
    }
}
