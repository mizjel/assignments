package minecraft.teamocto.doelwitlistener;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import minecraft.teamocto.DefensePlugin;

public class BlocksLeft implements CommandExecutor{
    private final DefensePlugin plugin;

	
	public BlocksLeft(DefensePlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		 String commandName = command.getName();
			
	     	switch (commandName.toLowerCase()) {
	     		case "blocksleft":
	     			String blocksGreen = this.plugin.doelWitScore.blocksLeftGreen();
	     			String blocksRed = this.plugin.doelWitScore.blocksLeftRed();
	     			Bukkit.broadcastMessage("The Green team has :" + blocksGreen + " blocks left");
	     			Bukkit.broadcastMessage("The Red team has :" + blocksRed + " blocks left");
	           	 	return true;
	           	 	
	         default:
	        	 return false;
	        }

		}
}
	