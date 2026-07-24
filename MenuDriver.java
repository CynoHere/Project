import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * MenuDriver.java
 * NRCC Bookstore Console - main driver.
 *
 * Options 1-3 are complete (provided by the senior dev).
 * Options 4-6 are implemented below as part of this sprint.
 */
public class MenuDriver {

    private static final String DEFAULT_CSV_PATH = "src/data/inventory_textbooks.csv";
    private static final String REPORTS_DIR = "src/data/reports/";
    private static final DateTimeFormatter TS_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=================================================");
        System.out.println("           NRCC Bookstore Console");
        System.out.println("=================================================");

        System.out.print("Enter CSV path (press Enter for default: " + DEFAULT_CSV_PATH + "): ");
        String pathInput = sc.nextLine().trim();
        String csvPath = pathInput.isEmpty() ? DEFAULT_CSV_PATH : pathInput;

        ArrayList<Books> books = loadBooks(csvPath);

        System.out.println("-------------------------------------------------");
        System.out.println("Data file : " + csvPath);
        System.out.println("Rows loaded: " + books.size());
        System.out.println("Date/Time : " + LocalDateTime.now().format(TS_FORMAT));
        System.out.println("-------------------------------------------------");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readMenuChoice(sc);

            switch (choice) {
                case 1 -> listAllBooks(books);
                case 2 -> searchByCourse(books, sc);
                case 3 -> saveAllBooksReport(books);
                case 4 -> saveCourseReport(books, sc);      // implemented below
                case 5 -> exportTopN(books, sc);            // implemented below
                case 6 -> exportPriceStats(books);          // implemented below
                case 0 -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("NRCC Bookstore Console");
        System.out.println("1) List all books");
        System.out.println("2) Search by course");
        System.out.println("3) Save ALL BOOKS report");
        System.out.println("4) Save COURSE report (list + totals)");
        System.out.println("5) Export TOP-N most expensive");
        System.out.println("6) Export PRICE STATS (min/max/avg/median)");
        System.out.println("0) Exit");
        System.out.print("Choice: ");
    }

    private static int readMenuChoice(Scanner sc) {
        String line = sc.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // -----------------------------------------------------------------
    // CSV Loading (provided - do not modify)
    // -----------------------------------------------------------------
    private static ArrayList<Books> loadBooks(String path) {
        ArrayList<Books> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length < 5) continue;

                String courseCode = parts[0].trim();
                String title = parts[1].trim();
                String author = parts[2].trim();
                double price;
                try {
                    price = Double.parseDouble(parts[3].trim());
                } catch (NumberFormatException e) {
                    price = 0.0;
                }
                boolean required = parts[4].trim().equalsIgnoreCase("Required");

                list.add(new Books(title, author, price, courseCode, required));
            }
        } catch (IOException e) {
            System.out.println("Error loading CSV: " + e.getMessage());
        }
        return list;
    }

    // -----------------------------------------------------------------
    // Option 1: List all books (provided)
    // -----------------------------------------------------------------
    private static void listAllBooks(ArrayList<Books> books) {
        System.out.println();
        System.out.printf("%-30.30s %-20.20s %10s  %-10s %s%n",
                "Title", "Author", "Price", "Course", "Tag");
        System.out.println("-".repeat(85));
        for (Books b : books) {
            System.out.println(b);
        }
        System.out.println("-".repeat(85));
        System.out.println("Total rows: " + books.size());
    }

    // -----------------------------------------------------------------
    // Option 2: Search by course (provided)
    // -----------------------------------------------------------------
    private static void searchByCourse(ArrayList<Books> books, Scanner sc) {
        System.out.print("Enter course code to search (e.g., CSC 222): ");
        String query = sc.nextLine().trim();

        System.out.println();
        System.out.printf("%-30.30s %-20.20s %10s  %s%n", "Title", "Author", "Price", "Tag");
        System.out.println("-".repeat(75));
        int count = 0;
        for (Books b : books) {
            if (b.getCourseCode().equalsIgnoreCase(query)) {
                System.out.printf("%-30.30s %-20.20s $%9.2f  %s%n",
                        b.getTitle(), b.getAuthor(), b.getPrice(),
                        b.isRequired() ? "[Required]" : "[Optional]");
                count++;
            }
        }
        System.out.println("-".repeat(75));
        System.out.println("Matches found: " + count);
    }

    // -----------------------------------------------------------------
    // Option 3: Save ALL BOOKS report (provided)
    // -----------------------------------------------------------------
    private static void saveAllBooksReport(ArrayList<Books> books) {
        ensureReportsDir();
        String outPath = REPORTS_DIR + "all_books_report.txt";

        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(outPath)))) {
            pw.println("NRCC Bookstore - All Books Report");
            pw.println("Generated: " + LocalDateTime.now().format(TS_FORMAT));
            pw.println("=".repeat(90));
            pw.printf("%-30.30s %-20.20s %10s  %-10s %s%n",
                    "Title", "Author", "Price", "Course", "Tag");
            pw.println("-".repeat(90));

            double total = 0.0;
            for (Books b : books) {
                pw.printf("%-30.30s %-20.20s $%9.2f  %-10s %s%n",
                        b.getTitle(), b.getAuthor(), b.getPrice(),
                        b.getCourseCode().isBlank() ? "-" : b.getCourseCode(),
                        b.isRequired() ? "[Required]" : "[Optional]");
                total += b.getPrice();
            }

            pw.println("-".repeat(90));
            pw.println("Total books: " + books.size());
            pw.printf("Total value: $%.2f%n", total);

            System.out.println("Saved: " + outPath);
        } catch (IOException e) {
            System.out.println("Error writing report: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------
    // Option 4: Save COURSE report (list + totals) -- IMPLEMENTED
    // -----------------------------------------------------------------
    private static void saveCourseReport(ArrayList<Books> books, Scanner sc) {
        System.out.print("Enter course code (e.g., CSC 222): ");
        String courseInput = sc.nextLine().trim();

        if (courseInput.isEmpty()) {
            System.out.println("Course code cannot be empty.");
            return;
        }

        // Collect matching rows for the course (case-insensitive)
        ArrayList<Books> matches = new ArrayList<>();
        for (Books b : books) {
            if (b.getCourseCode().equalsIgnoreCase(courseInput)) {
                matches.add(b);
            }
        }

        if (matches.isEmpty()) {
            System.out.println("No books found for course: " + courseInput);
            return;
        }

        // Deduplicate by Title+Author
        ArrayList<Books> unique = new ArrayList<>();
        int duplicates = 0;
        for (Books b : matches) {
            if (seenByTitleAuthor(unique, b)) {
                duplicates++;
            } else {
                unique.add(b);
            }
        }

        double requiredSubtotal = 0.0;
        double optionalSubtotal = 0.0;
        for (Books b : unique) {
            if (b.isRequired()) {
                requiredSubtotal += b.getPrice();
            } else {
                optionalSubtotal += b.getPrice();
            }
        }
        double grandTotal = requiredSubtotal + optionalSubtotal;

        ensureReportsDir();
        // Build a filesystem-safe course token, e.g. "CSC 222" -> "CSC222"
        String courseToken = courseInput.toUpperCase().replaceAll("\\s+", "");
        String outPath = REPORTS_DIR + "course_" + courseToken + "_report.txt";

        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(outPath)))) {
            pw.println("NRCC Bookstore - Course Report");
            pw.println("Course: " + courseInput.toUpperCase());
            pw.println("Generated: " + LocalDateTime.now().format(TS_FORMAT));
            pw.println("=".repeat(80));
            pw.printf("%-30.30s %-22.22s %10s  %s%n", "Title", "Author", "Price", "Tag");
            pw.println("-".repeat(80));

            for (Books b : unique) {
                pw.printf("%-30.30s %-22.22s $%9.2f  %s%n",
                        b.getTitle(), b.getAuthor(), b.getPrice(),
                        b.isRequired() ? "[Required]" : "[Optional]");
            }

            pw.println("-".repeat(80));
            pw.printf("Required subtotal: $%.2f%n", requiredSubtotal);
            pw.printf("Optional subtotal: $%.2f%n", optionalSubtotal);
            pw.printf("Grand total:       $%.2f%n", grandTotal);
            pw.println();
            pw.println("(" + unique.size() + " unique books; " + duplicates + " duplicates ignored)");

            System.out.println("Saved course report: " + outPath +
                    " (" + unique.size() + " unique books; " + duplicates + " duplicates ignored)");
        } catch (IOException e) {
            System.out.println("Error writing course report: " + e.getMessage());
        }
    }

    // Helper: true if a book with the same Title+Author already exists in list.
    private static boolean seenByTitleAuthor(ArrayList<Books> list, Books b) {
        for (Books x : list) {
            if (x.getTitle().equalsIgnoreCase(b.getTitle())
                    && x.getAuthor().equalsIgnoreCase(b.getAuthor())) return true;
        }
        return false;
    }

    // -----------------------------------------------------------------
    // Option 5: Export TOP-N most expensive -- IMPLEMENTED
    // -----------------------------------------------------------------
    private static void exportTopN(ArrayList<Books> books, Scanner sc) {
        if (books.isEmpty()) {
            System.out.println("No books loaded.");
            return;
        }

        int n = -1;
        while (n < 1 || n > books.size()) {
            System.out.print("Enter N (1-" + books.size() + "): ");
            String input = sc.nextLine().trim();
            try {
                n = Integer.parseInt(input);
                if (n < 1 || n > books.size()) {
                    System.out.println("Please enter a value between 1 and " + books.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
                n = -1;
            }
        }

        // Copy list, sort descending by price, take first N
        ArrayList<Books> sorted = new ArrayList<>(books);
        Collections.sort(sorted, new Comparator<Books>() {
            @Override
            public int compare(Books a, Books b) {
                return Double.compare(b.getPrice(), a.getPrice());
            }
        });

        ArrayList<Books> topN = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            topN.add(sorted.get(i));
        }

        double total = 0.0;
        for (Books b : topN) {
            total += b.getPrice();
        }
        double average = total / n;

        ensureReportsDir();
        String outPath = REPORTS_DIR + "top" + n + ".txt";

        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(outPath)))) {
            pw.println("NRCC Bookstore - Top " + n + " Most Expensive Books");
            pw.println("Generated: " + LocalDateTime.now().format(TS_FORMAT));
            pw.println("=".repeat(80));
            pw.printf("%-4s%-30.30s %-22.22s %s%n", "#", "Title", "Author", "Price");
            pw.println("-".repeat(80));

            int rank = 1;
            for (Books b : topN) {
                pw.printf("%-4d%-30.30s %-22.22s $%9.2f%n",
                        rank, b.getTitle(), b.getAuthor(), b.getPrice());
                rank++;
            }

            pw.println("-".repeat(80));
            pw.printf("Top-%d total value: $%.2f%n", n, total);
            pw.printf("Average of top %d: $%.2f%n", n, average);

            System.out.println("Saved: " + outPath);
        } catch (IOException e) {
            System.out.println("Error writing top-N report: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------
    // Option 6: Export PRICE STATS (min/max/avg/median) -- IMPLEMENTED
    // -----------------------------------------------------------------
    private static void exportPriceStats(ArrayList<Books> books) {
        if (books.isEmpty()) {
            System.out.println("No books loaded.");
            return;
        }

        ArrayList<Double> prices = new ArrayList<>();
        for (Books b : books) {
            prices.add(b.getPrice());
        }
        Collections.sort(prices);

        int count = prices.size();
        double min = prices.get(0);
        double max = prices.get(count - 1);

        double sum = 0.0;
        for (double p : prices) {
            sum += p;
        }
        double average = sum / count;

        double median;
        if (count % 2 == 1) {
            median = prices.get(count / 2);
        } else {
            double lower = prices.get(count / 2 - 1);
            double upper = prices.get(count / 2);
            median = (lower + upper) / 2.0;
        }

        ensureReportsDir();
        String outPath = REPORTS_DIR + "price_stats.txt";

        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(outPath)))) {
            pw.println("NRCC Bookstore - Price Statistics");
            pw.println("Generated: " + LocalDateTime.now().format(TS_FORMAT));
            pw.println("=".repeat(40));
            pw.println("Count: " + count);
            pw.printf("Min: $%.2f%n", min);
            pw.printf("Max: $%.2f%n", max);
            pw.printf("Average: $%.2f%n", average);
            pw.printf("Median: $%.2f%n", median);

            System.out.println("Saved: " + outPath);
        } catch (IOException e) {
            System.out.println("Error writing price stats report: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------
    // Utility
    // -----------------------------------------------------------------
    private static void ensureReportsDir() {
        try {
            Files.createDirectories(Paths.get(REPORTS_DIR));
        } catch (IOException e) {
            System.out.println("Warning: could not verify reports directory: " + e.getMessage());
        }
    }
}
