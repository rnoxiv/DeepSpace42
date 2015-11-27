package org.kodejava.example.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class JAXBElementWrapper {
    public static void main(String[] args) {
        Track track1 = new Track();
        track1.setId(1);
        track1.setTitle("Love Me Do");

        Track track2 = new Track();
        track2.setId(2);
        track2.setTitle("From Me To You");

        Track track3 = new Track();
        track3.setId(3);
        track3.setTitle("She Loves You");

        Record record = new Record();
        record.setId(1);
        record.setTitle("The Beatles 1");
        record.getTracks().add(track1);
        record.getTracks().add(track2);
        record.getTracks().add(track3);

        try {
            JAXBContext context = JAXBContext.newInstance(Record.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(record, System.out);

            Writer writer = null;
            try {
                writer = new FileWriter("Record.xml");
                marshaller.marshal(record, writer);
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
