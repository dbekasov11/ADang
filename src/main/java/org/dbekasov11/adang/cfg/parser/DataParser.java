package org.dbekasov11.adang.cfg.parser;

import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.FileManager;
import org.dbekasov11.adang.data.DangData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class DataParser {
    private static final Set<String> POST_1_16_5_BIOMES = new HashSet<>(Arrays.asList(
            "WOODED_BADLANDS",
            "WINDSWEPT_HILLS",
            "WINDSWEPT_GRAVELLY_HILLS",
            "STONY_PEAKS",
            "MEADOW",
            "GROVE",
            "SNOWY_SLOPES",
            "JAGGED_PEAKS",
            "FROZEN_PEAKS",
            "CHERRY_GROVE"
    ));

    public static List<DangData> parseDangData(ADang plugin) {
        List<DangData> dangDataList = new ArrayList<>();
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration worldConfig = fileManager.getWorldConfig();

        ConfigurationSection dangsSection = worldConfig.getConfigurationSection("dang");
        if (dangsSection == null) {
            plugin.getLogger().warning("Раздел 'dang' отсутствует в world.yml.");
            return dangDataList;
        }

        Iterator<String> keys = dangsSection.getKeys(false).iterator();

        while (keys.hasNext()) {
            String key = keys.next();
            ConfigurationSection dangSection = dangsSection.getConfigurationSection(key);

            if (dangSection == null) {
                plugin.getLogger().warning("Некорректный формат данных для ключа: " + key);
                continue;
            }

            List<Biome> biomes = new ArrayList<>();
            String[] biomeStrings = dangSection.getString("biome", "").split(";");
            for (String biomeString : biomeStrings) {
                String biomeName = biomeString.trim().toUpperCase();

                if (POST_1_16_5_BIOMES.contains(biomeName)) {
                    continue;
                }

                try {
                    Biome biome = Biome.valueOf(biomeName);
                    biomes.add(biome);
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Недопустимый биом в конфигурации: " + biomeString);
                }
            }

            String fileName = dangSection.getString("fileName");
            String world = dangSection.getString("world");

            if (fileName == null || world == null) {
                plugin.getLogger().warning("Не указаны fileName или world для ключа: " + key);
                continue;
            }

            dangDataList.add(new DangData(fileName, world, biomes));
        }

        return dangDataList;
    }
}