package org.dbekasov11.adang.cfg.parser;

import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.FileManager;

public class RegionParser {
    public static int parseRegionSizeX(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        return fileManager.getRegionConfig().getInt("region.size.x", 12);
    }

    public static int parseRegionSizeZ(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        return fileManager.getRegionConfig().getInt("region.size.z", 12);
    }

    public static String parseRegionNameFormat(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        return fileManager.getRegionConfig().getString("region.name_format", "dang_{name}");
    }
}