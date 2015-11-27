package org.kodejava.example.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ParameterCheck extends HttpServlet implements Servlet {
    private static final String EMPTY = "";

    public ParameterCheck() {
        super();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        String username = EMPTY;
        String password = EMPTY;

        //
        // Check if username parameter exists
        //
        if (request.getParameterMap().containsKey("username")) {
            username = request.getParameter("username");
        }

        //
        // Check if password parameter exists
        //
        if (request.getParameterMap().containsKey("password")) {
            password = request.getParameter("password");
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
    }
}
