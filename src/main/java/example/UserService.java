package example;

import java.util.ArrayList;
import java.util.List;


public class UserService {
    private List<String> employee;
    private List<String> password;


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
                System.out.println("\nLogin Successful! \n" +
                        "Welcome " + colorCode + username.toUpperCase() + "\u001B[0m" + ", Role: " + role);
                return true;
            }
        }

        System.out.println("Invalid username or password! Try again");
        return false;
    }
}
