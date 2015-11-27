package org.kodejava.example.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet implements Servlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        doLogin(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        doLogin(request, response);
    }

    protected void doLogin(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {

        //
        // Here we read the parameters from servlet request
        //
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        PrintWriter pw = response.getWriter();
        if (username != null && username.equals("administrator")
                && password != null && password.equals("secret")) {
            // authentication accepted!
            pw.println("Success!");
        } else {
            // authentication denied!
            pw.println("Denied!");
        }
        pw.close();
    }
}	
