package minecraft.teamocto.muurbouwen;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Wall{
	
	// Empty constructor, for what? i dunno
	public Wall() {
		super();
	}
	
	public void BuildWall(PlayerInteractEvent event) {
		// get the player from the event
		Player player = event.getPlayer();
		
		// can not place a wall in the air
		if(!(player.getTargetBlock((Set<Material>) null, 2).getType() == Material.AIR)) {
			
			if (player.getInventory().contains(Material.IRON_BLOCK, 9)) {
				// get the location of the player
				Location blockLoc = player.getTargetBlock((Set<Material>) null, 4).getLocation();
				// get the direction of the player (NORTH,EAST,SOUTH or WEST
				String directionPlayer = getDirection(player);
				
				// set 9 blocks in front of the player 
				CreateBlockForWall(blockLoc,0,1,Material.IRON_BLOCK,directionPlayer);
	    		CreateBlockForWall(blockLoc,0,1,Material.IRON_BLOCK,directionPlayer);
				CreateBlockForWall(blockLoc,0,1,Material.IRON_BLOCK,directionPlayer);
				
				CreateBlockForWall(blockLoc,1,-2,Material.IRON_BLOCK,directionPlayer);
	    		CreateBlockForWall(blockLoc,0,1,Material.IRON_BLOCK,directionPlayer);
				CreateBlockForWall(blockLoc,0,1,Material.IRON_BLOCK,directionPlayer);
					
				CreateBlockForWall(blockLoc,1,-2,Material.IRON_BLOCK,directionPlayer);
	    		CreateBlockForWall(blockLoc,0,1,Material.IRON_BLOCK,directionPlayer);
				CreateBlockForWall(blockLoc,0,1,Material.IRON_BLOCK,directionPlayer);
				removeItems(player.getInventory(),Material.IRON_BLOCK,9);
			}
			else {
				player.sendMessage("Not enough iron blocks to build a wall, 9 are needed!");
			}
		}
	}
	public static void removeItems(Inventory inventory, Material type, int amount) {
        if (amount <= 0) return;
        int size = inventory.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            if (type == is.getType()) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                    break;
                } else {
                    inventory.clear(slot);
                    amount = -newAmount;
                    if (amount == 0) break;
                }
            }
        }
    }
		
	public static void CreateBlockForWall(Location loc, int x, int y, Material mat,String direction) {
		// check with direction the player is facing
		if (direction == "NORTH" || direction == "SOUTH") {
			// get the direction of the new block
			loc.setY(loc.getY() + y);
			loc.setX(loc.getX() + x);
			Block block = loc.getBlock();
			if (block.getType() == Material.AIR) {
				// if the block is air (can't replace exiting blocks)
				block.setType(mat);
			}
		}
		if (direction == "WEST" || direction == "EAST") {
			// get the direction of the new block
			loc.setY(loc.getY() + y);
			loc.setZ(loc.getZ() + x);
			Block block = loc.getBlock();
			if (block.getType() == Material.AIR) {
				// if the block is air (can't replace exiting blocks)
				block.setType(mat);
			}
		}
	}
	
	public static String getDirection(Player player) {
		// get the yaw of the player
		float yaw = player.getLocation().getYaw();
		// check if the yaw is lower than 0
		if (yaw < 0){
			yaw += 360;
		}
		// translate the yaw to SOUTH, WEST,NORTH or EAST 
		  if (yaw >= 315 || yaw < 45) {
		        return "SOUTH";
		    } else if (yaw < 135) {
		        return "WEST";
		    } else if (yaw < 225) {
		        return "NORTH";
		    } else if (yaw < 315) {
		        return "EAST";
		    }
		    return "NORTH";
	}
}