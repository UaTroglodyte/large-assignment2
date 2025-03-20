package model;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final String USER_FILE = "resources/users.txt";
    private Map<String, String> users; // Stores username -> hashed password
    private Map<String, LibraryModel> userLibraries; // Stores username -> LibraryModel
    private User currentUser;

    public UserManager() {
        this.users = new HashMap<>();
        this.userLibraries = new HashMap<>();
        loadUsers();
    }

    // Loads users from the file
    private void loadUsers() {
        File file = new File(USER_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]); // username -> hashed password
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Saves a new user to the file
    private void saveUser(String username, String hashedPassword) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(username + "," + hashedPassword);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hashes a password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Registers a new user
    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; // User already exists
        }

        String hashedPassword = hashPassword(password);
        users.put(username, hashedPassword);
        saveUser(username, hashedPassword);
        return true;
    }

    // Logs in a user and loads their library
    public boolean login(String username, String password) {
        String hashedPassword = hashPassword(password);
        if (users.containsKey(username) && users.get(username).equals(hashedPassword)) {
            currentUser = new User(username);
            if (!userLibraries.containsKey(username)) {
                userLibraries.put(username, new LibraryModel()); // Create a library if not exists
            }
            return true;
        }
        return false;
    }

    // Logs out the current user
    public void logout() {
        currentUser = null;
    }

    // Get the current user's library
    public LibraryModel getCurrentLibrary() {
        if (currentUser == null) return null;
        return userLibraries.get(currentUser.getUsername());
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
