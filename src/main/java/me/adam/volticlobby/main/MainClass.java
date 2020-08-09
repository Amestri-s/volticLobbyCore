package me.adam.volticlobby.main;

import me.adam.volticlobby.events.SignBreak;
import me.adam.volticlobby.events.SignInteractEvent;
import me.adam.volticlobby.events.SignPlace;
import me.adam.volticlobby.utils.PluginMessage;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {
    private static MainClass instance;

    public void onEnable() {
        getLogger().info("Loading");
        setInstance(this);
        loadConfig();
        getServer().getPluginManager().registerEvents(new SignInteractEvent(), this);
        getServer().getPluginManager().registerEvents(new SignPlace(), this);
        getServer().getPluginManager().registerEvents(new SignBreak(), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessage());

        getLogger().info("Loaded");
    }

    public static MainClass getInstance(){
        return instance;
    }

    public static void setInstance(MainClass instance){
        MainClass.instance = instance;
    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void beginStatusCheck(){

    }
}
