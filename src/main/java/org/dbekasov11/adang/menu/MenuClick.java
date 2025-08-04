package org.dbekasov11.adang.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.dbekasov11.adang.menu.utils.TeleportUtil;
import org.dbekasov11.adang.menu.utils.RegionUtils;
import org.bukkit.event.EventHandler;

public class MenuClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack clicked = e.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;

        String title = e.getView().getTitle();

        if (title.startsWith("Список Данжей")) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            ItemMeta meta = clicked.getItemMeta();
            if (meta == null) return;
            String name = meta.getDisplayName();

            if (clicked.getType() == Material.PLAYER_HEAD && name != null) {
                if (name.contains("Предыдущая страница") || name.contains("Следующая страница")) {
                    int currentPage = getCurrentPage(title);
                    int newPage = name.contains("Следующая страница") ? currentPage + 1 : currentPage - 1;
                    new MenuHandler().openDungeonMenu(player, newPage);
                    return;
                }
            }

            if (clicked.getType() == Material.PLAYER_HEAD && name != null && name.contains("Информация")) {
                return;
            }

            if (clicked.getType() == Material.NETHERRACK || clicked.getType() == Material.END_STONE || clicked.getType() == Material.DIRT) {
                if (e.isRightClick()) {
                    if (name != null && name.contains("Данж")) {
                        String dungeonId = name.replaceAll("§[0-9a-fk-or]", "")
                                .replace("Данж [", "")
                                .replace("]", "")
                                .trim();

                        String regionId = RegionUtils.getRegionId(Integer.parseInt(dungeonId));

                        player.closeInventory();
                        TeleportUtil.teleportToDungeon(player, regionId);
                    }
                }
            }
        }
    }

    private int getCurrentPage(String title) {
        try {
            String pageStr = title.substring(title.indexOf('(') + 1, title.indexOf('/'));
            return Integer.parseInt(pageStr.trim()) - 1;
        } catch (Exception e) {
            return 0;
        }
    }
}