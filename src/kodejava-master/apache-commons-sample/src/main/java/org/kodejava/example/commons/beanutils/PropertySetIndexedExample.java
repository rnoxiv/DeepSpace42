package org.kodejava.example.commons.beanutils;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class PropertySetIndexedExample {
    public static void main(String[] args) {
        String[] colors = new String[]{"red", "green", "blue"};

        MyBean myBean = new MyBean();
        myBean.setColors(colors);
        System.out.println("Colors = " + Arrays.toString(myBean.getColors()));

        try {
            PropertyUtils.setIndexedProperty(myBean, "colors", 1, "orange");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        System.out.println("Colors = " + Arrays.toString(myBean.getColors()));
    }
}
