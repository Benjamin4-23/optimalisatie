package src;

public class Edge {
    public int id;
    public EdgeType edgeType;
    public int cost;
    public int endpoint1;
    public int endpoint2;

    public enum EdgeType {
        REGULAR, OFFSTREET, EXISTING
    }

    public Edge(int id, EdgeType edgeType, int cost, int endpoint1, int endpoint2) {
        this.id = id;
        this.edgeType = edgeType;
        this.cost = cost;
        this.endpoint1 = endpoint1;
        this.endpoint2 = endpoint2;
    }
    // Getters and setters can be added here if needed
} 