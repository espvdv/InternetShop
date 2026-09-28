package shop;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Product> products;
    private double totalPrice;
    private String status;

    public Order(Cart cart) {
        this.products = new ArrayList<>(cart.getProducts());
        this.totalPrice = cart.getTotalPrice();
        this.status = "Нове";
    }

    public void setStatus(String status) { this.status = status; }
    public List<Product> getProducts() { return products; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Product product : products) {
            sb.append(product.toString()).append("\n\n");
        }
        sb.append("-----------------------\n");
        sb.append("Сума: ").append(totalPrice).append(" грн | Статус: ").append(status);
        return sb.toString();
    }
}