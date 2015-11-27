package org.kodejava.example.google.gson;

import com.google.gson.Gson;

import java.util.Calendar;

public class StudentToJson {
    public static void main(String[] args) {
        Calendar dob = Calendar.getInstance();
        dob.set(2000, 1, 1, 0, 0, 0);
        Student student = new Student("Duke", "Menlo Park", dob.getTime());

        Gson gson = new Gson();
        String json = gson.toJson(student);
        System.out.println("json = " + json);
    }
}
