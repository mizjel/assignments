package nl.sogyo.mancala.api.models;

import nl.sogyo.mancala.domain.Mancala;

public class GameStatusDto {
    boolean endOfGame;
    public boolean getEndOfGame() { return endOfGame; }
    
    String winner;
    public String getWinner() { return winner; }

    public GameStatusDto(Mancala mancala) {
        endOfGame = mancala.isEndOfGame();
        winner = endOfGame ? mancala.getWinnersName() : null;
    }
}