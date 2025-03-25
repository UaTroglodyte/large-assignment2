package view;

import model.*;
import store.MusicStore;
import java.util.*;

/**
 * MusicLibraryUI is a text-based user interface for interacting with the user's music library.
 * It communicates with the LibraryModel and MusicStore to search, add, and manage songs, albums, and playlists.
 *
 * NOTE: Portions of this code were generated using AI assistance and were reviewed and tested for correctness.
 */
public class UserInterface {
    private Scanner scanner;
    private LibraryModel library;
    private MusicStore store;
    private UserManager userManager;
    private User currentUser;

    public UserInterface(LibraryModel library, MusicStore store, UserManager userManager) {
        this.scanner = new Scanner(System.in);
        this.library = library;
        this.store = store;
        this.userManager = userManager;
        this.currentUser = null;
    }

    public void start() {
        while (true) {
            if (currentUser == null) {
                authenticateUser();
            } else {
                mainMenu();
            }
        }
    }

    private void authenticateUser() {
        System.out.println("Welcome! Please log in or register.");
        System.out.println("1. Log In");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                logIn();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }

    private void logIn() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        if (userManager.login(username, password)) {
            currentUser = new User(username);
            System.out.println("Login successful! Welcome, " + currentUser.getUsername() + "!");
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private void register() {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter new password: ");
        String password = scanner.nextLine();

        if (userManager.registerUser(username, password)) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Username already exists. Try again.");
        }
    }

    private void mainMenu() {
        System.out.println("\nMusic Library Menu:");
        System.out.println("1. Search for a Song");
        System.out.println("2. Search for an Album");
        System.out.println("3. Add Song to Library");
        System.out.println("4. Add Album to Library");
        System.out.println("5. Create Playlist");
        System.out.println("6. Add Song to Playlist");
        System.out.println("7. Rate a Song");
        System.out.println("8. Mark Song as Favorite");
        System.out.println("9. List Favorite Songs");
        System.out.println("10. List Songs in Library");
        System.out.println("11. List Playlists");
        System.out.println("12. List Album from Library");
        System.out.println("13. List Artists in Library");
        System.out.println("14. Shuffle Library");
        System.out.println("15. Play a Song");
        System.out.println("16. View Most Played Songs");
        System.out.println("17. View Recently Played Songs");
        System.out.println("18. Generate Auto Playlists");
        System.out.println("19. Remove Song from Library");
        System.out.println("20. Remove Album from Library");
        System.out.println("21. View Sorted Songs");
        System.out.println("22. Search By Genre");
        System.out.println("23. Log Out");
        System.out.println("24. Exit");
        System.out.print("Enter choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                searchSongOption();
                break;
            case 2:
                searchAlbumOption();
                break;
            case 3:
                addSongToLibrary();
                break;
            case 4:
                addAlbumToLibrary();
                break;
            case 5:
                createPlaylist();
                break;
            case 6:
                addSongToPlaylist();
                break;
            case 7:
                rateSong();
                break;
            case 8:
                markSongAsFavorite();
                break;
            case 9:
                listFavoriteSongs();
                break;
            case 10:
                listSongs();
                break;
            case 11:
                viewPlaylists();
                break;
            case 12:
                listAlbums();
                break;
            case 13:
                listArtists();
                break;
            case 14:
                library.shuffleLibrary();
                System.out.println("Library shuffled.");
                break;
            case 15:
                playSong();
                break;
            case 16:
                viewMostPlayed();
                break;
            case 17:
                viewRecentlyPlayed();
                break;
            case 18:
                library.generateAutomaticPlaylists();
                System.out.println("Auto playlists generated.");
                break;
            case 19:
            	removeSongFromLibrary();
            	break;
            case 20:
            	removeAlbumFromLibrary();
            	break;
            case 21:
            	viewSortedSongs();
            	break;
            case 22:
            	searchByGenre();
            	break;
            case 23:
                logOut();
                break;
            case 24:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }
    
    private void playSong() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();
        Song song = library.searchSong(title);
        if (song != null) {
            library.playSong(song);
            System.out.println("Playing: " + song.getTitle());
        } else {
            System.out.println("Song not found in library.");
        }
    }
    
    private void viewMostPlayed() {
        Playlist mostPlayed = library.getMostPlayedPlaylist();
        System.out.println("Most Played Songs:");
        for (Song song : mostPlayed.getSongs()) {
            System.out.println(song.getTitle());
        }
    }
    
    private void viewRecentlyPlayed() {
        Playlist recent = library.getRecentPlaysPlaylist();
        System.out.println("Recently Played Songs:");
        for (Song song : recent.getSongs()) {
            System.out.println(song.getTitle());
        }
    }
    
    private void removeSongFromLibrary() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();
        Song song = library.searchSong(title);
        if (song != null && library.removeSong(song)) {
            System.out.println("Song removed from library.");
        } else {
            System.out.println("Could not remove song.");
        }
    }

    private void removeAlbumFromLibrary() {
        System.out.print("Enter album title: ");
        String title = scanner.nextLine();
        Album album = library.searchAlbum(title);
        if (album != null && library.removeAlbum(album)) {
            System.out.println("Album removed from library.");
        } else {
            System.out.println("Could not remove album.");
        }
    }
    
    private void searchByGenre() {
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        List<Song> songs = library.searchSongsByGenre(genre);
        if (songs.isEmpty()) {
            System.out.println("No songs found for genre: " + genre);
        } else {
            System.out.println("Songs in genre " + genre + ":");
            for (Song song : songs) {
                System.out.println(" - " + song.getTitle() + " by " + song.getArtist());
            }
        }
    }
    
    private void viewSortedSongs() {
        System.out.println("Sort songs by:");
        System.out.println("1. Title");
        System.out.println("2. Artist");
        System.out.println("3. Rating");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        Comparator<Song> comparator;

        switch (choice) {
            case 1:
                comparator = Comparator.comparing(Song::getTitle);
                break;
            case 2:
                comparator = Comparator.comparing(Song::getArtist);
                break;
            case 3:
                comparator = Comparator.comparing(s -> s.getRating() != null ? s.getRating().getValue() : 0);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        List<Song> sortedSongs = library.getLibrarySorted(comparator);
        if (sortedSongs.isEmpty()) {
            System.out.println("Your library has no songs.");
        } else {
            System.out.println("🎵 Sorted Songs:");
            for (Song song : sortedSongs) {
                String rating = song.getRating() != null ? song.getRating().toString() : "Unrated";
                System.out.println(" - " + song.getTitle() + " by " + song.getArtist() + " (Rating: " + rating + ")");
            }
        }
    }
    
    private void searchSongOption() {
        System.out.println("Search by:");
        System.out.println("1. Song Title");
        System.out.println("2. Artist Name");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            searchSong();
        } else if (choice == 2) {
            searchSongByArtist();
        } else {
            System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private void searchSong() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();
        List<Song> results = store.searchSong(title);

        if (results.isEmpty()) {
            System.out.println("No matching songs found.");
            return;
        }

        results.forEach(System.out::println);

        System.out.print("Would you like to view the album for one of these songs? (yes/no): ");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            System.out.print("Enter the artist of the song you want to view the album for: ");
            String artist = scanner.nextLine();

            Song selected = null;
            for (Song s : results) {
                if (s.getArtist().equalsIgnoreCase(artist)) {
                    selected = s;
                    break;
                }
            }

            if (selected == null) {
                System.out.println("Artist not found for that song title.");
                return;
            }

            Album album = store.getAlbum(selected.getAlbum(), selected.getArtist());

            if (album != null) {
                System.out.println("Album Info:");
                System.out.println(album);

                if (library.searchAlbum(album.getTitle()) != null) {
                    System.out.println("This album is already in your library.");
                } else {
                    System.out.println("This album is NOT in your library.");
                }
            } else {
                System.out.println("Album details not found in store.");
            }
        }
    }
    
    private void searchSongByArtist() {
        System.out.print("Enter artist name: ");
        String artist = scanner.nextLine();
        List<Song> results = store.searchArtistSongs(artist);
        if (results.isEmpty()) {
            System.out.println("No songs found for artist " + artist);
        } else {
            results.forEach(System.out::println);
        }
    }
    
    private void searchAlbumOption() {
        System.out.println("Search for an album by:");
        System.out.println("1. Album Title");
        System.out.println("2. Artist Name");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            searchAlbumByTitle();
        } else if (choice == 2) {
            searchAlbumByArtist();
        } else {
            System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private void searchAlbumByTitle() {
        System.out.print("Enter album title: ");
        String title = scanner.nextLine();
        Album album = store.searchAlbum(title);
        if (album != null) {
            System.out.println(album);
        } else {
            System.out.println("Album not found.");
        }
    }
    
    private void searchAlbumByArtist() {
        System.out.print("Enter artist name: ");
        String artist = scanner.nextLine();
        List<Album> results = store.searchArtistAlbums(artist);
        if (results.isEmpty()) {
            System.out.println("No albums found for artist " + artist);
        } else {
            results.forEach(System.out::println);
        }
    }

    private void addSongToLibrary() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine();

        Song song = store.getSong(title, artist);

        if (song == null) {
            System.out.println("Song not found in MusicStore!");
            return;
        }

        System.out.println("Found song -> " + song.getTitle() + " by " + song.getArtist());
        
        boolean added = library.addSong(song, store);

        if (added) {
            System.out.println("Song added successfully!");
        } else {
            System.out.println("Could not add song. It might already be in the library or doesn't exist in the store.");
        }
    }


    private void addAlbumToLibrary() {
        System.out.print("Enter album title: ");
        String title = scanner.nextLine();
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine();
        Album album = store.getAlbum(title, artist);
        if (album != null && library.addAlbum(album, store)) {
            System.out.println("Album added successfully!");
        } else {
            System.out.println("Could not add album.");
        }
    }

    private void createPlaylist() {
        System.out.print("Enter playlist name: ");
        String name = scanner.nextLine();
        if (library.createPlaylist(name)) {
            System.out.println("Playlist created successfully!");
        } else {
            System.out.println("Playlist already exists.");
        }
    }

    private void addSongToPlaylist() {
        System.out.print("Enter playlist name: ");
        String playlist = scanner.nextLine();
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine();
        Song song = store.getSong(title, artist);
        if (song != null && library.addSongToPlaylist(playlist, song)) {
            System.out.println("Song added to playlist.");
        } else {
            System.out.println("Could not add song.");
        }
    }

    private void listFavoriteSongs() {
        List<Song> favorites = library.listFavorite();
        if (favorites.isEmpty()) {
            System.out.println("No favorite songs found.");
        } else {
            for (Song song : favorites) {
            	System.out.println(song.getTitle());
            }
        }
    }

    private void rateSong() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();
        System.out.print("Enter rating (1-5): ");
        int rating = scanner.nextInt();
        scanner.nextLine();

        Song song = library.searchSong(title);
        
        if (song != null && rating >= 1 && rating <= 5) {
            song.setRating(Rating.values()[rating - 1]);
            System.out.println("Rating updated.");

            //  If rating is 5, automatically mark as favorite
            if (rating == 5) {
                boolean favoriteAdded = library.makeFavorite(song);
                if (favoriteAdded) {
                    System.out.println("This song is now marked as a favorite!");
                } else {
                    System.out.println("Song was already a favorite.");
                }
            }
        } else {
            System.out.println("Invalid rating or song not found.");
        }
    }
    
    private void listSongs() {
        List<String> songs = library.listSongs();
        if (songs.isEmpty()) {
            System.out.println("📭 Your library has no songs.");
        } else {
            System.out.println("🎶 Songs in your library:");
            for (String song : songs) {
                System.out.println("   🎵 " + song);
            }
        }
    }
    
    private void viewPlaylists() {
        Set<String> playlists = library.listPlaylist();
        if (playlists.isEmpty()) {
            System.out.println("You have no playlists.");
        } else {
            System.out.println("Your playlists:");
            for (String name : playlists) {
                System.out.println( name);
            }
        }
    }
    
    private void listAlbums() {
        List<String> albums = library.listAlbums();
        if (albums.isEmpty()) {
            System.out.println("Your library has no albums.");
        } else {
            System.out.println("Albums in your library:");
            for (String album : albums) {
                System.out.println("   🎵 " + album);
            }
        }
    }
    
    private void listArtists() {
        Set<String> artists = library.listArtists();
        if (artists.isEmpty()) {
            System.out.println("Your library has no artists.");
        } else {
            System.out.println("Artists in your library:");
            for (String artist : artists) {
                System.out.println("   🎤 " + artist);
            }
        }
    }

    private void markSongAsFavorite() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine();
        Song song = store.getSong(title, artist);
        if (song != null && library.makeFavorite(song)) {
            System.out.println("Song marked as favorite.");
        } else {
            System.out.println("Could not mark song as favorite.");
        }
    }

    private void logOut() {
        System.out.println("Logging out...");
        currentUser = null;
    }
    
    public static void main(String[] args) {
        // Initialize Music Store with the path to albums file
        MusicStore store = new MusicStore("resources/albums/albums.txt"); 
        
        // Initialize User's Library
        LibraryModel library = new LibraryModel();

        UserManager userManager = new UserManager();
        
        // Start the User Interface
        UserInterface ui = new UserInterface(library, store, userManager);
        ui.start();
    }
}