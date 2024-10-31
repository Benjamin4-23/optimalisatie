public class Edge {
    int id;
    EdgeType edgeType;
    int cost;
    int endpoint1;
    int endpoint2;

    public enum EdgeType {
        REGULAR, OFFSTREET // Add other types if needed
    }

    // Getters and setters can be added here if needed
} 