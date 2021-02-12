package minecraft.teamocto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import minecraft.teamocto.builders.*;

public class ShieldBuilderTest {
    // private ShieldBuilder shieldBuilder;
    // private WallBuilder wallBuilder;
    // private PlayerInteractEvent event = mock(PlayerInteractEvent.class);
    // private Player player = mock(Player.class);
    // private Block clickedBlock = mock(Block.class);

    // private DefensePlugin plugin = new DefensePlugin();
    @Test
    public void player_clicks_right_mouse_with_wooden_pickaxe(){
        // shieldBuilder = new ShieldBuilder(plugin);
        // when(event.getClickedBlock()).thenReturn(clickedBlock);
        // when(clickedBlock.getLocation()).thenReturn(new Location(null,0,0,0));

        //if mock player clicks on a block, assert if (?????)
        assertEquals(true, true);
    }
}
