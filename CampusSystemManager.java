package service;

import datastructures.*;
import model.*;

/**
 * Service Manager unifying and synchronizing all data structures:
 * Linked List, Stack, Queue, BST, Hash Table, and Graph.
 */
public class CampusSystemManager {

    private final StudentLinkedList studentList;
    private final ActionStack actionStack;
    private final ServiceQueue serviceQueue;
    private final StudentBST studentBST;
    private final StudentHashTable studentHashTable;
    private final CampusGraph campusGraph;

    private int serviceRequestCounter;

    public CampusSystemManager() {
        this.studentList = new StudentLinkedList();
        this.actionStack = new ActionStack();
        this.serviceQueue = new ServiceQueue();
        this.studentBST = new StudentBST();
        this.studentHashTable = new StudentHashTable();
        this.campusGraph = new CampusGraph();
        this.serviceRequestCounter = 101;
    }

    // =========================================================================
    // 1. STUDENT RECORD OPERATIONS (Linked List, BST, Hash Table, Stack)
    // =========================================================================

    public boolean addStudentRecord(StudentRecord record) {
        if (record == null) return false;
        boolean added = studentList.add(record);
        if (added) {
            syncTreeAndHashTable();
            actionStack.push(new ActionLog("ADD_STUDENT",
                    "Added student record: " + record.getStudentId() + " (" + record.getName() + ")"));
            return true;
        }
        return false;
    }

    public boolean updateStudentRecord(String studentId, String newName, String newProg, double newMarks) {
        boolean updated = studentList.update(studentId, newName, newProg, newMarks);
        if (updated) {
            syncTreeAndHashTable();
            actionStack.push(new ActionLog("UPDATE_STUDENT",
                    "Updated record for Student ID: " + studentId.toUpperCase()));
            return true;
        }
        return false;
    }

    public boolean deleteStudentRecord(String studentId) {
        StudentRecord deleted = studentList.delete(studentId);
        if (deleted != null) {
            syncTreeAndHashTable();
            actionStack.push(new ActionLog("DELETE_STUDENT",
                    "Deleted student: " + deleted.getStudentId() + " (" + deleted.getName() + ")", deleted));
            return true;
        }
        return false;
    }

    private void syncTreeAndHashTable() {
        StudentRecord[] records = studentList.getAllRecords();
        studentBST.rebuild(records);
        studentHashTable.rebuild(records);
    }

    public void displayAllRecordsLinkedList() {
        studentList.display();
    }

    public void displayStudentsBST() {
        studentBST.displayInOrder();
    }

    public StudentRecord searchStudentHashTable(String studentId) {
        return studentHashTable.get(studentId);
    }

    public StudentRecord searchStudentLinkedList(String studentId) {
        return studentList.search(studentId);
    }

    // =========================================================================
    // 2. QUEUE & SERVICE REQUEST OPERATIONS
    // =========================================================================

    public boolean addServiceRequest(String studentId, String details) {
        if (!studentList.contains(studentId)) {
            System.out.println("-> Warning: Student ID '" + studentId + "' not found in record system, but queuing request.");
        }
        String reqId = "REQ" + (serviceRequestCounter++);
        ServiceRequest request = new ServiceRequest(reqId, studentId, details);
        serviceQueue.enqueue(request);
        actionStack.push(new ActionLog("ADD_REQUEST", "Queued service request " + reqId + " for Student " + studentId));
        return true;
    }

    public ServiceRequest processNextServiceRequest() {
        ServiceRequest req = serviceQueue.dequeue();
        if (req != null) {
            actionStack.push(new ActionLog("PROCESS_REQUEST", "Processed service request " + req.getRequestId() + " (" + req.getRequestDetails() + ")"));
        }
        return req;
    }

    public void displayServiceQueue() {
        serviceQueue.display();
    }

    // =========================================================================
    // 3. STACK & RECENT ACTIONS OPERATIONS
    // =========================================================================

    public void displayRecentActionsStack() {
        actionStack.display();
    }

    // =========================================================================
    // 4. GRAPH & CAMPUS ROUTE OPERATIONS
    // =========================================================================

    public boolean addCampusLocation(String locationName) {
        boolean added = campusGraph.addLocation(locationName);
        if (added) {
            actionStack.push(new ActionLog("ADD_LOCATION", "Added campus location: " + locationName.trim()));
            return true;
        }
        return false;
    }

    public boolean removeCampusLocation(String locationName) {
        boolean removed = campusGraph.removeLocation(locationName);
        if (removed) {
            actionStack.push(new ActionLog("REMOVE_LOCATION", "Removed campus location: " + locationName.trim()));
            return true;
        }
        return false;
    }

    public boolean addCampusConnection(String loc1, String loc2, double distance) {
        boolean added = campusGraph.addConnection(loc1, loc2, distance);
        if (added) {
            actionStack.push(new ActionLog("ADD_CONNECTION",
                    "Added road between '" + loc1 + "' and '" + loc2 + "' (" + (int)distance + "m)"));
            return true;
        }
        return false;
    }

    public boolean removeCampusConnection(String loc1, String loc2) {
        boolean removed = campusGraph.removeConnection(loc1, loc2);
        if (removed) {
            actionStack.push(new ActionLog("REMOVE_CONNECTION",
                    "Removed road connection between '" + loc1 + "' and '" + loc2 + "'"));
            return true;
        }
        return false;
    }

    public void displayCampusConnections() {
        campusGraph.displayConnections();
    }

    public void traverseCampusBFS(String startLocation) {
        campusGraph.bfsTraversal(startLocation);
    }

    public void traverseCampusDFS(String startLocation) {
        campusGraph.dfsTraversal(startLocation);
    }

    public CampusGraph getCampusGraph() {
        return campusGraph;
    }

    public StudentLinkedList getStudentList() {
        return studentList;
    }
}
