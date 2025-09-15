import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    java
    id("de.eldoria.plugin-yml.paper") version "0.7.1"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("com.gradleup.shadow") version "9.0.0-beta12"
}

group = "de.hiorcraft.nex"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.codemc.io/repository/maven-releases/")
    }
    maven {
        url = uri("https://repo.codemc.io/repository/maven-snapshots/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")
    compileOnly("dev.jorel:commandapi-bukkit-core:10.1.1")
}

paper {
    main = "de.horcraft.nex.StopTimerMain"
    apiVersion = "1.21"

    serverDependencies {
        register("CommandAPI") {
            required = true
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
        }
    }
}

tasks {
    runServer {
        minecraftVersion("1.21.7")

        downloadPlugins {
            hangar("commandapi", "10.1.1")
        }
    }
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}