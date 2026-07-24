/**
 * Books.java
 * Model class for a single bookstore inventory row.
 *
 * Fields:
 *   title      - book title
 *   author     - book author
 *   price      - retail price (>= 0)
 *   courseCode - course this book is adopted for (may be blank for inventory-only rows)
 *   required   - true if the book is a required text for its course
 *
 * This class is provided by the senior dev and should NOT be modified.
 */
public class Books {

    private final String title;
    private final String author;
    private final double price;
    private final String courseCode;
    private final boolean required;

    public Books(String title, String author, double price, String courseCode, boolean required) {
        // Basic validation / normalization so downstream code can trust the data.
        this.title = (title == null) ? "" : title.trim();
        this.author = (author == null) ? "" : author.trim();
        this.price = (price < 0) ? 0.0 : price;
        this.courseCode = (courseCode == null) ? "" : courseCode.trim();
        this.required = required;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean hasCourse() {
        return courseCode != null && !courseCode.isBlank();
    }

    @Override
    public String toString() {
        return String.format("%-30.30s %-20.20s $%8.2f  %-10s %s",
                title, author, price,
                courseCode.isBlank() ? "-" : courseCode,
                required ? "[Required]" : "[Optional]");
    }
}
