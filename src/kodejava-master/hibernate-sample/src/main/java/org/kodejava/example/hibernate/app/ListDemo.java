package org.kodejava.example.hibernate.app;

import org.kodejava.example.hibernate.model.Label;

import java.util.Date;
import java.util.List;

public class ListDemo {
    public static void main(String[] args) {
        LabelManager manager = new LabelManager();

        //
        // Creates a Label object we are going to stored in the database. We
        // set the name, modified by and modified date information.
        //
        Label label = new Label();
        label.setName("Sony Music");
        label.setCreated(new Date());

        //
        // Call the LabelManager saveLabel method.
        //
        manager.saveLabel(label);

        List labels = manager.getLabels();
        for (int i = 0; i < labels.size(); i++) {
            label = (Label) labels.get(i);
            System.out.println("Label = " + label);
        }
    }
}
