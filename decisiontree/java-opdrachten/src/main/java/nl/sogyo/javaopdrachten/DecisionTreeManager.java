package nl.sogyo.javaopdrachten;

import java.util.ArrayList;

public class DecisionTreeManager {
    ArrayList<Node> Nodes;
    ArrayList<Edge> Edges;
    Node StartNode;

    TextHelper Helper = new TextHelper();

    public DecisionTreeManager() {
        this(new ArrayList<Node>(), new ArrayList<Edge>());
    }
    public DecisionTreeManager(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        Nodes = nodes;
        Edges = edges;
        InitializeDecisionTree();
    }
    private void InitializeDecisionTree(){
        SetNodesAndEdges();
        SetStartNode();
    }
    public void TraverseTree(){
        Node currentNode = StartNode;

        while(!IsEndNode(currentNode)){
            System.out.println(currentNode.Question);
            System.out.println("J voor ja, N voor nee");
            char input = System.console().readLine().charAt(0);
            currentNode = GetNextNode(input, currentNode);
        }
        System.out.println("Answer is: " + currentNode.Question);
    }
    private Node GetNextNode(char input, Node originNode) {
        String answer = "";

        if(Character.toLowerCase(input) == 'j') answer = "Ja";
        else if (Character.toLowerCase(input) == 'n') answer = "Nee";

        //Loop through edges
        //If edge has answer same as input and the originNode as origin return node
        for(Edge edge : Edges){
            if(edge.Answer.equals(answer) && edge.Origin.equals(originNode)) return edge.Destination;
        }
        return new Node();
    }

    private Boolean IsEndNode(Node node) {
        //An answer/end node is a node where no edges originate from
        for(Edge edge : Edges){
            //Edge has node as origin so it's not an end node
            if(edge.Origin.Name.equals(node.Name)) return false;
        }
        return true;
    }
    
    private void SetStartNode() {
        StartNode = null;
        for(Node node : Nodes){
            Boolean isStartingNode = true;;
            for(Edge edge : Edges){
                if(node.Name.equals(edge.Destination.Name)){
                    isStartingNode = false;
                }
            }
            if(isStartingNode){
                StartNode = node;
                break;
            }
        }
    }
    private void SetNodesAndEdges(){
        ArrayList<String[]> nodesString = Helper.GetNodesStringList();
        for(String[] content : nodesString){
            String question = content[1].substring(0,1).replaceAll("\\s+","") + content[1].substring(1);
            String name = content[0].replaceAll("\\s+","");
            AddNode(new Node(name, question));
        }

        ArrayList<String[]> edgesString = Helper.GetEdgesStringList();
        for(String[] content : edgesString){
            Node originNode = GetNodeReference(content[0]);
            Node destinationNode = GetNodeReference(content[1]);
            String answer = content[2];
            AddEdge(new Edge(originNode, destinationNode, answer));
        }
    }
    private Node GetNodeReference(String name) {
        //One line streams solution!
        //Returns the node that has the same name as the name parameter
        return Nodes.stream().filter(n -> n.Name.equals(name)).findFirst().get();
    }
    //This method can be used to validate/verify input before adding it to the list
    private void AddEdge(Edge edge){
        Edges.add(edge);
    }
    private void AddNode(Node node){
        Nodes.add(node);
    }
}
