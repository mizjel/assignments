package nl.sogyo.template;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin {

    private MyPluginListener myPluginListener;
    private MyCommandExecutor myCommandExecutor;

    @Override
    public void onLoad() {
        super.onLoad();

        myPluginListener = new MyPluginListener(this);
        myCommandExecutor = new MyCommandExecutor(this);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        //Register the Event Listener belonging to this plugin.
        getServer().getPluginManager().registerEvents(myPluginListener, this);
        getCommand("ping").setExecutor(myCommandExecutor);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        //Unregister our Event Listener when we get disabled.
        //Strangely enough this does not go through the same API as registering.
        HandlerList.unregisterAll(myPluginListener);
    }
}
