package org.dbekasov11.adang.data;

import java.util.List;
import org.bukkit.block.Biome;

public class DangData {
    private final String fileName;
    private final String world;
    private final List<Biome> biome;

    public DangData(String fileName, String world, List<Biome> biome) {
        this.fileName = fileName;
        this.world = world;
        this.biome = biome;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getWorld() {
        return this.world;
    }

    public List<Biome> getBiome() {
        return this.biome;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DangData)) {
            return false;
        } else {
            DangData other = (DangData)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                String this$world;
                String other$world;
                label42: {
                    this$world = this.getFileName();
                    other$world = other.getFileName();
                    if (this$world == null) {
                        if (other$world == null) {
                            break label42;
                        }
                    } else if (this$world.equals(other$world)) {
                        break label42;
                    }

                    return false;
                }

                this$world = this.getWorld();
                other$world = other.getWorld();
                if (this$world == null) {
                    if (other$world != null) {
                        return false;
                    }
                } else if (!this$world.equals(other$world)) {
                    return false;
                }

                Object this$biome = this.getBiome();
                Object other$biome = other.getBiome();
                if (this$biome == null) {
                    if (other$biome != null) {
                        return false;
                    }
                } else if (!this$biome.equals(other$biome)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof DangData;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $fileName = this.getFileName();
        result = result * 59 + ($fileName == null ? 43 : $fileName.hashCode());
        Object $world = this.getWorld();
        result = result * 59 + ($world == null ? 43 : $world.hashCode());
        Object $biome = this.getBiome();
        result = result * 59 + ($biome == null ? 43 : $biome.hashCode());
        return result;
    }
}