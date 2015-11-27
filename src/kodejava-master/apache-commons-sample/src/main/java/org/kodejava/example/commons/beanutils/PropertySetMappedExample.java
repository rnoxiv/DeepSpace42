package org.kodejava.example.commons.beanutils;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PropertySetMappedExample {

    public static void main(String[] args) {
        //
        // Create an instance of Recording bean.
        //
        Recording recording = new Recording();
        recording.setId(1);
        recording.setTitle("Introduction");

        //
        // Create a map to hold recording tracks.
        //
        Map<String, Track> tracks = new HashMap<String, Track>();
        tracks.put("track-one", new Track());
        tracks.put("track-two", new Track());
        tracks.put("track-three", new Track());
        recording.setMapTracks(tracks);

        try {
            //
            // We add another tracks to the recording track using
            // a PropertyUtils.setMappedProperty() method.
            //
            PropertyUtils.setMappedProperty(recording, "tracks",
                    "track-four", new Track());
            PropertyUtils.setMappedProperty(recording, "tracks",
                    "track-five", new Track());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        tracks = (Map<String, Track>) recording.getTracks();
        System.out.println("New Track Numbers: " + tracks.size());
    }
}
