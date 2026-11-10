public class Burger extends FoodItem {
    public Burger() {
        super("Burger", 99, "🍔", "Classic Veg Burger");
    }
    @Override
    public double calculatePrice(int quantity) {
        return getPrice() * quantity;
    }
}
