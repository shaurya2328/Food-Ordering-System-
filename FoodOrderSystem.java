import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FoodOrderSystem extends JFrame {
    private final Color background = new Color(248, 247, 244);
    private final Color red = new Color(190, 65, 48);
    private final Color dark = new Color(40, 42, 48);
    private final ArrayList<CartItem> cart = new ArrayList<>();
    private final FoodItem[] menu = {
        new Pizza(), new Burger(), new Fries(),
        new Sandwich(), new Pasta(), new Milkshake()
    };

    private DefaultTableModel model;
    private JTable cartTable;
    private JLabel totalLabel, itemCount;
    private JSpinner quantitySpinner;

    public FoodOrderSystem() {
        setTitle("Foodie Hub - Food Ordering System");
        setSize(1050, 720);
        setMinimumSize(new Dimension(850, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(background);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(dark);
        header.setBorder(BorderFactory.createEmptyBorder(22, 30, 22, 30));
        JLabel logo = new JLabel("<html><b>FOODIE HUB</b><br><font size='3'>Fresh food, happy moments!</font></html>");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 25));
        header.add(logo, BorderLayout.WEST);
        JLabel online = new JLabel("● OPEN NOW");
        online.setForeground(new Color(100, 220, 130));
        header.add(online, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(20, 15));
        content.setBackground(background);
        content.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel menuPanel = new JPanel(new BorderLayout(10, 15));
        menuPanel.setBackground(background);
        JLabel title = new JLabel("<html><b>Explore Our Menu</b><br><font size='3'>Choose your favourite dishes</font></html>");
        title.setFont(new Font("Arial", Font.BOLD, 23));
        menuPanel.add(title, BorderLayout.NORTH);

        JPanel foodGrid = new JPanel(new GridLayout(3, 2, 15, 15));
        foodGrid.setBackground(background);
        for (FoodItem food : menu) foodGrid.add(createFoodCard(food));
        menuPanel.add(foodGrid, BorderLayout.CENTER);

        JPanel cartPanel = new JPanel(new BorderLayout(10, 12));
        cartPanel.setBackground(Color.WHITE);
        cartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JPanel cartHeading = new JPanel(new BorderLayout());
        cartHeading.setOpaque(false);
        cartHeading.add(new JLabel("Your Cart"), BorderLayout.WEST);
        itemCount = new JLabel("0 items");
        itemCount.setForeground(red);
        cartHeading.add(itemCount, BorderLayout.EAST);

        model = new DefaultTableModel(new String[]{"Food", "Price", "Qty", "Total"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        cartTable = new JTable(model);
        cartTable.setRowHeight(32);
        cartPanel.add(cartHeading, BorderLayout.NORTH);
        cartPanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(0, 1, 8, 8));
        bottom.setOpaque(false);
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quantityPanel.setOpaque(false);
        quantityPanel.add(new JLabel("Quantity:"));
        // Integer wrapper object is returned by the spinner
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        quantityPanel.add(quantitySpinner);
        JButton remove = button("Remove Selected Item", dark);
        JButton clear = button("Clear Cart", Color.GRAY);
        totalLabel = new JLabel("Total: Rs. 0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 21));
        totalLabel.setForeground(red);
        JButton order = button("PLACE ORDER", red);
        bottom.add(quantityPanel);
        bottom.add(remove);
        bottom.add(clear);
        bottom.add(totalLabel);
        bottom.add(order);
        cartPanel.add(bottom, BorderLayout.SOUTH);
        cartPanel.setPreferredSize(new Dimension(365, 500));

        content.add(menuPanel, BorderLayout.CENTER);
        content.add(cartPanel, BorderLayout.EAST);
        add(content, BorderLayout.CENTER);
        add(new JLabel("  FOODIE HUB | Thank you for visiting!"), BorderLayout.SOUTH);

        remove.addActionListener(e -> removeItem());
        clear.addActionListener(e -> clearCart());
        order.addActionListener(e -> placeOrder());
        setVisible(true);
    }

    private JPanel createFoodCard(FoodItem food) {
        JPanel card = new JPanel(new BorderLayout(5, 8));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(225, 225, 225)),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        JLabel emoji = new JLabel(food.getEmoji(), SwingConstants.CENTER);
        emoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        JLabel details = new JLabel("<html><b>" + food.getName() + "</b><br>"
            + food.getDescription() + "<br><font color='#BE4130'><b>Rs. "
            + food.getPrice() + "</b></font></html>");
        JButton add = button("+ Add To Cart", red);
        add.addActionListener(e -> addToCart(food, (Integer) quantitySpinner.getValue()));
        card.add(emoji, BorderLayout.NORTH);
        card.add(details, BorderLayout.CENTER);
        card.add(add, BorderLayout.SOUTH);
        return card;
    }

    private JButton button(String text, Color color) {
        JButton b = new JButton(text);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    private void addToCart(FoodItem food, int quantity) {
        for (CartItem item : cart) {
            if (item.getFood().getName().equals(food.getName())) {
                item.addQuantity(quantity);
                updateCart();
                return;
            }
        }
        cart.add(new CartItem(food, quantity));
        updateCart();
    }

    private void updateCart() {
        model.setRowCount(0);
        Double total = 0.0; // Double is a wrapper class
        int count = 0;
        for (CartItem item : cart) {
            double lineTotal = item.getTotal();
            total += lineTotal;
            count += item.getQuantity();
            model.addRow(new Object[]{item.getFood().getName(),
                "Rs. " + item.getFood().getPrice(), item.getQuantity(),
                String.format("Rs. %.2f", lineTotal)});
        }
        totalLabel.setText(String.format("Total: Rs. %.2f", total));
        itemCount.setText(count + " items");
    }

    private void removeItem() {
        int row = cartTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select an item first!");
            return;
        }
        cart.remove(row);
        updateCart();
    }

    private void clearCart() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is already empty!");
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to clear the cart?", "Confirm",
            JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            cart.clear();
            updateCart();
        }
    }

    private void placeOrder() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty!");
            return;
        }
        double total = 0;
        StringBuilder bill = new StringBuilder("       FOODIE HUB RECEIPT\n");
        bill.append("==============================\n");
        for (CartItem item : cart) {
            bill.append(item.getFood().getName()).append(" x ")
                .append(item.getQuantity()).append(" = Rs. ")
                .append(String.format("%.2f", item.getTotal())).append("\n");
            total += item.getTotal();
        }
        bill.append("==============================\n");
        bill.append(String.format("TOTAL AMOUNT: Rs. %.2f", total));
        bill.append("\n\nThank you for ordering!");
        JOptionPane.showMessageDialog(this, bill.toString(),
            "Order Confirmed", JOptionPane.INFORMATION_MESSAGE);
        cart.clear();
        updateCart();
    }

    public static void main(String[] args) {
        // Demonstration of Object.clone() through the Cloneable interface
        FoodItem originalPizza = new Pizza();
        FoodItem copiedPizza = originalPizza.clone();
        System.out.println("Cloned food item: " + copiedPizza);

        // Objects no longer referenced become eligible for garbage collection
        SwingUtilities.invokeLater(() -> new FoodOrderSystem());
    }
}
