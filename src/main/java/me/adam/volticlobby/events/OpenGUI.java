package me.adam.volticlobby.events;

import me.adam.volticlobby.utils.ItemCreator;
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
import org.bukkit.inventory.ItemStack;

public class OpenGUI implements Listener {
    public ItemCreator itemCreator = new ItemCreator();

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
            }else if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "")){

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
