package org.dbekasov11.adang.menu.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.dbekasov11.adang.cfg.Config;

public class TeleportUtil {

    public static boolean teleportToDungeon(Player player, String regionId) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();

        for (World world : Bukkit.getWorlds()) {
            RegionManager manager = container.get(BukkitAdapter.adapt(world));
            if (manager != null && manager.hasRegion(regionId)) {
                ProtectedRegion region = manager.getRegion(regionId);
                if (region != null) {
                    int x = (region.getMinimumPoint().getBlockX() + region.getMaximumPoint().getBlockX()) / 2;
                    int z = (region.getMinimumPoint().getBlockZ() + region.getMaximumPoint().getBlockZ()) / 2;
                    int y = world.getHighestBlockYAt(x, z) + 1;
                    Location loc = new Location(world, x + 0.5, y, z + 0.5);
                    player.teleport(loc);
                    for (String msg : Config.tpMessage) {player.sendMessage(msg.replace("{id}", regionId));}

                    return true;
                }
            }
        }

        for (String msg : Config.regionNotFoundMessage) {player.sendMessage(msg.replace("{id}", regionId));}

        return false;
    }
}

