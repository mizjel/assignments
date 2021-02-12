package minecraft.teamocto.builders;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import minecraft.teamocto.DefensePlugin;
import minecraft.teamocto.muurbouwen.Wall;

public class WallBuilder extends Builder {
    public WallBuilder(DefensePlugin plugin) {
        super(plugin, Material.WOODEN_PICKAXE);
    }

    @Override
    public void build(PlayerInteractEvent event) {
        //Bukkit.broadcastMessage("I would build a great wall, and no one builds walls better than me, believe me, and I'll build them very inexpensively. I will build a great wall and I'll have Mexico pay for that wall. - Donny 'Bunkerboy' J Trump");
        plugin.wall.BuildWall(event);
    }
}
