package org.dbekasov11.adang.shulker;


import org.bukkit.Location;
import org.dbekasov11.adang.bdang.BDang;

public class BDangShulker implements ShulkerActions {
   private final BDang bDang;

   public void addShulker(Location location) {
      System.out.println("Adding shulker at " + location);
      this.bDang.addShulker(location);
   }

   public BDangShulker(BDang bDang) {
      this.bDang = bDang;
   }
}