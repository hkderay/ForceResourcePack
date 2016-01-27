package fr.vinetos.frp.listeners;

import fr.vinetos.frp.ForceResourcePack;
import fr.vinetos.frp.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 * Created by VINETOS on 26/12/2015.
 */
public class PlayerChangedWorld implements Listener {

    @EventHandler
    public void listen(PlayerChangedWorldEvent e){
        Player p = e.getPlayer();
        String world = e.getPlayer().getWorld().getName();
        ConfigUtils cu = ForceResourcePack.getConfigUtils();
        p.setResourcePack(cu.getWorldResourcePack(world));
        ForceResourcePack.getPlayers().put(p, System.currentTimeMillis());

        Bukkit.getScheduler().runTaskLater(ForceResourcePack.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player pls : ForceResourcePack.getPlayers().keySet()){
                    if(System.currentTimeMillis() - ForceResourcePack.getPlayers().get(pls) >= 10000){//10s
                        pls.kickPlayer(ForceResourcePack.getConfigUtils().getMessage(ConfigUtils.MessagesType.NOREPONSE));
                    }
                }
            }
        }, 200);//10s
    }

}
