package minecraft.teamocto.doelwitlistener;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import minecraft.teamocto.DefensePlugin;

public class ResetScore implements CommandExecutor{
    private final DefensePlugin plugin;

	
	public ResetScore(DefensePlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		 String commandName = command.getName();
			
	     	switch (commandName.toLowerCase()) {
	     		case "resetscore":
	     			Bukkit.broadcastMessage("the score is reset!");
	            	plugin.doelWitScore.resetScore();
	           	 	return true;
	           	 	
	         default:
	        	 return false;
	        }

		}
}
	
		 
		 
		 
		 