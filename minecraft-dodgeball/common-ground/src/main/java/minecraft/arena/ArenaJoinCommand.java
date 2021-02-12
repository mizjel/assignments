package minecraft.arena;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class ArenaJoinCommand implements CommandExecutor {

    private final MyPlugin plugin;
    private final Random randomInt = new Random();

    public ArenaJoinCommand(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;
            if (plugin.getCurrentArena() == null) {
                player.sendMessage("arena does not exist");
                return false;
            }

            if (args.length == 1) { // if an argument is given, check if it is either 1 or 2
                try {
                    int teamId = Integer.parseInt(args[0]);
                    if (teamId == 1) {
                        plugin.getCurrentArena().getTeamTwo().remove(player); // if arena not exists.
                        plugin.getCurrentArena().getTeamOne().add(player);
                        player.teleport(plugin.getCurrentArena()
                                .getRandomAvailableLocation(plugin.getCurrentArena().getSpawnsTeamOne())); // arena will get an available spawn
                    }
                    else if (teamId == 2) {
                        plugin.getCurrentArena().getTeamOne().remove(player);
                        plugin.getCurrentArena().getTeamTwo().add(player);
                        player.teleport(plugin.getCurrentArena().
                                getRandomAvailableLocation(plugin.getCurrentArena().getSpawnsTeamTwo()));
                    }
                    else {
                        assignPlayerToRandomTeam(player);
                    }

                } catch (NumberFormatException n) {
                    assignPlayerToRandomTeam(player);
                }
                return true;
            }
            else {
                assignPlayerToRandomTeam(player);
                return false;
            }

        }
        return false;
    }

    private void assignPlayerToRandomTeam(Player player) {

        if (randomInt.nextInt(2) == 1) {
            plugin.getCurrentArena().getTeamTwo().remove(player);
            plugin.getCurrentArena().getTeamOne().add(player);
            player.teleport(plugin.getCurrentArena().getRandomAvailableLocation(plugin.getCurrentArena().getSpawnsTeamOne()));
        }
        else {
            plugin.getCurrentArena().getTeamOne().remove(player);
            plugin.getCurrentArena().getTeamTwo().add(player);
            player.teleport(plugin.getCurrentArena().getRandomAvailableLocation(plugin.getCurrentArena().getSpawnsTeamTwo()));
        }
    }
}
