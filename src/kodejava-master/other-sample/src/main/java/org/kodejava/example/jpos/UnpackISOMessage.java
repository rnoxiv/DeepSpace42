package org.kodejava.example.jpos;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

public class UnpackISOMessage {
    public static void main(String[] args) {
        UnpackISOMessage iso = new UnpackISOMessage();
        try {
            ISOMsg isoMsg = iso.parseISOMessage();
            iso.printISOMessage(isoMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ISOMsg parseISOMessage() throws Exception {
        String message = "02003220000000808000000010000000001500120604120000000112340001840";
        System.out.printf("Message = %s%n", message);
        try {
            GenericPackager packager = new GenericPackager("fields.xml");
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            isoMsg.unpack(message.getBytes());
            return isoMsg;
        } catch (ISOException e) {
            throw new Exception(e);
        }
    }

    public void printISOMessage(ISOMsg isoMsg) {
        try {
            System.out.printf("MTI = %s%n", isoMsg.getMTI());
            for (int i = 1; i <= isoMsg.getMaxField(); i++) {
                if (isoMsg.hasField(i)) {
                    System.out.printf("Field (%s) = %s%n", i, isoMsg.getString(i));
                }
            }
        } catch (ISOException e) {
            e.printStackTrace();
        }
    }
}
