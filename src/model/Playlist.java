/*
 * File: Playlist.java
 * 
 * Purpose: This is the Playlist class, that makes a playlist given a name from user.
 * Then stores songs the user chooses into that playlist 
 */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist{
    private String name;
    private List<Song> songs;

    public Playlist(String name){
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song){
        songs.add(song);
    }
    
    public void addSongs(List<Song> songs) {
        this.songs.addAll(songs);
    }

    public boolean removeSong(Song song){
        return songs.remove(song);
    }

    public void clearSongs() {
        songs.clear();
    }

    public String getName(){
        return name;
    }

    public List<Song> getSongs(){
        return Collections.unmodifiableList(songs);
    }
}
