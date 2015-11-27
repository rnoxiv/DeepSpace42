package org.kodejava.example.commons.lang;

import org.apache.commons.lang.ArrayUtils;

public class ObjectArrayToPrimitiveDemo {

    public static void main(String[] args) {
        //
        // An array of integer objects.
        //
        Integer[] integers = {new Integer(1), new Integer(2), new Integer(3),
                new Integer(5), new Integer(8), new Integer(13),
                new Integer(21), new Integer(34), new Integer(55)};

        //
        // Convert array of integer objects to a primitive array of type int
        //
        int[] fibbos = ArrayUtils.toPrimitive(integers);
        System.out.println(ArrayUtils.toString(fibbos));
    }
}
