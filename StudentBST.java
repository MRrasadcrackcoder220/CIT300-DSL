package datastructures;

import model.StudentRecord;

/**
 * Custom Binary Search Tree (BST) for organizing and searching student records by Student ID.
 * Member 3 Responsibility: BST/AVL Tree & Hashing Implementation.
 */
public class StudentBST {

    private static class TreeNode {
        StudentRecord record;
        TreeNode left;
        TreeNode right;

        TreeNode(StudentRecord record) {
            this.record = record;
            this.left = null;
            this.right = null;
        }
    }

    private TreeNode root;
    private int count;

    public StudentBST() {
        this.root = null;
        this.count = 0;
    }

    /**
     * Inserts a student record into the BST based on Student ID.
     */
    public boolean insert(StudentRecord record) {
        if (record == null || record.getStudentId() == null) {
            return false;
        }
        int initialCount = count;
        root = insertRecursive(root, record);
        return count > initialCount;
    }

    private TreeNode insertRecursive(TreeNode node, StudentRecord record) {
        if (node == null) {
            count++;
            return new TreeNode(record);
        }

        int cmp = record.getStudentId().compareToIgnoreCase(node.record.getStudentId());
        if (cmp < 0) {
            node.left = insertRecursive(node.left, record);
        } else if (cmp > 0) {
            node.right = insertRecursive(node.right, record);
        } else {
            // Duplicate ID found - update record
            node.record = record;
        }
        return node;
    }

    /**
     * Searches for a student record by ID in the BST.
     */
    public StudentRecord search(String studentId) {
        if (studentId == null) return null;
        TreeNode result = searchRecursive(root, studentId.trim());
        return result != null ? result.record : null;
    }

    private TreeNode searchRecursive(TreeNode node, String studentId) {
        if (node == null) return null;

        int cmp = studentId.compareToIgnoreCase(node.record.getStudentId());
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return searchRecursive(node.left, studentId);
        } else {
            return searchRecursive(node.right, studentId);
        }
    }

    /**
     * Deletes a student record by ID from the BST.
     */
    public boolean delete(String studentId) {
        if (studentId == null || search(studentId) == null) {
            return false;
        }
        root = deleteRecursive(root, studentId.trim());
        count--;
        return true;
    }

    private TreeNode deleteRecursive(TreeNode node, String studentId) {
        if (node == null) return null;

        int cmp = studentId.compareToIgnoreCase(node.record.getStudentId());
        if (cmp < 0) {
            node.left = deleteRecursive(node.left, studentId);
        } else if (cmp > 0) {
            node.right = deleteRecursive(node.right, studentId);
        } else {
            // Node to delete found
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Node with two children: find min in right subtree
            TreeNode minNode = findMin(node.right);
            node.record = minNode.record;
            node.right = deleteRecursive(node.right, minNode.record.getStudentId());
        }
        return node;
    }

    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Rebuilds the tree from an array of student records.
     */
    public void rebuild(StudentRecord[] records) {
        root = null;
        count = 0;
        if (records != null) {
            for (StudentRecord r : records) {
                insert(r);
            }
        }
    }

    /**
     * Displays all student records in sorted order (In-Order Traversal).
     */
    public void displayInOrder() {
        if (root == null) {
            System.out.println("-> BST is empty. No student records stored.");
            return;
        }

        System.out.println("\n=========================================================================================");
        System.out.println("                 STUDENT RECORDS IN BST (IN-ORDER TRAVERSAL - SORTED)                    ");
        System.out.println("=========================================================================================");
        System.out.printf("%-12s | %-24s | %-25s | %-8s%n", "Student ID", "Student Name", "Programme", "Marks");
        System.out.println("-----------------------------------------------------------------------------------------");
        inOrderRecursive(root);
        System.out.println("=========================================================================================");
        System.out.println("Total BST Nodes: " + count);
    }

    private void inOrderRecursive(TreeNode node) {
        if (node != null) {
            inOrderRecursive(node.left);
            StudentRecord r = node.record;
            System.out.printf("%-12s | %-24s | %-25s | %6.2f%n",
                    r.getStudentId(), r.getName(), r.getProgramme(), r.getMarks());
            inOrderRecursive(node.right);
        }
    }

    public void displayPreOrder() {
        if (root == null) {
            System.out.println("-> BST is empty.");
            return;
        }
        System.out.println("\n--- BST PRE-ORDER TRAVERSAL ---");
        preOrderRecursive(root);
    }

    private void preOrderRecursive(TreeNode node) {
        if (node != null) {
            System.out.println(node.record);
            preOrderRecursive(node.left);
            preOrderRecursive(node.right);
        }
    }

    public void displayPostOrder() {
        if (root == null) {
            System.out.println("-> BST is empty.");
            return;
        }
        System.out.println("\n--- BST POST-ORDER TRAVERSAL ---");
        postOrderRecursive(root);
    }

    private void postOrderRecursive(TreeNode node) {
        if (node != null) {
            postOrderRecursive(node.left);
            postOrderRecursive(node.right);
            System.out.println(node.record);
        }
    }

    public int getCount() {
        return count;
    }

    public boolean isEmpty() {
        return root == null;
    }
}
