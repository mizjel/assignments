package nl.sogyo.javaopdrachten;

public class Edge {
    Node Origin;
    Node Destination;
    String Answer;
    public Edge() {
        this(null, null, "");
    }
    public Edge(Node origin, Node destination, String answer) {
        Origin = origin;
        Destination = destination;
        Answer = answer;
    }
}
