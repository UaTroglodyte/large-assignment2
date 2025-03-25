import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import model.Playlist;
import model.Song;

class TestPlaylist {

    @Test
    void testCreatePlaylist() {
        Playlist playlist = new Playlist("My Favorites");
        assertEquals("My Favorites", playlist.getName());
        assertTrue(playlist.getSongs().isEmpty());
    }

    @Test
    void testAddSong() {
        Playlist playlist = new Playlist("Road Trip");
        Song song = new Song("Shape of You", "Ed Sheeran", "Divide", "Pop");
        playlist.addSong(song);
        List<Song> songs = playlist.getSongs();
        assertEquals(1, songs.size());
        assertEquals("Shape of You", songs.get(0).getTitle());
    }

    @Test
    void testRemoveSong() {
        Playlist playlist = new Playlist("Chill Vibes");
        Song song = new Song("Stay", "The Kid LAROI", "F*ck Love 3", "Pop");
        playlist.addSong(song);
        assertTrue(playlist.removeSong(song));
        assertTrue(playlist.getSongs().isEmpty());
    }

}
