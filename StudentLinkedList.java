package datastructures;

import model.StudentRecord;

/**
 * Custom Singly Linked List for managing Student Records.
 * Member 1 Responsibility: Linked List & Student Record Management.
 */
public class StudentLinkedList {

    private static class Node {
        StudentRecord record;
        Node next;

        Node(StudentRecord record) {
            this.record = record;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public StudentLinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Adds a new student record to the linked list.
     * @return true if added successfully, false if duplicate student ID exists.
     */
    public boolean add(StudentRecord record) {
        if (record == null || record.getStudentId() == null) {
            return false;
        }

        if (contains(record.getStudentId())) {
            return false; // Prevent duplicates
        }

        Node newNode = new Node(record);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        return true;
    }

    /**
     * Searches for a student record by ID.
     */
    public StudentRecord search(String studentId) {
        if (studentId == null || head == null) {
            return null;
        }
        Node current = head;
        while (current != null) {
            if (current.record.getStudentId().equalsIgnoreCase(studentId.trim())) {
                return current.record;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Checks if a student ID exists in the linked list.
     */
    public boolean contains(String studentId) {
        return search(studentId) != null;
    }

    /**
     * Updates an existing student record's details.
     */
    public boolean update(String studentId, String newName, String newProgramme, double newMarks) {
        StudentRecord record = search(studentId);
        if (record == null) {
            return false;
        }
        if (newName != null && !newName.trim().isEmpty()) {
            record.setName(newName.trim());
        }
        if (newProgramme != null && !newProgramme.trim().isEmpty()) {
            record.setProgramme(newProgramme.trim());
        }
        if (newMarks >= 0 && newMarks <= 100) {
            record.setMarks(newMarks);
        }
        return true;
    }

    /**
     * Deletes a student record by ID and returns the removed record.
     */
    public StudentRecord delete(String studentId) {
        if (studentId == null || head == null) {
            return null;
        }

        if (head.record.getStudentId().equalsIgnoreCase(studentId.trim())) {
            StudentRecord deleted = head.record;
            head = head.next;
            size--;
            return deleted;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.record.getStudentId().equalsIgnoreCase(studentId.trim())) {
                StudentRecord deleted = current.next.record;
                current.next = current.next.next;
                size--;
                return deleted;
            }
            current = current.next;
        }

        return null;
    }

    /**
     * Displays all student records stored in the Linked List.
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("-> Linked List is empty. No student records found.");
            return;
        }

        System.out.println("\n=========================================================================================");
        System.out.println("                       STUDENT RECORDS (LINKED LIST VIEW)                                ");
        System.out.println("=========================================================================================");
        System.out.printf("%-6s | %-12s | %-24s | %-25s | %-8s%n", "No.", "Student ID", "Student Name", "Programme", "Marks");
        System.out.println("-----------------------------------------------------------------------------------------");

        Node current = head;
        int count = 1;
        while (current != null) {
            StudentRecord r = current.record;
            System.out.printf("%-6d | %-12s | %-24s | %-25s | %6.2f%n",
                    count++, r.getStudentId(), r.getName(), r.getProgramme(), r.getMarks());
            current = current.next;
        }
        System.out.println("=========================================================================================");
        System.out.println("Total Records in Linked List: " + size);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns an array of all StudentRecords currently stored.
     */
    public StudentRecord[] getAllRecords() {
        StudentRecord[] array = new StudentRecord[size];
        Node current = head;
        int idx = 0;
        while (current != null) {
            array[idx++] = current.record;
            current = current.next;
        }
        return array;
    }
}
