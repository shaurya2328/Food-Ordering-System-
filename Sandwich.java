public class Sandwich extends FoodItem {
    public Sandwich() {
        super("Sandwich", 89, "🥪", "Fresh Grilled Sandwich");
    }
    @Override
    public double calculatePrice(int quantity) {
        return getPrice() * quantity;
    }
}
