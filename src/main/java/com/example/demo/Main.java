package com.example.demo;
import java.sql.*;

public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5433/Student";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    public static void main(String[] args) {
        System.out.println("=== STARTING CRUD OPERATIONS ===\n");

        addStudent("John Doe", 20, "john@email.com", "Computer Science");
        addStudent("Jane Smith", 22, "jane@email.com", "Mathematics");
        addStudent("Mike Johnson", 21, "mike@email.com", "Physics");
        addStudent("Sarah Wilson", 23, "sarah@email.com", "Chemistry");

        viewAllStudents();

        updateStudent(1, "Mukama", 25, "john.mukama@email.com", "Advanced Computer Science");

        System.out.println("\n=== AFTER UPDATE ===");
        viewAllStudents();

        deleteStudent(2);
        System.out.println("\n=== AFTER DELETE ===");
        viewAllStudents();

        System.out.println("\n=== ALL OPERATIONS COMPLETED ===");
    }

    private static void addStudent(String name, int age, String email, String course) {
        String sql = "INSERT INTO students (name, age, email, course) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, email);
            statement.setString(4, course);
            statement.executeUpdate();

            System.out.println(" Student added: " + name);

        } catch (SQLException e) {
            System.out.println(" Error adding student " + name + ": " + e.getMessage());
        }
    }

    private static void viewAllStudents() {
        String sql = "SELECT * FROM students";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet result = stmt.executeQuery(sql)) {

            System.out.println("\n--- ALL STUDENTS IN DATABASE ---");
            boolean hasStudents = false;

            while (result.next()) {
                hasStudents = true;
                Student student = new Student("", 0, "", "");

                student.setId(result.getInt("id"));
                student.setName(result.getString("name"));
                student.setAge(result.getInt("age"));
                student.setEmail(result.getString("email"));
                student.setCourse(result.getString("course"));
                System.out.println(student);
            }

            if (!hasStudents) {
                System.out.println("No students found in database!");
            }

        } catch (SQLException e) {
            System.out.println(" Error viewing students: " + e.getMessage());
        }
    }

    private static void updateStudent(int id, String name, int age, String email, String course) {
        String sql = "UPDATE students SET name = 'mukama', age = 25, email = 'mukama@email.com', course = 'biology' WHERE id = 81";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, email);
            statement.setString(4, course);
            statement.setInt(5, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(" Student updated: ID " + id + " -> " + name);
            } else {
                System.out.println(" Student not found with ID: " + id);
            }

        } catch (SQLException e) {
            System.out.println(" Error updating student: " + e.getMessage());
        }
    }

    private static void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = 82";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println(" Student deleted: ID " + id);
            } else {
                System.out.println(" Student not found with ID: " + id);
            }

        } catch (SQLException e) {
            System.out.println(" Error deleting student: " + e.getMessage());
        }
    }
}