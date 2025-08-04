package org.dbekasov11.adang.bdang;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.dbekasov11.adang.cfg.Config;

import java.util.*;

public class LootManager {
    private final BDang plugin;

    public LootManager(BDang plugin) {
        this.plugin = plugin;
    }

    public void fillInventoryWithRandomLoot(Inventory inventory) {
        ConfigurationSection itemsSection = plugin.getItemsStorage().getConfig().getConfigurationSection("items");
        if (itemsSection == null || itemsSection.getKeys(false).isEmpty()) {
            plugin.getItemsStorage().getConfig().createSection("items");
            plugin.getItemsStorage().save();
            return;
        }

        int minItems = Config.getMinItemsInShulker();
        int maxItems = Config.getMaxItemsInShulker();

        int targetItemCount = plugin.getRandomNumber(minItems, maxItems);
        if (targetItemCount <= 0) {
            return;
        }

        List<Integer> availableSlots = new ArrayList<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            availableSlots.add(i);
        }

        List<ItemStack> potentialItems = new ArrayList<>();
        for (String itemId : itemsSection.getKeys(false)) {
            ConfigurationSection itemSection = itemsSection.getConfigurationSection(itemId);
            if (itemSection == null) continue;

            ItemStack item = itemSection.getItemStack("item");
            if (item == null) continue;

            double chance = itemSection.getDouble("chance", 100.0);
            int minAmount = itemSection.getInt("minAmount", 1);
            int maxAmount = itemSection.getInt("maxAmount", 1);

            if (Math.random() * 100.0 < chance) {
                ItemStack itemToAdd = item.clone();
                itemToAdd.setAmount(plugin.getRandomNumber(minAmount, maxAmount));
                potentialItems.add(itemToAdd);
            }
        }

        Collections.shuffle(potentialItems);
        Collections.shuffle(availableSlots);

        int itemsAdded = 0;
        Iterator<ItemStack> itemIterator = potentialItems.iterator();

        while (itemIterator.hasNext() && itemsAdded < targetItemCount && !availableSlots.isEmpty()) {
            ItemStack item = itemIterator.next();
            int slot = availableSlots.remove(0);
            inventory.setItem(slot, item);
            itemsAdded++;
        }
    }

    public void addItem(String id, ItemStack item, int chance, int minAmount, int maxAmount) {
        ConfigurationSection itemsSection = plugin.getItemsStorage().getConfig().getConfigurationSection("items");
        if (itemsSection == null) {
            plugin.getItemsStorage().getConfig().createSection("items");
            addItem(id, item, chance, minAmount, maxAmount);
        } else {
            itemsSection = itemsSection.createSection(id);
            itemsSection.set("item", item);
            itemsSection.set("chance", chance);
            itemsSection.set("minAmount", minAmount);
            itemsSection.set("maxAmount", maxAmount);
            plugin.getItemsStorage().save();
        }
    }
}
