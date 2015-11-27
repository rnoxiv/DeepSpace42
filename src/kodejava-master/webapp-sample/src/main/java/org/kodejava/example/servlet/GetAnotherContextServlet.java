package org.kodejava.example.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/context"})
public class GetAnotherContextServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //
        // Get ServletContext of another application on the same Servlet
        // container. This allow us to forward request to another application
        // on the same application server.
        //
        ServletContext ctx = request.getServletContext().getContext("/otherapp");

        //
        // Set a request attribute and forward to hello.jsp page on another 
        // context.
        //
        request.setAttribute("MESSAGE", "Hello There!");
        RequestDispatcher dispatcher = ctx.getRequestDispatcher("/hello.jsp");
        dispatcher.forward(request, response);
    }
}
