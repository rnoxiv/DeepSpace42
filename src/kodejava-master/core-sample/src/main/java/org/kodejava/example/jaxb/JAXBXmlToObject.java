package org.kodejava.example.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JAXBXmlToObject {
    public static void main(String[] args) {
        try {
            File file = new File("Track.xml");
            JAXBContext context = JAXBContext.newInstance(Track.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            Track track = (Track) unmarshaller.unmarshal(file);

            System.out.println("Track = " + track);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
