package org.dbekasov11.adang.cfg.parser;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.FileManager;

public class SoundParser {

    public static Sound parseOpenSound(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration shulkerConfig = fileManager.getShulkerConfig();
        String soundName = shulkerConfig.getString("shulker.openSound", "ENTITY_GENERIC_EXTINGUISH_FIRE");
        return parseSound(soundName, Sound.UI_TOAST_CHALLENGE_COMPLETE, plugin);
    }

    public static Sound parseLockedSound(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration shulkerConfig = fileManager.getShulkerConfig();
        String soundName = shulkerConfig.getString("shulker.lockedSound", "BLOCK_BARREL_CLOSE");
        return parseSound(soundName, Sound.BLOCK_CHEST_LOCKED, plugin);
    }

    private static Sound parseSound(String soundName, Sound defaultSound, ADang plugin) {
        if (soundName == null || soundName.isEmpty()) {
            return defaultSound;
        }

        soundName = soundName.trim().toUpperCase().replace(" ", "_");

        try {
            return Sound.valueOf(soundName);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Недопустимый тип звука в конфигурации: " + soundName);
            return defaultSound;
        }
    }
}