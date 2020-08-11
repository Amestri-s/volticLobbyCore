package me.adam.volticlobby.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemCreator {

    public void createGameIcon(Material material, Inventory inv, int Slot, String name, String type, String desc, String desc2) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ArrayList<String> Lore = new ArrayList<String>();
        Lore.add("§8" + type);
        Lore.add(" ");
        Lore.add("§f" + desc);
        Lore.add("§f" + desc2 + ".");
        Lore.add("");
        Lore.add("§a§lCLICK §r§fto play");
        meta.setLore(Lore);
        item.setItemMeta(meta);

        inv.setItem(Slot, item);

    }
}
