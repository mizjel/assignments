package minecraft.arena;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCreateCommand implements CommandExecutor{
    private final MyPlugin plugin;

    public ArenaCreateCommand(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Block block = player.getTargetBlock(null, 20); // 200 -> max distance to place arena
            Location inFrontOfPlayer = block.getLocation();
            Arena newArena;

            if (args.length == 3) {
                try {
                    int width = Integer.parseInt(args[0]);
                    int depth = Integer.parseInt(args[1]);
                    int height = Integer.parseInt(args[2]);
                    newArena = new Arena(inFrontOfPlayer, width, depth, height);
                }catch (NumberFormatException e) {
                    newArena = new Arena(inFrontOfPlayer,60,60,10);
                }
            }
            else {
                // Default dimensions 30x30x30 used
                newArena = new Arena(inFrontOfPlayer,60,60,10);
            }
            plugin.setCurrentArena(newArena);
        }
        return false;
    }
}


