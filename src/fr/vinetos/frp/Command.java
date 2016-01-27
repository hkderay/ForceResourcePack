package fr.vinetos.frp;

import fr.vinetos.frp.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by VINETOS on 26/12/2015.
 */
public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(!sender.hasPermission("frp.command")){
            sender.sendMessage("§cYou don't have the permission to perform this command !");
            return true;
        }
        if(args.length != 2){
            if(args.length != 1){
                showHelp(sender);
                return true;
            }
            if(!args[0].equalsIgnoreCase("reload")){
                showHelp(sender);
                return true;
            }
            if(!sender.hasPermission("frp.command.reload")){
                sender.sendMessage("§cYou don't have the permission to perform this command !");
            }
            ForceResourcePack.getConfigUtils().reloadConfig();
            sender.sendMessage("§7[§cForceResourcePack§7]§8 Plugin reloaded !");
            return true;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if(target == null){
            sender.sendMessage("§cThis player cannot be found");
            return true;
        }
        if(sender instanceof Player){
            if(target.hasPermission("frp.bypass")){
                sender.sendMessage("§c"+target.getName()+" has permission to bypass your request !");
                return true;
            }
        }

        if(ForceResourcePack.getConfigUtils().getPack(args[1]) == null){
            sender.sendMessage("§cThis pack cannot be found !");
            return true;
        }
        target.setResourcePack(ForceResourcePack.getConfigUtils().getPack(args[1]));
        ForceResourcePack.getPlayers().put(target, System.currentTimeMillis());

        Bukkit.getScheduler().runTaskLater(ForceResourcePack.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player pls : ForceResourcePack.getPlayers().keySet()){
                    if(System.currentTimeMillis() - ForceResourcePack.getPlayers().get(pls) >= 10000){//10s
                        if(ForceResourcePack.getConfigUtils().getBoolean("kick")){
                            pls.kickPlayer(ForceResourcePack.getConfigUtils().getMessage(ConfigUtils.MessagesType.NOREPONSE));
                        }
                    }
                }
            }
        }, 200);//10s
        return false;
    }

    private void showHelp(CommandSender sender){
        sender.sendMessage("§e/frp <Player> <faithfull/myPack/...>§f: Set a resource pack for a player");
        sender.sendMessage("§e/frp reload§f: Reload the plugin configuration");
    }

}
