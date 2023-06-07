package example;

import java.util.Arrays;
import java.util.List;

class MenuItem {
    private String name;
    private String description;
    private int prepTime;
    private double price;
    private List<String> ingredients;
    Menu menu = new Menu();
    InventoryService inventory = new InventoryService();

    public MenuItem(String name, String description, int prepTime, double price, List<String> ingredients) {
        this.name = name;
        this.description = description;
        this.prepTime = prepTime;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    // Utility method to convert MenuItem to a string with delimiters
    public String toStringWithDelimiters() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("|");
        sb.append(description).append("|");
        sb.append(prepTime).append("|");
        sb.append(price).append("|");
        for (int i = 0; i < ingredients.size(); i++) {
            sb.append(ingredients.get(i));
            if (i < ingredients.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    // Utility method to create MenuItem object from a string with delimiters
    public static MenuItem fromStringWithDelimiters(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid line format");
        }

        String name = parts[0];
        String description = parts[1];
        int prepTime = Integer.parseInt(parts[2]);
        double price = Double.parseDouble(parts[3]);
        List<String> ingredients = Arrays.asList(parts[4].split(","));

        return new MenuItem(name, description, prepTime, price, ingredients);
    }





}