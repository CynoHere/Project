/**
 * Main.java
 * Driver class for the Store Inventory System.
 * Creates sample objects from the child classes and prints them
 * to prove inheritance is working correctly.
 */
public class Main {
    public static void main(String[] args) {

        Book book1 = new Book(101, "Clean Code", "In Stock", 34.99,
                "Robert C. Martin", "978-0132350884");

        Electronics laptop = new Electronics(202, "ThinkPad X1", "In Stock", 1299.99,
                "Lenovo", 24);

        System.out.println("---- Store Inventory ----");
        System.out.println(book1);
        System.out.println(laptop);

        // Show that both objects are still recognized as Items (inheritance in action)
        Item[] inventory = { book1, laptop };
        System.out.println("\n---- Looping through as Item type ----");
        for (Item item : inventory) {
            System.out.println(item);
        }
    }
}
