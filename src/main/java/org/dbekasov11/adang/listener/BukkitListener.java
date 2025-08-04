package org.dbekasov11.adang.listener;

import org.bukkit.event.Listener;
import org.dbekasov11.adang.dung.DungActions;

public class BukkitListener implements Listener {
   private DungActions dungActions;

   public BukkitListener(DungActions dungActions) {
      this.dungActions = dungActions;
   }
}