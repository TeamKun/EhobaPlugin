package com.bun133.ehoba;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BoundingBox;

public final class Ehoba extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new JoinListener(),this);
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