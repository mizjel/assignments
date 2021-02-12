package nl.sogyo.mancala.api.models;

import nl.sogyo.mancala.domain.Mancala;

public class PlayerDto {
	String name;
	public String getName() { return name; }
	
	String type;
	public String getType() { return type; }

	boolean hasTurn;
	public boolean getHasTurn() { return hasTurn; }

	PitDto[] pits;
	public PitDto[] getPits() { return pits; }

	public PlayerDto(Mancala mancala, String name, boolean isFirstPlayer) {
		this.name = name;
		type = isFirstPlayer ? "player1" : "player2";
		hasTurn = mancala.isToMovePlayer(isFirstPlayer ? 1 : 2);
		pits = new PitDto[7];
		var firstHole = isFirstPlayer ? 1 : 8;
		for(int i = 0; i < 7; ++i) {
			pits[i] = new PitDto(i + firstHole, mancala.getStonesForPit(i + firstHole));
		}
	}
}
