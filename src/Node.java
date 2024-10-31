package src;

public class Node {
    public int id;
    public double x;
    public double y;
    public NodeType nodeType;

    public enum NodeType {
        REGULAR, PROSPECT
    }

    public Node(int id, double x, double y, NodeType nodeType) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.nodeType = nodeType;
    }

    // Getters and setters can be added here if needed
} 