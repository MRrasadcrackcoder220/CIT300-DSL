package datastructures;

import model.ActionLog;

/**
 * Custom LIFO Stack for managing recent system actions and audit history.
 * Member 2 Responsibility: Stack & Queue Implementation.
 */
public class ActionStack {

    private static class Node {
        ActionLog log;
        Node next;

        Node(ActionLog log) {
            this.log = log;
            this.next = null;
        }
    }

    private Node top;
    private int size;

    public ActionStack() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Pushes a new action log onto the stack.
     */
    public void push(ActionLog log) {
        if (log == null) return;
        Node newNode = new Node(log);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Pops and returns the most recent action log from the stack.
     */
    public ActionLog pop() {
        if (isEmpty()) {
            return null;
        }
        ActionLog popped = top.log;
        top = top.next;
        size--;
        return popped;
    }

    /**
     * Peeks at the top action log without removing it.
     */
    public ActionLog peek() {
        return isEmpty() ? null : top.log;
    }

    /**
     * Displays all recent actions stored in the Stack (LIFO Order: Most Recent First).
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("-> Action Stack is empty. No recent actions recorded.");
            return;
        }

        System.out.println("\n=========================================================================================");
        System.out.println("                    RECENT ACTIONS AUDIT TRAIL (STACK - LIFO)                             ");
        System.out.println("=========================================================================================");
        System.out.printf("%-5s | %-20s | %-18s | %-40s%n", "Top", "Timestamp", "Action Type", "Description");
        System.out.println("-----------------------------------------------------------------------------------------");

        Node current = top;
        int level = 1;
        while (current != null) {
            ActionLog log = current.log;
            System.out.printf("#%-4d | %-20s | %-18s | %-40s%n",
                    level++, log.getTimestamp(), log.getActionType(), log.getDescription());
            current = current.next;
        }
        System.out.println("=========================================================================================");
        System.out.println("Total Action Logs in Stack: " + size);
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}
