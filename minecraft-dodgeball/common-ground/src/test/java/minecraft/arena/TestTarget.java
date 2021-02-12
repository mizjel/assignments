package minecraft.arena;

import static org.junit.jupiter.api.Assertions.*;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;

public class TestTarget {
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
  public void target_NewArena_BothTargetsHaveLentghPower3Blocks(){
    Target[] targets = testArena.getTargets();

    for(Target target: targets){
      int amountOfBlocks = 0;      
      Block[] blocks = target.getBlocks();

      for(Block block : blocks){
        if(block.getType() == target.getMaterial()){
          amountOfBlocks++;
        }
      }

      assertEquals((int) Math.pow(target.getLength(), 3), amountOfBlocks, "The amount of blocks in the target is incorrect");
    }
  }
}
