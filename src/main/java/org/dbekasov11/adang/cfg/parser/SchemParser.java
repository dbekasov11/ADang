package org.dbekasov11.adang.cfg.parser;

import org.bukkit.configuration.file.FileConfiguration;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.FileManager;

public class SchemParser {
    public static boolean parseIgnoreAirBlocks(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration schemConfig = fileManager.getSchemConfig();
        return schemConfig.getBoolean("ignore-air-blocks", false);
    }

    public static int parseOffsetX(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration schemConfig = fileManager.getSchemConfig();
        return schemConfig.getInt("schem-offset.x", 0);
    }

    public static int parseOffsetY(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration schemConfig = fileManager.getSchemConfig();
        return schemConfig.getInt("schem-offset.y", 0);
    }

    public static int parseOffsetZ(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration schemConfig = fileManager.getSchemConfig();
        return schemConfig.getInt("schem-offset.z", 0);
    }
}