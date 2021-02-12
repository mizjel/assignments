package nl.sogyo.mancala.domain;

public class Kalaha extends Bowl {
    public Kalaha() {
        this(new Player(), 0);
    }
    public Kalaha(Player player) {
        this(player, 0);
    }
    public Kalaha(Player player, int i) {
        super(player, i, 0);
    }
    @Override
    public Boolean isKalaha(){
        return true;
    }
    @Override
    public Object divideStones(Player player) throws InvalidMoveException {
        //Throw error, player not allowed to start in kalaha
        throw new InvalidMoveException("Not allowed to start in a Kalaha");
    }
    @Override
    protected Object divideStones(int amount, Player player){
        //When distributing, players include their own kalaha but not the opponent's
        //Continue with the next bowl if the kalaha does not belong to the player
        if(!player.equals(getOwner())) return this.getNextBowl().divideStones(amount, player);

        //Distribute one stone in the kalaha if calling player is the owner
        this.addStones(1);
        //If amount is 1 return the method, this means that the move has run out of stones
        //Returns the last bowl that was included in the move
        if(amount == 1) return this;

        return this.getNextBowl().divideStones(amount -1, player);
    }
    @Override
    public Kalaha getOwnerKalaha(Player player) {
        if(this.getOwner().equals(player)) return this;
        else return this.getNextBowl().getOwnerKalaha(player);
    }
    @Override
    public Boolean pitsAreEmptyForOwner(Player player, Bowl startingBowl) {
        //Kalaha implementation only calls own method on the next bowl if the startingBowl is not the same
        if(this.equals(startingBowl)) return true;
        else return this.getNextBowl().pitsAreEmptyForOwner(player, startingBowl);
    }
}
