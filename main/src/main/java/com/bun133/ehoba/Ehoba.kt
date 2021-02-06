package com.bun133.ehoba

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.Bukkit
import java.lang.Runnable
import org.bukkit.Material
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.entity.ItemSpawnEvent
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ItemStack
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class Ehoba : JavaPlugin() {
    var ehomaki: Ehomaki? = null
    override fun onEnable() {
        // Plugin startup logic
//        this.getServer().getPluginManager().registerEvents(new JoinListener(),this);
        server.pluginManager.registerEvents(CropSuperVisor(), this)
        Bukkit.addRecipe(Recipes.getEhoba(this))
        ehomaki = Ehomaki()
        server.pluginManager.registerEvents(ehomaki!!, this)
        server.scheduler.runTaskTimer(this, Runnable { ehomaki!!.onTick() }, 10, 1)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object {
        val Ehoba = Material.COOKED_BEEF
    }
}

internal class JoinListener : Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        e.player.setResourcePack(download_link, hash)
    }

    companion object {
        const val download_link = "https://www.dropbox.com/s/mqig9dffbov16b6/EhobaResourcePack.zip?dl=1"
        const val hash = "0ff388c6ddb3be0b73ecea97652b8017acafaf7d"
    }
}

internal class CropSuperVisor : Listener {
    @EventHandler
    fun onCropped(e: ItemSpawnEvent) {
        if (e.entity.itemStack.type == Material.WHEAT) {
            val m = e.entity.itemStack.itemMeta
            m.setDisplayName("お米")
            e.entity.itemStack.setItemMeta(m)
        } else if (e.entity.itemStack.type == Material.COOKED_BEEF) {
            val m = e.entity.itemStack.itemMeta
            m.setDisplayName("エホバ巻き")
            e.entity.itemStack.itemMeta = m
        }
    }
}

internal object Recipes {
    fun getEhoba(plugin: JavaPlugin?): ShapedRecipe {
        val ehoba = ItemStack(Ehoba.Ehoba, 1)
        val m = ehoba.itemMeta
        m.setDisplayName("エホバ巻き")
        ehoba.itemMeta = m
        val name = NamespacedKey(plugin!!, "ehoba")
        val recipe = ShapedRecipe(name, ehoba)
        recipe.shape("WCW", "WEW", "WFW")
        recipe.setIngredient('W', Material.DRIED_KELP)
        recipe.setIngredient('C', Material.CARROT)
        recipe.setIngredient('E', Material.EGG)
        recipe.setIngredient('F', Material.WHEAT)
        return recipe
    }
}