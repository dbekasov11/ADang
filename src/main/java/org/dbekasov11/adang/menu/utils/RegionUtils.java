package org.dbekasov11.adang.menu.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.dbekasov11.adang.cfg.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RegionUtils {

    public static String getRegionPrefix() {
        String format = Config.getRegionNameFormat();
        if (format.contains("{name}")) {
            return format.substring(0, format.indexOf("{name}"));
        }
        return "dang_";
    }

    public static String getRegionId(int number) {
        return Config.getRegionNameFormat().replace("{name}", String.valueOf(number));
    }

    public static int getRegionNumber(String regionId) {
        String prefix = getRegionPrefix();
        if (regionId.startsWith(prefix)) {
            try {
                return Integer.parseInt(regionId.substring(prefix.length()));
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public static List<String> getValidDungeonRegionIds() {
        var container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        if (container == null) return Collections.emptyList();

        String prefix = getRegionPrefix();
        List<String> ids = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            RegionManager manager = container.get(BukkitAdapter.adapt(world));
            if (manager != null) {
                ids.addAll(manager.getRegions().keySet().stream()
                        .filter(id -> id.startsWith(prefix))
                        .filter(id -> manager.getRegion(id) != null)
                        .collect(Collectors.toList()));
            }
        }

        ids.sort((id1, id2) -> {
            try {
                int num1 = getRegionNumber(id1);
                int num2 = getRegionNumber(id2);
                return Integer.compare(num1, num2);
            } catch (NumberFormatException e) {
                return 0;
            }
        });

        return ids;
    }

    public static World getWorldForRegion(String regionId) {
        for (World world : Bukkit.getWorlds()) {
            RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
            if (regionManager != null) {
                ProtectedRegion region = regionManager.getRegion(regionId);
                if (region != null) {
                    return world;
                }
            }
        }
        return null;
    }
}