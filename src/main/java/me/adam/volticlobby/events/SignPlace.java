package me.adam.volticlobby.events;

import me.adam.volticlobby.main.MainClass;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignPlace implements Listener {
    private MainClass plugin = MainClass.getInstance();

    @EventHandler
    public void signPlace(SignChangeEvent event){
        if(event.getLine(0).equals("[join]")){
            String port = event.getLine(3);
            event.setLine(0, ChatColor.GREEN + "[Join Game]");
            event.setLine(1, ChatColor.GRAY + event.getLine(1));
            event.setLine(3, ChatColor.RED + "Offline");

            Location loc = event.getBlock().getLocation();
            plugin.getConfig().set("signLocations." + loc.getBlockX() + loc.getBlockY() + loc.getBlockZ() + ".location", loc);
            plugin.getConfig().set("signLocations." + loc.getBlockX() + loc.getBlockY() + loc.getBlockZ() + ".port", port);
            plugin.saveConfig();
        }
    }

}
