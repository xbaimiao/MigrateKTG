package com.xbaimiao.migrate.ktg4.deserialize

import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.part.Inventory
import com.xbaimiao.migrate.ktg4.KTGData
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack

/**
 * InventoryDeserializer
 *
 * @author xbaimiao
 * @since 2023/8/18 01:10
 */
class InventoryDeserializer : Inventory(), KTG4MigratorDeserializer {

    override fun deserialize(data: KTGData, configuration: ConfigurationSection) {
        if (!configuration.contains("Len")) {
            return
        }
        val len = configuration.getInt("Len")
        val itemStack = configuration.getConfigurationSection("ItemStack")
            ?: return

        val all = arrayOfNulls<ItemStack>(len)
        for (i in all.indices) {
            all[i] = itemStack.getItemStack(i.toString())
        }

//        val playerInventory = Bukkit.createInventory(null, InventoryType.PLAYER, "inventory")
//        playerInventory.contents = all.toMutableList().mapNotNull { it }.toTypedArray()
        data.inventory = all.toMutableList() //InvSync.plugin.itemSerializer.serializerInventory(playerInventory)
    }

}
