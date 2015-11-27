package org.kodejava.example.servlet;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class SessionAttributeListener implements HttpSessionAttributeListener {

    public void attributeAdded(HttpSessionBindingEvent event) {
        //
        // This method is called when an attribute is added to a session.
        // The line below display session attribute name and its value.
        //
        System.out.println("Session attribute added: ["
                + event.getName() + "] = [" + event.getValue() + "]");
    }

    public void attributeRemoved(HttpSessionBindingEvent event) {
        //
        //  This method is called when an attribute is removed from a session.
        //
        System.out.println("Session attribute removed: ["
                + event.getName() + "] = [" + event.getValue() + "]");
    }

    public void attributeReplaced(HttpSessionBindingEvent event) {
        //
        // This method is invoked when an attibute is replaced in a session.
        //
        System.out.println("Session attribute replaced: ["
                + event.getName() + "] = [" + event.getValue() + "]");
    }
}
