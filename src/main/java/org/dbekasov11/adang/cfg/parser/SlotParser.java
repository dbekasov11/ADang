package org.dbekasov11.adang.cfg.parser;

import org.bukkit.configuration.file.FileConfiguration;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.FileManager;

public class SlotParser {

    public static int parseMinItemsInShulker(ADang plugin) {
        FileConfiguration config = new FileManager(plugin).getShulkerConfig();
        return config.getInt("shulker.minItems", 4);
    }

    public static int parseMaxItemsInShulker(ADang plugin) {
        FileConfiguration config = new FileManager(plugin).getShulkerConfig();
        return config.getInt("shulker.maxItems", 10);
    }
}