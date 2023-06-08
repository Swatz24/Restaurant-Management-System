//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static List<MenuItem> menuItems = new ArrayList();
    Scanner scanner;
    private String MENU_FILE;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.MENU_FILE = "/Users/minhsmair/SwathiTesting/TestingRestaurant/src/main/java/example/menu.txt";
    }

    public List<MenuItem> loadMenu() {
        try {
            File file = new File(this.MENU_FILE);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                menuItems.clear();

                String line;
                while((line = reader.readLine()) != null) {
                    MenuItem menuItem = MenuItem.fromStringWithDelimiters(line);
                    menuItems.add(menuItem);
                }

                reader.close();
                return menuItems;
            }
        } catch (IOException var5) {
            System.out.println("Error loading menu from file: " + var5.getMessage());
        }

        return new ArrayList();
    }

    public void saveMenu() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.MENU_FILE));

            for(int i = 0; i < menuItems.size(); ++i) {
                MenuItem menuItem = (MenuItem)menuItems.get(i);
                String line = i + 1 + ". " + menuItem.toStringWithDelimiters();
                writer.write(line);
                writer.newLine();
            }

            writer.close();
        } catch (IOException var5) {
            System.out.println("Error saving menu to file: " + var5.getMessage());
        }

    }

    public void addItem() {
        System.out.println("Enter the item details:");
        System.out.print("Name: ");
        String name = this.scanner.nextLine();
        System.out.print("Description: ");
        String description = this.scanner.nextLine();
        System.out.print("Preparation Time: ");
        int prepTime = Integer.parseInt(this.scanner.nextLine());
        System.out.print("Price: ");
        double price = this.scanner.nextDouble();
        this.scanner.nextLine();
        System.out.print("Ingredients (comma-separated): ");
        String ingredientsString = this.scanner.nextLine();
        List<String> ingredients = Arrays.asList(ingredientsString.split(","));
        MenuItem menuItem = new MenuItem(name, description, prepTime, price, ingredients);
        menuItems.add(menuItem);
        System.out.println("Menu item added successfully!");
    }

    public void removeItem() {
        System.out.print("Enter the name of the item to remove: ");
        String itemName = this.scanner.nextLine();
        boolean found = false;
        Iterator<MenuItem> iterator = menuItems.iterator();

        while(iterator.hasNext()) {
            MenuItem menuItem = (MenuItem)iterator.next();
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
        String itemName = this.scanner.nextLine();
        boolean found = false;
        Iterator var3 = menuItems.iterator();

        while(var3.hasNext()) {
            MenuItem menuItem = (MenuItem)var3.next();
            if (menuItem.getName().equalsIgnoreCase(itemName)) {
                System.out.println("Enter new values (leave blank to keep the current value):");
                System.out.print("New name (" + menuItem.getName() + "): ");
                String name = this.scanner.nextLine();
                if (!name.isEmpty()) {
                    menuItem.setName(name);
                }

                System.out.print("New description (" + menuItem.getDescription() + "): ");
                String description = this.scanner.nextLine();
                if (!description.isEmpty()) {
                    menuItem.setDescription(description);
                }

                System.out.print("New preparation time (" + menuItem.getPrepTime() + "): ");
                String prepTimeStr = this.scanner.nextLine();
                if (!prepTimeStr.isEmpty()) {
                    try {
                        int prepTime = Integer.parseInt(prepTimeStr);
                        menuItem.setPrepTime(prepTime);
                    } catch (NumberFormatException var12) {
                        System.out.println("Invalid input for preparation time. Keeping the current value.");
                    }
                }

                System.out.print("New price (" + menuItem.getPrice() + "): ");
                String priceStr = this.scanner.nextLine();
                if (!priceStr.isEmpty()) {
                    try {
                        double price = Double.parseDouble(priceStr);
                        menuItem.setPrice(price);
                    } catch (NumberFormatException var11) {
                        System.out.println("Invalid input for price. Keeping the current value.");
                    }
                }

                System.out.print("New ingredients (" + String.join(", ", menuItem.getIngredients()) + "): ");
                String ingredientsString = this.scanner.nextLine();
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
            Iterator var1 = menuItems.iterator();

            while(var1.hasNext()) {
                MenuItem menuItem = (MenuItem)var1.next();
                System.out.println("\nName: " + menuItem.getName());
                System.out.println("Description: " + menuItem.getDescription());
                System.out.println("Price: " + menuItem.getPrice());
            }
        }

    }
    public MenuItem getMenuItemByName(String name) {
        name = name.trim(); // Remove leading/trailing spaces from the item name
        for (MenuItem menuItem : menuItems) {

            if (menuItem.getName().equalsIgnoreCase(name)) {

                return menuItem;
            }
        }
        return null; // If no menu item with the given name is found
    }


    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public List<String> getMenuItemIngredients(String itemName) {
        List<String> ingredients = new ArrayList<>();

        MenuItem menuItem = getMenuItemByName(itemName);
        if (menuItem != null) {
            ingredients = menuItem.getIngredients();
        }

        return ingredients;
    }
}
