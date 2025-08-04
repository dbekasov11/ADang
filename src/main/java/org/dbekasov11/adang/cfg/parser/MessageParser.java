package org.dbekasov11.adang.cfg.parser;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.Config;
import org.dbekasov11.adang.cfg.FileManager;
import org.dbekasov11.adang.cfg.Utils;

import java.util.Collections;
import java.util.List;

public class MessageParser {

    private static final Utils utils = new Utils();

    public static void parseMessages(ADang plugin) {
        FileManager fileManager = new FileManager(plugin);
        FileConfiguration messagesConfig = fileManager.getMessagesConfig();

        ConfigurationSection messagesSection = messagesConfig.getConfigurationSection("messages");
        if (messagesSection != null) {
            Config.Messages.openDung = Utils.translateColorCodes(messagesSection.getStringList("openDung"));
            Config.Messages.saveKey = Utils.translateRGB(messagesSection.getString("saveKey"));
            Config.Messages.closedDung = Utils.translateColorCodes(messagesSection.getStringList("closedDung"));
        } else {
            plugin.getLogger().warning("Раздел 'messages' отсутствует в messages.yml");
        }

        ConfigurationSection commandsSection = messagesConfig.getConfigurationSection("messages");
        if (commandsSection != null) {
            Config.addItemHelp = Utils.translateColorCodes(commandsSection.getStringList("additem.helpadd"));
            Config.addItemSuccess = Utils.translateColorCodes(commandsSection.getStringList("additem.successfully"));
            Config.addItemNumberError = Utils.translateColorCodes(commandsSection.getStringList("additem.addnumberhelp"));
            Config.addItemNoItem = Utils.translateColorCodes(commandsSection.getStringList("additem.itemno"));
            Config.giveKrystalInvalidAmount = Utils.translateColorCodes(commandsSection.getStringList("givekrystal.invalidamount"));
            Config.giveKrystalSuccess = Utils.translateColorCodes(commandsSection.getStringList("givekrystal.givekrystal"));
            Config.giveKrystalNoPlayer = Utils.translateColorCodes(commandsSection.getStringList("givekrystal.noplayer"));
            Config.giveKrystalNickPlayer = Utils.translateColorCodes(commandsSection.getStringList("givekrystal.nickplayer"));
            Config.giveKeyInvalidAmount = Utils.translateColorCodes(commandsSection.getStringList("give.invalidamount"));
            Config.giveKeySuccess = Utils.translateColorCodes(commandsSection.getStringList("give.givekey"));
            Config.giveKeyNoPlayer = Utils.translateColorCodes(commandsSection.getStringList("give.noplayer"));
            Config.giveKeyNickPlayer = Utils.translateColorCodes(commandsSection.getStringList("give.nickplayer"));
            Config.spawnHelp = Utils.translateColorCodes(commandsSection.getStringList("spawn.helpspawn"));
            Config.spawnSuccess = Utils.translateColorCodes(commandsSection.getStringList("spawn.dangspawn"));
            Config.spawnNumberError = Utils.translateColorCodes(commandsSection.getStringList("spawn.numberhelp"));
            Config.spawnSchemHelp = Utils.translateColorCodes(commandsSection.getStringList("spawnschem.helpspawn"));
            Config.spawnSchemNotFound = Utils.translateColorCodes(commandsSection.getStringList("spawnschem.onlainfile"));
            Config.spawnSchemNotRegistered = Utils.translateColorCodes(commandsSection.getStringList("spawnschem.registerschem"));
            Config.spawnSchemSuccess = Utils.translateColorCodes(commandsSection.getStringList("spawnschem.spawncordenates"));
            Config.reloadSuccess = Utils.translateColorCodes(commandsSection.getStringList("reload.reloadplugin"));
            Config.undoHelp = Utils.translateColorCodes(commandsSection.getStringList("undo.helpundo"));
            Config.undoRemoveRegion = Utils.translateColorCodes(commandsSection.getStringList("undo.removeregion"));
            Config.undoRemoveShulker = Utils.translateColorCodes(commandsSection.getStringList("undo.removeshulker"));
            Config.undoRegionNotFound = Utils.translateColorCodes(commandsSection.getStringList("undo.regionfound"));
            Config.tpMessage = Utils.translateColorCodes(commandsSection.getStringList("list.tp"));
            Config.regionNotFoundMessage = Utils.translateColorCodes(commandsSection.getStringList("list.regionfound"));
            Config.spawnFailed = Utils.translateColorCodes(commandsSection.getStringList("spawn.failed"));

        } else {
            plugin.getLogger().warning("Раздел 'messages' отсутствует в messages.yml");
        }
    }
}