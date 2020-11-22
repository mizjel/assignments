package nl.sogyo.javaopdrachten;

import java.util.ArrayList;
import java.util.List;

public class Robot {
    int xCoordinate;
    int yCoordinate;
    String facing;
    Boolean newFacingPrinted = true;
    Boolean newMovementPrinted = true;

    List<Runnable> listOfMethods = new ArrayList<>();

    public Robot() {
        xCoordinate = 0;
        yCoordinate = 0;
        facing = "North";
    }
    public Robot(int x, int y, String face) {
        xCoordinate = x;
        yCoordinate = y;
        facing = face;
    }
    public void execute(){
        for(Runnable method : listOfMethods){
            method.run();
        }
    }
    public void goForward(){
        newMovementPrinted = false;
        switch(facing){
            case "North":
                yCoordinate++;
                break;
            case "East":
                xCoordinate++;
                break;
            case "South":
                yCoordinate--;
                break;
            case "West":
                xCoordinate--;
                break;
        }
    }
    public void goForward(int speed){
        if(speed < 1 || speed > 3){
            System.out.println("Unknown speed, reverting to default");
            forward();
            return;
        }

        newMovementPrinted = false;
        switch(facing){
            case "North":
                yCoordinate = yCoordinate + speed;
                break;
            case "East":
                xCoordinate = xCoordinate + speed;
                break;
            case "South":
                yCoordinate = yCoordinate - speed;
                break;
            case "West":
                xCoordinate = xCoordinate - speed;
                break;
        }
    }
    public void goBackward(){
        newMovementPrinted = false;
        switch(facing){
            case "North":
                yCoordinate--;
                break;
            case "East":
                xCoordinate--;
                break;
            case "South":
                yCoordinate++;
                break;
            case "West":
                xCoordinate++;
                break;
        }
    }
    public void doLeftTurn(){
        newFacingPrinted = false;
        switch(facing){
            case "North":
                facing = "West";
                break;
            case "East":
                facing = "North";
                break;
            case "South":
                facing = "East";
                break;
            case "West":
                facing = "South";
                break;
        }
    }
    public void doRightTurn(){
        newFacingPrinted = false;
        switch(facing){
            case "North":
                facing = "East";
                break;
            case "East":
                facing = "South";
                break;
            case "South":
                facing = "West";
                break;
            case "West":
                facing = "North";
                break;
        }
    }
    public void doPrintState(){
        if(newFacingPrinted && newMovementPrinted){
            System.out.println("Still facing " + "\"" + facing + "\"" + " at (" + xCoordinate + "," + yCoordinate + ")");
        }
        else{
            System.out.println("Now facing " + "\"" + facing + "\"" + " at (" + xCoordinate + "," + yCoordinate + ")");
            newFacingPrinted = true;
        }
    }
    public void forward(){
        //Call goForward without parameters
        Runnable method = new Runnable(){
            public void run(){
                goForward();
            }
        };
        listOfMethods.add(method);
    }
    public void forward(int speed){
        //Call goForward with speed parameter
        Runnable method = new Runnable(){
            public void run(){
                goForward(speed);
            }
        };
        listOfMethods.add(method);
    }
    public void backward(){
        //Call goBackward
        Runnable method = new Runnable(){
            public void run(){
                goBackward();
            }
        };
        listOfMethods.add(method);
    }
    public void turnLeft(){
        //Call doLeftTurn
        Runnable method = new Runnable(){
            public void run(){
                doLeftTurn();
            }
        };
        listOfMethods.add(method);
    }
    public void turnRight(){
        //Call doRightTurn
        Runnable method = new Runnable(){
            public void run(){
                doRightTurn();
            }
        };
        listOfMethods.add(method);
    }
    public void printState(){
        //Call doPrintState
        Runnable method = new Runnable(){
            public void run(){
                doPrintState();
            }
        };
        listOfMethods.add(method);
    }
}
