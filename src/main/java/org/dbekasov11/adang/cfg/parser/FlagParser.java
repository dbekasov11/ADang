package org.dbekasov11.adang.cfg.parser;


import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.FileManager;

import java.util.HashMap;
import java.util.Map;

public class FlagParser {

    public static Map<StateFlag, StateFlag.State> parseRegionFlags(ADang plugin) {
        Map<StateFlag, StateFlag.State> result = new HashMap<>();

        FileManager fileManager = new FileManager(plugin);
        FileConfiguration regionConfig = fileManager.getRegionConfig();
        ConfigurationSection section = regionConfig.getConfigurationSection("flags");
        if (section == null) return result;

        for (String key : section.getKeys(false)) {
            try {
                Flag<?> flag = Flags.fuzzyMatchFlag(WorldGuard.getInstance().getFlagRegistry(), key);

                if (flag instanceof StateFlag) {
                    StateFlag stateFlag = (StateFlag) flag;
                    String value = section.getString(key, "DENY").toUpperCase();
                    StateFlag.State state = StateFlag.State.valueOf(value);
                    result.put(stateFlag, state);
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Невозможно распознать флаг региона: " + key);
            }
        }

        return result;
    }
}

