package nl.sogyo.javaopdrachten;

public class Main {
    public static void main(String... args) {
        //Uncomment the line below and other lines in the method itself to test everything up until Part 6
        //ExecuteOldRegistration();
        
        //Part 6: User friendly check without exceptions
        //This implementation propagates a boolean through the program indicating whether the password was validated or not
        
        //Keep it in a loop so more users can be registered
        while(true){
            ExecuteRegistration();
        }
    }
    
    static void ExecuteRegistration(){
        Boolean isRegistered = false;
        //Noticed that the assignment on this part does not show "Enter a password" on the console when retrying
        //So this boolean will supress printing the mentioned line when retrying
        Boolean retry = false;

        System.out.println("Enter a username:");
        String username = System.console().readLine();

        //Keep the user in a loop until the registration has been completed
        while(!isRegistered){
            if(!retry) System.out.println("Enter a password:");
            String password = System.console().readLine();

            isRegistered = UserManager.IsUserRegistered(username, password);
            
            if(!isRegistered){
                System.out.println("This is not a valid password. Please enter a stronger password.");
                retry = true;
            } 
            
        }
        System.out.println("Registered user: " + username);
    }
    static void ExecuteOldRegistration(){
        String username = "";
        String password = "";

        System.out.println("Enter a username:");
        username = System.console().readLine();
        System.out.println("Enter a password:");
        password = System.console().readLine();

        //Uncomment lower parts based on which part is going to be tested
        //Part 4: Throw error, propagate through program and show error message and stacktrace
        try {
            UserManager.RegisterUser(username, password);
        } catch (InvalidPasswordException e) {
            e.printStackTrace();
        }

        //Part 5: Use catch clause to write a generic error to the console
        // try {
        //     UserManager.RegisterUser(username, password);
        // } catch (InvalidPasswordException e) {
        //     System.out.println("Something has gone wrong.");
        // }
        System.console().readLine();
    }
}
