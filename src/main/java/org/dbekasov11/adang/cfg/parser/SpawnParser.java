package org.dbekasov11.adang.cfg.parser;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.Config;
import org.dbekasov11.adang.cfg.FileManager;

public class SpawnParser {
    public static void parseSpawn(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration worldConfig = fileManager.getWorldConfig();

        ConfigurationSection spawnSection = worldConfig.getConfigurationSection("spawn");
        if (spawnSection != null) {
            Config.Spawn.minx = spawnSection.getInt("minx", -2000);
            Config.Spawn.maxx = spawnSection.getInt("maxx", 2000);
            Config.Spawn.minz = spawnSection.getInt("minz", -2000);
            Config.Spawn.maxz = spawnSection.getInt("maxz", 2000);
        } else {
            plugin.getLogger().warning("Раздел 'spawn' отсутствует в world.yml, используются значения по умолчанию");
            Config.Spawn.minx = -2000;
            Config.Spawn.maxx = 2000;
            Config.Spawn.minz = -2000;
            Config.Spawn.maxz = 2000;
        }
    }
}