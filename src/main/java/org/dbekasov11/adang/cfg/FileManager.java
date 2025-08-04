package org.dbekasov11.adang.cfg;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.dbekasov11.adang.ADang;

import java.io.File;

public class FileManager {

    private final ADang plugin;

    public FileManager(ADang plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration getShulkerConfig() {
        return getConfig("shulker.yml");
    }

    public FileConfiguration getSchemConfig() {
        return getConfig("schem.yml");
    }

    public FileConfiguration getWorldConfig() {
        return getConfig("world.yml");
    }

    public FileConfiguration getMessagesConfig() {
        return getConfig("messages.yml");
    }

    public FileConfiguration getKeyConfig() {
        return getConfig("key.yml");
    }

    public FileConfiguration getRegionConfig() {
        return getConfig("region.yml");
    }



    private FileConfiguration getConfig(String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);

        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }

        return YamlConfiguration.loadConfiguration(configFile);
    }
}