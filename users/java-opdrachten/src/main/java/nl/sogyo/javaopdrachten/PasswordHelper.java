package nl.sogyo.javaopdrachten;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordHelper {
    static Pattern pattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])");
    //Part 4 and 5: Throw exception
    public static Boolean IsValidPassword(String password) throws InvalidPasswordException {
        Matcher matcher = pattern.matcher(password);
        if(matcher.find()) return true;
        else throw new InvalidPasswordException("Invalid input has been entered.");
    }
    //Part 1: Static function that validates the password
    //This method returns true if the password matches the regular expression
    public static Boolean IsValidPasswordNoException(String password){
        Matcher matcher = pattern.matcher(password);
        if(matcher.find()) return true;
        else return false;
    }
}
