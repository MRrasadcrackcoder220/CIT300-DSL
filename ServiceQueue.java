package datastructures;

import model.ServiceRequest;

/**
 * Custom FIFO Queue for managing student service requests in order of arrival.
 * Member 2 Responsibility: Stack & Queue Implementation.
 */
public class ServiceQueue {

    private static class Node {
        ServiceRequest request;
        Node next;

        Node(ServiceRequest request) {
            this.request = request;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;
    private int size;

    public ServiceQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Enqueues a new service request at the rear of the queue.
     */
    public void enqueue(ServiceRequest request) {
        if (request == null) return;
        Node newNode = new Node(request);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /**
     * Dequeues and returns the next pending service request from the front of the queue.
     */
    public ServiceRequest dequeue() {
        if (isEmpty()) {
            return null;
        }
        ServiceRequest request = front.request;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return request;
    }

    /**
     * Peeks at the front request without dequeuing it.
     */
    public ServiceRequest peek() {
        return isEmpty() ? null : front.request;
    }

    /**
     * Displays all pending service requests in FIFO order.
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("-> Service Queue is empty. No pending requests.");
            return;
        }

        System.out.println("\n=========================================================================================");
        System.out.println("                   PENDING SERVICE REQUESTS (QUEUE - FIFO)                                ");
        System.out.println("=========================================================================================");
        System.out.printf("%-5s | %-10s | %-12s | %-25s | %-20s%n", "Pos", "Req ID", "Student ID", "Details", "Timestamp");
        System.out.println("-----------------------------------------------------------------------------------------");

        Node current = front;
        int pos = 1;
        while (current != null) {
            ServiceRequest req = current.request;
            System.out.printf("#%-4d | %-10s | %-12s | %-25s | %-20s%n",
                    pos++, req.getRequestId(), req.getStudentId(), req.getRequestDetails(), req.getTimestamp());
            current = current.next;
        }
        System.out.println("=========================================================================================");
        System.out.println("Total Pending Service Requests: " + size);
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }
}
