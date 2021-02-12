package nl.sogyo.mancala.domain;


public class Main {
    //Used this class for debugging..
    public static void main(String... args){
        Player playerOne = new Player("Player one");

        //Bowl playerOneBowl = new Pit(playerOne);
        //Bowl playerOneBowl = BowlFactory.create(playerOne, 6);
        // playerOneBowl.createBowls();

        // Kalaha playerOneKalaha = playerOneBowl.getOwnerKalaha(playerOne);
        // playerOne.makeMove(playerOneKalaha);
        Player playerTwo = new Player("Player two");

        Player.setOpponents(playerOne, playerTwo);
        Player.setTurnForPlayers(playerOne, playerTwo);
        //Bowl bowl = new Pit(playerOne);
        // Bowl bowls = BowlFactory.create(playerOne, playerTwo, 6);
        Bowl connectedBowls = new Pit(playerOne, playerTwo);

        //playerOne.switchPlayer();
        playerTwo.switchPlayer();
        // Kalaha playerOneKalaha = bowls.getOwnerKalaha(playerOne);
        // playerOne.makeMove(playerOneKalaha);

        // Bowl playerTwoFirstPit = bowls.getBowlForOwner(0, playerTwo);
        // playerOne.makeMove(playerTwoFirstPit);

        // Bowl playerTwoBowl = new Pit(playerTwo);
        // playerTwoBowl.createBowls();

        // Player.setOpponents(playerOne, playerTwo);
        // Player.setTurnForPlayers(playerOne, playerTwo);

        // Bowl allBowls = Bowl.ToConnectedBowl(playerOneBowl, playerTwoBowl);
        // allBowls.getBowlForOwner(4, playerOne).setStones(0);
        // Bowl takeOpposite = allBowls.getBowlForOwner(0, playerOne);
        // Bowl endingBowl = allBowls.getBowlForOwner(4, playerOne);
        // Bowl oppositeBowl = endingBowl.getOppositeBowl();
        // oppositeBowl.setStones(8);

        // playerOne.makeMove(takeOpposite);

        // Kalaha playerOneKalaha = allBowls.getOwnerKalaha(playerOne);
        System.out.println("mancala-java is running, you just can't do anything with it at the moment");
        System.console().readLine();
    }
}
