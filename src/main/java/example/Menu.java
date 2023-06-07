package example;

import java.io.*;
import java.util.*;


public class Menu {

    private static List<MenuItem> menuItems = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    private String MENU_FILE;

    public Menu(String s) {
        this.MENU_FILE = s;
        System.out.println(MENU_FILE);
        loadMenu();
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

    public void addItem(MenuItem menuItem) {
        menuItems.add(menuItem);
        System.out.println("Menu item added successfully!");
        saveMenu(); // Save the updated menu to file
    }

    public void removeItem(String itemName) {
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
            saveMenu(); // Save the updated menu to file
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
                int prepTime = Integer.parseInt(scanner.nextLine());
                if (prepTime != 0) {
                    menuItem.setPrepTime(prepTime);
                }

                System.out.print("New price (" + menuItem.getPrice() + "): ");
                String priceStr = scanner.nextLine();
                if (!priceStr.isEmpty()) {
                    double price = Double.parseDouble(priceStr);
                    menuItem.setPrice(price);
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
                System.out.println("Preparation Time: " + menuItem.getPrepTime());
                System.out.println("Price: " + menuItem.getPrice());
                System.out.println("Ingredients: " + String.join(", ", menuItem.getIngredients()));
            }
        }
    }
}
