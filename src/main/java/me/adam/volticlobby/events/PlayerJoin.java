package me.adam.volticlobby.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class PlayerJoin implements Listener {

    BossBar bar = Bukkit.createBossBar("§a§lVOLTIC MC §f- CUSTOM MINIGAMES", BarColor.GREEN, BarStyle.SOLID);

    @EventHandler
    public void joinServer(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);

        //Select game item
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
        lore.add("§a§lRIGHT CLICK §r§ato open");

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        player.getInventory().setItem(1, itemStack);
        player.getInventory().setHeldItemSlot(1);

        //Global stats item
        ItemStack itemStack1 = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemStack1.getItemMeta();

        skullMeta.setOwningPlayer(player);
        skullMeta.setDisplayName(ChatColor.GREEN + "Your Profile");

        ArrayList<String> lore1 = new ArrayList<String>();
        lore1.add(" ");
        lore1.add("§fClick me to view");
        lore1.add("§fglobal stats, settings");
        lore1.add("§fand more.");
        lore1.add(" ");
        lore1.add("§a§lRIGHT CLICK §r§ato open");

        skullMeta.setLore(lore1);
        itemStack1.setItemMeta(skullMeta);

        player.getInventory().setItem(2, itemStack1);

        player.setGameMode(GameMode.ADVENTURE);
        player.setPlayerListHeader("§a§lVOLTIC MC");
        player.setPlayerListFooter("§aIP: §fplay.volticmc.net");
        player.setPlayerListHeaderFooter("         §a§lVOLTIC MC         ", "         §aIP: §fplay.volticmc.net         ");
        event.setJoinMessage("");
        bar.addPlayer(player);


    }
}
