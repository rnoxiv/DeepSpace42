package org.kodejava.example.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class JAXBObjectToXml {
    public static void main(String[] args) {
        Track track = new Track();
        track.setId(1);
        track.setTitle("Hey Jude");

        try {
            JAXBContext context = JAXBContext.newInstance(Track.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(track, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
