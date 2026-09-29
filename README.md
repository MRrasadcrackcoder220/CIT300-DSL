# University Student Record & Campus Route Management System
**CIT300 Data Structures and Algorithms - Graded Practical Assignment 1 (Week 10)**  
*Contribution: 10% of Final Module Grade*

---

## 1. Group Member Details & Responsibilities

| No. | Full Name | Student ID | Assigned Responsibility | Contribution (%) |
|---|---|---|---|---|
| 1 | **M.R.Rasad Ahamed** | `23DA2-1158` | Linked list implementation & student-record management (Add, Update, Delete, Search, Display) | 25% |
| 2 | **M.I.M.Askhan** | `23DA2-0967` | Stack & Queue implementation, recent action audit history & service request queue processing | 25% |
| 3 | **M.N.Y.Ahamed** | `23DA2-0865` | Binary Search Tree (BST) sorted traversals & O(1) Separate Chaining Hash Table search | 25% |
| 4 | **M.S.M.Asifak** | `23DA2-0750` | Graph representation (Adjacency List), location/road management, and BFS/DFS graph traversals | 25% |

---

## 2. System Overview & Architecture

The **University Student Record and Campus Route Management System** is a console application developed in Java. The project implements 6 data structures built from scratch without relying on `java.util` collections for underlying data storage:

1. **Singly Linked List (`StudentLinkedList`)**: Stores and manages core student records (`Student ID`, `Name`, `Programme`, `Marks`).
2. **LIFO Stack (`ActionStack`)**: Maintains a complete audit log of recent system operations and deleted student record backups.
3. **FIFO Queue (`ServiceQueue`)**: Queues student service requests in order of arrival and processes them sequentially.
4. **Binary Search Tree (`StudentBST`)**: Organizes student records by Student ID and provides In-Order, Pre-Order, and Post-Order tree traversals.
5. **Hash Table (`StudentHashTable`)**: Implements separate chaining with a polynomial hash function to achieve average $O(1)$ student search performance.
6. **Adjacency List Graph (`CampusGraph`)**: Models campus locations as vertices and roads/paths as weighted undirected edges. Supports dynamic location/connection insertion/removal and graph traversals (Breadth-First Search & Depth-First Search).

---

## 3. Project Structure

```text
c:\Users\user\Desktop\DATA STRU FINAL\
├── src/
│   ├── model/
│   │   ├── StudentRecord.java        # Core Student entity (ID, Name, Programme, Marks)
│   │   ├── ServiceRequest.java       # Service request entity for Queue
│   │   └── ActionLog.java            # Action log entity for Stack
│   ├── datastructures/
│   │   ├── StudentLinkedList.java    # Custom Singly Linked List
│   │   ├── ActionStack.java          # Custom LIFO Stack
│   │   ├── ServiceQueue.java         # Custom FIFO Queue
│   │   ├── StudentBST.java           # Custom Binary Search Tree
│   │   ├── StudentHashTable.java     # Custom Separate Chaining Hash Table
│   │   └── CampusGraph.java          # Custom Adjacency List Graph (BFS/DFS)
│   ├── service/
│   │   └── CampusSystemManager.java  # Engine synchronizing all data structures
│   ├── util/
│   │   └── InputValidator.java       # Robust input reader and validation helper
│   └── Main.java                     # Console menu interface (Options 1-16)
└── README.md                         # Complete project documentation & guide
```

---

## 4. Menu Options & Features

The system presents an interactive 16-option console menu matching all assignment specifications:

1. **Add Student Record**: Inserts a new student into Linked List, BST, and Hash Table (validates marks 0-100 and checks duplicate ID).
2. **Update Student Record**: Updates existing student details across all structures.
3. **Delete Student Record**: Deletes record and pushes a backup to the Action Stack.
4. **Display All Records using Linked List**: Tabular view of all registered student records.
5. **Add Service Request to Queue**: Enqueues student service request in arrival order.
6. **Process Next Service Request**: Dequeues and processes the next pending request.
7. **Display Recent Actions using Stack**: Prints system audit trail (LIFO order).
8. **Display Students using BST/AVL**: Prints student records sorted by Student ID (In-Order Traversal).
9. **Search Student using Hashing**: Performs $O(1)$ average hash table lookup with bucket probe diagnostic output.
10. **Add Campus Location**: Adds a new vertex location to the campus graph.
11. **Remove Campus Location**: Deletes a campus location and all attached roads.
12. **Add Campus Connection/Road**: Creates a weighted road connection between two locations.
13. **Remove Campus Connection/Road**: Removes a road connection between two locations.
14. **Display Campus Connections**: Prints campus network adjacency list.
15. **Traverse Campus Locations using BFS or DFS**: Performs Breadth-First or Depth-First traversal starting from any location.
16. **Exit**: Terminates the application cleanly.

---

## 5. Compilation & Execution Guide

### Prerequisites
- JDK 11 or higher (Tested with OpenJDK / Oracle JDK 24).

### Step 1: Open Terminal in Project Directory
```powershell
cd "c:\Users\user\Desktop\DATA STRU FINAL"
```

### Step 2: Compile Java Source Files
```powershell
javac -d bin -sourcepath src src/Main.java
```

### Step 3: Run the Application
```powershell
java -cp bin Main
```

---

## 6. Final Submission Checklist

- [x] Complete project implemented including all 6 custom data structures & graph functionality.
- [x] All group member names and student IDs accurately documented.
- [x] Responsibilities and individual contributions defined (25% per member).
- [x] Compilation and execution verified clean without errors.
- [ ] If submitting via Google Drive:
  - [ ] Upload complete project folder to Google Drive.
  - [ ] Copy Google Drive link into a Notepad (`.txt`) file.
  - [ ] Grant **Editor** permission to `asanka.r@sltc.ac.lk`.
  - [ ] Grant **Editor** permission to `kaushika.w@sltc.ac.lk`.
  - [ ] Verify permissions before submitting `.txt` file on LMS before **29th September**.
