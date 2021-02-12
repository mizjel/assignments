package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_METHOD)
public class PlayerTest {
    Bowl bowls;
    Player playerOne;
    Player playerTwo;

    @BeforeEach
    public void setup(){
        TestSetup.setup();
        bowls = TestSetup.bowls;
        playerOne = TestSetup.playerOne;
        playerTwo = TestSetup.playerTwo;
    }
    //Test to check if player one has player two as opponent and vice versa
    @Test
    public void players_have_each_other_set_as_opponents(){
        assertEquals(playerTwo, playerOne.getOpponent());
        assertEquals(playerOne, playerTwo.getOpponent());
    }
    //Test to check if switching player works
    @Test
    public void player_switch_after_valid_move(){
        Bowl playerOneFirstBowl = bowls.getBowlForOwner(0, playerOne);

        playerOne.makeMove(playerOneFirstBowl);

        assertEquals(playerTwo, playerOne.getPlayerTurn());
    }
    //Test to check if player keeps move after trying to start an invalid move by starting the move at the kalaha
    @Test
    public void player_does_not_switch_after_invalid_move(){
        Kalaha kalahaPlayerOne = bowls.getOwnerKalaha(playerOne);

        playerOne.makeMove(kalahaPlayerOne);

        assertEquals(playerOne, playerOne.getPlayerTurn());
    }
}
