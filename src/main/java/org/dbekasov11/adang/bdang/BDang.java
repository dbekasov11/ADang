package org.dbekasov11.adang.bdang;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.dbekasov11.adang.storage.Storage;

public class BDang {
    private final Storage items;
    private final Storage shulkers;
    private final ShulkerManager shulkerManager;
    private final LootManager lootManager;

    public BDang(Storage items, Storage shulkers) {
        this.items = items;
        this.shulkers = shulkers;
        this.shulkerManager = new ShulkerManager(this);
        this.lootManager = new LootManager(this);
    }



    public Storage getItemsStorage() {
        return items;
    }

    public void addItem(String id, ItemStack item, int chance, int minAmount, int maxAmount) {
        this.lootManager.addItem(id, item, chance, minAmount, maxAmount);
    }

    public void addShulker(Location location) {
        this.shulkerManager.addShulker(location);
    }

    public Storage getShulkersStorage() {
        return shulkers;
    }

    public ShulkerManager getShulkerManager() {
        return shulkerManager;
    }

    public LootManager getLootManager() {
        return lootManager;
    }

    public int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}