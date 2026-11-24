import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

class Student {

    private int rollNumber;
    private String name;
    private String course;
    private double marks;

    public Student(int rollNumber, String name, String course, double marks) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.course = course;
        this.marks = marks;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public double getMarks() {
        return marks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public void display() {
        System.out.printf("%-12d %-20s %-15s %-8.2f%n",
                rollNumber, name, course, marks);
    }
}

public class CollegeStudentManagementSystem {

    static ArrayList<Student> students = new ArrayList<>();

    static HashMap<String, Integer> courseCount = new HashMap<>();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== COLLEGE STUDENT MANAGEMENT SYSTEM =====");

            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student by Roll Number");
            System.out.println("4. Search Student by Name");
            System.out.println("5. Sort Students by Name");
            System.out.println("6. Sort Students by Marks");
            System.out.println("7. Update Student");
            System.out.println("8. Delete Student");
            System.out.println("9. Show Course-wise Student Count");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");

            choice = readInt();

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    displayStudents();
                    break;

                case 3:
                    searchByRollNumber();
                    break;

                case 4:
                    searchByName();
                    break;

                case 5:
                    sortByName();
                    break;

                case 6:
                    sortByMarks();
                    break;

                case 7:
                    updateStudent();
                    break;

                case 8:
                    deleteStudent();
                    break;

                case 9:
                    showCourseCount();
                    break;

                case 0:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);

        sc.close();
    }

    // Method to read integer input

    public static int readInt() {

        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }

        int value = sc.nextInt();
        sc.nextLine();

        return value;
    }

    // Method to read and validate marks

    public static double readMarks() {

        while (!sc.hasNextDouble()) {
            System.out.print("Please enter valid marks: ");
            sc.next();
        }

        double marks = sc.nextDouble();
        sc.nextLine();

        while (marks < 0 || marks > 100) {

            System.out.print("Marks must be between 0 and 100: ");

            while (!sc.hasNextDouble()) {
                System.out.print("Please enter valid marks: ");
                sc.next();
            }

            marks = sc.nextDouble();
            sc.nextLine();
        }

        return marks;
    }

    // Check duplicate roll number

    public static boolean rollNumberExists(int rollNumber) {

        for (Student s : students) {

            if (s.getRollNumber() == rollNumber) {
                return true;
            }
        }

        return false;
    }

    // Add Student

    public static void addStudent() {

        System.out.print("Enter roll number: ");
        int roll = readInt();

        if (rollNumberExists(roll)) {
            System.out.println("Roll number already exists.");
            return;
        }

        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        System.out.print("Enter course: ");
        String course = sc.nextLine();

        System.out.print("Enter marks: ");
        double marks = readMarks();

        Student student = new Student(roll, name, course, marks);

        students.add(student);

        courseCount.put(course,
                courseCount.getOrDefault(course, 0) + 1);

        System.out.println("Student added successfully!");
    }

    // Display Students

    public static void displayStudents() {

        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        printTableHeading();

        for (Student s : students) {
            s.display();
        }
    }

    public static void printTableHeading() {

        System.out.printf("%-12s %-20s %-15s %-8s%n",
                "Roll Number", "Name", "Course", "Marks");

        System.out.println("------------------------------------------------------------");
    }

    // Search by Roll Number using Linear Search

    public static void searchByRollNumber() {

        System.out.print("Enter roll number to search: ");

        int roll = readInt();

        for (Student s : students) {

            if (s.getRollNumber() == roll) {

                System.out.println("Student found:");

                printTableHeading();

                s.display();

                return;
            }
        }

        System.out.println("Student not found.");
    }

    // Search by Name

    public static void searchByName() {

        System.out.print("Enter name to search: ");

        String searchName = sc.nextLine();

        boolean found = false;

        for (Student s : students) {

            if (s.getName().equalsIgnoreCase(searchName)) {

                if (!found) {
                    printTableHeading();
                }

                s.display();

                found = true;
            }
        }

        if (!found) {
            System.out.println("Student not found.");
        }
    }

    // Sort Students Alphabetically

    public static void sortByName() {

        Collections.sort(students,
                Comparator.comparing(
                        Student::getName,
                        String.CASE_INSENSITIVE_ORDER));

        System.out.println("Students sorted by name.");

        displayStudents();
    }

    // Sort Students by Marks (Highest to Lowest)

    public static void sortByMarks() {

        Collections.sort(students,
                Comparator.comparingDouble(Student::getMarks).reversed());

        System.out.println("Students sorted by marks.");

        displayStudents();
    }

    // Update Student Details

    public static void updateStudent() {

        System.out.print("Enter roll number to update: ");

        int roll = readInt();

        for (Student s : students) {

            if (s.getRollNumber() == roll) {

                String oldCourse = s.getCourse();

                System.out.print("Enter new name: ");
                String newName = sc.nextLine();

                System.out.print("Enter new course: ");
                String newCourse = sc.nextLine();

                System.out.print("Enter new marks: ");
                double newMarks = readMarks();

                s.setName(newName);
                s.setCourse(newCourse);
                s.setMarks(newMarks);

                if (!oldCourse.equalsIgnoreCase(newCourse)) {

                    courseCount.put(oldCourse,
                            courseCount.getOrDefault(oldCourse, 1) - 1);

                    courseCount.put(newCourse,
                            courseCount.getOrDefault(newCourse, 0) + 1);
                }

                System.out.println("Student updated successfully!");

                return;
            }
        }

        System.out.println("Student not found.");
    }

    // Delete Student

    public static void deleteStudent() {

        System.out.print("Enter roll number to delete: ");

        int roll = readInt();

        for (int i = 0; i < students.size(); i++) {

            Student s = students.get(i);

            if (s.getRollNumber() == roll) {

                String course = s.getCourse();

                students.remove(i);

                courseCount.put(course,
                        courseCount.getOrDefault(course, 1) - 1);

                System.out.println("Student deleted successfully!");

                return;
            }
        }

        System.out.println("Student not found.");
    }

    // Display Course-wise Student Count using HashMap

    public static void showCourseCount() {

        if (courseCount.isEmpty()) {
            System.out.println("No course information available.");
            return;
        }

        System.out.println("\nCourse-wise Student Count:");

        for (String course : courseCount.keySet()) {

            int count = courseCount.get(course);

            if (count > 0) {
                System.out.println(course + " : " + count);
            }
        }
    }
}