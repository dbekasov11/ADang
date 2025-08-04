package org.dbekasov11.adang.cfg;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.parser.*;
import org.dbekasov11.adang.data.DangData;

import java.util.Collections;
import java.util.List;

public class Config {
   private static FileConfiguration configuration;
   private static List<DangData> dangsDataList;
   private static ItemStack key;
   private static ItemStack krystal;
   private static Sound openSound;
   private static Particle particle;
   private static int particleCount;
   private static double particleOffsetX;
   private static double particleOffsetY;
   private static double particleOffsetZ;
   private static boolean ignoreAirBlocks;
   public static int saveChance;
   public static int chanceSpawn;
   public static List<String> addItemHelp;
   public static List<String> addItemSuccess;
   public static List<String> addItemNumberError;
   public static List<String> addItemNoItem;
   public static List<String> giveKrystalInvalidAmount;
   public static List<String> giveKrystalSuccess;
   public static List<String> giveKrystalNoPlayer;
   public static List<String> giveKrystalNickPlayer;
   public static List<String> giveKeyInvalidAmount;
   public static List<String> giveKeySuccess;
   public static List<String> giveKeyNoPlayer;
   public static List<String> giveKeyNickPlayer;
   public static List<String> spawnHelp;
   public static List<String> spawnSuccess;
   public static List<String> spawnNumberError;
   public static List<String> spawnSchemHelp;
   public static List<String> spawnSchemNotFound;
   public static List<String> spawnSchemNotRegistered;
   public static List<String> spawnSchemSuccess;
   public static List<String> reloadSuccess;
   private static int minItemsInShulker;
   private static int maxItemsInShulker;
   private static int regionSizeX;
   private static int regionSizeZ;
   private static String regionNameFormat;
   private static int schemOffsetX;
   private static int schemOffsetY;
   private static int schemOffsetZ;
   private static Sound lockedSound;
   public static List<String> undoHelp;
   public static List<String> undoRemoveRegion;
   public static List<String> undoRemoveShulker;
   public static List<String> undoRegionNotFound;
   public static List<String> tpMessage;
   public static List<String> regionNotFoundMessage;
   public static List<String> spawnFailed;

   public static void load(FileConfiguration config, ADang plugin) {
      configuration = config;
      dangsDataList = DataParser.parseDangData(plugin);
      key = ItemParser.parseKey(plugin);
      krystal = ItemParser.parseKrystal(plugin);
      openSound = SoundParser.parseOpenSound(plugin);
      particle = ParticleParser.parseParticle(plugin);
      particleCount = ParticleParser.parseParticleCount(plugin);
      particleOffsetX = ParticleParser.parseParticleOffsetX(plugin);
      particleOffsetY = ParticleParser.parseParticleOffsetY(plugin);
      particleOffsetZ = ParticleParser.parseParticleOffsetZ(plugin);
      ignoreAirBlocks = SchemParser.parseIgnoreAirBlocks(plugin);
      schemOffsetX = SchemParser.parseOffsetX(plugin);
      schemOffsetY = SchemParser.parseOffsetY(plugin);
      schemOffsetZ = SchemParser.parseOffsetZ(plugin);
      MessageParser.parseMessages(plugin);
      SpawnParser.parseSpawn(plugin);
      minItemsInShulker = SlotParser.parseMinItemsInShulker(plugin);
      maxItemsInShulker = SlotParser.parseMaxItemsInShulker(plugin);
      regionSizeX = RegionParser.parseRegionSizeX(plugin);
      regionSizeZ = RegionParser.parseRegionSizeZ(plugin);
      regionNameFormat = RegionParser.parseRegionNameFormat(plugin);
      lockedSound = SoundParser.parseLockedSound(plugin);

   }
   public static FileConfiguration getConfiguration() {
      return configuration;
   }



   public static List<DangData> getDangsDataList() {
      return Collections.unmodifiableList(dangsDataList);
   }

   public static ItemStack getKey() {
      return key;
   }

   public static ItemStack getKrystal() {
      return krystal;
   }

   public static int getSchemOffsetX() {
      return schemOffsetX;
   }

   public static int getSchemOffsetY() {
      return schemOffsetY;
   }

   public static int getSchemOffsetZ() {
      return schemOffsetZ;
   }

   public static int getChanceSpawn() {
      return chanceSpawn;
   }

   public static Sound getOpenSound() {
      return openSound;
   }

   public static Particle getParticle() {
      return particle;
   }

   public static int getParticleCount() {
      return particleCount;
   }

   public static double getParticleOffsetX() {
      return particleOffsetX;
   }

   public static double getParticleOffsetY() {
      return particleOffsetY;
   }

   public static double getParticleOffsetZ() {
      return particleOffsetZ;
   }

   public static int getSaveChance() {
      return saveChance;
   }


   public static boolean isIgnoreAirBlocks() {
      return ignoreAirBlocks;
   }

   public static int getMinItemsInShulker() {
      return minItemsInShulker;
   }

   public static int getMaxItemsInShulker() {
      return maxItemsInShulker;
   }

   public static Sound getLockedSound() {
      return lockedSound;
   }

   public static int getRegionSizeX() {
      return regionSizeX;
   }

   public static int getRegionSizeZ() {
      return regionSizeZ;
   }

   public static String getRegionNameFormat() {
      return regionNameFormat;
   }



   public static class Spawn {
      public static int minx;
      public static int maxx;
      public static int minz;
      public static int maxz;
   }
   public static class Messages {
      public static List<String> openDung;
      public static String saveKey;
      public static List<String> closedDung;
   }
}
