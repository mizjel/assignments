package minecraft.arena;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.Material;

class ResourcePileTest {
  private ServerMock server;
  private MyPlugin plugin;
  private World world;
  private ResourceChest itemPile;
  private Location itemPileLoc;

  @BeforeEach
  public void setUp() {
    server = MockBukkit.mock();
    world = new WorldMock(Material.STONE, 2);
    plugin = (MyPlugin) MockBukkit.load(MyPlugin.class);
    
    itemPileLoc = new Location(world, 3, 3, 3);
	itemPile = new ResourceChest(itemPileLoc); 
  }

  @AfterEach
  public void tearDown()
  {
      MockBukkit.unmock();
  }

  @Test
  void itemsInItemstackTest(){
	  /**
	   * Assert that a chest is created, exactly one diamond-chestplate is contained
	   * and at least one glass-pane (it is very unlikely that no glass pane is contained)
	   */
	assertEquals(Material.CHEST, itemPileLoc.getBlock().getType());
	assertTrue(itemPile.getInventory().contains(Material.GLASS_PANE));
	assertTrue(itemPile.getInventory().contains(Material.DIAMOND_CHESTPLATE, 1));
	assertFalse(itemPile.getInventory().contains(Material.DIAMOND_CHESTPLATE, 2));
  }
  
  @Test
  void itemstackRestockTest(){
	  /**
	   * Assert that on clearing the chest, materials are gone
	   * and on restock, materials are respawned
	   */
	
	itemPile.getInventory().clear();
	assertFalse(itemPile.getInventory().contains(Material.GLASS_PANE));
	
	itemPile.restock();
	assertTrue(itemPile.getInventory().contains(Material.GLASS_PANE));
	//assertTrue(itemPile.getInventory().contains(Material.DIAMOND_CHESTPLATE, 1)); // Since there is only a 0.2 chance to spawn a new diamond armour, this test will fail often
	assertFalse(itemPile.getInventory().contains(Material.DIAMOND_CHESTPLATE, 2));
  }
  
  @Test
  void arenaRefillChestsTest() {
	  /**
	   * Checks the refillResources(), which should only call the restock() on both chests
	   * (so this test is somewhat redundant)
	   */
	Arena anArena = new Arena(new Location(world, 0, 3, 0), 60, 60, 10);
	itemPile = anArena.getChestOne();
	
	itemPile.getInventory().clear();
	anArena.refillResources();
	
	assertTrue(itemPile.getInventory().contains(Material.GLASS_PANE));
	//assertTrue(itemPile.getInventory().contains(Material.DIAMOND_CHESTPLATE, 1)); // Since there is only a 0.2 chance to spawn a new diamond armour, this test will fail often
	assertFalse(itemPile.getInventory().contains(Material.DIAMOND_CHESTPLATE, 2));
  }
  
  @Test
  void arenaRespawnChestsTest() {
	  /**
	   * Assert that on chest disappearance, during the refillResources() a new chest is made
	   */
	Arena anArena = new Arena(new Location(world, 0, 3, 0), 60, 60, 10);
	itemPile = anArena.getChestOne();
	Location location1 = itemPile.getChest().getLocation();
	
	itemPile.getInventory().clear();
	itemPile.getChest().getBlock().setType(Material.AIR);
	anArena.refillResources();
	
	itemPile = anArena.getChestOne();
	Location location2 = itemPile.getChest().getLocation();
	
	assertEquals(Material.CHEST, itemPile.getChest().getBlock().getType());
	assertTrue(itemPile.getInventory().contains(Material.GLASS_PANE));
	assertEquals(location1, location2);
  }
  
  @Test
  void arenaRestockOnRespawnTest() {
	  /**
	   * Assert that on chest disappearance, during the refillResources()
	   * the other chest (after being cleared) is restocked
	   */
	Arena anArena = new Arena(new Location(world, 0, 3, 0), 60, 60, 10);
	itemPile = anArena.getChestOne();
	itemPile.getInventory().clear();
	
	ResourceChest itemPile2 = anArena.getChestTwo();
	itemPile2.getInventory().clear();
	itemPile2.getChest().getBlock().setType(Material.AIR);
	
	anArena.refillResources();

	assertTrue(anArena.getChestOne().getInventory().contains(Material.GLASS_PANE));
	assertFalse(anArena.getChestOne().getInventory().contains(Material.DIAMOND_CHESTPLATE, 2));
  }
}
