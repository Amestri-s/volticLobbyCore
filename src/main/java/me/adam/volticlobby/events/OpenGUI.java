package me.adam.volticlobby.events;

import me.adam.volticlobby.main.MainClass;
import me.adam.volticlobby.utils.ItemCreator;
import me.adam.volticlobby.utils.MongoDB;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OpenGUI implements Listener {
    public ItemCreator itemCreator = new ItemCreator();
    MongoDB mongoDB = new MongoDB();


    private  DecimalFormat format = new DecimalFormat("#.00");

    @EventHandler
    public void playerInteract(PlayerInteractEvent event){

        ItemStack itemStack = event.getItem();
        //Selecter GUI
        if(itemStack != null){
            if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Select Game")){
                Inventory inventory = Bukkit.createInventory(null, 27, "Select Game");
                itemCreator.createGameIcon(Material.TNT, inventory, 11, "TNT Game", "MINIGAME", "Battle it out by", "blowing up players");
                itemCreator.createGameIcon(Material.SPAWNER, inventory, 13, "Beasts", "MINIGAME", "Collect keys and", "destroy beasts");
                itemCreator.createGameIcon(Material.DIAMOND_SWORD, inventory, 15, "Murder Mystery","MINIGAME", "Become a detective solving crime", "or lurk around causing chaos.");
                itemCreator.createFillers(inventory);
                event.getPlayer().openInventory(inventory);
            }else if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Your Profile")){
                Inventory inventory = Bukkit.createInventory(null, 54, event.getPlayer().getDisplayName());

                //PLAYER HEAD
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);

                SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
                skullMeta.setOwningPlayer(event.getPlayer());
                skullMeta.setDisplayName("§aYour Profile");

                ArrayList<String> lore = new ArrayList<String>();
                lore.add("§fView your profile here.");
                lore.add(" ");
                lore.add("§fLast played: §a" + mongoDB.getPlayed(event.getPlayer().getUniqueId()));
                lore.add("§fFirst joined: §a" + mongoDB.getJoined(event.getPlayer().getUniqueId()));
                skullMeta.setLore(lore);

                playerHead.setItemMeta(skullMeta);

                //XP
                ItemStack xp = new ItemStack(Material.EXPERIENCE_BOTTLE);
                int experience = mongoDB.getXP(event.getPlayer().getUniqueId());

                ArrayList<String> xpLore = new ArrayList<String>();
                ItemMeta xpMeta = xp.getItemMeta();
                xpLore.add("§fYou can gain network xp");
                xpLore.add("§fon VolticMC by playing");
                xpLore.add("§fgames. Unlock levels for");
                xpLore.add("§fcool perks.");
                xpLore.add(" ");
                xpLore.add("§fTotal XP:§e " + experience);
                xpLore.add(" ");
                xpLore.add("§fLevel:§e " + Math.floor(experience/2000));
                xpLore.add("§e" + experience + "/" + (((Math.floor(experience/2000))+1)*2000));
                xpLore.add(" ");

                String greenBar = "";
                String redBar = "";
                int bars = 50;
                int barPercentage = 2;
                double percentage = (experience/(((Math.floor(experience/2000))+1)*2000)) * 100;
                double finalPercentage = percentage;

                while(percentage > barPercentage){
                    percentage -= barPercentage;
                    greenBar += "|";
                    bars -= 1;
                }

                while(bars > 0){
                    redBar += "|";
                    bars -= 1;
                }

                xpLore.add("§a" + greenBar + "§8" + redBar + "§e " + format.format(finalPercentage) + "%");
                xpLore.add("§e" + ((((experience/2000)+1)*2000) - experience) + "§f XP until next level");

                xpMeta.setLore(xpLore);
                xpMeta.setDisplayName("§eNetwork XP");
                xp.setItemMeta(xpMeta);
                inventory.setItem(4, playerHead);
                inventory.setItem(21, xp);

                itemCreator.createFillers(inventory);
                event.getPlayer().openInventory(inventory);
            }
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {

        if(event.getView().getTitle().equals("Select Game")){
          if(event.getCursor().getType().equals(Material.TNT)){
              event.getWhoClicked().closeInventory();
          }
          event.setCancelled(true);
        }else if(event.getWhoClicked().getGameMode().equals(GameMode.ADVENTURE)){
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if(event.getItemDrop().getItemStack().getType().equals(Material.COMPASS) || event.getItemDrop().getItemStack().getType().equals(Material.PLAYER_HEAD)){
            event.setCancelled(true);
        }
    }

}
