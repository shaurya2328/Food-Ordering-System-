public class Pasta extends FoodItem {
    public Pasta() {
        super("Pasta", 149, "🍝", "Creamy White Sauce Pasta");
    }
    @Override
    public double calculatePrice(int quantity) {
        return getPrice() * quantity;
    }
}
