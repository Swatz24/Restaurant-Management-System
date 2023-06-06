package example;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class UserAuthentication {
    private final Map<String, User> users;

    public UserAuthentication() {
        this.users = new HashMap<>();
    }

    public void registerUser(String username, String password, UserRole role) {
        String hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword, role);
        users.put(username, user);
        System.out.println("User registered successfully. Username: " + username);
    }

    public UserRole authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null) {
            String hashedPassword = hashPassword(password);
            if (user.getPassword().equals(hashedPassword)) {
                return user.getRole();
            }
        }
        return null;
    }

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
            e.printStackTrace();
        }
        return null;
    }
}

