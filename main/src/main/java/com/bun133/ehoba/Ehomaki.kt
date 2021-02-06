package com.bun133.ehoba

import com.destroystokyo.paper.Title
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import kotlin.math.abs
import kotlin.math.floor

class Ehomaki : Listener {
    /**
     * players holding Ehoumaki.
     */
    val holdingPlayer = mutableListOf<Player>()

    /**
     * Int value is show Title duration
     */
    val showingPlayer = mutableMapOf<Player, Pair<Int, String>>()

    /**
     * Stored Player will be death in ticks.
     * The DeathMessage have to be edited version.
     */
    val noLongerAlive = mutableListOf<Player>()

    @EventHandler
    fun onHandChange(e: PlayerItemHeldEvent) {
//        if (e.player.inventory.itemInMainHand.type === Ehoba.Ehoba) {
//            if (!holdingPlayer.contains(e.player)) {
//                holdingPlayer.add(e.player)
//            }
//        } else if (holdingPlayer.contains(e.player)) {
//            holdingPlayer.remove(e.player)
//        }
    }

    @EventHandler
    fun onAte(e: PlayerItemConsumeEvent) {
        if (e.item.type === Ehoba.Ehoba) {
            onEhoumaki(e.player)
        }
    }

    /**
     * When player have eaten Ehoumaki,
     * This will process the direction.
     */
    fun onEhoumaki(p: Player) {
        if (inDirection(p.location.yaw)) {
            showingPlayer[p] = Pair(showDuration, "おいしく食べられました!")
//            p.updateTitle(Title("おいしく食べられました!"))
        } else {
            showingPlayer[p] = Pair(showDuration, "" + ChatColor.RED + ChatColor.BOLD + "非国民め!")
//            p.updateTitle(Title("" + ChatColor.RED + ChatColor.BOLD + "非国民め!"))
            noLongerAlive.add(p)
            p.health = 0.0
        }
    }

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        if (noLongerAlive.contains(e.entity)) {
            e.deathMessage = "${e.entity.displayName}は非国民だったので殺されてしまった"
        }
    }

    val showDuration = 40

    /**
     * Each Tick,this function will be called.
     * This will send Titles to player holding Ehoumaki.
     */
    fun onTick() {
        Bukkit.getOnlinePlayers().filter {
            it.inventory.itemInMainHand.type === Ehoba.Ehoba
        }.forEach {
            it.updateTitle(
                Title(
                    showingPlayer.getOrDefault(it, Pair(showDuration, "")).second,
                    "正しい方向を向いて食べよう!",
                    0,
                    2,
                    0
                )
            )
            if (showingPlayer.containsKey(it)) {
                showingPlayer[it] = Pair(showingPlayer[it]!!.first - 1, showingPlayer[it]!!.second)
                if (showingPlayer[it]!!.first <= 0) {
                    showingPlayer.remove(it)
                }
            }
            it.sendActionBar(getDirectionMessage(it.location.yaw))
        }
    }
}

/**
 * This value is the direction of eating ehoumaki.
 *
 */
const val ehouDirection = 0.0f
const val range = 50

fun inDirection(yaw: Float): Boolean {
    return ehouDirection - range < yaw && yaw < ehouDirection + range + 360
}

/**
 * this value is how sensitive direction message is.
 * e.g. it is 50,each 50 deg,message arrow is be increased.
 */
const val messageSensitivity = 25
fun getDirectionMessage(yaw: Float): String {
    if (ehouDirection == yaw) {
        return "ここ!"
    }
    var s = ""
    s = if (ehouDirection > yaw) {
        ">"
    } else {
        "<"
    }
    var text = ""
    for (i in 0..floor((abs(ehouDirection - yaw)) % 360 / messageSensitivity).toInt()) {
        text += s
    }
//    text += " yaw:$yaw direction:${abs(ehouDirection - yaw) % 360}"
    return text
}