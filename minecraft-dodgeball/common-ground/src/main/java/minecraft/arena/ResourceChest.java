package minecraft.arena;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ResourceChest {
	private Location pileLocation;
	private Block pileBlock;
	private Chest pileChest;
	private final int MAX_ITEMS = 216; // The maximum amount of items, equal to the chest size of 27 * 8
	private final double armourChance = 0.2; // The chance to respawn an armour-item

	private final ArrayList<Material> MATERIALS = new ArrayList<>(Arrays.asList(
					Material.GLASS_PANE,
					Material.GLASS_PANE,
					Material.IRON_BLOCK,
					Material.IRON_BLOCK,
					Material.ARROW,
					Material.ARROW,
					Material.ARROW,
					Material.SPECTRAL_ARROW));
	private final ArrayList<Material> ARMOURS = new ArrayList<>(Arrays.asList(
					Material.DIAMOND_CHESTPLATE,
					Material.DIAMOND_BOOTS));
	
	public ResourceChest(Location location) {
		/**
		 * Places a chest of resources on the given location
		 */
		pileLocation = location;
		pileBlock = pileLocation.getBlock();
		pileBlock.setType(Material.CHEST);
		pileChest = (Chest) pileBlock.getState();
		pileChest.getInventory().clear();
		
		for (Material armour : ARMOURS) {
			pileChest.getInventory().addItem(new ItemStack(armour, 1));
		}
		
		randomFillChest(MAX_ITEMS - ARMOURS.size());
	}

	private void randomFillChest(int amountToBeAdded) {
		/** 
		 * Fills the chest with random materials from the list
		 * up to the input amount
		 */
		for (int i = 0; i < amountToBeAdded; i++) {
			int materialIndex = new Random().nextInt(MATERIALS.size());
			Material addedItemType = MATERIALS.get(materialIndex);
			
			ItemStack itemStack = new ItemStack(addedItemType, 1);
			pileChest.getInventory().addItem(itemStack);
		}
	}
	
	public Chest getChest() {
		return pileChest;
	}
	
	public Inventory getInventory() {
		return pileChest.getInventory();
	}

	public void restock() {
		/**
		 * Add diamond-armour if it is missing with a chance armourChance
		 * and refill the chest based on the free space in it
		 */
		
		for (Material armour : ARMOURS) {
			if (!pileChest.getInventory().contains(armour)) {
				if (new Random().nextDouble() < armourChance) {
					pileChest.getInventory().addItem(new ItemStack(armour, 1));
				}
			}
		}
		
		int availableSpace = MAX_ITEMS;
		for (ItemStack eachStack : pileChest.getInventory()) {
			if (eachStack != null) {
				availableSpace = availableSpace - eachStack.getAmount();
			}
		}
		
		if (availableSpace > 0) {
			randomFillChest(availableSpace);
		}
	}
}
