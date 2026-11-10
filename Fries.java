public class Fries extends FoodItem {
    public Fries() {
        super("French Fries", 79, "🍟", "Crispy Golden Fries");
    }
    @Override
    public double calculatePrice(int quantity) {
        return getPrice() * quantity;
    }
}
