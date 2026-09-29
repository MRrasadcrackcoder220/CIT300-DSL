import model.StudentRecord;
import model.ServiceRequest;
import service.CampusSystemManager;
import util.InputValidator;

/**
 * Main Entry Point - University Student Record and Campus Route Management System.
 * CIT300 Data Structures & Algorithms Practical Assignment.
 */
public class Main {

    private static final CampusSystemManager manager = new CampusSystemManager();

    public static void main(String[] args) {
        printHeader();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = InputValidator.readIntRange("Enter your choice (1-16): ", 1, 16);
            System.out.println();

            switch (choice) {
                case 1 -> handleAddStudent();
                case 2 -> handleUpdateStudent();
                case 3 -> handleDeleteStudent();
                case 4 -> manager.displayAllRecordsLinkedList();
                case 5 -> handleAddServiceRequest();
                case 6 -> handleProcessServiceRequest();
                case 7 -> manager.displayRecentActionsStack();
                case 8 -> manager.displayStudentsBST();
                case 9 -> handleSearchStudentHashing();
                case 10 -> handleAddCampusLocation();
                case 11 -> handleRemoveCampusLocation();
                case 12 -> handleAddCampusConnection();
                case 13 -> handleRemoveCampusConnection();
                case 14 -> manager.displayCampusConnections();
                case 15 -> handleTraverseCampusGraph();
                case 16 -> {
                    running = false;
                    System.out.println("=========================================================================================");
                    System.out.println("   Thank you for using the University Student & Campus Management System! Goodbye.");
                    System.out.println("=========================================================================================");
                }
            }
            System.out.println();
        }
    }

    private static void printHeader() {
        System.out.println("=========================================================================================");
        System.out.println("        CIT300 DATA STRUCTURES AND ALGORITHMS - GRADED PRACTICAL ASSIGNMENT 1            ");
        System.out.println("            UNIVERSITY STUDENT RECORD AND CAMPUS ROUTE MANAGEMENT SYSTEM                 ");
        System.out.println("=========================================================================================");
        System.out.println("Group Members:");
        System.out.println(" 1. M.R.Rasad Ahamed  (ID: 23DA2-1158) - Linked List & Student Records");
        System.out.println(" 2. M.I.M.Askhan      (ID: 23DA2-0967) - Stack & Queue Operations");
        System.out.println(" 3. M.N.Y.Ahamed     (ID: 23DA2-0865) - BST/AVL Tree & Hashing");
        System.out.println(" 4. M.S.M.Asifak      (ID: 23DA2-0750) - Graph Implementation & Traversals");
        System.out.println("=========================================================================================");
    }

    private static void printMenu() {
        System.out.println("\n-----------------------------------------------------------------------------------------");
        System.out.println("                                SYSTEM MAIN MENU                                         ");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println(" 1. Add Student Record                     9. Search Student using Hashing");
        System.out.println(" 2. Update Student Record                 10. Add Campus Location");
        System.out.println(" 3. Delete Student Record                 11. Remove Campus Location");
        System.out.println(" 4. Display All Records (Linked List)     12. Add Campus Connection/Road");
        System.out.println(" 5. Add Service Request to Queue          13. Remove Campus Connection/Road");
        System.out.println(" 6. Process Next Service Request          14. Display Campus Connections (Graph)");
        System.out.println(" 7. Display Recent Actions (Stack)        15. Traverse Campus Locations (BFS/DFS)");
        System.out.println(" 8. Display Students using BST/AVL         16. Exit Application");
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    private static void handleAddStudent() {
        System.out.println("--- [1] Add New Student Record ---");
        String id = InputValidator.readNonEmptyString("Enter Student ID (e.g. 23DA2-XXXX): ");
        if (manager.getStudentList().contains(id)) {
            System.out.println("-> Error: A student with ID '" + id + "' already exists!");
            return;
        }
        String name = InputValidator.readNonEmptyString("Enter Student Full Name: ");
        String prog = InputValidator.readNonEmptyString("Enter Programme / Major: ");
        double marks = InputValidator.readValidMarks("Enter Student Marks (0 - 100): ");

        StudentRecord record = new StudentRecord(id, name, prog, marks);
        if (manager.addStudentRecord(record)) {
            System.out.println("-> Success: Student record added successfully across all data structures!");
        } else {
            System.out.println("-> Error: Failed to add student record.");
        }
    }

    private static void handleUpdateStudent() {
        System.out.println("--- [2] Update Student Record ---");
        String id = InputValidator.readNonEmptyString("Enter Student ID to update: ");
        StudentRecord existing = manager.getStudentList().search(id);
        if (existing == null) {
            System.out.println("-> Error: No student record found with ID '" + id + "'.");
            return;
        }

        System.out.println("Current Details: " + existing);
        String name = InputValidator.readStringAllowEmpty("Enter New Name (or press Enter to keep current): ");
        String prog = InputValidator.readStringAllowEmpty("Enter New Programme (or press Enter to keep current): ");
        System.out.print("Update Marks? (y/n): ");
        String updateMarksChoice = InputValidator.readStringAllowEmpty("");
        double marks = existing.getMarks();
        if (updateMarksChoice.equalsIgnoreCase("y")) {
            marks = InputValidator.readValidMarks("Enter New Marks (0 - 100): ");
        }

        if (manager.updateStudentRecord(id, name, prog, marks)) {
            System.out.println("-> Success: Student record updated successfully!");
        } else {
            System.out.println("-> Error: Failed to update record.");
        }
    }

    private static void handleDeleteStudent() {
        System.out.println("--- [3] Delete Student Record ---");
        String id = InputValidator.readNonEmptyString("Enter Student ID to delete: ");
        if (manager.deleteStudentRecord(id)) {
            System.out.println("-> Success: Student record deleted and backed up to action history stack.");
        } else {
            System.out.println("-> Error: No record found with Student ID '" + id + "'.");
        }
    }

    private static void handleAddServiceRequest() {
        System.out.println("--- [5] Add Service Request to Queue ---");
        String studentId = InputValidator.readNonEmptyString("Enter Student ID requesting service: ");
        String details = InputValidator.readNonEmptyString("Enter Service Request Description: ");

        if (manager.addServiceRequest(studentId, details)) {
            System.out.println("-> Success: Service request added to FIFO Queue!");
        }
    }

    private static void handleProcessServiceRequest() {
        System.out.println("--- [6] Process Next Service Request ---");
        ServiceRequest req = manager.processNextServiceRequest();
        if (req != null) {
            System.out.println("-> Processed Request Successfully:");
            System.out.println("   " + req);
        } else {
            System.out.println("-> Queue is empty. No pending service requests to process.");
        }
    }

    private static void handleSearchStudentHashing() {
        System.out.println("--- [9] Search Student using Hashing (O(1) Avg Lookup) ---");
        String studentId = InputValidator.readNonEmptyString("Enter Student ID to search: ");
        StudentRecord result = manager.searchStudentHashTable(studentId);
        if (result != null) {
            System.out.println("\n[Record Found]");
            System.out.println("  Student ID : " + result.getStudentId());
            System.out.println("  Name       : " + result.getName());
            System.out.println("  Programme  : " + result.getProgramme());
            System.out.printf("  Marks      : %.2f%n", result.getMarks());
        } else {
            System.out.println("-> Result: No student record found for ID '" + studentId + "'.");
        }
    }

    private static void handleAddCampusLocation() {
        System.out.println("--- [10] Add Campus Location ---");
        String locName = InputValidator.readNonEmptyString("Enter New Campus Location Name: ");
        if (manager.addCampusLocation(locName)) {
            System.out.println("-> Success: Location '" + locName + "' added to campus graph.");
        } else {
            System.out.println("-> Error: Location already exists or invalid name.");
        }
    }

    private static void handleRemoveCampusLocation() {
        System.out.println("--- [11] Remove Campus Location ---");
        String locName = InputValidator.readNonEmptyString("Enter Campus Location Name to remove: ");
        if (manager.removeCampusLocation(locName)) {
            System.out.println("-> Success: Location '" + locName + "' and all connected roads removed.");
        } else {
            System.out.println("-> Error: Location '" + locName + "' not found in campus graph.");
        }
    }

    private static void handleAddCampusConnection() {
        System.out.println("--- [12] Add Campus Connection / Road ---");
        String loc1 = InputValidator.readNonEmptyString("Enter First Location: ");
        String loc2 = InputValidator.readNonEmptyString("Enter Second Location: ");
        double dist = InputValidator.readPositiveDouble("Enter Distance in Meters: ");

        if (manager.addCampusConnection(loc1, loc2, dist)) {
            System.out.println("-> Success: Road connection created between '" + loc1 + "' and '" + loc2 + "' (" + (int)dist + "m).");
        } else {
            System.out.println("-> Error: Could not add connection. Ensure both locations exist and are not already connected.");
        }
    }

    private static void handleRemoveCampusConnection() {
        System.out.println("--- [13] Remove Campus Connection / Road ---");
        String loc1 = InputValidator.readNonEmptyString("Enter First Location: ");
        String loc2 = InputValidator.readNonEmptyString("Enter Second Location: ");

        if (manager.removeCampusConnection(loc1, loc2)) {
            System.out.println("-> Success: Road connection between '" + loc1 + "' and '" + loc2 + "' removed.");
        } else {
            System.out.println("-> Error: Connection between '" + loc1 + "' and '" + loc2 + "' does not exist.");
        }
    }

    private static void handleTraverseCampusGraph() {
        System.out.println("--- [15] Traverse Campus Locations (BFS / DFS) ---");
        String startLoc = InputValidator.readNonEmptyString("Enter Starting Location Name: ");
        if (!manager.getCampusGraph().containsLocation(startLoc)) {
            System.out.println("-> Error: Location '" + startLoc + "' does not exist in campus network.");
            return;
        }

        System.out.println("Select Traversal Algorithm:");
        System.out.println("  1. Breadth-First Search (BFS)");
        System.out.println("  2. Depth-First Search (DFS)");
        int choice = InputValidator.readIntRange("Choice (1-2): ", 1, 2);

        if (choice == 1) {
            manager.traverseCampusBFS(startLoc);
        } else {
            manager.traverseCampusDFS(startLoc);
        }
    }
}
