package com.xbaimiao.migrate.ktg4

import cn.jja8.knapsackToGo4.bukkit.PlayerData
import com.xbaimiao.easylib.util.warn
import com.xbaimiao.migrate.ktg4.deserialize.KTG4MigratorDeserializer
import net.william278.husksync.api.HuskSyncAPI
import net.william278.husksync.user.User
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * KnapsackToGo4Migrator
 *
 * @author xbaimiao
 * @since 2023/8/18 00:20
 */
class KnapsackToGo4Migrator(private val sender: CommandSender) {

    init {
        if (Bukkit.getPluginManager().getPlugin("KnapsackToGo4") == null) {
            sender.sendMessage("§cKnapsackToGo4插件未安装")
            error("KnapsackToGo4插件未安装")
        }
    }

    fun migratorAll() {
        runCatching {
            val plugin = HuskSyncAPI.getInstance().plugin
            val database = plugin.database

            val dataCast = PlayerData.playerDataCase

            val allPlayers = KTG4Util.getAllUserData(dataCast)
            sender.sendMessage("§a正在迁移 ${allPlayers.size} 个玩家的数据")

            for ((index, ktG4Data) in allPlayers.withIndex()) {
                val ymlString = String(ktG4Data.data, StandardCharsets.UTF_8)
                val yamlConfiguration = YamlConfiguration()
                try {
                    yamlConfiguration.load(ymlString.reader())
                } catch (th: Throwable) {
                    th.printStackTrace()
                    warn("在迁移第 ${index + 1} 个玩家数据的时候 玩家uuid为${ktG4Data.uuid} 发生了错误 可能是数据损坏 跳过加载")
                    continue
                }

                val data = KTGData()

                // 无需迁移的数据
                data.uuid = ktG4Data.uuid

                // 需要迁移的数据
                for (ktG4MigratorDeserializer in KTG4MigratorDeserializer.all) {
                    val key = ktG4MigratorDeserializer.key()
                    val part = yamlConfiguration.getConfigurationSection(key) ?: continue
                    ktG4MigratorDeserializer.deserialize(data, part)
                }

                val uuid = UUID.fromString(data.uuid)
                val offlinePlayer = Bukkit.getOfflinePlayer(uuid)
                if (offlinePlayer.name == null) {
                    warn("玩家uuid为${data.uuid}的玩家名字获取失败 跳过迁移")
                    return
                }
                val user = User(uuid, offlinePlayer.name)
                database.ensureUser(user)
                database.addSnapshot(user, data.convertedData(plugin))
                sender.sendMessage("数据迁移进度 ${index + 1} / ${allPlayers.size}")
            }

            sender.sendMessage("§a数据迁移完成")
        }.onFailure {
            it.printStackTrace()
            warn("迁移失败,请联系作者")
        }
    }

}
