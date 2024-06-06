package com.xbaimiao.migrate.ktg4

import net.william278.husksync.HuskSync
import net.william278.husksync.data.BukkitData
import net.william278.husksync.data.DataSnapshot
import org.bukkit.inventory.ItemStack

/**
 * @author xbaimiao
 * @date 2024/6/6
 * @email owner@xbaimiao.com
 */
class KTGData {

    lateinit var uuid: String
    var food: Double = 20.0
    var health: Double = 20.0
    var maxHealth: Double = 20.0
    var expLevel: Int = 0
    var exp: Float = 0f
    lateinit var enderChest: Collection<ItemStack?>
    lateinit var inventory: Collection<ItemStack?>

    override fun toString(): String {
        return "KTGData(uuid='$uuid', food=$food, health=$health, expLevel=$expLevel, exp=$exp, enderChest=$enderChest, inventory=$inventory)"
    }

    fun convertedData(plugin: HuskSync): DataSnapshot.Packed? {
        return DataSnapshot.builder(plugin)
            .inventory(BukkitData.Items.Inventory.from(inventory.toTypedArray(), 0))
            .enderChest(BukkitData.Items.EnderChest.adapt(enderChest.toTypedArray()))
            .experience(BukkitData.Experience.from(exp.toInt(), expLevel, 0f))
            .gameMode(BukkitData.GameMode.from("SURVIVAL"))
            .saveCause(DataSnapshot.SaveCause.MPDB_MIGRATION)
            .buildAndPack()
    }

}
