package nl.sogyo.template;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MyCommandExecutor implements CommandExecutor {
    private final MyPlugin plugin;

    public MyCommandExecutor(MyPlugin plugin) {
        this.plugin = plugin;
    }
    /**
     * Executes the given command, returning its success
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();

        switch (commandName.toLowerCase()) {
            case "ping":
                handlePing(sender, command, label, args);
                break;
            default:
                return false;
        }
        return true;
    }

    private boolean handlePing(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Pong!");
        return true;
    };
}
