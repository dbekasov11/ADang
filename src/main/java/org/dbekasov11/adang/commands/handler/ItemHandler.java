package org.dbekasov11.adang.commands.handler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.dbekasov11.adang.bdang.BDang;
import org.dbekasov11.adang.cfg.Config;

import java.util.List;
import java.util.UUID;

public class ItemHandler {
    private final BDang dang;

    public ItemHandler(BDang dang) {
        this.dang = dang;
    }

    public boolean handleAddItem(Player player, String[] args) {
        if (args.length < 4) {
            for (String message : Config.addItemHelp) {
                if (!message.trim().isEmpty()) {
                    player.sendMessage(message);
                }
            }
            return true;
        } else {
            ItemStack hand = player.getInventory().getItemInMainHand();
            if (hand != null && hand.getType() != Material.AIR) {
                try {
                    int itemCount = Integer.parseInt(args[1]);
                    int min = Integer.parseInt(args[2]);
                    int max = Integer.parseInt(args[3]);
                    String uuid = UUID.randomUUID().toString();
                    this.dang.addItem(uuid, hand.clone(), itemCount, min, max);

                    for (String message : Config.addItemSuccess) {
                        if (!message.trim().isEmpty()) {
                            player.sendMessage(message);
                        }
                    }
                } catch (NumberFormatException e) {
                    for (String message : Config.addItemNumberError) {
                        if (!message.trim().isEmpty()) {
                            player.sendMessage(message);
                        }
                    }
                }
                return true;
            } else {
                for (String message : Config.addItemNoItem) {
                    if (!message.trim().isEmpty()) {
                        player.sendMessage(message);
                    }
                }
                return true;
            }
        }
    }
}
