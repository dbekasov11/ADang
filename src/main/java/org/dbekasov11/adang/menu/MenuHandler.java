package org.dbekasov11.adang.menu;

import org.bukkit.entity.Player;
import org.dbekasov11.adang.menu.utils.RegionUtils;

import java.util.List;

public class MenuHandler {

    public boolean openDungeonMenu(Player player, int page) {
        List<String> regionIds = RegionUtils.getValidDungeonRegionIds();
        return new MenuBuilder().openMenuWithRegionList(player, regionIds, page, "Список Данжей");
    }
}