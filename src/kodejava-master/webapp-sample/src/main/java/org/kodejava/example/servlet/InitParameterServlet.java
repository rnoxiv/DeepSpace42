package org.kodejava.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InitParameterServlet extends javax.servlet.http.HttpServlet
        implements javax.servlet.Servlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        //
        // Get application configuration path
        //
        String configPath = getInitParameter("configPath");

        System.out.println("Configuration Path: " + configPath);
    }
}
