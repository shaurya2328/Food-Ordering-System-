public class Pizza extends FoodItem {
    public Pizza() {
        super("Pizza", 199, "🍕", "Cheesy Italian Pizza");
    }
    @Override
    public double calculatePrice(int quantity) {
        return getPrice() * quantity;
    }
}
