package nl.sogyo.mancala.domain;

public abstract class Bowl implements SwitchPlayerEventListener  {
    private int stones;
    private int index;

    private Player owner;
    private Bowl nextBowl;

    public Bowl() {
        this(new Player());
    }
    public Bowl(Player player) {
        this(player, 0);
        this.createBowls(6);
    }
    public Bowl(Player player, int i) {
        this(player, i, 4);
    }
    public Bowl(Player player, int i, int amountOfStones) {
        this.owner = player;
        this.index = i;
        this.stones = amountOfStones;
    }
    public Bowl(Player playerOne, Player playerTwo) {
        this(playerOne, 0);
        playerOne.setSwitchPlayerEventListener(this);
        playerTwo.setSwitchPlayerEventListener(this);
        this.createBowls(playerOne, playerTwo, 6);
    }

    public abstract Kalaha getOwnerKalaha(Player player);
    public abstract Boolean pitsAreEmptyForOwner(Player player, Bowl startingBowl);
    public abstract Boolean isKalaha();
    protected abstract Object divideStones(int amount, Player player);
    public abstract Object divideStones(Player player) throws InvalidMoveException;

    private void createBowls(Player playerOne, Player playerTwo, int amount) {
        this.createBowls(0, amount, playerOne);
        this.getOwnerKalaha(playerOne).setNextBowl(new Pit(playerTwo));
        this.getOwnerKalaha(playerTwo).setNextBowl(this);
    }
    private void createBowls(int amount) {
        this.createBowls(0, amount, this.getOwner());
    }
    private Object createBowls(int i, int amount, Player player) {
        int newIndex = i + 1;
        
        //Create a pit as next and recurse into that pit
        if(i != (amount - 1)){
            this.setNextBowl(new Pit(player, newIndex));
            return this.getNextBowl().createBowls(newIndex, amount, player);
        }
        //Create a kalaha and escape the function
        else {
            this.setNextBowl(new Kalaha(player, newIndex));
            return this.getNextBowl();
        }
    }
    public int getIndex(){
        return this.index;
    }
    public int getStones(){
        return this.stones;
    }
    public void setStones(int amount){
        this.stones = amount;
    }
    public void addStones(int amount){
        this.stones += amount;
    }
    public void removeStones(int amount){
        this.stones -= amount;
    }
    public Boolean pitsAreEmptyForOwner(Player player) {
        return this.getNextBowl().pitsAreEmptyForOwner(player, this);
    }
    public Bowl getOppositeBowl(){
        //Get opposite of THIS bowl
        return this.getBowlForOwner(5 - this.getIndex(), getOwner().getOpponent());
    }
    public Bowl getBowlForOwner(int i, Player owns){
        if(this.getIndex() == i && owns.isOwner(this)) return this;
        else return this.getNextBowl().getBowlForOwner(i, owns);
    }
    public Integer getAmountOfStonesFromPlayerBowls(Player player) {
		return this.getNextBowl().getAmountOfStonesFromPlayerBowls(player, 0, this);
    }
    public Integer getAmountOfStonesFromPlayerBowls(Player player, int amount, Bowl startingBowl){
        if(this.getOwner().equals(player)){
            amount = amount + this.getStones();
        }
        if(this.equals(startingBowl)) return amount;
        return this.getNextBowl().getAmountOfStonesFromPlayerBowls(player, amount, startingBowl);
    }
    public Bowl getNextBowl(){
        return this.nextBowl;
    }
    public void setNextBowl(Bowl bowl){
        this.nextBowl = bowl;
    }
    public Player getOwner(){
        return this.owner;
    }
    @Override
    public void onSwitchPlayer(Player player) {
        //This event will fire when a player has been switched with the turn player as parameter
        //Check if all pits for this player are empty
        //Change gamestate if they are
        if(this.pitsAreEmptyForOwner(player)) {
            Player.setGameStateForPlayers(player, player.getOpponent(), GameState.GAMEOVER);
            //Get all beads from both players
            int stonesPlayer = this.getAmountOfStonesFromPlayerBowls(player);
            int stonesOpponent = this.getAmountOfStonesFromPlayerBowls(player.getOpponent());
            //The player with most beads wins
            if(stonesPlayer > stonesOpponent) Player.setWinner(player, player.getOpponent(), player);
            else Player.setWinner(player, player.getOpponent(), player.getOpponent());
        }
        
    }
	
}
