package org.dbekasov11.adang.cfg.parser;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.FileManager;

public class ParticleParser {

    public static Particle parseParticle(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration shulkerConfig = fileManager.getShulkerConfig();

        String particleName = shulkerConfig.getString("shulker.particle", "ENCHANTMENT_TABLE").toUpperCase().replace(" ", "_");
        try {
            return Particle.valueOf(particleName);
        } catch (IllegalArgumentException e) {
            Bukkit.getLogger().warning("Недопустимый тип партиклов в конфигурации: " + particleName);
            return Particle.ENCHANTMENT_TABLE;
        }
    }

    public static int parseParticleCount(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration shulkerConfig = fileManager.getShulkerConfig();
        return shulkerConfig.getInt("shulker.particleCount", 10);
    }

    public static double parseParticleOffsetX(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration shulkerConfig = fileManager.getShulkerConfig();
        return shulkerConfig.getDouble("shulker.particleOffsetX", 0.5D);
    }

    public static double parseParticleOffsetY(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration shulkerConfig = fileManager.getShulkerConfig();
        return shulkerConfig.getDouble("shulker.particleOffsetY", 0.5D);
    }

    public static double parseParticleOffsetZ(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration shulkerConfig = fileManager.getShulkerConfig();
        return shulkerConfig.getDouble("shulker.particleOffsetZ", 0.5D);
    }
}