package datastructures;

import model.StudentRecord;

/**
 * Custom Hash Table using Separate Chaining for O(1) average-time Student ID searching.
 * Member 3 Responsibility: BST/AVL Tree & Hashing Implementation.
 */
public class StudentHashTable {

    private static class HashNode {
        String key; // Student ID
        StudentRecord record;
        HashNode next;

        HashNode(String key, StudentRecord record) {
            this.key = key;
            this.record = record;
            this.next = null;
        }
    }

    private static final int DEFAULT_CAPACITY = 17; // Prime number for better distribution
    private HashNode[] table;
    private int capacity;
    private int size;

    public StudentHashTable() {
        this(DEFAULT_CAPACITY);
    }

    public StudentHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new HashNode[capacity];
        this.size = 0;
    }

    /**
     * Hash function to map Student ID string to a bucket index.
     */
    private int hashFunction(String key) {
        if (key == null) return 0;
        int hash = 0;
        String normalized = key.trim().toUpperCase();
        for (int i = 0; i < normalized.length(); i++) {
            hash = (31 * hash + normalized.charAt(i)) % capacity;
        }
        return Math.abs(hash);
    }

    /**
     * Inserts or updates a student record in the hash table.
     */
    public boolean put(StudentRecord record) {
        if (record == null || record.getStudentId() == null) {
            return false;
        }

        String key = record.getStudentId().trim();
        int index = hashFunction(key);

        HashNode head = table[index];
        HashNode current = head;

        // Check if key already exists in bucket chain
        while (current != null) {
            if (current.key.equalsIgnoreCase(key)) {
                current.record = record; // Update existing record
                return true;
            }
            current = current.next;
        }

        // Insert new entry at the head of bucket chain
        HashNode newNode = new HashNode(key, record);
        newNode.next = head;
        table[index] = newNode;
        size++;
        return true;
    }

    /**
     * Efficiently searches for a student record by ID using hashing in O(1) average time.
     */
    public StudentRecord get(String studentId) {
        if (studentId == null) return null;

        String key = studentId.trim();
        int index = hashFunction(key);
        HashNode current = table[index];

        int probes = 0;
        while (current != null) {
            probes++;
            if (current.key.equalsIgnoreCase(key)) {
                System.out.printf("-> Hash Search Success! Found in Bucket [%d] with %d probe(s).%n", index, probes);
                return current.record;
            }
            current = current.next;
        }

        System.out.printf("-> Hash Search: Record '%s' not found in Bucket [%d] (%d probes).%n", studentId, index, probes);
        return null;
    }

    /**
     * Removes a student record from the hash table.
     */
    public boolean remove(String studentId) {
        if (studentId == null) return false;

        String key = studentId.trim();
        int index = hashFunction(key);
        HashNode current = table[index];
        HashNode prev = null;

        while (current != null) {
            if (current.key.equalsIgnoreCase(key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    /**
     * Rebuilds the Hash Table from an array of student records.
     */
    public void rebuild(StudentRecord[] records) {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        size = 0;
        if (records != null) {
            for (StudentRecord r : records) {
                put(r);
            }
        }
    }

    /**
     * Displays the Hash Table bucket distribution to demonstrate internal hashing structure.
     */
    public void displayTable() {
        System.out.println("\n=========================================================================================");
        System.out.println("                 STUDENT HASH TABLE (SEPARATE CHAINING BUCKET VIEW)                      ");
        System.out.println("=========================================================================================");

        for (int i = 0; i < capacity; i++) {
            System.out.printf("Bucket [%02d] -> ", i);
            HashNode current = table[i];
            if (current == null) {
                System.out.println("[ Empty ]");
            } else {
                StringBuilder sb = new StringBuilder();
                while (current != null) {
                    sb.append(String.format("(ID: %s, Name: %s) -> ", current.key, current.record.getName()));
                    current = current.next;
                }
                sb.append("null");
                System.out.println(sb.toString());
            }
        }
        System.out.println("=========================================================================================");
        System.out.println("Total Hash Table Entries: " + size + " | Capacity: " + capacity);
    }

    public int getSize() {
        return size;
    }
}
