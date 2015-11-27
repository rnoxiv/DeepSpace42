package org.kodejava.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CookieExpirationExample extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        if (username != null) {
            Cookie cookie = new Cookie("username", username);

            //
            // Set the cookie age to 600 seconds (10 minutes). Setting the age
            // to 0 will delete the cookie while giving it a negative value will
            // not store the cookie and it will be deleted when the browser is
            // closed.
            //
            cookie.setMaxAge(600);
            response.addCookie(cookie);
        }
    }
}
