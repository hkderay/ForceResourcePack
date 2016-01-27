package fr.vinetos.frp;

import fr.vinetos.frp.listeners.PlayerChangedWorld;
import fr.vinetos.frp.listeners.PlayerJoin;
import fr.vinetos.frp.listeners.PlayerQuit;
import fr.vinetos.frp.listeners.PlayerResourcePackStatus;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * Created by VINETOS on 26/12/2015.
 */
public class EventsManager {

    protected Plugin pl;

    public EventsManager(Plugin pl){
        this.pl = pl;
    }

    public void registerEvents(){
        PluginManager pm = pl.getServer().getPluginManager();

        pm.registerEvents(new PlayerJoin(), pl);
        pm.registerEvents(new PlayerQuit(), pl);
        if(ForceResourcePack.getConfigUtils().getBoolean("multi-worlds"))
            pm.registerEvents(new PlayerChangedWorld(), pl);
        pm.registerEvents(new PlayerResourcePackStatus(), pl);
    }

}
