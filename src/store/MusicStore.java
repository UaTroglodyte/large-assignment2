package store;

import java.io.*;
import java.util.*;

import model.Album;
import model.Song;

public class MusicStore {
    private Map<String, Album> albums;
    private Map<String, Song> songs;

    public MusicStore(String file){
        this.albums = new HashMap<>();
        this.songs = new HashMap<>();
        loadAlbums(file);
    }

    // Grabs the albums from the given file, throws FileNotfound error
    private void loadAlbums(String file){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null){
                String[] lineParts = line.split(",");
                if (lineParts.length == 2){
                    String title = lineParts[0].trim();
                    String artist = lineParts[1].trim();
                    albumDetails(title,artist);
                }
            }
            reader.close();
        } catch (Exception e) {
            System.err.println("Cannot load albums: " + e.getMessage());
        }
    }
    
    
    // Fills out album dets from the text file
    public void albumDetails(String title, String artist){
        // Had to look up how to implemets file paths in java, since never did before!
        String file = "resources/albums/" + title + "_" + artist + ".txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String info = reader.readLine();
            // Makes sure input is vaild
            if (info == null){
                reader.close();
                return;
            }
            String[] classifications  = info.split(",");
            // Makes sure follows "Album, Artist, Genre, Year" format
            if (classifications.length < 4){
                reader.close();
                return;
            }
            String genre = classifications[2].trim();
            int year = Integer.parseInt(classifications[3].trim());
            Album album = new Album(title, artist, genre, year);
            String str;
            while ((str = reader.readLine()) != null){
                str = str.trim();
                Song song = new Song(str, artist, title, genre);
                album.addSong(song);
                songs.put(str + "_" + artist, song);
            }
            albums.put(title + "_" + artist, album);
            reader.close();
        } catch (Exception e) {
            System.err.println("Cant read the album: " + title + e.getMessage());
        }
    }

    // Checks to see if song exists
    public boolean containsSong(String title, String artist){
        return songs.containsKey(title + "_" + artist);
    }


    // Checks to see if album exists
    public boolean containsAlbum(String title, String artist){
        return albums.containsKey(title + "_" + artist);
    }

    // Search User Library for song
    public List<Song> searchSong(String song){
        List<Song> userSongs = new ArrayList<>();
        for (Song title : songs.values()){
            if (title.getTitle().equalsIgnoreCase(song)){
                userSongs.add(title);
            }
        }
        return userSongs;
    }

    // Search store for album
    public Album searchAlbum(String album){
        for (Album title : albums.values()){
            if (title.getTitle().equalsIgnoreCase(album)){
                return title;
            }
        }
        // Album not found
        return null;
    }

    // Search store for songs from artist
    public List<Song> searchArtistSongs(String artist){
        List<Song> artistSongs = new ArrayList<>();
        for (Song song : songs.values()){
            if (song.getArtist().equalsIgnoreCase(artist.trim())){
                artistSongs.add(song);
            }
        }
        return artistSongs;
    }

    // Search store for albums from artists
    public List<Album> searchArtistAlbums(String artist){
        List<Album> artistAlbums = new ArrayList<>();
        for (Album album : albums.values()){
            if (album.getArtist().equalsIgnoreCase(artist)){
                artistAlbums.add(album);
            }
        }
        return artistAlbums;
    }
    
    // Gets a song from store
    public Song getSong(String title, String artist) {
        return songs.get(title + "_" + artist);
    }

    // Gets an album from store
    public Album getAlbum(String title, String artist) {
        return albums.get(title + "_" + artist);
    }
}