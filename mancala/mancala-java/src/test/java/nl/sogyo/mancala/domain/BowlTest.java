package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_METHOD)
public class BowlTest {
    Bowl bowls;
    Player playerOne;
    Player playerTwo;

    @BeforeEach
    public void setup() {
        TestSetup.setup();
        playerOne = TestSetup.playerOne;
        playerTwo = TestSetup.playerTwo;
        bowls = TestSetup.bowls;
    }

    // Pit starts with four stones
    @Test
    public void pit_starts_with_four_stones() {
        Bowl bowl = new Pit();
        int amount = bowl.getStones();

        assertEquals(4, amount);
    }
    // Pit has an owner set
    @Test
    public void pit_has_owner(){
        Bowl bowl = new Pit();

        assertNotEquals(null, bowl.getOwner());
    }
    @Test
    public void pit_is_not_kalaha(){
        Bowl bowl = new Pit();

        assertEquals(false, bowl.isKalaha());
    }
    @Test
    public void kalaha_is_kalaha(){
        Kalaha kalaha = new Kalaha();

        assertEquals(true, kalaha.isKalaha());
    }
    // Kalaha starts with 0 stones
    @Test
    public void kalaha_starts_with_zero_stones() {
        Kalaha kalaha = new Kalaha();
        int amount = kalaha.getStones();

        assertEquals(0, amount);
    }

    // Bowl is connected - do a move that moves through the player kalaha to the
    // opponent pit
    @Test
    public void bowls_are_connected_from_player_one_kalaha() throws InvalidMoveException {
        // Get last bowl for player one before kalaha
        Bowl startingBowl = bowls.getBowlForOwner(5, playerOne);
        // Do a move
        Bowl lastBowl = (Bowl) startingBowl.divideStones(playerOne);
        // Check if the owner of the last used pit is not player one
        assertEquals(playerTwo, lastBowl.getOwner());
    }
    @Test
    public void bowls_are_connected_from_player_two_kalaha() throws InvalidMoveException {
        // Get last bowl for player two before kalaha
        Bowl startingBowl = bowls.getBowlForOwner(5, playerTwo);
        // Do a move
        Bowl lastBowl = (Bowl) startingBowl.divideStones(playerTwo);
        // Check if the owner of the last used pit is not player two
        assertEquals(playerOne, lastBowl.getOwner());
    }
    //Player can distribute to own pit - Do a move, check first(0 stones), last(5 stones) and last + 1 pit(4 stones)
    @Test
    public void player_can_distribute_to_own_pit() throws InvalidMoveException {
        //Get first bowl for player one
        Bowl startingBowl = bowls.getBowlForOwner(0, playerOne);
        //Divide stones beginning at first bowl
        Bowl lastBowl = (Bowl) startingBowl.divideStones(playerOne);
        //Check if first Pit has 0 stones
        assertEquals(0, startingBowl.getStones());
        //Last Pit has 5 stones
        assertEquals(5, lastBowl.getStones());
        //Last Pit.next has 4 stones
        assertEquals(4, lastBowl.getNextBowl().getStones());
    }
    //Player can distribute to opponent pit starting from own pit - Comparable logic to player distribution to own pit
    @Test
    public void player_can_distribute_to_opponent_pit() throws InvalidMoveException {
        //Get last bowl before kalaha for player one
        Bowl startingBowl = bowls.getBowlForOwner(5, playerOne);
        //Divide stones beginning at the starting bowl
        Bowl lastBowl = (Bowl)startingBowl.divideStones(playerOne);
        //Check if first Pit has 0 stones
        assertEquals(0, startingBowl.getStones());
        //Last pit has 5 stones
        assertEquals(5, lastBowl.getStones());
        //Last pit.next has 4 stones
        assertEquals(4, lastBowl.getNextBowl().getStones());
    }
    //Player cannot start move in opponent pit
    @Test
    public void player_cannot_start_in_opponent_pit(){
        Bowl opponentBowl = bowls.getBowlForOwner(0, playerTwo);

        assertThrows(InvalidMoveException.class, () -> { opponentBowl.divideStones(playerOne); });
    }
    //Player cannot start move in kalaha
    @Test
    public void player_cannot_start_in_kalaha(){
        Kalaha playerKalaha = bowls.getOwnerKalaha(playerOne);

        assertThrows(InvalidMoveException.class, () -> { playerKalaha.divideStones(playerOne); } );
    }
    //Test to check if the bowls are not empty at the start
    @Test
    public void pits_not_empty_at_start(){
        assertEquals(false, bowls.pitsAreEmptyForOwner(playerOne));
    }
}
