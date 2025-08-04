package org.dbekasov11.adang.menu;

import com.sk89q.worldedit.math.BlockVector3;
import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.dbekasov11.adang.cfg.Utils;
import org.dbekasov11.adang.menu.utils.HeadUtils;
import org.dbekasov11.adang.menu.utils.RegionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MenuBuilder {

    private static final int ITEMS_PER_PAGE = 45;
    private static final int INVENTORY_SIZE = 54;
    private static final int[] BOTTOM_BAR_SLOTS = {45, 46, 47, 48, 49, 50, 51, 52, 53};
    private static final int NEXT_PAGE_SLOT = 50;
    private static final int PREV_PAGE_SLOT = 48;

    public boolean openMenuWithRegionList(Player player, List<String> regionIds, int page, String titlePrefix) {
        int maxPages = Math.max(1, (int) Math.ceil((double) regionIds.size() / ITEMS_PER_PAGE));
        page = Math.max(0, Math.min(page, maxPages - 1));

        Inventory gui = Bukkit.createInventory(null, INVENTORY_SIZE, titlePrefix + " (" + (page + 1) + "/" + maxPages + ")");

        addDungeonItems(gui, regionIds, page);

        fillBottomBar(gui, page, maxPages);

        player.openInventory(gui);
        return true;
    }

    private void addDungeonItems(Inventory gui, List<String> regionIds, int page) {
        int start = page * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, regionIds.size());

        for (int i = start; i < end; i++) {
            String regionId = regionIds.get(i);
            int dungeonNumber = RegionUtils.getRegionNumber(regionId);
            ItemStack dungeonItem = createDungeonItem(regionId, dungeonNumber);
            gui.setItem(i - start, dungeonItem);
        }
    }

    private ItemStack createDungeonItem(String regionId, int dungeonNumber) {
        World regionWorld = RegionUtils.getWorldForRegion(regionId);
        String displayName = Utils.getInstance().translateColors("&fДанж &8[" + dungeonNumber + "]");
        String coordinatesLine = getCoordinates(regionWorld, regionId);
        String worldNameDisplay = regionWorld != null ? regionWorld.getName() : "Неизвестен";

        List<String> loreRaw = Arrays.asList(
                "",
                " &fМир: &#57B0FF" + worldNameDisplay,
                " &fКорды: &#ffd43f" + coordinatesLine,
                "",
                " &fПри нажатии &#57B0FFПКМ&f телепортирует",
                " &fвас прямо на место спавна данжа",
                ""
        );

        List<String> lore = loreRaw.stream()
                .map(Utils.getInstance()::translateColors)
                .collect(Collectors.toList());

        return createDungeonItemByWorld(regionWorld, displayName, lore);
    }

    private String getCoordinates(World world, String regionId) {
        if (world == null) return "Недоступно";

        var wgManager = com.sk89q.worldguard.WorldGuard.getInstance()
                .getPlatform().getRegionContainer()
                .get(com.sk89q.worldedit.bukkit.BukkitAdapter.adapt(world));

        if (wgManager == null || !wgManager.hasRegion(regionId)) {
            return "Недоступно";
        }

        var region = wgManager.getRegion(regionId);
        if (region == null) return "Недоступно";

        BlockVector3 min = region.getMinimumPoint();
        BlockVector3 max = region.getMaximumPoint();

        int x = (min.getBlockX() + max.getBlockX()) / 2;
        int y = (min.getBlockY() + max.getBlockY()) / 2;
        int z = (min.getBlockZ() + max.getBlockZ()) / 2;

        return String.format("X: %d Y: %d Z: %d", x, y, z);
    }

    private void fillBottomBar(Inventory gui, int currentPage, int maxPages) {
        ItemStack blackPane = createPane(Material.BLACK_STAINED_GLASS_PANE, "");
        for (int slot : BOTTOM_BAR_SLOTS) {
            gui.setItem(slot, blackPane);
        }

        gui.setItem(49, createInfoItem());
        if (currentPage < maxPages - 1) {
            gui.setItem(NEXT_PAGE_SLOT, createNextPageItem());
        }
        if (currentPage > 0) {
            gui.setItem(PREV_PAGE_SLOT, createPreviousPageItem());
        }
    }

    private ItemStack createPreviousPageItem() {
        String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzYyNTkwMmIzODllZDZjMTQ3NTc0ZTQyMmRhOGY4ZjM2MWM4ZWI1N2U3NjMxNjc2YTcyNzc3ZTdiMWQifX19";
        ItemStack head = HeadUtils.getCustomHead(texture);
        if (head == null || head.getType() != Material.PLAYER_HEAD) {
            head = new ItemStack(Material.ARROW);
        }
        ItemMeta meta = head.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§f[ §7← §f] §6Предыдущая страница");
            head.setItemMeta(meta);
        }
        return head;
    }

    private ItemStack createNextPageItem() {
        String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDRiZThhZWVjMTE4NDk2OTdhZGM2ZmQxZjE4OWIxNjY0MmRmZjE5ZjI5NTVjMDVkZWFiYTY4YzlkZmYxYmUifX19";
        ItemStack head = HeadUtils.getCustomHead(texture);
        if (head == null || head.getType() != Material.PLAYER_HEAD) {
            head = new ItemStack(Material.ARROW);
        }
        ItemMeta meta = head.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6Следующая страница §f[ §7→ §f]");
            head.setItemMeta(meta);
        }
        return head;
    }

    private ItemStack createDungeonItemByWorld(World world, String displayName, List<String> lore) {
        Material material = Material.DIRT;

        if (world != null) {
            String worldName = world.getName().toLowerCase();
            if (worldName.contains("nether") || worldName.equals("world_nether")) {
                material = Material.NETHERRACK;
            } else if (worldName.contains("end") || worldName.equals("world_the_end")) {
                material = Material.END_STONE;
            }
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createPane(Material material, String name) {
        ItemStack pane = new ItemStack(material);
        ItemMeta meta = pane.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            pane.setItemMeta(meta);
        }
        return pane;
    }

    private ItemStack createInfoItem() {
        try {
            String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTM1OWQ5MTI3NzI0MmZjMDFjMzA5YWNjYjg3YjUzM2YxOTI5YmUxNzZlY2JhMmNkZTYzYmY2MzVlMDVlNjk5YiJ9fX0=";

            ItemStack head = HeadUtils.getCustomHead(texture);
            if (head == null || head.getType() != Material.PLAYER_HEAD) {
                head = new ItemStack(Material.PAPER);
            }

            ItemMeta meta = head.getItemMeta();
            if (meta != null) {
                meta.setDisplayName("§7[§e₪§7] §fИнформация");
                meta.setLore(Arrays.asList(
                        "",
                        " §fВ данном меню вы сможете увидеть",
                        " §fвсе активные данжи на сервере",
                        ""
                ));
                head.setItemMeta(meta);
            }
            return head;
        } catch (Exception e) {
            ItemStack fallback = new ItemStack(Material.PAPER);
            ItemMeta meta = fallback.getItemMeta();
            if (meta != null) {
                meta.setDisplayName("§7[§e₪§7] §fИнформация");
                fallback.setItemMeta(meta);
            }
            return fallback;
        }
    }
}