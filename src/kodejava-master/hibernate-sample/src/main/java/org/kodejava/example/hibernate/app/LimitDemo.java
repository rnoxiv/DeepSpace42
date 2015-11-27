package org.kodejava.example.hibernate.app;

import org.kodejava.example.hibernate.model.Label;

import java.util.List;

public class LimitDemo {
    public static void main(String[] args) {
        LabelManager manager = new LabelManager();

        List labels = manager.getLabels(null, 1, 10);
        for (int i = 0; i < labels.size(); i++) {
            Label label = (Label) labels.get(i);
            System.out.println("Label = " + label);
        }
    }
}
