/**
 * Represents a single student enrolled in a course.
 */
public class Student {

    private String name;
    private int studentId;
    private String course;
    private double gpa;

    // Default constructor
    public Student() {
        this("Unknown", 0, "Undeclared", 0.0);
    }

    // Full constructor
    public Student(String name, int studentId, String course, double gpa) {
        this.name = name;
        this.studentId = studentId;
        this.course = course;
        this.gpa = gpa;
    }

    // --- Getters ---
    public String getName() {
        return name;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getCourse() {
        return course;
    }

    public double getGpa() {
        return gpa;
    }

    // --- Setters ---
    public void setName(String name) {
        this.name = name;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    /**
     * Readable one-line summary used when listing students.
     */
    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-15s | Course: %-20s | GPA: %.2f",
                studentId, name, course, gpa);
    }
}
