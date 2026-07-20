/**
 * Book.java
 * Child class of Item. Adds fields specific to books.
 */
public class Book extends Item {
    private String author;
    private String isbn;

    public Book(int id, String name, String status, double price, String author, String isbn) {
        super(id, name, status, price);
        this.author = author;
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", price=" + getPrice() +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
