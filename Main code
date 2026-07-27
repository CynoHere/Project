import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Driver class for the Student/Course tracker.
 * Provides a simple text menu to add and list students.
 */
public class Main {

    private static final ArrayList<Student> students = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // A couple of starter/hardcoded students so listing has something
        // to show right away (mentioned as optional in the assignment).
        students.add(new Student("Ava Thompson", 1001, "Data Structures", 3.7));
        students.add(new Student("Marcus Lee", 1002, "Calculus II", 3.2));

        boolean running = true;
        while (running) {
            printHeader();
            int choice = readMenuChoice();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    listStudents();
                    break;
                case 3:
                    searchStudents();
                    break;
                case 4:
                    exportToCsv();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 0, 1, 2, 3, or 4.");
            }
        }

        scanner.close();
    }

    /** Prints the menu header/options. */
    private static void printHeader() {
        System.out.println();
        System.out.println("=====================================");
        System.out.println("      Student / Course Tracker");
        System.out.println("=====================================");
        System.out.println("1. Add new student");
        System.out.println("2. List all students");
        System.out.println("3. Search for a student");
        System.out.println("4. Export to CSV");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    /** Reads and validates the menu selection. */
    private static int readMenuChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            return choice;
        } catch (NumberFormatException e) {
            return -1; // triggers "invalid option" in the switch
        }
    }

    /** Prompts the user for student fields, builds a Student, and stores it. */
    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        int id = readInt("Enter student ID: ");

        System.out.print("Enter course name: ");
        String course = scanner.nextLine().trim();

        double gpa = readDouble("Enter GPA (0.0 - 4.0): ");

        Student newStudent = new Student(name, id, course, gpa);
        students.add(newStudent);

        System.out.println("Student added successfully:");
        System.out.println("  " + newStudent);
    }

    /** Lists all students currently stored in memory. */
    private static void listStudents() {
        if (students.isEmpty()) {
            System.out.println("No students stored yet.");
            return;
        }

        System.out.println("\n--- Current Students (" + students.size() + ") ---");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    /**
     * Searches stored students by name (partial, case-insensitive match)
     * or by exact student ID, depending on what the user enters.
     */
    private static void searchStudents() {
        if (students.isEmpty()) {
            System.out.println("No students stored yet.");
            return;
        }

        System.out.print("Search by ID or name: ");
        String query = scanner.nextLine().trim();

        ArrayList<Student> matches = new ArrayList<>();

        // If the query is a whole number, treat it as an ID search first.
        try {
            int idQuery = Integer.parseInt(query);
            for (Student s : students) {
                if (s.getStudentId() == idQuery) {
                    matches.add(s);
                }
            }
        } catch (NumberFormatException e) {
            // Not a number -> fall through to name search below.
        }

        // Also check name matches (covers non-numeric queries).
        if (matches.isEmpty()) {
            for (Student s : students) {
                if (s.getName().toLowerCase().contains(query.toLowerCase())) {
                    matches.add(s);
                }
            }
        }

        if (matches.isEmpty()) {
            System.out.println("No student found matching \"" + query + "\".");
            return;
        }

        System.out.println("\n--- Search Results (" + matches.size() + ") ---");
        for (Student s : matches) {
            System.out.println(s);
        }
    }

    /**
     * Writes all stored students to a CSV file in the project folder so
     * the data can be opened in Excel or Google Sheets.
     */
    private static void exportToCsv() {
        if (students.isEmpty()) {
            System.out.println("No students to export yet.");
            return;
        }

        String fileName = "students.csv";

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(fileName))) {
            // Header row
            writer.println("studentId,name,course,gpa");

            // One line per student
            for (Student s : students) {
                writer.println(s.getStudentId() + "," +
                        s.getName() + "," +
                        s.getCourse() + "," +
                        s.getGpa());
            }

            System.out.println("Exported " + students.size() + " student(s) to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing CSV file: " + e.getMessage());
        }
    }

    /** Helper to safely read an integer from the console. */
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    /** Helper to safely read a double from the console. */
    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (e.g., 3.5).");
            }
        }
    }
}
