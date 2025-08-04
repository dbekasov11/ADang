package org.dbekasov11.adang.dung.manager;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import com.sk89q.worldedit.math.BlockVector3;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.Config;
import org.dbekasov11.adang.cfg.parser.FlagParser;
import org.dbekasov11.adang.menu.utils.RegionUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RegionManager {

    private Set<Integer> getUsedRegionNumbers() {
        Set<Integer> usedNumbers = new HashSet<>();
        String prefix = RegionUtils.getRegionPrefix();

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        if (container == null) return usedNumbers;

        for (World worldBukkit : Bukkit.getWorlds()) {
            com.sk89q.worldguard.protection.managers.RegionManager regionManager =
                    container.get(BukkitAdapter.adapt(worldBukkit));
            if (regionManager == null) continue;

            for (String regionName : regionManager.getRegions().keySet()) {
                if (regionName.startsWith(prefix)) {
                    try {
                        int number = RegionUtils.getRegionNumber(regionName);
                        usedNumbers.add(number);
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }
        return usedNumbers;
    }

    private int getNextAvailableRegionNumber() {
        Set<Integer> usedNumbers = getUsedRegionNumbers();
        int i = 1;
        while (usedNumbers.contains(i)) {
            i++;
        }
        return i;
    }

    public void createRegion(int x, int z, World worldBukkit) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        if (container == null) return;

        com.sk89q.worldguard.protection.managers.RegionManager regionManager =
                container.get(BukkitAdapter.adapt(worldBukkit));
        if (regionManager == null) return;

        int regionNumber = getNextAvailableRegionNumber();
        int sizeX = Config.getRegionSizeX();
        int sizeZ = Config.getRegionSizeZ();
        int minHeight = worldBukkit.getMinHeight();
        int maxHeight = worldBukkit.getMaxHeight() - 1;

        BlockVector3 min = BlockVector3.at(x - sizeX, minHeight, z - sizeZ);
        BlockVector3 max = BlockVector3.at(x + sizeX, maxHeight, z + sizeZ);

        String regionName = Config.getRegionNameFormat().replace("{name}", String.valueOf(regionNumber));
        ProtectedCuboidRegion region = new ProtectedCuboidRegion(regionName, min, max);

        Map<StateFlag, StateFlag.State> configFlags = FlagParser.parseRegionFlags(ADang.getInstance());
        for (Map.Entry<StateFlag, StateFlag.State> entry : configFlags.entrySet()) {
            region.setFlag(entry.getKey(), entry.getValue());
        }

        regionManager.addRegion(region);
    }
}