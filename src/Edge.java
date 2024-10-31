package src;

public class Edge {
    int id;
    EdgeType edgeType;
    int cost;
    int endpoint1;
    int endpoint2;

    public enum EdgeType {
        REGULAR, OFFSTREET, EXISTING
    }

    // Getters and setters can be added here if needed
} 