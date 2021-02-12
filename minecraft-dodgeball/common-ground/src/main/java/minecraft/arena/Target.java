package minecraft.arena;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Target {
  private final Location location;
  private final String owner;
  private final int length;
  private final Material material;
  private final Block[] blocks;

  public int getLength(){
    return this.length;
  }

  public Block[] getBlocks(){
    return this.blocks;
  }

  public Location getLocation() {
    return this.location;
  }

  public Material getMaterial(){
    return this.material;
  }

  protected Target(Location location, String owner, int length, Material material){
    this.location = location;
    this.owner = owner;
    this.length = length;
    this.blocks = new Block[(int) Math.pow(length,3)];
    this.material = material;

    int x1 = location.getBlockX() - (int) (0.5 * length);
    int y1 = location.getBlockY();
    int z1 = location.getBlockZ() - (int) (0.5 * length);

    int x2 = x1 + this.length;
    int y2 = y1 + this.length;
    int z2 = z1 + this.length;

    World world = this.location.getWorld();

    int i = 0;
    // Loop over the cube in the x dimension.
    for (int xPoint = x1; xPoint < x2; xPoint++) { 
        // Loop over the cube in the y dimension.
        for (int yPoint = y1; yPoint < y2; yPoint++) {
            // Loop over the cube in the z dimension.
            for (int zPoint = z1; zPoint < z2; zPoint++) {
                this.blocks[i] = world.getBlockAt(xPoint, yPoint, zPoint);
                this.blocks[i].setType(this.material);
                i++;
            }
        }
    }
  }
}
