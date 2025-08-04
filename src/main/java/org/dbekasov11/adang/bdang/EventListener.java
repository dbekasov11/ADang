package org.dbekasov11.adang.bdang;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.dbekasov11.adang.cfg.Config;

import java.util.List;
import java.util.Random;

public class EventListener implements Listener {
    private final BDang plugin;

    public EventListener(BDang plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) {
            return;
        }

        if (!plugin.getShulkerManager().isShulker(event.getClickedBlock())) {
            return;
        }

        ConfigurationSection locsSection = plugin.getShulkersStorage().getConfig().getConfigurationSection("locs");
        if (locsSection == null) {
            return;
        }

        for (String itemId : locsSection.getKeys(false)) {
            ConfigurationSection shulker = locsSection.getConfigurationSection(itemId);
            Location shulkerLocation = shulker.getLocation("location");
            if (event.getClickedBlock().getLocation().getBlockX() == shulkerLocation.getBlockX() &&
                    event.getClickedBlock().getLocation().getBlockY() == shulkerLocation.getBlockY() &&
                    event.getClickedBlock().getLocation().getBlockZ() == shulkerLocation.getBlockZ() &&
                    event.getClickedBlock().getLocation().getWorld().getName().equals(shulkerLocation.getWorld().getName()) &&
                    !shulker.getBoolean("opened")) {

                ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
                if (itemInHand != null &&
                        itemInHand.getItemMeta() != null &&
                        itemInHand.getItemMeta().getPersistentDataContainer()
                                .has(NamespacedKey.fromString("key"), PersistentDataType.STRING)) {
                    for (Player player : Bukkit.getOnlinePlayers()) {player.playSound(shulkerLocation, Config.getOpenSound(), 50.0F, 1.0F);}
                    shulkerLocation.getWorld().spawnParticle(Config.getParticle(), shulkerLocation.add(0.0D, 1.0D, 0.0D),
                            Config.getParticleCount(), Config.getParticleOffsetX(), Config.getParticleOffsetY(), Config.getParticleOffsetZ(), 0.1D);
                    shulker.set("opened", true);
                    plugin.getShulkersStorage().save();

                    ShulkerBox shulkerBox = (ShulkerBox) event.getClickedBlock().getState();
                    event.getPlayer().openInventory(shulkerBox.getInventory());


                    for (Player player : Bukkit.getOnlinePlayers()) {
                        for (String s : Config.Messages.openDung) {
                            player.sendMessage(s.replace("{player}", event.getPlayer().getName()));
                        }
                    }

                    if (Math.random() * 100.0D < Config.getSaveChance()) {
                        event.getPlayer().sendMessage(Config.Messages.saveKey);
                    } else {
                        ItemStack key = Config.getKey();
                        key.setAmount(1);
                        event.getPlayer().getInventory().removeItem(key);
                    }

                    return;
                } else {
                    event.getPlayer().playSound(shulkerLocation, Config.getLockedSound(), 1.0F, 1.0F);
                    for (String s : Config.Messages.closedDung) {
                        event.getPlayer().sendMessage(s);
                    }

                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onLoot(LootGenerateEvent e) {
        Random random = new Random();
        int result = random.nextInt(100);
        if (result < Config.getChanceSpawn()) {
            e.getLoot().add(Config.getKey());
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPistonExtend(BlockPistonExtendEvent e) {
        pistonUtil(e.getBlocks(), e);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPistonRetract(BlockPistonRetractEvent e) {
        pistonUtil(e.getBlocks(), e);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack itemInHand = event.getItem();
        if (itemInHand != null && itemInHand.isSimilar(Config.getKey())) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                return;
            }

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null) {
                Material clickedBlockType = event.getClickedBlock().getType();

                List<Material> allowedBlocks = List.of(
                        Material.CHEST, Material.FURNACE, Material.ENCHANTING_TABLE, Material.ENDER_CHEST,Material.BREWING_STAND,
                        Material.ANVIL, Material.CHIPPED_ANVIL, Material.DAMAGED_ANVIL, Material.LOOM, Material.BARREL,
                        Material.SMOKER, Material.BLAST_FURNACE, Material.CARTOGRAPHY_TABLE, Material.GRINDSTONE,
                        Material.SMITHING_TABLE, Material.STONECUTTER, Material.BELL, Material.DISPENSER, Material.NOTE_BLOCK,
                        Material.LEVER, Material.STONE_BUTTON, Material.OAK_BUTTON, Material.SPRUCE_BUTTON, Material.BIRCH_BUTTON,
                        Material.JUNGLE_BUTTON, Material.ACACIA_BUTTON, Material.DARK_OAK_BUTTON, Material.CRIMSON_BUTTON,
                        Material.WARPED_BUTTON, Material.POLISHED_BLACKSTONE_BUTTON, Material.DAYLIGHT_DETECTOR, Material.HOPPER,
                        Material.DROPPER, Material.OAK_DOOR, Material.IRON_DOOR, Material.SPRUCE_DOOR, Material.BIRCH_DOOR,
                        Material.JUNGLE_DOOR, Material.ACACIA_DOOR, Material.DARK_OAK_DOOR, Material.CRIMSON_DOOR, Material.WARPED_DOOR,
                        Material.OAK_FENCE_GATE, Material.SPRUCE_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.JUNGLE_FENCE_GATE,
                        Material.ACACIA_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.CRIMSON_FENCE_GATE, Material.WARPED_FENCE_GATE,
                        Material.CRAFTING_TABLE, Material.WHITE_BED, Material.ORANGE_BED, Material.MAGENTA_BED, Material.LIGHT_BLUE_BED,
                        Material.YELLOW_BED, Material.LIME_BED, Material.PINK_BED, Material.GRAY_BED, Material.LIGHT_GRAY_BED,
                        Material.CYAN_BED, Material.PURPLE_BED, Material.BLUE_BED, Material.BROWN_BED, Material.GREEN_BED,
                        Material.RED_BED, Material.BLACK_BED, Material.REPEATER, Material.COMPARATOR, Material.LECTERN,Material.BEACON,

                        Material.OAK_TRAPDOOR, Material.SPRUCE_TRAPDOOR, Material.BIRCH_TRAPDOOR, Material.JUNGLE_TRAPDOOR,
                        Material.ACACIA_TRAPDOOR, Material.DARK_OAK_TRAPDOOR, Material.CRIMSON_TRAPDOOR, Material.WARPED_TRAPDOOR,
                        Material.IRON_TRAPDOOR,

                        Material.SHULKER_BOX, Material.BLACK_SHULKER_BOX, Material.WHITE_SHULKER_BOX, Material.BLUE_SHULKER_BOX,
                        Material.CYAN_SHULKER_BOX, Material.BROWN_SHULKER_BOX, Material.YELLOW_SHULKER_BOX, Material.GREEN_SHULKER_BOX,
                        Material.LIME_SHULKER_BOX, Material.RED_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.LIGHT_GRAY_SHULKER_BOX,
                        Material.MAGENTA_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.PURPLE_SHULKER_BOX, Material.ORANGE_SHULKER_BOX,
                        Material.GRAY_SHULKER_BOX
                );

                if (allowedBlocks.contains(clickedBlockType)) {
                    return;
                }
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();

        for (ItemStack item : inventory.getMatrix()) {
            if (item == null) continue;
            if (item.getItemMeta() == null) continue;

            if (item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("key"), PersistentDataType.STRING)) {
                event.getInventory().setResult(null);
                break;
            }
        }
    }

    private void pistonUtil(List<Block> pushedBlocks, BlockPistonEvent e) {
        for (Block b : pushedBlocks) {
            if (plugin.getShulkerManager().isShulker(b)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (plugin.getShulkerManager().isShulker(e.getBlock())) {
            ConfigurationSection locsSection = plugin.getShulkersStorage().getConfig().getConfigurationSection("locs");
            for (String itemId : locsSection.getKeys(false)) {
                ConfigurationSection shulker = locsSection.getConfigurationSection(itemId);
                Location shulkerLocation = shulker.getLocation("location");
                if (e.getBlock().getLocation().getBlockX() == shulkerLocation.getBlockX() &&
                        e.getBlock().getLocation().getBlockY() == shulkerLocation.getBlockY() &&
                        e.getBlock().getLocation().getBlockZ() == shulkerLocation.getBlockZ() &&
                        e.getBlock().getLocation().getWorld().getName().equals(shulkerLocation.getWorld().getName())) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockFromTo(BlockFromToEvent e) {
        if (plugin.getShulkerManager().isShulker(e.getToBlock())) {
            e.setCancelled(true);
        }
    }
}
