package org.dbekasov11.adang.commands.handler;

import java.io.File;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.dbekasov11.adang.ADang;
import org.dbekasov11.adang.cfg.Config;
import org.dbekasov11.adang.data.DangData;
import org.dbekasov11.adang.dung.DungActions;

public class SpawnHandler {
    private final DungActions dungActions;
    private final ADang aDang;

    public SpawnHandler(DungActions dungActions, ADang aDang) {
        this.dungActions = dungActions;
        this.aDang = aDang;
    }

    public boolean handleSpawn(Player player, String[] strings) {
        if (strings.length != 2) {
            for (String message : Config.spawnHelp) {
                if (!message.trim().isEmpty())
                    player.sendMessage(message);
            }
            return true;
        }

        try {
            int amount = Integer.parseInt(strings[1]);
            int successCount = 0;

            for (int i = 0; i < amount; i++) {
                Location loc = randomSecurityLocation(player.getWorld());
                boolean spawned = this.dungActions.spawn(loc);

                if (spawned) {
                    successCount++;
                    for (String message : Config.spawnSuccess) {
                        if (!message.trim().isEmpty())
                            player.sendMessage(message
                                    .replace("{x}", String.valueOf(loc.getBlockX()))
                                    .replace("{y}", String.valueOf(loc.getBlockY()))
                                    .replace("{z}", String.valueOf(loc.getBlockZ())));
                    }
                } else {
                    for (String message : Config.spawnFailed) {
                        if (!message.trim().isEmpty())
                            player.sendMessage(message
                                    .replace("{x}", String.valueOf(loc.getBlockX()))
                                    .replace("{y}", String.valueOf(loc.getBlockY()))
                                    .replace("{z}", String.valueOf(loc.getBlockZ())));
                    }
                }
            }

        } catch (NumberFormatException e) {
            for (String message : Config.spawnNumberError) {
                if (!message.trim().isEmpty())
                    player.sendMessage(message);
            }
        }

        return true;
    }


    private boolean isSafeBlock(Material type) {
        return (type != Material.WATER && type != Material.LAVA && type != Material.AIR &&
                !type.name().contains("LEAVES") && !type.name().contains("FIRE"));
    }

    private int getSafeYNether(World world, int x, int z) {
        int minY = world.getMinHeight();
        int maxY = Math.min(world.getMaxHeight() - 1, 120);

        for (int y = minY + 32; y <= maxY; y++) {
            Location base = new Location(world, x, y, z);
            Block block = base.getBlock();
            Block above = block.getRelative(BlockFace.UP);
            Block above2 = above.getRelative(BlockFace.UP);

            if (isSafeBlock(block.getType()) &&
                    above.getType() == Material.AIR &&
                    above2.getType() == Material.AIR) {
                return y;
            }
        }
        return Math.max(minY, 64);
    }

    private int getSafeYEnd(World world, int x, int z) {
        int minY = world.getMinHeight();
        int maxY = Math.min(world.getMaxHeight() - 1, 80);
        for (int y = minY + 30; y <= maxY; y++) {
            Location base = new Location(world, x, y, z);
            Block block = base.getBlock();
            Block above = block.getRelative(BlockFace.UP);
            Block above2 = above.getRelative(BlockFace.UP);

            if (isSafeBlock(block.getType()) &&
                    above.getType() == Material.AIR &&
                    above2.getType() == Material.AIR) {
                return y;
            }
        }
        return Math.max(minY, 60);
    }

    public Location randomSecurityLocation(World world) {
        Location locTest = null;
        for (int i = 1; i <= 20; i++) {
            int y, x = getRandomNumber(Config.Spawn.minx, Config.Spawn.maxx);
            int z = getRandomNumber(Config.Spawn.minz, Config.Spawn.maxz);

            switch (world.getEnvironment()) {
                case NETHER:
                    y = getSafeYNether(world, x, z);
                    break;
                case THE_END:
                    y = getSafeYEnd(world, x, z);
                    break;
                default:
                    y = world.getHighestBlockYAt(x, z);
                    if (!isSafeBlock(new Location(world, x, y - 1, z).getBlock().getType())) {
                        y = findSafeOverworldY(world, x, z);
                    }
                    break;
            }

            Location loc = new Location(world, x, y, z);
            if (isSafeBlock(loc.getBlock().getType())) {
                locTest = loc;
                break;
            }
        }
        return locTest != null ? locTest : new Location(world, 0, world.getHighestBlockYAt(0, 0), 0);
    }

    private int findSafeOverworldY(World world, int x, int z) {
        int minY = world.getMinHeight();
        int maxY = world.getMaxHeight() - 1;

        for (int y = maxY; y >= minY; y--) {
            Location base = new Location(world, x, y, z);
            Block block = base.getBlock();
            Block above = block.getRelative(BlockFace.UP);

            if (isSafeBlock(block.getType()) && above.getType() == Material.AIR) {
                return y + 1;
            }
        }
        return minY + 64;
    }

    private int getRandomNumber(int min, int max) {
        return (int)(Math.random() * (max - min) + min);
    }

    public boolean handleSpawnSchem(Player player, String[] strings) {
        if (strings.length < 2) {
            for (String message : Config.spawnSchemHelp) {
                if (!message.trim().isEmpty())
                    player.sendMessage(message);
            }
            return true;
        }
        String schemName = strings[1] + ".schem";
        File schemFolder = new File(this.aDang.getDataFolder(), "schem");
        File schemFile = new File(schemFolder, schemName);

        if (!schemFile.exists()) {
            for (String message : Config.spawnSchemNotFound) {
                if (!message.trim().isEmpty())
                    player.sendMessage(message.replace("{schem_name}", schemName));
            }
            return true;
        }

        List<DangData> dangDataList = Config.getDangsDataList();
        boolean isRegistered = false;
        for (DangData dangData : dangDataList) {
            if (dangData.getFileName().equalsIgnoreCase(schemName)) {
                isRegistered = true;
                break;
            }
        }

        if (!isRegistered) {
            for (String message : Config.spawnSchemNotRegistered) {
                if (!message.trim().isEmpty())
                    player.sendMessage(message.replace("{schem_file}", schemName.replace(".schem", "")));
            }
            return true;
        }

        Location location = player.getLocation();
        for (String message : Config.spawnSchemSuccess) {
            if (!message.trim().isEmpty())
                player.sendMessage(message.replace("{schematika}", schemName.replace(".schem", "")));
        }

        this.dungActions.spawn(location, schemName);
        return true;
    }
}