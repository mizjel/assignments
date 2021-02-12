package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
@TestInstance(Lifecycle.PER_METHOD)
public class ScenarioTest {
    //Test the scenario's here
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
    //Test to check if player can put a stone in own kalaha(must have access)
    @Test
    public void player_puts_stone_in_own_kalaha(){
        Bowl secondLastBowl = bowls.getBowlForOwner(5, playerOne);

        playerOne.makeMove(secondLastBowl);

        Kalaha playerOneKalaha = secondLastBowl.getOwnerKalaha(playerOne);
        assertEquals(1, playerOneKalaha.getStones());
    }
    //Test to check if player can put a stone in opponent kalaha(must not have access)
    @Test
    public void player_passes_opponent_kalaha(){
        Bowl secondLastBowl = bowls.getBowlForOwner(5, playerOne);

        //Let's cheat a bit here and set it's amount to 8 stones
        secondLastBowl.setStones(8);

        playerOne.makeMove(secondLastBowl);

        Kalaha secondPlayerKalaha = bowls.getOwnerKalaha(playerTwo);

        assertEquals(0, secondPlayerKalaha.getStones());
    }
    @Test
    public void player_gets_another_move_by_ending_in_own_kalaha(){
        Bowl bowl = bowls.getBowlForOwner(2, playerOne);

        Bowl lastBowl = (Bowl)playerOne.makeMove(bowl);

        assertEquals(playerOne, playerOne.getPlayerTurn());
    }
    @Test
    public void player_takes_opposite_by_landing_in_own_empty_pit(){
        bowls.getBowlForOwner(4, playerOne).setStones(0);
        Bowl takeOpposite = bowls.getBowlForOwner(0, playerOne);

        Bowl endingBowl = bowls.getBowlForOwner(4, playerOne);

        Bowl oppositeBowl = endingBowl.getOppositeBowl();
        oppositeBowl.setStones(8);

        playerOne.makeMove(takeOpposite);

        Kalaha playerOneKalaha = bowls.getOwnerKalaha(playerOne);

        assertEquals(9, playerOneKalaha.getStones());
    }
    
    //Test to check if the game state changes to GAMEOVER if player gets turn and all pits for the player are empty
    @Test
    public void game_over_when_turn_player_pits_are_empty(){
        //Set all player two pit stones to 0
        for(int i = 0; i < 6; i++){
            bowls.getBowlForOwner(i, playerTwo).setStones(0);
        }
        //Switch player turn
        playerOne.switchPlayer();
        //Check if gamestate has changed
        assertEquals(GameState.GAMEOVER, playerOne.getGameState());
    }
    //Test to check if the winner is the player with the most beads on their territory including the kalaha
    @Test
    public void winner_is_player_with_most_beads(){
        //Set all player two pit stones to 0
        for(int i = 0; i < 6; i++){
            bowls.getBowlForOwner(i, playerTwo).setStones(0);
        }
        //Switch player turn
        playerOne.switchPlayer();

        //Check if winner is player with most beads including kalaha
        assertEquals(24, bowls.getAmountOfStonesFromPlayerBowls(playerOne.getWinner()));
    }
}
