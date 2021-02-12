package nl.sogyo.mancala.domain;
enum GameState { PLAYING, GAMEOVER };
public class Player {
    private String name;
    private Player opponent;
    private Player playerTurn;
    private Player winner;
    private GameState gameState;
    private SwitchPlayerEventListener listener;

    public Player() {
        this("Undefined");
    }

    public Player(String n) {
        this(n, null);
    }

    public Player(String n, Player o) {
        this(n, o, null);
    }

    public Player(String n, Player o, Player turn) {
        this.name = n;
        this.opponent = o;
        this.playerTurn = turn;
        this.gameState = GameState.PLAYING;
    }

    public Object makeMove(Bowl bowl) {
        // The game ends if all bowls of the turn player are empty
        // The winner is the player with the most on their territory(all bowls)
        Bowl lastBowl = null;
        if(!this.getGameState().equals(GameState.GAMEOVER)){
            try {
                lastBowl = (Bowl) bowl.divideStones(this);
            } catch (InvalidMoveException e) {
                System.out.println(e.getMessage());
                //e.printStackTrace();
            }
        }

        return lastBowl;
    }
    public void setSwitchPlayerEventListener(SwitchPlayerEventListener listener){
        this.listener = listener;
    }
    private void setWinner(Player winner) {
        this.winner = winner;
    }
    public Player getWinner(){
        return this.winner;
    }
    public void setGameState(GameState state){
        this.gameState = state;
    }
    public GameState getGameState(){
        return this.gameState;
    }
    public void switchPlayer(){
        if(this.getPlayerTurn().equals(this.getOpponent())){
            this.setPlayerTurn(this);
            this.getOpponent().setPlayerTurn(this);
        }
        else{
            this.setPlayerTurn(this.getOpponent());
            this.getOpponent().setPlayerTurn(this.getOpponent());
        }
        if(listener != null) listener.onSwitchPlayer(this.getPlayerTurn());
    }
    public Player getPlayerTurn(){
        return this.playerTurn;
    }
    public void setPlayerTurn(Player player){
        this.playerTurn = player;
    }
    public Player getOpponent(){
        return this.opponent;
    }
    public void setOpponent(Player o){
        this.opponent = o;
    }
    public Boolean isOwner(Bowl bowl){
        return this.equals(bowl.getOwner());
    }
    public static void setGameStateForPlayers(Player playerOne, Player playerTwo, GameState state){
        playerOne.setGameState(state);
        playerTwo.setGameState(state);
    }
    public static void setOpponents(Player playerOne, Player playerTwo){
        playerOne.setOpponent(playerTwo);
        playerTwo.setOpponent(playerOne);
    }
    public static void setTurnForPlayers(Player playerOne, Player playerTwo){
        playerOne.setPlayerTurn(playerOne);
        playerTwo.setPlayerTurn(playerOne);
    }
    public static void setWinner(Player playerOne, Player playerTwo, Player winner){
        playerOne.setWinner(winner);
        playerTwo.setWinner(winner);
    }
}
