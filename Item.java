/**
 * Item.java
 * Parent (base) class for the Store Inventory System.
 * Holds the fields shared by every inventory item.
 */
public class Item {
    private int id;
    private String name;
    private String status;
    private double price;

    public Item(int id, String name, String status, double price) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                '}';
    }
}
