package org.dbekasov11.adang.dung.manager;


import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.dbekasov11.adang.addshulkers.AddShulkers;
import org.dbekasov11.adang.cfg.Config;
import org.dbekasov11.adang.data.DangData;
import org.dbekasov11.adang.schem.SchemAction;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class SpawnManager {
    private final SchemAction schemAction;
    private final AddShulkers addShulkers;

    public SpawnManager(SchemAction schemAction, AddShulkers addShulkers) {
        this.schemAction = schemAction;
        this.addShulkers = addShulkers;
    }

    public boolean spawnRandomIfBiomeMatches(@NotNull Location loc) {
        String world = loc.getWorld().getName();
        List<DangData> dangDataList = Config.getDangsDataList();

        for (int i = 0; i <= 19; ++i) {
            DangData dangData = dangDataList.get((new Random()).nextInt(dangDataList.size()));

            for (Biome b : dangData.getBiome()) {
                if (dangData.getWorld().equals(world) && b == loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())) {
                    schemAction.spawnSchem(loc, dangData.getFileName());
                    addShulkers.addShulkersInLocation(loc, 35);
                    return true;
                }
            }
        }
        return false;
    }


    public void spawnWithName(@NotNull Location loc, String schemName) {
        schemAction.spawnSchem(loc, schemName);
        addShulkers.addShulkersInLocation(loc, 35);
    }
}

