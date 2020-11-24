package nl.sogyo.javaopdrachten;

import java.util.ArrayList;

public class UserManager {
    static ArrayList<User> users = new ArrayList<User>();

    //Parts 4 and 5: Throw exception
    public static void RegisterUser(String username, String password) throws InvalidPasswordException {
        if(PasswordHelper.IsValidPassword(password)){
            users.add(new User(username, password));
            System.out.println("Registered user: " + username);
        }
    }
    //Part 6: User friendly check without exceptions
    //This implementation uses methods that return boolean values indicating whether the password was validated or not and adds the user
    public static Boolean IsUserRegistered(String username, String password){
        Boolean isValid = PasswordHelper.IsValidPasswordNoException(password);
        if(isValid){
            users.add(new User(username, password));
        }
        return isValid;
    }
}
