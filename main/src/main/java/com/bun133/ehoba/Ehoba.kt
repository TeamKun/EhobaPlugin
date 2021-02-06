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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ehoba extends JavaPlugin {
    public static final Material Ehoba = Material.COOKED_BEEF;

    @Override
    public void onEnable() {
        // Plugin startup logic
//        this.getServer().getPluginManager().registerEvents(new JoinListener(),this);
        this.getServer().getPluginManager().registerEvents(new CropSuperVisor(),this);
        Bukkit.addRecipe(Recipes.getEhoba(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

class JoinListener implements Listener {
    public static final String download_link = "https://www.dropbox.com/s/mqig9dffbov16b6/EhobaResourcePack.zip?dl=1";
    public static final String hash = "0ff388c6ddb3be0b73ecea97652b8017acafaf7d";

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().setResourcePack(download_link,hash);
    }
}

class CropSuperVisor implements Listener{
    @EventHandler
    public void onCropped(ItemSpawnEvent e){
        if(e.getEntity().getItemStack().getType().equals(Material.WHEAT)){
            ItemMeta m = e.getEntity().getItemStack().getItemMeta();
            m.setDisplayName("お米");
            e.getEntity().getItemStack().setItemMeta(m);
        }else if(e.getEntity().getItemStack().getType().equals(Material.COOKED_BEEF)){
            ItemMeta m = e.getEntity().getItemStack().getItemMeta();
            m.setDisplayName("エホバ巻き");
            e.getEntity().getItemStack().setItemMeta(m);
        }
    }
}

class Recipes{
    public static ShapedRecipe getEhoba(JavaPlugin plugin){
        ItemStack ehoba = new ItemStack(Ehoba.Ehoba,1);
        ItemMeta m = ehoba.getItemMeta();
        m.setDisplayName("エホバ巻き");
        ehoba.setItemMeta(m);

        NamespacedKey name = new NamespacedKey(plugin,"ehoba");

        ShapedRecipe recipe = new ShapedRecipe(name,ehoba);
        recipe.shape("WCW","WEW","WFW");
        recipe.setIngredient('W',Material.DRIED_KELP);
        recipe.setIngredient('C',Material.CARROT);
        recipe.setIngredient('E',Material.EGG);
        recipe.setIngredient('F',Material.WHEAT);

        return recipe;
    }
}