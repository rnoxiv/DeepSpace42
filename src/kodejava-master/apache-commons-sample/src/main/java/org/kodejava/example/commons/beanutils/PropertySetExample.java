package org.kodejava.example.commons.beanutils;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

public class PropertySetExample {
    public static void main(String[] args) {
        Track track = new Track();

        try {
            PropertyUtils.setProperty(track, "id", Long.valueOf(10));
            PropertyUtils.setProperty(track, "title", "Hey Jude");
            PropertyUtils.setProperty(track, "duration", Integer.valueOf(180));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        System.out.println("Track = " + track);
    }
}
