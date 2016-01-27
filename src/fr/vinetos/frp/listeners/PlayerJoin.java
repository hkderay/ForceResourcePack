package fr.vinetos.frp.listeners;

import fr.vinetos.frp.ForceResourcePack;
import fr.vinetos.frp.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by VINETOS on 26/12/2015.
 */
public class PlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void listen(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(!ForceResourcePack.getConfigUtils().getBoolean("join-enabled"))return;
        if(p.hasPermission("frp.bypass"))return;
        /*Send the resource pack*/
        ForceResourcePack.getPlayers2().put(p, System.currentTimeMillis());
        ForceResourcePack.launchTask();
        ForceResourcePack.getPlayers().put(p, System.currentTimeMillis());

        Bukkit.getScheduler().runTaskLater(ForceResourcePack.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player pls : ForceResourcePack.getPlayers().keySet()){
                    if(System.currentTimeMillis() - ForceResourcePack.getPlayers().get(pls) >= 10000){//10s
                        pls.sendMessage(ForceResourcePack.getConfigUtils().getMessage(ConfigUtils.MessagesType.NOREPONSE));
                        if(ForceResourcePack.getConfigUtils().getBoolean("kick")){
                            pls.kickPlayer(ForceResourcePack.getConfigUtils().getMessage(ConfigUtils.MessagesType.NOREPONSE));
                        }
                    }
                }
            }
        }, 200);//10s
    }

}
