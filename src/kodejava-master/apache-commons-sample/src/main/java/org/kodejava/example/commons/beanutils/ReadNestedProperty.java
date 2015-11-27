package org.kodejava.example.commons.beanutils;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

public class ReadNestedProperty {
    public static void main(String[] args) {
        Track track = new Track();
        track.setId(1);
        track.setTitle("All My Loving");

        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Beatles");

        track.setArtist(artist);

        try {
            String artistName = (String) PropertyUtils.getNestedProperty(track, "artist.name");
            System.out.println("Artist Name = " + artistName);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
