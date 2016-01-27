package fr.vinetos.frp.listeners;

import fr.vinetos.frp.ForceResourcePack;
import fr.vinetos.frp.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

/**
 * Created by VINETOS on 26/12/2015.
 */
public class PlayerResourcePackStatus implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void listen(PlayerResourcePackStatusEvent e){
        final Player p = e.getPlayer();
        ForceResourcePack.getPlayers().remove(p);
        final ConfigUtils cu = ForceResourcePack.getConfigUtils();
        PlayerResourcePackStatusEvent.Status status = e.getStatus();

        if(status == PlayerResourcePackStatusEvent.Status.ACCEPTED){
            p.sendMessage(cu.getMessage(ConfigUtils.MessagesType.ACCEPTED));
        }
        else if(status == PlayerResourcePackStatusEvent.Status.DECLINED){
            Bukkit.getScheduler().runTask(ForceResourcePack.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if(ForceResourcePack.getConfigUtils().getBoolean("kick")){
                        p.kickPlayer(cu.getMessage(ConfigUtils.MessagesType.DECLINED));
                    }
                    p.sendMessage(cu.getMessage(ConfigUtils.MessagesType.DECLINED));
                }
            });
        }
        else if(status == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD){/*Error*/
            Bukkit.getScheduler().runTask(ForceResourcePack.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if(ForceResourcePack.getConfigUtils().getBoolean("kick")){
                        p.kickPlayer(cu.getMessage(ConfigUtils.MessagesType.FAILED));
                    }
                    p.sendMessage(cu.getMessage(ConfigUtils.MessagesType.FAILED));
                }
            });
        }
        else{/*Success*/
            p.sendMessage(cu.getMessage(ConfigUtils.MessagesType.SUCCESS));
        }
    }

}
