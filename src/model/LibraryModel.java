/*
 * File: LibraryModel.java
 * 
 * Purpose: This class is used as the user's library, where the user can add albums, songs, from the MusicStore, and create their own playlists.
 * I decided on using a Set for the songs and albums, since irl you cant really have duplicate things like that in your library, so a set insures that there are no dups!
 */


package model;

import java.util.*;

import store.MusicStore;

public class LibraryModel {
    private Set<Song> songs; //User song collection
    private Set<Album> albums; // User album collection
    private Map<String, Playlist> playlists; // User playlists

    /*
     * ------------------------------------------------------------------------------------------------
     * This Section is all the creation esc functions
     */

    public LibraryModel(){
        this.songs = new HashSet<>();
        this.albums = new HashSet<>();
        this.playlists = new HashMap<>();
    }


    // Adds song to user library
    public boolean addSong(Song song, MusicStore store){
        if(store.containsSong(song.getTitle(), song.getArtist())){
            return songs.add(song);
        }
        return false;
    }


    // Adds album to user library
    public boolean addAlbum(Album album, MusicStore store){
        if (store.containsAlbum(album.getTitle(), album.getArtist())){
            boolean albumAdd = albums.add(album);
            boolean songAdd = false;
            for (Song song : album.getSongs()) {
            	if (songs.add(song)) {
            		songAdd = true;
            	}
            }
            return albumAdd || songAdd;
        }
        return false;
    }
    
    // Removes a Song
    public boolean removeSong(Song song) {
        return songs.remove(song);
    }
    
    // Removes an Album
    public boolean removeAlbum(Album album) {
        return albums.remove(album);
    }


    // Creates new Playlist
    public boolean createPlaylist(String name){
        if(!playlists.containsKey(name)){
            playlists.put(name, new Playlist(name));
            return true;
        }
        return false;
    }

    // Add song to playlist
    public boolean addSongToPlaylist(String name, Song song){
        Playlist playlist = playlists.get(name);
        if (playlist != null && songs.contains(song)){
            playlist.addSong(song);
            return true;
        }
        return false;
    }

    // Remove song from playlist
    public boolean removeSongFromPlaylist(String name, Song song){
        Playlist playlist = playlists.get(name);
        return playlist.removeSong(song);
    }


    public boolean makeFavorite(Song song){
        if (songs.contains(song)){
            song.setRating(Rating.FIVE);
            return true;
        }
        return false;
    }

    public boolean rateSong(Song song, Rating rating){
        if (songs.contains(song)){
            song.setRating(rating);
            return true;
        }
        return false;
    }

    // ------------------------------------------------------------------------------------------------

    /*
     * ------------------------------------------------------------------------------------------------
     * This Section is all the seaching functions
     */

    // Search User Library for song
    public Song searchSong(String songTitle) {
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(songTitle)) {
                return song; // Return the first match found
            }
        }
        return null; // Return null if no match is found
    }

    // Search User Library for album
    public Album searchAlbum(String album){
        for (Album title : albums){
            if (title.getTitle().equalsIgnoreCase(album)){
                return title;
            }
        }
        // Album not found
        return null;
    }

    // Search User Library for songs from artist
    public List<Song> searchArtistSongs(String artist){
        List<Song> artistSongs = new ArrayList<>();
        for (Song song : songs){
            if (song.getArtist().equalsIgnoreCase(artist)){
                artistSongs.add(song);
            }
        }
        return artistSongs;
    }

    // Search User Library for albums from artists
    public List<Album> searchArtistAlbums(String artist){
        List<Album> artistAlbums = new ArrayList<>();
        for (Album album : albums){
            if (album.getArtist().equalsIgnoreCase(artist)){
                artistAlbums.add(album);
            }
        }
        return artistAlbums;
    }
    
    // Search User Library for genre
    public List<Song> searchSongsByGenre(String genre){
        List<Song> genreSongs = new ArrayList<>();
        for (Song song : songs){
            if (song.getGenre().equalsIgnoreCase(genre)){
                genreSongs.add(song);
            }
        }
        return genreSongs;
    }
    
    // Sort Songs
    public List<Song> getLibrarySorted(Comparator<Song> comparator) {
        return songs.stream().sorted(comparator).toList();
    }
    
    // Shuffle songs in the library
    public void shuffleLibrary() {
        List<Song> shuffled = new ArrayList<>(songs);
        Collections.shuffle(shuffled);
        songs = new LinkedHashSet<>(shuffled);
    }

    // Shuffle songs in a specific playlist
    public void shufflePlaylist(String playlistName) {
        Playlist playlist = playlists.get(playlistName);
        if (playlist != null) {
            playlist.shuffle();
        }
    }
    
    // Generate automatic playlists
    public void generateAutomaticPlaylists() {
        Playlist favorite = new Playlist("Favorite Songs");
        Playlist topRated = new Playlist("Top Rated");
        Map<String, Playlist> genrePlaylists = new HashMap<>();

        for (Song song : songs) {
            if (song.getRating() == Rating.FIVE) {
                favorite.addSong(song);
            }
            if (song.getRating().getValue() >= 4) {
                topRated.addSong(song);
            }
            genrePlaylists.computeIfAbsent(song.getGenre(), k -> new Playlist(k)).addSong(song);
        }

       // Add the Favorite Songs and Top Rated playlists
        playlists.put("Favorite Songs", favorite);
        playlists.put("Top Rated", topRated);

        // Add genre playlists only if they have 10 or more songs
        for (Map.Entry<String, Playlist> entry : genrePlaylists.entrySet()) {
            String genre = entry.getKey();
            Playlist playlist = entry.getValue();
            if (playlist.getSongs().size() >= 10) {
                playlists.put(genre, playlist);
            }
        }
    }

    // Search for User playlist
    public Playlist searchPlaylist(String name){
        return playlists.get(name);
    }

    // ------------------------------------------------------------------------------------------------


    /*
     * ------------------------------------------------------------------------------------------------
     * This Section is all the listing functions
     */    

    // List all songs in user library
    public List<String> listSongs(){
        List<String> userSongs = new ArrayList<>();
        for (Song song : songs){
            userSongs.add(song.getTitle());
        } 
        return userSongs;
    }
    // List all albums in user library
    public List<String> listAlbums(){
        List<String> userAlbums = new ArrayList<>();
        for (Album album : albums){
            userAlbums.add(album.getTitle());
        } 
        return userAlbums;
    }

    // List all artist in user library
    public Set<String> listArtists(){
        Set<String> userArtists = new HashSet<>();
        for (Song song : songs){
            userArtists.add(song.getArtist());
        }
        return userArtists;
    }

    // Lists all playlists
    public Set<String> listPlaylist(){
        return playlists.keySet();
    }

    // Lists favorite songs
    public List<Song> listFavorite(){
        List<Song> favs = new ArrayList<>();
        for (Song song : songs){
            if (song.getRating() == Rating.FIVE){
                favs.add(song);
            }
        }
        return favs;
    }

    // ------------------------------------------------------------------------------------------------
    
    
    // Basic getters section
    public Set<Song> getSong(){
        return Collections.unmodifiableSet(songs);
    }
    
    public Set<Album> getAlbums(){
        return Collections.unmodifiableSet(albums);
    }

    public Map<String, Playlist> getPlaylist(){
        return Collections.unmodifiableMap(playlists);
    }

    @Override
    public String toString(){
        return "Library:\nSongs: " + songs + "\nAlbums: " + albums + "\nPlaylists: " + playlists.keySet();
    }
}