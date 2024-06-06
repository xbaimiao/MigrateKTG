package com.xbaimiao.migrate.ktg4

import cn.jja8.knapsackToGo4.all.work.PlayerDataCase
import cn.jja8.knapsackToGo4.all.work.playerDataCase.file.FileDataCase
import cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql.MysqlDataCase
import cn.jja8.knapsackToGo4.all.work.playerDataCase.sqlite.SqliteDataCase
import com.xbaimiao.easylib.reflex.Reflex.Companion.getProperty
import java.io.File

/**
 * KTG4Util
 *
 * @author xbaimiao
 * @since 2023/8/18 00:26
 */
object KTG4Util {

    class KTG4Data(val data: ByteArray, val uuid: String)

    fun getAllUserData(dataCase: PlayerDataCase): List<KTG4Data> {
        when (dataCase) {
            is FileDataCase -> {
                val dataFile = dataCase.getProperty<File>("dataFile") ?: error("dataFile is null")

                return dataFile.listFiles()
                    ?.filter { it.name.endsWith(".dat") }
                    ?.mapNotNull { runCatching { it.name.removeSuffix(".dat") to it }.getOrNull() }
                    ?.mapNotNull { runCatching { it.second.readBytes() to it.first }.getOrNull() }
                    ?.map { KTG4Data(it.first, it.second) }
                    ?: emptyList()
            }

            is MysqlDataCase -> {
                dataCase.connection.use { connection ->
                    connection.prepareStatement("SELECT * FROM PlayerData").use { statement ->
                        statement.executeQuery().use { resultSet ->
                            val list = ArrayList<KTG4Data>()
                            while (resultSet.next()) {
                                list.add(
                                    KTG4Data(
                                        resultSet.getBytes("Data"),
                                        resultSet.getString("PlayerUUID")
                                    )
                                )
                            }
                            return list
                        }
                    }
                }
            }

            is SqliteDataCase -> {
                dataCase.connection.use { connection ->
                    connection.prepareStatement("SELECT * FROM PlayerData").use { statement ->
                        statement.executeQuery().use { resultSet ->
                            val list = ArrayList<KTG4Data>()
                            while (resultSet.next()) {
                                list.add(
                                    KTG4Data(
                                        resultSet.getBytes("Data"),
                                        resultSet.getString("PlayerUUID")
                                    )
                                )
                            }
                            return list
                        }
                    }
                }
            }

            else -> error("未知的PlayerDataCase类型")
        }
    }

}
