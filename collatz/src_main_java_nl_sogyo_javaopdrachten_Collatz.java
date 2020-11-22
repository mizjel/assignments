package nl.sogyo.javaopdrachten;

public class Collatz {
    static int currentNumber;
    public static void main(String... args){
        System.out.print("Enter a number: ");
        currentNumber = Integer.parseInt(System.console().readLine());

        System.out.println("Starting Collatz sequence!");

        boolean solved = false;
        //Keep looping until solved variable has been set to true
        while(!solved){
            System.out.println(currentNumber);
            //Keep executing if the number isn't 1
            if(currentNumber != 1) ExecuteSequence();
            //Break through the loop if number is 1
            else solved = true;
        }
        System.out.println("Sequence ended");
        
        System.out.println("Press a key to exit");
        System.console().readLine();
    }
    //Checks the number and does an operation based on the number being odd or even
    private static void ExecuteSequence() {
        if(currentNumber % 2 == 0) currentNumber = currentNumber / 2;
        else currentNumber = (currentNumber * 3) + 1;
    }
}
