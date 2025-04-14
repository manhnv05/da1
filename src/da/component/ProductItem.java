package da.component;

import java.math.BigDecimal;

public class ProductItem {
    private String name;
    private int quantity;
    private BigDecimal price;
    private String imagePath;

    public ProductItem(String name, int quantity, BigDecimal price, String imagePath) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
    

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return name;
    }
}
