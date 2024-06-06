package com.xbaimiao.migrate

import com.xbaimiao.easylib.EasyPlugin
import com.xbaimiao.easylib.command.command
import com.xbaimiao.easylib.util.info
import com.xbaimiao.migrate.ktg4.KnapsackToGo4Migrator
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.util.concurrent.Executors

@Suppress("unused")
class MigrateKTG : EasyPlugin() {

    private val migrateThread = Executors.newSingleThreadExecutor()

    override fun enable() {
        command<CommandSender>("migratektg") {
            description = "迁移ktg数据到husksync"
            permission = "migratektg.command"
            exec {
                info("迁移ktg数据到husksync")
                if (Bukkit.getOnlinePlayers().isNotEmpty()) {
                    error("请确保服务器没有玩家在线")
                    return@exec
                }
                migrateThread.submit {
                    KnapsackToGo4Migrator(sender).migratorAll()
                }
            }
        }.register()
    }

}
