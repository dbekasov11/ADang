package org.dbekasov11.adang.addshulkers;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.dbekasov11.adang.shulker.ShulkerActions;

public class AddShulkers {
   private final ShulkerActions actions;

   public void addShulkersInLocation(Location location, int radius) {
      for(int x = -radius; x <= radius; ++x) {
         for(int y = -radius; y <= radius; ++y) {
            for(int z = -radius; z <= radius; ++z) {
               Block block = location.getWorld().getBlockAt(location.clone().add((double)x, (double)y, (double)z));
               if (this.isShulker(block.getType())) {
                  this.actions.addShulker(block.getLocation());
               }
            }
         }
      }

   }

   private boolean isShulker(Material placedBlock) {
      return placedBlock.toString().endsWith("SHULKER_BOX");
   }

   public AddShulkers(ShulkerActions actions) {
      this.actions = actions;
   }
}