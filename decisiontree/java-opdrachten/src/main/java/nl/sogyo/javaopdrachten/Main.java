package nl.sogyo.javaopdrachten;

public class Main {
    public static void main(String... args) {
        DecisionTreeManager manager = new DecisionTreeManager();
        manager.TraverseTree();

        System.out.println("Press enter to exit.");
        System.console().readLine();
    }
}
