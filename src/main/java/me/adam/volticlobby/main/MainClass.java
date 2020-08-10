package me.adam.volticlobby.main;

import me.adam.volticlobby.events.PlayerJoin;
import me.adam.volticlobby.events.SignBreak;
import me.adam.volticlobby.events.SignInteractEvent;
import me.adam.volticlobby.events.SignPlace;
import me.adam.volticlobby.utils.PluginMessage;
import me.adam.volticlobby.utils.Server;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MainClass extends JavaPlugin {
    private static MainClass instance;
    private Plugin plugin = this;

    public void onEnable() {
        getLogger().info("Loading");
        setInstance(this);
        loadConfig();
        getServer().getPluginManager().registerEvents(new SignInteractEvent(), this);
        getServer().getPluginManager().registerEvents(new SignPlace(), this);
        getServer().getPluginManager().registerEvents(new SignBreak(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessage());
        beginStatusCheck();

        getLogger().info("Loaded");
    }

    public void onDisable(){
        getLogger().info("Goodbye");
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
        new BukkitRunnable(){
            @Override
            public void run(){

                ConfigurationSection signLocations = getConfig().getConfigurationSection("signLocations");
                signLocations.getKeys(false).forEach(signId -> {

                    ConfigurationSection signConfig = signLocations.getConfigurationSection(signId);

                    String portFetched = (String) signConfig.get("port");
                    int port = Integer.parseInt(portFetched);

                    Location location = signConfig.getLocation("location");

                    Server server = new Server("127.0.0.1", port);
                    String data = server.parseData(Server.Connection.MOTD);
                    String players = server.parseData(Server.Connection.PLAYERS_ONLINE);

                    Bukkit.getScheduler().runTask(plugin, () -> {
                        Block block = location.getBlock();
                        Sign sign = (Sign) block.getState();

                        if(data.equals("Offline")){
                            sign.setLine(3, "§c§lUNAVAILABLE");
                            sign.setLine(0, ChatColor.RED + "[Join Game]");
                        }else if(data.equals("IN GAME")){
                            sign.setLine(3, "§6§lIN GAME");
                            sign.setLine(0, ChatColor.RED + "[Join Game]");
                        }else {
                            sign.setLine(0, ChatColor.GREEN + "[Join Game]");
                            sign.setLine(3, "§a§n" + players);
                        }

                        sign.update(true);
                    });

                });
            }
        }.runTaskTimerAsynchronously(this, 0, 20);
    }
}
