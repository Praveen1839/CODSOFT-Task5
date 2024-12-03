import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;
    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }
    public String getCode() {
        return code;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getSchedule() {
        return schedule;
    }
    public int getAvailableSlots() {
        return capacity - enrolledStudents;
    }
    public boolean enrollStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }
    public boolean dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return String.format(
            "Course Code: %s\nTitle: %s\nDescription: %s\nSchedule: %s\nAvailable Slots: %d/%d\n",
            code, title, description, schedule, getAvailableSlots(), capacity
        );
    }
}
class Student {
    private String id;
    private String name;
    private ArrayList<Course> registeredCourses;
    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }
    public boolean registerCourse(Course course) {
        if (course.getAvailableSlots() > 0 && !registeredCourses.contains(course)) {
            registeredCourses.add(course);
            course.enrollStudent();
            return true;
        }
        return false;
    }
    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.dropStudent();
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Student ID: %s\nName: %s\nRegistered Courses:\n", id, name));
        for (Course course : registeredCourses) {
            sb.append(course.getTitle()).append("\n");
        }
        return sb.toString();
    }
}
public class CourseManagementSystem {
    private static HashMap<String, Course> courseDatabase = new HashMap<>();
    private static HashMap<String, Student> studentDatabase = new HashMap<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeCourses();
        initializeStudents();
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> displayCourses();
                case 2 -> registerStudent(scanner);
                case 3 -> dropCourse(scanner);
                case 4 -> displayStudentDetails(scanner);
                case 5 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        scanner.close();
    }
    private static void initializeCourses() {
        courseDatabase.put("CS101", new Course("CS101", "Intro to Computer Science", "Learn the basics of computer science.", 30, "Mon & Wed 10-11 AM"));
        courseDatabase.put("MATH201", new Course("MATH201", "Calculus I", "Introduction to calculus concepts.", 25, "Tue & Thu 1-2 PM"));
        courseDatabase.put("PHY111", new Course("PHY111", "Physics Basics", "Learn the fundamentals of physics.", 20, "Fri 3-4 PM"));
    }
    private static void initializeStudents() {
        studentDatabase.put("S001", new Student("S001", "Alice"));
        studentDatabase.put("S002", new Student("S002", "Bob"));
    }
    private static void displayMenu() {
        System.out.println("\n=== Course Management System ===");
        System.out.println("1. View Available Courses");
        System.out.println("2. Register Student for a Course");
        System.out.println("3. Drop a Course");
        System.out.println("4. View Student Details");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }
    private static void displayCourses() {
        System.out.println("\n=== Available Courses ===");
        for (Course course : courseDatabase.values()) {
            System.out.println(course);
        }
    }
    private static void registerStudent(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentDatabase.get(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.print("Enter Course Code to Register: ");
        String courseCode = scanner.nextLine();
        Course course = courseDatabase.get(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        if (student.registerCourse(course)) {
            System.out.println("Course registered successfully!");
        } else {
            System.out.println("Failed to register for the course. Either the course is full or already registered.");
        }
    }
    private static void dropCourse(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentDatabase.get(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.print("Enter Course Code to Drop: ");
        String courseCode = scanner.nextLine();
        Course course = courseDatabase.get(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        if (student.dropCourse(course)) {
            System.out.println("Course dropped successfully!");
        } else {
            System.out.println("Failed to drop the course. Either the course is not registered or an error occurred.");
        }
    }
    private static void displayStudentDetails(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentDatabase.get(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("\n=== Student Details ===");
        System.out.println(student);
    }
}
