package nl.sogyo.mancala.domain;

public class InvalidMoveException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    public InvalidMoveException(String errorMessage) {
        super(errorMessage);
    }
}
