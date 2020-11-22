package nl.sogyo.javaopdrachten;

public class Node {
    String Name;
    String Question;
    public Node() {
        this("None", "None");
    }
    public Node(String name, String question) {
        Name = name;
        Question = question;
    }
}
