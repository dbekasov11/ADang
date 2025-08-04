package org.dbekasov11.adang.dung;

import org.bukkit.Location;
import org.dbekasov11.adang.dung.manager.RegionManager;
import org.dbekasov11.adang.dung.manager.SpawnManager;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

public class DungActions {
   private final SpawnManager spawnManager;
   private final RegionManager regionManager;
   private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");

   public DungActions(SpawnManager spawnManager, RegionManager regionManager) {
      this.spawnManager = spawnManager;
      this.regionManager = regionManager;
   }



   public boolean spawn(@NotNull Location loc) {
      if (spawnManager.spawnRandomIfBiomeMatches(loc)) {
         regionManager.createRegion(loc.getBlockX(), loc.getBlockZ(), loc.getWorld());
         return true;
      }
      return false;
   }

   public void spawn(@NotNull Location loc, String schemName) {
      spawnManager.spawnWithName(loc, schemName);
      regionManager.createRegion(loc.getBlockX(), loc.getBlockZ(), loc.getWorld());
   }
}
