package nl.sogyo.mancala.domain;

public class Pit extends Bowl{
    public Pit() {
        super();
    }
    public Pit(Player player) {
        super(player);
    }
    public Pit(Player player, int i) {
        super(player, i, 4);
    }
    public Pit(Player playerOne, Player playerTwo) {
        super(playerOne, playerTwo);
    }
    @Override
    public Object divideStones(Player player) throws InvalidMoveException {
        //If a player does not own this bowl return an exception(not allowed to start in opponent bowl)
        if(!player.isOwner(this)) throw new InvalidMoveException("Cannot start in opponent pit");
        // The game ends if all bowls of the turn player are empty

        //First bowl, get all the stones from this bowl
        int amount = this.getStones();
        //Set the amount of stones to 0 for this bowl
        this.removeStones(amount);
        //and call the overloaded method with the amount, returns the last bowl used
        //and switches the player turn if end turn conditions have been reached
        return this.getNextBowl().divideStones(amount, player);
    }
    @Override
    protected Object divideStones(int amount, Player player) {
        //Will be called for all bowls after the first bowl
        //add one stone to this bowl and call own method again with amount -1
        
        this.addStones(1);
        //If amount is 1 return the method, this means that the move has run out of stones
        //Returns the last bowl that was included in the move
        if(amount == 1) {
            //If the last bead ends in an empty pit on the own side
            if(player.isOwner(this) && this.getStones() == 1){
                //the player can take the bead(1) from that pit and the direct opposite's beads
                //and put them in their kalaha
                this.getOwnerKalaha(player).addStones(this.getStones() + this.getOppositeBowl().getStones());
                this.removeStones(this.getStones());
                this.getOppositeBowl().removeStones(this.getOppositeBowl().getStones());
            }
            player.switchPlayer();
            return this;
        }
        return this.getNextBowl().divideStones(amount -1, player);
    }
    
    @Override
    public Boolean isKalaha() {
        return false;
    }
    @Override
    public Kalaha getOwnerKalaha(Player player) {
        return this.getNextBowl().getOwnerKalaha(player);
    }
    @Override
    public Boolean pitsAreEmptyForOwner(Player player, Bowl startingBowl) {
        //Check if pit is same as starting pit
        //Check if pit has player as owner
        //Check if pit has 0 stones, if not all pits are not empty so return false
        if(this.getOwner().equals(player) && this.getStones() != 0) 
            return false;
        else if(this.equals(startingBowl)) 
            return true;
        else return this.getNextBowl().pitsAreEmptyForOwner(player, startingBowl);
    }
    
}
