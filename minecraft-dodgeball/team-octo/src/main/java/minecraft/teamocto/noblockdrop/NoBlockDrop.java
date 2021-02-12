package minecraft.teamocto.noblockdrop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import minecraft.teamocto.DefensePlugin;

public class NoBlockDrop  implements Listener {
	
	 private final DefensePlugin plugin;

	    public NoBlockDrop(DefensePlugin myPlugin) {
	        this.plugin = myPlugin;
	    }
	    
	    @EventHandler
	    public void onBreak(BlockBreakEvent event) {
	    	
	    	if (event.getBlock().getType() == Material.GREEN_WOOL ||event.getBlock().getType() == Material.RED_WOOL ||
	    			event.getBlock().getType() == Material.IRON_BLOCK || event.getBlock().getType() == Material.GLASS_PANE ) {
	    		event.setDropItems(false);
	    	}
	    	
	    	}
	    
	    @EventHandler
	    public void onExplodeBlock(EntityExplodeEvent event) {
	    	//Bukkit.broadcastMessage("explosie event (NoBlockDrop listener)");
	    	for(Block b : event.blockList()){
	    		if (b.getType() == Material.GREEN_WOOL ||b.getType() == Material.RED_WOOL ||
	    				b.getType() == Material.IRON_BLOCK || b.getType() == Material.GLASS_PANE ) {
	    			//Bukkit.broadcastMessage("Material is in the list (NoBlockDrop listener)");
	    			event.setYield(0);
	    			}
	    		}
	    	}
}
