package minecraft.arena;

import static org.junit.jupiter.api.Assertions.*;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;

public class TestArena {
  private ServerMock server;
  private MyPlugin plugin;
  private World world;
  private Location someLocation;
  private Arena testArena;

  @BeforeEach
  public void setUp() {
    server = MockBukkit.mock();
    plugin = (MyPlugin) MockBukkit.load(MyPlugin.class);

    world = new WorldMock(Material.DIRT, 3);
    someLocation = new Location(world, 10, 10, 10, 360, 0);
    // convention: x depth, z width, y height
    testArena = new Arena(someLocation, 30, 40, 50);
  }

  @AfterEach
  public void tearDown() {
      MockBukkit.unmock();
  }

  @Test
  public void arena_NewArena_2TargetsAreCreated(){
    assertEquals(2, testArena.getTargets().length, "There should be 2 targets when an arena is spawned"); 
  }

  @Test
  public void arena_NewArena_TargetsAreInsideArena(){
    Arena differentSizeArenas[] = {  
                            new Arena(someLocation, 50, 100, 10),
                            new Arena(someLocation, 100, 50, 10)
                          };

    for (Arena arenaUnderTest : differentSizeArenas){
      Location arenaPosition = arenaUnderTest.getLocation();
      Location oppositeCorner = arenaPosition.clone().add(
                                                                          arenaUnderTest.getWidth(),
                                                                          arenaUnderTest.getHeight(),
                                                                          arenaUnderTest.getDepth()
                                                                        );

      Target[] targets = arenaUnderTest.getTargets();

      for (Target target : targets){
        int targetX = target.getLocation().getBlockX();
        int targetY = target.getLocation().getBlockY();
        int targetZ = target.getLocation().getBlockZ();

        assertTrue(targetX > arenaPosition.getBlockX() && targetX < oppositeCorner.getBlockX());
        assertTrue(targetY > arenaPosition.getBlockY() && targetY < oppositeCorner.getBlockY());
        assertTrue(targetZ > arenaPosition.getBlockZ() && targetZ < oppositeCorner.getBlockZ());
      }
    }
  }
}
