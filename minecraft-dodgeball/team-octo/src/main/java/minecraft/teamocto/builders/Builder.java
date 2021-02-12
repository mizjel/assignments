package minecraft.teamocto.builders;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import minecraft.teamocto.DefenseCreationEvent;
import minecraft.teamocto.DefensePlugin;

public abstract class Builder implements Listener {

    protected final DefensePlugin plugin;

    //Store the item type used for this type of building tool
    private Material material;

    protected Builder(DefensePlugin plugin, Material material) {
        this.plugin = plugin;
        this.material = material;
    }
    protected abstract void build(PlayerInteractEvent event);

    //Define an event handler for the player interact event
    //This is an event that fires whenever a player interacts with an object or air
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        //Check if the player did a right click on a block with the main hand and the corresponding axe equipped
        //Also check if the useInteractedBlock result is deny to not let it fire twice
        if(event.useInteractedBlock() != Result.DENY && event.getHand() == EquipmentSlot.HAND 
            && player.getInventory().getItemInMainHand().getType() == material && event.getAction() == Action.RIGHT_CLICK_BLOCK
            && event.getClickedBlock().getType() != Material.CHEST){
            //TODO: Check if player clicked in bounds of own playing field
            this.build(event);

            DefenseCreationEvent defenseCreationEvent = new DefenseCreationEvent(event); // Initialise your Event
            Bukkit.getPluginManager().callEvent(defenseCreationEvent); // This fires the event and allows any listener to listen to the event
            // Bukkit.broadcastMessage(defenseCreationEvent.getMessage());
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();

        if(player.getInventory().getItemInMainHand().getType() == this.material){
            event.setCancelled(true);
        }
    }
}
