package org.dbekasov11.adang.cfg.parser;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.Config;
import org.dbekasov11.adang.cfg.FileManager;
import org.dbekasov11.adang.cfg.Utils;

public class ItemParser {

    private static final Utils colorUtils = new Utils();

    public static ItemStack parseKey(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration keyConfig = fileManager.getKeyConfig();

        ConfigurationSection section = keyConfig.getConfigurationSection("key");
        if (section == null) {
            plugin.getLogger().warning("Раздел 'key' отсутствует в key.yml");
            return null;
        }

        Material material = Material.getMaterial(section.getString("material", "TRIPWIRE_HOOK").toUpperCase());
        if (material == null) {
            throw new IllegalArgumentException("Недопустимый материал: " + section.getString("material"));
        }

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Utils.translateRGB(section.getString("name", "&x&E&2&D&6&C&1Универсальный ключ")));
            itemMeta.setLore(Utils.translateColorCodes(section.getStringList("lore")));
            Config.chanceSpawn = section.getInt("chanceSpawn", 20);

            boolean hideEnchantments = section.getBoolean("hideEnchantments", true);
            if (hideEnchantments) {
                itemMeta.addEnchant(Enchantment.LUCK, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString("key"), PersistentDataType.STRING, "holyworld");
            itemStack.setItemMeta(itemMeta);
        }
        Config.saveChance = section.getInt("saveChance", 40);
        return itemStack;
    }

    public static ItemStack parseKrystal(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration keyConfig = fileManager.getKeyConfig();

        ConfigurationSection section = keyConfig.getConfigurationSection("krystal");
        if (section == null) {
            plugin.getLogger().warning("Раздел 'krystal' отсутствует в key.yml");
            return null;
        }

        Material material = Material.getMaterial(section.getString("material", "PRISMARINE_CRYSTALS").toUpperCase());
        if (material == null) {
            throw new IllegalArgumentException("Недопустимый материал: " + section.getString("material"));
        }

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Utils.translateRGB(section.getString("name", "&x&E&2&D&6&C&1Осколок от ключа")));
            itemMeta.setLore(Utils.translateColorCodes(section.getStringList("lore")));

            boolean hideEnchantments = section.getBoolean("hideEnchantments", true);
            if (hideEnchantments) {
                itemMeta.addEnchant(Enchantment.LUCK, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }
}