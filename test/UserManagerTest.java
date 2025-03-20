import model.UserManager;
import model.LibraryModel;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {
    private UserManager userManager;
    private static final String ORIGINAL_USER_FILE = "resources/users.txt"; 
    private static final String BACKUP_USER_FILE = "resources/users_backup.txt"; 

    @BeforeEach
    void setUp() {
        try {
            // Backup original user file if it exists
            Path originalPath = Paths.get(ORIGINAL_USER_FILE);
            Path backupPath = Paths.get(BACKUP_USER_FILE);
            if (Files.exists(originalPath)) {
                Files.copy(originalPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Clear the user file for test isolation
            Files.write(originalPath, new byte[0]); // Empty the file
        } catch (IOException e) {
            fail("Failed to set up test file.");
        }

        userManager = new UserManager(); // Will read from the hardcoded path
    }

    @Test
    void testRegisterUser_Success() {
        assertTrue(userManager.registerUser("Alice", "password123"), "User should be registered successfully");
        assertFalse(userManager.registerUser("Alice", "password123"), "Duplicate username should not be allowed");
    }

    @Test
    void testLogin_Success() {
        userManager.registerUser("Alice", "password123");
        assertTrue(userManager.login("Alice", "password123"), "Login should be successful with correct credentials");
    }

    @Test
    void testLogin_Fail_WrongPassword() {
        userManager.registerUser("Alice", "password123");
        assertFalse(userManager.login("Alice", "wrongpassword"), "Login should fail with incorrect password");
    }

    @Test
    void testLogin_Fail_NonExistentUser() {
        assertFalse(userManager.login("Bob", "password123"), "Login should fail for non-existent user");
    }

    @Test
    void testLogout() {
        userManager.registerUser("Alice", "password123");
        userManager.login("Alice", "password123");
        userManager.logout();
        assertNull(userManager.getCurrentUser(), "Current user should be null after logout");
    }

    @Test
    void testLibraryModel_UniquePerUser() {
        userManager.registerUser("Alice", "password123");
        userManager.registerUser("Bob", "securePass");

        userManager.login("Alice", "password123");
        LibraryModel aliceLibrary = userManager.getCurrentLibrary();
        assertNotNull(aliceLibrary, "Alice should have a library");

        userManager.logout();
        userManager.login("Bob", "securePass");
        LibraryModel bobLibrary = userManager.getCurrentLibrary();
        assertNotNull(bobLibrary, "Bob should have a library");

        assertNotSame(aliceLibrary, bobLibrary, "Each user should have a unique library instance");
    }

    @AfterEach
    void tearDown() {
        try {
            Path originalPath = Paths.get(ORIGINAL_USER_FILE);
            Path backupPath = Paths.get(BACKUP_USER_FILE);

            // Restore the original user file
            if (Files.exists(backupPath)) {
                Files.copy(backupPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
                Files.delete(backupPath); // Remove backup after restoration
            } else {
                Files.deleteIfExists(originalPath); // If no backup, just delete the test file
            }
        } catch (IOException e) {
            fail("Failed to restore the original user file.");
        }
    }
}
