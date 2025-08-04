package org.dbekasov11.adang.storage;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Storage {
   private File file;
   private FileConfiguration config;

   public Storage(String name, JavaPlugin plugin) {
      File dataFolder = new File(plugin.getDataFolder(), "data");
      if (!dataFolder.exists()) {
         dataFolder.mkdirs();
      }

      this.file = new File(dataFolder, name);

      try {
         if (!this.file.exists() && !this.file.createNewFile()) {
            throw new IOException();
         }
      } catch (IOException var4) {
         throw new RuntimeException("Failed to create file: ", var4);
      }

      this.config = YamlConfiguration.loadConfiguration(this.file);
   }

   public void save() {
      try {
         this.config.save(this.file);
      } catch (IOException var2) {
         throw new RuntimeException("Failed to save file:", var2);
      }
   }

   public void reloadConfig() {
      this.config = YamlConfiguration.loadConfiguration(this.file);
   }

   public FileConfiguration getConfig() {
      return this.config;
   }
}