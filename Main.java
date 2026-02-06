
import java.io.File;
import java.util.Scanner;

public class Main {
    private static final String CSV_FILE = "students.csv";

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        Scanner scanner = new Scanner(System.in);

        // Load data at start
        bst.loadFromFile(CSV_FILE);

        while (true) {
            System.out.println("\n=== Student Gradebook Manager (BST) ===");
            System.out.println("1. Insert Student");
            System.out.println("2. Search Student");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Display All Students (Sorted by USN)");
            System.out.println("6. Save & Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    insertStudent(scanner, bst);
                    break;
                case 2:
                    searchStudent(scanner, bst);
                    break;
                case 3:
                    updateStudent(scanner, bst);
                    break;
                case 4:
                    deleteStudent(scanner, bst);
                    break;
                case 5:
                    bst.displayAll();
                    break;
                case 6:
                    bst.saveToFile(CSV_FILE);
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void insertStudent(Scanner scanner, BinarySearchTree bst) {
        try {
            System.out.print("Enter USN (Integer): ");
            int usn = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Branch: ");
            String branch = scanner.nextLine();

            System.out.print("Enter CGPA: ");
            double cgpa = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Backlogs: ");
            int backlogs = Integer.parseInt(scanner.nextLine());

            bst.insert(usn, name, branch, cgpa, backlogs);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format! Please enter correct data types.");
        }
    }

    private static void searchStudent(Scanner scanner, BinarySearchTree bst) {
        try {
            System.out.print("Enter USN to search: ");
            int usn = Integer.parseInt(scanner.nextLine());
            StudentNode student = bst.search(usn);
            if (student != null) {
                System.out.println("Student Found:");
                System.out.println(student);
            } else {
                System.out.println("Student with USN " + usn + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid USN format!");
        }
    }

    private static void updateStudent(Scanner scanner, BinarySearchTree bst) {
        try {
            System.out.print("Enter USN to update: ");
            int usn = Integer.parseInt(scanner.nextLine());

            if (bst.search(usn) == null) {
                System.out.println("Student with USN " + usn + " does not exist.");
                return;
            }

            System.out.print("Enter New Branch: ");
            String branch = scanner.nextLine();

            System.out.print("Enter New CGPA: ");
            double cgpa = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter New Backlogs: ");
            int backlogs = Integer.parseInt(scanner.nextLine());

            bst.update(usn, cgpa, backlogs, branch);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format!");
        }
    }

    private static void deleteStudent(Scanner scanner, BinarySearchTree bst) {
        try {
            System.out.print("Enter USN to delete: ");
            int usn = Integer.parseInt(scanner.nextLine());
            bst.delete(usn);
        } catch (NumberFormatException e) {
            System.out.println("Invalid USN format!");
        }
    }
}
