package org.dbekasov11.adang;

import org.bukkit.plugin.java.JavaPlugin;
import org.dbekasov11.adang.addshulkers.AddShulkers;
import org.dbekasov11.adang.bdang.BDang;
import org.dbekasov11.adang.bdang.EventListener;
import org.dbekasov11.adang.cfg.Config;
import org.dbekasov11.adang.commands.BDangCommands;
import org.dbekasov11.adang.dung.DungActions;
import org.dbekasov11.adang.dung.manager.RegionManager;
import org.dbekasov11.adang.dung.manager.SpawnManager;
import org.dbekasov11.adang.listener.BukkitListener;
import org.dbekasov11.adang.menu.MenuClick;
import org.dbekasov11.adang.schem.SchemAction;
import org.dbekasov11.adang.shulker.BDangShulker;
import org.dbekasov11.adang.shulker.ShulkerActions;
import org.dbekasov11.adang.storage.Storage;

import java.io.File;

public final class ADang extends JavaPlugin {
   public Storage shulkers;
   private ShulkerActions shulkerActions;
   private SchemAction schemAction;
   private DungActions dungActions;
   private BDang bDang;
   private Storage items;
   private AddShulkers addShulkers;
   private static ADang instance;

   public void onEnable() {
      instance = this;
      Config.load(this.getConfig(), this);
      this.shulkers = new Storage("shulkers.yml", this);
      this.items = new Storage("items.yml", this);
      this.bDang = new BDang(this.items, this.shulkers);
      this.shulkerActions = new BDangShulker(this.bDang);
      this.addShulkers = new AddShulkers(this.shulkerActions);
      this.schemAction = new SchemAction(this);
      SpawnManager spawnManager = new SpawnManager(this.schemAction, this.addShulkers);
      RegionManager regionManager = new RegionManager();
      this.dungActions = new DungActions(spawnManager, regionManager);
      this.getServer().getPluginManager().registerEvents(new EventListener(this.bDang), this);
      this.getServer().getPluginManager().registerEvents(new BukkitListener(this.dungActions), this);
      new File(getDataFolder(), "schem").mkdirs();
      getServer().getPluginManager().registerEvents(new MenuClick(), this);
      this.getCommand("adang").setExecutor(new BDangCommands(this.bDang, this.dungActions, this, this.items, this.shulkers));
   }
   public static ADang getInstance() {
      return instance;
   }
}