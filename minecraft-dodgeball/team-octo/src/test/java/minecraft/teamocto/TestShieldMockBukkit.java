package minecraft.teamocto;

import be.seeseemelk.mockbukkit.WorldMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class TestShieldMockBukkit {
	
	private ServerMock server;
	private DefensePlugin plugin;
	private World world;
	private PlayerInteractEvent myEvent;
	private Location loc;

	@BeforeEach
	public void setUp() {
		server = MockBukkit.mock();
		plugin = (DefensePlugin) MockBukkit.load(DefensePlugin.class);
		world = new WorldMock(Material.STONE, 2);
		//Player aPlayer = mock(Player.class);
		//when(aPlayer.getFacing()).thenReturn(BlockFace.NORTH);
		PlayerMock aPlayer = server.addPlayer();
		loc = new Location(world, 0, 3, 0);
		myEvent = new PlayerInteractEvent(aPlayer, Action.RIGHT_CLICK_BLOCK, null, loc.getBlock(), BlockFace.UP);
	}

	@AfterEach
	public void tearDown() {
		MockBukkit.unmock();
	}
	
	@Test
	public void testSize() {
		Shield myShield = new Shield();
		myShield.buildShield(myEvent, 3);
		
		//Shield is placed from  location 0(x), 3(y), 0(z), so actual shield location starts from y + 1
		Location actualFirstLocation = loc.clone().add(0, 1, 0);
		assertEquals(Material.GLASS_PANE, actualFirstLocation.clone().add(new Vector(1,0,0)).getBlock().getType());
		assertEquals(Material.GLASS_PANE, actualFirstLocation.clone().add(new Vector(0,2,0)).getBlock().getType());
		assertEquals(Material.GLASS_PANE, actualFirstLocation.clone().add(new Vector(0,1,0)).getBlock().getType());
	}

}
