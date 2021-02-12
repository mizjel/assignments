package nl.sogyo.mancala.api.models;

public class PitDto {
	int index;

	public int getIndex() {
		return index;
	}

	int nrOfStones;

	public int getNrOfStones() { return nrOfStones; }

	public PitDto(int index, int nrOfStones) {
		this.index = index;
		this.nrOfStones = nrOfStones;
	}
}