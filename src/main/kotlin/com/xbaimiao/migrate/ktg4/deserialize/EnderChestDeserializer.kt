package com.xbaimiao.migrate.ktg4.deserialize

import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.part.EnderChest
import com.xbaimiao.migrate.ktg4.KTGData
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack

/**
 * EnderChestDeserializer
 *
 * @author xbaimiao
 * @since 2023/8/18 01:21
 */
class EnderChestDeserializer : EnderChest(), KTG4MigratorDeserializer {

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
        data.enderChest = all.toMutableList()
    }

}
