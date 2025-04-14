package da.component;

public class ProductItem {
    private String name;
    private int quantity;
    private String imagePath;

    public ProductItem(String name, int quantity, String imagePath) {
        this.name = name;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return name;
    }
}
