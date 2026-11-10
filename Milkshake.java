public class Milkshake extends FoodItem {
    public Milkshake() {
        super("Milkshake", 89, "🥤", "Cold Chocolate Shake");
    }
    @Override
    public double calculatePrice(int quantity) {
        return getPrice() * quantity;
    }
}
