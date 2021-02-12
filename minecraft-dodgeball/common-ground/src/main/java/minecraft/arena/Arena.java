package minecraft.arena;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public class Arena {
    // corrected convention : x depth, z width, y height.
    private Location location;
    private final int width;
    private final int depth;
    private final int height;
    private final World thisWorld;

    private ArrayList<Location> spawnsTeamOne; // list of locations to use for teleports and respawn
    private ArrayList<Location> spawnsTeamTwo;

    private HashSet<Player> teamOne;
    private HashSet<Player> teamTwo;

    private final Target[] targets;

    private ResourceChest[] resources = new ResourceChest[2];
    private Location loc1;
    private Location loc2;

    public Arena(Location location, int width, int depth, int height) {
        this.location = location;
        this.thisWorld = location.getWorld();
        this.width = width;
        this.depth = depth;
        this.height = height;
        this.spawnsTeamOne = new ArrayList<>();
        this.spawnsTeamTwo = new ArrayList<>();
        this.teamOne = new HashSet<>();
        this.teamTwo = new HashSet<>();
        this.buildArena();
        this.setSpawns();

        Location redTargetLocation = this.location.clone().add(this.width - 5, 2, this.depth / 2);
        Location greenTargetLocation = this.location.clone().add(5, 2, this.depth / 2);

        // target owned by green team is on the green side of the arena and must be destroyed by red team and vica versa
        this.targets = new Target[] {new Target(greenTargetLocation, "green", 3, Material.RED_WOOL), new Target(redTargetLocation, "red", 3, Material.GREEN_WOOL)};

        putResourceChests(redTargetLocation.clone().add(3, 0, 0), greenTargetLocation.clone().add(-3, 0, 0));
    }

    private void putResourceChests(Location chestLocOne, Location chestLocTwo) {
    	/**
    	 * Puts the resource chests on locations behind the target locations,
    	 * but only if that space is available
    	 */
    	loc1 = chestLocOne;
        loc2 = chestLocTwo;

        if (loc1.getBlock().isEmpty()) {
        	this.resources[0] = new ResourceChest(loc1);
        }
        if (loc2.getBlock().isEmpty()) {
        	this.resources[1] = new ResourceChest(loc2);
        }
    }

    public void refillResources() {
    	/**
    	 * Place a new chest if one disappears and/or restock the chests
    	 */
    	if (loc1.getBlock().isEmpty()) {
    		putResourceChests(loc1, loc2);
    		resources[1].restock();
    	}
    	else if (loc2.getBlock().isEmpty()) {
    		putResourceChests(loc1, loc2);
    		resources[0].restock();
    	}
    	else {
    		for (ResourceChest chest : resources) {
    			chest.restock();
    		}
    	}
    }
    
    public ResourceChest getChestOne() {
    	return resources[0];
    }
    
    public ResourceChest getChestTwo() {
    	return resources[1];
    }

    //<editor-fold desc="getters">
    public Location getLocation() {
        return location;
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public int getHeight() {
        return height;
    }

    public HashSet<Player> getTeamTwo() {return teamTwo; }

    public HashSet<Player> getTeamOne() {return teamOne; }

    public ArrayList<Location> getSpawnsTeamOne() {
        return spawnsTeamOne;
    }

    public ArrayList<Location> getSpawnsTeamTwo() {
        return spawnsTeamTwo;
    }

    private void setSpawns() {
        int x1 = location.getBlockX();
        int y1 = location.getBlockY();
        int z1 = location.getBlockZ();

        for (int x = x1; x < x1 + depth; x++) {
            for (int z = z1; z < z1 + width; z++) {
                if (x > x1 && x < x1 + depth / 2 - 2 && z > z1 && z < z1 + width-1) {
                    spawnsTeamOne.add(thisWorld.getBlockAt(x, y1+2, z).getLocation());
                } else if (x >= x1 + depth / 2 + 2 && x < x1 + depth - 1 && z > z1 && z < z1 + width-1) {
                    spawnsTeamTwo.add(thisWorld.getBlockAt(x, y1+2, z).getLocation());
                }
            }
        }
    }

    public Location getRandomAvailableLocation(ArrayList<Location> locations) {
        Random random = new Random();
        Location trialLocation = locations.get(random.nextInt(locations.size()));
        while (!(thisWorld.getBlockAt(trialLocation).isEmpty() && trialLocation.getBlockY() < location.getBlockY()+3)) {
            trialLocation = locations.get(random.nextInt(locations.size()));
        }
        return trialLocation;
    }

    public Target[] getTargets(){
        return this.targets;
    }

    //</editor-fold>
    private void buildArena() { // ugly
        int x1 = location.getBlockX();
        int y1 = location.getBlockY();
        int z1 = location.getBlockZ();
        for (int x = x1; x < x1 + width; x++) {
            for (int y = y1 + 1; y < y1 + height+1; y++) {
                for (int z = z1; z < z1 + depth ; z++) {
                    // x depth, z width, y height
                    Block currentBlock = thisWorld.getBlockAt(x, y, z);
                    if ((y == y1 + 1 && x < x1 + width && z < z1 + depth) || // bottom
                            (y < y1 + height && x < x1 + width && z == z1 ) || // left wall
                            (y < y1 + height && x < x1 +width && z == z1 + depth - 1 ) || // right wall
                            (y == y1 + 2 && x == x1 +width/2 + 1 && z < z1 + depth) || // inner right barrier
                            (y == y1 + 2 && x == x1 +width/2 - 2 && z < z1 + depth) // inner left barrier
                    ) {
                        currentBlock.setType(Material.BEDROCK);
                    }
                    else if (y > y1 + 1 && y < y1 + height && x == x1 + width - 1 && z < z1 + depth - 1) { // team red wall
                        currentBlock.setType(Material.BEDROCK);
                    }
                    else if (y > y1 +1 && y < y1 + height && x == x1 && z < z1 + depth - 1) { // team green wall
                        currentBlock.setType(Material.BEDROCK);
                    }
                    else if (y == y1 + 2 && (x == x1 +width/2 || x == x1 +width/2 - 1)&& z < z1 + depth) {
                        currentBlock.setType(Material.LAVA);
                    }

                    else {
                        currentBlock.setType(Material.AIR);
                    }
                }
            }
        }
    }
}
