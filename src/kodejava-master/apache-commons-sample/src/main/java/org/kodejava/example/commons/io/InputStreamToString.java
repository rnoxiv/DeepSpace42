package org.kodejava.example.commons.io;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class InputStreamToString {
    public static void main(String[] args) throws Exception {
        //
        // Create an input stream for reading data.txt file content.
        //
        InputStream is = new FileInputStream(new File("data.txt"));

        //
        // Get the content of an input stream as a string using UTF-8
        // as the character encoding.
        //
        try {
            String contents = IOUtils.toString(is, "UTF-8");
            System.out.println(contents);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
}
