package minecraft.arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import java.util.ArrayList;

public class ArenaListener implements Listener {

    private final MyPlugin plugin;

    public ArenaListener(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerRespawnEvent respawnEvent) {
        Player toBeRespawned = respawnEvent.getPlayer();
        ArrayList<Location> candidateSpawns;
        if (plugin.getCurrentArena() != null) {
            if (plugin.getCurrentArena().getTeamOne().contains(toBeRespawned)) {
                candidateSpawns = plugin.getCurrentArena().getSpawnsTeamOne();
                respawnEvent.setRespawnLocation(plugin.getCurrentArena()
                        .getRandomAvailableLocation(candidateSpawns));

            }
            else if (plugin.getCurrentArena().getTeamTwo().contains(toBeRespawned)) {
                candidateSpawns = plugin.getCurrentArena().getSpawnsTeamTwo();
                respawnEvent.setRespawnLocation(plugin.getCurrentArena()
                        .getRandomAvailableLocation(candidateSpawns));
            }
        }
    }

//    @EventHandler
//    public void onPlayerOutOfBounds(PlayerMoveEvent playerMove) {
//        Player toBeTeleported = playerMove.getPlayer();
//        ArrayList<Location> candidateSpawns;
//        if (!(plugin.getCurrentArena() == null)) {
//            // check whether player in arena
//            if (plugin.getCurrentArena().getTeamOne().contains(toBeTeleported)) {
//                candidateSpawns = plugin.getCurrentArena().getSpawnsTeamOne();
//                toBeTeleported.teleport(plugin.getCurrentArena()
//                        .getRandomAvailableLocation(candidateSpawns));
//
//            }
//            else if (plugin.getCurrentArena().getTeamTwo().contains(toBeTeleported)) {
//                candidateSpawns = plugin.getCurrentArena().getSpawnsTeamTwo();
//                toBeTeleported.teleport(plugin.getCurrentArena()
//                        .getRandomAvailableLocation(candidateSpawns));
//            }
//        }
//    }
}
