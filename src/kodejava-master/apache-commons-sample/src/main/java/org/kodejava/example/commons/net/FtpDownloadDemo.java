package org.kodejava.example.commons.net;

import org.apache.commons.net.ftp.FTPClient;

import java.io.FileOutputStream;
import java.io.IOException;

public class FtpDownloadDemo {
    public static void main(String[] args) {
        FTPClient client = new FTPClient();
        FileOutputStream fos = null;

        try {
            client.connect("ftp.domain.com");
            client.login("admin", "secret");

            //
            // The remote filename to be downloaded.
            //
            String filename = "sitemap.xml";
            fos = new FileOutputStream(filename);

            //
            // Download file from FTP server
            //
            client.retrieveFile("/" + filename, fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
