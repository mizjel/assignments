package nl.sogyo.javaopdrachten;

public class InvalidPasswordException extends Exception {

    private static final long serialVersionUID = 1L;
    public InvalidPasswordException(String errorMessage) {
        super(errorMessage);
    }
}
