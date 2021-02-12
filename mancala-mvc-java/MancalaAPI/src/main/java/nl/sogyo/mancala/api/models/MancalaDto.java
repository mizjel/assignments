package nl.sogyo.mancala.api.models;

import nl.sogyo.mancala.domain.Mancala;

public class MancalaDto {
    PlayerDto[] players;
    public PlayerDto[] getPlayers() { return players; }
    
    GameStatusDto gameStatus;
    public GameStatusDto getGameStatus() { return gameStatus; }


    public MancalaDto(Mancala mancala, String namePlayer1, String namePlayer2) {
        players = new PlayerDto[2];
        players[0] = new PlayerDto(mancala, namePlayer1, true);
        players[1] = new PlayerDto(mancala, namePlayer2, false);
        gameStatus = new GameStatusDto(mancala);
    }
}