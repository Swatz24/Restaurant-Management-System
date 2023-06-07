package example;

import java.io.*;
import java.util.*;


public class Menu {

    private static List<MenuItem> menuItems = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    private String MENU_FILE;

    public Menu(String s) {
        this.MENU_FILE = s;
    }

    public List<MenuItem> loadMenu() {
        try {
            File file = new File(MENU_FILE);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                menuItems.clear(); // Clear the existing menu items

                String line;
                while ((line = reader.readLine()) != null) {
                    MenuItem menuItem = MenuItem.fromStringWithDelimiters(line);
                    menuItems.add(menuItem);
                }

                reader.close();
                return menuItems;
            }
        } catch (IOException e) {
            System.out.println("Error loading menu from file: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void saveMenu() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(MENU_FILE));

            for (MenuItem menuItem : menuItems) {
                String line = menuItem.toStringWithDelimiters();
                writer.write(line);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving menu to file: " + e.getMessage());
        }
    }

    public void addItem() {
        System.out.println("Enter the item details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Preparation Time: ");
        int prepTime = Integer.parseInt(scanner.nextLine());
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character
        System.out.print("Ingredients (comma-separated): ");
        String ingredientsString = scanner.nextLine();
        List<String> ingredients = Arrays.asList(ingredientsString.split(","));

        MenuItem menuItem = new MenuItem(name, description, prepTime, price, ingredients);
        menuItems.add(menuItem);

        System.out.println("Menu item added successfully!");
    }

    public void removeItem() {
        System.out.print("Enter the name of the item to remove: ");
        String itemName = scanner.nextLine();

        boolean found = false;
        Iterator<MenuItem> iterator = menuItems.iterator();
        while (iterator.hasNext()) {
            MenuItem menuItem = iterator.next();
            if (menuItem.getName().equalsIgnoreCase(itemName)) {
                iterator.remove();
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Menu item removed successfully!");
        } else {
            System.out.println("Item not found in the menu.");
        }
    }

    public void editItem() {
        System.out.print("Enter the name of the item to edit: ");
        String itemName = scanner.nextLine();

        boolean found = false;
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getName().equalsIgnoreCase(itemName)) {
                System.out.println("Enter new values (leave blank to keep the current value):");

                System.out.print("New name (" + menuItem.getName() + "): ");
                String name = scanner.nextLine();
                if (!name.isEmpty()) {
                    menuItem.setName(name);
                }

                System.out.print("New description (" + menuItem.getDescription() + "): ");
                String description = scanner.nextLine();
                if (!description.isEmpty()) {
                    menuItem.setDescription(description);
                }

                System.out.print("New preparation time (" + menuItem.getPrepTime() + "): ");
                String prepTimeStr = scanner.nextLine();
                if (!prepTimeStr.isEmpty()) {
                    try {
                        int prepTime = Integer.parseInt(prepTimeStr);
                        menuItem.setPrepTime(prepTime);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for preparation time. Keeping the current value.");
                    }
                }

                System.out.print("New price (" + menuItem.getPrice() + "): ");
                String priceStr = scanner.nextLine();
                if (!priceStr.isEmpty()) {
                    try {
                        double price = Double.parseDouble(priceStr);
                        menuItem.setPrice(price);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for price. Keeping the current value.");
                    }
                }

                System.out.print("New ingredients (" + String.join(", ", menuItem.getIngredients()) + "): ");
                String ingredientsString = scanner.nextLine();
                if (!ingredientsString.isEmpty()) {
                    List<String> ingredients = Arrays.asList(ingredientsString.split(","));
                    menuItem.setIngredients(ingredients);
                }

                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Menu item edited successfully!");
        } else {
            System.out.println("Item not found in the menu.");
        }
    }


    public void displayMenu() {
        if (menuItems.isEmpty()) {
            System.out.println("Menu is empty.");
        } else {
            System.out.println("Menu:");
            for (MenuItem menuItem : menuItems) {
                System.out.println("\nName: " + menuItem.getName());
                System.out.println("Description: " + menuItem.getDescription());
//                System.out.println("Preparation Time: " + menuItem.getPrepTime());
                System.out.println("Price: " + menuItem.getPrice());
//                System.out.println("Ingredients: " + String.join(", ", menuItem.getIngredients()));
            }
        }
    }
}