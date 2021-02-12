package nl.sogyo.template;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class MyPluginListener implements Listener {

    private final MyPlugin myPlugin;

    public MyPluginListener(MyPlugin myPlugin) {

        this.myPlugin = myPlugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Bukkit.broadcastMessage("A wild player has appeared!");
        event.setJoinMessage("Please welcome " + event.getPlayer().getName() + " to the server!");
    }

    @EventHandler
    public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();

        if(event.isSprinting()) {
            player.setWalkSpeed(0);
            player.sendMessage("Bad " + player.getName() + " sprinting is forbidden!");
        }else {
            player.setWalkSpeed(0.2f);
        }
    }
}
