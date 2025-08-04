package org.dbekasov11.adang.commands.handler;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.dbekasov11.adang.bdang.BDang;
import org.dbekasov11.adang.cfg.Config;
import org.dbekasov11.adang.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class UndoHandler {

    private final BDang plugin;

    public UndoHandler(BDang plugin) {
        this.plugin = plugin;
    }

    public boolean handleUndo(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Config.undoHelp.get(0));
            return true;
        }

        String regionId = args[1];
        String regionName = Config.getRegionNameFormat().replace("{name}", regionId);

        boolean found = false;

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        if (container != null) {
            for (World world : Bukkit.getWorlds()) {
                RegionManager regionManager = container.get(BukkitAdapter.adapt(world));
                if (regionManager != null && regionManager.hasRegion(regionName)) {
                    ProtectedRegion region = regionManager.getRegion(regionName);

                    regionManager.removeRegion(regionName);
                    sender.sendMessage(Config.undoRemoveRegion.get(0)
                            .replace("{region_name}", regionName)
                            .replace("{world}", world.getName()));

                    Storage shulkersStorage = plugin.getShulkersStorage();
                    ConfigurationSection locsSection = shulkersStorage.getConfig().getConfigurationSection("locs");
                    if (locsSection != null) {
                        List<String> keysToRemove = new ArrayList<>();
                        for (String key : locsSection.getKeys(false)) {
                            Location loc = locsSection.getLocation(key + ".location");
                            if (loc != null) {
                                if (region.contains(BukkitAdapter.asBlockVector(loc))) {
                                    keysToRemove.add(key);
                                }
                            }
                        }
                        for (String key : keysToRemove) {
                            locsSection.set(key, null);
                        }
                        shulkersStorage.save();
                        sender.sendMessage(Config.undoRemoveShulker.get(0)
                                .replace("{shulker}", String.valueOf(keysToRemove.size())));
                    }

                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            sender.sendMessage(Config.undoRegionNotFound.get(0)
                    .replace("{id}", regionId));
        }

        return true;
    }
}

