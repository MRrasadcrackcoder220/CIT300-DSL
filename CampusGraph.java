package datastructures;

/**
 * Custom Adjacency List Graph for modeling University Campus Locations & Routes.
 * Supports Vertices (Locations), Edges (Roads/Paths with distance), and BFS/DFS Traversals.
 * Member 4 Responsibility: Graph Implementation, Campus Locations, Connections, & BFS/DFS.
 */
public class CampusGraph {

    // Represents an edge (road/path connection) to a target location with a distance/weight
    public static class EdgeNode {
        String targetLocation;
        double distanceMeters;
        EdgeNode next;

        EdgeNode(String targetLocation, double distanceMeters) {
            this.targetLocation = targetLocation;
            this.distanceMeters = distanceMeters;
            this.next = null;
        }
    }

    // Represents a vertex (Campus Location) and its adjacency list of connecting roads
    public static class LocationVertex {
        String locationName;
        EdgeNode headEdge;
        LocationVertex nextVertex;

        LocationVertex(String locationName) {
            this.locationName = locationName;
            this.headEdge = null;
            this.nextVertex = null;
        }
    }

    private LocationVertex headVertex;
    private int vertexCount;

    public CampusGraph() {
        this.headVertex = null;
        this.vertexCount = 0;
    }

    /**
     * Finds a vertex by location name.
     */
    private LocationVertex findVertex(String locationName) {
        if (locationName == null) return null;
        LocationVertex current = headVertex;
        while (current != null) {
            if (current.locationName.equalsIgnoreCase(locationName.trim())) {
                return current;
            }
            current = current.nextVertex;
        }
        return null;
    }

    /**
     * Adds a new campus location (Vertex) to the graph.
     * @return true if added successfully, false if duplicate or invalid.
     */
    public boolean addLocation(String locationName) {
        if (locationName == null || locationName.trim().isEmpty()) {
            return false;
        }
        String formattedName = locationName.trim();
        if (findVertex(formattedName) != null) {
            return false; // Duplicate location
        }

        LocationVertex newVertex = new LocationVertex(formattedName);
        if (headVertex == null) {
            headVertex = newVertex;
        } else {
            LocationVertex current = headVertex;
            while (current.nextVertex != null) {
                current = current.nextVertex;
            }
            current.nextVertex = newVertex;
        }
        vertexCount++;
        return true;
    }

    /**
     * Removes a campus location (Vertex) and all attached roads (Edges).
     */
    public boolean removeLocation(String locationName) {
        if (locationName == null || headVertex == null) {
            return false;
        }
        String targetName = locationName.trim();
        LocationVertex targetVertex = findVertex(targetName);
        if (targetVertex == null) {
            return false;
        }

        // 1. Remove all edges pointing TO this location from other vertices
        LocationVertex currV = headVertex;
        while (currV != null) {
            if (!currV.locationName.equalsIgnoreCase(targetName)) {
                removeEdgeFromVertex(currV, targetName);
            }
            currV = currV.nextVertex;
        }

        // 2. Remove the location vertex itself
        if (headVertex.locationName.equalsIgnoreCase(targetName)) {
            headVertex = headVertex.nextVertex;
        } else {
            LocationVertex current = headVertex;
            while (current.nextVertex != null) {
                if (current.nextVertex.locationName.equalsIgnoreCase(targetName)) {
                    current.nextVertex = current.nextVertex.nextVertex;
                    break;
                }
                current = current.nextVertex;
            }
        }
        vertexCount--;
        return true;
    }

    /**
     * Adds an undirected connection/road (Edge) between two campus locations.
     */
    public boolean addConnection(String loc1, String loc2, double distanceMeters) {
        if (loc1 == null || loc2 == null || loc1.equalsIgnoreCase(loc2)) {
            return false;
        }

        LocationVertex v1 = findVertex(loc1);
        LocationVertex v2 = findVertex(loc2);

        if (v1 == null || v2 == null) {
            return false; // One or both locations do not exist
        }

        if (hasConnection(v1, loc2)) {
            return false; // Edge already exists
        }

        // Add edge loc1 -> loc2
        addEdgeToVertex(v1, v2.locationName, distanceMeters);
        // Add edge loc2 -> loc1 (undirected graph)
        addEdgeToVertex(v2, v1.locationName, distanceMeters);

        return true;
    }

    private void addEdgeToVertex(LocationVertex v, String targetLoc, double dist) {
        EdgeNode newEdge = new EdgeNode(targetLoc, dist);
        if (v.headEdge == null) {
            v.headEdge = newEdge;
        } else {
            EdgeNode curr = v.headEdge;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = newEdge;
        }
    }

    /**
     * Removes an undirected connection/road (Edge) between two campus locations.
     */
    public boolean removeConnection(String loc1, String loc2) {
        LocationVertex v1 = findVertex(loc1);
        LocationVertex v2 = findVertex(loc2);

        if (v1 == null || v2 == null) {
            return false;
        }

        boolean removed1 = removeEdgeFromVertex(v1, v2.locationName);
        boolean removed2 = removeEdgeFromVertex(v2, v1.locationName);

        return removed1 || removed2;
    }

    private boolean removeEdgeFromVertex(LocationVertex v, String targetLoc) {
        if (v.headEdge == null) return false;

        if (v.headEdge.targetLocation.equalsIgnoreCase(targetLoc)) {
            v.headEdge = v.headEdge.next;
            return true;
        }

        EdgeNode curr = v.headEdge;
        while (curr.next != null) {
            if (curr.next.targetLocation.equalsIgnoreCase(targetLoc)) {
                curr.next = curr.next.next;
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    private boolean hasConnection(LocationVertex v, String targetLoc) {
        EdgeNode curr = v.headEdge;
        while (curr != null) {
            if (curr.targetLocation.equalsIgnoreCase(targetLoc)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    /**
     * Displays all campus locations and their connected neighbours (Adjacency List View).
     */
    public void displayConnections() {
        if (headVertex == null) {
            System.out.println("-> Campus Graph is empty. No locations added.");
            return;
        }

        System.out.println("\n=========================================================================================");
        System.out.println("                   CAMPUS LOCATIONS & ROUTE NETWORK (ADJACENCY LIST)                     ");
        System.out.println("=========================================================================================");

        LocationVertex current = headVertex;
        int count = 1;
        while (current != null) {
            System.out.printf("[%2d] Location: %-26s -> Connections: ", count++, current.locationName);
            EdgeNode edge = current.headEdge;
            if (edge == null) {
                System.out.println("[ Isolated - No direct roads ]");
            } else {
                StringBuilder sb = new StringBuilder();
                while (edge != null) {
                    sb.append(String.format("%s (%.0fm)", edge.targetLocation, edge.distanceMeters));
                    if (edge.next != null) sb.append(" | ");
                    edge = edge.next;
                }
                System.out.println(sb.toString());
            }
            current = current.nextVertex;
        }
        System.out.println("=========================================================================================");
        System.out.println("Total Campus Locations: " + vertexCount);
    }

    /**
     * Breadth-First Search (BFS) Traversal starting from a specific campus location.
     */
    public void bfsTraversal(String startLocation) {
        LocationVertex startV = findVertex(startLocation);
        if (startV == null) {
            System.out.println("-> Error: Starting location '" + startLocation + "' does not exist in campus graph.");
            return;
        }

        System.out.println("\n=========================================================================================");
        System.out.println("           BREADTH-FIRST SEARCH (BFS) TRAVERSAL FROM: " + startV.locationName.toUpperCase());
        System.out.println("=========================================================================================");

        String[] visited = new String[vertexCount];
        int visitedCount = 0;

        String[] queue = new String[vertexCount * 2];
        int qFront = 0, qRear = 0;

        // Enqueue start location
        queue[qRear++] = startV.locationName;
        visited[visitedCount++] = startV.locationName;

        System.out.print("Traversal Path (Level by Level): ");
        boolean first = true;

        while (qFront < qRear) {
            String currName = queue[qFront++];
            if (!first) System.out.print(" -> ");
            System.out.print("[" + currName + "]");
            first = false;

            LocationVertex currV = findVertex(currName);
            if (currV != null) {
                EdgeNode edge = currV.headEdge;
                while (edge != null) {
                    if (!isVisited(visited, visitedCount, edge.targetLocation)) {
                        visited[visitedCount++] = edge.targetLocation;
                        queue[qRear++] = edge.targetLocation;
                    }
                    edge = edge.next;
                }
            }
        }
        System.out.println("\n=========================================================================================");
        System.out.println("Visited Locations Count: " + visitedCount + " of " + vertexCount);
    }

    /**
     * Depth-First Search (DFS) Traversal starting from a specific campus location.
     */
    public void dfsTraversal(String startLocation) {
        LocationVertex startV = findVertex(startLocation);
        if (startV == null) {
            System.out.println("-> Error: Starting location '" + startLocation + "' does not exist in campus graph.");
            return;
        }

        System.out.println("\n=========================================================================================");
        System.out.println("             DEPTH-FIRST SEARCH (DFS) TRAVERSAL FROM: " + startV.locationName.toUpperCase());
        System.out.println("=========================================================================================");

        String[] visited = new String[vertexCount];
        int[] visitedCount = new int[]{0};

        System.out.print("Traversal Path (Depth First): ");
        dfsRecursive(startV.locationName, visited, visitedCount, true);
        System.out.println("\n=========================================================================================");
        System.out.println("Visited Locations Count: " + visitedCount[0] + " of " + vertexCount);
    }

    private void dfsRecursive(String locationName, String[] visited, int[] visitedCount, boolean isFirst) {
        visited[visitedCount[0]++] = locationName;

        if (!isFirst) System.out.print(" -> ");
        System.out.print("[" + locationName + "]");

        LocationVertex v = findVertex(locationName);
        if (v != null) {
            EdgeNode edge = v.headEdge;
            while (edge != null) {
                if (!isVisited(visited, visitedCount[0], edge.targetLocation)) {
                    dfsRecursive(edge.targetLocation, visited, visitedCount, false);
                }
                edge = edge.next;
            }
        }
    }

    private boolean isVisited(String[] visited, int count, String name) {
        for (int i = 0; i < count; i++) {
            if (visited[i].equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsLocation(String locationName) {
        return findVertex(locationName) != null;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
