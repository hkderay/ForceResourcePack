package fr.vinetos.frp;

import fr.vinetos.frp.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.concurrent.RunnableFuture;

/**
 * Created by VINETOS on 26/12/2015.
 */
public class ForceResourcePack extends JavaPlugin {

    public static ConfigUtils configUtils;
    private static HashMap<Player, Long> players = new HashMap<Player, Long>();
    private static HashMap<Player, Long> players2 = new HashMap<Player, Long>();
    private static Plugin instance;
    private static int task;

    @Override
    public void onEnable() {
        if(!Bukkit.getVersion().contains("1.8")){
            System.out.println("[ForceResourcePack] The plugin work only on a 1.8.X version.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        instance = this;
        saveDefaultConfig();
        configUtils = new ConfigUtils(getConfig());
        new EventsManager(this).registerEvents();
        getCommand("frp").setExecutor(new Command());
        getCommand("forceresourcepack").setExecutor(new Command());
    }

    public static void launchTask(){
        if(task != 0)
            Bukkit.getScheduler().cancelTask(task);

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), new Runnable() {
            @Override
            public void run() {
                //
                for(Player p : getPlayers2().keySet()){
                    if(System.currentTimeMillis() - getPlayers2().get(p) >= 2000){
                        p.setResourcePack(ForceResourcePack.getConfigUtils().getJoinPack());
                        getPlayers2().remove(p);
                    }
                }
            }
        }, 0, 20);
    }

    public static ConfigUtils getConfigUtils(){
        return configUtils;
    }

    public static HashMap<Player, Long> getPlayers(){
        return players;
    }

    public static HashMap<Player, Long> getPlayers2(){
        return players2;
    }

    public static Plugin getInstance(){
        return instance;
    }
}
