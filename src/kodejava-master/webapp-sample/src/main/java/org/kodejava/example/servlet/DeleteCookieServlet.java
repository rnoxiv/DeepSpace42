package org.kodejava.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteCookieServlet", urlPatterns = "/deleteCookie")
public class DeleteCookieServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //
        // To delete a cookie, we need to create a cookie that have the same
        // name with the cookie that we want to delete. We also need to set
        // the max age of the cookie to 0 and then add it to the Servlet's
        // response method.
        //
        Cookie cookie = new Cookie("username", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
