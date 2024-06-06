val ktVersion: String by project
val easylibVersion: String by project

plugins {
    java
    id("com.github.johnrengelman.shadow")
    id("com.xbaimiao.easylib")
    kotlin("jvm")
}

group = "com.xbaimiao.migrate"
version = "1.0.0"

easylib {
    env {
        mainClassName = "com.xbaimiao.migrate.MigrateKTG"
        pluginName = "MigrateKTG"
        kotlinVersion = ktVersion
    }
    version = easylibVersion

//    library("de.tr7zw:item-nbt-api:2.12.3", true){
//        relocate("de.tr7zw.changeme.nbtapi", "${project.group}.shadow.itemnbtapi")
//        repo("https://repo.codemc.org/repository/maven-public/")
//    }
//    library("redis.clients:jedis:5.0.1", true) {
//        relocate("redis.clients.jedis", "${project.group}.shadow.redis")
//    }
//    // jedis需要
//    library("org.apache.commons:commons-pool2:2.12.0", true){
//        relocate("org.apache.commons.pool2", "${project.group}.shadow.pool2")
//    }
//    library("com.zaxxer:HikariCP:4.0.3", true) {
//        relocate("com.zaxxer.hikari", "${project.group}.shadow.hikari")
//    }

//    val cloudOrmlite = true
//    library("com.j256.ormlite:ormlite-core:6.1", cloudOrmlite)
//    library("com.j256.ormlite:ormlite-jdbc:6.1", cloudOrmlite)
//    relocate("com.j256.ormlite", "${project.group}.shadow.ormlite", cloudOrmlite)

    relocate("com.xbaimiao.easylib", "${project.group}.easylib", false)
    relocate("com.cryptomorin.xseries", "${project.group}.shadow.xseries", true)
    relocate("kotlin", "${project.group}.shadow.kotlin", true)
    relocate("kotlinx", "${project.group}.shadow.kotlinx", true)
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    compileOnly("com.github.cryptomorin:XSeries:10.0.0")
    compileOnly(kotlin("stdlib-jdk8"))
//    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly(fileTree("libs"))
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    shadowJar {
        dependencies {
            easylib.library.forEach {
                if (it.cloud) {
                    exclude(dependency(it.id))
                }
            }
            exclude(dependency("org.slf4j:"))
            exclude(dependency("org.jetbrains:annotations:"))
            exclude(dependency("com.google.code.gson:gson:"))
            exclude(dependency("org.jetbrains.kotlin:"))
            exclude(dependency("org.jetbrains.kotlinx:"))
        }
        archiveClassifier.set("")
        easylib.getAllRelocate().forEach {
            relocate(it.pattern, it.replacement)
        }
        minimize()
    }

}
