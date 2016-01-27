package fr.vinetos.frp.utils;

import fr.vinetos.frp.ForceResourcePack;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created by VINETOS on 26/12/2015.
 */
public class ConfigUtils {

    protected FileConfiguration config;

    public ConfigUtils(FileConfiguration config){
        this.config = config;
    }

    public void reloadConfig(){
        config = YamlConfiguration.loadConfiguration(new File(ForceResourcePack.getInstance().getDataFolder(), "config.yml"));
    }

    public boolean isValid(String path){
        if(config.get(path) == null){
            return false;
        }
        return true;
    }

    public boolean getBoolean(String name){
        if(isValid(name)){
            return config.getBoolean(name);
        }
        return false;
    }

    public String getMessage(MessagesType message){
        if(isValid("messages." + message.getName())){
            return config.getString("messages." + message.getName()).replaceAll("&", "§");
        }
        return null;
    }

    public String getPack(String name){
        if(isValid("packs." + name)){
            return config.getString("packs." + name);
        }
        return null;
    }

    public String getJoinPack(){
        if(isValid("join-pack") && isValid("packs." + config.getString("join-pack"))){
            return config.getString("packs." + config.getString("join-pack"));
        }
        return null;
    }

    public String getWorldResourcePack(String world){
        if(isValid("worlds." + world) && isValid("packs." + config.getString("worlds." + world))){
            return config.getString("packs." + config.getString("worlds." + world));
        }
        return null;
    }

    public enum MessagesType{
        ACCEPTED("accepted"),
        DECLINED("declined"),
        FAILED("failed"),
        NOREPONSE("no-reponse"),
        SUCCESS("success");

        private String name;

        private MessagesType(String name){
            this.name = name;
        }

        public String getName(){
            return this.name;
        }

    }

}

