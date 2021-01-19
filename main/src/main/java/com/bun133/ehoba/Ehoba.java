package com.bun133.ehoba;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ehoba extends JavaPlugin {
    public static final Material Ehoba = Material.COOKED_BEEF;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new JoinListener(),this);
        Bukkit.addRecipe(Recipes.getEhoba(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

class JoinListener implements Listener {
    public static final String download_link = "";
    public static final String hash = "";

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().setResourcePack(download_link,hash);
    }
}

class CropSuperVisor implements Listener{
    @EventHandler
    public void onCropped(ItemSpawnEvent e){
        if(e.getEntity().getItemStack().getType().equals(Material.WHEAT)){
            e.getEntity().getItemStack().getItemMeta().setDisplayName("お米");
        }
    }
}

class Recipes{
    public static ShapedRecipe getEhoba(JavaPlugin plugin){
        ItemStack ehoba = new ItemStack(Ehoba.Ehoba,1);

        NamespacedKey name = new NamespacedKey(plugin,"ehoba");

        ShapedRecipe recipe = new ShapedRecipe(name,ehoba);
        recipe.shape("RRR","FFF","RRR");
        recipe.setIngredient('R',Material.WHEAT);
        recipe.setIngredient('F',Material.WHEAT);

        return recipe;
    }
}