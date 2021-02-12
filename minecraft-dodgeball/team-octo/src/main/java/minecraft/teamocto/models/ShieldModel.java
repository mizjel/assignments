package minecraft.teamocto.models;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ShieldModel {
    private Player owner;
    private List<Location> locations;

    public ShieldModel(Player player) {
        owner = player;
        locations = new ArrayList<>();
    }
    public ShieldModel(Player player, List<Location> list) {
        owner = player;
        locations = list;
    }
    public void addBlockLocation(Location location){
        locations.add(location);
    }
    public Player getOwner(){
        return this.owner;
    }
    public List<Location> getBlockLocations(){
        return this.locations;
    }
    public Boolean hasLocation(Location location){
        Location foundLocation = locations.stream().filter(l -> l.getX() == location.getX() && l.getY() == location.getY() && l.getZ() == location.getZ()).findAny().orElse(null);
        return (foundLocation != null) ? true : false;
    }
    public void breakShield(){
        for(Location location : locations){
            Block block = location.getBlock();
            if(block.getType() == Material.GLASS_PANE) block.setType(Material.AIR);
        }
    }
}
