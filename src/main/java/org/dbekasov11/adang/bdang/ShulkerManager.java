package org.dbekasov11.adang.bdang;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.configuration.ConfigurationSection;
import org.dbekasov11.adang.storage.Storage;

import java.util.UUID;

public class ShulkerManager {
    private final BDang plugin;

    public ShulkerManager(BDang plugin) {
        this.plugin = plugin;
    }

    public void addShulker(Location location) {
        if (location.getBlock().getState() instanceof ShulkerBox) {
            ShulkerBox shulkerBox = (ShulkerBox) location.getBlock().getState();
            plugin.getLootManager().fillInventoryWithRandomLoot(shulkerBox.getInventory());
            String uuid = UUID.randomUUID().toString();
            addShulkerConfig(uuid, location, false);
        }
    }



    public void addShulkerConfig(String id, Location location, boolean opened) {
        ConfigurationSection itemsSection = plugin.getShulkersStorage().getConfig().getConfigurationSection("locs");
        if (itemsSection == null) {
            plugin.getShulkersStorage().getConfig().createSection("locs");
            itemsSection = plugin.getShulkersStorage().getConfig().getConfigurationSection("locs");
        }
        itemsSection = itemsSection.createSection(id);
        itemsSection.set("location", location);
        itemsSection.set("opened", opened);
        plugin.getShulkersStorage().save();
    }

    public boolean isShulker(Block placedBlock) {
        return placedBlock.getType() == Material.SHULKER_BOX ||
                placedBlock.getType() == Material.BLACK_SHULKER_BOX ||
                placedBlock.getType() == Material.WHITE_SHULKER_BOX ||
                placedBlock.getType() == Material.BLUE_SHULKER_BOX ||
                placedBlock.getType() == Material.CYAN_SHULKER_BOX ||
                placedBlock.getType() == Material.BROWN_SHULKER_BOX ||
                placedBlock.getType() == Material.YELLOW_SHULKER_BOX ||
                placedBlock.getType() == Material.GREEN_SHULKER_BOX ||
                placedBlock.getType() == Material.LIME_SHULKER_BOX ||
                placedBlock.getType() == Material.RED_SHULKER_BOX ||
                placedBlock.getType() == Material.LIGHT_BLUE_SHULKER_BOX ||
                placedBlock.getType() == Material.LIGHT_GRAY_SHULKER_BOX ||
                placedBlock.getType() == Material.MAGENTA_SHULKER_BOX ||
                placedBlock.getType() == Material.PINK_SHULKER_BOX ||
                placedBlock.getType() == Material.PURPLE_SHULKER_BOX ||
                placedBlock.getType() == Material.ORANGE_SHULKER_BOX ||
                placedBlock.getType() == Material.GRAY_SHULKER_BOX;
    }
}
