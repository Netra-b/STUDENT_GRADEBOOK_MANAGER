
import java.io.*;
import java.util.*;

public class BinarySearchTree {
    private StudentNode root;

    public BinarySearchTree() {
        this.root = null;
    }

    // 2. Insert Student
    public void insert(int usn, String name, String branch, double cgpa, int backlogs) {
        if (search(usn) != null) {
            System.out.println("Error: Student with USN " + usn + " already exists!");
            return;
        }
        root = insertRec(root, usn, name, branch, cgpa, backlogs);
        System.out.println("Student with USN " + usn + " inserted successfully.");
    }

    private StudentNode insertRec(StudentNode root, int usn, String name, String branch, double cgpa, int backlogs) {
        if (root == null) {
            return new StudentNode(usn, name, branch, cgpa, backlogs);
        }
        if (usn < root.usn) {
            root.left = insertRec(root.left, usn, name, branch, cgpa, backlogs);
        } else if (usn > root.usn) {
            root.right = insertRec(root.right, usn, name, branch, cgpa, backlogs);
        }
        return root;
    }

    // 3. Search Student
    public StudentNode search(int usn) {
        return searchRec(root, usn);
    }

    private StudentNode searchRec(StudentNode root, int usn) {
        if (root == null || root.usn == usn) {
            return root;
        }
        if (usn < root.usn) {
            return searchRec(root.left, usn);
        }
        return searchRec(root.right, usn);
    }

    // 4. Update Student
    public void update(int usn, double newCgpa, int newBacklogs, String newBranch) {
        StudentNode node = search(usn);
        if (node != null) {
            node.updateDetails(null, newBranch, newCgpa, newBacklogs);
            System.out.println("Student details updated successfully for USN: " + usn);
            System.out.println("New Eligibility: " + (node.eligible ? "Yes" : "No"));
        } else {
            System.out.println("Student with USN " + usn + " not found!");
        }
    }

    // 5. Delete Student
    public void delete(int usn) {
        if (search(usn) == null) {
            System.out.println("Student with USN " + usn + " not found!");
            return;
        }
        root = deleteRec(root, usn);
        System.out.println("Student with USN " + usn + " deleted successfully.");
    }

    private StudentNode deleteRec(StudentNode root, int usn) {
        if (root == null) return root;

        if (usn < root.usn) {
            root.left = deleteRec(root.left, usn);
        } else if (usn > root.usn) {
            root.right = deleteRec(root.right, usn);
        } else {
            // Node with only one child or no child
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            root.usn = minValue(root.right);
            // We also need to copy data... technically strict BST delete often just copies the key
            // but here we are storing data in the node. So complexities arise. 
            // Better approach for data records: Replace the NODE with successor node data.
            // But we can't easily swap *all* fields without boiler plate. 
            // Let's find successor node, copy ALL data to current root, then delete successor.
            StudentNode successor = findMinNode(root.right);
            copyData(root, successor);
            
            root.right = deleteRec(root.right, successor.usn);
        }
        return root;
    }

    private int minValue(StudentNode root) {
        int minv = root.usn;
        while (root.left != null) {
            minv = root.left.usn;
            root = root.left;
        }
        return minv;
    }
    
    private StudentNode findMinNode(StudentNode root) {
        while (root.left != null) root = root.left;
        return root;
    }
    
    private void copyData(StudentNode dest, StudentNode src) {
        dest.usn = src.usn;
        dest.name = src.name;
        dest.branch = src.branch;
        dest.cgpa = src.cgpa;
        dest.backlogs = src.backlogs;
        dest.eligible = src.eligible;
    }

    // 6. Display All (Inorder)
    public void displayAll() {
        if (root == null) {
            System.out.println("No records found.");
            return;
        }
        System.out.println("\n--- Student Records (Sorted by USN) ---");
        inorderRec(root);
        System.out.println("---------------------------------------");
    }

    private void inorderRec(StudentNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root);
            inorderRec(root.right);
        }
    }

    // 7. CSV File Handling
    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File " + filename + " not found. Starting with empty database.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    try {
                        int usn = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        String branch = parts[2].trim();
                        double cgpa = Double.parseDouble(parts[3].trim());
                        int backlogs = Integer.parseInt(parts[4].trim());
                        
                        // Use internal insert or just direct insert to avoid duplicate messages on load? 
                        // Using insertRec to silently build tree is better for load
                        // But for simplicity reuse insert but avoid print spam? 
                        // Let's just use insert for now, console output is acceptable during init or we can suppress.
                        // Actually, let's just do silent insert here.
                        if (search(usn) == null) {
                             root = insertRec(root, usn, name, branch, cgpa, backlogs);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid record: " + line);
                    }
                }
            }
            System.out.println("Data loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            saveRec(root, bw);
            System.out.println("Data saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private void saveRec(StudentNode root, BufferedWriter bw) throws IOException {
        if (root != null) {
            saveRec(root.left, bw);
            bw.write(root.toCSV());
            bw.newLine();
            saveRec(root.right, bw);
        }
    }
}
