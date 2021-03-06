package org.kodejava.example.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ReadTextFileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        //
        // We are going to read a file called configuration.properties. This
        // file is placed under the WEB-INF directory.
        //
        String filename = "/WEB-INF/configuration.properties";

        ServletContext context = getServletContext();

        //
        // First get the file InputStream using ServletContext.getResourceAsStream()
        // method.
        //
        InputStream is = context.getResourceAsStream(filename);
        if (is != null) {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            PrintWriter writer = response.getWriter();
            String text = "";

            //
            // We read the file line by line and later will be displayed on the
            // browser page.
            //
            while ((text = reader.readLine()) != null) {
                writer.println(text);
            }
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
    }
}
