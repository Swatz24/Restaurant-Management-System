package example;

import java.util.ArrayList;
import java.util.List;


public class UserService {
    private List<String> employee;
    private List<String> password;

    // For text colors
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String PURPLE = "\033[0;35m";
    private static final String PINK = "\033[0;35m";



    public UserService() {
        employee = new ArrayList<>();
        password = new ArrayList<>();
    }

    public UserService(List<String> employee, List<String> password) {
        this.employee = employee;
        this.password = password;

    }

    public boolean login(String username, String inputPassword) {
        for (int i = 0; i < employee.size(); i++) {
            List<String> employeeDetails = List.of(employee.get(i).split("\\("));
            String uname = employeeDetails.get(0);
            UserRole role = UserRole.valueOf(employeeDetails.get(1).replace(")", ""));

            if (username.equalsIgnoreCase(uname) && LoginSystem.check(inputPassword, password.get(i))) {
                String colorCode = (role == UserRole.MANAGER) ? "\u001B[34m" : "\u001B[35m";
                System.out.println(ANSI_GREEN+ "\nLogin Successful! \n" + ANSI_RESET +
                        "\u001B[36mWelcome " + colorCode + username.toUpperCase() + PINK + ",\033[0;35m Role: \033[0;35m" + colorCode +  role  + "\033[0;35m");
                return true;
            }
        }
        return false;
    }
}
