package minecraft.teamocto;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import minecraft.teamocto.doelwitlistener.DoelwitScore;

class DoelWitScoreTest {
	
	DoelwitScore testDoel = new DoelwitScore(0,0);
	
	@Test
	void GetAddScoreBlueTest() {
		
		testDoel.addScoreGreen();
		testDoel.addScoreGreen();
		testDoel.addScoreGreen();
		
		assertTrue(testDoel.getScoreGreen() == 3);
	}
	@Test
	void GetAddScoreRedTest() {
		
		testDoel.addScoreRed();
		testDoel.addScoreRed();
		testDoel.addScoreRed();
		testDoel.addScoreRed();
		
		assertTrue(testDoel.getScoreRed() == 4);
		
	}
	
	@Test
	void ResetTest() {
		
		for(int i = 0; i < 64; ++i) {
			testDoel.addScoreRed();
		}
		
		testDoel.resetScore();
		
		assertTrue(testDoel.getScoreRed() == 0);
		assertTrue(testDoel.getScoreGreen() == 0);	
	}

}
