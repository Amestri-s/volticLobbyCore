package me.adam.volticlobby.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PlayerJoin implements Listener {

    @EventHandler
    public void joinServer(PlayerJoinEvent event){
        Player player = event.getPlayer();

        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(ChatColor.GREEN + "Select Game");
        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        ArrayList<String> lore = new ArrayList<String>();
        lore.add(" ");
        lore.add("§fThis compass can be");
        lore.add("§fused to select a game");
        lore.add("§fto play on §aVolticMC§f.");
        lore.add(" ");
        lore.add("§a§lRIGHT CLICK to use");

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        player.getInventory().setItem(4, itemStack);
        player.getInventory().setHeldItemSlot(4);


    }
}
