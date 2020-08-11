package me.adam.volticlobby.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class IllegalActions implements Listener {

    @EventHandler
    public void mobSpawn(EntitySpawnEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void damage(EntityDamageEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void rain(WeatherChangeEvent event){
        event.getWorld().setStorm(false);
        event.setCancelled(true);
    }

    @EventHandler
    public void foodLevelChange(FoodLevelChangeEvent event){
        event.setFoodLevel(20);
        event.setCancelled(true);
    }
}
