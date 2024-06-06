package com.xbaimiao.migrate.ktg4.deserialize

import com.xbaimiao.migrate.ktg4.KTGData
import org.bukkit.configuration.ConfigurationSection

/**
 * KTG4MigratorDeserializer
 *
 * @author xbaimiao
 * @since 2023/8/18 00:57
 */
interface KTG4MigratorDeserializer {

    fun key(): String

    fun deserialize(data: KTGData, configuration: ConfigurationSection)

    companion object {

        val all: List<KTG4MigratorDeserializer> by lazy {
            listOf(
                HealthDeserializer(),
                FoodLevelDeserializer(),
                InventoryDeserializer(),
                ExperienceDeserializer(),
                EnderChestDeserializer()
            )
        }

    }

}
