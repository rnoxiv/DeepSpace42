package org.kodejava.example.jasypt;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;

public class PasswordEncryptorDemo {
    public static void main(String[] args) {
        //
        // Creates an instance of BasicPasswordEncryptor.
        //
        PasswordEncryptor encryptor = new BasicPasswordEncryptor();

        //
        // Encrypted version of user password.
        //
        String encrypted = encryptor.encryptPassword("secret");
        System.out.println("encrypted = " + encrypted);

        //
        // Compare user's plain text password with the encrypted one to check
        // if they are match.
        //
        if (encryptor.checkPassword("secret", encrypted)) {
            System.out.println("Welcome to Jasypt");
        } else {
            System.out.println("Invalid secret word, access denied!");
        }
    }
}
