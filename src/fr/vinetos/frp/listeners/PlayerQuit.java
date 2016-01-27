package fr.vinetos.frp.listeners;

import fr.vinetos.frp.ForceResourcePack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by VINETOS on 26/12/2015.
 */
public class PlayerQuit implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void listen(PlayerQuitEvent e){
        Player p = e.getPlayer();

        ForceResourcePack.getPlayers().remove(p);
    }

}
