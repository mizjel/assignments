package nl.sogyo.mancala.domain;

public class TestSetup {
    public static Player playerOne;
    public static Player playerTwo;

    public static Bowl bowls;

    public static void setup(){
        playerOne = new Player("Player one");
        playerTwo = new Player("Player two");

        Player.setOpponents(playerOne, playerTwo);

        Player.setTurnForPlayers(playerOne, playerTwo);

        bowls = new Pit(playerOne, playerTwo);
    }
}
