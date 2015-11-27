package org.kodejava.example.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;

public class JAXBElementAttribute {
    public static void main(String[] args) {
        Student student = new Student();
        student.setId(1);
        student.setName("Alice");
        student.setGrade(12);

        try {
            JAXBContext context = JAXBContext.newInstance(Student.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(student, new FileWriter("Student.xml"));
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }
}
