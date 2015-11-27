package org.kodejava.example.commons.lang;

import org.apache.commons.lang.ArrayUtils;

public class ArrayUtilsExample {
    public static void main(String[] args) {
        int numbers[] = {1, 2, 3, 4, 5};
        boolean bools[] = {true, false, false, true};
        float decimals[] = {10.1f, 3.14f, 2.17f};

        // To convert from primitive arrays into object 
        // type arrays we can user Apache Commons Lang 
        // library. The commons lang provides an ArrayUtils 
        // class that did this conversion. To convert the 
        // other way just use the toPrimitive method.

        Integer numbersObj[] =
                ArrayUtils.toObject(numbers);
        Boolean boolsObj[] =
                ArrayUtils.toObject(bools);
        Float decimalsObj[] =
                ArrayUtils.toObject(decimals);
    }
}
