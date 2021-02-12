package minecraft.teamocto;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import minecraft.teamocto.builders.WallBuilder;
import minecraft.teamocto.muurbouwen.Wall;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WallTest{
  private ServerMock server;
  private DefensePlugin plugin;
  private World world;
  private Location someLocation;

  @BeforeEach
  public void setUp() {
    server = MockBukkit.mock();
    plugin = (DefensePlugin) MockBukkit.load(DefensePlugin.class);
    world = new WorldMock(Material.DIRT, 100000);
    someLocation = new Location(world, 10, 10, 10, 360, 0);

  }

  @AfterEach
  public void tearDown()
  {
      MockBukkit.unmock();
  }

  @Test
  public void removeItemTest(){
    PlayerMock player = server.addPlayer();
    
    player.getInventory().addItem(new ItemStack(Material.IRON_BLOCK));
    Wall.removeItems(player.getInventory(),Material.IRON_BLOCK,1);
    assertFalse(player.getInventory().contains(Material.IRON_BLOCK));

  }
  
}

