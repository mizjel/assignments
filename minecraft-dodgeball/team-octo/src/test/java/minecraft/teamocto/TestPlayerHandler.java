package minecraft.teamocto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

import org.bukkit.Material;

public class TestPlayerHandler {
  private ServerMock server;
  private DefensePlugin plugin;

  @BeforeEach
  public void setUp() {
    server = MockBukkit.mock();
    plugin = (DefensePlugin) MockBukkit.load(DefensePlugin.class);
  }

  @AfterEach
  public void tearDown()
  {
      MockBukkit.unmock();
  }

  @Test
  public void playerHandler_giveInitialItems_playerReceivesWoodGoldDiamondPickaxe(){
    PlayerMock player = server.addPlayer();

    PlayerHandler playerHandler = new PlayerHandler();

    playerHandler.giveInitialItems(player);

    assertTrue(player.getInventory().contains(Material.WOODEN_PICKAXE));
    assertTrue(player.getInventory().contains(Material.GOLDEN_PICKAXE));
    assertTrue(player.getInventory().contains(Material.DIAMOND_PICKAXE));
  }
}
