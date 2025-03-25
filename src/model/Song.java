package model;

public class Song{
    private String title;
    private String artist;
    private String album;
    private String genre;
    private Rating rating;


    public Song(String title, String artist, String album, String genre){
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.rating = null;
    }

    public String getTitle(){
        return title;
    }

    public String getArtist(){
        return artist;
    }

    public String getAlbum(){
        return album;
    }

    public Rating getRating(){
        return rating;
    }

    public void setRating(Rating rating){
        this.rating = rating;
    }
    
    public String getGenre() {
        return genre;
    }
    
    @Override
    public String toString() {
        return title + " - " + artist + " (Album: " + album + ")";
    }

}