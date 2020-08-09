package me.adam.volticlobby.events;

import me.adam.volticlobby.main.MainClass;
import me.adam.volticlobby.utils.PluginMessage;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignInteractEvent implements Listener {
    private PluginMessage pluginMessage = new PluginMessage();

    @EventHandler
    public void signInteract(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();

        if(block != null && block.getState() instanceof Sign){
            Sign sign = (Sign) block.getState();

            if(sign.getLine(0).equals(ChatColor.GREEN + "[Join Game]")){
                pluginMessage.connect(player, sign.getLine(2));
            }

        }
    }
}
