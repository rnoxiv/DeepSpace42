package org.kodejava.example.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class ContextInitParameter extends HttpServlet implements Servlet {

    public ContextInitParameter() {
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        //
        // Get an instance of ServletContext
        //
        ServletContext context = getServletContext();

        //
        // To read context initialization parameter we can call context.getInitParameter()
        // and pass the name of initialization parameter to be read. If the named
        // parameter does not exists the returned value will be null.
        //
        // In this example we read an initialization parameter called LOG.PATH
        //
        String logPath = context.getInitParameter("LOG.PATH");
        writer.println("Log Path: " + logPath + "<br/>");

        //
        // Reads all the name of servlet's initialization parameters. If the
        // servlet doesn't have any an empty enumeration will be returned.
        //
        Enumeration enumeration = context.getInitParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = (String) enumeration.nextElement();
            String paramValue = context.getInitParameter(paramName);

            writer.println("Context Init Param: [" + paramName + " = " + paramValue + "]<br/>");
        }
    }
}
