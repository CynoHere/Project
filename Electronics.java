/**
 * Electronics.java
 * Child class of Item. Adds fields specific to electronics.
 */
public class Electronics extends Item {
    private String brand;
    private int warrantyMonths;

    public Electronics(int id, String name, String status, double price, String brand, int warrantyMonths) {
        super(id, name, status, price);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    @Override
    public String toString() {
        return "Electronics{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", price=" + getPrice() +
                ", brand='" + brand + '\'' +
                ", warrantyMonths=" + warrantyMonths +
                '}';
    }
}
