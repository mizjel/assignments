package minecraft.teamocto.doelwitlistener;

import org.bukkit.Bukkit;

public class DoelwitScore {
	
int scoreGreen;
int scoreRed;
	
	public DoelwitScore(int blue,int red) {
		this.scoreGreen = blue;
		this.scoreRed = red;
	}
	
	public int getScoreGreen() {
		return this.scoreGreen;
		
	}
	public int getScoreRed() {
		return this.scoreRed;
		
	}
	public void addScoreRed() {
		this.scoreRed += 1;
	
	}
	public void addScoreGreen() {
		this.scoreGreen += 1;
	
	}
	
	public void resetScore() {
		this.scoreGreen = 0;
		this.scoreRed = 0;
	}
	
	public void checkWin() {
		if (this.scoreGreen >= 27 && this.scoreRed >= 27) {
			Bukkit.broadcastMessage("it is a draw!");
			this.resetScore();
		}
		else if (this.scoreGreen >= 27) {
			Bukkit.broadcastMessage("The Green team wins!");
			this.resetScore();
		}
		else if (this.scoreRed >= 27) {
			Bukkit.broadcastMessage("The Red team wins!");
			this.resetScore();
		}
		
	}
	public String blocksLeftRed() {
		int temp = 27 - this.scoreGreen;
		return String.valueOf(temp);
	}
	public String blocksLeftGreen() {
		int temp = 27 - this.scoreRed;
		return String.valueOf(temp);
	}
}
