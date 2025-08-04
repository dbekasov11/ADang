package org.dbekasov11.adang.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.bdang.BDang;
import org.dbekasov11.adang.commands.handler.*;
import org.dbekasov11.adang.dung.DungActions;
import org.dbekasov11.adang.menu.MenuHandler;
import org.dbekasov11.adang.storage.Storage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BDangCommands implements TabExecutor {
    private final BDang dang;
    private final DungActions dungActions;
    private final ADang aDang;
    private final Storage items, shulkers;
    private final ItemHandler itemHandler;
    private final SpawnHandler spawnHandler;
    private final KeyHandler giveHandler;
    private final ReloadHandler reloadHandler;
    private final UndoHandler undoHandler;
    private final MenuHandler menuHandler;

    public BDangCommands(BDang dang, DungActions dungActions, ADang aDang, Storage items, Storage shulkers) {
        this.dang = dang;
        this.dungActions = dungActions;
        this.aDang = aDang;
        this.items = items;
        this.shulkers = shulkers;
        this.itemHandler = new ItemHandler(dang);
        this.spawnHandler = new SpawnHandler(dungActions, aDang);
        this.giveHandler = new KeyHandler();
        this.reloadHandler = new ReloadHandler(aDang, items, shulkers);
        this.undoHandler = new UndoHandler(dang);
        this.menuHandler = new MenuHandler();


    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("bdang.admin")) return false;

        if (args.length == 0) return true;

        switch (args[0].toLowerCase()) {
            case "additem":
            case "spawn":
            case "spawnschem":
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Эта команда доступна только игрокам!");
                    return true;
                }
                break;
        }

        Player player = sender instanceof Player ? (Player) sender : null;

        switch (args[0].toLowerCase()) {
            case "additem":
                return itemHandler.handleAddItem((Player) sender, args);
            case "spawn":
                return spawnHandler.handleSpawn((Player) sender, args);
            case "spawnschem":
                return spawnHandler.handleSpawnSchem((Player) sender, args);
            case "givekrystal":
                return giveHandler.handleGiveKrystal(sender, args);
            case "givekey":
                return giveHandler.handleGiveKey(sender, args);
            case "reload":
                return reloadHandler.handleReload(sender);
            case "undo":
                return undoHandler.handleUndo(sender, args);
            case "list":
                return menuHandler.openDungeonMenu(player, 0);
            default:
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("bdang.admin")) return null;
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.addAll(List.of("additem", "spawn", "spawnschem", "givekey", "givekrystal", "reload", "undo", "list"));
        } else if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "spawn": suggestions.add("<количество>"); break;
                case "spawnschem":
                    File schemFolder = new File(aDang.getDataFolder(), "schem");
                    if (schemFolder.exists() && schemFolder.isDirectory()) {
                        File[] schemFiles = schemFolder.listFiles((dir, name) -> name.endsWith(".schem"));
                        if (schemFiles != null) {
                            for (File schemFile : schemFiles) {
                                suggestions.add(schemFile.getName().replace(".schem", ""));
                            }
                        }
                    }
                    break;
                case "givekey":
                case "givekrystal":
                    List<String> finalSuggestions = suggestions;
                    Bukkit.getOnlinePlayers().forEach(p -> finalSuggestions.add(p.getName()));
                    break;
                case "additem": suggestions.add("<шанс>"); break;
            }
        } else if (args.length == 3 && (args[0].equalsIgnoreCase("givekey") || args[0].equalsIgnoreCase("givekrystal"))) {
            suggestions.add("<количество>");
        } else if (args.length == 3 && args[0].equalsIgnoreCase("additem")) {
            suggestions.add("<минимум>");
        } else if (args.length == 4 && args[0].equalsIgnoreCase("additem")) {
            suggestions.add("<максимум>");
        }

        if (args.length == 1 || (args.length == 2 && args[0].equalsIgnoreCase("spawnschem"))) {
            String input = args[args.length - 1].toLowerCase();
            suggestions = suggestions.stream().filter(s -> s.startsWith(input)).collect(Collectors.toList());
        }

        return suggestions;
    }
}