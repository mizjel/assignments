package minecraft.teamocto.builders;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import minecraft.teamocto.DefensePlugin;

public class ShieldBuilder extends Builder {
    public int size = 3;

    public ShieldBuilder(DefensePlugin plugin){
        super(plugin, Material.GOLDEN_PICKAXE);
    }

    @Override
    public void build(PlayerInteractEvent event) {
        //Bukkit.broadcastMessage("Building a shield!");
        plugin.shield.buildShield(event, size);
    }
}
