package com.xbaimiao.migrate.ktg4.deserialize

import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.part.FoodLevel
import com.xbaimiao.migrate.ktg4.KTGData
import com.xbaimiao.migrate.ktg4.deserialize.KTG4MigratorDeserializer
import org.bukkit.configuration.ConfigurationSection

/**
 * FoodLevelDeserializer
 *
 * @author xbaimiao
 * @since 2023/8/18 01:08
 */
class FoodLevelDeserializer : FoodLevel(), KTG4MigratorDeserializer {

    override fun deserialize(data: KTGData, configuration: ConfigurationSection) {
        if (!configuration.contains("FoodLevel")) {
            return
        }
//        if (!configuration.contains("Saturation")) {
//            return
//        }
        val foodLevel = configuration.getInt("FoodLevel")
//        val saturation = configuration.getDouble("Saturation").toFloat()
        data.food = foodLevel.toDouble()
//        player.setSaturation(saturation)
    }

}
