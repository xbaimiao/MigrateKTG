package com.xbaimiao.migrate.ktg4.deserialize

import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.part.Experience
import com.xbaimiao.migrate.ktg4.KTGData
import com.xbaimiao.migrate.ktg4.deserialize.KTG4MigratorDeserializer
import org.bukkit.configuration.ConfigurationSection

/**
 * ExperienceDeserializer
 *
 * @author xbaimiao
 * @since 2023/8/18 01:17
 */
class ExperienceDeserializer : Experience(), KTG4MigratorDeserializer {

    override fun deserialize(data: KTGData, configuration: ConfigurationSection) {
        if (configuration.contains("Level")) {
            data.expLevel = configuration.getInt("Level")
        } else {
            data.expLevel = 1
        }
        if (configuration.contains("Exp")) {
            data.exp = configuration.getDouble("Exp").toFloat()
        } else {
            data.exp = 0f
        }

    }

}
