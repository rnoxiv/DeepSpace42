package org.kodejava.example.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class JAXBRootElementName {
    public static void main(String[] args) {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Johnny Mnemonic");

        Address address = new Address();
        address.setStreet("Sunset Road");
        address.setCity("Denpasar");
        address.setProvince("Bali");
        address.setCountry("Indonesia");
        address.setZipCode("800000");
        customer.setAddress(address);

        try {
            JAXBContext context = JAXBContext.newInstance(Customer.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(customer, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
