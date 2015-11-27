package org.kodejava.example.commons.io;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.Arrays;

public class FileSortLastModified {
    public static void main(String[] args) {
        File dir = new File("/home/username/Temp");
        File[] files = dir.listFiles();

        //
        // Sort files in ascending order base on last modification date.
        //
        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            System.out.printf("File %s - %2$tm %2$te,%2$tY%n= ", file.getName(),
                    file.lastModified());
        }

        System.out.printf("--------------------------------------------------");

        //
        // Sort files in descending order base on last modification date.
        //
        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            System.out.printf("File %s - %2$tm %2$te,%2$tY%n= ", file.getName(),
                    file.lastModified());
        }
    }
}
