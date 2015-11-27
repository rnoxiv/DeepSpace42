package org.kodejava.example.commons.beanutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recording {
    private Integer id;
    private String title;
    private List tracks = new ArrayList();
    private Map<String, Track> mapTracks = new HashMap<String, Track>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List getTracks() {
        return tracks;
    }

    public void setTracks(List tracks) {
        this.tracks = tracks;
    }

    public void setMapTracks(Map<String, Track> mapTracks) {
        this.mapTracks = mapTracks;
    }
}
