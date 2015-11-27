package org.kodejava.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ClientAddressServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //
        // Get client's IP address
        //
        String clientIP = request.getRemoteAddr();

        //
        // Get client's host name
        //
        String clintHost = request.getRemoteHost();

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("IP  : " + clientIP);
        out.println("Host: " + clintHost);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
