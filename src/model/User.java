package model;
import store.MusicStore;
import java.util.*;

public class User {
    private String username;
    private LibraryModel library;

    public User(String username) {
        this.username = username;
        this.library = new LibraryModel();
    }

    public String getUsername() {
        return username;
    }

    public LibraryModel getLibrary() {
        return library;
    }

    // User-level operations (delegating to LibraryModel)
    public boolean addSong(Song song, MusicStore store) {
        return library.addSong(song, store);
    }

    public boolean addAlbum(Album album, MusicStore store) {
        return library.addAlbum(album, store);
    }

    public boolean createPlaylist(String name) {
        return library.createPlaylist(name);
    }

    public Set<Song> listSongs() {
        return library.getSong();
    }

    public Set<Album> listAlbums() {
        return library.getAlbums();
    }
}
