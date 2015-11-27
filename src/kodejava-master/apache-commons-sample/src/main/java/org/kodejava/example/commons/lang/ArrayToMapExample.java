package org.kodejava.example.commons.lang;

import org.apache.commons.lang.ArrayUtils;

import java.util.Map;

public class ArrayToMapExample {

    public static void main(String[] args) {
        //
        // A two dimensional array of countries capital.
        //
        String[][] countries = {{"United States", "New York"},
                {"United Kingdom", "London"},
                {"Netherlands", "Amsterdam"},
                {"Japan", "Tokyo"},
                {"France", "Paris"}};

        //
        // To convert an array to a Map each array elements must 
        // be an array with at least two elements where the first 
        // element will be the key and the second element will be 
        // the value.
        //
        Map countryCapitals = ArrayUtils.toMap(countries);

        System.out.println("Capital of Japan is " + countryCapitals.get("Japan"));
        System.out.println("Capital of France is " + countryCapitals.get("France"));
    }
}
