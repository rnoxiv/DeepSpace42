package org.kodejava.example.jasypt;

import org.jasypt.util.digest.Digester;

import java.util.Arrays;

public class DigesterDemo {
    public static void main(String[] args) {
        //
        // Creates a new instance of Digester, using the SHA-1 algorithm.
        //
        Digester digister = new Digester("SHA-1");

        byte[] message = "Hello World from Jasypt".getBytes();

        //
        // Creates a disgest from a array of byte message.
        //
        byte[] digest = digister.digest(message);

        System.out.println("Digest = " + new String(digest));
        System.out.println("Digest = " + Arrays.toString(digest));
    }
}
