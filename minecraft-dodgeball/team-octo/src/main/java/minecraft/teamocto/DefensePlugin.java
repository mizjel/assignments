package minecraft.teamocto;

import java.io.File;
import java.util.logging.Handler;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import minecraft.teamocto.builders.*;
import minecraft.teamocto.doelwitlistener.DoelwitListener;
import minecraft.teamocto.doelwitlistener.DoelwitScore;
import minecraft.teamocto.doelwitlistener.ResetScore;
import minecraft.teamocto.muurbouwen.Wall;
import minecraft.teamocto.noblockdrop.NoBlockDrop;
import minecraft.teamocto.doelwitlistener.BlocksLeft;

public class DefensePlugin extends JavaPlugin {
    private PlayerHandler playerHandler;
    private Builder shieldBuilder;
    private Builder wallBuilder;
    private DoelwitListener scoreListerner;
    public DoelwitScore doelWitScore;
    public Wall wall;
    public Shield shield;
    private NoBlockDrop noBlockDrop;
    private ResetScore resetScore;
    private BlocksLeft blocksLeft;

    public DefensePlugin(){
        super();
    }

    // Needed for Mockbukkit
    protected DefensePlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        playerHandler =  new PlayerHandler();
        shieldBuilder = new ShieldBuilder(this);
        wallBuilder = new WallBuilder(this);
        scoreListerner = new DoelwitListener(this);
        wall = new Wall();
        shield = new Shield();
        doelWitScore = new DoelwitScore(0,0);
        noBlockDrop = new NoBlockDrop(this);
        resetScore = new ResetScore(this);
        blocksLeft = new BlocksLeft(this);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        // Register the event listeners for this plugin
        getServer().getPluginManager().registerEvents(shieldBuilder, this);
        getServer().getPluginManager().registerEvents(wallBuilder, this);
        getServer().getPluginManager().registerEvents(playerHandler, this);
        getServer().getPluginManager().registerEvents(scoreListerner, this);
        getServer().getPluginManager().registerEvents(noBlockDrop, this);
        getServer().getPluginManager().registerEvents(shield, this);
        getCommand("resetscore").setExecutor(resetScore);
        getCommand("blocksleft").setExecutor(blocksLeft);
    }

    @Override
    public void onDisable(){
        super.onDisable();

        // Unregister the event listeners for this plugin
        HandlerList.unregisterAll(playerHandler);
        HandlerList.unregisterAll(shieldBuilder);
        HandlerList.unregisterAll(wallBuilder);
        HandlerList.unregisterAll(scoreListerner);
        HandlerList.unregisterAll(noBlockDrop);
        HandlerList.unregisterAll(shield);
        HandlerList.unregisterAll(this);
    }
}
