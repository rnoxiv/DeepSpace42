package org.kodejava.example.jasypt;

import org.jasypt.util.text.BasicTextEncryptor;

public class TextEncryptorDemo {
    public static void main(String[] args) {
        String text = "The quick brown fox jumps over the lazy dog";
        System.out.println("Text      = " + text);

        BasicTextEncryptor bte = new BasicTextEncryptor();
        bte.setPassword("HelloWorld");

        String encrypted = bte.encrypt(text);
        System.out.println("Encrypted = " + encrypted);

        String original = bte.decrypt(encrypted);
        System.out.println("Original  = " + original);
    }
}
