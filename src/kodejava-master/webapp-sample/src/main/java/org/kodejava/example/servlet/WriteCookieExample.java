package org.kodejava.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WriteCookieExample extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        //
        // Send a cookie named username to the client. There are some others
        // properties that we can set before we send the cookie, such as comments,
        // domain name, max age, path, a secure flag, etc.
        //
        Cookie cookie = new Cookie("username", "jduke");
        response.addCookie(cookie);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
    }
}
