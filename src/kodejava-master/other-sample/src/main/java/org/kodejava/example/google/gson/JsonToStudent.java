package org.kodejava.example.google.gson;

import com.google.gson.Gson;

public class JsonToStudent {
    public static void main(String[] args) {
        String json = "{\"name\":\"Duke\",\"address\":\"Menlo Park\",\"dateOfBirth\":\"Feb 1, 2000 12:00:00 AM\"}";

        Gson gson = new Gson();
        Student student = gson.fromJson(json, Student.class);

        System.out.println("student.getName()        = " + student.getName());
        System.out.println("student.getAddress()     = " + student.getAddress());
        System.out.println("student.getDateOfBirth() = " + student.getDateOfBirth());
    }
}
