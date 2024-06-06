package com.xbaimiao.migrate.ktg4.deserialize

import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.part.Health
import com.xbaimiao.migrate.ktg4.KTGData
import com.xbaimiao.migrate.ktg4.deserialize.KTG4MigratorDeserializer
import org.bukkit.configuration.ConfigurationSection

/**
 * HealthDeserializer
 *
 * @author xbaimiao
 * @since 2023/8/18 00:58
 */
class HealthDeserializer : Health(true), KTG4MigratorDeserializer {

    override fun deserialize(data: KTGData, configuration: ConfigurationSection) {
        if (configuration.contains("MaxHealth")) {
            val maxHealth = configuration.getDouble("MaxHealth")
            data.maxHealth = maxHealth
        }
        if (configuration.contains("Health")) {
            var health = configuration.getDouble("Health")
            if (health < 1) {
                health = 1.0
            }
            data.health = health
        }
    }

}
