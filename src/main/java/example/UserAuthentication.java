package example;
import java.util.List;

public class UserAuthentication {
    public static String authenticateUser(String username, List<String> employee, List<String> password, String inputPassword) {
        for (int i = 0; i < employee.size(); i++) {
            String storedUsername = employee.get(i);
            String storedPassword = password.get(i);

            String[] employeeDetails = storedUsername.split("\\(");
            String uname = employeeDetails[0];

            if (uname.equalsIgnoreCase(username) && LoginSystem.check(inputPassword, storedPassword)) {
                if (storedUsername.endsWith("(MANAGER)")) {
                    return "MANAGER";
                } else if (storedUsername.endsWith("(STAFF)")) {
                    return "STAFF";
                }
                break;
            }
        }
        return "unknown";
    }
}
