package minecraft.arena;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

public class TestArenaCreateCommand {
  private ServerMock server;
  private MyPlugin plugin;
  private World world;
  private Location someLocation;

  private class LookingPlayerMock extends PlayerMock{
    // this class is because MockBukkit does not set a target block on a mocked player

    public LookingPlayerMock(ServerMock server, String name) {
      super(server, name);
    }

    Location lookingAtLocation = new Location(world, 10, 10, 10, 360, 0);
    Block targetBlock = new BlockMock(lookingAtLocation);
    
    @Override
    public Block getTargetBlock(Set<Material> transparent, int maxDistance){
      return this.targetBlock;
    }
  }

  @BeforeEach
  public void setUp() {
    server = MockBukkit.mock();
    plugin = (MyPlugin) MockBukkit.load(MyPlugin.class);

    world = new WorldMock(Material.DIRT, 3);
    someLocation = new Location(world, 10, 10, 10, 360, 0);
   }

  @AfterEach
  public void tearDown() {
      MockBukkit.unmock();
  }

  @Test
  public void arenaCreateCommand_CommandIssuedWithoutArguments_DefaultSizeArenaCreated(){
    LookingPlayerMock player = new LookingPlayerMock(server, "John Doe");

    plugin.getArenaCreateCommand().onCommand(player, null, null, new String[] {});

    assertNotNull(plugin.getCurrentArena(), "Arena not created");

    assertTrue( plugin.getCurrentArena().getWidth() == 60 &&
                plugin.getCurrentArena().getDepth() == 60 &&
                plugin.getCurrentArena().getHeight() == 10,
                "Arena Dimensions incorrect"
              );
  }

  @Test
  public void arenaCreateCommand_CommandIssuedWith3Arguments_ArenaCreatedWithGivenDimensions(){
    LookingPlayerMock player = new LookingPlayerMock(server, "John Doe");

    plugin.getArenaCreateCommand().onCommand(player, null, null, new String[] {"50", "100", "15"});

    assertNotNull(plugin.getCurrentArena(), "Arena not created");

    assertTrue( plugin.getCurrentArena().getWidth() == 50 &&
                plugin.getCurrentArena().getDepth() == 100 &&
                plugin.getCurrentArena().getHeight() == 15,
                "Arena Dimensions incorrect"
              );
  }

  @Test
  public void arenaCreateCommand_CommandIssuedWithoutNaNArguments_DefaultSizeArenaCreated(){
    LookingPlayerMock player = new LookingPlayerMock(server, "John Doe");

    plugin.getArenaCreateCommand().onCommand(player, null, null, new String[] {"x", "100", "15"});

    assertNotNull(plugin.getCurrentArena(), "Arena not created");
    assertTrue( plugin.getCurrentArena().getWidth() == 60 &&
                plugin.getCurrentArena().getDepth() == 60 &&
                plugin.getCurrentArena().getHeight() == 10,
                "Arena Dimensions incorrect"
              );

    plugin.getArenaCreateCommand().onCommand(player, null, null, new String[] {"50", "x", "15"});

    assertNotNull(plugin.getCurrentArena(), "Arena not created");
    assertTrue( plugin.getCurrentArena().getWidth() == 60 &&
                plugin.getCurrentArena().getDepth() == 60 &&
                plugin.getCurrentArena().getHeight() == 10,
                "Arena Dimensions incorrect"
              );
  
    plugin.getArenaCreateCommand().onCommand(player, null, null, new String[] {"50", "100", "x"});

    assertNotNull(plugin.getCurrentArena(), "Arena not created");
    assertTrue( plugin.getCurrentArena().getWidth() == 60 &&
                plugin.getCurrentArena().getDepth() == 60 &&
                plugin.getCurrentArena().getHeight() == 10,
                "Arena Dimensions incorrect"
              );
  }

  @Test
  public void arenaCreateCommand_CommandNotPerformedByAPlayer_FalseReturned(){
    ConsoleCommandSender ConsoleCommandSnd = new ConsoleCommandSenderMock();

    assertFalse(plugin.getArenaCreateCommand().onCommand(ConsoleCommandSnd, null, null, new String[] {}), "Command was not sent by a player but still processed");
  }
}
