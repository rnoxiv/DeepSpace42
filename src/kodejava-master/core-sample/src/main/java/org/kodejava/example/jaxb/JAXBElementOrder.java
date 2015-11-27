package org.kodejava.example.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class JAXBElementOrder {
    public static void main(String[] args) {
        Address address = new Address();
        address.setStreet("Sunset Road");
        address.setCity("Denpasar");
        address.setProvince("Bali");
        address.setCountry("Indonesia");
        address.setZipCode("800000");

        try {
            JAXBContext context = JAXBContext.newInstance(Address.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(address, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
