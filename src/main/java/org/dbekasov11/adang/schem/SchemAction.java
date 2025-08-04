package org.dbekasov11.adang.schem;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import org.bukkit.Location;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.Config;
import org.jetbrains.annotations.NotNull;

public class SchemAction {
   private final ADang plugin;

   public void spawnSchem(@NotNull Location location, @NotNull String fileName) {
      File var10002 = this.plugin.getDataFolder();
      File file = new File("" + var10002 + "/schem/" + fileName);
      ClipboardFormat format = ClipboardFormats.findByFile(file);

      try {
         ClipboardReader reader = format.getReader(new FileInputStream(file));

         try {
            Clipboard clipboard = reader.read();
            BlockVector3 cord = BlockVector3.at(
                    location.getX() + Config.getSchemOffsetX(),
                    location.getY() + Config.getSchemOffsetY(),
                    location.getZ() + Config.getSchemOffsetZ()
            );
            EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(location.getWorld()));
            Operation operation = (new ClipboardHolder(clipboard)).createPaste(editSession)
                    .to(cord)
                    .ignoreAirBlocks(Config.isIgnoreAirBlocks())
                    .build();
            Operations.complete(operation);
            editSession.close();
         } catch (Throwable var11) {
            if (reader != null) {
               try {
                  reader.close();
               } catch (Throwable var10) {
                  var11.addSuppressed(var10);
               }
            }
            throw var11;
         }

         if (reader != null) {
            reader.close();
         }
      } catch (WorldEditException | IOException var12) {
         var12.printStackTrace();
      }
   }




   public SchemAction(ADang plugin) {
      this.plugin = plugin;
   }
}

