

import model.LibraryModel;
import model.Song;
import model.Playlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrackingSongTest {
    private LibraryModel library;
    private Song song1, song2, song3, song4, song5, song6, song7, song8, song9, song10, song11;

    @BeforeEach
    void setUp() {
        library = new LibraryModel();

        song1 = new Song("Song 1", "Artist A", "Album A");
        song2 = new Song("Song 2", "Artist B", "Album B");
        song3 = new Song("Song 3", "Artist C", "Album C");
        song4 = new Song("Song 4", "Artist D", "Album D");
        song5 = new Song("Song 5", "Artist E", "Album E");
        song6 = new Song("Song 6", "Artist F", "Album F");
        song7 = new Song("Song 7", "Artist G", "Album G");
        song8 = new Song("Song 8", "Artist H", "Album H");
        song9 = new Song("Song 9", "Artist I", "Album I");
        song10 = new Song("Song 10", "Artist J", "Album J");
        song11 = new Song("Song 11", "Artist K", "Album K"); // Used for testing recent limit

        // Add songs to the library
        library.addSong(song1);
        library.addSong(song2);
        library.addSong(song3);
        library.addSong(song4);
        library.addSong(song5);
        library.addSong(song6);
        library.addSong(song7);
        library.addSong(song8);
        library.addSong(song9);
        library.addSong(song10);
    }

    @Test
    void testRecentPlaysTracking() {
        // Simulate playing songs
    	assertEquals(10, library.getSong().size());
    	
    	library.addSong(song1);
        library.addSong(song2);
        library.addSong(song3);
        library.addSong(song4);
        library.addSong(song5);
        library.addSong(song6);
        library.addSong(song7);
        library.addSong(song8);
        library.addSong(song9);
        library.addSong(song10);
        library.addSong(song11);
        
        library.playSong(song1);
        library.playSong(song2);
        library.playSong(song3);
        library.playSong(song4);
        library.playSong(song5);
        library.playSong(song6);
        library.playSong(song7);
        library.playSong(song8);
        library.playSong(song9);
        library.playSong(song10);
        library.playSong(song11); // This should push song1 out of recent plays

        Playlist recentPlaylist = library.getRecentPlaysPlaylist();
        List<Song> recentSongs = recentPlaylist.getSongs();
        System.out.println("Final Recent Songs: " + recentSongs.stream().map(Song::getTitle).toList());


        assertEquals(10, recentSongs.size());
        assertFalse(recentSongs.contains(song1)); // Song 1 should have been pushed out
        assertTrue(recentSongs.contains(song11)); // Song 11 should be in the list
    }

    @Test
    void testFrequentPlaysTracking() {
        // Simulate multiple plays
        library.playSong(song2);
        library.playSong(song2);
        library.playSong(song3);
        library.playSong(song3);
        library.playSong(song3);
        library.playSong(song4);
        library.playSong(song4);
        library.playSong(song4);
        library.playSong(song4);

        Playlist frequentPlaylist = library.getMostPlayedPlaylist();
        List<Song> mostPlayedSongs = frequentPlaylist.getSongs();

        assertEquals(3, mostPlayedSongs.size()); // Only 3 songs should be tracked
        assertTrue(mostPlayedSongs.contains(song4)); // Song 4 should be at the top
        assertTrue(mostPlayedSongs.contains(song3)); // Song 3 should be second
        assertTrue(mostPlayedSongs.contains(song2)); // Song 2 should be last
    }
}
