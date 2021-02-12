package minecraft.arena;

import java.io.File;

import org.bukkit.event.HandlerList;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.PluginDescriptionFile;

/*
    MyPlugin has field currentArena which can be accessed through getter for location and dimensions.
 */

public class MyPlugin extends JavaPlugin {

    private ArenaCreateCommand arenaCommandPlacer;
    private Arena currentArena;
    private ArenaJoinCommand arenaCommandJoiner;
    private ArenaListener myArenaListener;

    // for unit test ---------------------------
    public ArenaJoinCommand getArenaJoinCommand(){
        return this.arenaCommandJoiner;
    }

    public ArenaCreateCommand getArenaCreateCommand(){
        return arenaCommandPlacer;
    }

    // -----------------

    public MyPlugin(){
        super();
    }

    // Needed for Mockbukkit
    protected MyPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        arenaCommandPlacer = new ArenaCreateCommand(this);
        arenaCommandJoiner = new ArenaJoinCommand(this);
        myArenaListener = new ArenaListener(this);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        //Register the Event Listener belonging to this plugin.
        getServer().getPluginManager().registerEvents(myArenaListener, this);
        getCommand("placearena").setExecutor(arenaCommandPlacer);
        getCommand("joinarena").setExecutor(arenaCommandJoiner);
    }

    @Override
    public void onDisable() {
        super.onDisable();

    }

    public void setCurrentArena(Arena arena) {
        this.currentArena = arena;
        
        BukkitRunnable restockRunnable = new BukkitRunnable() {
        	public void run() {currentArena.refillResources();}
        };
        restockRunnable.runTaskTimer(this, 0L, 600L); // 20 ticks = 1s, 600 ticks = 0.5 min
    }

    public Arena getCurrentArena() {
        return currentArena;
    }
}
