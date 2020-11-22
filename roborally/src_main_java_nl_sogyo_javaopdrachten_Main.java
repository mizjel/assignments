package nl.sogyo.javaopdrachten;

public class Main {
    public static void main(String... args){
        Robot my_first_robot = new Robot(1, 0, "West");
        my_first_robot.turnLeft();
        my_first_robot.forward(2);
        my_first_robot.backward();
        my_first_robot.printState();
        my_first_robot.execute();

        System.console().readLine();
    }
}
