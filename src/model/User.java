package model;

import java.util.List;
import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private List<Song> library;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.library = new ArrayList<>();
    }

    public String getUser(){
        return username;
    }

    public String getPassword(){
        return password;
    }
     public List<Song> getLibrary(){
        return library;
    }

    public void addSong(Song song){
        library.add(song);
    }
}
