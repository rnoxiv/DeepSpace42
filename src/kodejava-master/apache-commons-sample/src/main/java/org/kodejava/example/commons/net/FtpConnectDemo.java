package org.kodejava.example.commons.net;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

public class FtpConnectDemo {
    public static void main(String[] args) {
        FTPClient client = new FTPClient();

        try {
            client.connect("ftp.domain.com");

            //
            // When login success the login method returns true.
            //
            boolean login = client.login("admin", "secret");

            if (login) {
                System.out.println("Login success...");

                //
                // When logout success the logout method returns true.
                //
                boolean logout = client.logout();
                if (logout) {
                    System.out.println("Logout from FTP server...");
                }
            } else {
                System.out.println("Login fail...");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //
                // Closes the connection to the FTP server
                //
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
