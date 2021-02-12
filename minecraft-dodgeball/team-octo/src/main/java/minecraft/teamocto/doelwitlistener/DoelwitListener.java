package minecraft.teamocto.doelwitlistener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import minecraft.teamocto.DefensePlugin;

public class DoelwitListener implements Listener{
	
	 private final DefensePlugin plugin;

	    public DoelwitListener(DefensePlugin myPlugin) {

	        this.plugin = myPlugin;
	    }
	@EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

    	if(event.getBlock().getType() == Material.GREEN_WOOL) {
    		this.plugin.doelWitScore.addScoreRed();
    		Bukkit.broadcastMessage("score Red team = " + String.valueOf(this.plugin.doelWitScore.getScoreRed()));
    	}
    	if(event.getBlock().getType() == Material.RED_WOOL) {
    		this.plugin.doelWitScore.addScoreGreen();
    		Bukkit.broadcastMessage("score Green team = " + String.valueOf(this.plugin.doelWitScore.getScoreGreen()));
    	}
    	this.plugin.doelWitScore.checkWin();
    }
    
    @EventHandler
    public void onBlocktnt(EntityExplodeEvent event) {
    	for(Block b : event.blockList()){
    		if(b.getType() == Material.GREEN_WOOL) {
        		this.plugin.doelWitScore.addScoreRed();
        		Bukkit.broadcastMessage("score Red team = " + String.valueOf(this.plugin.doelWitScore.getScoreRed()));
        		
        	}
        	if(b.getType() == Material.RED_WOOL) {
        		this.plugin.doelWitScore.addScoreGreen();
        		Bukkit.broadcastMessage("score Green team = " + String.valueOf(this.plugin.doelWitScore.getScoreGreen()));
        	}
    	 }
    	this.plugin.doelWitScore.checkWin();
       	
    }
}
