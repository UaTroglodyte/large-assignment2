import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import model.Album;
import model.Song;

class TestAlbum {

	 @Test
	    void testAlbumCreation() {
	        Album album = new Album("25", "Adele", "Pop", 2015);
	        assertEquals("25", album.getTitle());
	        assertEquals("Adele", album.getArtist());
	        assertEquals("Pop", album.getGenre());
	        assertEquals(2015, album.getYear());
	        assertTrue(album.getSongs().isEmpty());
	    }

	    @Test
	    void testAddSong() {
	        Album album = new Album("25", "Adele", "Pop", 2015);
	        Song song = new Song("Hello", "Adele", "25", "Pop");
	        album.addSong(song);
	        List<Song> songs = album.getSongs();
	        assertEquals(1, songs.size());
	        assertEquals("Hello", songs.get(0).getTitle());
	    }

	    @Test
	    void testAddInvalidSongThrowsException() {
	        Album album = new Album("25", "Adele", "Pop", 2015);
	        Song song = new Song("Rolling in the Deep", "Adele", "21", "Pop"); // Wrong album
	        assertThrows(IllegalArgumentException.class, () -> album.addSong(song));
	    }

}
