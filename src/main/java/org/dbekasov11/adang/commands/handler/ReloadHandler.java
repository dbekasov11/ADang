package org.dbekasov11.adang.commands.handler;

import org.bukkit.command.CommandSender;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.Config;
import org.dbekasov11.adang.storage.Storage;

import java.util.List;

public class ReloadHandler {
    private final ADang aDang;
    private final Storage items;
    private final Storage shulkers;

    public ReloadHandler(ADang aDang, Storage items, Storage shulkers) {
        this.aDang = aDang;
        this.items = items;
        this.shulkers = shulkers;
    }

    public boolean handleReload(CommandSender sender) {
        this.aDang.reloadConfig();
        this.items.reloadConfig();
        this.shulkers.reloadConfig();

        Config.load(this.aDang.getConfig(), this.aDang);

        for (String message : Config.reloadSuccess) {
            if (!message.trim().isEmpty()) {
                sender.sendMessage(message);
            }
        }
        return true;
    }
}