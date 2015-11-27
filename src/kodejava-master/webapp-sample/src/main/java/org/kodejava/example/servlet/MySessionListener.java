package org.kodejava.example.servlet;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

public class MySessionListener implements HttpSessionListener {

    // Notification that a new session was created
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();

        System.out.println("New session created  : " + session.getId());
        System.out.println("Session creation time: " + new Date(session.getCreationTime()));
    }

    // Notification that a session was invalidated
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();

        System.out.println("Session destroyed  : " + session.getId());
    }
}
