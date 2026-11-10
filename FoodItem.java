public abstract class FoodItem implements Cloneable {
    private String name;
    private double price;
    private String emoji;
    private String description;
    private static int totalItemsCreated = 0;

    public FoodItem(String name, double price, String emoji, String description) {
        this.name = name;
        this.price = price;
        this.emoji = emoji;
        this.description = description;
        totalItemsCreated++;
    }

    // Overloaded constructor; this() calls the main constructor
    public FoodItem(String name, double price) {
        this(name, price, "", "Freshly prepared");
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getEmoji() { return emoji; }
    public String getDescription() { return description; }

    // Abstract method implemented by every child class
    public abstract double calculatePrice(int quantity);

    // Method overloading: same method name with different parameters
    public double calculateBill(int quantity) {
        return calculatePrice(quantity);
    }

    public double calculateBill(int quantity, double discountPercent) {
        double amount = calculatePrice(quantity);
        return amount - (amount * discountPercent / 100.0);
    }

    // final method cannot be overridden
    public final String getRestaurantName() { return "FOODIE HUB"; }

    public static int getTotalItemsCreated() { return totalItemsCreated; }

    // Object class method overriding
    @Override
    public String toString() { return name + " - Rs. " + price; }

    // Cloneable interface and Object.clone()
    @Override
    public FoodItem clone() {
        try {
            return (FoodItem) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
