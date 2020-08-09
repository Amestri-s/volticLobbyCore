package me.adam.volticlobby.events;

import me.adam.volticlobby.main.MainClass;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SignBreak implements Listener {
    private MainClass plugin = MainClass.getInstance();

    @EventHandler
    public void blockBreak(BlockBreakEvent event){
        Block block = event.getBlock();

        if(block.getState() instanceof Sign){
            Sign sign = (Sign) block.getState();

            if(sign.getLine(0).equals(ChatColor.GREEN + "[Join Game]") || sign.getLine(0).equals(ChatColor.RED + "[Join Game]")){
                Location loc = block.getLocation();
                plugin.getConfig().set("signLocations."+ loc.getBlockX() + loc.getBlockY() + loc.getBlockZ(), null);
                plugin.saveConfig();
            }
        }
    }
}
