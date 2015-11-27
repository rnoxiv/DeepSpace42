package org.kodejava.example.commons.lang;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ReflectionToString {

    private Integer id;
    private String name;
    private String description;
    public static final String KEY = "APP-KEY";
    private transient String secretKey;

    public ReflectionToString(Integer id, String name, String description,
                              String secretKey) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        /*
         * Generate toString including transient and static attributes.
         */
        return ReflectionToStringBuilder.toString(this,
                ToStringStyle.SIMPLE_STYLE, true, true);
    }

    public static void main(String[] args) {
        ReflectionToString demo =
                new ReflectionToString(1, "MANU", "Manchester United", "Alex");
        System.out.println("Demo = " + demo);
    }
}
