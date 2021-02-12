package minecraft.teamocto;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import minecraft.teamocto.models.ShieldModel;

public class Shield implements Listener {
	
	private final Material MATERIAL = Material.GLASS_PANE;
	private int SIZE;
	private Location location;
	private Block mainBlock;
	private BlockFace shieldNormal;
	private Inventory playersStuff;
	private List<ShieldModel> builtShields;

	public Shield() {
		builtShields = new ArrayList<>();
	}
	public void buildShield(PlayerInteractEvent myEvent, int size){
		mainBlock = myEvent.getClickedBlock();
		SIZE = size;
		shieldNormal = myEvent.getPlayer().getFacing();
		this.location = mainBlock.getLocation();
		playersStuff = myEvent.getPlayer().getInventory();
		
		if (SIZE > 1) {
			if (playersStuff.contains(MATERIAL, SIZE * SIZE)) {
				createShieldBlocks(myEvent.getPlayer());
			}
			else {
				myEvent.getPlayer().sendMessage("Not enough glass pane to build a wall, " +
						SIZE * SIZE + " are needed!");
				myEvent.setCancelled(true);
			}
		}
		else if (SIZE == 1) {
			mainBlock.setType(MATERIAL);
		}


	}
	private void createShieldBlocks(Player player) {
		// Put the shield perpendicular to facing direction of player
		Vector shieldSide = shieldNormal.getDirection().rotateAroundY(Math.PI/2);
		ShieldModel builtShield = new ShieldModel(player);

		for (int y = 1; y <= SIZE; y++) {
			Location nextTopBlockLoc = location.clone().add(new Vector(0,y,0));
			Location nextTopBlockLocClone = nextTopBlockLoc.clone();
			for (int x = 0; x <= SIZE/2; x++) {
				Location nextSideBlockLoc = nextTopBlockLoc.add(shieldSide.clone().multiply(x));
				changeBlock(nextSideBlockLoc, builtShield);
			}
			for (int x = 0; x <= (SIZE - 1)/2; x++) {
				Location nextSideBlockLoc = nextTopBlockLocClone.subtract(shieldSide.clone().multiply(x));
				changeBlock(nextSideBlockLoc, builtShield);
			}
		}
		builtShields.add(builtShield);
	}
	
	private void changeBlock(Location location, ShieldModel shield) {
		Block thisBlock = location.getBlock();
		if (thisBlock.getType() == Material.AIR) {
			removeItem();
			thisBlock.setType(MATERIAL);
			shield.addBlockLocation(location.clone());
			// TODO remove resource
		}
	}
	
	public void removeItem() {
		/**
		 * Loop over the itemstacks in the player's inventory,
		 * find the correct material and remove one.
		 * If there is only one left, the slot will be emptied
		 */
        int size = playersStuff.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack slotIS = playersStuff.getItem(slot);
            if (slotIS.getType() == MATERIAL) {
            	int amount = slotIS.getAmount();
            	if (amount == 1) {
            		playersStuff.clear(slot);
            	}
            	else {
            		slotIS.setAmount(amount - 1);
            	}
            	break;
            }
        }
    }

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Location breakLocation = event.getBlock().getLocation();
		ShieldModel foundShield = null;

		for(ShieldModel shield : builtShields){
			if(shield.getOwner() == event.getPlayer() && Boolean.TRUE.equals(shield.hasLocation(breakLocation))) {
				event.setCancelled(true);
				foundShield = shield;
				break;
			}	
		}
		if(foundShield != null) {
			foundShield.breakShield();
			builtShields.remove(foundShield);
		}
	}

	public Material getType() {
		return MATERIAL;
	}
	
	public Location getLocation() {
		return location;
	}

}
