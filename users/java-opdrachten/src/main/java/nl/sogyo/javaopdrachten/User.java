package nl.sogyo.javaopdrachten;

public class User {
    String userName;
    String password;

    public User() {
        this("","");
    }
    public User(String user, String pass) {
        userName = user;
        password = pass;
    }
}
