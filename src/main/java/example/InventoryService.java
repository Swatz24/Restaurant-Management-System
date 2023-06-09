package example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    private Map<String, InventoryItem> inventory;
    private String inventoryFilePath;

    private Map<String, Integer> ingredients;

    // For text colors
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";

    public InventoryService() {
        ingredients = new HashMap<>();
    }

    public InventoryService(String inventoryFilePath) {
        inventory = new HashMap<>();
        ingredients = new HashMap<>();
        this.inventoryFilePath = inventoryFilePath;
        loadInventory(); // Load inventory from file when creating an instance
    }

    // Add an ingredient to the inventory
    public void addIngredient(String name, int quantity) {
        InventoryItem item = inventory.get(name);
        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            inventory.put(name, new InventoryItem(name, quantity));
        }
        saveInventory(); // Save inventory to file after modification
        System.out.println(quantity +" units of " + name + " added to the inventory list.");
    }

    //changes by Chitra for testing
    public Map<String, InventoryItem> getInventory() {
        return inventory;
    }

    // Use an ingredient from the inventory
    public void useIngredient(String name, int quantity) {
        InventoryItem item = null;
        for (Map.Entry<String, InventoryItem> entry : inventory.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(name)) {
                item = entry.getValue();
                break;
            }
        }

        if (item != null) {
            int currentQuantity = item.getQuantity();
            if (currentQuantity >= quantity) {
                item.setQuantity(currentQuantity - quantity);
            } else {
                System.out.println(ANSI_RED + "Insufficient quantity of " + name + ANSI_RESET);
            }
        } else {
            System.out.println(ANSI_RED + "Ingredient " + name + " not found" + ANSI_RESET);
        }

        saveInventory(); // Save inventory to file after modification
    }



    // Check the quantity of a specific ingredient in the inventory
    public void checkIngredientQuantity(String name) {
        InventoryItem item = inventory.get(name);
        if (item != null) {
            int quantity = item.getQuantity();
            System.out.println("Available quantity of " + name + ": " + quantity);
        } else {
            System.out.println(ANSI_RED + "Ingredient " + name + " not found" + ANSI_RESET);
        }
    }

    // Check the status of the entire inventory
    public void checkInventoryStatus() {
        System.out.println("Inventory Status:");
        inventory.forEach((name, item) -> System.out.println(name + ": " + item.getQuantity()));
    }

    // Save the inventory to a file
    private void saveInventory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inventoryFilePath))) {
            inventory.forEach((name, item) -> {
                try {
                    writer.write(name + "," + item.getQuantity()); // Write the name and quantity to the file
                    writer.newLine(); // Move to the next line
                } catch (IOException e) {
                    System.out.println("Failed to write inventory item: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.out.println("Failed to save inventory: " + e.getMessage());
        }
    }

    // Load the inventory from a file
    private void loadInventory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\CTAC\\RestaurantMgmtSystem\\untitled\\src\\main\\java\\example\\inventory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String ingredient = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());
//                    System.out.println(ingredient);
                    inventory.put(ingredient, new InventoryItem(ingredient, quantity));

                    ingredients.put(ingredient, quantity);

                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load inventory: " + e.getMessage());
        }
    }


    public boolean isIngredientAvailable(String ingredient, int quantity) {
        for (InventoryItem item : inventory.values()) {
            if (item.getName().equalsIgnoreCase(ingredient) && item.getQuantity() >= quantity) {
                return true;
            }
        }
        return false;
    }


    // Ingredient alert for all ingredient, also case-insensitive when checking the ingredient in the inventory list.
    public void checkIngredientAlert() {
//        System.out.println("Ingredient Alert:");
        inventory.forEach((name, item) -> {
            if (item.getQuantity() < 5) {
                System.out.println(ANSI_RED + "Low quantity of " + name + ": " + item.getQuantity()+ ANSI_RESET);
            }
        });
    }

    // Ingredient alert for a given ingredient, also case-insensitive when checking the ingredient in the inventory list.
    public void checkIngredientAlert(String ingredient) {
        for (Map.Entry<String, InventoryItem> entry : inventory.entrySet()) {
            String itemName = entry.getKey();
            InventoryItem item = entry.getValue();

            if (itemName.equalsIgnoreCase(ingredient) && item.getQuantity() < 5) {
                System.out.println(ANSI_RED + "Low quantity of " + itemName + ": " + item.getQuantity() + " units." + ANSI_RESET);
                break;
            }
        }
    }




}
