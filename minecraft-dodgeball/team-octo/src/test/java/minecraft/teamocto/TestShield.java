package minecraft.teamocto;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;

// For Mockito to work, dependencies need to be added to the pom.xml
// To be abe to mock final classes or methods, the test/resources/mockito-extensions is needed
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestShield {
	private Shield myShield;
	private Material goalMaterial = Material.GLASS_PANE;
	
	private PlayerInteractEvent myEvent = mock(PlayerInteractEvent.class);
	private Block block = mock(Block.class);
	private Player aPlayer = mock(Player.class);
	Material blockMaterial = Material.AIR;
	
	@BeforeEach
	public void setBehaviour() {
		/**
		 * Set the behaviour of the mock objects.
		 * This can be done with either when(mock.function()).thenVERB(return-object)
		 * or with doVERB(return-object).when(mock).function();
		 * there is a difference in the order of handling these calls
		 * when().thenVERB() might give issues
		 */
		when(myEvent.getPlayer()).thenReturn(aPlayer);
		when(aPlayer.getFacing()).thenReturn(BlockFace.NORTH);
		//System.out.println("myEvent.getPlayer().getFacing() now gives " + myEvent.getPlayer().getFacing());
		
		when(myEvent.getClickedBlock()).thenReturn(block);
		when(block.getLocation()).thenReturn(new Location(null,0,0,0));
		
		when(block.getType()).thenReturn(blockMaterial);
		doAnswer(new Answer<Void>() {
		    public Void answer(InvocationOnMock invocation) {
		       blockMaterial = (Material) invocation.getArguments()[0];
		       when(block.getType()).thenReturn(blockMaterial);
		       return null;
		    }
		}).when(block).setType(any(Material.class));
	}

	@Test
	public void createShieldTest() {
		myShield = new Shield();
		myShield.buildShield(myEvent, 1);

		// Check the position of the shield
		assertEquals(myEvent.getClickedBlock().getLocation(), myShield.getLocation());
		// Check the shield's material is inserted into the event-Block
		assertEquals(goalMaterial, myShield.getType());
		assertEquals(goalMaterial, myEvent.getClickedBlock().getType());
	}
	
	@Test
	public void checkShieldSize() {
		// TODO check Shields neighbours block type is also goalMaterial
	}

}
