package example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class UserAuthenticationTest {

    @Test
    public void testAuthenticateUser_Manager() {
        // Prepare test data
        String username = "john";
        String password = "password";
        List<String> employees = new ArrayList<>();
        employees.add("john(MANAGER)");
        List<String> passwords = new ArrayList<>();
        passwords.add(LoginSystem.hash(password));

        // Perform authentication
        String result = UserAuthentication.authenticateUser(username, employees, passwords, password);

        // Verify the result
        assertEquals("MANAGER", result);
    }

    @Test
    public void testAuthenticateUser_Staff() {
        // Prepare test data
        String username = "john";
        String password = "password";
        List<String> employees = new ArrayList<>();
        employees.add("john(STAFF)");
        List<String> passwords = new ArrayList<>();
        passwords.add(LoginSystem.hash(password));

        // Perform authentication
        String result = UserAuthentication.authenticateUser(username, employees, passwords, password);

        // Verify the result
        assertEquals("STAFF", result);
    }

    @Test
    public void testAuthenticateUser_UnknownUser() {
        // Prepare test data
        String username = "unknown";
        String password = "password";
        List<String> employees = new ArrayList<>();
        employees.add("john(MANAGER)");
        List<String> passwords = new ArrayList<>();
        passwords.add(LoginSystem.hash(password));

        // Perform authentication
        String result = UserAuthentication.authenticateUser(username, employees, passwords, password);

        // Verify the result
        assertEquals("unknown", result);
    }

    @Test
    public void testAuthenticateUser_IncorrectPassword() {
        // Prepare test data
        String username = "john";
        String correctPassword = "password";
        String incorrectPassword = "wrongpassword";
        List<String> employees = new ArrayList<>();
        employees.add("john(MANAGER)");
        List<String> passwords = new ArrayList<>();
        passwords.add(LoginSystem.hash(correctPassword));

        // Perform authentication with incorrect password
        String result = UserAuthentication.authenticateUser(username, employees, passwords, incorrectPassword);

        // Verify the result
        assertEquals("unknown", result);
    }
}
