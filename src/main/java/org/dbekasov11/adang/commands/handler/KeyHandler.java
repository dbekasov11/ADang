package org.dbekasov11.adang.commands.handler;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.dbekasov11.adang.cfg.Config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class KeyHandler {

    public boolean handleGiveKrystal(CommandSender sender, String[] strings) {
        if (strings.length >= 2) {
            Player target = Bukkit.getPlayer(strings[1]);
            if (target != null) {
                int amount = 1;
                if (strings.length >= 3) {
                    try {
                        amount = Integer.parseInt(strings[2]);
                    } catch (NumberFormatException e) {
                        for (String message : Config.giveKrystalInvalidAmount) {
                            if (!message.trim().isEmpty()) {
                                sender.sendMessage(message);
                            }
                        }
                        return true;
                    }
                }

                ItemStack krystal = Config.getKrystal();

                for (int i = 0; i < amount; i++) {
                    ItemStack single = krystal.clone();
                    single.setAmount(1);

                    boolean invFull = Arrays.stream(target.getInventory().getStorageContents())
                            .noneMatch(Objects::isNull);
                    if (invFull) {
                        target.getWorld().dropItemNaturally(target.getLocation(), single);
                    } else {
                        target.getInventory().addItem(single);
                    }
                }

                for (String message : Config.giveKrystalSuccess) {
                    if (!message.trim().isEmpty()) {
                        sender.sendMessage(message
                                .replace("{player}", target.getName())
                                .replace("{amount}", String.valueOf(amount)));
                    }
                }
            } else {
                for (String message : Config.giveKrystalNoPlayer) {
                    if (!message.trim().isEmpty()) {
                        sender.sendMessage(message);
                    }
                }
            }
        } else {
            for (String message : Config.giveKrystalNickPlayer) {
                if (!message.trim().isEmpty()) {
                    sender.sendMessage(message);
                }
            }
        }
        return true;
    }

    public boolean handleGiveKey(CommandSender sender, String[] strings) {
        if (strings.length >= 2) {
            Player target = Bukkit.getPlayer(strings[1]);
            if (target != null) {
                int amount = 1;
                if (strings.length >= 3) {
                    try {
                        amount = Integer.parseInt(strings[2]);
                    } catch (NumberFormatException e) {
                        for (String message : Config.giveKeyInvalidAmount) {
                            if (!message.trim().isEmpty()) {
                                sender.sendMessage(message);
                            }
                        }
                        return true;
                    }
                }

                ItemStack key = Config.getKey();

                for (int i = 0; i < amount; i++) {
                    ItemStack single = key.clone();
                    single.setAmount(1);

                    boolean invFull = Arrays.stream(target.getInventory().getStorageContents())
                            .noneMatch(Objects::isNull);
                    if (invFull) {
                        target.getWorld().dropItemNaturally(target.getLocation(), single);
                    } else {
                        target.getInventory().addItem(single);
                    }
                }

                for (String message : Config.giveKeySuccess) {
                    if (!message.trim().isEmpty()) {
                        sender.sendMessage(message
                                .replace("{player}", target.getName())
                                .replace("{amount}", String.valueOf(amount)));
                    }
                }
            } else {
                for (String message : Config.giveKeyNoPlayer) {
                    if (!message.trim().isEmpty()) {
                        sender.sendMessage(message);
                    }
                }
            }
        } else {
            for (String message : Config.giveKeyNickPlayer) {
                if (!message.trim().isEmpty()) {
                    sender.sendMessage(message);
                }
            }
        }
        return true;
    }
}